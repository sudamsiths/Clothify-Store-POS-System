package util;

import java.util.regex.Pattern;

public class ValidationUtil {
    
    private static final Pattern EMAIL_PATTERN = 
        Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
    
    private static final Pattern PHONE_PATTERN = 
        Pattern.compile("^[0-9]{10}$");
    
    private static final Pattern NIC_PATTERN = 
        Pattern.compile("^([0-9]{9}[vVxX]|[0-9]{12})$");
    
    public static boolean isValidEmail(String email) {
        return email != null && EMAIL_PATTERN.matcher(email).matches();
    }
    
    public static boolean isValidPhone(String phone) {
        return phone != null && PHONE_PATTERN.matcher(phone).matches();
    }
    
    public static boolean isValidNIC(String nic) {
        return nic != null && NIC_PATTERN.matcher(nic).matches();
    }
    
    public static boolean isNotEmpty(String value) {
        return value != null && !value.trim().isEmpty();
    }
    
    public static boolean isPositiveNumber(Double value) {
        return value != null && value > 0;
    }
    
    public static boolean isPositiveInteger(Integer value) {
        return value != null && value > 0;
    }
}