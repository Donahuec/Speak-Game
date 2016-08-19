package Pages;

import Pages.*;
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

/**
 * Created by Caitlin on 1/27/2016.
 */
public class PageBusEntrance extends PageStory {
    private AnimatedObject door;
    private AnimatedObject toll;
    private Rectangle tollRec;
    private String desc;
    private boolean waitTime;
    private boolean walkTime;

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
        Image[] doorArr = new Image[7];
        doorArr[0] = new Image("file:" + getPicDir() + "busentrance" + File.separator + "busdoor_1.png");
        doorArr[1] = new Image("file:" + getPicDir() + "busentrance" + File.separator + "busdoor_2.png");
        doorArr[2] = new Image("file:" + getPicDir() + "busentrance" + File.separator + "busdoor_3.png");
        doorArr[3] = new Image("file:" + getPicDir() + "busentrance" + File.separator + "busdoor_4.png");
        doorArr[4] = new Image("file:" + getPicDir() + "busentrance" + File.separator + "busdoor_5.png");
        
        door = new AnimatedObject(speak, doorArr, 0.07, true, 4, false);
        Image[] tollArr = new Image[7];
        tollArr[0] = new Image("file:" + getPicDir() + "busentrance" + File.separator + "toll_1.png");
        tollArr[1] = new Image("file:" + getPicDir() + "busentrance" + File.separator + "toll_2.png");
        tollArr[2] = new Image("file:" + getPicDir() + "busentrance" + File.separator + "toll_3.png");
        tollArr[3] = new Image("file:" + getPicDir() + "busentrance" + File.separator + "toll_4.png");
        tollArr[4] = tollArr[3];
        tollArr[5] = tollArr[2];
        tollArr[6] = tollArr[1];
        toll = new AnimatedObject(speak, tollArr, 0.1, false, 3, true);
        tollRec = new Rectangle(getWidth()  * 0.6, getHeight() * 0.31, getWidth() * 0.3, getHeight() * 0.7);

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
            changePage(P.BUS_SEAT);
            updateAnxiety(30, 20, 40);
            updateStress(10, 5, 15);
            end();
        } else if (curInteraction == interactions.get("didWait") && choice != 0) {
            changePage(P.START);

            end();
        } else {
            if(choice != 0) {
                float rand = getStats().getRandom();
                if (rand >= 0.8) {
                    curInteraction = interactions.get("panic");
                    isInteraction = true;
                } else if (curInteraction == interactions.get("toll")) {
                    if (choice == 1) {
                        updateAnxiety(-10, -10, 5);
                        changePage(P.BUS_SEAT);
                        end();
                    } else if(choice == 2) {
                        curInteraction = interactions.get("didWait");
                        updateAnxiety(-10, -15, -5);
                        isInteraction = true;
                    } else if (choice == 3) {
                        updateAnxiety(-15, -20, -10);
                        changePage(P.START);
                        end();
                    } else if (choice == 6) {
                        curInteraction = interactions.get("panic");
                        isInteraction = true;
                    }
                }
            }
        }
    }

    @Override
    public void drawImages() {
        getGC().drawImage(toll.getFrame(),getWidth()  * 0.6, getHeight() * 0.31, getWidth() * 0.3, getHeight() * 0.7 );
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
