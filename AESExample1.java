import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import java.util.Base64;
import java.util.Scanner;

public class AESExample1 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Enter the text to encrypt:");
        String originalText = scanner.nextLine();
        scanner.close();

        SecretKey secretKey = KeyGenerator.getInstance("AES").generateKey();
        System.out.println("Secret Key (Base64): " + Base64.getEncoder().encodeToString(secretKey.getEncoded()));

        String encryptedText = encrypt(originalText, secretKey);
        String decryptedText = decrypt(encryptedText, secretKey);

        System.out.println("Original: " + originalText);
        System.out.println("Encrypted: " + encryptedText);
        System.out.println("Decrypted: " + decryptedText);
    }

    private static String encrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.ENCRYPT_MODE, key);
        return Base64.getEncoder().encodeToString(cipher.doFinal(data.getBytes()));
    }

    private static String decrypt(String data, SecretKey key) throws Exception {
        Cipher cipher = Cipher.getInstance("AES");
        cipher.init(Cipher.DECRYPT_MODE, key);
        return new String(cipher.doFinal(Base64.getDecoder().decode(data)));
    }
}
