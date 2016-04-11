import javafx.scene.paint.Color;


/**
 * Created by Caitlin on 2/8/2016.
 */
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


    public Interaction(PageStory page, String description, TextOption[] options, int timer) {

        this.page = page;
        this.description = description;
        this.options = options;
        this.timer = timer;
        //width of box that contains options
        this.width = (page.getWidth() / 2);
        this.height = (page.getHeight()/ 2);
        this.x = page.getWidth() / 4;
        this.y = page.getHeight() / 5;
        startTime = -1;
   }

    /**
     * processes how the options should be displayed and drawn
     * todo add timer functionality
     * @param time
     */
    public void process(double time) {

        //set the start time in the first frame
        if (startTime == -1) {
            startTime = time;
        }

        double elapsed = time - startTime;
        if (timer != 0 && elapsed >= timer) {
            //choose the panic option
            page.setChoice(6);
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

    public void removeLast() {
        TextOption[] temp = new TextOption[options.length - 1] ;
        for (int i = 0; i < options.length - 1; i++){
            temp[i] = options[i];
        }
        options = temp;
    }

    public int getLength() { return options.length; }

}
