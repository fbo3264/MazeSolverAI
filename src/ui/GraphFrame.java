/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import graph.Graph;
import graph.Vertex;
import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JFrame;

/**
 * Displays a graph containing vertices and edges
 *
 * @author Friedrich Bösch
 */
public class GraphFrame extends JFrame {

    private GraphPanel _mainPanel;
    private Graph _graph;

    public static int WIDTH = 600;
    public static int HEIGHT = 400;

    public GraphFrame(Graph g) {
        _graph = g;

        this.setSize(new Dimension(WIDTH, HEIGHT));
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH);
        this.setVisible(true);
        Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
        double width = screenSize.getWidth();
        double height = screenSize.getHeight();
        HEIGHT = (int) height;
        WIDTH = (int) width;
        GraphPanel.PANEL_HEIGHT = HEIGHT;
        GraphPanel.PANEL_WIDTH = WIDTH;
        _mainPanel = new GraphPanel();
        _mainPanel.addVertices(_graph.getVertices());
        _mainPanel.addEdges(_graph.getEdges());
        this.add(_mainPanel);
        System.out.println(HEIGHT);
        System.out.println(WIDTH);
    }

}
