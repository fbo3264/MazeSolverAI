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
package test;

import graph.Edge;
import graph.Graph;
import graph.Vertex;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author fbo
 */
public class GraphTest {

    public static void main(String[] args) {

        //  GraphModel g = GraphAM.generateGraphFromMap(m);
        // Map m = new Map("/home/fbo/tmp/map.dat");
        Vertex v = new Vertex("A");
        Vertex v1 = new Vertex("B");
        Vertex v2 = new Vertex("C");
        Vertex v3 = new Vertex("D");
        Vertex v4 = new Vertex("E");

        Edge e = new Edge(v, v1, 0);
        Edge e1 = new Edge(v1, v2, 0);
        Edge e2 = new Edge(v1, v3, 0);
        Edge e3 = new Edge(v1, v4, 0);
        Edge e4 = new Edge(v2, v4, 0);
        Edge e5 = new Edge(v3, v4, 0);
        Edge e6 = new Edge(v, v2, 0);

        List<Vertex> vlist = new ArrayList<>();
        vlist.add(v);
        vlist.add(v1);
        vlist.add(v2);
        vlist.add(v3);
        vlist.add(v4);

        List<Edge> eList = new ArrayList<>();
        eList.add(e);
        eList.add(e1);
        eList.add(e2);
        eList.add(e3);
        eList.add(e4);
        eList.add(e5);
        eList.add(e6);

        Graph g = new Graph(vlist, eList);
        g.debugGraph();
        System.out.println("------------------------------------");
        g.traverseDFS();
        g.reset();
        System.out.println("------------------------------------");
        g.traverseIterativeDFS();
        g.reset();
        System.out.println("------------------------------------");
        g.traverseBFS();
        

    }
}
