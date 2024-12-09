

import java.text.*;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;
import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Represents user details and provides methods to manage user information,
 * including validation for fields such as username, password, email, and 
 * date of birth.
 * 
 * This class also ensures the generation of a unique ID for each user 
 * upon creation.
 * @author PK Ewusie-Mensah
 */
public class UserDetails
{
    private String idNum;       // Unique identifier for the user
    private String userName;    // User's username
    private String passWord;    // User's password
    private String dateOfBirth; // User's date of birth
    private String fName;       // User's first name
    private String lName;       // User's last name
    private String email;       // User's email address
    Random rands = new Random();

    /**
     * Default constructor for UserDetails. 
     * Generates a unique ID based on the current timestamp and a random number.
     */
    public UserDetails()
    {
       userName = "";
        long timestamp = System.currentTimeMillis();
        Random random = new Random();
        int randomNum = random.nextInt(1000);  

        idNum = "USER" + timestamp + randomNum;
        
    }
   
    /**
     * Parameterized constructor for creating a UserDetails object with all 
     * fields.
     *
     * @param idnum1    Unique identifier for the user
     * @param userName  Username
     * @param passWord  Password
     * @param dOB       Date of birth
     * @param email     Email address
     * @param fName     First name
     * @param lName     Last name
     */
    public UserDetails(String idnum1, String userName, String passWord, 
          String dOB, String email, String fName, String lName)
    {
        this.idNum = idnum1;
        this.userName = userName;
        this.passWord = passWord;
        this.dateOfBirth = dOB;
        this.email = email;
        this.fName = fName;
        this.lName = lName;
    } 

    //getters
    public String getIdNum(){
       return idNum;
    }

     public String getUserName()
    {
        return userName;
    }
    
    public String getPassword()
    {
        return passWord;
    }
    
    public String getDateOfBirth()
    {
       return dateOfBirth;
    }
    public String getEmail(){
       return email;
    }
    
    public String getFName(){
       return fName;
    }
    
    public String getLName(){
       return lName;
    }
   
    //setters
    
    /**
     * Sets the username with validation ensuring it is longer than 5 characters
     * contains at least one uppercase letter and one number.
     *
     * @param input Scanner object for user input
     */
    public void setUserName(Scanner input){
       String username = null;
        boolean isValid = false;

        while (!isValid) {
            System.out.print("Enter a username: ");
            username = input.nextLine();
            
            if (!isValidUsername(username)) {
                System.out.println("Invalid username. Please try again.");
                System.out.println("Username must have more than 5 characters,"
                      + " contains uppercase and numbers");
                isValid = false;  
            } 
            else {
                isValid = true;
            }
        }
        this.userName = username;
    } 
    
    /**
     * Sets the password with validation ensuring it meets criteria:
     * minimum 8 characters, at least one uppercase letter, one number, and one 
     * special character.
     *
     * @param input Scanner object for user input
     */
    public void setPassword(Scanner input){
       String password = null;
       boolean valid = false;

       while (!valid) {
           System.out.print("Enter your password: ");
           password = input.nextLine();

            if (!isValidPassword(password)) {
                System.out.println("Invalid password. Password must...");
                System.out.println("- At least 8 characters");
                System.out.println("- Contains at least one uppercase letter");
                System.out.println("- Contains at least one number");
                System.out.println("- Contains at least one special character");
                valid = false;
            } else {
                valid = true;
            }
        }
       this.passWord = password;
        
   }
    
    /**
     * Sets the date of birth with validation ensuring the date is in the format
     * 'dd/MM/yyyy'
     * and the user is at least 21 years old.
     *
     * @param input Scanner object for user input
     */
    public void setDateOfBirth(Scanner input){
       SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");  
        dateFormat.setLenient(false);  

        Date dateOfBirth = null;
        boolean valid = false;
        
        while (!valid) {
            System.out.print("Enter your date of birth (dd/MM/yyyy): ");
            String inputDate = input.nextLine();

            try {
                dateOfBirth = dateFormat.parse(inputDate);  
                valid = true; 
            }
            catch (ParseException e) {
                System.out.println("Invalid date format. Please try again.");
            }
            if(valid){
               if (!isOlderThan21(dateOfBirth)) {
                  System.out.println("You are not older than 21.");
                  valid = false;
               }
            }
            
        }
        this.dateOfBirth = dateFormat.format(dateOfBirth);
    }
    
