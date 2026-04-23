package util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordUtil {
    public static final BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
    public static String hashPassword(String rawPassword){
        return encoder.encode(rawPassword);
    }
}
