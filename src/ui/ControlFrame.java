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

import ai.MazeSolver;
import ai.PathDirections;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.GridLayout;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.SwingUtilities;
import threads.FrameMonitor;

/**
 *
 * @author fbo
 */
public class ControlFrame extends JFrame {

    //*******GUI components **********
    private final JButton _startSimBtn = new JButton("Start Simulation");
    private final JButton _stopSimBtn = new JButton("Stop Simulation");
    private final JButton _pauseSimBtn = new JButton("Pause Simulation");

    private final JPanel _btnPanel = new JPanel(new GridLayout(3, 1));

    private MazePanel _mazePanel;

    //*******End GUI components **********
    private MazeSolver _mazeSolver;
    private Map _map = new Map(true);
    private boolean _simStarted = false;
    private final List<DisplayFrame> _simulationFrames = new ArrayList<>();
    public static final FrameMonitor _frameMonitor = new FrameMonitor();

    public ControlFrame() {
        this.setLayout(new BorderLayout());

        _btnPanel.add(_startSimBtn);
        _btnPanel.add(_pauseSimBtn);
        _btnPanel.add(_stopSimBtn);

        _btnPanel.setBackground(Color.red);

        this.add(_btnPanel, BorderLayout.WEST);
        this._mazePanel = new MazePanel(_map);
        _map.DEBUG = true;
        this.add(_mazePanel, BorderLayout.CENTER);

        initBtnClickListeners();

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void initBtnClickListeners() {
        _startSimBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                startSimulation();
            }
        });

        _pauseSimBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                synchronized (_frameMonitor) {
                    if (_frameMonitor._pauseExecution == true) {
                        SwingUtilities.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                _pauseSimBtn.setText("Pause Simulation");
                            }
                        });
                        _frameMonitor._pauseExecution = false;
                        _frameMonitor.notifyAll();

                    } else {
                        SwingUtilities.invokeLater(new Runnable() {

                            @Override
                            public void run() {
                                _pauseSimBtn.setText("Continue Simulation");
                            }
                        });
                        _frameMonitor._pauseExecution = true;
                    }
                }

            }
        });
    }

    /**
     * Starts the simulation. Creates frames for all search paths
     */
    private void startSimulation() {
        if (_simStarted) {
            return;
        }
        _simStarted = true;

        int currX = 0;
        int currY = 0;
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = ge.getDefaultScreenDevice();
        Rectangle rect = defaultScreen.getDefaultConfiguration().getBounds();

        for (int i = 0; i < PathDirections.values().length - 1; i++) {

            // position frames
            if (i == 0) {
                currX = this.getWidth();
            } else if (currX + MazePanel.PANEL_WIDTH >= rect.width) {
                currX = 0;
                currY += this.getHeight();
            } else {
                currX += MazePanel.PANEL_WIDTH;
            }
            
            DisplayFrame df = new DisplayFrame(new Map(true), PathDirections.values()[i]);
            df.setLocation(currX, currY);

            this._simulationFrames.add(df);

        }

        _mazeSolver = new MazeSolver(_mazePanel, _map, PathDirections.values()[PathDirections.values().length - 1]);
        Thread t = new Thread(_mazeSolver);
        t.start();

        for (final DisplayFrame sFrame : _simulationFrames) {
            Thread currThread = new Thread(sFrame.getSolverThread());            

            currThread.start();
        }

    }

}
