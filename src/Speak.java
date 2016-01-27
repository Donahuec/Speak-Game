/*
*Main File for Game run. Initializes window and starts game loop
 */
import javafx.application.Application;

import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCombination;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.geometry.Rectangle2D;



// must extend Application for JavaFx
public class Speak extends Application 
{
    //holds variables for game will change to own file
	public Variables vars = new Variables(this);
    public GameStats stats = new GameStats();
    public GraphicsContext gc;
    public Stage gameStage;
    public Scene baseScene;
    public GameLoop gameLoop;

    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage stage)
    {

        gameStage = stage;
        // set up window
        //currently is windowed full screen
        //test on Mac?
        gameStage.setTitle( "Speak" );
        //gameStage.setMaximized(true);
        gameStage.initStyle(StageStyle.UNDECORATED);
        gameStage.setFullScreen(true);
        gameStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        gameStage.setFullScreenExitHint("");

        //container for scene items
        Group root = new Group();

        baseScene = new Scene( root );
        gameStage.setScene( baseScene );
        gameStage.show();

        //canvas to draw on, size of window
        Canvas canvas = new Canvas( gameStage.getWidth(), gameStage.getHeight());
        root.getChildren().add( canvas );
        gc = canvas.getGraphicsContext2D();

        //initialize Game Loop
        gameLoop = new GameLoop(this);

        gameLoop.start();
    }
}








