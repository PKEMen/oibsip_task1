import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Scanner;


/**
 * Represents a form for handling train reservations.
 * Allows users to select train details, travel class, journey date, 
 * and locations, and generates a unique PNR number.
 * 
 * @author PK Ewusie-Mensah
 */
public class Forms {
   
   private HashMap<Integer, String> trainDetails = new HashMap<>();
   private UserDetails usersDet;
   private int trainNum;
   private String pnrNum;
   private String trainName;
   private String classType;
   private String dateOfJ;
   private String originLoc;
   private String destinationLoc;
   
   Random randm = new Random();
   StringBuilder randomLetters = new StringBuilder();
   
   // defines constants for menu item choices
    private static final int OPT_ONE = 1;
    private static final int OPT_TWO = 2;    
    private static final int OPT_THREE = 3; 
    
    private static final String CLASS_ONE = "First Class";
    private static final String CLASS_TWO = "Business Class";    
    private static final String CLASS_THREE = "Economy Class"; 
   
   /**
     * Constructs a Forms object and initializes train details.
     * 
     * @param users1 The user details associated with the reservation.
     */
   public Forms(UserDetails users1){
      this.usersDet = users1;
      
      // Generate unique pnr
      String randNum = users1.getIdNum().substring(users1.getIdNum().length() 
            - 4,
            users1.getIdNum().length() - 1);
        for (int i = 0; i < 3; i++) {
            char randomLetter = (char) ('A' + randm.nextInt(26));  
            randomLetters.append(randomLetter);
        }
        
        randomLetters.append(randNum);
        this.pnrNum = randomLetters.toString();
      
        //initialize train details
      trainDetails.put(50, "Cardinal (New York City to Chicago)");
      trainDetails.put(7, "Empire Builder (Chicago to Seattle)");
      trainDetails.put(27, "Empire Builder (Chicago to Portland)");
      trainDetails.put(91, "Silver Star (New York City to Miami)");
      trainDetails.put(3, "Southwest Chief (Chicago to Los Angeles)");
      trainDetails.put(11, "Coast Starlight (Seattle to Los Angeles)");
      trainDetails.put(6, "California Zephyr (Chicago to San Francisco)");
      trainDetails.put(79, "Carolinian (New York City to Charlotte)");
      trainDetails.put(66, "Northeast Regional (Boston to Washington, D.C.)");
   }
   
   /**
     * Overloaded constructor to initialize reservation details directly.
     * 
     * @param users1 The user details associated with the reservation.
     * @param pnrNum The unique PNR number.
     * @param trainNum The train number.
     * @param trainName The train name.
     * @param classType The travel class.
     * @param dateOfJ The date of journey.
     * @param originLoc The origin location.
     * @param destLoc The destination location.
     */
   public Forms(UserDetails users1, String pnrNum, int trainNum,
         String trainName, String classType, String dateOfJ, String originLoc, 
         String destLoc)
   {
      this.usersDet = users1;
      this.pnrNum = pnrNum;
      this.trainNum  = trainNum;
      this.trainName = trainName;
      this.classType = classType;
      this.dateOfJ = dateOfJ;
      this.originLoc = originLoc;
      this.destinationLoc = destLoc;
      
      //initialize train details
      trainDetails.put(50, "Cardinal (New York City to Chicago)");
      trainDetails.put(7, "Empire Builder (Chicago to Seattle)");
      trainDetails.put(27, "Empire Builder (Chicago to Portland)");
      trainDetails.put(91, "Silver Star (New York City to Miami)");
      trainDetails.put(3, "Southwest Chief (Chicago to Los Angeles)");
      trainDetails.put(11, "Coast Starlight (Seattle to Los Angeles)");
      trainDetails.put(6, "California Zephyr (Chicago to San Francisco)");
      trainDetails.put(79, "Carolinian (New York City to Charlotte)");
      trainDetails.put(66, "Northeast Regional (Boston to Washington, D.C.)");
   }
   
