# Wesolowski's Verifiable Delay Function (VDF) Implementation in Java

This repository contains an implementation of the Verifiable Delay Function (VDF) as proposed by Benjamin Wesolowski.

Verifiable Delay Functions are cryptographic primitives that take a specified amount of real time to compute, yet produce a unique output that can be efficiently verified. They are used in a variety of applications, such as proofs of sequential work, leader election in blockchain protocols, and preventing precomputation attacks.

My primary motivation behind this implementation was my personal interest in cryptography and its use in blockchain technology. Specifically, I needed a VDF for the next block producer election process in a simple blockchain project I was working on.

This repository contains an implementation of the Verifiable Delay Function (VDF) as proposed by Benjamin Wesolowski. This implementation is based on [this research paper](https://www.researchgate.net/publication/349760306_Implementation_Study_of_Two_Verifiable_Delay_Functions).


## Features

- Utilizes BigInteger for large number handling.
- Supports a variety of hash algorithms including SHA-256, SHA-512, SHA3-256, and SHA3-512.
- Efficient computation and verification of VDF proofs.
- Simple and easy to use API.

## Getting Started

This VDF implementation consists of the following Java classes:

1. `WesolowskiVDF`: This class provides methods to setup VDF parameters, compute the VDF function, and verify its proof.
2. `EvalResult`: This class encapsulates the proof and l-prime values computed by the VDF.
3. `Main`: This class provides a demonstration of how to use the `WesolowskiVDF` and `EvalResult` classes.

## Example

Here is a basic usage example:

```java
import java.math.BigInteger;

public class VDFExample {

    public static void main(String[] args) {
        WesolowskiVDF vdf = new WesolowskiVDF();
        vdf.setup(2048, "SHA3-256");

        String msg = "hello!";
        int T = 500000;   // sequential steps, approximately 10 seconds
        long start = System.currentTimeMillis();
        EvalResult result = vdf.eval(msg.getBytes(), T);
        long end = System.currentTimeMillis();
        System.out.println("Eval:   " + result.getLPrime());
        System.out.println("Time taken: " + (end - start) + " ms");

        start = System.currentTimeMillis();
        boolean bb = vdf.verify(msg.getBytes(), T, result.getLPrime(), result.getProof());
        end = System.currentTimeMillis();
        if (bb) {
            System.out.println("Valid!");
        } else {
            System.out.println("Invalid!");
        }
        System.out.println("Time taken: " + (end - start) + " ms");
    }
}
```

## Testing the VDF Implementation

In the `Main` class, you will find a demonstration of how the VDF is set up, evaluated, and verified. This example also includes a measure of the time taken to evaluate and verify the VDF.

You can test the integrity of the function by altering the input message `msg` between the evaluation and verification steps. The VDF is designed in such a way that even the smallest alteration to the input message should cause the verification to fail, demonstrating the robustness of this implementation against changes in input data.

Here's a snippet of code from the `Main` class demonstrating this:


```java
// In order to test if the VDF will recognize that the message has been altered,
// just change the message here, add something or delete one character

msg = "hello! altered"; // altered message
start = System.currentTimeMillis();
boolean bb = vdf.verify(msg.getBytes(), T, result.getLPrime(), result.getProof());
end = System.currentTimeMillis();
if (bb) {
    System.out.println("Valid!");
} else {
    System.out.println("Invalid!");
}
System.out.println("Time taken: " + (end - start) + " ms");

```

In this example, any alteration to the `msg` variable after the evaluation should cause the verification step to return Invalid!. This helps you understand how the VDF reacts to changes in input data.

Note: Ensure that the verification is performed with the original, unaltered message to obtain a `Valid!` output under normal circumstances.



## Disclaimer
This implementation has not undergone any rigorous security audit. Therefore, it should not be used in production environments or for purposes that require high-security assurance. The main purpose of this project is for educational and experimental use. While it has been implemented with care and a strong emphasis on adhering to the principles laid out in the aforementioned research paper, it has not been thoroughly stress-tested under all possible scenarios. Thus, it may not handle edge cases correctly and is not recommended for production-level code, particularly in contexts where security is critical.