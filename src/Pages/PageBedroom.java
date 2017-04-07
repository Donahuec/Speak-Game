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
        Image[] door = new Image[7];
        door[0] = new Image("file:" + getPicDir() + "bedroom" + File.separator + "door_1.png");
        door[1] = new Image("file:" + getPicDir() + "bedroom" + File.separator + "door_2.png");
        door[2] = new Image("file:" + getPicDir() + "bedroom" + File.separator + "door_3.png");
        door[3] = new Image("file:" + getPicDir() + "bedroom" + File.separator + "door_4.png");
        door[4] = door[3];
        door[5] = door[2];
        door[6] = door[1];
        livingRoomDoor = new AnimatedObject(speak, door, 0.1, false, 3, true);
        doorRec = new Rectangle((getWidth() / 10) * 9, 0, getWidth() / 10, (getHeight() / 4) * 3);
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
        getGC().drawImage(livingRoomDoor.getFrame(), (getWidth() / 20) * 17, 0, (getWidth() / 20) * 3, (getHeight() / 4) * 3);
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
