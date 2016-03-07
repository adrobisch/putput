
package org.putput.api.resource;

import java.math.BigDecimal;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import org.putput.api.model.EventList;
import org.putput.api.support.ResponseWrapper;

@Path("events")
public interface Events {


    /**
     * 
     * @param format
     *     the formatting of the events, implicitly set to 'json', also possible: 'ical'
     * @param page
     *     The page to retrieve
     */
    @GET
    @Produces({
        "application/hal+json",
        "text/calendar"
    })
    Events.GetEventsResponse getEvents(
        @QueryParam("format")
        String format,
        @QueryParam("page")
        BigDecimal page)
        throws Exception
    ;

    public class GetEventsResponse
        extends ResponseWrapper
    {


        private GetEventsResponse(Response delegate) {
            super(delegate);
        }

        /**
         * 
         */
        public static Events.GetEventsResponse withBadRequest() {
            Response.ResponseBuilder responseBuilder = Response.status(400);
            return new Events.GetEventsResponse(responseBuilder.build());
        }

        /**
         * 
         * @param entity
         *     
         */
        public static Events.GetEventsResponse withCalendarOK(String entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "text/calendar");
            responseBuilder.entity(entity);
            return new Events.GetEventsResponse(responseBuilder.build());
        }

        /**
         * 
         * @param entity
         *     
         */
        public static Events.GetEventsResponse withHaljsonOK(EventList entity) {
            Response.ResponseBuilder responseBuilder = Response.status(200).header("Content-Type", "application/hal+json");
            responseBuilder.entity(entity);
            return new Events.GetEventsResponse(responseBuilder.build());
        }

    }

}
