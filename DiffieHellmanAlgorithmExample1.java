import java.util.Scanner;

public class DiffieHellmanAlgorithmExample1 {
    public static void main(String[] args) {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter prime P: ");
        long P = sc.nextLong();
        System.out.print("Enter primitive root G: ");
        long G = sc.nextLong();
        System.out.print("Enter private key a (User1): ");
        long a = sc.nextLong();
        System.out.print("Enter private key b (User2): ");
        long b = sc.nextLong();

        long x = modPow(G, a, P);
        long y = modPow(G, b, P);

        long ka = modPow(y, a, P);
        long kb = modPow(x, b, P);

        System.out.println("User1's public key: " + x);
        System.out.println("User2's public key: " + y);
        System.out.println("User1's secret key: " + ka);
        System.out.println("User2's secret key: " + kb);
        System.out.println(ka == kb ? "Shared secrets are equal!" : "Shared secrets are NOT equal!");

        sc.close();
    }

    private static long modPow(long base, long exp, long mod) {
        return (long) Math.pow(base, exp) % mod;
    }
}
