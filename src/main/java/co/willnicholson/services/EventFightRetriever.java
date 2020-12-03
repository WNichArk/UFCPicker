package co.willnicholson.services;

import com.ftpix.sherdogparser.Sherdog;
import com.ftpix.sherdogparser.exceptions.SherdogParserException;
import com.ftpix.sherdogparser.models.Event;
import com.ftpix.sherdogparser.models.Fight;
import com.ftpix.sherdogparser.models.Organization;
import com.ftpix.sherdogparser.models.Organizations;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.text.ParseException;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
public class EventFightRetriever {

    private final ZoneId ZONE_ID = ZoneId.of("America/Chicago");


    public List<Event> grabEventsAfterDate(int month, int day, int year) throws ParseException, IOException, SherdogParserException {
        Sherdog parser = new Sherdog.Builder().build();
        log.info("Begin grab fights");
        Organization ufc = parser.getOrganization(Organizations.UFC);
        log.info("Here");
        ZonedDateTime date = ZonedDateTime.of(year, month, day, 12, 00, 00, 000, ZONE_ID);
        List<Event> events = ufc.getEvents();
        List<Event> eventsAfter = new ArrayList<>();
        log.info("Fights grabbed");
        for(Event e: events) {
            if (e.getDate().isAfter(date) && e.getDate().isBefore(ZonedDateTime.now().minusDays(1))) {
                    eventsAfter.add(e);
                    System.out.println("added event");
                }
            }

        return eventsAfter;

    }

    public List<Fight> grabFightsAfterDate(int month, int day, int year) throws ParseException, IOException, SherdogParserException {
        Sherdog parser = new Sherdog.Builder().build();
        List<Event> events = this.grabEventsAfterDate(month, day, year); //grabs list of events
        List<Fight> eventFights = new ArrayList<>();
        log.info(events.size() + " events found.");

    for (int i = 0; i < events.size(); i++) {
        try {
        if (events.get(i).getFights() != null) {
            Event useEvent = events.get(i);
            if (parser.getEvent(useEvent.getSherdogUrl()).getFights() != null) {
                log.info(parser.getEvent(useEvent.getSherdogUrl()).getFights().size() + " fights in event " + i);
                eventFights.addAll(parser.getEvent(useEvent.getSherdogUrl()).getFights());
                eventFights.addAll(useEvent.getFights());
                log.info(eventFights.size() + " fights added");
            } else {
                log.warn("Null fightblob detected");
            }
        } else {
            log.info("Null Event Detected after " + events.get(i - 1).getDate().toString());
        }

    }catch(IndexOutOfBoundsException e){
        log.info("Event has no fights");
    }
}
        return eventFights;
    }

    public List<Event> grabUpcomingEvents() throws ParseException, IOException, SherdogParserException {
        //Setup
        Sherdog parser = new Sherdog.Builder().build();
        ZonedDateTime today = ZonedDateTime.now();
        ZonedDateTime advance = ZonedDateTime.now().plusDays(6);
        //Grab all events (unfortunate part of Sherdog Parser)
        log.info("Begin grab fights");
        Organization ufc = parser.getOrganization(Organizations.UFC);
        List<Event> events = ufc.getEvents();

        List<Event> eventsAfterDate = new ArrayList<>();
        log.info("Fights grabbed");
        for(Event e: events) {
            if (e.getDate().isAfter(today) && e.getDate().isBefore(advance)) {
                eventsAfterDate.add(e);
                System.out.println("added event");
            }
        }
        return eventsAfterDate;
    }



    public List<Fight> grabUpcomingFights() throws ParseException, IOException, SherdogParserException {
        Sherdog parser = new Sherdog.Builder().build();
        List<Event> events = this.grabUpcomingEvents(); //grabs list of events
        List<Fight> eventFights = new ArrayList<>();
        log.info(events.size() + " events found.");

        for (int i = 0; i < events.size(); i++) {
            try {
                if (events.get(i).getFights() != null) {
                    Event useEvent = events.get(i);
                    if (parser.getEvent(useEvent.getSherdogUrl()).getFights() != null) {
                        log.info(parser.getEvent(useEvent.getSherdogUrl()).getFights().size() + " fights in event " + i);
                        eventFights.addAll(parser.getEvent(useEvent.getSherdogUrl()).getFights());
                        eventFights.addAll(useEvent.getFights());
                        log.info(eventFights.size() + " fights added");

                    } else {
                        log.warn("Null fightblob detected");
                    }
                } else {
                    log.info("Null Event Detected after " + events.get(i - 1).getDate().toString());
                }

            }catch(IndexOutOfBoundsException e){
                log.info("Event has no fights");
            }
        }
        return eventFights;
    }


}