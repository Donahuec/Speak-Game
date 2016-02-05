import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Created by Caitlin on 2/5/2016.
 */
public class AnimatedObject {
    private Speak speak;
    private Image[] frames;
    private double duration;
    private boolean isTimeBased;
    private int curFrame;
    private int delay;
    private int curDelay;

    /**
     * Constructor for Animated Objects whose Animation is time based
     * @param speak
     * @param frames
     * @param duration
     * @param isTimeBased
     */
    public AnimatedObject(Speak speak, Image[] frames, double duration, boolean isTimeBased) {
        this.speak = speak;
        this.frames = frames;
        this.duration = duration;
        this.isTimeBased = isTimeBased;
        curFrame = 0;
    }

    /**
     * Constructor for animated objects whose animation is delay/interaction based
     * @param speak
     * @param frames
     * @param delay
     */
    public AnimatedObject(Speak speak, Image[] frames, int delay) {
        this.speak = speak;
        this.frames = frames;
        this.duration = 0;
        this.isTimeBased = false;
        this.delay = delay;
        curFrame = 0;
        curDelay = 0;
    }


    /**
     * Gets the next frame in the animation sequence
     */
    public Image getFrame()
    {
        int index;
        if (isTimeBased){
            //calculate the current frame based off of game time
            index = (int)((getLoop().curTime % (frames.length * duration)) / duration);
            return frames[index];
        } else {
            if (curDelay < delay) {
                //controls the length of animations that are not time based
                curDelay++;
                index = curFrame;
            } else {
                index = curFrame;
                curFrame++;
                if (curFrame >= frames.length){
                    curFrame = 0;
                }
                curDelay = 0;
            }
        }
        return frames[index];
    }

    /**
     * gets the current frame for paused animations
     */
    public Image getCurFrame() {
        return frames[curFrame];
    }



    public Image[] getFrames() { return frames; }

    public double getDuration() { return duration; }



    //functions to pull information from speak and its variables for easier reading
    //as well as easier modification
    public String getPicDir(){ return speak.vars.getPicDir(); }

    public Group getRoot(){ return speak.root; }

    public Stage getStage() { return speak.gameStage; }

    public GraphicsContext getGC() { return speak.gc; }

    public GameLoop getLoop() { return speak.gameLoop; }

    public Scene getBaseScene() { return speak.baseScene; }

    public double getWidth() { return speak.gameStage.getWidth(); }

    public double getHeight() {return speak.gameStage.getHeight(); }
}
