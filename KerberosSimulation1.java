import java.security.*;
import java.util.*;

public class KerberosSimulation1 {
    public static void main(String[] args) throws Exception {
        Scanner scanner = new Scanner(System.in);
        KeyPairGenerator keyGen = KeyPairGenerator.getInstance("RSA");
        keyGen.initialize(2048);
        KeyPair asKeyPair = keyGen.generateKeyPair(), tgsKeyPair = keyGen.generateKeyPair();

        System.out.print("Enter client name: ");
        String clientName = scanner.nextLine();
        String tgt = requestTGT(clientName, asKeyPair.getPrivate());
        System.out.println("TGT received: " + tgt);

        System.out.print("Enter service name: ");
        String serviceName = scanner.nextLine();
        String st = requestST(clientName, serviceName, tgt, tgsKeyPair.getPrivate());
        System.out.println("ST received: " + st);

        boolean authenticated = authenticateWithService(serviceName, st, tgsKeyPair.getPublic());
        System.out.println("Authentication " + (authenticated ? "successful" : "failed"));

        String tamperedData = clientName + "tampered";
        boolean tamperedVerified = authenticateWithService(serviceName, tamperedData, tgsKeyPair.getPublic());
        System.out.println("Tampered authentication " + (tamperedVerified ? "successful" : "failed"));
        scanner.close();
    }

    public static String requestTGT(String clientName, PrivateKey asPrivateKey) throws Exception {
        return encrypt("TGT_for_" + clientName + "_issued_by_AS", asPrivateKey);
    }

    public static String requestST(String clientName, String serviceName, String tgt, PrivateKey tgsPrivateKey) throws Exception {
        if (!decrypt(tgt).startsWith("TGT_for_" + clientName)) throw new Exception("Invalid TGT");
        return encrypt("ST_for_" + clientName + "_to_access_" + serviceName, tgsPrivateKey);
    }

    public static boolean authenticateWithService(String serviceName, String st, PublicKey tgsPublicKey) throws Exception {
        try {
            return decrypt(st, tgsPublicKey).endsWith("_to_access_" + serviceName);
        } catch (Exception e) {
            return false;
        }
    }

    public static String encrypt(String data, PrivateKey privateKey) throws Exception {
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initSign(privateKey);
        signature.update(data.getBytes());
        return Base64.getEncoder().encodeToString(signature.sign()) + ":" + data;
    }

    public static String decrypt(String encryptedData, PublicKey publicKey) throws Exception {
        String[] parts = encryptedData.split(":");
        Signature signature = Signature.getInstance("SHA256withRSA");
        signature.initVerify(publicKey);
        signature.update(parts[1].getBytes());
        if (signature.verify(Base64.getDecoder().decode(parts[0]))) return parts[1];
        else throw new Exception("Invalid signature");
    }

    public static String decrypt(String encryptedData) {
        return encryptedData.split(":")[1];
    }
}
