/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mturing.model;

import mturing.data.Constants;
import mturing.model.basics.Point;
import java.util.Objects;

/**
 *
 * @author Allan Leon
 */
public class State {
    
    public enum StateType {
        NORMAL, START, END
    }
    
    private String name;
    private boolean accepted;
    private Point pos;
    private StateType type;
    
    public State(String name, boolean accepted) {
        this.name = name;
        this.accepted = accepted;
        this.pos = new Point(0, 0);
        this.type = StateType.NORMAL;
    }
    
    public State(String name, boolean accepted, Point pos) {
        this.name = name;
        this.accepted = accepted;
        this.pos = pos;
        this.type = StateType.NORMAL;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @return the accepted
     */
    public boolean isAccepted() {
        return accepted;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @param accepted the accepted to set
     */
    public void setAccepted(boolean accepted) {
        this.accepted = accepted;
    }
    
    public void updateAccepted() {
        this.accepted = !this.accepted;
    }
    
    /**
     * @return the pos
     */
    public Point getPos() {
        return pos;
    }

    /**
     * @param pos the pos to set
     */
    public void setPos(Point pos) {
        this.pos = pos;
    }
    
    /**
     * @return the type
     */
    public StateType getType() {
        return type;
    }

    /**
     * @param type the type to set
     */
    public void setType(StateType type) {
        this.type = type;
    }
    
    public boolean checkPointCollision(Point point) {
        return Math.abs(point.getX() - pos.getX()) <= Constants.STATE_RADIUS
                && Math.abs(point.getY() - pos.getY()) <= Constants.STATE_RADIUS;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 37 * hash + Objects.hashCode(this.name);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (getClass() != obj.getClass()) {
            return false;
        }
        final State other = (State) obj;
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        return true;
    }
}
