import javafx.scene.paint.Color;


/**
 * Created by Caitlin on 2/8/2016.
 */
public class Interaction {
    private TextOption[] options;
    private String description;
    private int timer;
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
   }

    /**
     * processes how the options should be displayed and drawn
     * todo add timer functionality
     * @param time
     */
    public void process(double time) {

        page.addDescription(description);

        page.getGC().setFill(Color.AQUA);
        page.getGC().setGlobalAlpha(0.25);

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

    /**
     * remove all options
     */
    public void clear() {
        for (int i = 0; i < options.length; i ++) {
            options[i].makeInvisible();
        }
    }

    public int getLength() { return options.length; }

}
