import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.event.EventHandler;
import javafx.scene.text.Font;

/**
 * Created by Caitlin on 1/25/2016.
 */
public class PageMenu extends Page {
    public Image phoneBackground;
    public Image phoneButton;
    public Rectangle statusButton;
    public Rectangle agendaButton;
    public Rectangle todoButton;
    public Rectangle tutorialButton;
    public Rectangle quitButton;
    public Rectangle resumeButton;




    public PageMenu(Speak speak){
        super(speak);
    }

    /**
     * initializes the scene
     */
    public void begin(){
        getAssets();
        //set up event handler for clicking buttons
        getBaseScene().setOnMouseClicked(new PressButton());

        //event handler for Esc function
        getStage().getScene().setOnKeyPressed(new MenuPressEsc());
        initialized = true;
    }

    /**
     * initializes assets for the scene
     */
    public void getAssets(){
        phoneBackground = new Image("file:" + getPicDir() + "phone_bg.png", getWidth() / 3, getHeight(), true, true);
        phoneButton = new Image("file:" + getPicDir() + "phone_button.png", phoneBackground.getRequestedWidth() / 7, phoneBackground.getRequestedWidth() / 7, false, true);
    }

    /**
     * checks for changes in the page
     */
    public void update(){

        //Draw the phone Background
        getGC().drawImage(phoneBackground, (getWidth() / 2) - (phoneBackground.getWidth() / 2), 0);

        //Draw the buttons
        getGC().drawImage(phoneButton, (getWidth() / 2) - (phoneButton.getWidth() / 2), phoneButton.getHeight() * 1.5);
        getGC().drawImage(phoneButton, (getWidth() / 2) - (phoneButton.getWidth() * 2.5), phoneButton.getHeight() * 1.5);
        getGC().drawImage(phoneButton, (getWidth() / 2) + (phoneButton.getWidth() * 1.5), phoneButton.getHeight() * 1.5);

        getGC().drawImage(phoneButton, (getWidth() / 2) - (phoneButton.getWidth() * 2.5), phoneButton.getHeight() * 3.5);
        getGC().drawImage(phoneButton, (getWidth() / 2) - (phoneButton.getWidth() / 2), phoneButton.getHeight() * 3.5);
        getGC().drawImage(phoneButton, (getWidth() / 2) + (phoneButton.getWidth() * 1.5), phoneButton.getHeight() * 3.5);

        //rectangles for pressing buttons
        statusButton = new Rectangle((getWidth() / 2) - (phoneButton.getWidth() / 2), phoneButton.getHeight() * 1.5, phoneButton.getWidth(), phoneButton.getHeight());
        agendaButton = new Rectangle((getWidth() / 2) - (phoneButton.getWidth() * 2.5), phoneButton.getHeight() * 1.5, phoneButton.getWidth(), phoneButton.getHeight());
        todoButton = new Rectangle((getWidth() / 2) + (phoneButton.getWidth() * 1.5), phoneButton.getHeight() * 1.5, phoneButton.getWidth(), phoneButton.getHeight());

        tutorialButton = new Rectangle((getWidth() / 2) - (phoneButton.getWidth() * 2.5), phoneButton.getHeight() * 3.5, phoneButton.getWidth(), phoneButton.getHeight());
        quitButton = new Rectangle((getWidth() / 2) - (phoneButton.getWidth() / 2), phoneButton.getHeight() * 3.5, phoneButton.getWidth(), phoneButton.getHeight());
        resumeButton = new Rectangle((getWidth() / 2) + (phoneButton.getWidth() * 1.5), phoneButton.getHeight() * 3.5, phoneButton.getWidth(), phoneButton.getHeight());

        //print time
        getGC().setFill( Color.WHITE );
        getGC().setLineWidth(2);
        Font timeFont = Font.font(30);
        getGC().setFont(timeFont);
        getGC().fillText( speak.stats.getTimeString(), (getWidth() / 2) + (phoneBackground.getWidth() * 0.20), phoneButton.getHeight() * 1 );



    }

    /**
     * cleans up and ends the page
     */
    public void end(){
        speak.vars.setCurrentPage(speak.vars.getReturnPage());
    }


    /**
     * Handler for pressing the start button
     */
    class PressButton implements EventHandler<MouseEvent>
    {
        public void handle(MouseEvent e)
        {
            if ( quitButton.contains( e.getX(), e.getY() )){
                getLoop().stop();
                Platform.exit();
            } else if (resumeButton.contains( e.getX(), e.getY() )) {
                end();
            }
        }
    }

    /**
     * Handler for pressing Esc button
     */
    class MenuPressEsc implements EventHandler<KeyEvent>{
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ESCAPE){
                end();
            }
        }
    }


}
