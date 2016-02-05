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


public class pageTemp extends Page{
    public Image endButton;
    public Rectangle end;
    public double circPosition = 0;
    public Image[] animationTest = new Image[4];
    public AnimatedObject timeTest;
    public AnimatedObject delayTest;
    public Rectangle delayRec;
    public  boolean hover = false;

    public pageTemp(Speak speak){
        super(speak);

    }
    /**
     * initializes the scene
     */
    public void begin(){
        speak.vars.setReturnPage(speak.vars.TEMP);
        getAssets();
        //event handler for clicking end button
        getStage().getScene().setOnMouseClicked(new PressEnd());

        //event handler for Esc function
        getStage().getScene().setOnKeyPressed(new PressEsc());

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
        delayTest = new AnimatedObject(speak,animationTest, 8);

        delayRec = new Rectangle(getWidth() - 200 - animationTest[0].getWidth(), 200, animationTest[0].getWidth(), animationTest[0].getHeight());



    }

    /**
     * checks for changes in the page
     */
    public void update(){
        getStage().getScene().setOnMouseMoved(new MouseEnter());


        		//currently the test animation is moving a circle across the screen
		//update circle position
		circPosition += 4;
		if (circPosition > getWidth()){
			circPosition = -100;
		}

        getGC().setFill( Color.BLACK );
		//draw circle
		getGC().fillOval(circPosition,getHeight() /2 ,100 , 100);

		//rectangle to handle click event for end button
		end = new Rectangle((getWidth() / 2) - (endButton.getWidth() / 2) ,
				(getHeight() / 2) + 200, endButton.getWidth(), endButton.getHeight());

		getGC().drawImage(endButton, (getWidth() / 2) - (endButton.getWidth() / 2) ,
				(getHeight() / 2) + 200);

        getGC().drawImage(timeTest.getFrame(), 200, 200);

        if (hover == true) {
            getGC().drawImage(delayTest.getFrame(),getWidth() - 200 - animationTest[0].getWidth(), 200);
        } else {
            getGC().drawImage(delayTest.getCurFrame(),getWidth() - 200 - animationTest[0].getWidth(), 200);
        }



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
            if (delayRec.contains(e.getX(), e.getY())){
                hover = true;
            } else {
                hover = false;
            }

        }
    }




    /**
     * Handler for pressing Esc button
     */
    class PressEsc implements EventHandler<KeyEvent>{
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ESCAPE){

                speak.vars.setCurrentPage(speak.vars.MENU_HOME);
                end();
            }
        }
    }
}
