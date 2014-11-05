/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package graph;

/**
 * Definition of a Vertex to be used with the Graph class.
 *
 * The distance field is designed to hold the length of the path from the source
 * of the traversal to this vertex
 *
 * The predecessor field refers to the previous field on the path from the
 * source (i.e. the vertex one edge closer to the source).
 *
 */
public class Vertex implements Comparable<Vertex> {

    // label 
    public String name;

    // the next three boolean variables for search
    public boolean processed;
    public boolean discovered;
    public Vertex predecessor; // previous vertex

    // the next variable for path length of shortest path from source
    public int distance;

    // Infinite distance indicates that there is no path from the source to this vertex
    public static final int INFINITY = Integer.MAX_VALUE;
    private int _xCoord;
    private int _yCoord;

    public Vertex(String v) {
        name = v;

        predecessor = null;
        processed = false;
        discovered = false;
        distance = INFINITY; // start as infinity away
    }

    public Vertex(String v, int x, int y) {
        name = v;
        this._xCoord = x;
        this._yCoord = y;
        predecessor = null;
        processed = false;
        discovered = false;
        distance = INFINITY; // start as infinity away
    }

    @Override
    public String toString() {
        return name;
    }

    /**
     * Compare on the basis of distance from source first and then
     * lexicographically
     *
     * @param other
     * @return
     */
    @Override
    public int compareTo(Vertex other) {
        int diff = distance - other.distance;
        if (diff != 0) {
            return diff;
        } else {
            return name.compareTo(other.name);
        }
    }
}
