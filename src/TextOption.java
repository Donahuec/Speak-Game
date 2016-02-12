import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;

/**
 * Created by Caitlin on 2/8/2016.
 */
public class TextOption {
    private String opString;
    private Text option;
    private String description;
    private Page page;

    public TextOption(String option, String description, int pos, Page page) {
        this.page = page;
        this.opString = option;
        this.option = new Text("");
        this.option.setWrappingWidth(page.getWidth() / 2);
        this.option.setTextAlignment(TextAlignment.CENTER);
        this.option.setFont(Font.font( "Times New Roman", page.getHeight() / 40 ));
        //dimensions of rectangle that contains options
        double x = page.getWidth() / 4;
        double y = page.getHeight() / 5;
        double height = (page.getHeight() / 28) + 10;
        //set the pos of option based off of option number
        this.option.setX(x + 20);
        switch(pos){
            case 0:
                this.option.setY(y + height);
                break;
            case 1:
                this.option.setY(1.5 * y + height);
                break;
            case 2:
                this.option.setY(2 * y + height);
                break;
            case 3:
                this.option.setY(2.5 * y + height);
                break;
            case 4:
                this.option.setY(3 * y + height);
                break;
        }

        page.getRoot().getChildren().add(this.option);
        this.description = description;
    }


    public void makeVisible(){
        option.setText(opString);
    }

    public void MakeInvisible() {
        option.setText("");
    }

    public void destructor(){
        page.getRoot().getChildren().remove(option);
    }

    public Text getOption() {
        return option;
    }

    public String getDescription() {
        return description;
    }

    public void setOption(Text option) {
        this.option = option;
    }

    public void setDescription(String description) {
        this.description = description;
    }
}
