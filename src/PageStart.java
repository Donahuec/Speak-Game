import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Created by Caitlin on 1/25/2016.
 */
public class PageStart extends PageStory {
    public Image startButton;
    public Image endButton;
    public Rectangle start;
    public Rectangle end;
    public GameText text;
    public Text text1;
    public Text text2;
    public Image bg;
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
        //set up event handler for clicking start button
        getBaseScene().setOnMouseClicked(new PressStart());
        getStage().getScene().setOnMouseMoved(new MouseEnter());
        buttonHover = 0;
        initialized = true;

    }

    /**
     * initializes assets for the scene
     */
    public void getAssets(){
        startButton = new Image("file:" + getPicDir() + "startButton.png");
        endButton = new Image("file:" + getPicDir() + "quit.png");
        text = new GameText(getTextDir() + "Start.txt");
        text1 = new Text(  getWidth() / 2 - (getWidth() /6), getHeight() / 4, "" );
        text2 = new Text( getWidth() / 2 - (getWidth() / 6), getHeight() / 3, "" );
        getRoot().getChildren().add(text1);
        getRoot().getChildren().add(text2);
        bg = new Image("file:" + getPicDir() + "cover.png", getWidth(), getHeight(), true, true);

        titleAnim[0] = new Image("file:" + getPicDir() + "title1.png");
        titleAnim[1] = new Image("file:" + getPicDir() + "title2.png");
        titleAnim[2] = new Image("file:" + getPicDir() + "title3.png");

        title = new AnimatedObject(speak,titleAnim, 0.15, true);

    }


    /**
     * checks for changes in the page
     */
    public void update(){
        getGC().drawImage(bg, 0, 0);

        getGC().drawImage(title.getFrame(), 0, 0, getWidth() / 2, getHeight() / 3);

        //Draw the start button
        double startWidth = getWidth() / 4;
        double startHeight = getHeight() / 6;
        double endWidth = getWidth() / 4;
        double endHeight = getHeight() / 6;

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
        start = new Rectangle(getWidth() / 8, getHeight() / 4 ,startWidth, startHeight - (getHeight() / 24));
        end = new Rectangle(getWidth() / 8, (getHeight() / 4) + (getHeight() / 6) ,endWidth, endHeight );




    }

    /**
     * cleans up and ends the page
     */
    public void end(){
        //clear all of the assets to save memory.
        //they will be initialized next time we change to this page
        startButton = null;
        start = null;
        text = null;


        //remove text nodes form root
        getRoot().getChildren().remove(text1);
        getRoot().getChildren().remove(text2);

        text1 = null;
        text2 = null;

        //make sure the begin method is called again next time page
        //is loaded
        initialized = false;
        //change the page
        changePage(P.ALARM);
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
            }
            else if ( end.contains( e.getX(), e.getY() ) ){
                buttonHover = 2;
            } else {
                buttonHover = 0;
            }

        }
    }


}
