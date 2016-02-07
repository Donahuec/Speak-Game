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
    private int curFrame;
    private int delay;
    private int curDelay;
    private boolean active;

    /**
     * Constructor for Animated Objects whose Animation is time based
     * @param speak
     * @param frames
     * @param duration
     * @param isTimeBased
     */
    public AnimatedObject(Speak speak, Image[] frames, double duration, boolean isTimeBased, boolean active) {
        this.speak = speak;
        this.frames = frames;
        this.duration = duration;
        delay = (int)((duration / 4) * 60);
        System.out.println(delay);
        curFrame = 0;
        curDelay = 0;
        this.active = active;
    }

    /**
     * Constructor for animated objects whose animation is delay/interaction based
     * @param speak
     * @param frames
     * @param delay
     */
    public AnimatedObject(Speak speak, Image[] frames, int delay, boolean active) {
        this.speak = speak;
        this.frames = frames;
        this.duration = 0;
        this.delay = delay;
        curFrame = 0;
        curDelay = 0;
        this.active = active;
    }


    /**
     * Gets the next frame in the animation sequence
     */
    public Image getFrame()
    {
        if(active){
            int index;

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

            return frames[index];
    } else {
            return frames[curFrame];
        }

    }

    public void setActive(boolean set) {
        active = set;
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
