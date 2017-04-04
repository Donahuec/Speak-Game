package GameObject;

/**
 * Stores and processes animated items
 */

import GameProcessing.*;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;


public class AnimatedObject {
    private Speak speak;
    private Image[] frames;
    private double duration;
    private int curFrame;
    private boolean active;
    private boolean paused;
    // -1 if no pause in animation
    private int pauseFrame;
    private double prevTime;
    /*
    This variable decides if the animation should finish its loop when it is deactivated.
    For instance, if you have a door, and you remove the mouse from it, it should finish opening and closing
    while ignoring and pause.
     */
    private boolean returnToStart;

    /**
     * Constructor for an animated object
     * @param speak
     * @param frames an array of images that make up the frames of the object
     * @param duration how long each frame should last in seconds
     * @param active should the animation start immediately
     * @param pauseFrame if the animation needs to pause, what frame does it pause on
     * @param returnToStart does the animation loop finish when deactivated
     */
    public AnimatedObject(Speak speak, Image[] frames, double duration, boolean active, int pauseFrame, boolean returnToStart) {
        this.speak = speak;
        this.frames = frames;
        this.duration = duration;
        curFrame = 0;
        this.active = active;
        prevTime = getLoop().getCurTime();
        this.paused = false;
        this.pauseFrame = pauseFrame;
        this.returnToStart = returnToStart;
    }

    /**
     * Gets the next frame in the animation sequence
     */
    public Image getFrame()
    {
        if((active && !paused) || (!active && returnToStart)){
            //check if enough time has passed
            if ((active || (!active && curFrame != 0)) && (getLoop().getCurTime() - prevTime > duration)) {
                curFrame++;
                //loop back to beginning of animation if necessary
                if (curFrame == frames.length){
                    curFrame = 0;
                }

                if (active && curFrame == pauseFrame) {
                    paused = true;
                }
                //store the time this change occurred
                prevTime = getLoop().getCurTime();
            }
        }
        return frames[curFrame];
    }

    public void setActive(boolean set) {
        active = set;
    }

    public void setPaused(boolean set) { paused = set; }

    public Image[] getFrames() { return frames; }

    public double getDuration() { return duration; }

    public boolean isActive() { return active; }

    public  boolean isPaused() { return paused; }

    //functions to pull information from speak and its variables for easier reading
    //as well as easier modification
    public String getPicDir(){ return speak.getVars().getPicDir(); }

    public Group getRoot(){ return speak.getRoot(); }

    public Stage getStage() { return speak.getGameStage(); }

    public GraphicsContext getGC() { return speak.getGraphicsContext(); }

    public GameLoop getLoop() { return speak.getGameLoop(); }

    public Scene getBaseScene() { return speak.getBaseScene(); }

    public double getWidth() { return speak.getGameStage().getWidth(); }

    public double getHeight() {return speak.getGameStage().getHeight(); }
}
