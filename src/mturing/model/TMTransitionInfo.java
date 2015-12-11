/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mturing.model;

import java.util.Objects;
import mturing.model.TMTransition.TMMovement;

/**
 *
 * @author Allan Leon
 */
public class TMTransitionInfo {
    
    private Character read;
    private Character write;
    private TMMovement movement;

    public TMTransitionInfo(Character read, Character write, TMMovement movement) {
        this.read = read;
        this.write = write;
        this.movement = movement;
    }

    /**
     * @return the read
     */
    public Character getRead() {
        return read;
    }

    /**
     * @return the write
     */
    public Character getWrite() {
        return write;
    }

    /**
     * @return the movement
     */
    public TMMovement getMovement() {
        return movement;
    }

    /**
     * @param read the read to set
     */
    public void setRead(Character read) {
        this.read = read;
    }

    /**
     * @param write the write to set
     */
    public void setWrite(Character write) {
        this.write = write;
    }

    /**
     * @param movement the movement to set
     */
    public void setMovement(TMMovement movement) {
        this.movement = movement;
    }
    
    @Override
    public String toString() {
        Character dir = '>';
        if (movement == TMMovement.LEFT) {
            dir = '<';
        }
        return read + "|" + write + "," + dir;
    }

    @Override
    public int hashCode() {
        int hash = 3;
        hash = 97 * hash + Objects.hashCode(this.read);
        hash = 97 * hash + Objects.hashCode(this.write);
        hash = 97 * hash + Objects.hashCode(this.movement);
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
        final TMTransitionInfo other = (TMTransitionInfo) obj;
        if (!Objects.equals(this.read, other.read)) {
            return false;
        }
        if (!Objects.equals(this.write, other.write)) {
            return false;
        }
        if (this.movement != other.movement) {
            return false;
        }
        return true;
    }
}
