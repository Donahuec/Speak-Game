import javafx.animation.AnimationTimer;
import javafx.application.Platform;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameLoop extends AnimationTimer {
	//make these variables of Speak so they dont have
	//to be passed as parameters.
	protected Speak speak;
	private GraphicsContext gc;
	private Stage gameStage;
    
	public GameLoop(Speak speak, GraphicsContext gc, Stage gameStage){
        this.speak = speak;
        this.gc = gc;
        this.gameStage = gameStage;
    }

    @Override
    public void handle(long now) {
		//make sure canvas is clear
    	gc.clearRect(0, 0, gameStage.getWidth(),gameStage.getHeight());

		//currently the test animation is moving a circle across the screen
		//update circle position
		speak.c.circPosition += 2;
		if (speak.c.circPosition > gameStage.getWidth()){
			speak.c.circPosition = -100;
		}

		//draw circle
		gc.fillOval(speak.c.circPosition,gameStage.getHeight() /2 ,100 , 100);

		//rectangle to handle click event for end button
		Rectangle end = new Rectangle((gameStage.getWidth() / 2) - (speak.c.endButton.getWidth() / 2) ,
				(gameStage.getHeight() / 2) + 200, speak.c.endButton.getWidth(), speak.c.endButton.getHeight());

		gc.drawImage(speak.c.endButton, (gameStage.getWidth() / 2) - (speak.c.endButton.getWidth() / 2) ,
				(gameStage.getHeight() / 2) + 200);


		//event handler for clicking end button
		gameStage.getScene().setOnMouseClicked(
				new EventHandler<MouseEvent>()
				{
					public void handle(MouseEvent e)
					{
						if ( end.contains( e.getX(), e.getY() ) ){
							stop();
							Platform.exit();
						}
					}
				});

		//event handler for Esc function
		gameStage.getScene().setOnKeyPressed(new EventHandler<KeyEvent>() {
			@Override
			public void handle(KeyEvent event) {
				if (event.getCode() == KeyCode.ESCAPE){
					stop();
					Platform.exit();
				}
			}
		});

		gameStage.show();
	}

    @Override
    public void start(){
        super.start();
    }
    @Override
    public void stop(){
        super.stop();
    }
}
