package Pages;

import GameProcessing.Speak;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.io.File;

/**
 * Base class for all of the Menu Pages
 */

abstract class MenuBase extends Page {
    protected Image phoneBackground;

    public MenuBase(Speak speak) {
        super(speak);
    }

    /**
     * initializes the scene
     */
    public abstract void begin();

    /**
     * initializes assets for the scene
     */
    public void getAssets(){
        double phoneWidth = getWidth() / 3;
        phoneBackground = new Image("file:" + getPicDir() + "menu" + File.separator + "phone_bg.png", phoneWidth, getHeight(), true, true);
    }
    /**
     * checks for changes in the page
     */
    public void update(){
        //event handler for Esc function
        getStage().getScene().setOnKeyPressed(new MenuPressEsc());
        //Draw the phone Background
        double phoneX = (getWidth() / 2) - (phoneBackground.getWidth() / 2);
        getGC().drawImage(phoneBackground, phoneX, 0);
    }

    /**
     * cleans up and ends the page
     */
    public abstract void end();

    /**
     * Handler for pressing Esc button
     */
    class MenuPressEsc implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ESCAPE){
                end();
            }
        }
    }
}
