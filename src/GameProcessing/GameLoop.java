package GameProcessing;

import Pages.Page;
import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

/**
 * Handles the GameLoop
 */


public class GameLoop extends AnimationTimer {
	protected Speak speak;
	//get these from main class (speak) so the don't
	//have to be passed as parameters
	private GraphicsContext graphicsContext;
	private Stage gameStage;
	private long startTime; // in nanoseconds
	double curTime; // in seconds

	public GameLoop(Speak speak){
		this.speak = speak;
		this.graphicsContext = speak.getGraphicsContext();
		this.gameStage = speak.getGameStage();
	}

	public Stage getGameStage() {
		return gameStage;
	}

	public double getCurTime() {
		return curTime;
	}

	public GraphicsContext getGraphicsContext() {
		return graphicsContext;
	}

	public Page getCurrentPage() {return speak.getVars().getCurrentPage(); }


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

    @Override
	// JavaFX function that runs every frame
    public void handle(long now) {
		isGameOver();
		//current game time
		double toSeconds = 1000000000.0;
		curTime= (now - startTime) / toSeconds;
		//make sure canvas is clear
    	graphicsContext.clearRect(0, 0, gameStage.getWidth(),gameStage.getHeight());
		checkSceneChange();
		getCurrentPage().update();
		//update the screen
		gameStage.show();
	}

	/**
	 * Checks if the scene has changed since the update, and sets up the new scene
	 **/
	private void checkSceneChange() {
		if (!getCurrentPage().initialized){
			getCurrentPage().begin();
		}
	}

	/**
	 * Checks if the anxiety bar is full, and ends the game if it is
	 * TODO build an end page instead of just exiting the game immediately
	 */
	private void isGameOver() {
		if (speak.getStats().getAnxiety() >= speak.getStats().MAX_ANXIETY) {
			stop();
			Platform.exit();
		}
	}

}
