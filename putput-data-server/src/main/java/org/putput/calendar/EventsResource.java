package org.putput.calendar;

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
    public GetEventsResponse getEvents(BigDecimal page) throws Exception {
        Page<EventEntity> eventPage = eventService.getEvents(
            user().getUsername(), 
            new PageRequest(ofNullable(page).map(BigDecimal::intValue).orElse(0), PAGE_SIZE)
        );
        
        EventListLinks links = new EventListLinks();

        List<Event> events = eventPage
                .getContent()
                .stream()
                .map(eventEntityToDto(user().getUsername()))
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
