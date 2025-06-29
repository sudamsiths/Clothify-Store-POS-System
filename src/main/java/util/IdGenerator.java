package util;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicInteger;

public class IdGenerator {
    
    private static final AtomicInteger customerCounter = new AtomicInteger(1);
    private static final AtomicInteger productCounter = new AtomicInteger(1);
    private static final AtomicInteger supplierCounter = new AtomicInteger(1);
    private static final AtomicInteger orderCounter = new AtomicInteger(1);
    
    public static String generateCustomerId() {
        return String.format("C%03d", customerCounter.getAndIncrement());
    }
    
    public static String generateProductId() {
        return String.format("P%03d", productCounter.getAndIncrement());
    }
    
    public static String generateSupplierId() {
        return String.format("S%03d", supplierCounter.getAndIncrement());
    }
    
    public static String generateOrderId() {
        String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd"));
        return String.format("O%s%03d", timestamp, orderCounter.getAndIncrement());
    }
    
    public static String incrementId(String currentId, String prefix) {
        if (currentId != null && currentId.startsWith(prefix)) {
            String numberPart = currentId.substring(prefix.length());
            try {
                int number = Integer.parseInt(numberPart);
                return String.format("%s%03d", prefix, number + 1);
            } catch (NumberFormatException e) {
                return prefix + "001";
            }
        }
        return prefix + "001";
    }
}