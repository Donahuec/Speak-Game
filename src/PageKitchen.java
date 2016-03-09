import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.io.File;

/**
 * Created by Caitlin on 1/27/2016.
 */
public class PageKitchen extends PageStory {
    private AnimatedObject fridgeDoor;
    private Rectangle fridgeRec;
    private Rectangle doorRec;
    private String roomDesc;
    private String doorDesc;
    private String fridgeDesc;
    private boolean walkTime;
    private boolean mediumTime;
    private boolean hasEaten = false;
    private String curDesc;

    public PageKitchen(Speak speak) {
        super(speak);
    }

    /**
     * initializes the scene
     */
    public void begin() {
        speak.getVars().setReturnPage(speak.getVars().KITCHEN);
        getAssets();
        initialized = true;
    }

    /**
     * initializes assets for the scene
     */
    public void getAssets() {
        bg = new Image("file:" + getPicDir() + "kitchen" + File.separator + "kitchen_bg.png", getWidth(), getHeight(), false, true);

        Image[] doorArr = new Image[7];
        doorArr[0] = new Image("file:" + getPicDir() + "kitchen" + File.separator + "fridgedoor_1.png");
        doorArr[1] = new Image("file:" + getPicDir() + "kitchen" + File.separator + "fridgedoor_2.png");
        doorArr[2] = new Image("file:" + getPicDir() + "kitchen" + File.separator + "fridgedoor_3.png");
        doorArr[3] = new Image("file:" + getPicDir() + "kitchen" + File.separator + "fridgedoor_4.png");
        doorArr[4] = doorArr[3];
        doorArr[5] = doorArr[2];
        doorArr[6] = doorArr[1];
        fridgeDoor = new AnimatedObject(speak, doorArr, 0.1, false, 3, true);
        fridgeRec = new Rectangle(getWidth()  * 0.76, getHeight() * 0.33, getWidth() * 0.24, getHeight() * 0.44 );

        doorRec = new Rectangle(getWidth() * 0.02, 0, getWidth() * 0.13, getHeight() * 0.91);

        text = new GameText(getTextDir() + "kitchen.xml");
        roomDesc = text.getText("roomDesc");
        doorDesc = text.getText("door");
        fridgeDesc = text.getText("fridge");

        curDesc = roomDesc;

        //door option
        options.put("livingroom", new TextOption( text.getText("livingroom"),text.getText("livingroomDesc"), 0,  this));
        options.put("bus", new TextOption( text.getText("bus"),text.getText("busDesc"), 1,  this));
        options.put("walk", new TextOption( text.getText("walk"),text.getText("walkDesc"), 2,  this));
        TextOption[] arrExit = { options.get("livingroom"),options.get("bus"), options.get("walk")};

        interactions.put("hallway", new Interaction(this, text.getText("hallChoice") , arrExit , 0));
        curInteraction = interactions.get("hallway");
        walkTime = false;

        //refrigerator option
        options.put("small", new TextOption(text.getText("small"), text.getText("smallDesc"), 0, this));
        options.put("medium", new TextOption(text.getText("medium"), text.getText("mediumDesc"), 1, this));
        options.put("large", new TextOption(text.getText("large"), text.getText("largeDesc"), 2, this));

        TextOption[] arrFridge = { options.get("small"), options.get("medium"), options.get("large") };
        interactions.put("fridge", new Interaction(this, text.getText("hallChoice") , arrFridge , 0));

        mediumTime = false;

    }


    /**
     * cleans up and ends the page
     */
    public void end() {
        fridgeDoor = null;
        fridgeRec = null;
        doorRec = null;
        roomDesc = null;
        doorDesc = null;
        fridgeDesc = null;
        curDesc = null;
        endPage();
    }

    @Override
    public void handleLogic() {
        if (timeCompare(8, 10) == 1 && walkTime == false) {
            interactions.get("hallway").removeLast();
            interactions.get("fridge").removeLast();
            walkTime = true;
        }
        if (timeCompare(8, 30) == 1 && mediumTime == false) {
            interactions.get("fridge").removeLast();
            mediumTime = true;
        }
        if (curInteraction == interactions.get("hallway")) {
            if (choice == 1) {
                //go to livingroom
                changePage(P.LIVINGROOM);
                end();
            } else if (choice == 2) {
                //take bus
                changePage(P.BUS_ENTRANCE);
                end();
            } else if (choice == 3) {
                //walk to work
                changePage(P.STREET);
                end();
            }
        } else {
            if (choice == 1) {
                updateAnxiety(10);
                updateStress(5);
                hasEaten = true;
            } else if (choice == 2) {
                getStats().setSmallBreakfast(false);
                updateTime(0,10);
                hasEaten = true;
            } else if (choice == 3) {
                updateAnxiety(-15);
                updateStress(-5);
                getStats().setSmallBreakfast(false);
                updateTime(0, 30);
                hasEaten = true;
            }
        }



    }

    @Override
    public void drawImages() {
        getGC().drawImage(fridgeDoor.getFrame(), getWidth()  * 0.75, getHeight() * 0.1, getWidth() * 0.25, getHeight() * 0.80);
    }

    @Override
    public void setEventHandlers() {
        getBaseScene().setOnMouseClicked(new MouseClick());
        getBaseScene().setOnKeyPressed(new PageStory.PressEsc());
        getBaseScene().setOnMouseMoved(new MouseEnter());
    }

    @Override
    public void updateDescription() {
        addDescription(curDesc);
    }

    /**
     * Handler for the mouse hovering on items
     *
     */
    class MouseEnter implements EventHandler<MouseEvent> {
        @Override
        public void handle(MouseEvent e) {
            if (isInteraction) {
                getInteractionHover(e);
            } else {
                if (fridgeRec.contains(e.getX(), e.getY()) && hasEaten == false) {
                    fridgeDoor.setActive(true);
                    curDesc = fridgeDesc;
                } else if (doorRec.contains(e.getX(), e.getY())) {
                    curDesc = doorDesc;
                } else {
                    fridgeDoor.setActive(false);
                    fridgeDoor.setPaused(false);
                    curDesc = roomDesc;
                }
            }

        }
    }

    /**
     * Handler for pressing End button
     */
    class MouseClick implements EventHandler<MouseEvent>{
        @Override
        public void handle(MouseEvent e)
        {
            if (isInteraction) {
                getInteractionChoice(e);
            } else {
                if (fridgeRec.contains(e.getX(), e.getY()) && hasEaten == false) {
                    curInteraction = interactions.get("fridge");
                    isInteraction = true;
                } else if (doorRec.contains(e.getX(), e.getY())) {
                    curInteraction = interactions.get("hallway");
                    isInteraction = true;

                }
            }
        }
    }
}