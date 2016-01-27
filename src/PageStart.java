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
        initialized = true;
    }

    /**
     * initializes assets for the scene
     */
    public void getAssets(){
        startButton = new Image("file:" + speak.vars.getPicDir() + "startButton.png");
    }

    /**
     * checks for changes in the page
     */
    public void update(){
        //Rectangle to be able to click start button
        rec = new Rectangle((speak.gameStage.getWidth() / 2) - (startButton.getWidth() / 2) ,
                (speak.gameStage.getHeight() / 2) - (startButton.getHeight() / 2), startButton.getWidth(),
                startButton.getHeight());

        //Draw the start button
        speak.gc.drawImage(startButton, (speak.gameStage.getWidth() / 2) - (startButton.getWidth() / 2)
                ,(speak.gameStage.getHeight() / 2) - (startButton.getHeight() / 2));

        //set up event handler for clicking start button
        speak.baseScene.setOnMouseClicked(new PressStart());
    }

    /**
     * cleans up and ends the page
     */
    public void end(){
        speak.vars.setCurrentPage(speak.vars.TEMP);
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
