import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Created by Caitlin on 1/25/2016.
 */
public abstract class Page {
    public Speak speak;
    public boolean initialized;
    public HashMap<String, Interaction> interactions;
    public HashMap<String, TextOption> options;



    public Page(Speak speak) {
        this.speak = speak;
        initialized = false;
        options = new HashMap();
        interactions = new HashMap();
    }


    //functions to pull information from speak and its variables for easier reading
    //as well as easier modification
    public String getPicDir(){ return speak.getVars().getPicDir(); }

    public String getFontDir(){ return speak.getVars().getFontDir(); }

    public String getTextDir(){ return speak.getVars().getTextDir(); }

    public Group getRoot(){ return speak.getRoot(); }

    public Stage getStage() { return speak.getGameStage(); }

    public GraphicsContext getGC() { return speak.getGc(); }

    public GameLoop getLoop() { return speak.getGameLoop(); }

    public Scene getBaseScene() { return speak.getBaseScene(); }

    public double getWidth() { return speak.getGameStage().getWidth(); }

    public double getHeight() {return speak.getGameStage().getHeight(); }

    public int getAnxiety() { return speak.getStats().getAnxiety(); }

    public int getStress() { return speak.getStats().getStress(); }

    public int[] getTime() { return speak.getStats().getTime(); }

    public String getTimeString() { return speak.getStats().getTimeString(); }

    public void updateAnxiety(int change){
        speak.getStats().updateAnxiety(change);
    }

    public void  updateStress(int change) { speak.getStats().updateStress(change); }

    public void updateTime(int hours, int mins) { speak.getStats().updateTime(hours, mins); }

    public int timeCompare(int hours, int mins) { return speak.getStats().timeCompare(hours, mins);}

    public void changePage(P page){
        switch (page) {
            case ALARM:
                speak.getVars().setCurrentPage(speak.getVars().ALARM);
                break;
            case START:
                speak.getVars().setCurrentPage(speak.getVars().START);
                break;
            case END:
                //speak.getVars().setCurrentPage(speak.getVars().END);
                break;
            case MENU_HOME:
                speak.getVars().setCurrentPage(speak.getVars().MENU_HOME);
                break;
            case M_STATUS:
                speak.getVars().setCurrentPage(speak.getVars().M_STATUS);
                break;
            case M_AGENDA:
                speak.getVars().setCurrentPage(speak.getVars().M_AGENDA);
                break;
            case M_TODO:
                speak.getVars().setCurrentPage(speak.getVars().M_TODO);
                break;
            case M_TUTORIAL:
                speak.getVars().setCurrentPage(speak.getVars().M_TUTORIAL);
                break;
            case BEDROOM:
                speak.getVars().setCurrentPage(speak.getVars().BEDROOM);
                break;
            case KITCHEN:
                //speak.getVars().setCurrentPage(speak.getVars().KITCHEN);
                break;
            case LIVINGROOM:
                speak.getVars().setCurrentPage(speak.getVars().LIVINGROOM);
                break;
            case STREET:
               // speak.getVars().setCurrentPage(speak.getVars().STREET);
                break;
            case BUS_ENTRANCE:
               // speak.getVars().setCurrentPage(speak.getVars().BUS_ENTRANCE);
                break;
            case BUS_SEAT:
                //speak.getVars().setCurrentPage(speak.getVars().BUS_SEAT);
                break;
            case OFFICE_DESK:
                //speak.getVars().setCurrentPage(speak.getVars().OFFICE_DESK);
                break;
            case OFFICE_BREAKROOM:
                //speak.getVars().setCurrentPage(speak.getVars().OFFICE_BREAKROOM);
                break;
            case OFFICE_HALLWAY:
               // speak.getVars().setCurrentPage(speak.getVars().OFFICE_HALLWAY);
                break;
            case OFFICE_MEETING:
                //speak.getVars().setCurrentPage(speak.getVars().OFFICE_MEETING);
                break;
            case LUNCH:
               // speak.getVars().setCurrentPage(speak.getVars().LUNCH);
                break;
            case DINNER:
               // speak.getVars().setCurrentPage(speak.getVars().DINNER);
                break;
            case CAR:
               // speak.getVars().setCurrentPage(speak.getVars().CAR);
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
