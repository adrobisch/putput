package org.putput.calendar;

import org.putput.api.model.Event;
import org.putput.common.UuidService;
import org.putput.users.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Calendar;
import java.util.Optional;

import static java.util.Optional.ofNullable;

@Service
@Transactional
public class EventService {
    
    @Autowired
    EventRepository eventRepository;
    
    @Autowired
    UserRepository userRepository;
    
    @Autowired
    UuidService uuidService;
    
    public EventEntity createEvent(String username, Event newEvent) {
        EventEntity newEventEntity = new EventEntity()
                .setId(uuidService.uuid())
                .setOwner(userRepository.findByUsername(username))
                .setTitle(newEvent.getTitle())
                .setDescription(newEvent.getDescription())
                .setType(EventEntity.Type.valueOf(newEvent.getType()))
                .setRecurrence(newEvent.getRecurrence())
                .setTimezone(Optional
                        .ofNullable(newEvent.getTimezone())
                        .orElse(defaultTimeZone()))
                .setStart(newEvent.getStart().longValue())
                .setEnd(newEvent.getEnd().longValue());
        
        return eventRepository.save(newEventEntity);
    }

    private String defaultTimeZone() {
        return "Europe/Berlin";
    }

    public Optional<EventEntity> getEvent(String username, String id) {
        return ofNullable(eventRepository.findOne(id))
                .filter(eventEntity -> eventEntity.getOwner().getUsername().equals(username));           
    }
    
    public Page<EventEntity> getEvents(String username, Pageable pageable) {
        return eventRepository.findByOwner(username, pageable);
    }

    public Optional<EventEntity> updateEvent(String username, Event updatedEvent) {
        return ofNullable(eventRepository.findByOwnerAndId(username, updatedEvent.getId()))
                .map(eventEntity -> 
                        eventRepository.save(eventEntity
                                .setTitle(updatedEvent.getTitle())
                                .setDescription(updatedEvent.getDescription())
                                .setStart(updatedEvent.getStart().longValue())
                                .setEnd(updatedEvent.getEnd().longValue())
                                .setType(EventEntity.Type.valueOf(updatedEvent.getType()))
                                .setTimezone(updatedEvent.getTimezone())
                                .setRecurrenceEnd(updatedEvent.getRecurrenceEnd().longValue())
                                .setRecurrence(updatedEvent.getRecurrence()))
                );     
    }

    public Optional<String> deleteEvent(String username, String id) {
        return getEvent(username, id).map(eventEntity -> {
            eventRepository.delete(id);
            return id;
        });
    }
}
