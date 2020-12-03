package co.willnicholson.services;

import co.willnicholson.DTOs.FightDTO;
import co.willnicholson.models.ServerDB;
import com.ftpix.sherdogparser.Sherdog;
import com.ftpix.sherdogparser.exceptions.SherdogParserException;
import com.ftpix.sherdogparser.models.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.text.ParseException;
import java.util.List;

@Slf4j
@Component
public class PlacerService {

    @Autowired
    public PlacerService() {
    }
    //Pull all fights by date from Sherdog.com, and add fights/fighters to DB
    public static void batchPlaceFightHistory(int month, int day, int year) throws ParseException, IOException, SherdogParserException {
        Sherdog parser = new Sherdog.Builder().build();
        EventFightRetriever eventFightRetriever = new EventFightRetriever();
        List<Fight> fights = eventFightRetriever.grabFightsAfterDate(month, day, year);
        ServerDB localDatabase = new ServerDB();
        for (Fight fight : fights) {
            localDatabase.putFight(fight);
            Fighter fighter1 = parser.getFighter(fight.getFighter1().getSherdogUrl());
            Fighter fighter2 = parser.getFighter(fight.getFighter2().getSherdogUrl());
            localDatabase.putFighter(fighter1);
            localDatabase.putFighter(fighter2);

        }
    }

    public static void placeUpcomingFights() throws ParseException, IOException, SherdogParserException {
        Sherdog parser = new Sherdog.Builder().build();
        EventFightRetriever eventFightRetriever = new EventFightRetriever();
        List<Fight> fights = eventFightRetriever.grabUpcomingFights();
        ServerDB serverDB = new ServerDB();
        for (Fight fight : fights) {
            serverDB.putUpcomingFight(fight);
        }
    }

    public static void placeRecentFights(int month, int day, int year) throws ParseException, IOException, SherdogParserException {
        Sherdog parser = new Sherdog.Builder().build();
        EventFightRetriever eventFightRetriever = new EventFightRetriever();
        List<Fight> fights = eventFightRetriever.grabFightsAfterDate(month, day, year);
        ServerDB serverDB = new ServerDB();
        for (Fight fight : fights) {
            serverDB.putUpcomingFight(fight);
        }
    }

    //FIXME don't leave this static... or at all.
    public static void printAllFights(){
        ServerDB localDatabase = new ServerDB();
        List<FightDTO> allFights = localDatabase.getAllFights();
        double roundOne = 0;
        double collSize = allFights.size();
        for(FightDTO f: allFights){
            if(f.getWinRound() == 1){
                roundOne++;
            }
        }
        double average = Math.round(roundOne/collSize*100);
        System.out.println(roundOne);
        System.out.println(collSize);
        System.out.println("Round one finishes account for " + average + "% of fights.");
    }

    public static void testWinStyle() throws ParseException, IOException, SherdogParserException {
        Sherdog parser = new Sherdog.Builder().build();
        //Organization ufc = parser.getOrganization(Organizations.UFC);
        Fighter fighter = parser.getFighter("https://www.sherdog.com/fighter/Michael-Bisping-10196");
        FighterAnalyze analyze = new FighterAnalyze();
        System.out.println(fighter.getName());
        System.out.println("Win rating: " + analyze.getWinRating(fighter));
        System.out.println("Win style: " + analyze.fighterWinStyle(fighter).toString());
        System.out.println("Loss style: " +analyze.fighterLossStyle(fighter).toString());

    }
    public static void putFighter() throws ParseException, IOException, SherdogParserException {
        Sherdog parser = new Sherdog.Builder().build();
        Fighter test = parser.getFighter("https://www.sherdog.com/fighter/Ottman-Azaitar-173073");
        ServerDB localDatabase = new ServerDB();
        localDatabase.putFighter(test);
    }
}
