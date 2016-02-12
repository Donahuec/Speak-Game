import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.HashMap;

/**
 * Created by Caitlin on 1/25/2016.
 */
public abstract class Page {
    public Speak speak;
    public boolean initialized;
    public boolean isInteraction;
    public Interaction curInteraction;
    public int hover;
    public int choice;
    private double width;
    private double height;
    private double x;
    private double y;
    private Rectangle choice1;
    private Rectangle choice2;
    private Rectangle choice3;
    private Rectangle choice4;
    private Rectangle choice5;
    private boolean descriptionInUse;
    private Text description;
    private boolean descrec;
    public HashMap<String, Interaction> interactions;
    public HashMap<String, TextOption> options;


    public Page(Speak speak) {
        this.speak = speak;
        initialized = false;
        isInteraction = false;
        hover = 0;
        choice = 0;

        //dimensions for options rectangle
        width = (speak.getGameStage().getWidth() / 2);
        height = (speak.getGameStage().getHeight()/ 2);
        x = speak.getGameStage().getWidth() / 4;
        y = speak.getGameStage().getHeight() / 5;

        //choice sections
        choice1 = new Rectangle(x, y, width, height / 7);
        choice2 = new Rectangle(x, y + (0.5 * y), width, height / 7);
        choice3 = new Rectangle(x, y + y, width, height / 7);
        choice4 = new Rectangle(x, y + (1.5 * y), width, height / 7);
        choice5 = new Rectangle(x, y + (2 * y), width, height / 7);

        descriptionInUse = false;

        //text object for the Description
        description = new Text(  30, getHeight() - (getHeight()/ 6) + 30,  "");
        description.setWrappingWidth(getWidth() - 60);
        description.setTextAlignment(TextAlignment.CENTER);
        description.setFont(Font.font( "Times New Roman", getHeight() / 60 ));
        getRoot().getChildren().add(description);

        //rectangle to see if description rectangle has been drawn this frame
        descrec = false;

        options = new HashMap();
        interactions = new HashMap();
    }

    /**
    * Cleanup function to clear variables at end of frame
     */
    public void cleanup() {
        choice = 0;
        descrec = false;
    }

    /**
     * sets the description text to an empty string
     * so that it is no longer visible
     * @return
     */
    public boolean clearDescription() {
        if (descriptionInUse){
            description.setText("");
            descriptionInUse = false;
            return true;
        }
        return false;
    }

    /**
     * processes interactions for this frame
     */
    public void handleInteractions() {
        if (isInteraction && curInteraction != null) {
            curInteraction.process(getLoop().curTime);
        }
    }

    /**
     * Draws the rectangle to hold the description text
     */
    public void drawDescriptionSquare() {
        //check if the rectangle has already been drawn this scene
        if (!descrec){
            getGC().setFill(Color.AQUAMARINE);
            getGC().setStroke(Color.BLACK);
            getGC().setLineWidth(1);
            //opacity
            getGC().setGlobalAlpha(0.5);
            getGC().fillRoundRect(10, getHeight() - (getHeight()/ 6), getWidth() - 20, getHeight() / 7, 15, 15);
            getGC().strokeRoundRect(10, getHeight() - (getHeight()/ 6), getWidth() - 20, getHeight() / 7, 15, 15);
            getGC().setGlobalAlpha(1);
            descrec = true;
        }

    }

    /**
     * changes the description to str
     * @param str
     */
    public void addDescription(String str) {
        drawDescriptionSquare();
        description.setText(str);
        descriptionInUse = true;
    }


    /**
     * method to add to eventhandler that gets what choice (if any) has been clicked
     * @param e
     */
    public void getInteractionChoice(MouseEvent e) {
        if ( choice1.contains( e.getX(), e.getY() )  ){
            choice = 1;
            isInteraction = false;
            curInteraction.clear();
        } else if ( choice2.contains( e.getX(), e.getY() ) && curInteraction.getLength() > 1 ){
            choice = 2;
            isInteraction = false;
            curInteraction.clear();
        } else if ( choice3.contains( e.getX(), e.getY() ) && curInteraction.getLength() > 2 ){
            choice = 3;
            isInteraction = false;
            curInteraction.clear();
        } else if ( choice4.contains( e.getX(), e.getY() ) && curInteraction.getLength() > 3 ){
            choice = 4;
            isInteraction = false;
            curInteraction.clear();
        } else if ( choice5.contains( e.getX(), e.getY() ) && curInteraction.getLength() > 5 ){
            choice = 5;
            isInteraction = false;
            curInteraction.clear();
        }
    }

    /**
     * method to be called by eventhandler to get what choice is being hovered over
     * @param e
     */
    public void getInteractionHover(MouseEvent e) {
        if ( choice1.contains( e.getX(), e.getY() ) ){
            hover = 1;
        } else if ( choice2.contains( e.getX(), e.getY() ) ){
            hover = 2;
        } else if ( choice3.contains( e.getX(), e.getY() ) ){
            hover = 3;
        } else if ( choice4.contains( e.getX(), e.getY() ) ){
            hover = 4;
        } else if ( choice5.contains( e.getX(), e.getY() ) ){
            hover = 5;
        } else {
            hover = 0;
        }
    }


    public boolean isInteraction() {
        return isInteraction;
    }

    public int getChoice() {
        return choice;
    }

    public int getHover() {
        return hover;
    }

    public Interaction getCurInteraction() {
        return curInteraction;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public void setHover(int hover) {
        this.hover = hover;
    }

    //functions to pull information from speak and its variables for easier reading
    //as well as easier modification
    public String getPicDir(){ return speak.getVars().getPicDir(); }

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

    public void changePage(P page){
        switch (page) {
            case ALARM:
                speak.getVars().setCurrentPage(speak.getVars().ALARM);
                break;
            case START:
                speak.getVars().setCurrentPage(speak.getVars().START);
                break;
            case END:
                speak.getVars().setCurrentPage(speak.getVars().END);
                break;
            case TEMP:
                speak.getVars().setCurrentPage(speak.getVars().TEMP);
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
                speak.getVars().setCurrentPage(speak.getVars().KITCHEN);
                break;
            case LIVINGROOM:
                speak.getVars().setCurrentPage(speak.getVars().LIVINGROOM);
                break;
            case STREET:
                speak.getVars().setCurrentPage(speak.getVars().STREET);
                break;
            case BUS_ENTRANCE:
                speak.getVars().setCurrentPage(speak.getVars().BUS_ENTRANCE);
                break;
            case BUS_SEAT:
                speak.getVars().setCurrentPage(speak.getVars().BUS_SEAT);
                break;
            case OFFICE_DESK:
                speak.getVars().setCurrentPage(speak.getVars().OFFICE_DESK);
                break;
            case OFFICE_BREAKROOM:
                speak.getVars().setCurrentPage(speak.getVars().OFFICE_BREAKROOM);
                break;
            case OFFICE_HALLWAY:
                speak.getVars().setCurrentPage(speak.getVars().OFFICE_HALLWAY);
                break;
            case OFFICE_MEETING:
                speak.getVars().setCurrentPage(speak.getVars().OFFICE_MEETING);
                break;
            case LUNCH:
                speak.getVars().setCurrentPage(speak.getVars().LUNCH);
                break;
            case DINNER:
                speak.getVars().setCurrentPage(speak.getVars().DINNER);
                break;
            case CAR:
                speak.getVars().setCurrentPage(speak.getVars().CAR);
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
