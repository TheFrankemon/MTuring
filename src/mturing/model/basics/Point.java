/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mturing.model.basics;

/**
 *
 * @author Allan Leon
 */
public class Point {
    
    protected int x;
    protected int y;

    public Point(int x, int y) {
            this.x = x;
            this.y = y;
    }

    public int getX() {
            return x;
    }

    public int getY() {
            return y;
    }

    public void setX(int x) {
            this.x = x;
    }

    public void setY(int y) {
            this.y = y;
    }

    public double getDistanceTo(Point p) {
            double dx = x - p.getX();
            double dy = y - p.getY();
            return Math.sqrt(Math.pow(dx, 2) + Math.pow(dy, 2));
    }
}
