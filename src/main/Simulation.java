/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

import javax.swing.SwingUtilities;

/**
 *
 * @author fbo
 */
public class Simulation {

    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            public void run() {
                ControlFrame app = new ControlFrame();
                app.setVisible(true);
            }
        });

    }

}
