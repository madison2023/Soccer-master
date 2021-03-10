package cs301.Soccer;

import android.os.Build;
import android.support.annotation.RequiresApi;
import android.util.Log;
import cs301.Soccer.soccerPlayer.SoccerPlayer;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.*;

/**
 * Soccer player database -- presently, all dummied up
 *
 * @author *** put your name here ***
 * @version *** put date of completion here ***
 *
 */
public class SoccerDatabase implements SoccerDB {

    // dummied up variable; you will need to change this
    private Hashtable database = new Hashtable();



    /**
     * add a player
     *
     * @see SoccerDB#addPlayer(String, String, int, String)
     */
    @Override
    public boolean addPlayer(String firstName, String lastName,
                             int uniformNumber, String teamName) {

        //database = new Hashtable();
        String playerName = firstName+"##"+lastName;
        if (database.get(playerName) != null) {
            return false;
        }
        else {
            SoccerPlayer myPlayer = new SoccerPlayer(firstName,lastName,uniformNumber,teamName);
            database.put(playerName,myPlayer);
            return true;
        }
    }

    /**
     * remove a player
     *
     * @see SoccerDB#removePlayer(String, String)
     */
    @Override
    public boolean removePlayer(String firstName, String lastName) {
        String playerName = firstName + "##" + lastName;
        if (database.containsKey(playerName)) {
            database.remove(playerName);
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * look up a player
     *
     * @see SoccerDB#getPlayer(String, String)
     */
    @Override
    public SoccerPlayer getPlayer(String firstName, String lastName) {
        String playerName = firstName+"##"+lastName;
        SoccerPlayer player = (SoccerPlayer)database.get(playerName);
        if (player == null) {
            return null;
        }
        else {
            return player;
        }
    }

    /**
     * increment a player's goals
     *
     * @see SoccerDB#bumpGoals(String, String)
     */
    @Override
    public boolean bumpGoals(String firstName, String lastName) {
        String playerName = firstName+"##"+lastName;
        if(database.containsKey(playerName)) {
            SoccerPlayer mySoccerPlayer = (SoccerPlayer)database.get(playerName);
            mySoccerPlayer.bumpGoals();
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * increment a player's yellow cards
     *
     * @see SoccerDB#bumpYellowCards(String, String)
     */
    @Override
    public boolean bumpYellowCards(String firstName, String lastName) {
        String playerName = firstName+"##"+lastName;
        if(database.containsKey(playerName)) {
            SoccerPlayer mySoccerPlayer = (SoccerPlayer)database.get(playerName);
            mySoccerPlayer.bumpYellowCards();
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * increment a player's red cards
     *
     * @see SoccerDB#bumpRedCards(String, String)
     */
    @Override
    public boolean bumpRedCards(String firstName, String lastName) {
        String playerName = firstName+"##"+lastName;
        if(database.containsKey(playerName)) {
            SoccerPlayer mySoccerPlayer = (SoccerPlayer)database.get(playerName);
            mySoccerPlayer.bumpRedCards();
            return true;
        }
        else {
            return false;
        }
    }

    /**
     * tells the number of players on a given team
     *
     * @see SoccerDB#numPlayers(String)
     */
    @Override
    // report number of players on a given team (or all players, if null)
    public int numPlayers(String teamName) {
        if(teamName == null) {
            return database.size();
        }
        else {
           Set<String> setOfPlayerNames = database.keySet();
           int numPlayers = 0;
           for(String key : setOfPlayerNames){
               SoccerPlayer myPlayer = (SoccerPlayer)database.get(key);
               if((myPlayer.getTeamName()).equals(teamName)) {
                   numPlayers++;
               }
           }
           return numPlayers;
        }

    }

    /**
     * gives the nth player on a the given team
     *
     * @see SoccerDB#playerIndex(int, String)
     */
    // get the nTH player
    @Override
    public SoccerPlayer playerIndex(int idx, String teamName) {
        if(idx > database.size()) {
            return null;
        }
        else {
            Set<String> setOfPlayerNames = database.keySet();
            int n = 0;
            for (String key : setOfPlayerNames) {
                SoccerPlayer myPlayer = (SoccerPlayer) database.get(key);
                if (teamName == null) {
                    if (idx == n) {
                        return myPlayer;
                    }
                    n++;
                }
                else if ((myPlayer.getTeamName()).equals(teamName)) {
                    if (idx == n) {
                        return myPlayer;
                    }
                    n++;
                }

            }
            return null;
        }
    }

    /**
     * reads database data from a file
     *
     * @see SoccerDB#readData(java.io.File)
     */
    // read data from file
    @RequiresApi(api = Build.VERSION_CODES.N)
    @Override
    public boolean readData(File file) {
        try {
            Scanner sc = new Scanner(file);
            while(sc.hasNextLine()) {
                String firstName = sc.nextLine();
                String lastName = sc.nextLine();
                String teamName = sc.nextLine();
                String uniform = sc.nextLine();
                String goals = sc.nextLine();
                String yellowCards = sc.nextLine();
                String redCards = sc.nextLine();

                int uniformNum = Integer.parseInt(uniform);
                int goalsNum = Integer.parseInt(goals);
                int yellowNum = Integer.parseInt(yellowCards);
                int redNum = Integer.parseInt(redCards);


                SoccerPlayer myPlayer = new SoccerPlayer(firstName,lastName,uniformNum,teamName);
                String playerName = firstName+"##"+lastName;
                if (database.containsKey(playerName)) {
                    database.replace(playerName,myPlayer);
                }
                else {
                    database.put(playerName, myPlayer);
                }

                for (int i = 0; i < goalsNum; i++) {
                    myPlayer.bumpGoals();
                }

                for (int i = 0; i < yellowNum; i++) {
                    myPlayer.bumpYellowCards();
                }

                for (int i = 0; i < redNum; i++) {
                    myPlayer.bumpRedCards();
                }

            }
            return true;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }

        //return file.exists();
    }

    /**
     * write database data to a file
     *
     * @see SoccerDB#writeData(java.io.File)
     */
    // write data to file
    @Override
    public boolean writeData(File file) {
        try {
            PrintWriter writer = new PrintWriter(file);
            Set<String> setOfPlayerNames = database.keySet();
            for(String key : setOfPlayerNames){
                SoccerPlayer myPlayer = (SoccerPlayer)database.get(key);
                writer.println(logString(myPlayer.getFirstName()));
                writer.println(logString(myPlayer.getLastName()));
                writer.println(logString(myPlayer.getTeamName()));
                writer.println(logString("" + myPlayer.getUniform()));
                writer.println(logString(""+ myPlayer.getGoals()));
                writer.println(logString("" + myPlayer.getYellowCards()));
                writer.println(logString("" + myPlayer.getRedCards()));


            }
            writer.close();
            return true;

        } catch (FileNotFoundException e) {
            e.printStackTrace();
            return false;
        }


    }

    /**
     * helper method that logcat-logs a string, and then returns the string.
     * @param s the string to log
     * @return the string s, unchanged
     */
    private String logString(String s) {
//        Log.i("write string", s);
        return s;
    }

    /**
     * returns the list of team names in the database
     *
     * @see cs301.Soccer.SoccerDB#getTeams()
     */
    // return list of teams
    @Override
    public HashSet<String> getTeams() {
        HashSet<String> myHash = new HashSet<>();
        Set<String> setOfPlayerNames = database.keySet();
        for(String key : setOfPlayerNames){
            SoccerPlayer myPlayer = (SoccerPlayer)database.get(key);
            if(myHash.contains(myPlayer.getTeamName()) == false) {
                myHash.add(myPlayer.getTeamName());
            }
        }
        //return new HashSet<String>();
        return myHash;
    }

    /**
     * Helper method to empty the database and the list of teams in the spinner;
     * this is faster than restarting the app
     */
    public boolean clear() {
        if(database != null) {
            database.clear();
            return true;
        }
        return false;
    }
}
