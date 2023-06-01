import java.util.Scanner;
import java.util.regex.Pattern;

public class Email {
    final private static String regex = "^[A-Za-z0-9+_.-]+@(.+)$";
    // Source https://www.javatpoint.com/java-email-validation
    
    // return true if email is valid
    public static boolean validateEmail(String email) {
        return Pattern.compile(regex).matcher(email).matches();
    }

    // ask email from user until valid
    public static String getEmail() {
        String eMail = new String();
        Scanner sc = new Scanner(System.in);
        while (!validateEmail(eMail)) {
            System.out.println("Email Address: ");
            eMail = sc.next();  
        }
        return eMail; 
    }
}

