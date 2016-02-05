/**
* Handles the GameLoop
*/

import javafx.animation.AnimationTimer;
import javafx.scene.Node;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Text;
import javafx.stage.Stage;


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
        this.gc = speak.gc;
        this.gameStage = speak.gameStage;
    }

    @Override
    public void handle(long now) {
		//clear any text nodes currently on screen
		for (Node node : speak.root.getChildren()) {
			if (node instanceof Text) {
				// clear
				((Text)node).setText("");
			}
		}

		//current game time
		curTime= (curTime - startTime) / 1000000000.0;
		//make sure canvas is clear
    	gc.clearRect(0, 0, gameStage.getWidth(),gameStage.getHeight());
		checkSceneChange();
		speak.vars.getCurrentPage().update();
		gameStage.show();

	}

    @Override
    public void start(){
        startTime = speak.startNanoTime;
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
		if (!speak.vars.getCurrentPage().initialized) speak.vars.getCurrentPage().begin();
	}

}
