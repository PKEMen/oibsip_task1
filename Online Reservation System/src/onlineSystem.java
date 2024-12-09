
import java.awt.*;
import java.awt.Robot;
import java.awt.event.KeyEvent;
import java.util.Scanner;


/**
 * This class requires a user to login to the system. Once the user's details 
 * have been verified, the user is allows to access, create, edit or cancel
 * reservations made.
 *
 * @author PK Ewusie-Mensah
 * @version 1.0  2024-Nov-17
 */
public class onlineSystem {

   // defines constants for menu item choices
    private static final int OPT_ONE = 1;
    private static final int OPT_TWO = 2;    
    private static final int OPT_THREE = 3;    
    private static final int OPT_FOUR = 4;
    private static final int OPT_FIVE = 5;
    private static final int OPT_SIX = 6;
    private static final int OPT_SEVEN = 7;
    private static final int OPT_EIGHT = 8;
    private static final int OPT_NINE = 9;
    private static final int QUIT_PROGRAM = 0;
    
    // private constant values
    private static final int LOW_MENU_CHOICE = QUIT_PROGRAM ;
    private static final int HIGH_MENU_CHOICE = 10;
    
    // constant for data file location
    private static final String DATA_FILE_NAME = "data/systemUsers.txt";
    private static final String FORMS_FILE_NAME = "data/systemForms.txt";
    
   /**
     * Main method to run the online reservation system.
     *
     * @param args command-line arguments
     * @throws InterruptedException if thread sleep is interrupted
     * @throws AWTException if the Robot class encounters an error
     */
   
   public static void main(String[] args) throws InterruptedException, 
         AWTException {
      Scanner input = new Scanner(System.in);
      UserDetails loginSuccessful = null; // Keeps track of the logged-in user
      boolean continueReservationMenu = true; // Flag for the reservation menu
      int menuChoice = -1;
      
      UserInfo userInf = new UserInfo();
      Reservation userRes = new Reservation();
      
      // Display welcome message and load user data
      displayIntro();
      Thread.sleep(1000);
      clear();
      
      userInf.loadData(DATA_FILE_NAME);
      userRes.loadData(FORMS_FILE_NAME);
      
      while (menuChoice != QUIT_PROGRAM)
      {
         displayMenu();
         System.out.println();         
         menuChoice = getUserMenuInput(input);
            
         switch (menuChoice)
         {
            // User login
            case OPT_ONE:
                 loginSuccessful = userInf.login(input);
                 break;
                 
             // Create a new account
             case OPT_TWO:
                 createUserAccount(userInf, input);
                 loginSuccessful = userInf.login(input);
                 break;
                 
             // Save data and exit the program
             case QUIT_PROGRAM:
                 System.out.println("Quitting program..."); 
                 userInf.saveData(DATA_FILE_NAME);
                 userRes.saveData(FORMS_FILE_NAME);
                 System.out.println("\nTHANKS FOR USING THE "
                         + "PK HOTELS' ONLINE RESERVATIONS\n");
                 break;
                 
             default:
                 System.out.println("ERROR: This should not be possible!");
                 break;
         }
         
         // If login fails, notify the user
         if(loginSuccessful == null){
            System.out.println("ERROR - Try again or create new account!");
         }
         
         clear();
         
         // Reservation menu for logged-in users
         while(loginSuccessful != null && continueReservationMenu){
                        
            clear();
            displayIntro();            
            displayInMenu(); 
            
            System.out.println();
         
            menuChoice = getUserMenuInput(input);
         
            switch (menuChoice)
            {
                // Add a new reservation
                case OPT_ONE:
                    addReservation(userRes, loginSuccessful, input);
                    break;
                    
                // Edit existing reservations
                case OPT_TWO:
                    if(subMenuEdit(userRes, loginSuccessful, input)){
                        continueReservationMenu = false;
                        System.out.println("Quitting program..."); 
                        userInf.saveData(DATA_FILE_NAME);
                        userRes.saveData(FORMS_FILE_NAME);
                        System.out.println("\nTHANKS FOR USING THE "
                            + "PK HOTELS' ONLINE RESERVATIONS\n");
                    }                    
                    break;
                    
                // Cancel a reservation
                case OPT_THREE:
                   System.out.println("Enter your reservation pnr number");
                   String pnrNumb1 = input.nextLine();
                    cancelReservation(userRes, pnrNumb1, input);
                    break;
                    
                // Display reservation details    
                case OPT_FOUR:
                   System.out.println("Enter your reservation pnr number");
                   String pnrNumb = input.nextLine();
                    userRes.displayForm(userRes.findForm(pnrNumb), input);
                    break;
                    
                // Return to the login menu    
                case OPT_FIVE:
                    continueReservationMenu = false;
                    break;
                    
                // Save data and exit    
                case QUIT_PROGRAM:
                    continueReservationMenu = false;
                    System.out.println("Quitting program..."); 
                    userInf.saveData(DATA_FILE_NAME);
                    userRes.saveData(FORMS_FILE_NAME);
                    System.out.println("\nTHANKS FOR USING THE "
                            + "PK HOTELS' ONLINE RESERVATIONS\n");
                    break;
                    
                default:
                    System.out.println("ERROR: This should not be possible!");
                    break;
            }
         }   
      }
   }
     
