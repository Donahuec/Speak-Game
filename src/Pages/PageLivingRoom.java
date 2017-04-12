package Pages;

/**
 * Page for the living room. Here you can leave the apartment, do bills, watch tv, or read
 */

import GameObject.AnimatedObject;
import GameObject.GameText;
import GameObject.Interaction;
import GameObject.TextOption;
import GameProcessing.Speak;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.io.File;


public class PageLivingRoom extends PageStory {
    private AnimatedObject bedroomDoor;
    private Rectangle bedroomRec;
    private AnimatedObject kitchenDoor;
    private Rectangle kitchenRec;

    private String roomDesc;
    private String bedroomDoorDesc;
    private String kitchenDoorDesc;

    private boolean timeout;

    private final int DOOR_FRAMES = 7;
    private final int DOOR_PAUSE = 3;
    private final double ANIM_TIME = 0.1;
    private final int[] WALK_TIME = {8, 10};

    private double doorWidth;
    private double doorHeight;
    private double doorY;
    private double bedroomDoorX;
    private double kitchenDoorX;

    public PageLivingRoom(Speak speak) {
        super(speak);
    }

    /**
     * initializes the scene
     */
    public void begin() {
        speak.getVars().setReturnPage(speak.getVars().LIVINGROOM);
        getAssets();
        initialized = true;
    }

    /**
     * initializes assets for the scene
     */
    public void getAssets() {
        doorWidth = getWidth() * 0.13;
        doorHeight = getHeight() * 0.83;
        doorY = getHeight() * 0.07;
        bedroomDoorX = getWidth()  * 0.031;
        kitchenDoorX = getWidth()  * 0.84;

        bg = new Image("file:" + getPicDir() + "livingroom" + File.separator + "livingroom_bg.png", getWidth(), getHeight(), false, true);
        Image[] bedroomDoorArr = new Image[DOOR_FRAMES];
        bedroomDoorArr[0] = new Image("file:" + getPicDir() + "livingroom" + File.separator + "bdoor_1.png");
        bedroomDoorArr[1] = new Image("file:" + getPicDir() + "livingroom" + File.separator + "bdoor_2.png");
        bedroomDoorArr[2] = new Image("file:" + getPicDir() + "livingroom" + File.separator + "bdoor_3.png");
        bedroomDoorArr[3] = new Image("file:" + getPicDir() + "livingroom" + File.separator + "bdoor_4.png");
        bedroomDoorArr[4] = bedroomDoorArr[3];
        bedroomDoorArr[5] = bedroomDoorArr[2];
        bedroomDoorArr[6] = bedroomDoorArr[1];
        bedroomDoor = new AnimatedObject(speak, bedroomDoorArr, ANIM_TIME, false, DOOR_PAUSE, true);
        bedroomRec = new Rectangle(bedroomDoorX, doorY, doorWidth, doorHeight);

        Image[] kitchenDoorArr = new Image[DOOR_FRAMES];
        kitchenDoorArr[0] = new Image("file:" + getPicDir() + "livingroom" + File.separator + "kdoor_1.png");
        kitchenDoorArr[1] = new Image("file:" + getPicDir() + "livingroom" + File.separator + "kdoor_2.png");
        kitchenDoorArr[2] = new Image("file:" + getPicDir() + "livingroom" + File.separator + "kdoor_3.png");
        kitchenDoorArr[3] = new Image("file:" + getPicDir() + "livingroom" + File.separator + "kdoor_4.png");
        kitchenDoorArr[4] = kitchenDoorArr[3];
        kitchenDoorArr[5] = kitchenDoorArr[2];
        kitchenDoorArr[6] = kitchenDoorArr[1];
        kitchenDoor = new AnimatedObject(speak, kitchenDoorArr, ANIM_TIME, false, DOOR_PAUSE, true);
        kitchenRec = new Rectangle(kitchenDoorX, doorY, doorWidth, doorHeight);

        text = new GameText(getTextDir() + "livingroom.xml");
        roomDesc = text.getText("roomDesc");
        bedroomDoorDesc = text.getText("bedroom");
        kitchenDoorDesc = text.getText("hallway");

        //add options to hashmap
        options.put("kitchen", new TextOption( text.getText("kitchen"),text.getText("kitchenDesc"), 0,  this));
        options.put("bus", new TextOption( text.getText("bus"),text.getText("busDesc"), 1,  this));
        options.put("walk", new TextOption( text.getText("walk"),text.getText("walkDesc"), 2,  this));
        TextOption[] arr = { options.get("kitchen"),options.get("bus"), options.get("walk")};

        //add interactions to hashmap
        interactions.put("hallway", new Interaction(this, text.getText("hallChoice") , arr , 0));
        curInteraction = interactions.get("hallway");
        timeout = false;
    }


    /**
     * cleans up and ends the page
     */
    public void end() {
        bedroomDoor = null;
        bedroomRec = null;

        kitchenDoor = null;
        kitchenRec = null;

        roomDesc = null;
        bedroomDoorDesc = null;
        kitchenDoorDesc = null;

        endPage();
    }

    @Override
    public void handleLogic() {
        if (timeCompare(WALK_TIME[0], WALK_TIME[1]) == -1 && timeout == false) {
            interactions.get("hallway").removeLast();
            timeout = true;
        }
        if (choice == 1) {
            //go to kitchen
            changePage(P.KITCHEN);
            end();
        } else if (choice == 2) {
            //take bus
            changePage(P.BUS_ENTRANCE);
            end();
        } else if (choice == 3) {
            //walk to work
            changePage(P.STREET);
            end();
        }
    }

    @Override
    public void drawImages() {
        getGC().drawImage(bedroomDoor.getFrame(), bedroomDoorX, doorY, doorWidth, doorHeight);
        getGC().drawImage(kitchenDoor.getFrame(), kitchenDoorX, doorY, doorWidth, doorHeight);
    }

    @Override
    public void setEventHandlers() {
        getBaseScene().setOnMouseClicked(new MouseClick());
        getBaseScene().setOnKeyPressed(new PressEsc());
        getBaseScene().setOnMouseMoved(new MouseEnter());
    }

    @Override
    public void updateDescription() {
        if (bedroomDoor.isActive()) {
            addDescription(bedroomDoorDesc);
        } else if (kitchenDoor.isActive()){
            addDescription(kitchenDoorDesc);
        } else {
            addDescription(roomDesc);
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
            } else {
                if (bedroomRec.contains(e.getX(), e.getY())) {
                    bedroomDoor.setActive(true);
                } else if (kitchenRec.contains(e.getX(), e.getY())) {
                    kitchenDoor.setActive(true);
                } else {
                    bedroomDoor.setActive(false);
                    bedroomDoor.setPaused(false);
                    kitchenDoor.setActive(false);
                    kitchenDoor.setPaused(false);
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
            }else if (kitchenRec.contains(e.getX(), e.getY()) && !isInteraction){
                curInteraction = interactions.get("hallway");
                isInteraction = true;
            } else {
                if (bedroomRec.contains(e.getX(), e.getY())) {
                    changePage(P.BEDROOM);
                    end();
                }
            }
        }
    }
}