   /**
     * Prompts the user to select a train number and sets the train name 
     * accordingly.
     * 
     * @param input A Scanner object for reading user input.
     */   
   public void setTrainNumName(Scanner input){
      int trainNumb = -1;
      boolean valid = false;
      
      while(!valid){
         System.out.println("LIST OF TRAINS AND THEIR NUMBERS");
         for (Map.Entry<Integer, String> entry : trainDetails.entrySet()) {
            System.out.println("      Train " + entry.getKey() + ": " 
                  + entry.getValue());
        }
         System.out.println();
         System.out.println("Enter your train number:");

         trainNumb = input.nextInt();


         if(trainDetails.containsKey(trainNumb)){
            valid = true;
         }
         else{
             System.out.println("Train number " + trainNumb + " is invalid. "
                   + "Please check and try again.");
         }
      }
      this.trainNum = trainNumb;
      this.trainName = trainDetails.get(trainNumb);
      System.out.println("The train is: " + trainName);
      System.out.println();
      
   }
   
   public void setTrainName(){
      this.trainName = trainDetails.get(trainNum);
      
      System.out.println("The train is: " + trainName);
   }
   
   /**
     * Sets the travel class based on user input.
     * 
     * @param input A Scanner object for reading user input.
     */
   public void setClassType(Scanner input){
      String classType = null;
      int menuChoice = 0;
      
      
      while (menuChoice != -1)
      {
         displayMenu();
         
         System.out.println();
         
         menuChoice = getUserMenuInput(input);
         
         switch (menuChoice)
         {
            // Login to system
            case OPT_ONE:
                 classType = CLASS_ONE; 
                 break;
             // removes an item from the reading log based on name
             case OPT_TWO:
                 classType = CLASS_TWO; 
                 break;
             // quits out of the program and cleans things up if needed
             case OPT_THREE:
                 classType = CLASS_THREE; 
                 break;
             default:
                 System.out.println("ERROR: This should not be possible!");
                 break;
         }
         menuChoice = -1;
      }
      
      this.classType = classType;
   }
   
