
import java.util.ArrayList;

class Graph {

    private int vertices;
    private ArrayList<ArrayList<Integer>> adj;

    Graph(int v) {
        vertices = v;
        adj = new ArrayList<ArrayList<Integer>>(vertices);
        for (int i=0;i<v; i++) {
            adj.add(new ArrayList<Integer>(vertices));
            for (int j=0;j<v;j++) {
                adj.get(i).add(0);
            }
        }
    }

    void addEdge(int v, int w) {
        adj.get(v).set(w,1);
        adj.get(w).set(v,1);
    }

    void greedyColoring() {
        return;
    }

    public String toString() {
        StringBuilder graph = new StringBuilder();
        for (int i = 0;i<vertices;i++) {
            for (int j = 0;j<vertices;j++) {
                graph.append(adj.get(i).get(j) + " ");
            }

            graph.append("\n");
        }
        
        return graph.toString();
    }
}