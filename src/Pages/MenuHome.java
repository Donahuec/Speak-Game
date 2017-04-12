package Pages;

/**
 * Home page of the menu
 */

import GameProcessing.Speak;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.io.File;



public class MenuHome extends MenuBase {
    public Image phoneButton;
    public Rectangle statusButton;
    public Rectangle agendaButton;
    public Rectangle todoButton;
    public Rectangle tutorialButton;
    public Rectangle quitButton;
    public Rectangle resumeButton;
    public MenuHome(Speak speak){
        super(speak);
    }

    private final int LINE_WIDTH = 2;
    private final int FONT_SIZE = 30;


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
        super.getAssets();
        double buttonDimension = phoneBackground.getRequestedWidth() / 7;
        String phonePath = "file:" + getPicDir() + "menu" + File.separator + "phone_button.png";
        phoneButton = new Image(phonePath, buttonDimension, buttonDimension, false, true);
    }

    /**
     * checks for changes in the page
     */
    public void update(){
        super.update();
        //set up event handler for clicking buttons
        getBaseScene().setOnMouseClicked(new PressButton());
        //Draw the buttons
        double middle = getWidth() / 2;
        double firstColumn = phoneButton.getWidth() * 2.5;
        double secondColumn = phoneButton.getWidth() / 2;
        double thirdColumn = phoneButton.getWidth() * 1.5;
        double firstRow = phoneButton.getHeight() * 1.5;
        double secondRow = phoneButton.getHeight() * 3.5;

        getGC().drawImage(phoneButton, middle - firstColumn, firstRow);
        getGC().drawImage(phoneButton, middle  - secondColumn, firstRow);
        getGC().drawImage(phoneButton, middle + thirdColumn, firstRow);

        getGC().drawImage(phoneButton, middle - firstColumn, secondRow);
        getGC().drawImage(phoneButton, middle - secondColumn, secondRow);
        getGC().drawImage(phoneButton, middle + thirdColumn, secondRow);

        //rectangles for pressing buttons
        statusButton = new Rectangle(middle - firstColumn, firstRow, phoneButton.getWidth(), phoneButton.getHeight());
        agendaButton = new Rectangle(middle - secondColumn, firstRow, phoneButton.getWidth(), phoneButton.getHeight());
        todoButton = new Rectangle(middle + thirdColumn, firstRow, phoneButton.getWidth(), phoneButton.getHeight());

        tutorialButton = new Rectangle(middle - firstColumn, secondRow, phoneButton.getWidth(), phoneButton.getHeight());
        quitButton = new Rectangle(middle - secondColumn, secondRow, phoneButton.getWidth(), phoneButton.getHeight());
        resumeButton = new Rectangle(middle + thirdColumn, secondRow, phoneButton.getWidth(), phoneButton.getHeight());

        //print time
        getGC().setFill( Color.WHITE );
        getGC().setLineWidth(LINE_WIDTH);
        Font timeFont = Font.font(FONT_SIZE);
        getGC().setFont(timeFont);

        double timeX = phoneBackground.getWidth() * 0.20;
        getGC().fillText( getTimeString(), middle + timeX, phoneButton.getHeight());
    }

    /**
     * cleans up and ends the page
     */
    public void end(){
        changePage(getReturnPage());
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
}
