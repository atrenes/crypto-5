import java.math.BigInteger;
import java.nio.charset.Charset;

public class Main {
    public static void main(String[] args) {
        BigInteger N1 = new BigInteger("408685041841");
        BigInteger N2 = new BigInteger("409542365311");
        BigInteger N3 = new BigInteger("411702675541");

        String C1 = """
                161938982030
                93539768747
                198680625546
                324985467275
                364301388858
                121946924018
                130171610724
                264709094112
                198127513690
                98490234931
                86416406414
                347341863803
                261057850418""";

        String C2 = """
                227240021793
                240397026641
                319693734726
                143364329762
                267584092696
                104392885896
                60224870888
                54379930123
                281164821607
                51747910478
                152858842656
                198634569843
                304306303763""";

        String C3 = """
                124238176183
                56013695777
                98169308648
                320302328458
                257566073714
                180123701720
                231998512656
                220441010255
                105926142958
                104088206001
                312601660772
                358423325011
                229574485891""";

        System.out.printf("N1 = %d\n", N1);
        System.out.printf("N2 = %d\n", N2);
        System.out.printf("N3 = %d\n", N3);
        System.out.printf("C1 = %s\n", C1);
        System.out.printf("C2 = %s\n", C2);
        System.out.printf("C3 = %s\n", C3);

        String[] c1Array = C1.split("\n");
        String[] c2Array = C2.split("\n");
        String[] c3Array = C3.split("\n");
        StringBuilder message = new StringBuilder();

        BigInteger M0 = N1.multiply(N2).multiply(N3);
        BigInteger m1 = N2.multiply(N3);
        BigInteger m2 = N1.multiply(N3);
        BigInteger m3 = N1.multiply(N2);

        BigInteger n1 = m1.modInverse(N1);
        BigInteger n2 = m2.modInverse(N2);
        BigInteger n3 = m3.modInverse(N3);

        System.out.printf("M0 = N1 * N2 * N3 = %d * %d * %d = %d\n", N1, N2, N3, M0);
        System.out.printf("m1 = N2 * N3 = %d * %d = %d\n", N2, N3, m1);
        System.out.printf("m2 = N1 * N3 = %d * %d = %d\n", N1, N3, m2);
        System.out.printf("m3 = N1 * N2 = %d * %d = %d\n", N1, N2, m3);
        System.out.printf("n1 = m1^(-1) mod N1 = %d\n", m1.modInverse(N1));
        System.out.printf("n2 = m2^(-1) mod N2 = %d\n", m2.modInverse(N2));
        System.out.printf("n3 = m3^(-1) mod N3 = %d\n\n", m3.modInverse(N3));

        for (int i = 0; i < c1Array.length; i++) {
            BigInteger c1 = new BigInteger(c1Array[i]);
            BigInteger c2 = new BigInteger(c2Array[i]);
            BigInteger c3 = new BigInteger(c3Array[i]);

            BigInteger S = c1.multiply(n1).multiply(m1)
                    .add(c2.multiply(n2).multiply(m2))
                    .add(c3.multiply(n3).multiply(m3));

            BigInteger SmodM0 = S.mod(M0);

            BigInteger M = BigInteger.valueOf((long) Math.cbrt(SmodM0.doubleValue()));

            byte[] b = M.toByteArray();
            String ms = new String(b, Charset.forName("Windows-1251"));

            message.append(ms);

            System.out.printf("S[%d] = c1[%d] * n1 * m1 + c2[%d] * n2 * m2 + c3[%d] * n3 * m3 = %d\n", i, i, i, i, S);
            System.out.printf("SmodM0[%d] = S[%d] mod M0 = %d\n", i, i, SmodM0);
            System.out.printf("M = cbrt(SmodM0[%d]) = %d => text(M) = %s\n\n", i, M, ms);
        }

        System.out.println("message = " + message);
    }
}