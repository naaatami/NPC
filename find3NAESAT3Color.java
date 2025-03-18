// Solves a 3NAESAT problem by reducing it to the 3 color problem.
// The file to be solved must be passed as an argument.
// javac find3NAESAT3Color.java
// java find3NAESAT3Color cnfs2025.txt
// Natalie Simova, Bennett Beltran, Shane Burke

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.File;
import java.io.IOException;
import java.util.Hashtable;
import java.util.List;
import java.util.Collections;
import java.util.ArrayList;
import java.util.Random;

public class find3NAESAT3Color {
    public static void main(String[] args) {
        if (args.length == 0)
        {
            System.out.println("ERROR: Please provide the text file you'd like to use.");
            return;
        }

        String cnfFile = args[0]; 
        System.out.println("** Find find3NAESAT3Color in " + cnfFile + " (reduced to 3-Color Problem):");
        int cnfCount = 1;
        
        try {
            BufferedReader reader = new BufferedReader(new FileReader(cnfFile));
            String line = reader.readLine();
         
            while (line != null) {
                // System.out.println(line);

                long startTime = System.currentTimeMillis();
                int[] cnfArray = Helper.splitCNFInput(line);
                int[] nk = Helper.findNandK(cnfArray);
                int nCount = nk[0];
                int kCount = nk[1];
                Graph graph = makeNewGraph(cnfArray, nCount, kCount);
                boolean coloringFound = graph.threeColorSolve(0);
                long elapsedTime = System.currentTimeMillis() - startTime;
                boolean[] varAssignments = new boolean[nCount];

                //output
                // System.out.println("\n" + graph.toString());
                System.out.println("\n3CNF No." + cnfCount + ":[n=" + nCount + " k=" + kCount + "]");
                System.out.print("(" + elapsedTime + " ms) ");
                if(coloringFound)
                {
                    System.out.print("NAE certificate = ");
                } else {
                    System.out.print("No NAE positive certificate! Using an random assignment = ");
                }

                // translating the solve into T or F values
                // TODO: break this out into a separate method
                ArrayList<Color> colorList = graph.getColorList();
                if(coloringFound) {
                    Color uselessColor = colorList.get(0);
                    Color trueColor = colorList.get(1);
                    for(int i = 0; i < varAssignments.length; i++) {
                        if(colorList.get(i * 2 + 1) == trueColor)
                            varAssignments[i] = true;
                        else
                            varAssignments[i] = false;
                    }
                } else {
                    Random random = new Random();
                    for(int i = 0; i < varAssignments.length; i++) {
                        if(random.nextBoolean())
                            varAssignments[i] = true;
                        else
                            varAssignments[i] = false;
                    }
                }

                // printing NAE certificate
                System.out.print("[");
                for(int i = 0; i < varAssignments.length; i++) {
                    String boolValue = varAssignments[i] == true ? "T" : "F";
                    System.out.print((i+1) + ":" + boolValue);
                    if (i < varAssignments.length - 1) {
                        System.out.print(" ");
                    }
                }
                System.out.println("]");


                // printing assignments
                // TODO: bennett please help
                System.out.println(Helper.format3CNF(cnfArray) + " ==>");

                ArrayList<String> results = new ArrayList<String>();
                for (int i = 0; i < cnfArray.length; i += 3) {
                    String group = "(";
                    // Determine what assignment we are looking at, and find its boolean value
                    boolean value1 = varAssignments[Math.abs(cnfArray[i])-1];
                    boolean value2 = varAssignments[Math.abs(cnfArray[i+1])-1];
                    boolean value3 = varAssignments[Math.abs(cnfArray[i+2])-1];

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
                System.out.println(String.join("/\\", results));

                cnfCount++;
                line = reader.readLine();
            }
            reader.close();
        } catch (IOException error) {
            error.printStackTrace();
        }
    }

    public static Graph makeNewGraph(int[] cnfArray, int n, int k)
    {
        // total vertex count = x + bars + triangles
        int vertexCount = 1 + 2*n + 3*k;
        Graph graph = new Graph(vertexCount);

        // vertex 0 will be x
        // vertices 1 through 2*n+1 will be a, not a, etc.
        // 2*n+1 through the end are clause triangles

        // setting x as adjacent to all top vertices (a, not a, etc.)
        // nVerticeMax represents the range of vertices that form bars
        int nVerticeMax = 2 * n + 1;
        for(int i = 1; i < nVerticeMax; i++)
        {
            graph.addEdge(0, i);
        }

        // setting bars as adjacent to each other
        for(int i = 1; i < nVerticeMax; i+=2) {
            graph.addEdge(i, i+1);
        }

        // setting all triangles of clauses adjacent to each other
        for(int i = nVerticeMax; i < vertexCount; i+=3)
        {
            graph.addEdge(i,i+1);
            graph.addEdge(i, i+2);
            graph.addEdge(i+1, i+2);
        }

        // setting all equal values across bars and triangles adjacent to each other
        // (and making a new array first that represents the values of vertices 1 - nVerticeMax
        int[] nVertexValue = new int[n * 2];
        for (int i = 0; i < nVertexValue.length/2; i++) {
            nVertexValue[i * 2] = i + 1;
            nVertexValue[i * 2 + 1] = -(i + 1);
        }

        for(int i = 0; i < cnfArray.length; i++)
        {
            for(int j = 0; j < nVertexValue.length; j++)
            {
                if(cnfArray[i] == nVertexValue[j])
                {
                    graph.addEdge(nVerticeMax+i, j+1);
                }
            }
        }

        return graph;
    }
}
