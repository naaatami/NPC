// Solves a 3 color problem when given a file representing graph(s).
// The file to be solved must be passed as an argument.
// Natalie Simova, Bennett Beltran, Shane Burke

import java.io.*;
import java.util.*;
import java.util.ArrayList;

public class find3Color {
    public static void main(String[] args) {
        if (args.length == 0) {
            System.out.println("ERROR: Please provide the text file you'd like to use.");
            return;
        }

        String inFileString = args[0];
        System.out.println("** Find 3-Color plans for graphs in " + inFileString);
        ArrayList<Graph> graphs = new ArrayList<Graph>();

        try {
            File inFile = new File(inFileString);
            Scanner scan = new Scanner(inFile);
            while (scan.hasNext()) {
                int vertices = scan.nextInt();
                if (vertices != 0) {
                    graphs.add(new Graph(vertices));
                    scan.nextLine();
                    for (int i = 0; i < vertices; i++) {
                        for (int j = 0; j < vertices; j++) {
                            if (scan.nextInt() == 1) {
                                graphs.get(graphs.size() - 1).addEdge(i, j);
                            }
                        }
                        scan.nextLine();
                    }
                }
            }
        } catch (FileNotFoundException e) {
            System.out.println("ERROR: File not found");
            e.printStackTrace();
        }

        // EDGE COUNT IS NOT ACTUALLY RIGHT AS A HEADS UP
        // TODO: fix the output
        // TODO: fix the edge count
        int graphCount = 1;
        for (Graph currentGraph : graphs) {
            long startTime = System.currentTimeMillis();
            int vCount = currentGraph.getVertexCount();
            // Why is the edge count weird here but fine in the other problem? Dont ask
            int eCount = (currentGraph.getEdgeCount() - vCount) / 2;

            System.out.print("\nG" + graphCount + ":(|V|=" + vCount + ",|E|=" + eCount + ")");
            boolean currentGraphSolved = currentGraph.threeColorSolve(0);
            long elapsedTime = System.currentTimeMillis() - startTime;

            // Not sure if we need below line or not
            // System.out.print(" " + currentGraph.getColorString());
            System.out.print(" (ms=" + elapsedTime + ")\n");

            // Add this condition back to the if statement if you want..
            // && currentGraph.getVertexCount() <= 20
            if (currentGraphSolved) {
                System.out.println(currentGraph.toString());
            }

            graphCount++;
        }

    }

}
