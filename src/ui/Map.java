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
package ui;

import ai.PathDirections;
import java.awt.Color;
import java.awt.Graphics;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The map holds the data about game area. In this case its responsible for both
 * rendering the map and check collision against the grid cells within.
 *
 * Our map is a simple WIDTHxHEIGHT grid containing value 0 to indicate a clear
 * cell and 1 to indicate a wall.
 *
 * @author Kevin Glass
 */
public final class Map {

    public static final int MARKED = 2;
    public static final int EXIT = 3;
    public static final int EXIT_FOUND = 4;
    public static final int CURRENT_POS = 5;

    public static final int UP = 1;
    public static final int DOWN = 2;
    public static final int RIGHT = 3;
    public static final int LEFT = 4;
    /**
     * The value indicating a clear cell
     */
    public static final int CLEAR = 0;
    /**
     * The value indicating a blocked cell
     */
    public static final int BLOCKED = 1;

    /**
     * The width in grid cells of our map
     */
    public static int WIDTH = 15;
    /**
     * The height in grid cells of our map
     */
    public static int HEIGHT = 15;

    /**
     * The rendered size of the tile (in pixels)
     */
    public static final int TILE_SIZE = 20;

    private static boolean validPosition(int idxX, int idxY) {
        if (idxX < 0 || idxX >= WIDTH || idxY < 0 || idxY >= HEIGHT) {
            return false;
        }
        return true;
    }
    private int _lastX = 0;
    private int _lastY = 0;
    public static boolean DEBUG = false;

    public Map(boolean f) {
        if (f) {
            generateDefaultMaze();
        }
    }

    public void generateDefaultMaze() {
        // create some default map data - it would be way
        // cooler to load this from a file and maybe provide
        // a map editor of some sort, but since we're just doing
        // a simple tutorial here we'll manually fill the data
        // with a simple little map
        for (int y = 0; y < HEIGHT; y++) {
            data[0][y] = BLOCKED;
            data[2][y] = BLOCKED;
            data[7][y] = BLOCKED;
            data[11][y] = BLOCKED;
            data[WIDTH - 1][y] = BLOCKED;
        }
        for (int x = 0; x < WIDTH; x++) {
            if ((x > 0) && (x < WIDTH - 1)) {
                data[x][10] = CLEAR;
            }

            if (x > 2) {
                data[x][9] = BLOCKED;
            }
            data[x][0] = BLOCKED;
            data[x][HEIGHT - 1] = BLOCKED;
        }

        data[4][9] = CLEAR;
        data[7][5] = CLEAR;
        data[7][4] = CLEAR;
        data[11][7] = CLEAR;
        data[14][7] = EXIT;
    }

