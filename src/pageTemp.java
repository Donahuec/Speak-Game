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
        circPosition = 0;
        initialized = true;
    }

    /**
     * initializes assets for the scene
     */
    public void getAssets() {
        endButton = new Image("file:" + speak.vars.getPicDir() + "endButton.png");
    }

    /**
     * checks for changes in the page
     */
    public void update(){
        		//currently the test animation is moving a circle across the screen
		//update circle position
		circPosition += 4;
		if (circPosition > speak.gameStage.getWidth()){
			circPosition = -100;
		}

        speak.gc.setFill( Color.BLACK );
		//draw circle
		speak.gc.fillOval(circPosition,speak.gameStage.getHeight() /2 ,100 , 100);

		//rectangle to handle click event for end button
		end = new Rectangle((speak.gameStage.getWidth() / 2) - (endButton.getWidth() / 2) ,
				(speak.gameStage.getHeight() / 2) + 200, endButton.getWidth(), endButton.getHeight());

		speak.gc.drawImage(endButton, (speak.gameStage.getWidth() / 2) - (endButton.getWidth() / 2) ,
				(speak.gameStage.getHeight() / 2) + 200);

		//event handler for clicking end button
		speak.gameStage.getScene().setOnMouseClicked(new PressEnd());

		//event handler for Esc function
		speak.gameStage.getScene().setOnKeyPressed(new PressEsc());
    }

    /**
     * cleans up and ends the page
     */
    public void end(){
        speak.gameLoop.stop();
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
