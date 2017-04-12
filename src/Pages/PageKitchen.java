package Pages;

/**
 * Page for the kitchen. Here you make meals, and do dishes
 */

import GameObject.AnimatedObject;
import GameObject.GameText;
import GameObject.Interaction;
import GameObject.TextOption;
import GameProcessing.Speak;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Rectangle;

import java.io.File;


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

    private final int[] WALK_TIME = {8, 10};
    private final int[] LARGE_MEAL_TIME = {8, 30};
    private final int[] SMALL_MEAL_STRESS = {10, 7, 13};
    private final int[] SMALL_MEAL_ANXIETY = {20, 15, 25};
    private final int[] L_MEAL_STRESS = {-10, -13, -7};
    private final int[] L_MEAL_ANXIETY = {-20, -25, -15};
    private final int[] M_MEAL_TIME = {0, 10};
    private final int[] L_MEAL_TIME = {0, 30};
    private final int DOOR_FRAMES = 7;
    private final int PAUSE_FRAME = 3;
    private final double ANIM_TIME = 0.1;

    private final int SMALL_BREAKFAST = 0;
    private final int MEDIUM_BREAKFAST = 1;
    private final int LARGE_BREAKFAST = 2;
    private final int LIVING_ROOM = 0;
    private final int BUS = 1;
    private final int WALK = 2;

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

        Image[] doorArr = new Image[DOOR_FRAMES];
        doorArr[0] = new Image("file:" + getPicDir() + "kitchen" + File.separator + "fridgedoor_1.png");
        doorArr[1] = new Image("file:" + getPicDir() + "kitchen" + File.separator + "fridgedoor_2.png");
        doorArr[2] = new Image("file:" + getPicDir() + "kitchen" + File.separator + "fridgedoor_3.png");
        doorArr[3] = new Image("file:" + getPicDir() + "kitchen" + File.separator + "fridgedoor_4.png");
        doorArr[4] = doorArr[3];
        doorArr[5] = doorArr[2];
        doorArr[6] = doorArr[1];
        fridgeDoor = new AnimatedObject(speak, doorArr, ANIM_TIME, false, PAUSE_FRAME, true);

        double fridgeX = getWidth()  * 0.76;
        double fridgeY = getHeight() * 0.33;
        double fridgeWidth = getWidth() * 0.24;
        double fridgeHeight = getHeight() * 0.44;
        fridgeRec = new Rectangle(fridgeX, fridgeY, fridgeWidth, fridgeHeight);


        double doorX = getWidth() * 0.02;
        double doorY = 0;
        double doorWidth = getWidth() * 0.13;
        double doorHeight = getHeight() * 0.91;
        doorRec = new Rectangle(doorX, doorY, doorWidth, doorHeight);

        text = new GameText(getTextDir() + "kitchen.xml");
        roomDesc = text.getText("roomDesc");
        doorDesc = text.getText("door");
        fridgeDesc = text.getText("fridge");

        curDesc = roomDesc;

        //door option
        options.put("livingroom", new TextOption( text.getText("livingroom"),text.getText("livingroomDesc"), LIVING_ROOM,  this));
        options.put("bus", new TextOption( text.getText("bus"),text.getText("busDesc"), BUS,  this));
        options.put("walk", new TextOption( text.getText("walk"),text.getText("walkDesc"), WALK,  this));
        TextOption[] arrExit = { options.get("livingroom"),options.get("bus"), options.get("walk")};

        interactions.put("hallway", new Interaction(this, text.getText("hallChoice") , arrExit , 0));
        curInteraction = interactions.get("hallway");
        walkTime = false;

        //refrigerator option
        options.put("small", new TextOption(text.getText("small"), text.getText("smallDesc"), SMALL_BREAKFAST, this));
        options.put("medium", new TextOption(text.getText("medium"), text.getText("mediumDesc"), MEDIUM_BREAKFAST, this));
        options.put("large", new TextOption(text.getText("large"), text.getText("largeDesc"), LARGE_BREAKFAST, this));

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
        if (timeCompare(WALK_TIME[0], WALK_TIME[1]) == -1 && walkTime == false) {
            interactions.get("hallway").removeLast();
            interactions.get("fridge").removeLast();
            walkTime = true;
        }
        if (timeCompare(LARGE_MEAL_TIME[0], LARGE_MEAL_TIME[1]) == -1 && mediumTime == false) {
            interactions.get("fridge").removeLast();
            mediumTime = true;
        }
        if (curInteraction == interactions.get("hallway")) {
            if (choice == LIVING_ROOM) {
                //go to living room
                changePage(P.LIVINGROOM);
                end();
            } else if (choice == BUS) {
                //take bus
                changePage(P.BUS_ENTRANCE);
                end();
            } else if (choice == WALK) {
                //walk to work
                changePage(P.STREET);
                end();
            }
        } else {
            if (choice == SMALL_BREAKFAST) {
                updateAnxiety(SMALL_MEAL_ANXIETY[0], SMALL_MEAL_ANXIETY[1], SMALL_MEAL_ANXIETY[2]);
                updateStress(SMALL_MEAL_STRESS[0], SMALL_MEAL_STRESS[1], SMALL_MEAL_STRESS[2]);
                hasEaten = true;
            } else if (choice == MEDIUM_BREAKFAST) {
                getStats().setSmallBreakfast(false);
                updateTime(M_MEAL_TIME[0], M_MEAL_TIME[1]);
                hasEaten = true;
            } else if (choice == LARGE_BREAKFAST) {
                updateAnxiety(L_MEAL_ANXIETY[0], L_MEAL_ANXIETY[1], L_MEAL_ANXIETY[2]);
                updateStress(L_MEAL_STRESS[0], L_MEAL_STRESS[1], L_MEAL_STRESS[2]);
                getStats().setSmallBreakfast(false);
                updateTime(L_MEAL_TIME[0], L_MEAL_TIME[1]);
                hasEaten = true;
            }
        }



    }

    @Override
    public void drawImages() {
        double fridgeX = getWidth()  * 0.75;
        double fridgeY = getHeight() * 0.1;
        double fridgeWidth = getWidth() * 0.25;
        double fridgeHeight = getHeight() * 0.80;
        getGC().drawImage(fridgeDoor.getFrame(), fridgeX, fridgeY, fridgeWidth, fridgeHeight);
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