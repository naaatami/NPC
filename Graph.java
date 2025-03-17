// A graph class represented using an adjacency list for find3Color.
// Natalie Simova, Bennett Beltran, Shane Burke
// TODO: sweep through here and find if anything can be simplified

import java.util.ArrayList;

class Graph {

    private int vertices, edgeCount;
    private ArrayList<ArrayList<Integer>> adjacencyList;
    private boolean coloringFound;
    private ArrayList<Color> colorList = new ArrayList<Color>();

    Graph(int v) {
        vertices = v;
        edgeCount = 0;
        adjacencyList = new ArrayList<ArrayList<Integer>>(vertices);
        coloringFound = false;
        for (int i=0;i<v; i++) {
            adjacencyList.add(new ArrayList<Integer>(vertices));
            colorList.add(null);
            for (int j=0;j<v;j++) {
                adjacencyList.get(i).add(0);
            }
        }
    }

    void addEdge(int v, int w) {
        adjacencyList.get(v).set(w,1);
        adjacencyList.get(w).set(v,1);
        edgeCount++;
    }

    int getEdge(int v, int w) {
        return adjacencyList.get(v).get(w);
    }


    public ArrayList<Color> getColorList() {
        return colorList;
    }

    void addColor(int v, Color color) {
        colorList.set(v, color);
    }


    boolean isSafe(int k, Color color) {
        for (int i = 0; i < vertices; i++) {
            if(k != i && getEdge(k, i) == 1 && color == colorList.get(i)) {
                return false;
            }
        }
        return true;
    }

    public boolean threeColorSolve(int vertex) {
        if(vertex == vertices)
        {
            coloringFound = true;
            return true;
        }

        for (Color color: Color.values()) {
            if (isSafe(vertex, color)) {
                colorList.set(vertex, color);

                if (threeColorSolve(vertex+1))
                {
                    return true;
                }
                else
                {
                    colorList.set(vertex, null);
                }
            }
        }

        return false;
    }

    public String toString() {
        StringBuilder stringGraph = new StringBuilder();
        stringGraph.append("  ");
        for(Color color : colorList)
        {
            stringGraph.append(color + " ");
        }
        stringGraph.append("\n");

        for (int i = 0;i<vertices;i++) {
            stringGraph.append(colorList.get(i) + " ");
            for (int j = 0;j<vertices;j++) {
                if(i == j)
                    stringGraph.append("X ");
                else if(adjacencyList.get(i).get(j) == 1)
                    stringGraph.append("1 ");
                else
                    stringGraph.append("  ");
            }

            stringGraph.append("\n");
        }
        
        return stringGraph.toString();
    }

    public String getColorString()
    {
        if(coloringFound == false)
        {
            return "Not 3-colorable ";
        }

        String colorString = "";
        for(Color color : colorList)
        {
            switch (color) {
                case G:
                    colorString = colorString + "G";
                    break;
                case B:
                    colorString = colorString + "B";
                    break;
                case R:
                    colorString = colorString + "R";
                    break;
                default:
                    break;
            }
            colorString = colorString + " ";
        }
        return colorString;
    }


    public int getEdgeCount() {
        return edgeCount;
    }

    public int getVertexCount() {
        return vertices;
    }
}
