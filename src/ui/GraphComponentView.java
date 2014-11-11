/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import java.awt.Graphics2D;

/**
 *
 * @author Friedrich Bösch
 */
public abstract class GraphComponentView {

    protected int _topLeftX;
    protected int _topLeftY;
    protected int _width;
    protected int _height;
    
    protected float _strokeWidth;

    abstract void paint(Graphics2D g);

}
