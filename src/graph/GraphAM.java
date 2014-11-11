/* 
 * The MIT License
 *
 * Copyright 2014 Friedrich Bösch.
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in
 * all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN
 * THE SOFTWARE.
 */
package graph;

import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import ui.Map;

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
    private int _nrOfVertices = 0;
    private int _totalNrofVertices = 0;

    private HashMap<Integer, String> _indexNameMapping = new HashMap<>();
    private HashMap<String, Integer> _nameIndexMapping = new HashMap<>();

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
        _totalNrofVertices = Map.HEIGHT * Map.WIDTH;
        _graphData = new int[_totalNrofVertices][_totalNrofVertices][2];

        for (int i = 0; i < Map.HEIGHT; i++) {
            for (int j = 0; j < Map.WIDTH; j++) {

                String currName = "y(" + i + ")" + "," + "x(" + j + ")";
                if (!_nameIndexMapping.containsKey(currName)) {
                    _indexNameMapping.put(_nrOfVertices, currName);
                    _nameIndexMapping.put(currName, _nrOfVertices);
                    _nrOfVertices++;
                }
                //We don't want to generate edges for walls ;)
                if (m.blockedOrOutOfIdx(j, i)) {
                    continue;
                }

                String tmpName = "";
                if (!m.blockedOrOutOfIdx(j, i - 1)) {
                    tmpName = "y(" + (i - 1) + ")" + "," + "x(" + j + ")";
                    if (!_nameIndexMapping.containsKey(tmpName)) {
                        _indexNameMapping.put(_nrOfVertices, tmpName);
                        _nameIndexMapping.put(tmpName, _nrOfVertices);
                        _nrOfVertices++;
                    }
                    addEdge(currName, tmpName, 0);
                }
                if (!m.blockedOrOutOfIdx(j, i + 1)) {
                    tmpName = "y(" + (i + 1) + ")" + "," + "x(" + j + ")";
                    if (!_nameIndexMapping.containsKey(tmpName)) {
                        _indexNameMapping.put(_nrOfVertices, tmpName);
                        _nameIndexMapping.put(tmpName, _nrOfVertices);
                        _nrOfVertices++;
                    }
                    addEdge(currName, tmpName, 0);

                }
                if (!m.blockedOrOutOfIdx(j - 1, i)) {
                    tmpName = "y(" + (i) + ")" + "," + "x(" + (j - 1) + ")";
                    if (!_nameIndexMapping.containsKey(tmpName)) {
                        _indexNameMapping.put(_nrOfVertices, tmpName);
                        _nameIndexMapping.put(tmpName, _nrOfVertices);
                        _nrOfVertices++;
                    }
                    addEdge(currName, tmpName, 0);

                }
                if (!m.blockedOrOutOfIdx(j + 1, i)) {
                    tmpName = "y(" + (i) + ")" + "," + "x(" + (j + 1) + ")";
                    if (!_nameIndexMapping.containsKey(tmpName)) {
                        _indexNameMapping.put(_nrOfVertices, tmpName);
                        _nameIndexMapping.put(tmpName, _nrOfVertices);
                        _nrOfVertices++;
                    }
                    addEdge(currName, tmpName, 0);
                }
            }
        }
    }

    @Override
    public Iterable<Edge> outEdges(Vertex v) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void addEdge(String src, String dest, int weight) {
        try {
            if (!_nameIndexMapping.containsKey(src) || !_nameIndexMapping.containsKey(dest)) {
                throw new Exception("One or both vertices are not in the graph");
            }
            int idxStart = _nameIndexMapping.get(src);
            int idxDest = _nameIndexMapping.get(dest);
            _graphData[idxStart][idxDest][0] = 1;
            _graphData[idxStart][idxDest][1] = weight;

        } catch (Exception ex) {
            Logger.getLogger(GraphAM.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    @Override
    public void debugGraph() {
        for (String vertex : _nameIndexMapping.keySet()) {
            System.out.println("Vertex " + vertex + " :");
            int curIdxSrc = _nameIndexMapping.get(vertex);
            for (int i = 0; i < _totalNrofVertices; i++) {
                int set = _graphData[curIdxSrc][i][0];
                if (set == 1) {
                    String s = "y(" + curIdxSrc + "),x(" + i + ")";
                    System.out.println("--> " + _indexNameMapping.get(i));
                }
            }
            System.out.println("---------------------");
        }
    }

}
