/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mturing.model;

import java.util.Objects;

/**
 *
 * @author Allan Leon
 */
public class Configuration {
    
    private State state;
    private String word;
    private boolean dead;

    public Configuration(State state, String word) {
        this.state = state;
        this.word = word;
        this.dead = false;
    }
    
    public Configuration(State state, String word, boolean dead) {
        this.state = state;
        this.word = word;
        this.dead = dead;
    }

    /**
     * @return the state
     */
    public State getState() {
        return state;
    }

    /**
     * @return the word
     */
    public String getWord() {
        return word;
    }
    
    public boolean isDead() {
        return dead;
    }

    /**
     * @param state the state to set
     */
    public void setState(State state) {
        this.state = state;
    }

    /**
     * @param word the word to set
     */
    public void setWord(String word) {
        this.word = word;
    }
    
    public void setDead(boolean dead) {
        this.dead = dead;
    }
    
    public boolean isEmpty() {
        return word.length() == 0;
    }
    
    public boolean isValid() {
        return word.length() == 0 && state.isAccepted();
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.state);
        hash = 59 * hash + Objects.hashCode(this.word);
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
        final Configuration other = (Configuration) obj;
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Objects.equals(this.word, other.word)) {
            return false;
        }
        return true;
    }
}
 