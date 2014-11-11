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

    public String toShortString() {
        return "(" + _xCoord + "," + _yCoord + ")";
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

    public void setDiscovered() {
        discovered = true;
    }

    void setProcessed() {
        processed = true;
    }

    void reset() {
        processed = false;
        discovered = false;
    }
}
