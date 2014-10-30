package main;

import java.awt.Color;
import java.awt.Graphics2D;

/**
 * The map holds the data about game area. In this case its responsible for both
 * rendering the map and check collision against the grid cells within.
 *
 * Our map is a simple WIDTHxHEIGHT grid containing value 0 to indicate a clear
 * cell and 1 to incidate a wall.
 *
 * @author Kevin Glass
 */
public class Map {

    public static final int MARKED = 2;
    public static final int EXIT = 3;
    public static final int EXIT_FOUND = 4;

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
    public static final int WIDTH = 15;
    /**
     * The height in grid cells of our map
     */
    public static final int HEIGHT = 15;

    /**
     * The rendered size of the tile (in pixels)
     */
    public static final int TILE_SIZE = 20;

    /**
     * The actual data for our map
     */
    public int[][] data = new int[WIDTH][HEIGHT];

    /**
     * Create a new map with some default contents
     */
    public Map() {
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
     * Copy constructor
     *
     * @param p
     */
    Map(Map p) {
        this.data = p.data;
    }

    /**
     * Render the map to the graphics context provided. The rendering is just
     * simple fill rectangles
     *
     * @param g The graphics context on which to draw the map
     */
    public void paint(Graphics2D g) {
        // loop through all the tiles in the map rendering them
        // based on whether they block or not
        for (int x = 0; x < WIDTH; x++) {
            for (int y = 0; y < HEIGHT; y++) {

                // so if the cell is blocks, draw a light grey block
                // otherwise use a dark gray
                if (data[x][y] == MARKED) {
                    g.setColor(Color.darkGray);
                    // draw the rectangle with a dark outline
                    g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(g.getColor().darker());
                    g.drawRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);

                    g.setColor(Color.red);
                    g.fillOval((int) x * TILE_SIZE + 5, (int) y * TILE_SIZE + 5, TILE_SIZE / 2, TILE_SIZE / 2);

                } else if (data[x][y] == EXIT_FOUND || data[x][y] == EXIT) {
                    g.setColor(Color.blue);
                    // draw the rectangle with a dark outline
                    g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(Color.DARK_GRAY);
                    g.drawRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    if (data[x][y] == EXIT_FOUND) {
                        g.setColor(Color.red);
                        g.fillOval((int) x * TILE_SIZE + 5, (int) y * TILE_SIZE + 5, TILE_SIZE / 2, TILE_SIZE / 2);
                    }
                } else {
                    g.setColor(Color.darkGray);
                    if (data[x][y] == BLOCKED) {
                        g.setColor(Color.gray);
                    }

                    // draw the rectangle with a dark outline
                    g.fillRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
                    g.setColor(g.getColor().darker());
                    g.drawRect(x * TILE_SIZE, y * TILE_SIZE, TILE_SIZE, TILE_SIZE);
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
    public boolean blocked(float x, float y) {
        // look up the right cell (based on simply rounding the floating
        // values) and check the value
        return data[(int) x][(int) y] == BLOCKED;
    }
}
