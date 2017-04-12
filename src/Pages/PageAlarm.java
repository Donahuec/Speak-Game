package Pages;

/**
 * Page for waking up to the alarm
 */

import GameObject.GameText;
import GameObject.Interaction;
import GameObject.TextOption;
import GameProcessing.Speak;
import javafx.event.EventHandler;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;

import java.io.File;

public class PageAlarm extends PageStory {

    private String alarmDescription;
    private boolean choiceMade;
    private Rectangle alarmClick;
    private boolean timesUp;

    private final int[] SNOOZE_TIME = {0, 10};
    private final int[] SNOOZE_STRESS = {5, 3, 7};
    private final int[] WAKE_TIME = {0,20};
    private final int[] MAX_SNOOZE_TIME = {8, 10};

    private final int WAKE = 0;
    private final int SNOOZE = 1;


    public PageAlarm(Speak speak){
        super(speak);
        choiceMade = false;
    }

    /**
     * initializes the scene
     */
    public void begin() {
        speak.getVars().setReturnPage(speak.getVars().ALARM);
        getAssets();
        initialized = true;
        timesUp = false;
        isInteraction = false;
    }

    /**
     * initializes assets for the scene
     */
    public void getAssets() {
        text = new GameText(getTextDir() + "alarm.xml");

        alarmDescription = text.getText("alarmDesc");

        //add options to hashmap
        options.put("up", new TextOption( text.getText("up"),text.getText("upDesc"), WAKE,  this));
        options.put("snooze", new TextOption( text.getText("snooze"),text.getText("snoozeDesc"), SNOOZE,  this));

        TextOption[] options = { this.options.get("up"), this.options.get("snooze")};

        //add interactions to hashmap
        interactions.put("snooze", new Interaction(this, alarmDescription , options , 0));

        String alarmPath = "file:" + getPicDir() + "alarm" + File.separator + "alarm_bg.png";
        bg = new Image(alarmPath, getWidth(), getHeight(), false, true);

        double alarmWidth = (getWidth() / 5) * 3.0;
        double alarmHeight = (getHeight() / 5) * 3.0;
        double alarmX = getWidth() / 5;
        double alarmY = getHeight() / 5;

        alarmClick = new Rectangle(alarmX, alarmY, alarmWidth, alarmHeight);
    }

    /**
     * cleans up and ends the page
     */
    public void end() {
        text = null;
        alarmDescription = null;
        bg = null;
        choiceMade = false;
        alarmClick = null;
        endPage();
    }

    @Override
    public void handleLogic() {
        if (choice == SNOOZE) {
            //snooze alarm, takes 10 minutes
            updateTime(SNOOZE_TIME[0], SNOOZE_TIME[1]);
            isInteraction = false;
            curInteraction.clear();
            updateStress(SNOOZE_STRESS[0], SNOOZE_STRESS[1], SNOOZE_STRESS[2]);
        } else if (choice == WAKE) {
            //wake up and change scenes
            updateTime(WAKE_TIME[0], WAKE_TIME[1]);
            changePage(P.BEDROOM);
            end();
        }
        //if past 8:10 you need to get up
        if (timeCompare(MAX_SNOOZE_TIME[0], MAX_SNOOZE_TIME[1]) == -1 && !timesUp) {
            System.out.println("here");
            curInteraction.removeLast();
            options.get("up").setDescription(text.getText("timeout"));
            alarmDescription = text.getText("timeout");
            timesUp = true;
        }
    }

    @Override
    public void drawImages() {
        //draw time on alarm
        getGC().setFill( Color.RED );

        double fontSize = getHeight() / 2;
        double timeX = getWidth() / 5;
        double timeY = getHeight() / 1.5;
        double timeMaxWidth = (getWidth()/5) * 3;

        Font alarmFont = Font.loadFont("file:" + getFontDir() + "LCDMN___.TTF", fontSize);
        getGC().setFont(alarmFont);
        getGC().setEffect(new GaussianBlur());
        getGC().fillText(getTimeString(), timeX, timeY, timeMaxWidth);
        getGC().fillText(getTimeString(), timeX, timeY, timeMaxWidth);
        getGC().setEffect(null);
    }

    @Override
    public void setEventHandlers() {
        //add event handlers
        getBaseScene().setOnMouseClicked(new MouseClick());
        getBaseScene().setOnKeyPressed(new PressEsc());
        getBaseScene().setOnMouseMoved(new MouseEnter());
    }

    @Override
    public void updateDescription() {
        if(!isInteraction) {
            addDescription(alarmDescription);
        }
    }

    /**
     * Handler for the mouse hovering on items
     *
     */
    class MouseEnter implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            if (isInteraction) {
                getInteractionHover(e);
            }
        }
    }

    /**
     * Handler for pressing End button
     */
    class MouseClick implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent e)
        {
            if (isInteraction) {
                getInteractionChoice(e);
            } else if (alarmClick.contains(e.getX(), e.getY()) && !isInteraction){
                curInteraction = interactions.get("snooze");
                isInteraction = true;
            }

        }
    }
}