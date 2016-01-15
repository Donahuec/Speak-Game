
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;




public class Speak extends Application 
{
	public Variables c = new Variables();
	
    public static void main(String[] args) 
    {
        launch(args);
    }

    @Override
    public void start(Stage gameStage) 
    {
    	
        gameStage.setTitle( "Speak" );
        gameStage.setMaximized(true);
        gameStage.initStyle(StageStyle.UNDECORATED);


        Group root = new Group();
        Scene baseScene = new Scene( root );
        gameStage.setScene( baseScene );
        gameStage.show();
        
        Canvas canvas = new Canvas( gameStage.getWidth(), gameStage.getHeight() );
        root.getChildren().add( canvas );  
        
        GraphicsContext gc = canvas.getGraphicsContext2D();

        
        GameLoop gameLoop = new GameLoop(this, gc, gameStage);

        gc.clearRect(0, 0, gameStage.getWidth(),gameStage.getHeight());
        Rectangle rec = new Rectangle((gameStage.getWidth() / 2) - (c.startButton.getWidth() / 2) ,(gameStage.getHeight() / 2) - (c.startButton.getHeight() / 2), c.startButton.getWidth(), c.startButton.getHeight());

        gc.drawImage(c.startButton, (gameStage.getWidth() / 2) - (c.startButton.getWidth() / 2) ,(gameStage.getHeight() / 2) - (c.startButton.getHeight() / 2));

        baseScene.setOnMouseClicked(
                new EventHandler<MouseEvent>()
                {
                    public void handle(MouseEvent e)
                    {
                        if ( rec.contains( e.getX(), e.getY() ) ){

                            gameLoop.start();
                        }
                    }
                });


        gameStage.show();
        
        //gameLoop.start();
        
        //final long timeStart = System.currentTimeMillis();
        
        
        
        
    }
    
}


class Variables {
	public double circPosition;
	String workingDir = System.getProperty("user.dir");
	boolean endPress;
	Image startButton;
    Image endButton;
	
	public Variables(){
		String name = workingDir + File.separator + "assets" + File.separator;
		startButton = new Image("file:" + name + "startButton.png");
        endButton = new Image("file:" + name + "endButton.png");
		circPosition = 0;
	}
}





