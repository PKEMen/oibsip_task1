
import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Scanner;


/**
 * UserAccountManager handles user account operations such as login,
 * adding users, finding users, and loading/saving user data.
 * 
 * @author PK Ewusie-Mensah
 */
public class UserInfo {
   
   private final LinkedList<UserDetails> onlineUsers;
   
   //constructor
   UserInfo(){
      onlineUsers = new LinkedList<>();
   }
   
   /**
     * Allows a user to log in by verifying their credentials.
     *
     * @param input a Scanner object for user input
     * @return the UserDetails object of the logged-in user, or null if login fails
     */
   public UserDetails login(Scanner input){
      System.out.println("  _       ____   ____ ___ _   _ \n" +
                           " | |     |  _ \\ / ___|_ _| \\ | |\n" +
                           " | |     | | | | |  _ | ||  \\| |\n" +
                           " | |___  | |_| | |_| || || |\\  |\n" +
                           " |_____| |____/ \\____|___|_| \\_|\n" +
                           "                                ");
      System.out.println("ENTER YOUR LOGIN DETAILS");
      System.out.println("Enter Your Username");
      String user1Name = input.nextLine();
      System.out.println("Enter Your Password");
      String user1Pass = input.nextLine();
        
      UserDetails user1 = this.findUser(user1Name, user1Pass);
      
      return user1;
   }

   /**
     * Finds a user based on their username and password.
     *
     * @param username the username to search for
     * @param password the password to match
     * @return the UserDetails object if found, or null if not found
     */
   UserDetails findUser(String userName, String password){
      UserDetails user1 = null;
      Iterator<UserDetails> iter = onlineUsers.iterator();
       
      while(iter.hasNext()){
         user1 = iter.next();
        
         if(user1.getUserName().equals(userName) && 
               user1.getPassword().equals(password)){
            return user1;
         }
      }
      return null;
    }
   
   /**
     * Finds a user based on their unique ID.
     *
     * @param userId the ID of the user to search for
     * @return the UserDetails object if found, or null if not found
     */
   UserDetails findUser(String userIdNum){
      UserDetails user1 = null;
      Iterator<UserDetails> iter = onlineUsers.iterator();
       
      while(iter.hasNext()){
         user1 = iter.next();
         System.out.println(user1.getIdNum());
        
         if(user1.getIdNum().equals(userIdNum)){
            return user1;
         }
      }
      return null;
    }
   
   /**
     * Adds a new user to the account list.
     *
     * @param userDet the UserDetails object to add
     * @return true if the user is successfully added, false otherwise
     */   
   public boolean addUser1(UserDetails userDet){
      boolean userAdded = false;
      
      if(userDet == null){
         userAdded = false;
       }
       else{
          onlineUsers.add(0, userDet);
          userAdded = true;
       }
       
       return userAdded;
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
       UserDetails userDeti = null;
       try{
          Scanner loadFile = new Scanner(loadingFile);
          if(!loadFile.hasNextLine()){
             System.out.println("No Current Users... Select Create account"
                   + "\n");
          }
          while(loadFile.hasNextLine()){
            String line = loadFile.nextLine();
            
            lineValues = line.split("###");
            if(lineValues.length == 7){      
                 userDeti = new UserDetails(lineValues[0], lineValues[1],
                       lineValues[2], lineValues[3], lineValues[4],
                       lineValues[5], lineValues[6]);
                  onlineUsers.add(onlineUsers.size(), userDeti);
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
          
          UserDetails userDe = null;
          Iterator<UserDetails> iter = onlineUsers.iterator();
       
          while(iter.hasNext()){
            userDe = iter.next();
            fileWrite.println(userDe.toFileFormat());
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



