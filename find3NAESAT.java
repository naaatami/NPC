// javac find3NAESAT.java
// java find3NAESAT cnfs2025.txt
// Natalie Simova, Bennett Beltran, Shane Burke

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.Hashtable;
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

                Hashtable<Integer, Boolean> baseAssignments = new Hashtable<>();
                for (int i = 1; i < numTerms + 1; i++) {
                    baseAssignments.put(i, true);
                }

                System.out.println(find3NAESATCertificate(cnfArray, baseAssignments, 1, numTerms));
                System.out.println(baseAssignments);
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

    // Forgot it was supposed to be NAESAT... will make another function later
    public static boolean is3SAT(int[] cnfArray, Hashtable<Integer, Boolean> assignments) {
        for (int i = 0; i < cnfArray.length; i++) {
            // Calculate mod 3, as we are working in groups of 3
            int r = i % 3;

            // Determine what assignment we are looking at, and find its boolean value
            int term = Math.abs(cnfArray[i]);
            boolean value = assignments.get(term);

            // If the term is negative, flip the boolean value
            if (cnfArray[i] < 0) {
                value = !value;
            }

            if (value == true) {
                // If the value is true we can move to the next group of 3 terms
                if (r == 0) {
                    i += 3;
                    continue;
                } else if (r == 1) {
                    i += 2;
                    continue;
                }
            } else if (r == 2 && value == false) {
                // If we reach the last term in a group of 3 and it is false,
                // then the whole expression is false.
                return false;
            }

        }
        return true;
    }

    public static boolean is3NAESAT(int[] cnfArray, Hashtable<Integer, Boolean> assignments) {
        boolean trueFlag = false;
        boolean NAEFlag = false;

        for (int i = 0; i < cnfArray.length; i++) {
            // Determine what assignment we are looking at, and find its boolean value
            int term = Math.abs(cnfArray[i]);
            boolean value = assignments.get(term);

            // If the term is negative, flip the boolean value
            if (cnfArray[i] < 0) {
                value = !value;
            }

            // Calculate mod 3, as we are working in groups of 3
            int r = i % 3;

            // If we are in new group, reset flags
            if (r == 0) {
                trueFlag = false;
                NAEFlag = false;
            }

            // Set flags
            if (value == true) {
                trueFlag = true;
            } else {
                NAEFlag = true;
            }

            // If we reach the last term in a group of 3 and one of the flags is false,
            // then the whole expression is false.
            if (r == 2 && (trueFlag == false || NAEFlag == false)) {
                return false;
            } else if (r == 1 && (trueFlag && NAEFlag)) {
                // Skip ahead if already NAESAT
                i += 2;
            }
        }
        return true;
    }

    public static boolean find3NAESATCertificate(int[] cnfArray, Hashtable<Integer, Boolean> assignments, int curTerm,
            int numTerms) {
        boolean[] bools = { true, false };

        for (boolean b : bools) {
            assignments.put(curTerm, b);

            if (curTerm > numTerms) {
                if (is3NAESAT(cnfArray, assignments)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                if (find3NAESATCertificate(cnfArray, assignments, curTerm + 1, numTerms)) {
                    return true;
                }
            }
        }

        return false;
    }

}
