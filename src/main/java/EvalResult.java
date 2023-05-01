import java.math.BigInteger;

public class EvalResult {
    private final BigInteger proof;
    private final BigInteger lPrime;

    public EvalResult(BigInteger proof, BigInteger lPrime) {
        this.proof = proof;
        this.lPrime = lPrime;
    }

    public BigInteger getProof() {
        return proof;
    }

    public BigInteger getLPrime() {
        return lPrime;
    }
}
