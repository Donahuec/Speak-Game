package GameObject;

import java.util.Random;

/**
 * Holds statistics and variables related to gameplay
 */

public class GameStats {

    public final int MAX_ANXIETY = 200;
    public final int STARTING_ANXIETY = 50;
    public final int MAX_STRESS = 50;
    public final int STARTING_STRESS = 10;
    private final int HOURS = 0;
    private final int MINUTES = 1;
    private final int AM_PM = 2;
    private final int START_HOUR = 7;
    private final int START_MINUTE = 20;

    private boolean gameOver;
    private int anxiety;
    private int stress;
    private int hour;
    private int minutes;
    private boolean twelveHourClock;
    private Random rand;

    //specific game variables
    private boolean smallBreakfast;

    public GameStats() {
        anxiety = STARTING_ANXIETY;
        stress = STARTING_STRESS;
        hour = START_HOUR;
        minutes = START_MINUTE;
        twelveHourClock = true;
        smallBreakfast = true;
        gameOver = false;
        rand = new Random();
    }

    public int getAnxiety() {return anxiety;}
    public int getStress() {return stress;}

    /**
     * Gets the current time
     * @return an array
     */
    public int[] getTime() {
        int[] timeArray = new int[3];
        timeArray[MINUTES] = minutes;
        timeArray[HOURS] = hour;
        if (hour >= 12) {
            timeArray[2] = 1;
        } else if (hour > 24) {
            timeArray[AM_PM] = -1;
        } else {
            timeArray[AM_PM] = 0;
        }
        return timeArray;
    }

    /**
     * Updates the anxiety to a weighted random value from change,
     * with a minimum and maximum limit
     * @param change the average amount the anxiety should change
     * @param min the minimum amount the anxiety should change
     * @param max the maximum amount the anxiety should change
     */
    public void updateAnxiety(int change, int min, int max){
        // make sure min and max are viable
        if (change < 0) {
            assert max < change && min > change;
        } else if (change > 0) {
            assert max > change && min < change;
        }
        //the final amount to update the anxiety
        float update = change;
        update = update * getRandom();
        //weight the change based on the current stress and anxiety
        //the exact formula might change with testing
        if(change < 0) {
            update = (int)(update  * (2 - (stress / (MAX_STRESS / 2.0))));
            update = (int)(update * (2 - (anxiety / (MAX_ANXIETY / 2.0))));
        } else {
            update = (int)(update  * (stress / (MAX_STRESS / 2.0)));
            update = (int)(update * (anxiety / (MAX_ANXIETY / 2.0)));
        }
        //if outside the max or min, set to appropriate value
        if (update > max){
            update = max;
        } else if (update < min) {
            update = min;
        }
        //anxiety can't be less than 0
        anxiety += (int)update;
        if (anxiety < 0) {
            anxiety = 0;
        }
        //if anxiety is full end the game
        if (anxiety >= MAX_ANXIETY) {
            gameOver = true;
        }
    }

    /**
     * Updates the stress to a weighted random value from change,
     * with a minimum and maximum limit
     * @param change the average amount the stress should change
     * @param min the minimum amount the stress should change
     * @param max the maximum amount the stress should change
     */
    public void  updateStress(int change, int min, int max) {
        if (change < 0) {
            assert max < change && min > change;
        } else if (change > 0) {
            assert max > change && min < change;
        }
        //stress is not currently weighted (subject to change)
        int update = (int)(change * getRandom());
        if (update > max){
            update = max;
        } else if (update < min) {
            update = min;
        }
        stress += update;
        if (stress > MAX_STRESS) {
            stress = MAX_STRESS;
        } else if (stress < 0) {
            stress = 0;
        }
    }

    /**
     * Updates the current time given a number of hours and minutes to change
     * @param hours
     * @param mins
     */
    public void updateTime(int hours, int mins) {
        assert hours > 0 && hours < 24: "Invalid change in hours";
        assert mins > 0 && mins < 60: "Invalid change in minutes";

        hour += hours;
        minutes += mins;

        while (minutes >= 60) {
            hour++;
            minutes = minutes % 60;
        }
    }

    /**
     * Gets a string that represents the current time
     * @return a string that represents the current time
     */
    public String getTimeString() {
        int[] timeArray = getTime();
        String timeString = "";
        if (twelveHourClock && timeArray[AM_PM] != 0) {
            timeString =  String.format("%02d:%02d", timeArray[HOURS] - 12, timeArray[MINUTES]);
        } else {
            timeString =  String.format("%02d:%02d", timeArray[HOURS], timeArray[MINUTES]);
        }

        if (!twelveHourClock) {
            if (timeArray[AM_PM] == 0) {
                timeString += " AM";
            } else {
                timeString += " PM";
            }
        }
        return timeString;
    }

    /**
     * returns 0 if times are equal
     * returns -1 if current time is earlier than given time
     * returns 1 if current time is later than given time
     * @param cHour
     * @param cMinutes
     * @return 0 if the given time is the current time, -1 if the given time is
     * earlier than the current time, and 1 if the given time is later than the current time
     */
    public int timeCompare(int cHour, int cMinutes){
        assert cHour > 0 && cHour < 24: "Invalid change in hours";
        assert cMinutes > 0 && cMinutes < 60: "Invalid change in minutes";

        if (hour == cHour && minutes == cMinutes) {
            return 0;
        }
        if (hour > cHour){
            return -1;
        } else if (hour < cHour) {
            return 1;
        } else {
            if (minutes > cMinutes) {
                return -1;
            } else {
                return 1;
            }
        }
    }

    /**
     * Returns random float between 0 and 2
     * @return
     */
    public float getRandom(){
        float num  = rand.nextFloat() * 2;
        return num;
    }

    public boolean isSmallBreakfast() {
        return smallBreakfast;
    }

    public void setSmallBreakfast(boolean smallBreakfast) {
        this.smallBreakfast = smallBreakfast;
    }

    public boolean isGameOver() {return gameOver;}

    public void setGameOver(boolean gameOver) {this.gameOver = gameOver;}
}