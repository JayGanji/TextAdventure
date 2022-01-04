/**
 *  This class is the main class of the "World of Zuul" application. 
 *  "World of Zuul" is a very simple, text based adventure game.  Users 
 *  can walk around some scenery. That's all. It should really be extended 
 *  to make it more interesting!
 * 
 *  To play this game, create an instance of this class and call the "play"
 *  method.
 * 
 *  This main class creates and initialises all the others: it creates all
 *  rooms, creates the parser and starts the game.  It also evaluates and
 *  executes the commands that the parser returns.
 * 
 * @author  Michael Kölling and David J. Barnes modified by Jay Ganji
 * @version 2021.03.22
 */

public class Game 
{
    private Parser parser;
    private Room currentRoom;
    
    /**
     * Allows the game to be run without BlueJ
     */
    public static void main(String[] args) 
    {
        Game newGame = new Game();
        newGame.play();
    }
    
    /**
     * Create the game and initialise its internal map.
     */
    public Game() 
    {
        createRooms();
        parser = new Parser();
    }

    /**
     * Create all the rooms and link their exits together.
     */
    private void createRooms()
    {
        Room cell, firstHallwayMain, firstHallwayEast,
        firstHallwayWest, firstHallwayWestCell, firstHallwayMainCell,
        firstHallwayEastCell, westHallway, westHallwayCell, eastHallway,
        eastHallwayCell, westHallwayUpper, eastHallwayUpper,
        westHallwayUpperCell, eastHallwayUpperCell, westHallwayHovel,
        eastHallwayExit;
      
        // create the rooms
        cell = new Room("in a barren room with stone walls and no windows.");
        firstHallwayMain = new Room("in a dimly lit hallway with entrances"
            + " to other cells.");
        firstHallwayMainCell = new Room("in a cell situated opposite to yours."
            + " Looks very much like yours.");
        firstHallwayWest = new Room("in the western part of the hallway"
            + " outside your cell.");
        firstHallwayWestCell = new Room("in another cell in this prison, much"
            + " like yours.");
        firstHallwayEast = new Room("in the eastern part of the hallway"
            + " outside your cell.");    
        firstHallwayEastCell = new Room("in another cell in this prison, much"
            + " like yours.");
        westHallway = new Room("in another hallway, which its way to you, "
            + "perpendicular to the previous one.");
        westHallwayCell = new Room("in another cell in this prison, much"
            + " like yours.");
        eastHallway = new Room("in another hallway, which opens its way to you, "
            + "perpendicular to the previous one.");
        eastHallwayCell = new Room("in another cell in this prison, much"
            + " like yours.");
        westHallwayUpper = new Room("in the same hallway continuing north, sloping up.");
        eastHallwayUpper = new Room("in the same hallway continuing north, sloping up.");
        westHallwayUpperCell = new Room("in another cell in this prison, much"
            + " like yours.");
        eastHallwayUpperCell = new Room("in another cell in this prison, much"
            + " like yours.");
        westHallwayHovel = new Room("in a strange hole in the wall "
            + "surrounded by dirt, and very dark.");
        eastHallwayExit = new Room("in the outside"
            + " world. Congratulations!");
        
        
        // initialise room exits
        cell.setExit("north", firstHallwayMain);
        firstHallwayMain.setExit("south", cell);
        firstHallwayMain.setExit("north", firstHallwayMainCell);
        firstHallwayMain.setExit("west", firstHallwayWest);
        firstHallwayMain.setExit("east", firstHallwayEast);
        firstHallwayMainCell.setExit("south", firstHallwayMain);
        firstHallwayWest.setExit("north", firstHallwayWestCell);
        firstHallwayWest.setExit("east", firstHallwayMain);
        firstHallwayWest.setExit("west", westHallway);
        firstHallwayWestCell.setExit("south", firstHallwayWest);
        westHallway.setExit("east", firstHallwayWest);
        westHallway.setExit("south", westHallwayCell);
        westHallway.setExit("north", westHallwayUpper);
        westHallwayCell.setExit("north", westHallway);
        westHallwayUpper.setExit("south", westHallway);
        westHallwayUpper.setExit("east", westHallwayUpperCell);
        westHallwayUpper.setExit("north", westHallwayHovel);
        westHallwayHovel.setExit("south", westHallwayUpper);
        firstHallwayEast.setExit("north", firstHallwayEastCell);
        firstHallwayEast.setExit("west", firstHallwayMain);
        firstHallwayEast.setExit("East", eastHallway);
        firstHallwayEastCell.setExit("south", firstHallwayEast);
        eastHallway.setExit("west", firstHallwayEast);
        eastHallway.setExit("south", eastHallwayCell);
        eastHallway.setExit("north", eastHallwayUpper);
        eastHallwayCell.setExit("north", eastHallway);
        eastHallwayUpper.setExit("south", eastHallway);
        eastHallwayUpper.setExit("west", eastHallwayUpperCell);
        eastHallwayUpper.setExit("north", eastHallwayExit);
        eastHallwayExit.setExit("south", eastHallwayUpper);
        
        westHallwayHovel.addItem("a small shiny metal key",1);

        currentRoom = cell;  // start game in cell
    }

