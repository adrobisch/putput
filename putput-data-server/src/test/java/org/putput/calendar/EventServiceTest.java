package org.putput.calendar;

import org.junit.Test;
import org.putput.SpringTest;
import org.putput.api.model.Event;
import org.springframework.beans.factory.annotation.Autowired;

import static org.assertj.core.api.Assertions.assertThat;

public class EventServiceTest extends SpringTest {
    
    @Autowired
    EventService eventService;
    
    @Test
    public void createsAllDayEvent() {
        Event newEvent = new Event()
                .withTitle("Title")
                .withDescription("Description")
                .withLocation("Location")
                .withStart(1457564400000.0)
                .withEnd(1457737200000.0)
                .withType("ALLDAY");

        EventEntity eventEntity = eventService.createEvent("user", newEvent);
        System.out.println(eventEntity);
        
        assertThat(eventEntity.getStart()).isEqualTo(1457564400000L);
        assertThat(eventEntity.getEnd()).isEqualTo(1457823600000L); // corrected to next day at 00:00
        assertThat(eventEntity.getType()).isEqualTo(EventEntity.Type.ALLDAY);
        assertThat(eventEntity.getTitle()).isEqualTo("Title");
        assertThat(eventEntity.getDescription()).isEqualTo("Description");
        assertThat(eventEntity.getLocation()).isEqualTo("Location");
    }
}