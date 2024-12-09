import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;


/**
 * BookingManager manages train ticket bookings and operations such as
 * reservation, searching, displaying, loading, and saving reservation data.
 * 
 * @author PK Ewusie-Mensah
 */
public class Reservation {
   
   private final LinkedList<Forms> usersReservation;
   
   //Constructor
   Reservation(){
      usersReservation = new LinkedList<>();
   }
   
   /**
     * Logs a reservation by prompting the user for a PNR number.
     *
     * @param input a Scanner object for user input
     * @return the BookingForm object if found, null otherwise
     */
   public Forms reservationLog(Scanner input){
      boolean wasAdded = false;
      Forms user1 = null;
      String user1Num = null;

      System.out.println("  _____  ______  _____ ______ _______      ________ "
            + "\n" + " |  __ \\|  ____|/ ____|  ____|  __ \\ \\    / /  ____|\n"
            + " | |__) | |__  | (___ | |__  | |__) \\ \\  / /| |__   \n" +
            " |  _  /|  __|  \\___ \\|  __| |  _  / \\ \\/ / |  __|  \n" +
            " | | \\ \\| |____ ____) | |____| | \\ \\  \\  /  | |____ \n" +
            " |_|  \\_\\______|_____/|______|_|  \\_\\  \\/   |______|\n" +
            "                                                    \n" +
            "                                                  ");
      System.out.println("ENTER YOUR RESERVATION DETAILS");
      System.out.println("Enter Your PNR Number");
      user1Num = input.nextLine();
        
      user1 = this.findForm(user1Num);
      
      return user1;
   }

   /**
     * Finds a reservation by its PNR number.
     *
     * @param pnrNumber the PNR number to search for
     * @return the BookingForm object if found, null otherwise
     */
   Forms findForm(String pnrNum){
      Forms user1 = null;
      Iterator<Forms> iter = usersReservation.iterator();
       
      while(iter.hasNext()){
         user1 = iter.next();
        
         if(user1.getPnrNum().equals(pnrNum)){
             return user1;
         }
      }
      return null;
    }
   
   /**
     * Finds a reservation by email and ID number.
     *
     * @param email the email of the user
     * @param idNumber the ID number of the user
     * @return the BookingForm object if found, null otherwise
     */
   Forms findForms(String email, String idNum){
      Forms user1 = null;
      Iterator<Forms> iter = usersReservation.iterator();
       
      while(iter.hasNext()){
        user1 = iter.next();
        
         if(user1.getUsersDet().getEmail().equals(email) && 
               user1.getUsersDet().getIdNum().equals(idNum)){
            return user1;
         }
      }
      return null;
    }
   
   /**
     * Adds a new reservation to the booking list.
     *
     * @param userForm the BookingForm object to add
     * @return true if the booking was added successfully, false otherwise
     */
   public boolean addForm1(Forms userForm){
      boolean userAdded = false;
      
      if(userForm == null){
         userAdded = false;
       }
       else{
          usersReservation.add(0, userForm);
          userAdded = true;
       }
       
       return userAdded;
   }
   
   /**
     * Removes a reservation by its PNR number.
     *
     * @param pnrNumber the PNR number of the reservation to remove
     * @return the removed BookingForm object, or null if not found
     */
   Forms removeForm(String pnrNum){
      Forms user1 = null;
      Iterator<Forms> iter = usersReservation.iterator();
       
      while(iter.hasNext()){
         user1 = iter.next();
        
         if(user1.getPnrNum().equals(pnrNum)){
            iter.remove();
            return user1;
         }
      }
      return null;
    }
   
