package Pages;

/**
 * Start page of the game.
 */

import GameProcessing.Speak;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.io.File;


public class PageStart extends PageStory {
    public Image startButton;
    public Image endButton;
    public Rectangle start;
    public Rectangle end;
    public int buttonHover;
    private final double BUTTON_SIZE_CHANGE = 1.2;


    public PageStart(Speak speak){
        super(speak);
    }

    /**
     * initializes the scene
     */
    public void begin(){
        speak.getVars().setReturnPage(speak.getVars().START);
        getAssets();
        setEventHandlers();
        buttonHover = 0;
        initialized = true;
    }

    /**
     * initializes assets for the scene
     */
    public void getAssets(){
        startButton = new Image("file:" + getPicDir() + "start" + File.separator + "start.png");
        endButton = new Image("file:" + getPicDir() + "start" + File.separator + "quit.png");
        bg = new Image("file:" + getPicDir() + "start" + File.separator + "cover.png", getWidth(), getHeight(), false, true);
    }

    public void update() {
        setEventHandlers();
        drawbg();
        drawImages();
        cleanup();
    }


    /**
     * cleans up and ends the page
     */
    public void end(){
        //clear all of the assets to save memory.
        startButton = null;
        start = null;
        initialized = false;
        //change the page
        changePage(P.ALARM);
        endPage();
    }


    @Override
    public void drawImages() {

        double startWidth = getWidth() / 5.49;
        double startHeight = getHeight() / 10.8;

        double endWidth = getWidth() / 5.49;
        double endHeight = getHeight() / 10.8;

        //if we are hovering over a button, make it bigger
        if (buttonHover == 1) {
            startWidth *= BUTTON_SIZE_CHANGE;
            startHeight *= BUTTON_SIZE_CHANGE;
        }
        if (buttonHover == 2) {
            endWidth *= BUTTON_SIZE_CHANGE;
            endHeight *= BUTTON_SIZE_CHANGE;
        }

        double startX = getWidth() / 8;
        double startY = getHeight() / 15;
        double endX = getWidth() / 1.3;
        double endY = getHeight() / 15;

        double startWidthMod = (getWidth() / 20);
        double startHeightMod = (getHeight() / 24);
        double endWidthMod = (getWidth() / 15);

        getGC().drawImage(startButton, startX, startY ,startWidth, startHeight );
        getGC().drawImage(endButton, endX, endY,endWidth, endHeight );
        //Rectangle to be able to click start button
        start = new Rectangle(startX, startY,startWidth - startWidthMod, startHeight - startHeightMod);
        end = new Rectangle(endX, endY,endWidth  - endWidthMod, endHeight);
    }

    @Override
    public void setEventHandlers() {
        //set up event handler for clicking start button
        getBaseScene().setOnMouseClicked(new PressStart());
        getStage().getScene().setOnMouseMoved(new MouseEnter());
    }


    /**
     * Handler for pressing the start button
     */
    class PressStart implements EventHandler<MouseEvent>
    {
        public void handle(MouseEvent e)
        {
            //enter game loop if start button pressed
            if ( start.contains( e.getX(), e.getY() ) ){
                end();
            }
            if ( end.contains( e.getX(), e.getY() ) ){
                getLoop().stop();
                Platform.exit();
            }
        }
    }

    /**
     * Handler for the mouse hovering on items
     *
     */
    class MouseEnter implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent e)
        {
            if ( start.contains( e.getX(), e.getY() ) ){
                buttonHover = 1;
            } else if ( end.contains( e.getX(), e.getY() ) ){
                buttonHover = 2;
            } else {
                buttonHover = 0;
            }

        }
    }
}