   /**
     * Displays the introductory message.
     */
   public static void displayIntro(){
      System.out.println("                                        \\  /\n" +
            "                  __                                     \\/\n" +
            "     _   ---===##===---_________________________--------------  _"
            + "\n    [ ~~~=================###=###=###=###=###================="
            + "~~ ]\n    /  ||  | |~\\  ;;;;     PKP    ;;;  ET22-689  ;;;;  /~"
            + "| |  ||  \\\n   /___||__| |  \\ ;;;;            [_]            "
            + ";;;; /  | |__||___\\\n   [\\        |__| ;;;;  ;;;; ;;;; ;;; ;;;"
            + "; ;;;;  ;;;; |__|        /]\n  (=|    ____[-]___________________"
            + "____________________[-]____Kraq|=)\n  /  /___/|#(__)=o########o="
            + "(__)#||___|#(__)=o#########o=(__)#|\\___\\\n _________-=\\__/="
            + "--=\\__/=--=\\__/=-_____-=\\__/=--=\\__/=--=\\__/=-______");
      System.out.println(" WELOME TO PK TRAINS' ONLINE RESERVATIONS ");
   }
   
   /**
     * Displays the login menu.
     */
   private static void displayMenu(){
      System.out.println();
      System.out.println("Login Page");
      System.out.println("--------------------------------------");
      System.out.println(OPT_ONE + " - Enter Login Details");
      System.out.println(OPT_TWO + " - Create Account");
      System.out.println("--------------------------------------");
      System.out.println( QUIT_PROGRAM + ". Quit Program");
      System.out.println("--------------------------------------");
   }
   
   /**
     * Displays the reservation menu for logged-in users.
     */
   private static void displayInMenu(){
      System.out.println();
      System.out.println("RESERVATIONS");
      System.out.println("--------------------------------------");
      System.out.println(OPT_ONE + " - Submit Reservation Forms");
      System.out.println(OPT_TWO + " - Edit Reservation");
      System.out.println(OPT_THREE + " - Cancel Reservation ");
      System.out.println(OPT_FOUR + " - Display Reservation");
      System.out.println("--------------------------------------");      
      System.out.println(OPT_FIVE + " - Return To The Previous Menu");
      System.out.println( QUIT_PROGRAM + ". Quit Program");
      System.out.println("--------------------------------------");
   }
   
