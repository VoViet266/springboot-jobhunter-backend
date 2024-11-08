package vn.hoidanit.jobhunter.util;


    import java.security.NoSuchAlgorithmException;
    import java.util.Base64;

    import javax.crypto.KeyGenerator;
    import javax.crypto.SecretKey;
    
    public class SecretKeyGenerator {
        // Hàm tạo secret key tự động với thuật toán HmacSHA256 và độ dài key là 256 bits (32 bytes) 
        public static String generateSecretKey() {
            try {
                KeyGenerator keyGenerator = KeyGenerator.getInstance("HmacSHA256");
                keyGenerator.init(256);
                SecretKey secretKey = keyGenerator.generateKey();
                return Base64.getEncoder().encodeToString(secretKey.getEncoded()); // Mã hóa key thành chuỗi Base64
            } catch (NoSuchAlgorithmException e) {
                throw new RuntimeException("Error generating secret key", e);
            }
        }
    }
    

