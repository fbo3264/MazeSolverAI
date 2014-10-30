/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

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
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

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
    private Map _map = new Map();
    private boolean _simStarted = false;
    private final List<DisplayFrame> _simulationFrames = new ArrayList<>();

    public ControlFrame() {
        this.setLayout(new BorderLayout());

        _btnPanel.add(_startSimBtn);
        _btnPanel.add(_pauseSimBtn);
        _btnPanel.add(_stopSimBtn);

        _btnPanel.setBackground(Color.red);

        this.add(_btnPanel, BorderLayout.WEST);
        this._mazePanel = new MazePanel(_map);
        this.add(_mazePanel, BorderLayout.CENTER);

        initBtnClickListeners();

        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

    }

    private void initBtnClickListeners() {
        _startSimBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {
                System.out.println("click");

                startSimulation();

                //fire "start" event
            }
        });

        _pauseSimBtn.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent ae) {

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

            // position frame            
            if (i == 0) {
                currX = this.getWidth();
            } else if (currX + MazePanel.PANEL_WIDTH >= rect.width) {
                currX = 0;
                currY += this.getHeight();
            } else {
                currX += MazePanel.PANEL_WIDTH;
            }
            DisplayFrame df = new DisplayFrame(_map, PathDirections.values()[i]);
            df.setLocation(currX, currY);

            this._simulationFrames.add(df);

        }

        Thread t = new Thread() {

            @Override
            public void run() {
                _mazeSolver = new MazeSolver(_mazePanel, _map, PathDirections.values()[PathDirections.values().length - 1]);
                _mazeSolver.findWay();
            }

        };
        t.start();

        for (final DisplayFrame sFrame : _simulationFrames) {
            Thread currThread = new Thread() {

                @Override
                public void run() {
                    sFrame.logic();
                }

            };
            currThread.start();
        }
        

    }

}
