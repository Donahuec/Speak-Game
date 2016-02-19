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

/**
 * Created by Caitlin on 1/29/2016.
 */
abstract class PageStory extends Page {
    public boolean isInteraction;
    public Interaction curInteraction;
    //whether we are hovering over an option
    public int hover;
    //have we made a choice this frame?
    public int choice;
    //dimensions of the area containing options
    private double width;
    private double height;
    private double x;
    private double y;
    //rectangles that hold choices
    private Rectangle[] choices;

    //the current description
    private Text description;
    //have we drawn the description rectangle this frame?
    private boolean descrec;
    public Image bg;
    //variables to have the HUD increment Slowly
    private static int curAnxiety = 50;
    private static int curStress = 10;


    public PageStory(Speak speak) {
        super(speak);
        isInteraction = false;
        hover = 0;
        choice = 0;

        //dimensions for options rectangle
        width = (speak.getGameStage().getWidth() / 2);
        height = (speak.getGameStage().getHeight()/ 2);
        x = speak.getGameStage().getWidth() / 4;
        y = speak.getGameStage().getHeight() / 5;

        choices = new Rectangle[5];

        //choice sections
        choices[0] = new Rectangle(x, y, width, height / 7);
        choices[1] = new Rectangle(x, y + (0.5 * y), width, height / 7);
        choices[2] = new Rectangle(x, y + y, width, height / 7);
        choices[3] = new Rectangle(x, y + (1.5 * y), width, height / 7);
        choices[4] = new Rectangle(x, y + (2 * y), width, height / 7);

        //text object for the Description
        description = new Text(  30, getHeight() - (getHeight()/ 6) + 30,  "");
        description.setWrappingWidth(getWidth() - 60);
        description.setTextAlignment(TextAlignment.CENTER);
        description.setFont(Font.font( "Times New Roman", getHeight() / 60 ));
        getRoot().getChildren().add(description);

        //rectangle to see if description rectangle has been drawn this frame
        descrec = false;


    }

    /**
     * draw the background
     */
    public void drawbg(){
        getGC().drawImage(bg, 0, 0);
    }

    /**
     * Cleanup function to clear variables at end of frame
     */
    public void cleanup() {
        choice = 0;
        descrec = false;
    }

    public void drawAnxietyBar() {
        //draw background
        getGC().setFill(Color.DARKGRAY);
        getGC().setGlobalAlpha(0.8);
        getGC().fillRoundRect(10, 5, 9 *( getWidth() / 10), getHeight() / 80, 15, 15);

        //draw fill
        getGC().setGlobalAlpha(1.0);
        getGC().setFill(Color.CADETBLUE);
        getGC().fillRoundRect(10, 5, (9 *( getWidth() / 10)) * (curAnxiety / 200.0), getHeight() / 80, 15, 15);
        getGC().setFill(Color.WHITE);
        getGC().fillRoundRect(15, 8, ((9 *( getWidth() / 10)) * (curAnxiety / 200.0)) - 10, 2, 15, 15);
        
        if (curAnxiety < getAnxiety()) {
            curAnxiety += 1;
        } else if (curAnxiety > getAnxiety()) {
            curAnxiety -= 1;
        } 
        if (curAnxiety == getAnxiety() -1 || curAnxiety == getAnxiety() + 1) {
            curAnxiety = getAnxiety();
        }
        

        //draw outline
        getGC().setLineWidth(1.0);
        getGC().setStroke(Color.BLACK);
        getGC().strokeRoundRect(10.5, 5.5, 9 *(getWidth() / 10), getHeight() / 80, 15, 15);


    }

    public void drawStressBar() {
        //draw background
        getGC().setFill(Color.DARKGRAY);
        getGC().setGlobalAlpha(0.8);
        getGC().fillRoundRect(10, 10 + (getHeight() / 80), getWidth() / 2, getHeight() / 80, 15, 15);
        //draw fill
        getGC().setGlobalAlpha(1.0);
        getGC().setFill(Color.CADETBLUE);
        getGC().fillRoundRect(10, 10 + (getHeight() / 80),  getWidth() / 2 * (curStress / 50.0), getHeight() / 80, 15, 15);
        getGC().setFill(Color.WHITE);
        getGC().fillRoundRect(15, 13 + (getHeight() / 80), getWidth() / 2 * (curStress / 50.0) - 10, 2, 15, 15);

        if (curStress < getStress()) {
            curStress += 1;
        } else if (curStress > getStress()) {
            curStress -= 1;
        }
        if (curStress == getStress() -1 || curStress == getStress() + 1) {
            curStress = getStress();
        }
        
        //draw outline
        getGC().setLineWidth(1.0);
        getGC().setStroke(Color.BLACK);
        getGC().strokeRoundRect(10.5, 10.5 + (getHeight() / 80), getWidth() / 2, getHeight() / 80, 15, 15);
    }

