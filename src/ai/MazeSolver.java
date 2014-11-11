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
package ai;

import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import ui.ControlFrame;
import ui.Map;
import ui.MazePanel;

/**
 *
 * @author fbo
 */
public class MazeSolver implements Runnable {

    private PathDirections path;

    private Map _map;

    public boolean Running = false;

    private float _xPos = 1.0f;
    private float _yPos = 1.0f;
    private final MazePanel _jpanel;

    public MazeSolver(MazePanel frame, Map map, PathDirections pathDirections) {
        this._map = map;
        this.path = pathDirections;
        this._jpanel = frame;

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
            _jpanel.repaint();
            return 1;
        } else if (_map.data[y][x] == Map.MARKED) {
            return 0;
        } else if (_map.data[y][x] == Map.BLOCKED) {
            return -1;
        } else {
            //mark current position
            _map.data[y][x] = Map.MARKED;
            _jpanel.repaint();

            try {
                Thread.sleep(300);

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
                return -1;
            }
            //reset position
            _map.data[y][x] = Map.CLEAR;
            return -1;

        }
    }

    public int findWayAsync(int y, int x, PathDirections path) throws IOException {

        synchronized (ControlFrame._frameMonitor) {
            if (ControlFrame._frameMonitor._pauseExecution == true) {
                try {
                    ControlFrame._frameMonitor.wait();
                    System.out.println("monitor is waiting");
                } catch (InterruptedException ex) {
                    Logger.getLogger(MazeSolver.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }

        if (_map.data[y][x] == Map.EXIT) {
            _map.data[y][x] = Map.EXIT_FOUND;
            _jpanel.repaint();
            return 1;
        } else if (_map.data[y][x] == Map.MARKED) {
            return 0;
        } else if (_map.data[y][x] == Map.BLOCKED) {
            return -1;
        } else {
            //mark current position
            _map.data[y][x] = Map.CURRENT_POS;
            
            //WORKS BUT FLICKERS: _jpanel.paint(_jpanel.getGraphics());
            _jpanel.updateMap();
            
            _map.data[y][x] = Map.MARKED;

            try {
                Thread.sleep(800);

            } catch (Exception ex) {
                Logger.getLogger(MazeSolver.class.getName()).log(Level.SEVERE, null, ex);
            }

            if (path.equals(PathDirections.URDL)) {
                if (findWayAsync(y - 1, x, path) == 1) {
                    return 1;
                }

                if (findWayAsync(y, x + 1, path) == 1) {
                    return 1;
                }

                if (findWayAsync(y + 1, x, path) == 1) {
                    return 1;
                }

                if (findWayAsync(y, x - 1, path) == 1) {
                    return 1;
                }
                return -1;
            } else if (path.equals(PathDirections.LURD)) {
                if (findWayAsync(y, x - 1, path) == 1) {
                    return 1;
                }

                if (findWayAsync(y - 1, x, path) == 1) {
                    return 1;
                }

                if (findWayAsync(y, x + 1, path) == 1) {
                    return 1;
                }

                if (findWayAsync(y + 1, x, path) == 1) {
                    return 1;
                }
                return -1;
            } else if (path.equals(PathDirections.RULD)) {
                if (findWayAsync(y, x + 1, path) == 1) {
                    return 1;
                }

                if (findWayAsync(y - 1, x, path) == 1) {
                    return 1;
                }

                if (findWayAsync(y, x - 1, path) == 1) {
                    return 1;
                }

                if (findWayAsync(y + 1, x, path) == 1) {
                    return 1;
                }
                return -1;
            } else if (path.equals(PathDirections.DRUL)) {
                if (findWayAsync(y + 1, x, path) == 1) {
                    return 1;
                }

                if (findWayAsync(y, x + 1, path) == 1) {
                    return 1;
                }

                if (findWayAsync(y - 1, x, path) == 1) {
                    return 1;
                }

                if (findWayAsync(y, x - 1, path) == 1) {
                    return 1;
                }
                return -1;
            }
            //reset position
            //  _map.data[y][x] = Map.CLEAR;
            return -1;

        }
    }

    @Override
    public void run() {
        findWayAsync();
    }

    private int findWayAsync() {
        try {
            int foundPath = 0;

            foundPath = findWayAsync((int) _xPos, (int) _yPos, path);
            return foundPath;
        } catch (Exception ex) {
            Logger.getLogger(MazeSolver.class.getName()).log(Level.SEVERE, null, ex);
        }

        return -1;
    }

}
