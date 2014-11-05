/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JPanel;

/**
 *
 * @author fbo
 */
public class MazePanel extends JPanel {
    
    public static int PANEL_HEIGHT = 300;
    public static int PANEL_WIDTH = 300;
    
    private Map _map;
    
    public MazePanel(Map m) {
        this.setLayout(null);
        this.setPreferredSize(new Dimension(PANEL_WIDTH, PANEL_HEIGHT));
        this.setBounds(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        _map = m;
       
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        System.out.println("in paint ocmponoetn");
      //  Graphics2D g2d = (Graphics2D) g;
        //clear the screen
        g.setColor(Color.black);
        g.fillRect(0, 0, PANEL_WIDTH, PANEL_HEIGHT);
        
        _map.paint(g);
    }
    
    public void updateMap() {
        
        _map.paint(this.getGraphics());
        
    }
    
}
