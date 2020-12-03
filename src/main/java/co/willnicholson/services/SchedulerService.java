package co.willnicholson.services;

import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;

import static java.time.LocalDate.now;


//TODO (zone = "GMT-5:00") - Central Time

@Slf4j
@Service
public class SchedulerService {

    private PlacerService placerService;
    EventFightRetriever eventFightRetriever;
    private ZoneId zoneId = ZoneId.of("America/Chicago");


    public SchedulerService(PlacerService p, EventFightRetriever e) throws Exception{
        this.eventFightRetriever = e;
        this.placerService = p;
    }

    @Scheduled(zone = "GMT-5:00", cron = "0 0 15 ? * 5")
    public void scheduledUpcomingFightDownload() throws Exception{
        placerService.placeUpcomingFights();
//    placerService.batchGetFightHistory();
    }

    @Scheduled(zone = "GMT-5:00", cron = "0 0 15 ? * 1")
    public void placeRecentFights() throws Exception{
        LocalDate minusSixDays = LocalDate.now().minusDays(6);
        int month = minusSixDays.getMonthValue();
        int day = minusSixDays.getDayOfMonth();
        int year = minusSixDays.getYear();
        placerService.placeRecentFights(month, day, year);
    }

    @Scheduled(cron ="0/20 * * * * * ")
    public void logScheduleTest(){
        log.info("another minute");
        LocalDate minusSixDays = LocalDate.now().minusDays(6);
        int month = minusSixDays.getMonthValue();
        int day = minusSixDays.getDayOfMonth();
        int year = minusSixDays.getYear();
        log.info("Month reads: "+ month);
        log.info("Day reads: " + day);
    }


}
