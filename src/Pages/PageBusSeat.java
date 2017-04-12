package Pages;

/**
 * Page for sitting down on the bus
 */

import GameObject.GameText;
import GameObject.Interaction;
import GameObject.TextOption;
import GameProcessing.Speak;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;

import java.io.File;


public class PageBusSeat extends PageStory {
    private Image girl;
    private boolean isGirl;
    private boolean walkTime;

    private final int[] BUS_TIME = {0, 10};
    private final double PANIC_CHANCE = 0.8;
    private final int[] NO_ANXIETY = {10, 5, 15};
    private final int[] NO_PANIC_ANXIETY = {-5, -10, 0};
    private final int[] YES_ANXIETY = {0, 0, 5};
    private final int[] YES_PANIC_ANXIETY = {15, 10, 20};
    private final int[] PANIC_ANXIETY = {25, 15, 30};
    private final int[] LEAVE_ANXIETY = {-10, -10, -5};



    public PageBusSeat(Speak speak) {
        super(speak);
    }

    /**
     * initializes the scene
     */
    public void begin() {
        speak.getVars().setReturnPage(speak.getVars().BUS_SEAT);
        getAssets();
        initialized = true;
    }

    /**
     * initializes assets for the scene
     */
    public void getAssets() {
        bg = new Image("file:" + getPicDir() + "bus" + File.separator + "bus.png", getWidth(), getHeight(), false, true);
        double girlWidth = getWidth() * 0.6;
        girl = new Image("file:" + getPicDir() + "bus" + File.separator + "neighbor_sit.png", girlWidth, getHeight(), false, true);
        isGirl = false;
        walkTime = false;

        text = new GameText(getTextDir() + "busSeat.xml");
        String cont = text.getText("continue");

        options.put("sit", new TextOption( cont, text.getText("sitDownDesc"), 0,  this));
        options.put("secondStop", new TextOption( cont, text.getText("secondStopDesc"), 0,  this));
        options.put("yes", new TextOption( text.getText("yes"),text.getText("yesDesc"), 0,  this));
        options.put("no", new TextOption( text.getText("no"),text.getText("noDesc"), 1,  this));
        options.put("leave", new TextOption( text.getText("leave"),text.getText("leaveDesc"), 2,  this));

        options.put("yesRes", new TextOption( cont,text.getText("yesRes"), 0,  this));
        options.put("yesResPanic", new TextOption( cont,text.getText("yesResPanic"), 0,  this));
        options.put("noRes", new TextOption( cont,text.getText("noRes"), 0,  this));
        options.put("noResPanic", new TextOption( cont,text.getText("noResPanic"), 0,  this));
        options.put("leaveRes", new TextOption( cont,text.getText("leaveRes"), 0,  this));
        options.put("panicRes", new TextOption( cont,text.getText("panicRes"), 0,  this));

        TextOption[] firstArr = {options.get("sit")};
        TextOption[] secondArr = {options.get("secondStop")};
        TextOption[] choiceArr = {options.get("yes"), options.get("no"), options.get("leave")};
        TextOption[] yesArr = {options.get("yesRes")};
        TextOption[] yesTwoArr = {options.get("yesResPanic")};
        TextOption[] noArr = {options.get("noRes")};
        TextOption[] noTwoArr = {options.get("noResPanic")};
        TextOption[] leaveArr = {options.get("leaveRes")};
        TextOption[] panicArr = {options.get("panicRes")};

        interactions.put("sit", new Interaction(this, text.getText("sitDownDesc") , firstArr , 0));
        interactions.put("secondStop", new Interaction(this, text.getText("secondStopDesc") , secondArr , 0));
        interactions.put("choice", new Interaction(this, text.getText("secondStopDesc") , choiceArr , 3));
        interactions.put("yes", new Interaction(this, text.getText("yesRes") , yesArr , 0));
        interactions.put("yesPanic", new Interaction(this, text.getText("yesResPanic") , yesTwoArr , 0));
        interactions.put("no", new Interaction(this, text.getText("noRes") , noArr , 0));
        interactions.put("noPanic", new Interaction(this, text.getText("noResPanic") , noTwoArr , 0));
        interactions.put("leave", new Interaction(this, text.getText("leaveRes") , leaveArr , 0));
        interactions.put("panic", new Interaction(this, text.getText("panicRes") , panicArr , 0));
        curInteraction = interactions.get("sit");
        isInteraction = true;
    }


    /**
     * cleans up and ends the page
     */
    public void end() {
        girl = null;
        endPage();
    }

    @Override
    //TODO Update with clearer passage names
    public void handleLogic() {
        if (curInteraction == interactions.get("sit") && choice != 0) {
            updateTime(BUS_TIME[0], BUS_TIME[1]);
            curInteraction = interactions.get("secondStop");
            isGirl = true;
        } else if (curInteraction == interactions.get("secondStop") && choice != 0) {
            curInteraction = interactions.get("choice");
        } else if (curInteraction == interactions.get("choice")) {
            float num = getRandom();
            if (choice == 1) {
                if (num < PANIC_CHANCE) {
                    curInteraction = interactions.get("yes");
                    updateAnxiety(YES_ANXIETY[0], YES_ANXIETY[1], YES_ANXIETY[2]);
                } else {
                    curInteraction = interactions.get("yesPanic");
                    updateAnxiety(YES_PANIC_ANXIETY[0], YES_PANIC_ANXIETY[1], YES_PANIC_ANXIETY[2]);
                }
            } else if (choice == 2) {
                if (num < PANIC_CHANCE) {
                    curInteraction = interactions.get("no");
                    isGirl = false;
                    updateAnxiety(NO_ANXIETY[0], NO_ANXIETY[1], NO_ANXIETY[2]);
                } else {
                    curInteraction = interactions.get("noPanic");
                    isGirl = false;
                    updateAnxiety(NO_PANIC_ANXIETY[0], NO_PANIC_ANXIETY[1], NO_PANIC_ANXIETY[2]);
                }
            } else if (choice == 3) {
                curInteraction = interactions.get("leave");
                updateAnxiety(LEAVE_ANXIETY[0], LEAVE_ANXIETY[1], LEAVE_ANXIETY[2]);
            } else if (choice == PANIC) {
                curInteraction = interactions.get("panic");
                updateAnxiety(PANIC_ANXIETY[0], PANIC_ANXIETY[1], PANIC_ANXIETY[2]);
            }
        } else {
            if (choice != 0) {
                changePage(P.START);
                end();
            }
        }
        isInteraction = true;
    }

    @Override
    public void drawImages() {
        if (isGirl) {
            getGC().drawImage(girl, 0, 0);
        }
    }

    @Override
    public void setEventHandlers() {
        getBaseScene().setOnMouseClicked(new MouseClick());
        getBaseScene().setOnKeyPressed(new PageStory.PressEsc());
        getBaseScene().setOnMouseMoved(new MouseEnter());
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
            }
        }
    }
}
