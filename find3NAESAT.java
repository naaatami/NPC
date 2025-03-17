// javac find3NAESAT.java
// java find3NAESAT cnfs2025.txt
// Natalie Simova, Bennett Beltran, Shane Burke

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Hashtable;
import java.util.List;

import java.io.IOException;

public class find3NAESAT {
    public static void main(String[] args) {
        // Ensure that a file name is given
        if (args.length == 0) {
            System.out.println("ERROR: Please provide the text file you'd like to use.");
            return;
        }

        String cnfFile = args[0];

        // Start output
        System.out.println("** Find 3NAESAT in " + cnfFile + " (by backtracking):");

        try {
            BufferedReader reader = new BufferedReader(new FileReader(cnfFile));
            String line = reader.readLine();

            int cnfCount = 1;
            // Iterate through every line and construct the 3CNF
            while (line != null) {
                int[] cnfArray = Helper.splitCNFInput(line);
                int[] numTermsNumClauses = Helper.findNandK(cnfArray);
                int numTerms = numTermsNumClauses[0];
                int numClauses = numTermsNumClauses[1];

                System.out.println("3CNF No." + cnfCount + ": [n=" + numTerms + " k=" + numClauses + "]");

                Hashtable<Integer, Boolean> baseAssignments = new Hashtable<>();
                long startTime = System.currentTimeMillis();
                boolean certFound = find3NAESATCertificate(cnfArray, baseAssignments, 1, numTerms);
                long endTime = System.currentTimeMillis();

                System.out.print("(" + (endTime - startTime) + " ms) ");

                // Construct cert
                if (certFound) {
                    System.out.print("NAE certificate = [");
                } else {
                    System.out.print("No NAE positive certificate! Using an random assignment = [");
                }
                // Get the keys and sort them
                List<Integer> sortedKeys = new ArrayList<>(baseAssignments.keySet());
                Collections.sort(sortedKeys);

                String cert = "";
                for (int termNum : sortedKeys) {
                    String a = baseAssignments.get(termNum) ? "T" : "F";
                    cert += termNum + ":" + a + " ";
                }
                System.out.print(cert.substring(0, cert.length() - 1) + "]");

                System.out.println();
                System.out.println(Helper.format3CNF(cnfArray) + " ==>");
                System.out.println(generateAssignments(cnfArray, baseAssignments));
                System.out.println();

                cnfCount++;

                line = reader.readLine();
            }

            reader.close();
        } catch (IOException error) {
            error.printStackTrace();
        }

    }

    public static String generateAssignments(int[] cnfArray, Hashtable<Integer, Boolean> assignments) {
        ArrayList<String> results = new ArrayList<String>();

        for (int i = 0; i < cnfArray.length; i += 3) {
            String group = "(";
            // Determine what assignment we are looking at, and find its boolean value
            boolean value1 = assignments.get(Math.abs(cnfArray[i]));
            boolean value2 = assignments.get(Math.abs(cnfArray[i + 1]));
            boolean value3 = assignments.get(Math.abs(cnfArray[i + 2]));

            // If the term is negative, flip the boolean value
            if (cnfArray[i] < 0) {
                value1 = !value1;
            }
            if (cnfArray[i + 1] < 0) {
                value2 = !value2;
            }
            if (cnfArray[i + 2] < 0) {
                value3 = !value3;
            }
            String a1 = value1 ? "T" : "F";
            String a2 = value2 ? "T" : "F";
            String a3 = value3 ? "T" : "F";

            group += " " + a1 + "| " + a2 + "| " + a3;
            group += ")";
            results.add(group);
        }
        return String.join("/\\", results);
    }

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
                i += 1;
            }
        }
        return true;
    }

    public static boolean find3NAESATCertificate(int[] cnfArray, Hashtable<Integer, Boolean> assignments, int curTerm,
            int numTerms) {
        boolean[] bools = { true, false };

        for (boolean b : bools) {
            if (curTerm > numTerms) {
                if (is3NAESAT(cnfArray, assignments)) {
                    return true;
                } else {
                    return false;
                }
            } else {
                assignments.put(curTerm, b);
                if (find3NAESATCertificate(cnfArray, assignments, curTerm + 1, numTerms)) {
                    return true;
                }
            }
        }

        return false;
    }

}
