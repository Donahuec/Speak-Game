/**
 * Created by Caitlin on 1/25/2016.
 */
public abstract class Page {
    public Speak speak;
    public boolean initialized;

    public Page(Speak speak) {
        this.speak = speak;
        initialized = false;
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

}
