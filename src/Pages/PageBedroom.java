package Pages;

/**
 * Page for the bedroom
 */

import GameObject.AnimatedObject;
import GameObject.GameText;
import GameProcessing.Speak;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.io.File;

public class PageBedroom extends PageStory {
    private AnimatedObject livingRoomDoor;
    private Rectangle doorRec;
    private String doorDesc;
    private String roomDesc;

    private final int DOOR_FRAMES = 7;
    private final double FRAME_SPEED = 0.1;
    private final int DOOR_PAUSE_FRAME = 3;

    public PageBedroom(Speak speak) {
        super(speak);
    }

    /**
     * initializes the scene
     */
    public void begin() {
        speak.getVars().setReturnPage(speak.getVars().BEDROOM);
        getAssets();
        initialized = true;
    }

    /**
     * initializes assets for the scene
     */
    public void getAssets() {
        text = new GameText(getTextDir() + "bedroom.xml");
        roomDesc = text.getText("bedroom");
        doorDesc = text.getText("door");

        bg = new Image("file:" + getPicDir() + "bedroom" + File.separator + "bedroom_bg.png", getWidth(), getHeight(), false, true);

        Image[] door = new Image[DOOR_FRAMES];
        door[0] = new Image("file:" + getPicDir() + "bedroom" + File.separator + "door_1.png");
        door[1] = new Image("file:" + getPicDir() + "bedroom" + File.separator + "door_2.png");
        door[2] = new Image("file:" + getPicDir() + "bedroom" + File.separator + "door_3.png");
        door[3] = new Image("file:" + getPicDir() + "bedroom" + File.separator + "door_4.png");
        door[4] = door[3];
        door[5] = door[2];
        door[6] = door[1];
        livingRoomDoor = new AnimatedObject(speak, door, FRAME_SPEED, false, DOOR_PAUSE_FRAME, true);

        double doorX = (getWidth() / 10) * 9;
        double doorY = 0;
        double doorWidth = getWidth() / 10;
        double doorHeight = (getHeight() / 4) * 3;
        doorRec = new Rectangle(doorX, doorY, doorWidth, doorHeight);
    }


    /**
     * cleans up and ends the page
     */
    public void end() {
        livingRoomDoor = null;
        doorRec = null;
        doorDesc = null;
        roomDesc = null;
        endPage();
    }


    @Override
    public void drawImages() {
        double doorX = (getWidth() / 20) * 17;
        double doorY = 0;
        double doorWidth = (getWidth() / 20) * 3;
        double doorHeight = (getHeight() / 4) * 3;
        getGC().drawImage(livingRoomDoor.getFrame(), doorX, doorY, doorWidth, doorHeight);
    }

    @Override
    public void setEventHandlers() {
        getBaseScene().setOnMouseClicked(new MouseClick());
        getBaseScene().setOnKeyPressed(new PressEsc());
        getBaseScene().setOnMouseMoved(new MouseEnter());
    }

    @Override
    public void updateDescription() {
        if (livingRoomDoor.isActive()) {
            //if hovering over living room door
            addDescription(doorDesc);
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
                //hovering over living room door
                if (doorRec.contains(e.getX(), e.getY())) {
                    livingRoomDoor.setActive(true);
                } else {
                    livingRoomDoor.setActive(false);
                    livingRoomDoor.setPaused(false);
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
                if (doorRec.contains(e.getX(), e.getY())) {
                    changePage(P.LIVINGROOM);
                    end();
                }
            }
        }
    }
}
