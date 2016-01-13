import javafx.scene.canvas.GraphicsContext;
import javafx.stage.Stage;

public class GamePage {
	protected Speak speak;
	private GraphicsContext gc;
	private Stage gameStage;
	
	
	public GamePage(Speak speak, GraphicsContext gc, Stage gameStage){
		this.speak = speak;
        this.gc = gc;
        this.gameStage = gameStage;
	}
}