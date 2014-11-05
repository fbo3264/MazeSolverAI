/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.util.HashMap;
import main.Map;

/**
 * Class representing a graph in adjacency matrix form
 *
 * @author fbo
 */
public class GraphAM implements GraphModel {

    /**
     * The connection between all vertices. For a given vertex at i,j:
     * _graphData[i][j] can have the following values: -1 .. no connection
     */
    private int[][][] _graphData;
    private int _nrOfVertices;

    private HashMap<Integer, String> _indexNameMapping = new HashMap<>();

    public GraphAM(int nrOfVertices) {
        _nrOfVertices = nrOfVertices;
        this._graphData = new int[nrOfVertices][nrOfVertices][1];
    }

    /**
     *
     * @param a
     */
    private GraphAM() {

    }

    public static GraphAM generateGraphFromMap(Map m) {
        GraphAM am = new GraphAM();
        am.generateGraph(m);
        return am;
    }

    @Override
    public void generateGraph(Map m) {

    }

    @Override
    public Iterable<Edge> outEdges(Vertex v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

}
