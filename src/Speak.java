/*
*Main File for Game run. Initializes window and starts game loop
 */
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;



// must extend Application for JavaFx
public class Speak extends Application 
{
    //holds variables for game will change to own file
	public Variables c = new Variables();
	
    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage gameStage) 
    {

        // set up window
        //currently is windowed full screen
        //test on Mac?
        gameStage.setTitle( "Speak" );
        gameStage.setMaximized(true);
        gameStage.initStyle(StageStyle.UNDECORATED);

        //container for scene items
        Group root = new Group();

        Scene baseScene = new Scene( root );
        gameStage.setScene( baseScene );
        gameStage.show();

        //canvas to draw on, size of window
        Canvas canvas = new Canvas( gameStage.getWidth(), gameStage.getHeight() );
        root.getChildren().add( canvas );
        GraphicsContext gc = canvas.getGraphicsContext2D();

        //initialize Game Loop
        GameLoop gameLoop = new GameLoop(this, gc, gameStage);

        //Rectangle to be able to click start button
        Rectangle rec = new Rectangle((gameStage.getWidth() / 2) - (c.startButton.getWidth() / 2) ,
                (gameStage.getHeight() / 2) - (c.startButton.getHeight() / 2), c.startButton.getWidth(),
                c.startButton.getHeight());

        //Draw the start button
        gc.drawImage(c.startButton, (gameStage.getWidth() / 2) - (c.startButton.getWidth() / 2)
                ,(gameStage.getHeight() / 2) - (c.startButton.getHeight() / 2));

        //set up event handler for clicking start button
        baseScene.setOnMouseClicked(
                new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent e)
                    {
                        //enter game loop if start button pressed
                        if ( rec.contains( e.getX(), e.getY() ) ){
                            gameLoop.start();
                        }
                    }
                });
    }
}








