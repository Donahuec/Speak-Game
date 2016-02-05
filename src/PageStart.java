import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.event.EventHandler;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Created by Caitlin on 1/25/2016.
 */
public class PageStart extends Page {
    public Image startButton;
    public Rectangle rec;
    public GameText text;


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
        text = new GameText(getTextDir() + "Start.txt");

    }


    /**
     * checks for changes in the page
     */
    public void update(){



        Text text1 = new Text(  getWidth() / 2 - (getWidth() /6), getHeight() / 4,  text.getText("one"));
        text1.setWrappingWidth(getWidth() / 3);
        text1.setTextAlignment(TextAlignment.CENTER);
        text1.setFont(Font.font( "Times New Roman", getHeight() / 10 ));
        getRoot().getChildren().add(text1);


        Text text2 = new Text( getWidth() / 2 - (getWidth() / 6), getHeight() / 3,  text.getText("two"));
        text2.setWrappingWidth(getWidth() / 3);
        text2.setTextAlignment(TextAlignment.CENTER);
        text2.setFont(new Font(getHeight() / 50));

        getRoot().getChildren().add(text2);




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
        startButton = null;
        rec = null;
        text = null;
        initialized = false;
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
