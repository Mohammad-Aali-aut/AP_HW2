import java.util.ArrayList;
import java.util.Scanner;

public class hamrahbank {
    static ArrayList<String[]> users = new ArrayList<>();
    static String[] loginUser = null;
    static long nextCardNumber = 6037000000000000L;
    
    private static String[] findUsername(String username){
        int flag = 0;
        String[] u = new String[7];
        for (int i=0; i<users.size(); i++){
            if (users.get(i)[0].equals(username)){
                flag = 1;
                u = users.get(i);
                break;
            
            }
        }
        if (flag == 0){
            return null;
        }
        else{
            return u;
        }
    }
    private static String[] findCardnum(String cardNumber) {
        int flag = 0;
        String[] u = new String[7];
        for (int i=0; i<users.size(); i++){
            if (users.get(i)[3].equals(cardNumber)){
                flag = 1;
                u = users.get(i);
                break;
            
            }
        }
        if (flag == 0){
            return null;
        }
        else{
            return u;
        }
    }
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

        if (findUsername(username)!=null){
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

        String cardNumber = String.valueOf(nextCardNumber);
        nextCardNumber += 1;
        String balance = "0";
        String[] user = new String[7];
        user[0] = username;
        user[1] = password;
        user[2] = fullName;
        user[3] = cardNumber;
        user[4] = phoneNumber;
        user[5] = email;
        user[6] = balance;
        users.add(user);
        System.out.println("Registered successfully.");
        System.out.println("Assigned card number: " + cardNumber);
    }

    private static void Login(String[] splits){
        String username = splits[1];
        String password = splits[2];
        if (splits.length != 3) {
            System.out.println("Error: invalid login command.");
            return;
        }

        String[] user = findUsername(username);
        if (user==null){
            System.out.println("Error: invalid username or password.");
            return;
        }

        if (!user[1].equals(password)){
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
                System.out.println("Current balance: " + loginUser[6]);

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
            double newBalance = Double.parseDouble(loginUser[6]) + Double.parseDouble(splits[1]);
            loginUser[6] = String.valueOf(newBalance);
            System.out.println("Deposit successful. Current balance: " + loginUser[6]);
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
            if (Double.parseDouble(splits[1])>Double.parseDouble(loginUser[6])){
                System.out.println("Error: insufficient balance.");
            }

            else{
                double newBalance = Double.parseDouble(loginUser[6]) - Double.parseDouble(splits[1]);
                loginUser[6] = String.valueOf(newBalance);
                System.out.println("Withdrawal successful. Current balance: " + loginUser[6]);
            }
        }
    }

    private static void Transfer(String[] splits) {
        String[] User_dest = findCardnum(splits[1]);
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
        else if (Double.parseDouble(splits[2])>Double.parseDouble(loginUser[6])){
            System.out.println("Error: insufficient balance.");
            
        }

        else{
            double newBalance_send = Double.parseDouble(loginUser[6]) - Double.parseDouble(splits[2]);
            loginUser[6] = String.valueOf(newBalance_send);
            double newBalance_dest = Double.parseDouble(User_dest[6]) + Double.parseDouble(splits[2]);
            User_dest[6] = String.valueOf(newBalance_dest);
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
