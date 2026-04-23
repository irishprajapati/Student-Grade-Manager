package util;
import java.util.regex.Pattern; //java's autocompiled regex engine
public final class PhoneUtils { //no class can extend phone utils
    private static final String PHONE_REGEX = "^(98|97)\\d{8}$";
    private static final Pattern PHONE_PATTERN = Pattern.compile(PHONE_REGEX);

    private PhoneUtils(){
        throw new UnsupportedOperationException("Utility class");
    }
    public static boolean isValid(String phoneNumber){
        if(phoneNumber == null) return false;
        String cleaned = phoneNumber.replaceAll("\\s+", "");
        return PHONE_PATTERN.matcher(cleaned).matches();
    }
    public static String normalize(String phoneNumber){
        if(phoneNumber == null) throw new IllegalArgumentException("Phone number can't be empty");
        return phoneNumber.replaceAll("\\s+", "").trim();
    }
    public static String validateAndNormalize(String phoneNumber)
    {
        String normalized = normalize(phoneNumber);
        if(!PHONE_PATTERN.matcher(normalized).matches()){
            throw new IllegalArgumentException("Invalid phone number: " + phoneNumber);
        }
        return normalized;

    }
}
