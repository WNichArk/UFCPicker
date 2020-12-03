package co.willnicholson.services;

import com.ftpix.sherdogparser.models.Fighter;
import java.util.*;

public class FighterAnalyze {


    public ArrayList<Character> fighterWinStyle(Fighter f){

        HashMap<Character, Integer>winMap = new HashMap<>();

        ArrayList<Character> winStyle = new ArrayList<>() ;
        winMap.put('K', f.getWinsKo());
        winMap.put('S', f.getWinsSub());
        winMap.put('D', f.getWinsDec());
        winMap.put('O', f.getWinsOther());
        int maxValue = Collections.max(winMap.values());

        for(Object o: winMap.keySet()){
            if(winMap.get(o).equals(maxValue)){
                winStyle.add((char)o);
            }
        }
        return winStyle;

    }
    public double getWinRating(Fighter f){
        double wins = f.getWins();
        double losses = f.getLosses();
        double draws = f.getDraws();
        double nc = f.getNc();
        return wins/(wins+losses+draws+nc);

    }
    public ArrayList<Character> fighterLossStyle(Fighter f){

        HashMap<Character, Integer>lossMap = new HashMap<>();

        ArrayList<Character> lossStyle = new ArrayList<>() ;
        lossMap.put('K', f.getLossesKo());
        lossMap.put('S', f.getLossesSub());
        lossMap.put('D', f.getLossesDec());
        lossMap.put('O', f.getLossesOther());
        int maxValue = Collections.max(lossMap.values());

        for(Object o: lossMap.keySet()){
            if(lossMap.get(o).equals(maxValue)){
                lossStyle.add((char)o);
            }
        }

        return lossStyle;

    }

    public double getFighterShape(Fighter f){
        int heightAnalog = Integer.parseInt(f.getHeight().replace("'", "").replace("\"", ""));
        //convert to inches;
        String weight = f.getWeight();



        long timedate = System.currentTimeMillis();
        Date today = new Date(timedate);
        Date birthday = f.getBirthday();

        double shape = 0.0;

        return shape;
    }

}
