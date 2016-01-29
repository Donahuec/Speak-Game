import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

/**
 * Created by Caitlin on 1/25/2016.
 */
public abstract class Page {
    public Speak speak;
    public boolean initialized;

    public Page(Speak speak) {
        this.speak = speak;
        initialized = false;
    }


    //functions to pull information from speak and its variables for easier reading
    //as well as easier modification
    public String getPicDir(){ return speak.vars.getPicDir(); }

    public Stage getStage() { return speak.gameStage; }

    public GraphicsContext getGC() { return speak.gc; }

    public GameLoop getLoop() { return speak.gameLoop; }

    public Scene getBaseScene() { return speak.baseScene; }

    public double getWidth() { return speak.gameStage.getWidth(); }

    public double getHeight() {return speak.gameStage.getHeight(); }

    public int getAnxiety() { return speak.stats.getAnxiety(); }

    public int getStress() { return speak.stats.getStress(); }

    public int[] getTime() { return speak.stats.getTime(); }

    public String getTimeString() { return speak.stats.getTimeString(); }

    public void updateAnxiety(int change){
        speak.stats.updateAnxiety(change);
    }

    public void  updateStress(int change) { speak.stats.updateStress(change); }

    public void updateTime(int hours, int mins) { speak.stats.updateTime(hours, mins); }

    public void changePage(P page){
        switch (page) {
            case START:
                speak.vars.setCurrentPage(speak.vars.START);
                break;
            case END:
                speak.vars.setCurrentPage(speak.vars.END);
                break;
            case TEMP:
                speak.vars.setCurrentPage(speak.vars.TEMP);
                break;
            case MENU_HOME:
                speak.vars.setCurrentPage(speak.vars.MENU_HOME);
                break;
            case M_STATUS:
                speak.vars.setCurrentPage(speak.vars.M_STATUS);
                break;
            case M_AGENDA:
                speak.vars.setCurrentPage(speak.vars.M_AGENDA);
                break;
            case M_TODO:
                speak.vars.setCurrentPage(speak.vars.M_TODO);
                break;
            case M_TUTORIAL:
                speak.vars.setCurrentPage(speak.vars.M_TUTORIAL);
                break;
            case BEDROOM:
                speak.vars.setCurrentPage(speak.vars.BEDROOM);
                break;
            case KITCHEN:
                speak.vars.setCurrentPage(speak.vars.KITCHEN);
                break;
            case LIVINGROOM:
                speak.vars.setCurrentPage(speak.vars.LIVINGROOM);
                break;
            case STREET:
                speak.vars.setCurrentPage(speak.vars.STREET);
                break;
            case BUS_ENTRANCE:
                speak.vars.setCurrentPage(speak.vars.BUS_ENTRANCE);
                break;
            case BUS_SEAT:
                speak.vars.setCurrentPage(speak.vars.BUS_SEAT);
                break;
            case OFFICE_DESK:
                speak.vars.setCurrentPage(speak.vars.OFFICE_DESK);
                break;
            case OFFICE_BREAKROOM:
                speak.vars.setCurrentPage(speak.vars.OFFICE_BREAKROOM);
                break;
            case OFFICE_HALLWAY:
                speak.vars.setCurrentPage(speak.vars.OFFICE_HALLWAY);
                break;
            case OFFICE_MEETING:
                speak.vars.setCurrentPage(speak.vars.OFFICE_MEETING);
                break;
            case LUNCH:
                speak.vars.setCurrentPage(speak.vars.LUNCH);
                break;
            case DINNER:
                speak.vars.setCurrentPage(speak.vars.DINNER);
                break;
            case CAR:
                speak.vars.setCurrentPage(speak.vars.CAR);
                break;


        }
    }

    /**
     * initializes the scene
     */
    abstract void begin();

    /**
     * initializes assets for the scene
     */
    abstract void getAssets();

    /**
     * checks for changes in the page
     */
    abstract void update();

    /**
     * cleans up and ends the page
     */
    abstract void end();

}
