// Helper methods for NPC problems

import java.util.ArrayList;

public class Helper
{
    // given a CNF clause, finds n and k and sends it back in an array.
    // index 0 is n, index 1 is k
    public static int[] findNandK(int[] cnfArray)
    {
        int[] nk = {0, 0};
        for(int currentNum : cnfArray)
        {
            int value = Math.abs(currentNum);
            if(value > nk[0])
            {
                nk[0] = value;
            }
            nk[1]++;
        }
        nk[1] = nk[1] / 3;
        return nk;
    }

    // splits the 3CNF clause into a string array so it can be more easily processed
    public static int[] splitCNFInput(String inputString)
    {
        String[] numberStringArray = inputString.trim().split("\\s+"); // separating by spaces

        int[] numbers = new int[numberStringArray.length];
        for (int i = 0; i < numberStringArray.length; i++) {
            numbers[i] = Integer.parseInt(numberStringArray[i]); // Convert to int
        }

        return numbers;
    }

    // given a CNF clause, formats it correctly for easy user viewing
    public static String format3CNF(int[] cnfArray) {
        ArrayList<String> results = new ArrayList<String>();
        for (int i = 0; i < cnfArray.length; i += 3) {
            String group = "(";
            if (cnfArray[i] < 0)
                group += cnfArray[i];
            else
                group = group + " " + cnfArray[i];
            group += "|";
            if (cnfArray[i + 1] < 0)
                group += cnfArray[i + 1];
            else
                group = group + " " + cnfArray[i + 1];
            group += "|";
            if (cnfArray[i + 2] < 0)
                group += cnfArray[i + 2];
            else
                group = group + " " + cnfArray[i + 2];
            group += ")";
            results.add(group);
        }
        return String.join("/\\", results);
    }

}

