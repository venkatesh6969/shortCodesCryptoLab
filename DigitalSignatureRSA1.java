import java.security.*;
import java.util.Base64;
import java.util.Scanner;

public class DigitalSignatureRSA1 {
    public static void main(String[] args) throws Exception {
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair keyPair = keyGen.generateKeyPair();
        System.out.println("Private key: " + Base64.getEncoder().encodeToString(keyPair.getPrivate().getEncoded()));
        System.out.println("Public key: " + Base64.getEncoder().encodeToString(keyPair.getPublic().getEncoded()));

        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(keyPair.getPrivate());
        System.out.println("Enter text to sign:");
        String data = new Scanner(System.in).nextLine();
        signature.update(data.getBytes());
        byte[] digitalSignature = signature.sign();
        System.out.println("Signature: " + Base64.getEncoder().encodeToString(digitalSignature));

        Signature verifier = Signature.getInstance("SHA256withRSA");
        verifier.initVerify(keyPair.getPublic());
        verifier.update(data.getBytes());
        System.out.println("Signature verified: " + verifier.verify(digitalSignature));

        verifier.update((data + "tampered").getBytes());
        System.out.print("Tampered signature verified: " + verifier.verify(digitalSignature));
    }
}
