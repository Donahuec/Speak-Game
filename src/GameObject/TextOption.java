package GameObject;

/**
 * This class holds all of the information related to an option in an interaction
 * this includes its position, text, and description
 */

import Pages.Page;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;



public class TextOption {
    //String version of option
    private String opString;
    private Text option;
    private String description;
    private Page page;

    /**
     * Set up an option in an interaction
     * @param option A string with the text for the option
     * @param description A string with the description for the option
     * @param index The index in the list of options for an interaction. defines position
     * @param page the current page
     */
    public TextOption(String option, String description, int index, Page page) {
        assert option !=  null: "option is null";
        assert description != null: "description is null";
        assert index >= 0 && index < 5: "Invalid position";

        this.page = page;
        this.opString = option;
        //set up Javafx text settings
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
        switch(index){
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

    public void makeInvisible() {
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
