package util;

public class EmailUtils {
    public static void validate(String email){
        if(email == null || email.isBlank()) throw  new IllegalArgumentException("Email cannot be empty");
        if(!email.matches("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+$")){
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
        if(email.contains("..")){
            throw new IllegalArgumentException("Invalid email format: " + email);
        }
    }
}
