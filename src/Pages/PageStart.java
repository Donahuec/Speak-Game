package Pages;

import GameObject.AnimatedObject;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.event.EventHandler;

import java.io.File;
import Pages.*;
import GameObject.*;
import GameProcessing.*;

/**
 * Created by Caitlin on 1/25/2016.
 */
public class PageStart extends PageStory {
    public Image startButton;
    public Image endButton;
    public Rectangle start;
    public Rectangle end;
    public Image[] titleAnim = new Image[3];
    public AnimatedObject title;
    public int buttonHover;


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

        titleAnim[0] = new Image("file:" + getPicDir() + "start" + File.separator + "title1.png");
        titleAnim[1] = new Image("file:" + getPicDir() + "start" + File.separator + "title2.png");
        titleAnim[2] = new Image("file:" + getPicDir() + "start" + File.separator + "title3.png");

        title = new AnimatedObject(speak,titleAnim, 0.15, true, -1, false);

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
        getGC().drawImage(title.getFrame(), 0, 0, getWidth() / 2, getHeight() / 3);

        double startWidth = getWidth() / 4;
        double startHeight = getHeight() / 6;

        double endWidth = getWidth() / 4;
        double endHeight = getHeight() / 6;

        //if we are hovering over a button, make it bigger
        if (buttonHover == 1) {
            startWidth = getWidth() / 3.8;
            startHeight = getHeight() / 5.8;
        }
        if (buttonHover == 2) {
            endWidth = getWidth() / 3.8;
            endHeight = getHeight() / 5.8;
        }


        getGC().drawImage(startButton, getWidth() / 8, getHeight() / 4 ,startWidth, startHeight );
        getGC().drawImage(endButton, getWidth() / 8, (getHeight() / 4) + (getHeight() / 8) ,endWidth, endHeight );
        //Rectangle to be able to click start button
        start = new Rectangle(getWidth() / 8, getHeight() / 4 ,startWidth - (getWidth() / 20), startHeight - (getHeight() / 24));
        end = new Rectangle(getWidth() / 8, (getHeight() / 4) + (getHeight() / 8) ,endWidth  - (getWidth() / 15), endHeight );
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
