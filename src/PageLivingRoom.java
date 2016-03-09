import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.io.File;

/**
 * Created by Caitlin on 1/27/2016.
 */
public class PageLivingRoom extends PageStory {
    private AnimatedObject bdoor;
    private Rectangle brec;
    private AnimatedObject kdoor;
    private Rectangle krec;

    private String roomDesc;
    private String bdoorDesc;
    private String kdoorDesc;

    private boolean timeout;

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
        bg = new Image("file:" + getPicDir() + "livingroom" + File.separator + "livingroom_bg.png", getWidth(), getHeight(), false, true);
        Image[] bdoorArr = new Image[7];
        bdoorArr[0] = new Image("file:" + getPicDir() + "livingroom" + File.separator + "bdoor_1.png");
        bdoorArr[1] = new Image("file:" + getPicDir() + "livingroom" + File.separator + "bdoor_2.png");
        bdoorArr[2] = new Image("file:" + getPicDir() + "livingroom" + File.separator + "bdoor_3.png");
        bdoorArr[3] = new Image("file:" + getPicDir() + "livingroom" + File.separator + "bdoor_4.png");
        bdoorArr[4] = bdoorArr[3];
        bdoorArr[5] = bdoorArr[2];
        bdoorArr[6] = bdoorArr[1];
        bdoor = new AnimatedObject(speak, bdoorArr, 0.1, false, 3, true);
        brec = new Rectangle(getWidth()  * 0.056, getHeight() * 0.07, getWidth() / 10, (getHeight() / 10) * 8);

        Image[] kdoorArr = new Image[7];
        kdoorArr[0] = new Image("file:" + getPicDir() + "livingroom" + File.separator + "kdoor_1.png");
        kdoorArr[1] = new Image("file:" + getPicDir() + "livingroom" + File.separator + "kdoor_2.png");
        kdoorArr[2] = new Image("file:" + getPicDir() + "livingroom" + File.separator + "kdoor_3.png");
        kdoorArr[3] = new Image("file:" + getPicDir() + "livingroom" + File.separator + "kdoor_4.png");
        kdoorArr[4] = kdoorArr[3];
        kdoorArr[5] = kdoorArr[2];
        kdoorArr[6] = kdoorArr[1];
        kdoor = new AnimatedObject(speak, kdoorArr, 0.1, false, 3, true);
        krec = new Rectangle(getWidth()  * 0.83, getHeight() * 0.075, getWidth() / 10, (getHeight() / 10) * 8);

        text = new GameText(getTextDir() + "livingroom.xml");
        roomDesc = text.getText("roomDesc");
        bdoorDesc = text.getText("bedroom");
        kdoorDesc = text.getText("hallway");

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
        bdoor = null;
        brec = null;

        kdoor = null;
        krec = null;

        roomDesc = null;
        bdoorDesc = null;
        kdoorDesc = null;

        endPage();
    }

    @Override
    public void handleLogic() {
        if (timeCompare(8, 10) == 1 && timeout == false) {
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
        getGC().drawImage(bdoor.getFrame(), getWidth()  * 0.031, getHeight() * 0.069, getWidth() * 0.13, getHeight() * 0.83);
        getGC().drawImage(kdoor.getFrame(), getWidth()  * 0.84, getHeight() * 0.067, getWidth() * 0.13, getHeight() * 0.83);
    }

    @Override
    public void setEventHandlers() {
        getBaseScene().setOnMouseClicked(new MouseClick());
        getBaseScene().setOnKeyPressed(new PressEsc());
        getBaseScene().setOnMouseMoved(new MouseEnter());
    }

    @Override
    public void updateDescription() {
        if (bdoor.isActive()) {
            addDescription(bdoorDesc);
        } else if (kdoor.isActive()){
            addDescription(kdoorDesc);
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
                if (brec.contains(e.getX(), e.getY())) {
                    bdoor.setActive(true);
                } else if (krec.contains(e.getX(), e.getY())) {
                    kdoor.setActive(true);
                } else {
                    bdoor.setActive(false);
                    bdoor.setPaused(false);
                    kdoor.setActive(false);
                    kdoor.setPaused(false);
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
            }else if (krec.contains(e.getX(), e.getY()) && !isInteraction){
                curInteraction = interactions.get("hallway");
                isInteraction = true;
            } else {
                if (brec.contains(e.getX(), e.getY())) {
                    changePage(P.BEDROOM);
                    end();
                }
            }
        }
    }
}
