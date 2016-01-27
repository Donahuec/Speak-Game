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
    public double circPosition;

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

        circPosition = 0;
        initialized = true;
    }

    /**
     * initializes assets for the scene
     */
    public void getAssets() {
        endButton = new Image("file:" + getPicDir() + "endButton.png");
    }

    /**
     * checks for changes in the page
     */
    public void update(){
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


    }

    /**
     * cleans up and ends the page
     */
    public void end(){
        getLoop().stop();
        Platform.exit();
    }

    /**
     * Handler for pressing End button
     */
    class PressEnd implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent e)
        {
            if ( end.contains( e.getX(), e.getY() ) ){
                end();
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
                initialized = false;
                speak.vars.setCurrentPage(speak.vars.MENU_BASE);
            }
        }
    }
}
