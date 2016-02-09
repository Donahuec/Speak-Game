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
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;


public class pageTemp extends Page{
    public Image endButton;
    public Rectangle end;
    public double circPosition = 0;
    public Image[] animationTest = new Image[4];
    public AnimatedObject timeTest;
    public AnimatedObject delayTest;
    public Rectangle delayRec;
    public GameText text;
    public Text text1;


    public pageTemp(Speak speak){
        super(speak);

    }
    /**
     * initializes the scene
     */
    public void begin(){
        speak.getVars().setReturnPage(speak.getVars().TEMP);
        getAssets();
        //event handler for clicking end button
        getStage().getScene().setOnMouseClicked(new PressEnd());

        //event handler for Esc function
        getStage().getScene().setOnKeyPressed(new PressEsc());

        initialized = true;
    }

    /**
     * initializes assets for the scene
     */
    public void getAssets() {
        endButton = new Image("file:" + getPicDir() + "endButton.png");

        animationTest[0] = new Image("file:" + getPicDir() + "lev1.png");
        animationTest[1] = new Image("file:" + getPicDir() + "lev2.png");
        animationTest[2] = new Image("file:" + getPicDir() + "lev3.png");
        animationTest[3] = new Image("file:" + getPicDir() + "lev4.png");

        timeTest = new AnimatedObject(speak,animationTest, 0.15, true);
        delayTest = new AnimatedObject(speak,animationTest, 0.15, false);

        delayRec = new Rectangle(getWidth() - 200 - animationTest[0].getWidth(), 200, animationTest[0].getWidth(), animationTest[0].getHeight());

        text = new GameText(getTextDir() + "Start.txt");

        text1 = new Text(  getWidth() / 2 - (getWidth() /6), getHeight() / 4,  "");
        getRoot().getChildren().add(text1);

    }

    /**
     * checks for changes in the page
     */
    public void update(){
        getStage().getScene().setOnMouseMoved(new MouseEnter());

        text1.setText(text.getText("three"));
        text1.setWrappingWidth(getWidth() / 3);
        text1.setTextAlignment(TextAlignment.CENTER);
        text1.setFont(Font.font( "Times New Roman", getHeight() / 40 ));


        		//currently the test animation is moving a circle across the screen
		//update circle position
		circPosition += 4;
		if (circPosition > getWidth()){
			circPosition = -100;
		}

        getGC().setFill( Color.BLACK );
		//draw circle
		getGC().fillOval(circPosition,getHeight() /2 ,100 , 100);

		//rectangle to handle click event for end button
		end = new Rectangle((getWidth() / 2) - (endButton.getWidth() / 2) ,
				(getHeight() / 2) + 200, endButton.getWidth(), endButton.getHeight());

		getGC().drawImage(endButton, (getWidth() / 2) - (endButton.getWidth() / 2) ,
				(getHeight() / 2) + 200);

        getGC().drawImage(timeTest.getFrame(), 200, 200);


        getGC().drawImage(delayTest.getFrame(),getWidth() - 200 - animationTest[0].getWidth(), 200);


    }

    /**
     * cleans up and ends the page
     */
    public void end(){
        endButton = null;
        end = null;
        getRoot().getChildren().remove(text1);
        initialized = false;
    }

    /**
     * Handler for pressing End button
     */
    class PressEnd implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent e)
        {
            if ( end.contains( e.getX(), e.getY() ) ){
                getLoop().stop();
                Platform.exit();
            }
        }
    }

    /**
     * Handler for the mouse hovering on items
     *
     */
    class MouseEnter implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent e)
        {
            if (delayRec.contains(e.getX(), e.getY())){
                delayTest.setActive(true);
            } else {
                delayTest.setActive(false);
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

                speak.getVars().setCurrentPage(speak.getVars().MENU_HOME);
                end();
            }
        }
    }
}