    /**
     * Generates a maze using depth-first-search generation-algorithm with a map
     * as underlying data structure
     *
     * @param height
     * @param width
     * @return
     */
    public static Map generateMazeDFS(int height, int width) {
        HEIGHT = height;
        WIDTH = width;
        Map p = new Map();
        p.data = new int[height][width];
        //start with initialising map with walls
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                p.data[i][j] = BLOCKED;
            }
        }

        p.data[0][0] = CLEAR;

        return p;

    }

    /**
     * The actual data for our map
     */
    public int[][] data = new int[WIDTH][HEIGHT];

    /**
     * Create a new map with some default contents
     */
    public Map() {

    }

    public Map(int w, int h) {
        data = new int[h][w];
        WIDTH = w;
        HEIGHT = h;

        for (int i = 0; i <= (int) ((w * h) * 0.4); i++) {
            int idx = (int) (Math.random() * (WIDTH - 1));
            int idy = (int) (Math.random() * (HEIGHT - 1));
            data[idy][idx] = BLOCKED;
        }

    }

    public Map(String filepath) {
        File f = new File(filepath);
        if (f.exists()) {
            parseMapFile(f);
        }
    }

    private boolean parseMapFile(File f) {
        try {
            FileReader fr = new FileReader(f);
            int readInt = 0;
            StringBuilder sb = new StringBuilder();
            while ((readInt = fr.read()) != -1) {
                sb.append((char) readInt);
            }
            System.out.println(sb.toString());
            String mapString = sb.toString();
            String lines[] = mapString.split("\\r?\\n");
            if (lines.length < 2) {
                throw new Exception("File format error!");
            }
            String currLine = lines[0];
            String[] currArr = currLine.split(" ");
            int w = Integer.parseInt(currArr[0]);
            int h = Integer.parseInt(currArr[1]);

            data = new int[h][w];
            WIDTH = w;
            HEIGHT = h;

            currLine = lines[1];
            currLine = currLine.replace(",", "");
            for (int i = 0; i < h; i++) {
                for (int j = 0; j < w; j++) {

                    char currChar = currLine.charAt(i * w + j);
                    if (currChar == 'X') {
                        data[i][j] = BLOCKED;
                    } else if (currChar == ' ') {
                        data[i][j] = CLEAR;
                    }
                }
            }

        } catch (FileNotFoundException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Map.class.getName()).log(Level.SEVERE, null, ex);
        }

        return true;
    }

    /**
     * Copy constructor
     *
     * @param p
     */
    public Map(Map p) {
        this.data = p.data;
    }

    /**
     * Prints the map to the console
     */
    public void printMap() {
        for (int i = 0; i < WIDTH; i++) {
            System.out.print("--");
        }
        System.out.println();
        for (int i = 0; i < HEIGHT; i++) {
            for (int j = 0; j < WIDTH; j++) {
                if (data[i][j] == BLOCKED) {
                    System.out.print("X");
                } else {
                    System.out.print(" ");
                }
            }
            System.out.println();
        }

    }

    /**
     * Render the map to the graphics context provided. The rendering is just
     * simple fill rectangles
     *
     * @param g The graphics context on which to draw the map
     */
    public void paint(Graphics g) {
        // loop through all the tiles in the map rendering them
        // based on whether they block or not
        for (int x = 0; x < HEIGHT; x++) {
            for (int y = 0; y < WIDTH; y++) {

                // so if the cell is blocks, draw a light grey block
                // otherwise use a dark gray
                if (data[x][y] == MARKED) {
                    g.setColor(Color.gray);
                    // draw the rectangle with a dark outline
                    g.fillRect(y * TILE_SIZE, x * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(g.getColor().darker());
                    g.drawRect(y * TILE_SIZE, x * TILE_SIZE, TILE_SIZE, TILE_SIZE);

                    g.setColor(Color.red);
                    g.fillOval((int) y * TILE_SIZE + 5, (int) x * TILE_SIZE + 5, TILE_SIZE / 2, TILE_SIZE / 2);

                } else if (data[x][y] == EXIT_FOUND || data[x][y] == EXIT) {
                    g.setColor(Color.blue);
                    // draw the rectangle with a dark outline
                    g.fillRect(y * TILE_SIZE, x * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.gray);
                    g.drawRect(y * TILE_SIZE, x * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if (data[x][y] == EXIT_FOUND) {
                        g.setColor(Color.red);
                        g.fillOval((int) y * TILE_SIZE + 5, (int) x * TILE_SIZE + 5, TILE_SIZE / 2, TILE_SIZE / 2);
                    }
                } else if (data[x][y] == CURRENT_POS) {
                    g.setColor(Color.gray);
                    // draw the rectangle with a dark outline
                    g.fillRect(y * TILE_SIZE, x * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(g.getColor().darker());
                    g.drawRect(y * TILE_SIZE, x * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.YELLOW);
                    g.fillOval((int) y * TILE_SIZE + 5, (int) x * TILE_SIZE + 5, TILE_SIZE / 2, TILE_SIZE / 2);
//                    //draw the current pos as an arrow
//                    double dx = x - _lastX;
//                    double dy = y - _lastY;
//
//                    double angle = Math.atan2(dy, dx) * 180 / Math.PI;
//                    _lastX = x;
//                    _lastY = y;
//                    if (DEBUG) {
//                        if (angle >= 0 && angle <= 45) {
//                            System.out.println("RIGHT");
//                        }
//                        if (angle > 45 && angle <= 90) {
//                            System.out.println("UP");
//                        }
//                         if (angle >90 && angle <= 180) {
//                            System.out.println("LEFT");
//                        }
//                        if (angle >= -90 && angle == 0) {
//                            System.out.println("DOWN");
//                        }
//                    }
//
//                    int[] xList = {(int) y * TILE_SIZE + 5, (int) y * TILE_SIZE + 10, (int) y * TILE_SIZE + 15};
//                    int[] yList = {(int) x * TILE_SIZE + 2, (int) x * TILE_SIZE + 15, (int) x * TILE_SIZE + 2};
//                    g.setColor(Color.red);
//                    g.fillPolygon(xList, yList, 3);
                } else {
                    g.setColor(Color.gray);
                    if (data[x][y] == BLOCKED) {
                        g.setColor(Color.DARK_GRAY);
                    }

                    // draw the rectangle with a dark outline
                    g.fillRect(y * TILE_SIZE, x * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(g.getColor().darker());
                    g.drawRect(y * TILE_SIZE, x * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                }
            }
        }
    }

    /**
     * Check if a particular location on the map is blocked. Note that the x and
     * y parameters are floating point numbers meaning that we can be checking
     * partially across a grid cell.
     *
     * @param x The x position to check for blocking
     * @param y The y position to check for blocking
     * @return True if the location is blocked
     */
    public boolean blockedOrOutOfIdx(float x, float y) {
        // look up the right cell (based on simply rounding the floating
        // values) and check the value
        try {
            int idxX = (int) x;
            int idxY = (int) y;
            if (!validPosition(idxX, idxY)) {
                return true;
            }

            return data[idxY][idxX] == BLOCKED;
        } catch (ArrayIndexOutOfBoundsException aie) {
            System.out.println(Arrays.toString(aie.getStackTrace()));
        }
        return true;
    }
}
