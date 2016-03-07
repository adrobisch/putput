package org.putput.calendar;

import net.fortuna.ical4j.model.Calendar;
import net.fortuna.ical4j.model.DateTime;
import net.fortuna.ical4j.model.Dur;
import net.fortuna.ical4j.model.TimeZone;
import net.fortuna.ical4j.model.TimeZoneRegistry;
import net.fortuna.ical4j.model.TimeZoneRegistryFactory;
import net.fortuna.ical4j.model.component.VAlarm;
import net.fortuna.ical4j.model.component.VEvent;
import net.fortuna.ical4j.model.property.Action;
import net.fortuna.ical4j.model.property.CalScale;
import net.fortuna.ical4j.model.property.Description;
import net.fortuna.ical4j.model.property.Duration;
import net.fortuna.ical4j.model.property.Location;
import net.fortuna.ical4j.model.property.ProdId;
import net.fortuna.ical4j.model.property.Repeat;
import net.fortuna.ical4j.model.property.Uid;
import net.fortuna.ical4j.model.property.Version;
import org.putput.api.model.Event;
import org.putput.api.model.EventList;
import org.putput.api.model.EventListLinks;
import org.putput.api.resource.Events;
import org.putput.common.web.BaseResource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.Optional.ofNullable;
import static org.putput.calendar.EventResource.eventEntityToDto;

@Component
public class EventsResource extends BaseResource implements Events {

    public static final int PAGE_SIZE = 100;
    
    @Autowired
    EventService eventService;
    
    @Override
    public GetEventsResponse getEvents(String format, BigDecimal page) throws Exception {
        Page<EventEntity> eventPage = eventService.getEvents(
            user(),
            new PageRequest(ofNullable(page).map(BigDecimal::intValue).orElse(0), PAGE_SIZE)
        );
        
        if (format == null || format.equalsIgnoreCase("json")) {
            return halJsonResponse(eventPage);    
        } else if (format.equalsIgnoreCase("ical")) {
            return icalResponse(eventPage);            
        } else {
            return GetEventsResponse.withBadRequest();    
        }
    }

    private GetEventsResponse icalResponse(Page<EventEntity> eventPage) {
        Calendar calendar = new Calendar();
        calendar.getProperties().add(new ProdId("-//PutPut//iCal4j 1.0//EN"));
        calendar.getProperties().add(Version.VERSION_2_0);
        calendar.getProperties().add(CalScale.GREGORIAN);
        
        eventPage
                .getContent()
                .stream()
                .forEach(event -> {
                    TimeZoneRegistry registry = TimeZoneRegistryFactory.getInstance().createRegistry();
                    net.fortuna.ical4j.model.TimeZone timezone = registry.getTimeZone(event.getTimezone());
                    
                    VEvent vEvent = createVEvent(event, timezone);
                    
                    vEvent.getProperties().add(new Uid(event.getId()));
                    vEvent.getProperties().add(new Description(event.getDescription()));
                    vEvent.getProperties().add(new Location(event.getLocation()));
                    vEvent.getProperties().add(timezone.getVTimeZone().getTimeZoneId());
                    
                    calendar.getComponents().add(vEvent);
                });
        
        return GetEventsResponse.withCalendarOK(calendar.toString());
    }

    private void addDefaultReminders(VEvent vEvent, EventEntity event) {
        VAlarm oneWeekBefore = new VAlarm(new Dur(-7, 0, 0, 0));

        VAlarm oneDayBefore = new VAlarm(new Dur(-1, 0, 0, 0));

        VAlarm oneHourBefore = new VAlarm(new Dur(0, -1, 0, 0));
        oneHourBefore.getProperties().add(new Repeat(4));
        oneHourBefore.getProperties().add(new Duration(new Dur(0, 0, 15, 0)));

        addDisplayAction(event, oneWeekBefore);
        addDisplayAction(event, oneDayBefore);
        addDisplayAction(event, oneHourBefore);
        
        vEvent.getAlarms().add(oneWeekBefore);
        vEvent.getAlarms().add(oneDayBefore);
        vEvent.getAlarms().add(oneHourBefore);
    }

    private void addDisplayAction(EventEntity event, VAlarm reminder) {
        reminder.getProperties().add(Action.DISPLAY);
        reminder.getProperties().add(new Description(event.getDescription()));
    }

    private VEvent createVEvent(EventEntity event, TimeZone tz) {
        if (event.getEnd() == null) {
            return new VEvent(dateTime(event.getStart(), tz), event.getTitle());
        }
        else {
            return new VEvent(dateTime(event.getStart(), tz), dateTime(event.getEnd(), tz), event.getTitle());
        }
    }

    DateTime dateTime(Long time, TimeZone tz) {
        DateTime dateTime = new DateTime(time);
        dateTime.setTimeZone(tz);
        return dateTime;
    }

    private GetEventsResponse halJsonResponse(Page<EventEntity> eventPage) {
        EventListLinks links = new EventListLinks();

        List<Event> events = eventPage
                .getContent()
                .stream()
                .map(eventEntityToDto(user()))
                .collect(Collectors.toList());

        EventList eventList =  new EventList()
                .withCurrentPage(eventPage.getNumber())
                .withCount(new Long(eventPage.getTotalElements()).intValue())
                .withPageSize(PAGE_SIZE)
                .withEvents(events)
                .withLinks(links);

        return GetEventsResponse.withHaljsonOK(eventList);
    }
}
