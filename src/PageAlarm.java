
import javafx.event.EventHandler;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import java.io.File;
import java.util.Iterator;

public class PageAlarm extends PageStory {

    public String alarmDescription;
    public boolean choiceMade;
    public Rectangle alarmClick;
    private boolean timesUp;


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
        options.put("up", new TextOption( text.getText("up"),text.getText("upDesc"), 0,  this));
        options.put("snooze", new TextOption( text.getText("snooze"),text.getText("snoozeDesc"), 1,  this));
        TextOption[] arr = { options.get("up"),options.get("snooze")};

        //add interactions to hashmap
        interactions.put("snooze", new Interaction(this, alarmDescription , arr , 0));

        bg = new Image("file:" + getPicDir() + "alarm" + File.separator + "alarm_bg.png", getWidth(), getHeight(), false, true);

        alarmClick = new Rectangle(getWidth() / 5, getHeight() / 5, (getWidth() / 5) * 3., (getHeight() / 5) * 3  );
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
        if (choice == 2) {
            //snooze alarm
            updateTime(0, 10);
            isInteraction = false;
            curInteraction.clear();
            updateStress(5, 3, 7);
        } else if (choice == 1) {
            //wake up and change scenes
            updateTime(0, 20);
            changePage(P.BEDROOM);
            end();
        }
        if (timeCompare(8, 10) == 1 && !timesUp) {
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
        Font alarmFont = Font.loadFont("file:" + getFontDir() + "LCDMN___.TTF", getHeight() / 2);
        getGC().setFont(alarmFont);
        getGC().setEffect(new GaussianBlur());
        getGC().fillText(getTimeString(), getWidth() / 5, getHeight() / 1.5, (getWidth()/5) * 3);
        getGC().fillText(getTimeString(), getWidth() / 5, getHeight() / 1.5, (getWidth()/5) * 3);
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