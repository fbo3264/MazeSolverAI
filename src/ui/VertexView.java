/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import graph.Vertex;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics2D;
import java.awt.Stroke;

/**
 *
 * @author Friedrich Bösch
 */
public class VertexView extends GraphComponentView {

    private final Vertex _vertex;

    public VertexView(Vertex _vertex, int topLeftX, int topLeftY, int w, int h) {
        this._vertex = _vertex;
        _topLeftX = topLeftX;
        _topLeftY = topLeftY;
        _width = w;
        _height = h;
        _strokeWidth = 2.f;

    }

    @Override
    public void paint(Graphics2D g) {
        Stroke prev = g.getStroke();
        g.setColor(Color.LIGHT_GRAY);
        g.setStroke(new BasicStroke(_strokeWidth));
        g.drawOval(_topLeftX, _topLeftY, _width, _height);
        g.setStroke(prev);
        g.setFont(new Font("TimesRoman", Font.PLAIN, 8));

        g.drawString(_vertex.toShortString(), _topLeftX + 2, _topLeftY + _height / 2);

    }

    public Vertex getVertex() {
        return _vertex;
    }

}
