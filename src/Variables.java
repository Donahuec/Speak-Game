


import java.io.File;

public class Variables {
    private Speak speak;
    private String workingDir;
    private String assetDir;
    private String picDir;
    private String fontDir;
    private  String textDir;
    private Page currentPage;
    public Page returnPage;

    public PageAlarm ALARM;
    public PageStart START;
//    public PageEnd END;

    public MenuHome MENU_HOME;
    public MenuStatus M_STATUS;
    public MenuAgenda M_AGENDA;
    public MenuToDo M_TODO;
    public MenuTutorial M_TUTORIAL;

    public PageBedroom BEDROOM;
    public PageKitchen KITCHEN;
    public PageLivingRoom LIVINGROOM;

    public PageStreet STREET;
    public PageBusEntrance BUS_ENTRANCE;
    public PageBusSeat BUS_SEAT;
//
//    public PageOfficeDesk OFFICE_DESK;
//    public PageOfficeBreakRoom OFFICE_BREAKROOM;
//    public PageOfficeHallway OFFICE_HALLWAY;
//    public PageOfficeMeeting OFFICE_MEETING;
//
//    public PageLunch LUNCH;
//    public PageDinner DINNER;
//    public PageCar CAR;

    public Variables(Speak speak){
        this.speak = speak;
        //get the directory the files are in
        workingDir = System.getProperty("user.dir");
        //make sure src is not included in the path
        workingDir = workingDir.replace("src" + File.separator, "");
        workingDir = workingDir.replace(File.separator + "src", "");
        //add assets, to access images (do this in File manager class)
        assetDir = workingDir + File.separator + "assets" + File.separator;
        picDir = assetDir + "images" + File.separator;
        textDir = assetDir + "text" + File.separator;
        fontDir = assetDir + "fonts" + File.separator;

        //initialize pages
        ALARM = new PageAlarm(speak);
        START = new PageStart(speak);
       // END = new PageEnd(speak);
        MENU_HOME = new MenuHome(speak);
        M_STATUS = new MenuStatus(speak);
        M_AGENDA = new MenuAgenda(speak);
        M_TODO = new MenuToDo(speak);
        M_TUTORIAL = new MenuTutorial(speak);
        BEDROOM = new PageBedroom(speak);
        KITCHEN = new PageKitchen(speak);
        LIVINGROOM = new PageLivingRoom(speak);
        STREET = new PageStreet(speak);
        BUS_ENTRANCE = new PageBusEntrance(speak);
        BUS_SEAT = new PageBusSeat(speak);
        //OFFICE_DESK = new PageOfficeDesk(speak);
        //OFFICE_BREAKROOM = new PageOfficeBreakRoom(speak);
        //OFFICE_HALLWAY = new PageOfficeHallway(speak);
        //OFFICE_MEETING = new PageOfficeMeeting(speak);
       // LUNCH = new PageLunch(speak);
       // DINNER = new PageDinner(speak);
       // CAR = new PageCar(speak);

        currentPage = START;
        returnPage = START;
    }

    //getters and setters
    public String getWorkingDir(){ return workingDir; }

    public String getAssetDir(){
        return assetDir;
    }

    public String getTextDir() { return  textDir; }

    public String getFontDir() { return fontDir; }

    public Page getCurrentPage() {
        return currentPage;
    }

    public Page getReturnPage() { return returnPage; }

    // TODO 4/11/2016: make sure this is a valid page
    public void setCurrentPage(Page newPage){
        currentPage = newPage;
    }

    // TODO 4/11/2016: Make sure this is a valid page
    public void setReturnPage(Page page){ returnPage = page; }

    public String getPicDir() { return picDir; }
}