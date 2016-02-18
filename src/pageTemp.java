/**
 * Created by Caitlin on 1/25/2016.
 */


import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class pageTemp extends PageStory{
    public Image endButton;
    public Rectangle end;
    public Image[] animationTest = new Image[4];
    public AnimatedObject timeTest;
    public AnimatedObject delayTest;
    public Rectangle delayRec;
    public Image bg;
    public boolean choiceMade;



    public pageTemp(Speak speak){
        super(speak);
        choiceMade = false;

    }
    /**
     * initializes the scene
     */
    public void begin(){
        speak.getVars().setReturnPage(speak.getVars().TEMP);
        getAssets();
        //event handler for clicking end button
        initialized = true;
    }

    /**
     * initializes assets for the scene
     */
    public void getAssets() {
        endButton = new Image("file:" + getPicDir() + "endButton.png");

        animationTest[0] = new Image("file:" + getPicDir() + "lev1.png");
        animationTest[1] = new Image("file:" + getPicDir() + "lev2.png");
        animationTest[2] = new Image("file:" + getPicDir() + "lev3.png");
        animationTest[3] = new Image("file:" + getPicDir() + "lev4.png");

        timeTest = new AnimatedObject(speak,animationTest, 0.15, true);
        delayTest = new AnimatedObject(speak,animationTest, 0.15, false);

        delayRec = new Rectangle(getWidth() - 200 - animationTest[0].getWidth(), 200, animationTest[0].getWidth(), animationTest[0].getHeight());

        //rectangle to handle click event for end button
        end = new Rectangle((getWidth() / 2) - (endButton.getWidth() / 2) ,
                (getHeight() / 2) + 200, endButton.getWidth(), endButton.getHeight());

        bg = new Image("file:" + getPicDir() + "bedroom_bg.png", getWidth(), getHeight(), true, true);


    }

    /**
     * checks for changes in the page
     */
    public void update(){
        getStage().getScene().setOnMouseClicked(new PressEnd());

        //event handler for Esc function
        getStage().getScene().setOnKeyPressed(new PressEsc());
        getStage().getScene().setOnMouseMoved(new MouseEnter());

        getGC().drawImage(bg, 0, 0);


		getGC().drawImage(endButton, (getWidth() / 2) - (endButton.getWidth() / 2) ,
				(getHeight() / 2) + 200);

        getGC().drawImage(timeTest.getFrame(), 200, 200);

        getGC().drawImage(delayTest.getFrame(),getWidth() - 200 - animationTest[0].getWidth(), 200);

        cleanup();
    }

    /**
     * cleans up and ends the page
     */
    public void end(){
        endButton = null;
        end = null;
        initialized = false;

    }

    /**
     * Handler for pressing End button
     */
    class PressEnd implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent e)
        {
            if (isInteraction) {
                getInteractionChoice(e);
            }

            else if ( end.contains( e.getX(), e.getY() ) ){
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
            if(isInteraction){
                getInteractionHover(e);
            }

            else if (delayRec.contains(e.getX(), e.getY())){
                delayTest.setActive(true);
            } else {
                delayTest.setActive(false);
            }

        }
    }


    /**
     * Handler for pressing Esc button
     */
    class PressEsc implements EventHandler<KeyEvent>{
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ESCAPE ){
                clearDescription();
                speak.getVars().setCurrentPage(speak.getVars().MENU_HOME);
            }
        }
    }
}
