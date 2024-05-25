import java.io.*;
import java.util.Scanner;

public class Login {
    private static final String usersFile = "users.txt";

    public static boolean login() {
        Scanner sc = new Scanner(System.in);
        String uname;
        String pword;
        int attempts = 0;

        while (attempts < 3) {
            System.out.println("  =======================  LOGIN FORM  =======================  ");
            System.out.print("ENTER USERNAME: ");
            uname = sc.next();
            System.out.print("ENTER PASSWORD: ");
            pword = sc.next();

            if (checkCredentials(uname, pword)) {
                System.out.println("WELCOME TO OUR SYSTEM !! YOUR LOGIN IS SUCCESSFUL");
                return true;
            } else {
                System.out.println("SORRY !!!!  LOGIN IS UNSUCCESSFUL");
                attempts++;
            }
        }

        System.out.println("Sorry you have entered the wrong username and password too many times!!!");
        return false;
    }

    private static boolean checkCredentials(String username, String password) {
        try (BufferedReader br = new BufferedReader(new FileReader(usersFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts[0].equals(username) && parts[1].equals(password)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users file: " + e.getMessage());
        }
        return false;
    }

    public static void signUp() {
        Scanner sc = new Scanner(System.in);
        System.out.print("Enter new username: ");
        String username = sc.next();
        System.out.print("Enter new password: ");
        String password = sc.next();

        if (isUsernameTaken(username)) {
            System.out.println("Username is already taken. Please choose a different username.");
            return;
        }

        try (BufferedWriter bw = new BufferedWriter(new FileWriter(usersFile, true))) {
            bw.write(username + "\t" + password + "\n");
            System.out.println("Sign up successful! You can now log in.");
        } catch (IOException e) {
            System.out.println("Error writing to users file: " + e.getMessage());
        }
    }

    private static boolean isUsernameTaken(String username) {
        try (BufferedReader br = new BufferedReader(new FileReader(usersFile))) {
            String line;
            while ((line = br.readLine()) != null) {
                String[] parts = line.split("\t");
                if (parts[0].equals(username)) {
                    return true;
                }
            }
        } catch (IOException e) {
            System.out.println("Error reading users file: " + e.getMessage());
        }
        return false;
    }
}
