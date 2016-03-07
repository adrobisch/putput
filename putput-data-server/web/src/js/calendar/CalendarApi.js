function CalendarApi(http, api) {
    this.getEvents = function () {
        return api.withLink("events", function (eventsLink) {
            return http.get(eventsLink);
        });
    };

    this.nextPage = function (eventsList) {
        if (!eventsList._links.nextPage) {
            return;
        }
        return http.get(eventsList._links.nextPage.href);
    };

    this.previousPage = function (eventsList) {
        if (!eventsList._links.previousPage) {
            return;
        }
        return http.get(eventsList._links.previousPage.href);
    };

    this.getEvent = function(id) {
        return api.withLink('event', function (eventLink) {
            return http.get(eventLink.replace("{id}", id));
        });
    };
    
    this.createEvent = function(event) {
        return api.withLink('event', function (eventLink) {
            return http.post(eventLink.replace("{id}", ""), event); 
        });  
    };

    this.deleteEvent = function (event) {
        return api.withLink('event', function (eventLink) {
            return http.delete(eventLink.replace("{id}", event.id));
        });
    }
}

CalendarApi.$inject = ['$http', 'api'];

module.exports = CalendarApi;