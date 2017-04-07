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
        girl = new Image("file:" + getPicDir() + "bus" + File.separator + "neighbor_sit.png", getWidth() *.6, getHeight(), false, true);
        isGirl = false;
        walkTime = false;

        text = new GameText(getTextDir() + "busSeat.xml");
        String cont = text.getText("continue");

        options.put("first", new TextOption( cont, text.getText("firstDesc"), 0,  this));
        options.put("second", new TextOption( cont, text.getText("secondDesc"), 0,  this));
        options.put("yes", new TextOption( text.getText("yes"),text.getText("yesDesc"), 0,  this));
        options.put("no", new TextOption( text.getText("no"),text.getText("noDesc"), 1,  this));
        options.put("leave", new TextOption( text.getText("leave"),text.getText("leaveDesc"), 2,  this));

        options.put("yesRes", new TextOption( cont,text.getText("yesRes"), 0,  this));
        options.put("yesResTwo", new TextOption( cont,text.getText("yesRes2"), 0,  this));
        options.put("noRes", new TextOption( cont,text.getText("noRes"), 0,  this));
        options.put("noResTwo", new TextOption( cont,text.getText("noRes2"), 0,  this));
        options.put("leaveRes", new TextOption( cont,text.getText("leaveRes"), 0,  this));
        options.put("panicRes", new TextOption( cont,text.getText("panicRes"), 0,  this));

        TextOption[] firstArr = {options.get("first")};
        TextOption[] secondArr = {options.get("second")};
        TextOption[] choiceArr = {options.get("yes"), options.get("no"), options.get("leave")};
        TextOption[] yesArr = {options.get("yesRes")};
        TextOption[] yesTwoArr = {options.get("yesResTwo")};
        TextOption[] noArr = {options.get("noRes")};
        TextOption[] noTwoArr = {options.get("noResTwo")};
        TextOption[] leaveArr = {options.get("leaveRes")};
        TextOption[] panicArr = {options.get("panicRes")};

        interactions.put("first", new Interaction(this, text.getText("firstDesc") , firstArr , 0));
        interactions.put("second", new Interaction(this, text.getText("secondDesc") , secondArr , 0));
        interactions.put("choice", new Interaction(this, text.getText("secondDesc") , choiceArr , 3));
        interactions.put("yes", new Interaction(this, text.getText("yesRes") , yesArr , 0));
        interactions.put("yesTwo", new Interaction(this, text.getText("yesResTwo") , yesTwoArr , 0));
        interactions.put("no", new Interaction(this, text.getText("noRes") , noArr , 0));
        interactions.put("noTwo", new Interaction(this, text.getText("noRes2") , noTwoArr , 0));
        interactions.put("leave", new Interaction(this, text.getText("leaveRes") , leaveArr , 0));
        interactions.put("panic", new Interaction(this, text.getText("panicRes") , panicArr , 0));
        curInteraction = interactions.get("first");
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
        if (curInteraction == interactions.get("first") && choice != 0) {
            updateTime(0, 10);
            curInteraction = interactions.get("second");
            isGirl = true;
        } else if (curInteraction == interactions.get("second") && choice != 0) {
            curInteraction = interactions.get("choice");
        } else if (curInteraction == interactions.get("choice")) {
            float num = getRandom();
            if (choice == 1) {
                if (num < 0.8) {
                    curInteraction = interactions.get("yes");
                } else {
                    curInteraction = interactions.get("yesTwo");
                    updateAnxiety(15, 10, 20);
                }
            } else if (choice == 2) {
                if (num < 0.8) {
                    curInteraction = interactions.get("noTwo");
                    isGirl = false;
                    updateAnxiety(-5, -10, 0);
                } else {
                    curInteraction = interactions.get("no");
                    isGirl = false;
                    updateAnxiety(10, 5, 15);
                }
            } else if (choice == 3) {
                curInteraction = interactions.get("leave");
                updateAnxiety(-10, -10, -5);
            } else if (choice == 6) {
                curInteraction = interactions.get("panic");
                updateAnxiety(25, 15, 30);
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
