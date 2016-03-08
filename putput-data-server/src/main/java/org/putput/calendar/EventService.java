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
import java.util.function.Function;

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
        EventEntity.Type type = EventEntity.Type.valueOf(newEvent.getType());
        EventEntity newEventEntity = new EventEntity()
                .setId(uuidService.uuid())
                .setOwner(userRepository.findByUsername(username))
                .setTitle(newEvent.getTitle())
                .setDescription(newEvent.getDescription())
                .setLocation(newEvent.getLocation())
                .setType(type)
                .setRecurrence(newEvent.getRecurrence())
                .setTimezone(Optional
                        .ofNullable(newEvent.getTimezone())
                        .orElse(defaultTimeZone()))
                .setStart(getStartDate(newEvent, type))
                .setEnd(getEndDate(newEvent, type));
        
        return eventRepository.save(newEventEntity);
    }

    private long getStartDate(Event newEvent, EventEntity.Type type) {
        if (type == EventEntity.Type.ALLDAY) {
            return zeroTime(newEvent.getStart().longValue()).getTimeInMillis();
        }
        return newEvent.getStart().longValue();
    }

    private Long getEndDate(Event newEvent, EventEntity.Type type) {
        if (type == EventEntity.Type.ALLDAY && newEvent.getEnd() == null) {
            return nextDay(zeroTime(newEvent.getStart().longValue()).getTimeInMillis()).getTimeInMillis();    
        } else if (type == EventEntity.Type.ALLDAY) {
            return nextDay(zeroTime(newEvent.getEnd().longValue()).getTimeInMillis()).getTimeInMillis();       
        } else {
            return newEvent.getEnd().longValue();    
        }
    }

    private Calendar nextDay(long l) {
        Calendar c = zeroTime(l);
        c.add(Calendar.DATE, 1);
        return c;
    }

    private Calendar zeroTime(long l) {
        Calendar c = Calendar.getInstance();
        c.setTimeInMillis(l);

        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.MINUTE, 0);
        c.set(Calendar.MILLISECOND, 0);
        
        return c;
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
                .map(updateEntity(updatedEvent));
    }

    public Function<EventEntity, EventEntity> updateEntity(Event updatedEvent) {
        return eventEntity -> {
            EventEntity.Type type = EventEntity.Type.valueOf(updatedEvent.getType());

            EventEntity updatedEntity = eventEntity
                .setTitle(updatedEvent.getTitle())
                .setDescription(updatedEvent.getDescription())
                .setStart(getStartDate(updatedEvent, type))
                .setLocation(updatedEvent.getLocation())
                .setEnd(getEndDate(updatedEvent, type))
                .setType(type)
                .setTimezone(updatedEvent.getTimezone());

            ofNullable(updatedEvent.getRecurrenceEnd()).ifPresent(recurrenceEnd -> {
                eventEntity.setRecurrenceEnd(updatedEvent.getRecurrenceEnd().longValue());
            });

            ofNullable(updatedEvent.getRecurrence()).ifPresent(recurrence -> {
                eventEntity.setRecurrence(updatedEvent.getRecurrence());
            });

            return eventRepository.save(updatedEntity);
        };
    }

    public Optional<String> deleteEvent(String username, String id) {
        return getEvent(username, id).map(eventEntity -> {
            eventRepository.delete(id);
            return id;
        });
    }
}
