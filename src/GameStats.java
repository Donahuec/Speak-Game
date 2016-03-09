import java.util.Random;

public class GameStats {

    private boolean gameOver;
    private int anxiety;
    private int stress;
    private int hour;
    private int minutes;
    private boolean twelveHourClock;
    private boolean smallBreakfast;
    private Random rand;

    public GameStats() {
        anxiety = 50;
        stress = 10;
        hour = 7;
        minutes = 20;
        twelveHourClock = true;
        smallBreakfast = true;
        gameOver = false;
        rand = new Random();
    }

    public int getAnxiety() {return anxiety;}
    public int getStress() {return stress;}

    public int[] getTime() {
        int[] timeArray = new int[3];
        timeArray[1] = minutes;
        if (twelveHourClock && hour > 12) {
            timeArray[0] = hour - 12;
        } else {
            timeArray[0] = hour;
        }
        if (hour > 11 && hour < 24) {
            timeArray[2] = 1;
        } else {
            timeArray[2] = 0;
        }
        return timeArray;
    }

    public void updateAnxiety(int change, int min, int max){
        float update = change;
        update = update * getRandom();
        if(change < 0) {
            update = (int)(update  * (2 - (stress / 25.0)));
            update = (int)(update * (2 - (anxiety / 100.0)));
        } else {
            update = (int)(update  * (stress / 25.0));
            update = (int)(update * (anxiety / 100.0));
        }
        if (update > max){
            update = max;
        } else if (update < min) {
            update = min;
        }
        anxiety += (int)update;
        if (anxiety < 0) {
            anxiety = 0;
        }
        if (anxiety >= 200) {
            gameOver = true;
        }
    }

    public void  updateStress(int change, int min, int max) {
        int update = (int)(change * getRandom());
        if (update > max){
            update = max;
        } else if (update < min) {
            update = min;
        }
        stress += update;
        if (stress > 50) {
            stress = 50;
        } else if (stress < 0) {
            stress = 0;
        }
    }

    public void updateTime(int hours, int mins) {
        hour += hours;
        minutes += mins;

        while (minutes >= 60) {
            hour++;
            minutes = minutes % 60;
        }
    }

    public String getTimeString() {
        int[] timeArray = getTime();
        String timeString =  String.format("%02d:%02d ", timeArray[0], timeArray[1]);
        if (timeArray[2] == 0) {
            timeString += "AM";
        } else {
            timeString += "PM";
        }

        return timeString;
    }

    /**
     * returns 0 if times are equal
     * returns -1 if current time is earlier than given time
     * returns 1 if current time is later than given time
     * @param cHour
     * @param cMinutes
     * @return
     */
    public int timeCompare(int cHour, int cMinutes){
        if (hour == cHour && minutes == cMinutes) {
            return 0;
        }
        if (hour < cHour){
            return -1;
        } else if (hour > cHour) {
            return 1;
        } else {
            if (minutes < cMinutes) {
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
        int ranInt = rand.nextInt(50) + 50;
        float num  = (float) (ranInt / 100.0);
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