import javafx.scene.image.Image;
import java.io.File;

public class Variables {
    //will create getters and setters when setting up the class in a more fleshed out way
    public double circPosition;
    private String workingDir;
    private String assetDir;
    private Page currentPage;
    public Image startButton;
    public Image endButton;
    // enum for the various possible pages
    public enum Page {
        START, END, MENU, M_STATUS, M_AGENDA, M_TODO, M_CONTROLS, BEDROOM, KITCHEN, LIVINGROOM,
        BUS, BUS_ENTRANCE, STREET, OFFICE, O_DESK, O_BREAKROOM, O_HALLWAY, O_MEETINGROOM,
        LUNCH, CAR, DINNER
    }

    public Variables(){
        //get the directory the files are in
        workingDir = System.getProperty("user.dir");
        //make sure src is not included in the path
        workingDir = workingDir.replace("src" + File.separator, "");
        workingDir = workingDir.replace(File.separator + "src", "");
        //add assets, to access images (do this in File manager class)
        assetDir = workingDir + File.separator + "assets" + File.separator;
        //Initialize images
        startButton = new Image("file:" + assetDir + "startButton.png");
        endButton = new Image("file:" + assetDir + "endButton.png");
        circPosition = 0;
        currentPage = Page.START;
    }

    //getters and setters
    public String getWorkingDir(){
        return workingDir;
    }

    public String getAssetDir(){
        return assetDir;
    }

    public Page getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Page newPage){
        currentPage = newPage;
    }
}