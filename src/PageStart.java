import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.event.EventHandler;

/**
 * Created by Caitlin on 1/25/2016.
 */
public class PageStart extends Page {
    public Image startButton;
    public Rectangle rec;


    public PageStart(Speak speak){
        super(speak);
    }

    /**
     * initializes the scene
     */
    public void begin(){
        speak.vars.setReturnPage(speak.vars.START);
        getAssets();
        //set up event handler for clicking start button
        getBaseScene().setOnMouseClicked(new PressStart());
        initialized = true;
    }

    /**
     * initializes assets for the scene
     */
    public void getAssets(){
        startButton = new Image("file:" + getPicDir() + "startButton.png");
    }

    /**
     * checks for changes in the page
     */
    public void update(){
        //Rectangle to be able to click start button
        rec = new Rectangle((getWidth() / 2) - (startButton.getWidth() / 2) ,
                (getHeight() / 2) - (startButton.getHeight() / 2), startButton.getWidth(),
                startButton.getHeight());

        //Draw the start button
        getGC().drawImage(startButton, (getWidth() / 2) - (startButton.getWidth() / 2)
                ,(getHeight() / 2) - (startButton.getHeight() / 2));


    }

    /**
     * cleans up and ends the page
     */
    public void end(){
        changePage(P.TEMP);
    }

    /**
     * Handler for pressing the start button
     */
    class PressStart implements EventHandler<MouseEvent>
    {
        public void handle(MouseEvent e)
        {
            //enter game loop if start button pressed
            if ( rec.contains( e.getX(), e.getY() ) ){
                end();

            }
        }
    }


}
