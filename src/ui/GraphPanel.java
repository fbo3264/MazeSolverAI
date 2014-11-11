/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import graph.Edge;
import graph.Vertex;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JPanel;

/**
 *
 * @author Friedrich Bösch
 */
public class GraphPanel extends JPanel {

    private List<EdgeView> _edgeViews = new ArrayList<>();
    private List<VertexView> _vertexViews = new ArrayList<>();

    public static int PANEL_WIDTH;
    public static int PANEL_HEIGHT;

    public GraphPanel() {
        this.setLayout(null);
        this.setBackground(Color.red);
        this.setSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT);

    }

    public void resizePanel() {
        //TODO!!
        this.setPreferredSize(new Dimension(WIDTH, HEIGHT));
        this.setBounds(0, 0, WIDTH, HEIGHT);
    }
    
    

    public void addVertices(List<Vertex> vList) {
        //we start top left and populate the screen with the vertices
        int nrOfVertices = vList.size();

        int vertexWidth = 40;
        int vertexHeight = vertexWidth;

        int margin = vertexHeight / 2;
        int currX = margin;
        int currY = margin;

        System.out.println("Vertices: " + nrOfVertices);
        System.out.println("V-Heigth" + vertexHeight);
        System.out.println("V-Width" + vertexWidth);

        for (int i = 0; i < vList.size(); i++) {
            VertexView cv = new VertexView(vList.get(i), currX, currY, vertexWidth, vertexHeight);
            _vertexViews.add(cv);
            currX = currX + vertexWidth + margin;
            if (currX + vertexWidth + margin >= PANEL_WIDTH) {
                currX = margin;
                currY = currY + vertexHeight + margin;
            }
        }
    }

    public void addEdges(List<Edge> edges) {
        for (Edge edge : edges) {
            VertexView src = null;
            VertexView dest = null;
            for (VertexView _vertexView : _vertexViews) {
                Vertex tmp = edge.getDestVertex();
                Vertex tmpS = edge.getSourceVertex();
                if (_vertexView.getVertex().equals(tmp)) {
                    dest = _vertexView;
                } else if (_vertexView.getVertex().equals(tmpS)) {
                    src = _vertexView;
                }
                if (src != null && dest != null) {
                    break;
                }
            }
            _edgeViews.add(new EdgeView(edge, src, dest));
        }
    }

    @Override
    public void paint(Graphics g) {

        g.clearRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        g.setColor(Color.black);
        g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        for (VertexView vertexView : _vertexViews) {
            vertexView.paint((Graphics2D) g);
        }
        for (EdgeView _edgeView : _edgeViews) {
            _edgeView.paint((Graphics2D) g);
        }
    }

}
