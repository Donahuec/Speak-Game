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
    }


    /**
     * cleans up and ends the page
     */
    public void end() {
        bdoor = null;
        brec = null;

        endPage();
    }

    @Override
    public void handleLogic() {
        //todo
    }

    @Override
    public void drawImages() {
        getGC().drawImage(bdoor.getFrame(), getWidth()  * 0.031, getHeight() * 0.069, getWidth() * 0.13, getHeight() * 0.83);
    }

    @Override
    public void setEventHandlers() {
        getBaseScene().setOnMouseClicked(new MouseClick());
        getBaseScene().setOnKeyPressed(new PressEsc());
        getBaseScene().setOnMouseMoved(new MouseEnter());
    }

    @Override
    public void updateDescription() {
        //todo
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
                } else {
                    bdoor.setActive(false);
                    bdoor.setPaused(false);
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
                if (brec.contains(e.getX(), e.getY())) {
                    changePage(P.BEDROOM);
                    end();
                }
            }
        }
    }
}
