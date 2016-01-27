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
        initialized = true;
    }

    /**
     * initializes assets for the scene
     */
    public void getAssets(){
        phoneBackground = new Image("file:" + speak.vars.getPicDir() + "phone_bg.png", speak.gameStage.getWidth() / 3, speak.gameStage.getHeight(), true, true);
        phoneButton = new Image("file:" + speak.vars.getPicDir() + "phone_button.png", phoneBackground.getRequestedWidth() / 7, phoneBackground.getRequestedWidth() / 7, false, true);
    }

    /**
     * checks for changes in the page
     */
    public void update(){

        //Draw the phone Background
        speak.gc.drawImage(phoneBackground, (speak.gameStage.getWidth() / 2) - (phoneBackground.getWidth() / 2), 0);

        //Draw the buttons
        speak.gc.drawImage(phoneButton, (speak.gameStage.getWidth() / 2) - (phoneButton.getWidth() / 2), phoneButton.getHeight() * 1.5);
        speak.gc.drawImage(phoneButton, (speak.gameStage.getWidth() / 2) - (phoneButton.getWidth() * 2.5), phoneButton.getHeight() * 1.5);
        speak.gc.drawImage(phoneButton, (speak.gameStage.getWidth() / 2) + (phoneButton.getWidth() * 1.5), phoneButton.getHeight() * 1.5);

        speak.gc.drawImage(phoneButton, (speak.gameStage.getWidth() / 2) - (phoneButton.getWidth() * 2.5), phoneButton.getHeight() * 3.5);
        speak.gc.drawImage(phoneButton, (speak.gameStage.getWidth() / 2) - (phoneButton.getWidth() / 2), phoneButton.getHeight() * 3.5);
        speak.gc.drawImage(phoneButton, (speak.gameStage.getWidth() / 2) + (phoneButton.getWidth() * 1.5), phoneButton.getHeight() * 3.5);

        //rectangles for pressing buttons
        statusButton = new Rectangle((speak.gameStage.getWidth() / 2) - (phoneButton.getWidth() / 2), phoneButton.getHeight() * 1.5, phoneButton.getWidth(), phoneButton.getHeight());
        agendaButton = new Rectangle((speak.gameStage.getWidth() / 2) - (phoneButton.getWidth() * 2.5), phoneButton.getHeight() * 1.5, phoneButton.getWidth(), phoneButton.getHeight());
        todoButton = new Rectangle((speak.gameStage.getWidth() / 2) + (phoneButton.getWidth() * 1.5), phoneButton.getHeight() * 1.5, phoneButton.getWidth(), phoneButton.getHeight());

        tutorialButton = new Rectangle((speak.gameStage.getWidth() / 2) - (phoneButton.getWidth() * 2.5), phoneButton.getHeight() * 3.5, phoneButton.getWidth(), phoneButton.getHeight());
        quitButton = new Rectangle((speak.gameStage.getWidth() / 2) - (phoneButton.getWidth() / 2), phoneButton.getHeight() * 3.5, phoneButton.getWidth(), phoneButton.getHeight());
        resumeButton = new Rectangle((speak.gameStage.getWidth() / 2) + (phoneButton.getWidth() * 1.5), phoneButton.getHeight() * 3.5, phoneButton.getWidth(), phoneButton.getHeight());

        //print time
        speak.gc.setFill( Color.WHITE );
        speak.gc.setLineWidth(2);
        Font timeFont = Font.font(30);
        speak.gc.setFont(timeFont);
        speak.gc.fillText( speak.stats.getTimeString(), (speak.gameStage.getWidth() / 2) + (phoneBackground.getWidth() * 0.20), phoneButton.getHeight() * 1 );


        //set up event handler for clicking buttons
        speak.baseScene.setOnMouseClicked(new PressButton());

        //event handler for Esc function
        speak.gameStage.getScene().setOnKeyPressed(new MenuPressEsc());
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
                speak.gameLoop.stop();
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