     /**
     * Sets the email address with validation for proper format.
     *
     * @param input Scanner object for user input
     */    
    public void setEmail(Scanner input){
       String emailIn = "";
       boolean valid = false;

      while(!valid){
         System.out.println("Enter your email address: ");
         emailIn = input.nextLine();
         
        if (emailIn == null || emailIn.trim().isEmpty()) {
            System.out.println("Email cannot be empty.");
        }
        else if (!isValidEmail(emailIn)) {
            System.out.println("Invalid email format.");
        }
        else{
           valid = true;
        }
      }
      this.email = emailIn;
    }
    
    /**
     * Sets the first name, ensuring it is not empty and converts it to title case.
     *
     * @param input Scanner object for user input
     */
    public void setFirstName(Scanner input){
       String firstName = null;
       boolean valid = false;
       
       while(!valid){
         System.out.println("Enter Your First Name: ");
         firstName = input.nextLine();
       
       
         if (firstName == null || firstName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty.");
         }
         else{
            valid = true;
         }
         
       }
       firstName = toTitleCase(firstName);
       this.fName = firstName;
    }
    
    /**
     * Sets the last name, ensuring it is not empty and converts it to title 
     * case.
     *
     * @param input Scanner object for user input
     */
    public void setLastName(Scanner input){
       String lastName = null;
       boolean valid = false;
       
       while(!valid){
         System.out.println("Enter Your Last Name: ");
         lastName = input.nextLine();
       
       
         if (lastName == null || lastName.trim().isEmpty()) {
            throw new IllegalArgumentException("First name cannot be empty.");
         }
         else{
            valid = true;
         }
         
       }
       lastName = toTitleCase(lastName);
       this.lName = lastName;
    }
    
   //Helper methods
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
    
     public static boolean isOlderThan21(Date dateOfBirth) {
        Calendar today = Calendar.getInstance();
        Calendar dob = Calendar.getInstance();
        dob.setTime(dateOfBirth);

        int age = today.get(Calendar.YEAR) - dob.get(Calendar.YEAR);

        if (today.get(Calendar.DAY_OF_YEAR) < dob.get(Calendar.DAY_OF_YEAR)) 
        {
            age--;
        }

        return age >= 21;
     } 
     
     private boolean isValidUsername(String username) {
        if (username.length() <= 5) {
            return false;
        }
        
        boolean hasUpperCase = false;
        boolean hasNumber = false;

        for (char c : username.toCharArray()) {
            if (Character.isUpperCase(c)) {
                hasUpperCase = true;
            }
            if (Character.isDigit(c)) {
                hasNumber = true;
            }
        }
        
        return hasUpperCase && hasNumber;
     }
     
    public static boolean isValidPassword(String password) {
        String regex = "^(?=.*[A-Z])(?=.*[0-9])(?=.*[!@#$%^&*()-+=]).{8,}$";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(password);
        return matcher.matches();
    }
     
    private boolean isValidEmail(String email) {
       final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$";
       final Pattern EMAIL_PATTERN = Pattern.compile(EMAIL_REGEX);
       
       return EMAIL_PATTERN.matcher(email).matches();
    }

    /**
     * Converts user details to a string format suitable for file storage.
     * 
     * @return Formatted string with user details
     */
    public String toFileFormat()
    {
        final String delimiter = "###";

        return idNum + delimiter
              + userName + delimiter
              + passWord + delimiter
              + dateOfBirth + delimiter
              + email + delimiter
              + fName + delimiter
              + lName + delimiter;
        
    }

   

    

}
