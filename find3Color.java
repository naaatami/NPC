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

        // reading in graphs
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

        // solve and print graph
        int graphCount = 1;
        for (Graph currentGraph : graphs) {
            long startTime = System.currentTimeMillis();
            int vCount = currentGraph.getVertexCount();
            int eCount = (currentGraph.getEdgeCount() - vCount) / 2;

            System.out.print("\nG" + graphCount + ":(|V|=" + vCount + ",|E|=" + eCount + ")");
            boolean currentGraphSolved = currentGraph.threeColorSolve(0);
            long elapsedTime = System.currentTimeMillis() - startTime;

            if(!currentGraphSolved)
                System.out.print(" Not 3-colorable");

            System.out.print(" (ms=" + elapsedTime + ")\n");

            if (currentGraphSolved) {
                System.out.println(currentGraph.toString());
            }

            graphCount++;
        }

    }

}
