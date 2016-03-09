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
    }


    /**
     * cleans up and ends the page
     */
    public void end() {
    }

    @Override
    public void handleLogic() {
    }

    @Override
    public void drawImages() {
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
