package org.go.together.validation;

import lombok.RequiredArgsConstructor;
import org.go.together.dto.*;
import org.go.together.dto.form.FilterDto;
import org.go.together.dto.form.FormDto;
import org.go.together.enums.CrudOperation;
import org.go.together.enums.FindOperator;
import org.go.together.kafka.producers.crud.FindKafkaProducer;
import org.go.together.kafka.producers.crud.ValidateKafkaProducer;
import org.go.together.validation.dto.DateIntervalDto;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.Map;
import java.util.UUID;
import java.util.function.Function;

@Component
@RequiredArgsConstructor
public class EventValidator extends CommonValidator<EventDto> {
    private final FindKafkaProducer<UserDto> findUserKafkaProducer;
    private final ValidateKafkaProducer<GroupPhotoDto> photoValidator;
    private final ValidateKafkaProducer<GroupLocationDto> locationValidator;
    private final ValidateKafkaProducer<GroupRouteInfoDto> routeInfoValidator;

    @Override
    public Map<String, Function<EventDto, ?>> getMapsForCheck(UUID requestId) {
        return Map.of(
                "event name", EventDto::getName,
                "event description", EventDto::getDescription,
                "event people capacity", EventDto::getPeopleCount,
                "event dates", eventDto -> new DateIntervalDto(eventDto.getStartDate(), eventDto.getEndDate()),
                "routes", EventDto::getRoute,
                "photos", eventDto -> eventDto.getGroupPhoto().getPhotos(),
                "routes locations", eventDto -> eventDto.getRoute().getLocations(),
                "event photos", eventDto -> photoValidator.validate(requestId, eventDto.getGroupPhoto()),
                "event locations", eventDto -> locationValidator.validate(requestId, eventDto.getRoute()),
                "routes info", eventDto -> routeInfoValidator.validate(requestId, eventDto.getRouteInfo())
        );
    }

    @Override
    protected String commonValidation(UUID requestId, EventDto dto, CrudOperation crudOperation) {
        StringBuilder errors = new StringBuilder();

        if (isNotPresentUser(requestId, dto.getAuthor().getId())) {
            errors.append("Author has incorrect uuid: ")
                    .append(dto.getAuthor().getId())
                    .append(". ");
        }

        return errors.toString();
    }

    private boolean isNotPresentUser(UUID requestId, UUID authorId) {
        FilterDto filterDto = new FilterDto();
        filterDto.setFilterType(FindOperator.EQUAL);
        filterDto.setValues(Collections.singleton(Collections.singletonMap("id", authorId)));
        FormDto formDto = new FormDto();
        formDto.setFilters(Collections.singletonMap("id", filterDto));
        formDto.setMainIdField("users.id");
        return findUserKafkaProducer.find(requestId, formDto).getResult().isEmpty();
    }
}