    /**
     *  Main play routine.  Loops until end of play.
     */
    public void play() 
    {            
        printWelcome();

        // Enter the main command loop.  Here we repeatedly read commands and
        // execute them until the game is over.
                
        boolean finished = false;
        while (! finished) {
            Command command = parser.getCommand();
            finished = processCommand(command);
        }
        System.out.println("Thank you for playing.  Good bye.");
    }

    /**
     * Print out the opening message for the player.
     */
    private void printWelcome()
    {
        System.out.println();
        System.out.println("Welcome to the World of Zuul!");
        System.out.println("World of Zuul is a new, incredibly boring adventure game.");
        System.out.println("You woke up in your prison cell, with amnesia.");
        System.out.println("There is no one else in this prison. Try to find the exit.");
        System.out.println("Type '" + CommandWord.HELP + "' if you need help.");
        System.out.println();
        System.out.println(currentRoom.getLongDescription());
    }

    /**
     * Given a command, process (that is: execute) the command.
     * @param command The command to be processed.
     * @return true If the command ends the game, false otherwise.
     */
    private boolean processCommand(Command command) 
    {
        boolean wantToQuit = false;

        CommandWord commandWord = command.getCommandWord();

        switch (commandWord) {
            case UNKNOWN:
                System.out.println("I don't know what you mean...");
                break;

            case HELP:
                printHelp();
                break;

            case GO:
                goRoom(command);
                break;
                
            case LOOK:
                System.out.println(currentRoom.getLongDescription());
                break;
                
            case EAT:
                System.out.println("You have eaten and are now full.");
                break;    

            case QUIT:
                wantToQuit = quit(command);
                break;
        }
        return wantToQuit;
    }

    // implementations of user commands:

    /**
     * Print out some help information.
     * Here we print some stupid, cryptic message and a list of the 
     * command words.
     */
    private void printHelp() 
    {
        System.out.println("You are lost. You are alone. You wander");
        System.out.println("around at the university.");
        System.out.println();
        System.out.println("Your command words are:");
        parser.showCommands();
    }

    /** 
     * Try to go in one direction. If there is an exit, enter the new
     * room, otherwise print an error message.
     */
    private void goRoom(Command command) 
    {
        if(!command.hasSecondWord()) {
            // if there is no second word, we don't know where to go...
            System.out.println("Go where?");
            return;
        }

        String direction = command.getSecondWord();

        // Try to leave current room.
        Room nextRoom = currentRoom.getExit(direction);

        if (nextRoom == null) {
            System.out.println("There is no door!");
        }
        else {
            currentRoom = nextRoom;
            System.out.println(currentRoom.getLongDescription());
        }
    }

    /** 
     * "Quit" was entered. Check the rest of the command to see
     * whether we really quit the game.
     * @return true, if this command quits the game, false otherwise.
     */
    private boolean quit(Command command) 
    {
        if(command.hasSecondWord()) {
            System.out.println("Quit what?");
            return false;
        }
        else {
            return true;  // signal that we want to quit
        }
    }
}
