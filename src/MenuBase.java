import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

/**
 * Created by Caitlin on 1/29/2016.
 */
abstract class MenuBase extends Page {
    public Image phoneBackground;
    public MenuBase(Speak speak) {
        super(speak);
    }
    /**
     * initializes the scene
     */
    abstract void begin();

    /**
     * initializes assets for the scene
     */
    public void getAssets(){
        phoneBackground = new Image("file:" + getPicDir() + "phone_bg.png", getWidth() / 3, getHeight(), true, true);
    }
    /**
     * checks for changes in the page
     */
    public void update(){
        //event handler for Esc function
        getStage().getScene().setOnKeyPressed(new MenuPressEsc());
        //Draw the phone Background
        getGC().drawImage(phoneBackground, (getWidth() / 2) - (phoneBackground.getWidth() / 2), 0);
    }

    /**
     * cleans up and ends the page
     */
    abstract void end();

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
