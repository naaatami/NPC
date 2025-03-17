
import java.io.*;
import java.util.*;
import java.util.ArrayList;


public class find3Color {


    public static void main(String[] args) {
        ArrayList<Graph> graphs = new ArrayList<Graph>();
        try {
            File inFile = new File("C:\\Users\\burke\\IT328\\findcolor\\findthreecolor\\src\\graphs2025.txt");
            System.out.println("File Read.");
            Scanner scan = new Scanner(inFile);
            while (scan.hasNext()) {
                int vertices = scan.nextInt();
                System.out.println("V: " + vertices);
                if (vertices != 0) {
                    graphs.add(new Graph(vertices));
                    scan.nextLine();
                    for (int i = 0;i<vertices;i++) {
                        for (int j = 0; j<vertices;j++) {
                            if (scan.nextInt() == 1) {
                                graphs.get(graphs.size()-1).addEdge(i,j);
                            }
                            // System.out.print(scan.next());
                        }
                        scan.nextLine();
                    }

                    System.out.println(graphs.get(graphs.size()-1));
                }

            }
        } catch (FileNotFoundException e) {
            System.out.println("File Not Found");
            e.printStackTrace();
        }

        for(Graph g:graphs) {
            g.greedyColoring(0);
        }

    }
    
}