   /**
     * Prompts the user to set the date of journey in a valid format.
     * 
     * @param input A Scanner object for reading user input.
     */
   public void setDateOfJ(Scanner input){
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
        dateFormat.setLenient(false);  

        Date dateOfJor = null;
        boolean valid = false;
        while (!valid) {
            System.out.print("Enter the date for your reservation (dd/MM/yyyy):"
                  );
            String inputDate = input.nextLine();

            try {
                dateOfJor = dateFormat.parse(inputDate); //Try to parse theinput
                valid = true;  // Input is valid if parsing succeeds
            }
            catch (ParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
            if(valid){
               if (!isOlderThanCurrent(dateOfJor)) {
                  System.out.println("Reservation Date Too Early");
                  valid = false;
               }
            }
            
        }
        this.dateOfJ = dateFormat.format(dateOfJor);
        
    }
   
   /**
     * Prompts the user to set the origin location, ensuring valid input.
     * 
     * @param scanner A Scanner object for reading user input.
     */
    public void setoriginLoc(Scanner scanner) {
        String location;
        while (true) {
            System.out.print("Enter the location you are coming from: ");
            location = scanner.nextLine().trim();

            if (location.isEmpty()) {
                System.out.println("Invalid input. The location cannot be "
                      + "empty. Please try again.");
            } else {
                // Convert input to Title Case
                location = toTitleCase(location);
                break;
            }
        }
        this.originLoc = location;
    }
    
    /**
     * Prompts the user to set the destination location, ensuring valid input.
     * 
     * @param scanner A Scanner object for reading user input.
     */
    public void setDestinationLoc(Scanner scanner) {
        String location;
        while (true) {
            System.out.print("Enter the location you are travelling to: ");
            location = scanner.nextLine().trim();

            if (location.isEmpty()) {
                System.out.println("Invalid input. The location cannot be empty."
                      + " Please try again.");
            } else {
                // Convert input to Title Case
                location = toTitleCase(location);
                break;
            }
        }
        this.destinationLoc = location;
    }
    
    public String getPnrNum(){
       return pnrNum;
    }
    
    public UserDetails getUsersDet(){
       return usersDet;
    }
    public int getTrainNum(){
       return trainNum;
    }
    
    public String getTrainName(){
       return trainName;
    }
    public String getClassType(){
       return classType;
    }
    
    public String getDateofJ(){
       return dateOfJ;
    }

    public String getOriginLoc(){
       return originLoc;
    }
    
    public String getDestinationLoc(){
       return destinationLoc;
    }
    
    /**
     * Converts a string to Title Case, where each word starts with a 
     * capital letter.
     * 
     * @param input The string to be converted.
     * @return The converted string in Title Case.
     */
    public static String toTitleCase(String input) {
        String[] words = input.split("\\s+");
        StringBuilder titleCase = new StringBuilder();

        for (String word : words) {
            if (word.length() > 0) {
                titleCase.append(Character.toUpperCase(word.charAt(0)))
                         .append(word.substring(1).toLowerCase())
                         .append(" ");
            }
        }
        return titleCase.toString().trim();
    }
   
   /**
     * Checks if a given date is not earlier than the current date.
     * 
     * @param dateOfJor The date to be validated.
     * @return True if the date is not earlier than today; otherwise, false.
     */ 
   public static boolean isOlderThanCurrent(Date dateOfJor) {
           Calendar today = Calendar.getInstance();
           Calendar doj = Calendar.getInstance();
           doj.setTime(dateOfJor);
           boolean isEarly = false;
           
           if (today.get(Calendar.DAY_OF_YEAR) < doj.get(Calendar.DAY_OF_YEAR)) {
               isEarly = true;
           }

           return isEarly;
       } 
   
   /**
     * Displays a menu for travel class selection.
     */
   private void displayMenu() {
      System.out.println("Train Classes");
      System.out.println("    " + OPT_ONE + " - First Class ");
      System.out.println("    " + OPT_TWO + " - Business Class ");
      System.out.println("    " + OPT_THREE + " - Economy Class ");
   }
   
   /**
     * Retrieves and validates user input for menu selection.
     * 
     * @param input A Scanner object for reading user input.
     * @return The valid menu choice as an integer.
     */
   private static int getUserMenuInput(Scanner input){
        String userChoice = "";
        int intChoice = -1;
        

        while (intChoice < 1 || intChoice > 3)
        {
            System.out.print("Choose your class type: ");
            input.nextLine();
            userChoice = input.nextLine();

            while (!isPositiveInteger(userChoice))
            {
                System.out.println("ERROR: Invalid choice!");
                System.out.print("Choose your class type: ");
                userChoice = input.nextLine();
            }

            intChoice = Integer.parseInt(userChoice);

            if (intChoice < 0 || intChoice > 4)
            {
                System.out.println("ERROR: Invalid choice!");
            }
        }

        return intChoice;
    }
   
   /**
     * Checks if a given string is a positive integer.
     * 
     * @param strNum The string to be checked.
     * @return True if the string is a positive integer; otherwise, false.
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
     * Formats the object data for file storage, using "###" as a delimiter.
     * 
     * @return A formatted string of the object data.
     */
   public String toFileFormat()
    {
        final String delimiter = "###";

        return usersDet.getIdNum() + delimiter
              + usersDet.getUserName() + delimiter
              + usersDet.getPassword() + delimiter
              + usersDet.getDateOfBirth() + delimiter
              + usersDet.getEmail() + delimiter
              + usersDet.getFName() + delimiter
              + usersDet.getLName() + delimiter
              + pnrNum + delimiter
              + trainNum + delimiter
              + trainName + delimiter
              + classType + delimiter
              + dateOfJ + delimiter
              + originLoc + delimiter
              + destinationLoc;
    }
}
