// javac find3NAESAT.java
// java find3NAESAT cnfs2025.txt
// Natalie Simova, Bennett Beltran, Shane Burke

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

public class find3NAESAT {
    public static void main(String[] args) {
        String cnfFile = args[0];

        // Ensure that a file name is given
        if (args.length == 0) {
            System.out.println("ERROR: Please provide the text file you'd like to use.");
            return;
        }

        // Start output
        System.out.println("** Find 3NAESAT in " + cnfFile + " (by backtracking):");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(cnfFile));
            String line = reader.readLine();

            int cnfCount = 1;
            // Iterate through every line and construct the 3CNF
            while (line != null) {
                int[] cnfArray = splitInput(line);
                int[] numTermsNumClauses = findNandK(cnfArray);
                int numTerms = numTermsNumClauses[0];
                int numClauses = numTermsNumClauses[1];

                System.out.println("3CNF No." + cnfCount + ": [n=" + numTerms + " k=" + numClauses + "]");
                System.out.println(line);

                System.out.println("\n");
                cnfCount++;

                line = reader.readLine();
            }

            reader.close();
        } catch (IOException error) {
            error.printStackTrace();
        }

    }

    // given a CNF clause, finds n and k and sends it back in an array.
    // index 0 is n, index 1 is k
    public static int[] findNandK(int[] cnfArray) {
        int[] nk = { 0, 0 };
        for (int currentNum : cnfArray) {
            int value = Math.abs(currentNum);
            if (value > nk[0]) {
                nk[0] = value;
            }
            nk[1]++;
        }
        nk[1] = nk[1] / 3;
        return nk;
    }

    // splits the 3CNF clause into a string array so it can be more easily processed
    public static int[] splitInput(String inputString) {
        String[] numberStringArray = inputString.trim().split("\\s+"); // separating by spaces

        int[] numbers = new int[numberStringArray.length];
        for (int i = 0; i < numberStringArray.length; i++) {
            numbers[i] = Integer.parseInt(numberStringArray[i]); // Convert to int
        }

        return numbers;
    }

    public static String is3NAESAT(int[] cnfArray, int numTerms, int numClauses) {

        return "";
    }

}
