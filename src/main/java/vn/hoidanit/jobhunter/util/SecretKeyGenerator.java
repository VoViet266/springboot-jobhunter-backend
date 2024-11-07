package vn.hoidanit.jobhunter.util;


    import javax.crypto.KeyGenerator;
    import javax.crypto.SecretKey;
    import java.security.NoSuchAlgorithmException;
    import java.util.Base64;
    
    public class SecretKeyGenerator {
        // Hàm tạo secret key tự động với thuật toán HmacSHA256 và độ dài key là 256 bits (32 bytes) 
        public static String generateSecretKey() {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
                keyGenerator.init(256); // Đặt độ dài key là 256 bits
                SecretKey secretKey = keyGenerator.generateKey();
                return Base64.getEncoder().encodeToString(secretKey.getEncoded()); // Mã hóa key thành chuỗi Base64
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error generating secret key", e);
            }
        }
    }
    

