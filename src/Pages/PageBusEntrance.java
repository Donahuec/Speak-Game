package Pages;

/**
 * Page for getting on the bus
 */

import GameObject.*;
import GameProcessing.*;
import GameObject.AnimatedObject;
import GameObject.GameText;
import GameObject.TextOption;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;
import java.io.File;

public class PageBusEntrance extends PageStory {
    private AnimatedObject door;
    private AnimatedObject toll;
    private Rectangle tollRec;
    private String desc;
    private boolean waitTime;
    private boolean walkTime;

    private double tollX;
    private double tollY;
    private double tollWidth;
    private double tollHeight;

    private int DOOR_ANIM_FRAMES = 5;
    private int TOLL_ANIM_FRAMES = 7;
    private double ANIM_TIME = 0.1;
    private int TOLL_PAUSE = 3;
    private int BOARD = 1;
    private int WAIT = 2;
    private int WALK = 3;
    private double PANIC_CHANCE = 0.8;
    private int[] PANIC_ANXIETY = {30, 20, 40};
    private int[] PANIC_STRESS = {10, 5, 15};
    private int[] BOARD_ANXIETY = {-10, -10, 5};
    private int[] WAIT_ANXIETY = {-10, -15, -5};
    private int[] WALK_ANXIETY = {-15, -20, -10};


    public PageBusEntrance(Speak speak) {
        super(speak);
    }

    /**
     * initializes the scene
     */
    public void begin() {
        speak.getVars().setReturnPage(speak.getVars().BUS_ENTRANCE);
        getAssets();
        initialized = true;
    }

    /**
     * initializes assets for the scene
     */
    public void getAssets() {
        bg = new Image("file:" + getPicDir() + "busentrance" + File.separator + "busentrance_bg.png", getWidth(), getHeight(), false, true);
        Image[] doorArr = new Image[DOOR_ANIM_FRAMES];
        doorArr[0] = new Image("file:" + getPicDir() + "busentrance" + File.separator + "busdoor_1.png");
        doorArr[1] = new Image("file:" + getPicDir() + "busentrance" + File.separator + "busdoor_2.png");
        doorArr[2] = new Image("file:" + getPicDir() + "busentrance" + File.separator + "busdoor_3.png");
        doorArr[3] = new Image("file:" + getPicDir() + "busentrance" + File.separator + "busdoor_4.png");
        doorArr[4] = new Image("file:" + getPicDir() + "busentrance" + File.separator + "busdoor_5.png");
        
        door = new AnimatedObject(speak, doorArr, ANIM_TIME, true, DOOR_ANIM_FRAMES - 1, false);

        Image[] tollArr = new Image[TOLL_ANIM_FRAMES];
        tollArr[0] = new Image("file:" + getPicDir() + "busentrance" + File.separator + "toll_1.png");
        tollArr[1] = new Image("file:" + getPicDir() + "busentrance" + File.separator + "toll_2.png");
        tollArr[2] = new Image("file:" + getPicDir() + "busentrance" + File.separator + "toll_3.png");
        tollArr[3] = new Image("file:" + getPicDir() + "busentrance" + File.separator + "toll_4.png");
        tollArr[4] = tollArr[3];
        tollArr[5] = tollArr[2];
        tollArr[6] = tollArr[1];
        toll = new AnimatedObject(speak, tollArr, ANIM_TIME, false, TOLL_PAUSE, true);

        tollX = getWidth() * 0.6;
        tollY = getHeight() * 0.31;
        tollWidth = getWidth() * 0.3;
        tollHeight = getHeight() * 0.7;

        tollRec = new Rectangle(tollX, tollY, tollWidth, tollHeight);

        text = new GameText(getTextDir() + "busEntrance.xml");
        desc = text.getText("mainDesc");

        options.put("pay", new TextOption( text.getText("pay"), desc, 0,  this));
        options.put("wait", new TextOption( text.getText("wait"), desc, 1,  this));
        options.put("walk", new TextOption( text.getText("walk"), desc, 2,  this));

        TextOption[] arr = {options.get("pay"), options.get("wait"), options.get("walk")};

        interactions.put("toll", new Interaction(this, desc , arr , 3));
        curInteraction = interactions.get("toll");

        waitTime = false;
        walkTime = false;

        options.put("panic", new TextOption(text.getText("continue"), text.getText("panic"), 0, this));

        TextOption[] arrPanic = {options.get("panic")};
        interactions.put("panic", new Interaction(this, text.getText("panic"), arrPanic, 0));

        options.put("didWait", new TextOption(text.getText("continue"), text.getText("didWait"), 0, this));
        TextOption[] arrWait = {options.get("didWait")};
        interactions.put("didWait", new Interaction(this, text.getText("didWait"), arrWait, 0));
    }


    /**
     * cleans up and ends the page
     */
    public void end() {
        endPage();
    }

    @Override
    public void handleLogic() {
        if (curInteraction == interactions.get("panic") && choice != 0){
            //panic and stressfully get on the bus
            changePage(P.BUS_SEAT);
            updateAnxiety(PANIC_ANXIETY[0], PANIC_ANXIETY[1], PANIC_ANXIETY[2]);
            updateStress(PANIC_STRESS[0], PANIC_STRESS[1], PANIC_STRESS[2]);
            end();
        } else if (curInteraction == interactions.get("didWait") && choice != 0) {
            //TODO not yet implemented
            changePage(P.START);
            end();
        } else {
            if(choice != 0) {
                float rand = getStats().getRandom();
                if (rand >= PANIC_CHANCE) {
                    curInteraction = interactions.get("panic");
                    isInteraction = true;
                } else if (curInteraction == interactions.get("toll")) {
                    if (choice == BOARD) {
                        updateAnxiety(BOARD_ANXIETY[0], BOARD_ANXIETY[1], BOARD_ANXIETY[2]);
                        changePage(P.BUS_SEAT);
                        end();
                    } else if(choice == WAIT) {
                        curInteraction = interactions.get("didWait");
                        updateAnxiety(WAIT_ANXIETY[0], WAIT_ANXIETY[1], WAIT_ANXIETY[2]);
                        isInteraction = true;
                    } else if (choice == WALK) {
                        updateAnxiety(WALK_ANXIETY[0], WALK_ANXIETY[1], WALK_ANXIETY[2]);
                        changePage(P.START);
                        end();
                    } else if (choice == PANIC) {
                        curInteraction = interactions.get("panic");
                        isInteraction = true;
                    }
                }
            }
        }
    }

    @Override
    public void drawImages() {
        getGC().drawImage(toll.getFrame(), tollX, tollY, tollWidth, tollHeight);
        getGC().drawImage(door.getFrame(), 0, 0, getWidth(), getHeight());
    }


    @Override
    public void setEventHandlers() {
        getBaseScene().setOnMouseClicked(new MouseClick());
        getBaseScene().setOnKeyPressed(new PressEsc());
        getBaseScene().setOnMouseMoved(new MouseEnter());
    }

    @Override
    public void updateDescription() {
        addDescription(desc);
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
            } else {
                if (tollRec.contains(e.getX(), e.getY())) {
                    toll.setActive(true);
                } else {
                    toll.setActive(false);
                    toll.setPaused(false);
                }
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
            } else {
                if (tollRec.contains(e.getX(), e.getY())) {
                    curInteraction = interactions.get("toll");
                    isInteraction = true;
                }
            }
        }
    }
}