    public void drawTime() {
        //draw background
        getGC().setFill(Color.LIGHTGRAY);
        getGC().setGlobalAlpha(.5);
        getGC().fillRect(0, 0, getWidth(), (getHeight() / 40) + 15 );

        //reset global alpha
        getGC().setGlobalAlpha(1.0);

        //draw time
        getGC().setFill( Color.BLACK );
        getGC().setLineWidth(2);
        Font timeFont = Font.font(getHeight() / 40);
        getGC().setFont(timeFont);
        getGC().fillText( getTimeString(), (getWidth() / 14) * 13, getHeight() / 40 );
    }

    public void drawHUD() {
        drawTime();
        drawAnxietyBar();
        drawStressBar();
    }

    /**
     * sets the description text to an empty string
     * so that it is no longer visible
     * @return
     */
    public void clearDescription() {
        description.setText("");
    }

    /**
     * processes interactions for this frame
     */
    public void handleInteractions() {
        if (isInteraction && curInteraction != null) {
            curInteraction.process(getLoop().curTime);
        }
    }

    /**
     * Draws the rectangle to hold the description text
     */
    public void drawDescriptionSquare() {
        //check if the rectangle has already been drawn this scene
        if (!descrec){
            getGC().setFill(Color.AQUAMARINE);
            getGC().setStroke(Color.BLACK);
            getGC().setLineWidth(1);
            //opacity
            getGC().setGlobalAlpha(0.5);
            getGC().fillRoundRect(10, getHeight() - (getHeight()/ 6), getWidth() - 20, getHeight() / 7, 15, 15);
            getGC().strokeRoundRect(10, getHeight() - (getHeight()/ 6), getWidth() - 20, getHeight() / 7, 15, 15);
            getGC().setGlobalAlpha(1.0);
            descrec = true;
        }

    }

    /**
     * changes the description to str
     * @param str
     */
    public void addDescription(String str) {
        drawDescriptionSquare();
        description.setText(str);
    }


    /**
     * method to add to eventhandler that gets what choice (if any) has been clicked
     * @param e
     */
    public void getInteractionChoice(MouseEvent e) {
        for (int i = 0; i < curInteraction.getLength(); i++){
            if ( choices[i].contains( e.getX(), e.getY() )  ){
                choice = i + 1;
                isInteraction = false;
                curInteraction.clear();
            }
        }
    }

    /**
     * method to be called by eventhandler to get what choice is being hovered over
     * @param e
     */
    public void getInteractionHover(MouseEvent e) {
        int tempHover = 0;

        for (int i = 0; i < choices.length; i ++) {
            if ( choices[i].contains( e.getX(), e.getY() ) ){
                tempHover = i + 1;
            }
        }

        hover = tempHover;

    }

    public boolean isInteraction() {
        return isInteraction;
    }

    public int getChoice() {
        return choice;
    }

    public int getHover() {
        return hover;
    }

    public Interaction getCurInteraction() {
        return curInteraction;
    }

    public void setChoice(int choice) {
        this.choice = choice;
    }

    public void setHover(int hover) {
        this.hover = hover;
    }

    /**
     * checks for changes in the page
     */
    public void update() {
        setEventHandlers();
        drawbg();
        updateDescription();
        drawImages();
        handleInteractions();
        handleLogic();
        drawHUD();
        cleanup();

    }


    /**
     * initializes the scene
     */
    abstract void begin();

    /**
     * initializes assets for the scene
     */
    abstract void getAssets();


    /**
     * cleans up and ends the page
     */
    abstract void end();

    /**
     * Handle the logic for the update
     */
    abstract void handleLogic();

    /**
     * draw the images for the page
     */
    abstract void drawImages();

    /**
     * set event handlers for the page
     */
    abstract void setEventHandlers();

    /**
     * update the description for the page
     */
    abstract void updateDescription();

    /**
     * Handler for pressing Esc button
     */
    class PressEsc implements EventHandler<KeyEvent> {
        @Override
        public void handle(KeyEvent event) {
            if (event.getCode() == KeyCode.ESCAPE && !isInteraction){
                clearDescription();
                speak.getVars().setCurrentPage(speak.getVars().MENU_HOME);
                end();
            }
        }
    }

}
