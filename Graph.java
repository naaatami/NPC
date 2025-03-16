
import java.util.ArrayList;

class Graph {
    private enum colors {green,blue,red,none}
    private int vertices;
    private ArrayList<ArrayList<Integer>> adj;
    private ArrayList<colors> colorList;

    Graph(int v) {
        vertices = v;
        adj = new ArrayList<ArrayList<Integer>>(vertices);
        for (int i=0;i<v; i++) {
            adj.add(new ArrayList<Integer>(vertices));
            colorList.add(colors.none);
            for (int j=0;j<v;j++) {
                adj.get(i).add(0);
            }
        }
    }

    void addEdge(int v, int w) {
        adj.get(v).set(w,1);
        adj.get(w).set(v,1);
    }

    int getEdge(int v, int w) {
        return adj.get(v).get(w);
    }

    void addColor(int v, colors color) {
        colorList.set(v, color);
    }

    void greedyColoring() {
        
        int vertex = 0;
        addColor(vertex, colors.red);
        
        for (int i=0;i<vertices;i++) {
            if (this.getEdge(vertex, i) == 1) {
                vertex = i;
                i = vertices; //break
            }
        }



        
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