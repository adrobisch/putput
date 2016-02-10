package org.putput.calendar;

import org.putput.api.resource.Event;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import java.util.Optional;
import java.util.function.Function;

@Controller
public class EventResource extends BaseResource implements Event {
    
    @Autowired
    EventService eventService;
    
    @Override
    public PostEventResponse postEvent(org.putput.api.model.Event newEvent) throws Exception {
        EventEntity newEventEntity = eventService.createEvent(user().getUsername(), newEvent);
        return PostEventResponse.withCreated(link(Event.class, newEventEntity.getId()).getHref());
    }

    @Override
    public GetEventByIdResponse getEventById(String id) throws Exception {
        Optional<EventEntity> optionalEvent = eventService.getEvent(user().getUsername(), id);
        
        return optionalEvent
                .map(eventEntityToDto(user().getUsername()))
                .map(GetEventByIdResponse::withHaljsonOK)
                .orElse(GetEventByIdResponse.withNotFound());
    }

     static Function<EventEntity, org.putput.api.model.Event> eventEntityToDto(String username) {
        return eventEntity -> new org.putput.api.model.Event()
                .withOwner(username)
                .withCreated(eventEntity.getCreated().doubleValue())
                .withStart(eventEntity.getStart().doubleValue())
                .withEnd(eventEntity.getEnd().doubleValue())
                .withTitle(eventEntity.getTitle())
                .withDescription(eventEntity.getDescription())
                .withRecurrence(eventEntity.getRecurrence())
                .withType(eventEntity.getType().name())
                .withTimezone(eventEntity.getTimezone())
                .withRecurrenceEnd(eventEntity.getRecurrenceEnd().map(Long::doubleValue).orElse(null))
                .withId(eventEntity.getId());
    }

    @Override
    public PutEventByIdResponse putEventById(String id, org.putput.api.model.Event updatedEvent) throws Exception {
        return eventService.updateEvent(user().getUsername(), updatedEvent)
                .map(eventEntity -> PutEventByIdResponse.withOK())
                .orElse(PutEventByIdResponse.withOK());
    }

    @Override
    public DeleteEventByIdResponse deleteEventById(String id) throws Exception {
        return eventService
                .deleteEvent(user().getUsername(), id)
                .map(deletedId -> DeleteEventByIdResponse.withOK())
                .orElse(DeleteEventByIdResponse.withNotFound());
    }
}
