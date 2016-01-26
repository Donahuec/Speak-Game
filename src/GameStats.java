public class GameStats {
    private int anxiety;
    private int stress;
    private int hour;
    private int minutes;
    private boolean twelveHourClock;

    public GameStats() {
        anxiety = 50;
        stress = 10;
        hour = 7;
        minutes = 0;
        twelveHourClock = true;
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

    public void updateAnxiety(int change){
        anxiety += change;
    }

    public void  updateStress(int change) {
        stress += change;
    }

    public void updateTime(int hours, int mins) {
        hour += hours;
        minutes += mins;

        while (minutes >= 60) {
            hours++;
            minutes -= 60;
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
}