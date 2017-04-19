package GameProcessing;

/**
*Main File for Game run. Initializes window and starts game loop
 */

import GameObject.GameStats;
import javafx.application.Application;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.KeyCombination;
import javafx.stage.Stage;


// must extend Application for JavaFx
public class Speak extends Application 
{
    //holds variables for game will change to own file
	private GameVariables vars;
    private GameStats stats = new GameStats();
    private GraphicsContext graphicsContext;
    private Stage gameStage;
    private Scene baseScene;
    private GameLoop gameLoop;
    private Group root;
    private final long startNanoTime = System.nanoTime();

    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage stage)
    {
        gameStage = stage;

        // set up window
        gameStage.setTitle( "Speak" );
        //gameStage.setMaximized(true);
        //gameStage.initStyle(StageStyle.UNDECORATED);
        gameStage.setFullScreen(true);
        gameStage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
        gameStage.setFullScreenExitHint("");

        //container for scene items
        root = new Group();

        baseScene = new Scene( root );
        gameStage.setScene( baseScene );
        gameStage.show();

        //canvas to draw on, size of window
        Canvas canvas = new Canvas( gameStage.getWidth(), gameStage.getHeight());
        root.getChildren().add( canvas );
        graphicsContext = canvas.getGraphicsContext2D();

        vars = new GameVariables(this);
        //initialize GameObject Loop
        gameLoop = new GameLoop(this);
        gameLoop.start();
    }

    public GameLoop getGameLoop() {
        return gameLoop;
    }

    public GameStats getStats() {
        return stats;
    }

    public Group getRoot() { return root; }

    public void setRoot( Group newRoot) { root = newRoot; }

    public GraphicsContext getGraphicsContext() {
        return graphicsContext;
    }

    public long getStartNanoTime() {
        return startNanoTime;
    }

    public Scene getBaseScene() {
        return baseScene;
    }

    public Stage getGameStage() {
        return gameStage;
    }

    public GameVariables getVars() {
        return vars;
    }
}








