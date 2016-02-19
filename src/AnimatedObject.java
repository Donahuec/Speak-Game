
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
    private boolean active;
    private double lastTime;

    /**
     * Constructor for Animated Objects
     * @param speak
     * @param frames
     * @param duration

     */
    public AnimatedObject(Speak speak, Image[] frames, double duration, boolean active) {
        this.speak = speak;
        this.frames = frames;
        this.duration = duration;
        curFrame = 0;
        this.active = active;
        lastTime = getLoop().curTime;
    }


    /**
     * Gets the next frame in the animation sequence
     */
    public Image getFrame()
    {
        if(active){
            //check if enough time has passed
            if (getLoop().curTime - lastTime > duration) {
                curFrame++;
                //loop back to beginning of animation if necessary
                if (curFrame == frames.length){
                    curFrame = 0;
                }
                //store the time this change occured
                lastTime = getLoop().curTime;
            }
        }
        return frames[curFrame];
    }

    public void setActive(boolean set) {
        active = set;
    }



    public Image[] getFrames() { return frames; }

    public double getDuration() { return duration; }

    public boolean isActive() { return active; }



    //functions to pull information from speak and its variables for easier reading
    //as well as easier modification
    public String getPicDir(){ return speak.getVars().getPicDir(); }

    public Group getRoot(){ return speak.getRoot(); }

    public Stage getStage() { return speak.getGameStage(); }

    public GraphicsContext getGC() { return speak.getGc(); }

    public GameLoop getLoop() { return speak.getGameLoop(); }

    public Scene getBaseScene() { return speak.getBaseScene(); }

    public double getWidth() { return speak.getGameStage().getWidth(); }

    public double getHeight() {return speak.getGameStage().getHeight(); }
}
