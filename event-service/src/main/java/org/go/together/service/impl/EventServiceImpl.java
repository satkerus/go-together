package org.go.together.service.impl;

import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.go.together.base.CommonCrudService;
import org.go.together.client.ContentClient;
import org.go.together.client.LocationClient;
import org.go.together.client.RouteInfoClient;
import org.go.together.client.UserClient;
import org.go.together.compare.FieldMapper;
import org.go.together.dto.*;
import org.go.together.enums.CrudOperation;
import org.go.together.model.Event;
import org.go.together.repository.interfaces.EventRepository;
import org.go.together.service.interfaces.EventService;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EventServiceImpl extends CommonCrudService<EventDto, Event> implements EventService {
    private final LocationClient locationClient;
    private final ContentClient contentClient;
    private final UserClient userClient;
    private final RouteInfoClient routeInfoClient;

    @Override
    protected Event enrichEntity(Event entity, EventDto dto, CrudOperation crudOperation) {
        if (crudOperation == CrudOperation.UPDATE) {
            GroupLocationDto locationDto = dto.getRoute();
            locationDto.setGroupId(entity.getId());
            locationDto.setCategory(LocationCategory.EVENT);
            IdDto route = locationClient.update("groupLocations", locationDto);
            entity.setRouteId(route.getId());

            GroupPhotoDto groupPhotoDto = dto.getGroupPhoto();
            groupPhotoDto.setGroupId(entity.getId());
            groupPhotoDto.setCategory(PhotoCategory.EVENT);
            IdDto photoId = contentClient.update("groupPhotos", groupPhotoDto);
            entity.setGroupPhotoId(photoId.getId());
        } else if (crudOperation == CrudOperation.CREATE) {
            GroupLocationDto locationDto = dto.getRoute();
            locationDto.setGroupId(entity.getId());
            locationDto.setCategory(LocationCategory.EVENT);
            IdDto route = locationClient.create("groupLocations", dto.getRoute());
            entity.setRouteId(route.getId());

            GroupPhotoDto groupPhotoDto = dto.getGroupPhoto();
            groupPhotoDto.setGroupId(entity.getId());
            groupPhotoDto.setCategory(PhotoCategory.EVENT);
            IdDto photoId = contentClient.create("groupPhotos", groupPhotoDto);
            entity.setGroupPhotoId(photoId.getId());

            EventLikeDto eventLikeDto = new EventLikeDto();
            eventLikeDto.setEventId(entity.getId());
            eventLikeDto.setUsers(Collections.emptySet());
            userClient.create("eventLikes", eventLikeDto);
        } else if (crudOperation == CrudOperation.DELETE) {
            locationClient.delete("groupLocations", entity.getRouteId());
            contentClient.delete("groupPhotos", entity.getGroupPhotoId());
            userClient.deleteEventLike(entity.getId());
        }
        return entity;
    }

    @Override
    public Set<SimpleDto> autocompleteEvents(String name) {
        Collection<Event> events;
        if (StringUtils.isNotBlank(name)) {
            events = ((EventRepository) repository).findEventsByNameLike(name, 0, 5);
        } else {
            events = repository.createQuery().build().fetchWithPageable(0, 5);
        }
        return events.stream()
                .map(event -> new SimpleDto(event.getId().toString(), event.getName()))
                .collect(Collectors.toSet());
    }

    @Override
    public String getServiceName() {
        return "events";
    }

    @Override
    public Map<String, FieldMapper> getMappingFields() {
        return Map.of("name", FieldMapper.builder()
                        .currentServiceField("name")
                        .fieldClass(String.class).build(),
                "authorId", FieldMapper.builder()
                        .currentServiceField("authorId")
                        .fieldClass(UUID.class).build(),
                "author", FieldMapper.builder()
                        .currentServiceField("authorId")
                        .remoteServiceClient(userClient)
                        .remoteServiceName("users")
                        .remoteServiceFieldGetter("id")
                        .fieldClass(UUID.class).build(),
                "idEventRoutes", FieldMapper.builder()
                        .currentServiceField("id")
                        .remoteServiceClient(locationClient)
                        .remoteServiceName("groupLocations")
                        .remoteServiceFieldGetter("groupId")
                        .fieldClass(UUID.class).build(),
                "startDate", FieldMapper.builder()
                        .currentServiceField("startDate")
                        .fieldClass(Date.class).build(),
                "endDate", FieldMapper.builder()
                        .fieldClass(Date.class)
                        .currentServiceField("endDate").build());
    }
}