   /**
     * Displays the details of a reservation.
     *
     * @param booking the BookingForm object to display
     * @param input a Scanner object for user confirmation
     */
   public void displayForm(Forms userRes, Scanner input){
      System.out.println();
      System.out.printf("%25s \n", "RESERVATION");
      System.out.println("----------------------------------------------"
            + "--------------------------");
      System.out.printf("%-25s %-25s %-25s \n", "First Name:", "Last Name:", 
            "Email:");
      System.out.printf("%-25s %-25s %-25s \n", userRes.getUsersDet().getFName(), 
            userRes.getUsersDet().getLName(), userRes.getUsersDet().getEmail());
      System.out.println();
      System.out.printf("%-25s %-25s \n", "Train Number:", "Train Name:");
      System.out.printf("%-25s %-25s \n", userRes.getTrainNum(), 
            userRes.getTrainName());
      System.out.println();
      System.out.printf("%-25s %-25s \n", "Class: ", "Date:");
      System.out.printf("%-25s %-25s \n", userRes.getClassType(), 
            userRes.getDateofJ());
      System.out.println();
      System.out.printf("%-25s %-25s \n", "From:", "To:");
      System.out.printf("%-25s %-25s \n", userRes.getOriginLoc(), 
            userRes.getDestinationLoc());
      System.out.println();
      System.out.printf("%-25s", "Your PNR Number: ");
      System.out.printf("%s \n", userRes.getPnrNum());
      System.out.println("----------------------------------------------"
            + "--------------------------");
      System.out.println();
      System.out.println("Type 1 or Y to confirm you have your"
                          + " PNR number saved");
      
      String userInput2 = input.nextLine().trim().toLowerCase();
      if(userInput2.equals("1") || (userInput2.equals("y"))){
         System.out.println("SUCCESS");
      }
           
      
   }
   
   /**
     * Loads data from a file into the reading log.
     *
     * @param filename the name of the file to load data from.
     * @return true if the data was loaded successfully, false otherwise.
     */ 
   public boolean loadData(String filename){
       boolean isFileLoaded = false;
       File loadingFile = new File(filename);
       String[] lineValues;
       Forms userRes = null;
       try(Scanner loadFile = new Scanner(loadingFile)){
          if(!loadFile.hasNextLine()){
             System.out.println("No Current Reservations..."
                   + "\n");
          }
          while(loadFile.hasNextLine()){
            String line = loadFile.nextLine();
            lineValues = line.split("###");
            
            if(lineValues.length == 14){
               UserInfo user1 = new UserInfo();
               UserDetails userDet = new UserDetails(lineValues[0],
                     lineValues[1], lineValues[2], lineValues[3], lineValues[4], 
                     lineValues[5], lineValues[6]);
               userRes = new Forms(userDet, lineValues[7], 
                     Integer.parseInt(lineValues[8]), lineValues[9], 
                     lineValues[10], lineValues[11], lineValues[12], 
                     lineValues[13]);
                  usersReservation.add(usersReservation.size(), userRes);
               }
            else{
               System.out.println("ERROR: Illegal Line Format!");
               System.out.println("  Line Ignored: " + line);
            }
         }
            
         loadFile.close();
         isFileLoaded = true;    
       }
       catch(FileNotFoundException fnfe){
          System.out.println("ERROR! File could not be opened");
          isFileLoaded = false;
       }
       return isFileLoaded;
   }
    
    
    /**
     * Saves the reading log data to a file.
     *
     * @param filename the name of the file to save data to.
     * @return true if the data was saved successfully, false otherwise.
     */
     
      public boolean saveData(String filename){
       boolean dataIsSaved = true;
       File saveFile = new File(filename);
       try{
          PrintWriter fileWrite = new PrintWriter(saveFile);
          
          Forms userRe = null;
          Iterator<Forms> iter = usersReservation.iterator();
       
          while(iter.hasNext()){
            userRe = iter.next();
            fileWrite.println(userRe.toFileFormat());
          }
         
          dataIsSaved = true;
          fileWrite.flush();
          fileWrite.close();
       }
       catch(Exception e){
          System.out.println(filename + " File could not be created");
          dataIsSaved = false;
       }
       return dataIsSaved;
   }
      
}



