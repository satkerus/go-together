package org.go.together.kafka.producer.config.crud;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.common.serialization.UUIDDeserializer;
import org.apache.kafka.common.serialization.UUIDSerializer;
import org.go.together.dto.Dto;
import org.go.together.dto.FormDto;
import org.go.together.dto.ResponseDto;
import org.go.together.enums.TopicKafkaPostfix;
import org.go.together.kafka.producer.config.interfaces.KafkaProducerConfigurator;
import org.go.together.kafka.producer.enums.ProducerPostfix;
import org.go.together.kafka.producer.impl.CommonFindKafkaProducer;
import org.go.together.kafka.producers.ReplyKafkaProducer;
import org.go.together.kafka.producers.crud.FindKafkaProducer;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.kafka.core.ConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaConsumerFactory;
import org.springframework.kafka.core.DefaultKafkaProducerFactory;
import org.springframework.kafka.core.ProducerFactory;
import org.springframework.kafka.listener.ContainerProperties;
import org.springframework.kafka.listener.KafkaMessageListenerContainer;
import org.springframework.kafka.requestreply.ReplyingKafkaTemplate;
import org.springframework.kafka.support.serializer.JsonDeserializer;
import org.springframework.kafka.support.serializer.JsonSerializer;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

@Component
public class FindProducerKafkaConfig<D extends Dto> implements KafkaProducerConfigurator {
    private ProducerFactory<UUID, FormDto> findProducerFactory(String kafkaServer) {
        Map<String, Object> configProps = new HashMap<>();
        configProps.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        configProps.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, UUIDSerializer.class);
        configProps.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, JsonSerializer.class);
        return new DefaultKafkaProducerFactory<>(configProps);
    }

    private Map<String, Object> findConsumerConfigs(String kafkaServer, String kafkaGroupId) {
        Map<String, Object> props = new HashMap<>();
        props.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, kafkaServer);
        props.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, UUIDDeserializer.class);
        props.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, JsonDeserializer.class);
        props.put(ConsumerConfig.GROUP_ID_CONFIG, kafkaGroupId);
        return props;
    }

    private ReplyingKafkaTemplate<UUID, FormDto, ResponseDto<Object>> findReplyingKafkaTemplate(String kafkaServer,
                                                                                                KafkaMessageListenerContainer<UUID, ResponseDto<Object>> repliesContainer) {
        return new ReplyingKafkaTemplate<>(findProducerFactory(kafkaServer), repliesContainer);
    }

    private KafkaMessageListenerContainer<UUID, ResponseDto<Object>> findRepliesContainer(ConsumerFactory<UUID, ResponseDto<Object>> readReplyConsumerFactory,
                                                                                          String kafkaGroupId,
                                                                                          String consumerId) {
        String replyTopic = getReplyTopicId(consumerId) + kafkaGroupId;
        ContainerProperties containerProperties = new ContainerProperties(replyTopic);
        return new KafkaMessageListenerContainer<>(readReplyConsumerFactory, containerProperties);
    }

    private ConsumerFactory<UUID, ResponseDto<Object>> findReplyConsumerFactory(String kafkaServer, String kafkaGroupId) {
        JsonDeserializer<ResponseDto<Object>> resultJsonDeserializer = new JsonDeserializer<>();
        resultJsonDeserializer.addTrustedPackages("org.go.together.dto");
        return new DefaultKafkaConsumerFactory<>(findConsumerConfigs(kafkaServer, kafkaGroupId),
                new UUIDDeserializer(),
                resultJsonDeserializer);
    }

    public void configure(String kafkaServer,
                          String kafkaGroupId,
                          ConfigurableListableBeanFactory beanFactory,
                          String consumerId) {
        ConsumerFactory<UUID, ResponseDto<Object>> consumerFactory = findReplyConsumerFactory(kafkaServer, kafkaGroupId);
        KafkaMessageListenerContainer<UUID, ResponseDto<Object>> kafkaMessageListenerContainer =
                findRepliesContainer(consumerFactory, kafkaGroupId, consumerId);
        beanFactory.registerSingleton(consumerId + "FindRepliesContainer", kafkaMessageListenerContainer);
        ReplyingKafkaTemplate<UUID, FormDto, ResponseDto<Object>> replyingKafkaTemplate = findReplyingKafkaTemplate(kafkaServer, kafkaMessageListenerContainer);
        beanFactory.registerSingleton(consumerId + "FindReplyingKafkaTemplate", replyingKafkaTemplate);
        FindKafkaProducer<D> commonFindKafkaProducer = CommonFindKafkaProducer.create(replyingKafkaTemplate, consumerId, kafkaGroupId);
        beanFactory.registerSingleton(consumerId + ProducerPostfix.FIND.getDescription(), commonFindKafkaProducer);
    }

    private String getReplyTopicId(String consumerId) {
        return consumerId + TopicKafkaPostfix.FIND + ReplyKafkaProducer.KAFKA_REPLY_ID;
    }
}
