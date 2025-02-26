// javac find3NAESAT3Color.java
// java find3NAESAT3Color cnfs2025.txt
// Natalie Simova, Bennett Beltran, Shane Burke

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;

public class find3NAESAT3Color {
    public static void main(String[] args) {
        if (args.length == 0)
        {
            System.out.println("ERROR: Please provide the text file you'd like to use.");
            return;
        }

        String cnfFile = args[0]; 
        System.out.println("** Find find3NAESAT3Color in " + cnfFile);
        int cnfCount = 1;
        
        // read input
        try {
            BufferedReader reader = new BufferedReader(new FileReader(cnfFile));
            String line = reader.readLine();
         
            while (line != null) {
                int[] cnfArray = splitInput(line);
                int[] nk = findNandK(cnfArray);
                System.out.println("3CNF No. " + cnfCount + ": [n=" + nk[0] + " k=" + nk[1] + "]");
                System.out.println(line);
                int[][] adjacencyMatrix = makeGraph(cnfArray, nk[0], nk[1]);
                System.out.println("\n");
                cnfCount++;

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    // splits the 3CNF clause into a string array so it can be more easily processed
    public static int[] splitInput(String inputString)
    {
        String[] numberStringArray = inputString.trim().split("\\s+"); // separating by spaces

        int[] numbers = new int[numberStringArray.length];
        for (int i = 0; i < numberStringArray.length; i++) {
            numbers[i] = Integer.parseInt(numberStringArray[i]); // Convert to int
        }

        return numbers;
    }

    // given a CNF clause, finds n and k and sends it back in an array.
    // index 0 is n, index 1 is k
    public static int[] findNandK(int[] cnfArray)
    {
        int[] nk = {0, 0};
        for(int currentNum : cnfArray)
        {
            int value = currentNum;
            if(value > nk[0])
            {
                nk[0] = value;
            }
            nk[1]++;
        }
        nk[1] = nk[1] / 3;
        return nk;
    }

    // makes a graph given a 3CNF clause
    public static int[][] makeGraph(int[] cnfArray, int n, int k)
    {
        // total vertex count = x + bars + triangles
        int vertexCount = 1 + 2*n + 3*k;
        int[][] adjacencyMatrix = new int[vertexCount][vertexCount];

        // vertex 0 will be x
        // vertices 1 through 2*n+1 will be a, not a, etc.
        // 2*n+1 through the end are clause triangles

        // setting x as adjacent to all top vertices (a, not a, etc.)
        // nVerticeMax represents the range of vertices that form bars
        int nVerticeMax = 2 * n + 1;
        for(int i = 1; i < nVerticeMax; i++)
        {
            adjacencyMatrix[0][i] = 1;
            adjacencyMatrix[i][0] = 1;
        }

        // setting bars as adjacent to each other
        for(int i = 1; i < nVerticeMax; i+=2) {
            adjacencyMatrix[i][i+1] = 1;
            adjacencyMatrix[i+1][i] = 1;
        }

        // setting all triangles of clauses adjacent to each other
        for(int i = nVerticeMax; i < vertexCount; i+=3)
        {
            adjacencyMatrix[i][i+1] = 1;
            adjacencyMatrix[i+1][i] = 1;
            adjacencyMatrix[i][i+2] = 1;
            adjacencyMatrix[i+2][i] = 1;
            adjacencyMatrix[i+1][i+2] = 1;
            adjacencyMatrix[i+2][i+1] = 1;
        }

        // setting all equal values across bars and triangles adjacent to each other
        // (and making a new array first that represents the values of vertices 1 - nVerticeMax
        int[] nVertexValue = new int[1 + n * 2];
        for (int i = 0; i < n; i++) {
            nVertexValue[i * 2] = i + 1;
            nVertexValue[i * 2 + 1] = -(i + 1);
        }

        // THIS IS BROKEN, IT'S PROBABLY AN OFF BY ONE ERROR
        for(int i = 1; i < nVerticeMax; i++)
        {
            for(int j = nVerticeMax; j < vertexCount; j++)
            {
                int indexInCnfArray = i - 1;
                int nVertexShift = j - nVerticeMax;
                System.out.println("Comparing " + cnfArray[indexInCnfArray] + " and " + nVertexValue[nVertexShift]);
                if(cnfArray[indexInCnfArray] == nVertexValue[nVertexShift])
                {
                    System.out.println("Setting " + i + " and " + j + " to 1");
                    adjacencyMatrix[i][j] = 1;
                    adjacencyMatrix[j][i] = 1;
                }
            }
        }

        // consider adding a loop to just mirror, since this is symmetric. this should save you from the extra lines you did above

        printAdjacencyMatrix(adjacencyMatrix);
        return adjacencyMatrix;
    }

    public static void printAdjacencyMatrix(int[][] matrix)
    {
        for (int[] x : matrix)
        {
            for (int y : x)
            {
                System.out.print(y + " ");
            }
            System.out.println();
        }
    }
}
