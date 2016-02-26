import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.io.File;

/**
 * Created by Caitlin on 1/27/2016.
 */
public class PageKitchen extends PageStory {
    public PageKitchen(Speak speak) {
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
        bg = new Image("file:" + getPicDir() + "kitchen" + File.separator + "kitchen_bg.png", getWidth(), getHeight(), false, true);
    }


    /**
     * cleans up and ends the page
     */
    public void end() {
        //todo

        endPage();
    }

    @Override
    public void handleLogic() {
        //todo
    }

    @Override
    public void drawImages() {
        //todo
    }

    @Override
    public void setEventHandlers() {
        getBaseScene().setOnMouseClicked(new MouseClick());
        getBaseScene().setOnKeyPressed(new PageStory.PressEsc());
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