   public static boolean subMenuEdit(Reservation userR, UserDetails userD, 
         Scanner scanner) {
      boolean inMenu = true;
      boolean quitProg = false;
      int trainNum = -1;
      Forms userReser = null;
      String user1Email = userD.getEmail();
      String user1IdNum = userD.getIdNum();
      userReser = userR.findForms(user1Email, user1IdNum);
      
        while (inMenu) {
            System.out.println("RESERVATIONS");
            System.out.println("--------------------------------------");
            System.out.println(OPT_ONE + " - Edit Train Number");
            System.out.println(OPT_TWO + " - Edit Train Class");
            System.out.println(OPT_THREE + " - Edit Date of Jorney ");
            System.out.println(OPT_FOUR + " - Edit Depature Location ");
            System.out.println(OPT_FIVE + " - Edit Arrival Location ");
            System.out.println("--------------------------------------");      
            System.out.println(OPT_SIX + " - Return To The Previous Menu");
            System.out.println( QUIT_PROGRAM + ". Quit Program");
            System.out.println("--------------------------------------");
            int choice = getUserMenuInput(scanner);

            switch (choice) 
            {
                // Login to system
                case OPT_ONE:
                   System.out.println();
                   System.out.println("Set new train");
                    userReser.setTrainNumName(scanner);
                    System.out.println();
                    
                    break;
                // removes an item from the reading log based on name
                case OPT_TWO:
                   System.out.println();
                   System.out.println("Set new train class");
                    userReser.setClassType(scanner);
                    System.out.println();
                    break;
                // quits out of the program and cleans things up if needed
                case OPT_THREE:
                   System.out.println();
                   System.out.println("Set new date for reservation");
                    userReser.setDateOfJ(scanner);
                    System.out.println();
                    break;
                case OPT_FOUR:
                   System.out.println();
                   System.out.println("Set new reservation departure location");
                    userReser.setoriginLoc(scanner);
                    System.out.println();
                    break;
                case OPT_FIVE:
                   System.out.println();
                   System.out.println("Set new reservation arrival location");
                    userReser.setDestinationLoc(scanner);
                    System.out.println();
                    break;
                case OPT_SIX:
                    inMenu = false;
                    break;
                              
                case QUIT_PROGRAM:
                    inMenu = false;
                    quitProg = true;
                    break;
                default:
                    System.out.println("ERROR: This should not be possible!");
                    break;
            }
              
        }
        return quitProg;
    }
   
   /**
     * Prompts the user for menu input.
     *
     * @param input the Scanner object
     * @return the validated menu choice
     */
   private static int getUserMenuInput(Scanner input){
        int intChoice = -1;
        
        while (intChoice < LOW_MENU_CHOICE || intChoice > HIGH_MENU_CHOICE)
        {
            System.out.print("Enter menu option number: ");
            String userChoice = input.nextLine();

            while (!isPositiveInteger(userChoice))
            {
                System.out.println("ERROR: Invalid choice!");
                System.out.print("Enter menu option number: ");
                userChoice = input.nextLine();
            }

            intChoice = Integer.parseInt(userChoice);

            if (intChoice < LOW_MENU_CHOICE || intChoice > HIGH_MENU_CHOICE)
            {
                System.out.println("ERROR: Invalid choice!");
            }
        }

        return intChoice;
    }
   
   
   /**
     * Validates whether a string represents a positive integer.
     *
     * @param strNum the input string
     * @return true if valid, false otherwise
     */
    public static boolean isPositiveInteger(String strNum)
    {
        final char LOW_INT_VALUE = '0';
        final char HIGH_INT_VALUE = '9';
        final String BAD_STRING = "";

        // if the string is null or empty, it is not valid
        if (strNum == null || strNum.equals(BAD_STRING))
        {
            return false;
        }

        // check each character, making sure it is between 0 and 9 inclusive
        for (int i = 0; i < strNum.length(); i++)
        {
            if (strNum.charAt(i) < LOW_INT_VALUE
                    || strNum.charAt(i) > HIGH_INT_VALUE)
            {
                return false;
            }
        }

        // all tests passed, so string contains a positive integer
        return true;
    }
   
    /**
     * Handles user account creation.
     *
     * @param userInfo the UserInfo object
     * @param input the Scanner object for user input
     */
    private static void createUserAccount(UserInfo userInformation, 
            Scanner input)
    {
        UserDetails userNew = new UserDetails();

        System.out.println("Create A New User");
        userNew.setUserName(input);
        userNew.setPassword(input);
        
        String user1Name = userNew.getUserName();
        String user1Pass = userNew.getPassword();

        UserDetails accountExist = userInformation.findUser(user1Name, 
              user1Pass);

        if (accountExist == null){
           userNew.setDateOfBirth(input);
            userNew.setFirstName(input);
            userNew.setLastName(input);
            userNew.setEmail(input);
            userInformation.addUser1(userNew);
            System.out.println("SUCCESS - account was successfully created!");
        }
        
        else{
            System.out.println("ACCOUNT ALREADY EXIST");
        }
        System.out.println();
    }
    
