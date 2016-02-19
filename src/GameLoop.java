/**
* Handles the GameLoop
*/

import javafx.animation.AnimationTimer;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;


public class GameLoop extends AnimationTimer {
	//make these variables of Speak so they dont have
	//to be passed as parameters.
	protected Speak speak;
	private GraphicsContext gc;
	private Stage gameStage;
	private long startTime;
	double curTime;
    
	public GameLoop(Speak speak){
        this.speak = speak;
        this.gc = speak.getGc();
        this.gameStage = speak.getGameStage();
    }

    @Override
    public void handle(long now) {
		isGameOver();
		//current game time
		curTime= (now - startTime) / 1000000000.0;
		//make sure canvas is clear
    	gc.clearRect(0, 0, gameStage.getWidth(),gameStage.getHeight());
		checkSceneChange();
		speak.getVars().getCurrentPage().update();
		gameStage.show();

	}

    @Override
    public void start(){
        startTime = speak.getStartNanoTime();
		curTime = startTime;
		super.start();
    }
    @Override
    public void stop(){
        super.stop();
    }


	/**
	 * Checks if the scene has changed since the update, and sets up the new scene
	 */
	private void checkSceneChange() {
		if (!speak.getVars().getCurrentPage().initialized){
			speak.getVars().getCurrentPage().begin();
		}

	}

	private void isGameOver() {
		if (speak.getStats().getAnxiety() >= 200) {
			stop();
			Platform.exit();
		}
	}

}
