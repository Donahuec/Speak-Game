package GameObject;

import Pages.PageStory;

/**
 * Holds all of the information related to an interaction
 * An interaction is the dialogue where the player makes decisions about their actions
 */

import Pages.PageStory;
import javafx.scene.paint.Color;


public class Interaction {
    private TextOption[] options;
    private String description;
    private int timer;
    private double startTime;
    private PageStory page;
    private double width;
    private double height;
    private double x;
    private double y;


    /**
     * An object that holds all of the information necessary for a user to make interactive choices
     * @param page The page that the interaction takes place in
     * @param description The default description for the interaction
     * @param options An array of TextOptions for the player to choose from
     * @param timer The time limit for the decision. = if there is no limit
     */
    public Interaction(PageStory page, String description, TextOption[] options, int timer) {
        assert description != null : "description is null";
        assert page != null : "Page is null";
        assert options != null : "Options array is null";
        assert timer >= 0 : "timer has invalid time";

        this.page = page;
        this.description = description;
        this.options = options;
        this.timer = timer;
        //width of box that contains options
        this.width = (page.getWidth() / 2);
        this.height = (page.getHeight()/ 2);
        this.x = page.getWidth() / 4;
        this.y = page.getHeight() / 5;
        //the decision has not started yet
        startTime = -1;
   }

    /**
     * processes how the options should be displayed and drawn
     * @param the current time (pulled from Speak)
     */
    public void process(double time) {
        assert time > 0 : "invalid time";

        //set the start time in the first frame
        if (startTime == -1) {
            startTime = time;
        }

        double elapsed = time - startTime;
        if (timer != 0 && elapsed >= timer) {
            //choose the panic option
            page.setChoice(6);
            //TODO should this be defined here????
            page.updateAnxiety(20,15,25);
            page.isInteraction = false;
            page.curInteraction.clear();
        } else {
            //draw timer
            if (timer != 0) {
                page.getGC().setFill(Color.RED);
                page.getGC().fillRoundRect(x, y - 15, width - (width * (elapsed / timer)), 10, 15, 15);
            }

            page.addDescription(description);

            page.getGC().setFill(Color.AQUA);
            page.getGC().setGlobalAlpha(0.25);

            //draw the rectangles
            for (int i = 0; i < options.length; i++) {
                //are we hovering over this option?
                if (page.getHover() == i + 1) {
                    page.getGC().setGlobalAlpha(1);
                    page.addDescription(options[i].getDescription());
                    page.getGC().setGlobalAlpha(0.5);
                }
                page.getGC().fillRoundRect(x, ((i + 2) * 0.5) * y, width, height / 7, 15, 15);
                page.getGC().setGlobalAlpha(0.25);
            }

            //reset opacity
            page.getGC().setGlobalAlpha(1.0);

            //draw all options
            page.getGC().setFill(Color.BLACK);
            for (int i = 0; i < options.length; i ++) {
                options[i].makeVisible();
            }
        }
    }

    /**
     * remove all options
     */
    public void clear() {
        for (int i = 0; i < options.length; i ++) {
            options[i].makeInvisible();
        }
    }

    /**
     * Remove the last option
     */
    public void removeLast() {
        TextOption[] temp = new TextOption[options.length - 1] ;
        for (int i = 0; i < options.length - 1; i++){
            temp[i] = options[i];
        }
        options = temp;
    }

    /**
    * Find the number of options
     */
    public int getLength() { return options.length; }

}
