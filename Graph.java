
import java.util.ArrayList;

class Graph {
    private enum colors {green,blue,red}
    private int vertices, edgeCount;
    private ArrayList<ArrayList<Integer>> adj;
    private boolean coloringFound;
    private ArrayList<colors> colorList = new ArrayList<colors>();

    Graph(int v) {
        vertices = v;
        edgeCount = 0;
        adj = new ArrayList<ArrayList<Integer>>(vertices);
        coloringFound = false;
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
        edgeCount++;
    }

    int getEdge(int v, int w) {
        return adj.get(v).get(w);
    }


    // public ArrayList<colors> getColorList() {
    //     return colorList;
    // }

    void addColor(int v, colors color) {
        colorList.set(v, color);
    }


    boolean isSafe(int k, colors c) {
        for (int i=0;i<vertices;i++) {
            if(k != i && getEdge(k, i) == 1 && c==colorList.get(i)) {
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
                    coloringFound = true;
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

    public String getColorList()
    {
        if(coloringFound == false)
        {
            return "No coloring found!";
        }

        String colorString = "";
        for(colors color : colorList)
        {
            switch (color) {
                case green:
                    colorString = colorString + "G";
                    break;
                case blue:
                    colorString = colorString + "B";
                    break;
                case red:
                    colorString = colorString + "R";
                    break;
                default:
                    break;
            }
            colorString = colorString + " ";
        }
        return colorString;
    }

    // public String[] getColorArray()
    // {
    //     String[] colorArray = new String[vertices];
    //     for(int i = 0; i < colorList.size(); i++)
    //     {
    //         switch (colorList.get(i)) {
    //             case green:
    //                 colorArray[i] = "G";
    //                 break;
    //             case blue:
    //                 colorArray[i] = "B";
    //                 break;
    //             case red:
    //                 colorArray[i] = "R";
    //                 break;
    //             default:
    //                 break;
    //         }
    //     }
    //     return colorArray;
    // }

    public int getEdgeCount() {
        return edgeCount;
    }

    public int getVertexCount() {
        return vertices;
    }
}
