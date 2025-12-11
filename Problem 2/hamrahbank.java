import java.util.HashMap;
import java.util.Scanner;

public class hamrahbank {
    static class User{
        String username;
        String password;
        String fullName;
        String cardNumber;
        String phoneNumber;
        String email;
        String balance;
        public User(String username, String password, String fullName, String cardNumber, String phoneNumber, String email){
            this.username = username;
            this.password = password;
            this.fullName = fullName;
            this.cardNumber = cardNumber;
            this.phoneNumber = phoneNumber;
            this.email = email;
            this.balance = "0";
        }
    }
    static HashMap<String, User> usersByUsername = new HashMap<>();
    static HashMap<String, User> usersByCardNumber = new HashMap<>();
    static User loginUser = null;
    static long nextCardNumber = 6037000000000000L;    
    private static boolean CheckPhone(String phoneNumber){
        if (phoneNumber.length()!=11){
            return false;
        }
        else if (!phoneNumber.startsWith("09")){
            return false;
        }
        else{
            int flag = 0;
            for (int i=0; i<phoneNumber.length(); i++){
                if (!Character.isDigit(phoneNumber.charAt(i))){
                    flag = 1;
                    break;
                }
            }
            if (flag == 1){
                return false;
            }
            else{
                return true;
            }
        }
    }

    private static boolean CheckEmail(String email){
        int count = 0;
        for (int i=0; i<email.length(); i++){
            if (email.charAt(i) == '@'){
                count += 1;

            }

        }
        if (count!=1){
            System.err.println(count);
            return false;
        }

        else if (email.charAt(0)=='.'){
            return false;
        }

        else if (email.indexOf('@') == 0){
            return false;
        }
        else if (!email.substring(email.indexOf('@')+1).equals("aut.com")){
            return false;
        }
        else{
            return true;
        }

    }

    private static boolean CheckPassword(String password) {
        if (password.length()<8){
            return false;
        }

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;
        boolean hasSpecial = false;
        String specials = "@!&$?";

        for (int i=0; i<password.length(); i++){
            if (Character.isUpperCase(password.charAt(i))){
                hasUpper = true;
            }
            else if (Character.isLowerCase(password.charAt(i))){
                hasLower = true;
            } 
            else if (Character.isDigit(password.charAt(i))){
                hasDigit = true;
            }
            if (specials.indexOf(password.charAt(i))>=0){
                hasSpecial = true;
            }
        }

        if (!hasUpper || !hasLower || !hasDigit || !hasSpecial){
            return false;

        }
        else{
            return true;
        }
    }

    private static void Register(String[] splits){
        if (splits.length!=7) {
            System.out.println("Error: invalid register command.");
            return;
        }

        String username = splits[1];
        String password = splits[2];
        String firstName = splits[3];
        String lastName = splits[4];
        String phoneNumber = splits[5];
        String email = splits[6];
        String fullName = firstName + " " + lastName;

        if (usersByUsername.containsKey(username)){
            System.out.println("Error: username already exists.");
            return;
        }

        if (!CheckPhone(phoneNumber)){
            System.out.println("Error: invalid phone number.");
            return;
        }

        if (!CheckEmail(email)){
            System.out.println("Error: invalid email.");
            return;
        }

        if (!CheckPassword(password)){
            System.out.println("Error: invalid password.");
            return;
        }

        while (true){
            String cardNumber = String.valueOf(nextCardNumber);
            nextCardNumber += 1;
            if (!usersByCardNumber.containsKey(cardNumber)){
                User user = new User(username, password, fullName, cardNumber, phoneNumber, email);
                usersByUsername.put(username, user);
                usersByCardNumber.put(cardNumber, user);
                System.out.println("Registered successfully.");
                System.out.println("Assigned card number: " + cardNumber);
                break;
            }
        
        }

    }
    private static void Login(String[] splits){
        String username = splits[1];
        String password = splits[2];
        if (splits.length != 3) {
            System.out.println("Error: invalid login command.");
            return;
        }

        User user = usersByUsername.get(username);

        if (user==null){
            System.out.println("Error: invalid username or password.");
            return;
        }

        if (!user.password.equals(password)){
            System.out.println("Error: invalid username or password.");
            return;
        }

        loginUser = user;
        System.out.println("Login successful.");
    }

