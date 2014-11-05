/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.TreeSet;
import main.Map;

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

    public void printGraph() {

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

    public static void main(String[] args) {
        Map m = new Map(10, 7);
        m.printMap();
        System.out.println("");
        Graph g = Graph.generateGraphFromMap(m);
        g.printGraph();
    }

}
