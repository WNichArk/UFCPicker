package co.willnicholson.DTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FightDTO {
    private String date;
    private String fighter1name;
    private String fighter1url;
    private int fighter1id;
    private String fighter2name;
    private String fighter2url;
    private int fighter2id;
    private int winRound;
    private String winMethod;
    private String winTime;
    private String event;
    private String eventUrl;
    private String result;



}