    private static void Show(String[] splits) {
        if (splits.length!=2){
            System.out.println("Error: invalid show command.");
        }
        else if (!splits[1].toLowerCase().equals("balance")){
            System.out.println("Error: invalid show command.");
            
        }
        else{
            if (loginUser==null){
                System.out.println("Error: You should login first.");
            }
            else{
                System.out.println("Current balance: " + loginUser.balance);

            }

        }
            
            
    } 
      
    private static void Deposit(String[] splits) {
        if (loginUser==null){
            System.out.println("Error: You should login first.");
            
        }

        else if (splits.length!=2){
            System.out.println("Error: invalid deposit command.");
            
        }

        else if (Double.parseDouble(splits[1])<=0){
            System.out.println("Error: amount must be positive.");
            
        }
        else{
            double newBalance = Double.parseDouble(loginUser.balance) + Double.parseDouble(splits[1]);
            loginUser.balance = String.valueOf(newBalance);
            System.out.println("Deposit successful. Current balance: " + loginUser.balance);
        }
    }

    private static void Withdraw(String[] splits) {
        if (loginUser==null){
            System.out.println("Error: You should login first.");
        }

        else if (splits.length != 2){
            System.out.println("Error: invalid withdraw command.");
            
        }

        else if (Double.parseDouble(splits[1])<=0){
            System.out.println("Error: amount must be positive.");
        }

        else{
            if (Double.parseDouble(splits[1])>Double.parseDouble(loginUser.balance)){
                System.out.println("Error: insufficient balance.");
            }

            else{
                double newBalance = Double.parseDouble(loginUser.balance) - Double.parseDouble(splits[1]);
                loginUser.balance = String.valueOf(newBalance);
                System.out.println("Withdrawal successful. Current balance: " + loginUser.balance);
            }
        }
    }

    private static void Transfer(String[] splits) {
        User User_dest = usersByCardNumber.get(splits[1]);;
        if (loginUser==null){
            System.out.println("Error: You shoulld login first.");
        }

        else if (splits.length!=3){
            System.out.println("Error: invalid transfer command.");
            
        }
        
        else if (Double.parseDouble(splits[2])<=0){
            System.out.println("Error: amount must be positive.");
        }
        else if (User_dest==null){
            System.out.println("Error: invalid card number.");
        }
        else if (Double.parseDouble(splits[2])>Double.parseDouble(loginUser.balance)){
            System.out.println("Error: insufficient balance.");
            
        }

        else{
            double newBalance_send = Double.parseDouble(loginUser.balance) - Double.parseDouble(splits[2]);
            loginUser.balance = String.valueOf(newBalance_send);
            double newBalance_dest = Double.parseDouble(User_dest.balance) + Double.parseDouble(splits[2]);
            User_dest.balance = String.valueOf(newBalance_dest);
            System.out.println("Transferred successfully.");
        }
    }


    private static void Logout() {
        if (loginUser==null) {
            System.out.println("Error: no user is logged in.");
        }
        else{
        loginUser = null;
        System.out.println("Logout successful.");
        }
    }

    public static void main(String[] args) {
        Scanner scn = new Scanner(System.in);
        while (true){
            String line = scn.nextLine();
            if (line.toLowerCase().equals("exit")){
                System.out.println("Goodbye!");
                break;
            }

            String[] splits = line.split(" ");
            if (line.toLowerCase().startsWith("register")){
                Register(splits);
            } 
            else if (line.toLowerCase().startsWith("login")){
                Login(splits);
            } 

            else if (line.toLowerCase().startsWith("show")){
                Show(splits);
            } 
            else if (line.toLowerCase().startsWith("deposit")){
                Deposit(splits);
            } 
            else if (line.toLowerCase().startsWith("withdraw")){
                Withdraw(splits);
            } 
            else if (line.toLowerCase().startsWith("transfer")){
                Transfer(splits);
            } 
            else if (line.toLowerCase().startsWith("logout")){
                Logout();
            } 
            else{
                System.out.println("Error: unknown command.");
            }
        }

       
    }


    
}
