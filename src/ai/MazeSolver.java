/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ai;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JPanel;
import main.Map;

/**
 *
 * @author fbo
 */
public class MazeSolver {

    private PathDirections path;

    private Map _map;

    public boolean Running = false;

    private float _xPos = 1.0f;
    private float _yPos = 1.0f;
    private final JPanel _jframe;

    public MazeSolver(JPanel frame, Map map, PathDirections pathDirections) {
        this._map = map;
        this.path = pathDirections;
        this._jframe = frame;
    }

    public int findWay() {

        try {
            int foundPath = 0;

            foundPath = findWay((int) _xPos, (int) _yPos, path);
            return foundPath;
        } catch (Exception ex) {
            Logger.getLogger(MazeSolver.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;

    }

    public int findWay(int y, int x, PathDirections path) throws IOException {

        if (_map.data[y][x] == Map.EXIT) {
            _map.data[y][x] = Map.EXIT_FOUND;
            _jframe.repaint();
            return 1;
        } else if (_map.data[y][x] == Map.MARKED) {
            return 0;
        } else if (_map.data[y][x] == Map.BLOCKED) {
            return -1;
        } else {
            //mark current position
            _map.data[y][x] = Map.MARKED;
            _jframe.repaint();
            try {
                Thread.sleep(100);

            } catch (Exception ex) {
                Logger.getLogger(MazeSolver.class.getName()).log(Level.SEVERE, null, ex);
            }
            if (path.equals(PathDirections.URDL)) {
                if (findWay(y - 1, x, path) == 1) {
                    return 1;
                }

                if (findWay(y, x + 1, path) == 1) {
                    return 1;
                }

                if (findWay(y + 1, x, path) == 1) {
                    return 1;
                }

                if (findWay(y, x - 1, path) == 1) {
                    return 1;
                }

                System.out.println("In sackgasse");
                return -1;
            } else if (path.equals(PathDirections.LURD)) {
                if (findWay(y, x - 1, path) == 1) {
                    return 1;
                }

                if (findWay(y - 1, x, path) == 1) {
                    return 1;
                }

                if (findWay(y, x + 1, path) == 1) {
                    return 1;
                }

                if (findWay(y + 1, x, path) == 1) {
                    return 1;
                }

                System.out.println("In sackgasse");
                return -1;
            } else if (path.equals(PathDirections.RULD)) {
                if (findWay(y, x + 1, path) == 1) {
                    return 1;
                }

                if (findWay(y - 1, x, path) == 1) {
                    return 1;
                }

                if (findWay(y, x - 1, path) == 1) {
                    return 1;
                }

                if (findWay(y + 1, x, path) == 1) {
                    return 1;
                }

                System.out.println("In sackgasse");
                return -1;
            } else if (path.equals(PathDirections.DRUL)) {
                if (findWay(y + 1, x, path) == 1) {
                    return 1;
                }

                if (findWay(y, x + 1, path) == 1) {
                    return 1;
                }

                if (findWay(y - 1, x, path) == 1) {
                    return 1;
                }

                if (findWay(y, x - 1, path) == 1) {
                    return 1;
                }

                System.out.println("In sackgasse");
                return -1;
            }
            //reset position
            _map.data[y][x] = Map.CLEAR;
            return -1;

        }
    }

}
