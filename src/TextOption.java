import javafx.scene.text.Text;

/**
 * Created by Caitlin on 2/8/2016.
 */
public class TextOption {
    private Text option;
    private Text description;

    public TextOption(Text option, Text description) {
        this.option = option;
        this.description = description;
    }

    public Text getOption() {
        return option;
    }

    public Text getDescription() {
        return description;
    }

    public void setOption(Text option) {
        this.option = option;
    }

    public void setDescription(Text description) {
        this.description = description;
    }
}
