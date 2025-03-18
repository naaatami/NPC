// A graph class represented using an adjacency list for find3Color.
// Natalie Simova, Bennett Beltran, Shane Burke

import java.util.ArrayList;

class Graph {

    private int vertices, edgeCount;
    private ArrayList<ArrayList<Integer>> adjacencyList;
    private boolean coloringFound;
    private ArrayList<Color> colorList = new ArrayList<Color>();

    // graph constructor method
    Graph(int v) {
        vertices = v;
        edgeCount = 0;
        adjacencyList = new ArrayList<ArrayList<Integer>>(vertices);
        coloringFound = false;
        for (int i = 0; i < vertices; i++) {
            adjacencyList.add(new ArrayList<Integer>(vertices));
            colorList.add(null);
            for (int j = 0; j < vertices; j++) {
                adjacencyList.get(i).add(0);
            }
        }
    }

    // all getter methods
    int getEdge(int startVertex, int endVertex) {
        return adjacencyList.get(startVertex).get(endVertex);
    }

    // returns a string that has the coloring of the vertexes in order
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

    public ArrayList<Color> getColorList() {
        return colorList;
    }


    //all setter methods:
    void addEdge(int startVertex, int endVertex) {
        adjacencyList.get(startVertex).set(endVertex,1);
        adjacencyList.get(endVertex).set(startVertex,1);
        edgeCount++;
    }

    void addColor(int vertex, Color color) {
        colorList.set(vertex, color);
    }


    // checks if a vertex being colored a certain way is valid
    boolean isSafe(int vertex, Color color) {
        for (int i = 0; i < vertex; i++) {
            if(vertex != i && getEdge(vertex, i) == 1 && color == colorList.get(i)) {
                return false;
            }
        }
        return true;
    }

    // solves the three color problem for the graph using backtracking
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

    // prints out the graph, including the three color solve
    public String toString() {
            if (vertices<20) {
                StringBuilder stringGraph = new StringBuilder();
                stringGraph.append("  ");
                for(Color color : colorList) {
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
                    if(i != vertices-1)
                        stringGraph.append("\n");
                }

                return stringGraph.toString();

        }
        else {
            return getColorString();
        }
    }


}
