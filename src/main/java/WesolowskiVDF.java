import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Objects;

public class WesolowskiVDF {

    private BigInteger N;

    /**
     *  Research paper that was used for this implementation
     *  https://www.researchgate.net/publication/349760306_Implementation_Study_of_Two_Verifiable_Delay_Functions
     *
     * /

    // VDF(setup, eval, verify)
    /*
     *  lambda - RSA security usually 2048 for high security
     *  k      - security parameter (typically between 128 and 256 bits is used) as an input
     */
    public void setup(int lambda, int k) {
        SecureRandom rand = new SecureRandom();
        BigInteger p = BigInteger.probablePrime(lambda / 2, rand);
        BigInteger q = BigInteger.probablePrime(lambda / 2, rand);
        N = p.multiply(q);
    }

    public EvalResult eval(byte[] m, int T) {
        BigInteger x = new BigInteger(hash(m));
        BigInteger y = x;
        for (int i = 0; i < T; i++) {
            y = y.pow(2).mod(N);
        }
        BigInteger l = hashPrime((x.add(y)).toByteArray());
        BigInteger proof = x.modPow(BigInteger.TWO.pow(T).divide(l), N);
        return new EvalResult(proof, l);
    }

    public boolean verify(byte[] m, int T, BigInteger l, BigInteger proof) {
        BigInteger x = new BigInteger(hash(m));
        BigInteger r = BigInteger.TWO.pow(T).mod(l);
        BigInteger y = modExp(proof,x,l,r,N);
        BigInteger xPlusY = x.add(y);

        System.out.println("Verify: " + hashPrime(xPlusY.toByteArray()));
        return Objects.equals(l, hashPrime(xPlusY.toByteArray()));
    }



    // HELPER FUNCTIONS BELLOW
    // note that this H output needs to be 2*k length
    // for example now if k = 256 we use SHA-512
    private byte[] hash(byte[] input) {
        try {
            MessageDigest md = MessageDigest.getInstance("SHA-256");
            return md.digest(input);
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException("SHA-256 not supported", e);
        }
    }

    private BigInteger hashPrime(byte[] m) {
        byte[] hash = hash(m);
        BigInteger hashVal = new BigInteger(1, hash);
        BigInteger prime = hashVal.abs().nextProbablePrime();
        return prime;
    }

    private BigInteger modExp(BigInteger x, BigInteger y, BigInteger a, BigInteger b, BigInteger N) {
        int h = Math.max(a.bitLength() + 1, b.bitLength() + 1);
        BigInteger z = BigInteger.ONE;
        BigInteger q = x.multiply(y).mod(N);
        for (int i = h - 1; i >= 0; i--) {
            z = z.multiply(z).mod(N);
            if (a.testBit(i) && !b.testBit(i)) {
                z = z.multiply(x).mod(N);
            } else if (!a.testBit(i) && b.testBit(i)) {
                z = z.multiply(y).mod(N);
            } else if (a.testBit(i) && b.testBit(i)) {
                z = z.multiply(q).mod(N);
            }
        }
        return z;
    }
}
