
import java.util.ArrayList;

class Graph {
    private enum colors {green,blue,red}
    private int vertices;
    private ArrayList<ArrayList<Integer>> adj;
    private ArrayList<colors> colorList = new ArrayList<colors>();

    Graph(int v) {
        vertices = v;
        adj = new ArrayList<ArrayList<Integer>>(vertices);
        for (int i=0;i<v; i++) {
            adj.add(new ArrayList<Integer>(vertices));
            colorList.add(null);
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


    public ArrayList<colors> getColorList() {
        return colorList;
    }

    void addColor(int v, colors color) {
        colorList.set(v, color);
    }


    boolean isSafe(int k, colors c) {
        for (int i=0;i<vertices;i++) {
            if(getEdge(k, i) == 1 && c==colorList.get(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean greedyColoring(int v) {
        
        for (colors c:colors.values()) {
            if (isSafe(v,c)) {
                colorList.set(v, c);
                if (v+1<vertices) {
                    greedyColoring(v+1);
                }
                else {
                    return true;
                }  
            }
        }
        
        return false;
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