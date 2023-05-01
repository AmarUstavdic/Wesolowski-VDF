

public class Main {

    public static void main(String[] args) {

        WesolowskiVDF vdf = new WesolowskiVDF();
        vdf.setup(2048, 256);

        String msg = "hello!";
        int T = 1000000;
        long start = System.currentTimeMillis();
        EvalResult result = vdf.eval(msg.getBytes(), T);
        long end = System.currentTimeMillis();
        System.out.println("Eval:   " + result.getLPrime());
        System.out.println("Time taken: " + (end - start));


        System.out.println();
        start = System.currentTimeMillis();
        boolean bb = vdf.verify(msg.getBytes(), T, result.getLPrime(), result.getProof());
        end = System.currentTimeMillis();
        if (bb) {
            System.out.println("Valid!");
        } else {
            System.out.println("Invalid!");
        }
        System.out.println("Time taken: " + (end - start));


    }
}
