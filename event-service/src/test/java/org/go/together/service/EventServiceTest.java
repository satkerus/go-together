package org.go.together.service;

import org.go.together.client.ContentClient;
import org.go.together.client.LocationClient;
import org.go.together.client.RouteInfoClient;
import org.go.together.client.UserClient;
import org.go.together.context.RepositoryContext;
import org.go.together.dto.EventDto;
import org.go.together.dto.IdDto;
import org.go.together.dto.SimpleDto;
import org.go.together.dto.ValidationMessageDto;
import org.go.together.enums.CrudOperation;
import org.go.together.model.Event;
import org.go.together.notification.streams.NotificationSource;
import org.go.together.service.interfaces.EventService;
import org.go.together.tests.CrudServiceCommonTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.MessageChannel;
import org.springframework.test.context.ContextConfiguration;

import java.util.Calendar;
import java.util.Date;
import java.util.Set;
import java.util.UUID;

import static org.apache.commons.lang3.StringUtils.EMPTY;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ContextConfiguration(classes = RepositoryContext.class)
public class EventServiceTest extends CrudServiceCommonTest<Event, EventDto> {
    @Autowired
    private UserClient userClient;

    @Autowired
    private LocationClient locationClient;

    @Autowired
    private ContentClient contentClient;

    @Autowired
    private RouteInfoClient routeInfoClient;

    @Autowired
    private NotificationSource source;

    @Override
    @BeforeEach
    public void init() {
        super.init();
        MessageChannel messageChannel = Mockito.mock(MessageChannel.class);
        when(source.output()).thenReturn(messageChannel);
        when(messageChannel.send(any())).thenReturn(true);
        prepareDto(dto);
        prepareDto(updatedDto);
    }

    @Test
    public void autocompleteEventTest() {
        IdDto idDto = crudService.create(dto);
        Set<SimpleDto> events = ((EventService) crudService).autocompleteEvents(dto.getName());

        assertEquals(1, events.size());
        assertEquals(idDto.getId(), UUID.fromString(events.iterator().next().getId()));
    }

    @Override
    protected EventDto createDto() {
        EventDto eventDto = factory.manufacturePojo(EventDto.class);
        Date date = new Date();
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MONTH, rand.nextInt(10) + 1);
        eventDto.setStartDate(calendar.getTime());

        calendar.add(Calendar.MONTH, rand.nextInt(10) + 1);
        eventDto.setEndDate(calendar.getTime());
        return eventDto;
    }

    private void prepareDto(EventDto eventDto) {
        when(userClient.checkIfUserPresentsById(eventDto.getAuthor().getId())).thenReturn(true);
        when(userClient.read("users", eventDto.getAuthor().getId())).thenReturn(eventDto.getAuthor());
        when(locationClient.read("groupLocations", eventDto.getRoute().getId())).thenReturn(eventDto.getRoute());
        when(contentClient.read("groupPhotos", eventDto.getGroupPhoto().getId())).thenReturn(eventDto.getGroupPhoto());
        when(locationClient.create("groupLocations", eventDto.getRoute())).thenReturn(new IdDto(eventDto.getRoute().getId()));
        when(locationClient.update("groupLocations", eventDto.getRoute())).thenReturn(new IdDto(eventDto.getRoute().getId()));
        when(contentClient.update("groupPhotos", eventDto.getGroupPhoto())).thenReturn(new IdDto(eventDto.getGroupPhoto().getId()));
        when(contentClient.create("groupPhotos", eventDto.getGroupPhoto())).thenReturn(new IdDto(eventDto.getGroupPhoto().getId()));
        when(contentClient.validate("groupPhotos", eventDto.getGroupPhoto())).thenReturn(new ValidationMessageDto(EMPTY));
        when(locationClient.validate("groupLocations", eventDto.getRoute())).thenReturn(new ValidationMessageDto(EMPTY));
    }

    @Override
    protected void checkDtos(EventDto dto, EventDto savedObject, CrudOperation operation) {
        assertEquals(dto.getAuthor(), savedObject.getAuthor());
        assertTrue(dto.getRoute().getLocations().stream()
                .allMatch(route -> savedObject.getRoute().getLocations().stream().anyMatch(route::equals)));
        assertEquals(dto.getDescription(), savedObject.getDescription());
        assertEquals(dto.getName(), savedObject.getName());
        assertEquals(dto.getEndDate(), savedObject.getEndDate());
        assertEquals(dto.getStartDate(), savedObject.getStartDate());
        assertEquals(dto.getGroupPhoto(), savedObject.getGroupPhoto());
        assertEquals(dto.getPeopleCount(), savedObject.getPeopleCount());
    }
}
