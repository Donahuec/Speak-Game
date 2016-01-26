import java.io.File;

public class Variables {
    //will create getters and setters when setting up the class in a more fleshed out way
    private Speak speak;
    private String workingDir;
    private String assetDir;
    private Page currentPage;
    public PageStart START;
    public pageTemp TEMP;

    public Variables(Speak speak){
        this.speak = speak;
        //get the directory the files are in
        workingDir = System.getProperty("user.dir");
        //make sure src is not included in the path
        workingDir = workingDir.replace("src" + File.separator, "");
        workingDir = workingDir.replace(File.separator + "src", "");
        //add assets, to access images (do this in File manager class)
        assetDir = workingDir + File.separator + "assets" + File.separator;
        START = new PageStart(speak);
        TEMP = new pageTemp(speak);
        currentPage = START;
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