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

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Stack;
import ui.Map;

/**
 *
 * @author fbo
 */
public class Graph implements GraphModel {

    private HashMap<String, Vertex> _namesVerticesMap = new HashMap<>();

    /**
     * All vertices in the graph
     */
    private List<Vertex> _vertices = new ArrayList<>();

    private HashMap<Vertex, List<Edge>> _adjList = new HashMap<>();

    private Graph() {

    }

    public static Graph generateGraphFromMap(Map m) {
        Graph g = new Graph();
        g.generateGraph(m);
        return g;
    }

    public Graph(List<Vertex> vlist, List<Edge> eList) {
        this._vertices = vlist;
        _adjList = new HashMap<>();
        for (Vertex v : vlist) {
            if (!_adjList.containsKey(v)) {
                _adjList.put(v, new ArrayList<Edge>());
                for (Edge currEdge : eList) {
                    if (currEdge.getSourceVertex().equals(v)) {
                        _adjList.get(v).add(currEdge);
                    }
                }
            }
        }
    }

    @Override
    public void debugGraph() {

        for (Vertex _vertice : _vertices) {
            System.out.println("Vertex " + _vertice.name + ":");
            if (_adjList.containsKey(_vertice)) {
                for (Edge edge : _adjList.get(_vertice)) {
                    System.out.println("--> " + edge.getDestVertex());
                }
                System.out.println("");
            }
            System.out.println("");
        }

    }

    public void addEdge(Vertex src, Vertex dst, int weight) {
        Edge e = new Edge(src, dst, weight);
        if (_adjList.containsKey(src)) {
            _adjList.get(src).add(e);
        } else {
            ArrayList<Edge> l = new ArrayList<>();
            l.add(e);
            _adjList.put(src, l);
        }
    }

    /**
     * Add a vertex to the graph
     *
     * @param name
     */
    public void addVertex(String name) {
        Vertex v = new Vertex(name);
        _vertices.add(v);
        _adjList.put(v, new ArrayList<Edge>());
    }

    /**
     * Add a vertex to the graph
     *
     * @param name
     */
    public void addVertex(String name, int x, int y) {
        Vertex v = new Vertex(name, x, y);
        _vertices.add(v);
        _adjList.put(v, new ArrayList<Edge>());
    }

    @Override
    public Iterable<Edge> outEdges(Vertex v) {
        if (_adjList.containsKey(v)) {
            return _adjList.get(v);
        }
        return null;
    }

    @Override
    public void generateGraph(Map m) {
        for (int i = 0; i < Map.HEIGHT; i++) {
            for (int j = 0; j < Map.WIDTH; j++) {

                String currName = "y(" + i + ")" + "," + "x(" + j + ")";
                Vertex v;
                if (!_namesVerticesMap.containsKey(currName)) {
                    v = new Vertex(currName, j, i);
                    _vertices.add(v);
                    _namesVerticesMap.put(currName, v);
                    _adjList.put(v, new ArrayList<Edge>());
                } else {
                    v = _namesVerticesMap.get(currName);
                }
                //We don't want to generate edeges for walls ;)
                if (m.blockedOrOutOfIdx(i, j)) {
                    continue;
                }
                //generate edges
                Vertex tmp = null;
                String tmpName = "";
                if (!m.blockedOrOutOfIdx(j, i - 1)) {

                    tmpName = "y(" + (i - 1) + ")" + "," + "x(" + j + ")";
                    if (!_namesVerticesMap.containsKey(tmpName)) {
                        tmp = new Vertex(tmpName, j, i - 1);
                        _vertices.add(tmp);
                        _namesVerticesMap.put(tmpName, tmp);
                    } else {
                        tmp = _namesVerticesMap.get(tmpName);
                    }
                    addEdge(v, tmp, 0);

                }
                if (!m.blockedOrOutOfIdx(j, i + 1)) {
                    tmpName = "y(" + (i + 1) + ")" + "," + "x(" + j + ")";
                    if (!_namesVerticesMap.containsKey(tmpName)) {
                        tmp = new Vertex(tmpName, j, i + 1);
                        _vertices.add(tmp);
                        _namesVerticesMap.put(tmpName, tmp);
                    } else {
                        tmp = _namesVerticesMap.get(tmpName);
                    }
                    addEdge(v, tmp, 0);
                }
                if (!m.blockedOrOutOfIdx(j - 1, i)) {
                    tmpName = "y(" + (i) + ")" + "," + "x(" + (j - 1) + ")";
                    if (!_namesVerticesMap.containsKey(tmpName)) {
                        tmp = new Vertex(tmpName, j - 1, i);
                        _vertices.add(tmp);
                        _namesVerticesMap.put(tmpName, tmp);
                    } else {
                        tmp = _namesVerticesMap.get(tmpName);
                    }
                    addEdge(v, tmp, 0);
                }
                if (!m.blockedOrOutOfIdx(j + 1, i)) {
                    tmpName = "y(" + (i) + ")" + "," + "x(" + (j + 1) + ")";
                    if (!_namesVerticesMap.containsKey(tmpName)) {
                        tmp = new Vertex(tmpName, j + 1, i);
                        _vertices.add(tmp);
                        _namesVerticesMap.put(tmpName, tmp);
                    } else {
                        tmp = _namesVerticesMap.get(tmpName);
                    }
                    addEdge(v, tmp, 0);
                }

            }
        }
    }

    public List<Vertex> getVertices() {
        return _vertices;
    }

    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        for (java.util.Map.Entry<Vertex, List<Edge>> entrySet : _adjList.entrySet()) {
            Vertex key = entrySet.getKey();
            List<Edge> value = entrySet.getValue();
            for (Edge currEdge : value) {
                edges.add(currEdge);
            }

        }
        return edges;
    }

    public void traverseDFS() {
        for (Vertex _vertice : _vertices) {
            traverseDFS(_vertice);
        }

    }

    public void traverseDFS(Vertex currentVertex) {
        if (!currentVertex.discovered) {
            currentVertex.setDiscovered();
            System.out.println(currentVertex.toString());
            for (Edge outEdge : outEdges(currentVertex)) {
                traverseDFS(outEdge.getDestVertex());
            }
        }
    }

    public void traverseIterativeDFS() {
        Stack<Vertex> stack = new Stack<>();
        stack.push(_vertices.get(0));
        while (!stack.empty()) {
            Vertex curr = stack.pop();
            curr.setDiscovered();
            System.out.println(curr.toString());
            for (Edge outEdge : outEdges(curr)) {
                Vertex next = outEdge.getDestVertex();
                if (!next.discovered) {
                    stack.push(next);
                }
            }
        }
    }

    public void reset() {
        for (Vertex _vertice : _vertices) {
            _vertice.reset();
        }
    }

    public void traverseBFS() {
        Queue<Vertex> q = new LinkedList<>();
        q.offer(_vertices.get(0));
        while (!q.isEmpty()) {
            Vertex curr = q.remove();
            curr.setProcessed();
            System.out.println(curr.toString());
            for (Edge outEdge : outEdges(curr)) {
                Vertex next = outEdge.getDestVertex();
                if (!next.discovered && !next.processed) {
                    next.setDiscovered();
                    q.offer(next);
                }
            }
        }
    }
}
