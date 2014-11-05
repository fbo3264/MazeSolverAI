/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

import main.Map;

/**
 *
 * @author fbo
 */
public interface GraphModel {
    
    void generateGraph(Map m);
    Iterable<Edge> outEdges(Vertex v);
    
    
}
