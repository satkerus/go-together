package org.go.together.controller;

import org.go.together.client.LocationClient;
import org.go.together.dto.EventLocationDto;
import org.go.together.dto.IdDto;
import org.go.together.service.EventLocationService;
import org.springframework.web.bind.annotation.RestController;

import java.util.Set;
import java.util.UUID;

@RestController
public class LocationController implements LocationClient {
    private final EventLocationService eventLocationService;

    public LocationController(EventLocationService eventLocationService) {
        this.eventLocationService = eventLocationService;
    }

    @Override
    public Set<EventLocationDto> getEventRoute(UUID eventId) {
        return eventLocationService.getEventRoute(eventId);
    }

    @Override
    public Set<IdDto> saveEventRoutes(Set<EventLocationDto> eventLocationDtos) {
        return eventLocationService.saveEventRoutes(eventLocationDtos);
    }

    @Override
    public Set<IdDto> updateEventRoute(Set<EventLocationDto> eventLocationDtos) {
        return eventLocationService.updateEventRoutes(eventLocationDtos);
    }

    @Override
    public boolean deleteRoutes(Set<EventLocationDto> eventLocationDtos) {
        return eventLocationService.deleteByEventId(eventLocationDtos);
    }
}
