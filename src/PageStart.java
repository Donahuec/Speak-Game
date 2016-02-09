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
public class PageStart extends Page {
    public Image startButton;
    public Rectangle rec;
    public GameText text;
    public Text text1;
    public Text text2;




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
        initialized = true;

    }

    /**
     * initializes assets for the scene
     */
    public void getAssets(){
        startButton = new Image("file:" + getPicDir() + "startButton.png");
        text = new GameText(getTextDir() + "Start.txt");
        text1 = new Text(  getWidth() / 2 - (getWidth() /6), getHeight() / 4, "" );
        text2 = new Text( getWidth() / 2 - (getWidth() / 6), getHeight() / 3, "" );
        getRoot().getChildren().add(text1);
        getRoot().getChildren().add(text2);
    }


    /**
     * checks for changes in the page
     */
    public void update(){



        text1.setText(text.getText("one"));
        text1.setWrappingWidth(getWidth() / 3);
        text1.setTextAlignment(TextAlignment.CENTER);
        text1.setFont(Font.font( "Times New Roman", getHeight() / 10 ));


        text2.setText(text.getText("two"));
        text2.setWrappingWidth(getWidth() / 3);
        text2.setTextAlignment(TextAlignment.CENTER);
        text2.setFont(new Font(getHeight() / 50));


        //Rectangle to be able to click start button
        rec = new Rectangle((getWidth() / 2) - (startButton.getWidth() / 2) ,
                (getHeight() / 2), startButton.getWidth(),
                startButton.getHeight());

        //Draw the start button
        getGC().drawImage(startButton, (getWidth() / 2) - (startButton.getWidth() / 2)
                ,(getHeight() / 2));


    }

    /**
     * cleans up and ends the page
     */
    public void end(){
        //clear all of the assets to save memory.
        //they will be initialized next time we change to this page
        startButton = null;
        rec = null;
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
