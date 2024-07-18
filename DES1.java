import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.spec.SecretKeySpec;
import java.security.SecureRandom;
import java.util.Scanner;

class DES1 {
    private static byte[] raw;

    public DES1() {
        try {
            generateSymmetricKey();
            Scanner scanner = new Scanner(System.in);
            System.out.print("Enter message to encrypt: ");
            String inputMessage = scanner.nextLine();
            scanner.close();

            byte[] encrypted = encrypt(inputMessage.getBytes());
            System.out.println("Encrypted message: " + new String(encrypted));

            byte[] decrypted = decrypt(encrypted);
            System.out.println("Decrypted message: " + new String(decrypted));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void generateSymmetricKey() throws Exception {
        KeyGenerator kgen = KeyGenerator.getInstance("DES");
        kgen.init(56, new SecureRandom());
        raw = kgen.generateKey().getEncoded();
        System.out.println("DES Symmetric key = " + new String(raw));
    }

    private static byte[] encrypt(byte[] clear) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.ENCRYPT_MODE, new SecretKeySpec(raw, "DES"));
        return cipher.doFinal(clear);
    }

    private static byte[] decrypt(byte[] encrypted) throws Exception {
        Cipher cipher = Cipher.getInstance("DES");
        cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(raw, "DES"));
        return cipher.doFinal(encrypted);
    }

    public static void main(String[] args) {
        new DES1();
    }
}
