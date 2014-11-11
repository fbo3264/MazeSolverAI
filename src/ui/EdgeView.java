/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ui;

import graph.Edge;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;

/**
 *
 * @author Friedrich Bösch
 */
public class EdgeView extends GraphComponentView {

    private Edge _edge;
    private VertexView _src;
    private VertexView _dest;

    public EdgeView(Edge _edge, VertexView _src, VertexView _dest) {
        this._edge = _edge;
        this._src = _src;
        this._dest = _dest;

    }

    @Override
    public void paint(Graphics2D g) {
        g.setColor(Color.ORANGE);
        g.drawLine(_src._topLeftX, _src._topLeftY, _dest._topLeftX, _dest._topLeftY);
    }

}