    /**
    * Adds a new reservation to the reservation system.
    * This method allows the user to submit details for a new reservation,
    * including train information, class type, journey date, departure location,
    * and destination. If a reservation for the user already exists, the method
    * will inform the user and suggest editing or canceling the existing 
    * reservation.
    *
    * @param userRes the Reservation object that holds all reservations in the 
    * system
    * @param userD the UserDetails object containing information about the 
    * currently logged-in user
    * @param input the Scanner object for capturing user input
    * @throws AWTException if an error occurs during the clearing of the console
    */
    private static void addReservation(Reservation userRes, UserDetails userD, 
            Scanner input) throws AWTException
    {
        Forms existingReservation = null;
        String user1Email = userD.getEmail();
        String user1IdNum = userD.getIdNum();
        Forms userResNew = null;
        
        // Check if a reservation already exists for the user
        existingReservation = userRes.findForms(user1Email, user1IdNum);
        
        if (existingReservation == null)        {
           clear();
           userResNew = new Forms(userD);
           System.out.println("""
                       _____  ______  _____ ______ _______      ________ 
                      |  __ \\|  ____|/ ____|  ____|  __ \\ \\    / /  ____|
                      | |__) | |__  | (___ | |__  | |__) \\ \\  / /| |__   
                      |  _  /|  __|  \\___ \\|  __| |  _  / \\ \\/ / |  __|  
                      | | \\ \\| |____ ____) | |____| | \\ \\  \\  /  | |____ 
                      |_|  \\_\\______|_____/|______|_|  \\_\\  \\/   |______|

                                                                           """);
           System.out.println("Submit a reservation forms:");
           
           // Collect reservation details
           userResNew.setTrainNumName(input);
           userResNew.setClassType(input);
           userResNew.setDateOfJ(input);
           userResNew.setoriginLoc(input);
           userResNew.setDestinationLoc(input);
           
           // Confirm and save the reservation
           System.out.println("Type 1 or Y to save reservations");
           String userInput = input.nextLine().trim().toLowerCase();
           
           if(userInput.equals("1") || (userInput.equals("y"))){
              userRes.addForm1(userResNew);
              System.out.println("SUCCESS - reservation was successfully "
                    + "created!");
              userRes.displayForm(userResNew, input);           
           }
           else{
              System.out.println("FAILED - reservation was not created!");
           }
                       
        }
        else{
            System.out.println("RESERVATION ALREADY EXIST...");
            System.out.println("Edit or Cancel Reservation");
            System.out.println("Your PNR number is: " + 
                  existingReservation.getPnrNum());
            System.out.println("Type 1 or Y to confirm");
           String userInput = input.nextLine().trim().toLowerCase();
            if(userInput.equals("1") || (userInput.equals("y"))){
              userRes.addForm1(userResNew);         
           }
        }
        System.out.println();
    }
    
    /**
    * Cancels an existing reservation for the currently logged-in user.
    * This method locates the reservation based on the user's details and 
    * deletes it
    * from the system if found. If no reservation exists, it informs the user.
    *
    * @param userRes the Reservation object that holds all reservations in 
    * the system
    * @param userD the UserDetails object containing information about the 
    * currently logged-in user
    * @param input the Scanner object for capturing user input
    */
    private static void cancelReservation(Reservation userRes,String pnrNum,
          Scanner input) {
       Forms existingReservation = null;

       // Check if a reservation exists for the user
       existingReservation = userRes.findForm(pnrNum);

       if (existingReservation != null) {
           // Confirm cancellation
           System.out.println("Are you sure you want to cancel your reservation? "
                 + "(Type 1or Y to confirm)");
           String confirmation = input.nextLine().trim().toLowerCase();

           if (confirmation.equals("1") || confirmation.equals("Y")) {
               userRes.removeForm(pnrNum);
               System.out.println("SUCCESS - Reservation successfully "
                     + "canceled!");
           } 
           else {
               System.out.println("Operation canceled. Your reservation was not"
                     + " deleted.");
           }
       }
       else {
        System.out.println("No reservation exists for your account to cancel.");
      }
   }
    
    /**
     * Clears the console screen using the Robot class.
     */
    public static void clear() throws AWTException {
      Robot rob = new Robot();
      try {
        rob.keyPress(KeyEvent.VK_CONTROL); // press "CTRL"
        rob.keyPress(KeyEvent.VK_L); // press "L"
        rob.keyRelease(KeyEvent.VK_L); // unpress "L"
        rob.keyRelease(KeyEvent.VK_CONTROL); // unpress "CTRL"
        Thread.sleep(1000); // add delay in milisecond
      } 
      catch (InterruptedException e) {
      }
   }

   
}

