package org.go.together.service;

import org.go.together.CrudServiceImpl;
import org.go.together.dto.FieldMapper;
import org.go.together.dto.IdDto;
import org.go.together.dto.NotificationDto;
import org.go.together.enums.NotificationStatus;
import org.go.together.model.Notification;
import org.go.together.repository.NotificationRepository;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.Optional;
import java.util.UUID;

@Component
public class NotificationService extends CrudServiceImpl<NotificationDto, Notification> {
    private final NotificationRepository notificationRepository;

    protected NotificationService(NotificationRepository notificationRepository) {
        this.notificationRepository = notificationRepository;
    }

    public NotificationDto saveNotificationByProducerId(UUID producerId, NotificationStatus status) {
        Optional<Notification> notificationOptional = notificationRepository.findByProducerId(producerId);
        IdDto savedNotification;
        if (notificationOptional.isPresent()) {
            Notification notification = notificationOptional.get();
            NotificationDto notificationDto = mapper.entityToDto(notification);
            notificationDto.setStatus(status);
            savedNotification = super.update(notificationDto);

        } else {
            NotificationDto notificationDto = new NotificationDto();
            notificationDto.setProducerId(producerId);
            notificationDto.setStatus(status);
            savedNotification = super.create(notificationDto);
        }
        return super.read(savedNotification.getId());
    }

    @Override
    public String getServiceName() {
        return "notification";
    }

    @Override
    public Map<String, FieldMapper> getMappingFields() {
        return null;
    }
}
