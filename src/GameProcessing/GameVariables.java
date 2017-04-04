package GameProcessing;

/**
* Holds all of the information related to running the game
*/

import Pages.Page;
import Pages.*;
import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class GameVariables {
    private Speak speak;
    private String workingDir;
    private String assetDir;
    private String picDir;
    private String fontDir;
    private  String textDir;
    private Page currentPage;
    public Page returnPage;
    public List<Page> pageList;

    public PageAlarm ALARM;
    public PageStart START;
//    public PageEnd END;

    public MenuHome MENU_HOME;
//    public MenuStatus M_STATUS;
//    public MenuAgenda M_AGENDA;
//    public MenuToDo M_TODO;
//    public MenuTutorial M_TUTORIAL;

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

    public GameVariables(Speak speak){
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

        pageList = new ArrayList<Page>();

        //initialize pages
        ALARM = new PageAlarm(speak);
        pageList.add(ALARM);
        START = new PageStart(speak);
        pageList.add(START);
       // END = new PageEnd(speak);
        //pageList.add(END);
        MENU_HOME = new MenuHome(speak);
        pageList.add(MENU_HOME);
        //M_STATUS = new MenuStatus(speak);
        //pageList.add(M_STATUS);
        //M_AGENDA = new MenuAgenda(speak);
        //pageList.add(M_AGENDA);
        //M_TODO = new MenuToDo(speak);
        //pageList.add(M_TODO);
//        M_TUTORIAL = new MenuTutorial(speak);
//        pageList.add(M_TUTORIAL);
        BEDROOM = new PageBedroom(speak);
        pageList.add(BEDROOM);
        KITCHEN = new PageKitchen(speak);
        pageList.add(KITCHEN);
        LIVINGROOM = new PageLivingRoom(speak);
        pageList.add(LIVINGROOM);
        STREET = new PageStreet(speak);
        pageList.add(STREET);
        BUS_ENTRANCE = new PageBusEntrance(speak);
        pageList.add(BUS_ENTRANCE);
        BUS_SEAT = new PageBusSeat(speak);
        pageList.add(BUS_SEAT);
        //OFFICE_DESK = new PageOfficeDesk(speak);
        //pageList.add(OFFICE_DESK);
        //OFFICE_BREAKROOM = new PageOfficeBreakRoom(speak);
        //pageList.add(OFFICE_BREAKROOM);
        //OFFICE_HALLWAY = new PageOfficeHallway(speak);
        //pageList.add(OFFICE_HALLWAY);
        //OFFICE_MEETING = new PageOfficeMeeting(speak);
        //pageList.add(OFFICE_MEETING);
       // LUNCH = new PageLunch(speak);
        //pageList.add(LUNCH);
       // DINNER = new PageDinner(speak);
        //pageList.add(DINNER);
       // CAR = new PageCar(speak);
        //pageList.add(CAR);

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

    public void setCurrentPage(Page newPage){
        assert pageList.contains(newPage) : "Not a valid Page";
        currentPage = newPage;
    }

    public void setReturnPage(Page page){
        assert pageList.contains(page) : "Not a valid Page";
        returnPage = page;
    }

    public String getPicDir() { return picDir; }
}