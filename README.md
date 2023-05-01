# Efficient Wesolowski VDF

VDF-Wesolowski-Java is a Java implementation of the Wesolowski verifiable delay function (VDF),
which is a cryptographic primitive that allows one to prove that a certain amount of time
has passed between two events. This implementation is based on 
[this](https://www.researchgate.net/publication/349760306_Implementation_Study_of_Two_Verifiable_Delay_Functions)
research paper.

### Usage
To use WesolowskiVDF in your Java project, simply add the '**WesolowskiVDF.java**' file to your source directory. 
The implementation provides the following methods:

- '**void setup(int lambda, int k)**': Initializes the VDF with an RSA security parameter '**lambda**' and a security
parameter '**k**'.

- '**EvalResult eval(byte[] m, int T)**': Computes the VDF on a given message '**m**' for a given delay time '**T**'
and returns an '**EvalResult**' object containing the proof and the output.

- '**boolean verify(byte[] m, int T, BigInteger l, BigInteger proof)**': Verifies that the proof is valid for the 
given message '**m**' and delay time '**T**'.

Here's an example of how to use WesolowskiVDF:

```java
class Main {
    public static void main(String[] args) {

        WesolowskiVDF vdf = new WesolowskiVDF();
        vdf.setup(2048, 256);

        byte[] message = "hello".getBytes();
        int time = 10;

        EvalResult result = vdf.eval(message, time);

        boolean isValid = vdf.verify(message, time, result.getL(), result.getProof());
    }
}
```
