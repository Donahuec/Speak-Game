
/**
 * Created by Caitlin on 1/29/2016.
 */
abstract class PageStory extends Page {
    public PageStory(Speak speak) {
        super(speak);
    }
    /**
     * initializes the scene
     */
    abstract void begin();

    /**
     * initializes assets for the scene
     */
    abstract void getAssets();
    /**
     * checks for changes in the page
     */
    abstract void update();

    /**
     * cleans up and ends the page
     */
    abstract void end();

    /**
     * Draws interaction Pane
     */
    abstract void drawInteractionPane();
}
