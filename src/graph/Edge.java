/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

/**
 *
 * @author fbo
 */
public class Edge {

    private Vertex _originVertex;
    private Vertex _destVertex;
    private int _weight;

    public Edge(Vertex _originVertex, Vertex _destVertex, int weight) {
        this._originVertex = _originVertex;
        this._destVertex = _destVertex;
        this._weight = weight;
    }

    public void setWeight(int weight) {
        this._weight = weight;
    }

    public Vertex getDestVertex() {
        return _destVertex;
    }

    @Override
    public String toString() {
        return "";
    }
    
    

}
