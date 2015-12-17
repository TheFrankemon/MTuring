/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mturing.view;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import javax.swing.JPanel;

/**
 *
 * @author Allan Leon
 */
public class CustomPanel extends JPanel {
    
    private BufferedImage doubleBuffer;
    
    public CustomPanel(BufferedImage db) {
        super();
        this.doubleBuffer = db;
    }
    
    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.drawImage(doubleBuffer, 0, 0, this);
    }
}
