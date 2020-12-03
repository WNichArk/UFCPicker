package co.willnicholson.models;

import co.willnicholson.DTOs.FightDTO;
import co.willnicholson.configuration.Config;
import com.ftpix.sherdogparser.models.Fight;
import com.ftpix.sherdogparser.models.Fighter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import java.sql.*;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


@Slf4j
@Component
public class ServerDB implements Config {

    String url;
    String username;
    String password;

    @Autowired
    public ServerDB(){
        this.url = Config.url;
        this.username = Config.username;
        this.password = Config.password;
    }

    //Adds a single fight to database
    public void putFight(Fight fight) {

        try {
            String fighter1name = fight.getFighter1().getName().replace("'", "\\'");
            String fighter2name = fight.getFighter2().getName().replace("'", "\\'");
            String eventName = fight.getEvent().getName().replace("'", "\\'");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into fights (date, fighter1name, fighter1url,  fighter2name, fighter2url, winround, winmethod, wintime, result, event, eventurl) values ('" +
                    fight.getDate().toString() + "','" +
                    fighter1name + "','" +
                    fight.getFighter1().getSherdogUrl() + "','" +
                    fighter2name + "','" +
                    fight.getFighter2().getSherdogUrl() + "','" +
                    fight.getWinRound() + "','"+
                    fight.getWinMethod() + "','" +
                    fight.getWinTime() + "','" +
                    fight.getResult().toString() + "','" +
                    eventName + "','" +
                    fight.getEvent().getSherdogUrl()+ "');");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.info(e.getMessage());
        }
    }



    public List<FightDTO> getAllFights(){
        List<FightDTO> allFights = new ArrayList<>();
        try{
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            ResultSet results = statement.executeQuery("select * from fights;");
            while(results.next()){
                FightDTO fight = new FightDTO();
                fight.setDate(results.getString("id"));
                fight.setFighter1name(results.getString("fighter1name"));
                fight.setFighter1url(results.getString("fighter1url"));
                fight.setFighter2name(results.getString("fighter2name"));
                fight.setFighter2url(results.getString("fighter2url"));
                fight.setWinMethod(results.getString("winmethod"));
                fight.setWinRound(results.getInt("winround"));
                fight.setWinTime(results.getString("wintime"));
                fight.setEvent(results.getString("event"));
                fight.setEventUrl(results.getString("eventurl"));
                fight.setResult(results.getString("result"));

                allFights.add(fight);

            }


        }catch(SQLException e){
            log.info(e.getMessage());
        }
        return allFights;

    }
    //Note adds fighter
    public void putFighter(Fighter fighter){
        System.out.println(
                fighter.getName() +
                        fighter.getHeight() + " " +
                        fighter.getWeight() +" " +
                        fighter.getPicture() +" " +
                        fighter.getSherdogUrl() +" " +
                        fighter.getFights().size() +" " +
                        fighter.getBirthday() +" " + //FIXME convert to MySQL date
                        fighter.getDraws() +" " +
                        fighter.getNc() +" " +
                        fighter.getLosses() +" " +
                        fighter.getLossesSub() +" " +
                        fighter.getLossesDec() +" " +
                        fighter.getLossesKo() +" " +
                        fighter.getLossesOther() +" " +
                        fighter.getWins() +" " +
                        fighter.getWinsKo() +" " +
                        fighter.getWinsDec() +" " +
                        fighter.getWinsSub() +" " +
                        fighter.getWinsOther() );
        try {
            //Formatting for MySQL
            String datePattern = "yyyy-MM-dd";
            SimpleDateFormat formattedDate = new SimpleDateFormat(datePattern);
            String birthdate = "0000-00-00";
            if(fighter.getBirthday() != null) {
                 birthdate = formattedDate.format(fighter.getBirthday());
            }
            String height = fighter.getHeight().replace("'", "\\'").replace("\"", "\\\"");
            

            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into fighters (name, height, weight, picture, url, totalfights, birthdate, draws, nc, losses, lossessub, lossesdec, lossesko, lossesother, wins, winsko, winsdec, winssub, winsother) values ('" +
            fighter.getName() + "','" +
                    height + "','" +
                    fighter.getWeight() + "','" +
                    fighter.getPicture() + "','" +
                    fighter.getSherdogUrl() + "','" +
                    fighter.getFights().size() + "','" +
                    birthdate + "','" +
                    fighter.getDraws() + "','" +
                    fighter.getNc() + "','" +
                    fighter. getLosses() + "','" +
                    fighter.getLossesSub() + "','" +
                    fighter.getLossesDec() + "','" +
                    fighter.getLossesKo() + "','" +
                    fighter.getLossesOther() + "','" +
                    fighter.getWins() + "','" +
                    fighter.getWinsKo() + "','" +
                    fighter.getWinsDec() + "','" +
                    fighter.getWinsSub() + "','" +
                    fighter.getWinsOther() + "');");
        }catch(SQLException e){
            System.out.println(e.getMessage());
            log.info(e.getMessage());
        }

    }

    public void putUpcomingFight(Fight fight) {

        try {
            String fighter1name = fight.getFighter1().getName().replace("'", "\\'");
            String fighter2name = fight.getFighter2().getName().replace("'", "\\'");
            String eventName = fight.getEvent().getName().replace("'", "\\'");
            Connection connection = DriverManager.getConnection(url, username, password);
            Statement statement = connection.createStatement();
            statement.executeUpdate("insert into upcomingfights (date, fighter1name, fighter1url,  fighter2name, fighter2url, event, eventurl) values ('" +
                    fight.getDate().toString() + "','" +
                    fighter1name + "','" +
                    fight.getFighter1().getSherdogUrl() + "','" +
                    fighter2name + "','" +
                    fight.getFighter2().getSherdogUrl() + "','" +
                    eventName + "','" +
                    fight.getEvent().getSherdogUrl()+ "');");
        } catch (SQLException e) {
            System.out.println(e.getMessage());
            log.info(e.getMessage());
        }
    }
}
