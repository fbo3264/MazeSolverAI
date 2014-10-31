/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import ai.MazeSolver;
import ai.PathDirections;
import javax.swing.JFrame;

/**
 *
 * @author fbo
 */
public class DisplayFrame extends JFrame {

    private MazeSolver _mazeSolver;

    private final MazePanel _mazePanel;

    DisplayFrame(Map p, PathDirections value) {
        p = new Map();
        _mazePanel = new MazePanel(p);
        this.add(_mazePanel);

        this.setVisible(true);
        this.pack();
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        _mazeSolver = new MazeSolver(_mazePanel, p, value);
    }

    public void logic() {
        _mazeSolver.findWay();
    }

   

}
