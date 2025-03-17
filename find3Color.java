
import java.io.*;
import java.util.*;
import java.util.ArrayList;


public class find3Color {
    public static void main(String[] args) {
        if (args.length == 0)
        {
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
                    for (int i = 0;i<vertices;i++) {
                        for (int j = 0; j<vertices;j++) {
                            if (scan.nextInt() == 1) {
                                graphs.get(graphs.size()-1).addEdge(i,j);
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
        int graphCount = 1;
        for(Graph currentGraph : graphs) {
            long startTime = System.currentTimeMillis();
            System.out.print("\nG" + graphCount + ":(|V|=" + currentGraph.getVertexCount() + ",|E|=" + currentGraph.getEdgeCount() + ")");
            boolean currentGraphSolved = currentGraph.threeColorSolve(0);
            long elapsedTime = System.currentTimeMillis() - startTime;

            System.out.print(" " + currentGraph.getColorString());
            System.out.print("(ms=" + elapsedTime + ")\n");

            if(currentGraphSolved && currentGraph.getVertexCount() <= 20)
            {
                System.out.println(currentGraph.toString());
            }

            graphCount++;
        }

    }

    
}
