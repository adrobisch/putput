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
    
    this.createEvent = function(event) {
        return api.withLink('event', function (eventLink) {
            return http.post(eventLink, event); 
        });  
    };
}

CalendarApi.$inject = ['$http', 'api'];

module.exports = CalendarApi;