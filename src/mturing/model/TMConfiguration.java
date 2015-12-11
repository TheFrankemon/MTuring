/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mturing.model;

import java.util.Arrays;
import java.util.Objects;
import mturing.data.Constants;

/**
 *
 * @author Allan Leon
 */
public class TMConfiguration {
    
    private TMState state;
    private char[] word;
    private int head;
    private boolean dead;
    
    public TMConfiguration(TMState state, char[] word, int head) {
        this.state = state;
        this.word = word;
        this.head = head;
        this.dead = false;
    }

    public TMConfiguration(TMState state, char[] word, int head, boolean dead) {
        this.state = state;
        this.word = word;
        this.head = head;
        this.dead = dead;
    }

    /**
     * @return the state
     */
    public TMState getState() {
        return state;
    }

    /**
     * @return the word
     */
    public char[] getWord() {
        return word;
    }

    /**
     * @return the head
     */
    public int getHead() {
        return head;
    }

    /**
     * @return the dead
     */
    public boolean isDead() {
        return dead;
    }
    
    public boolean isAccepted() {
        return state.isAccepted();
    }

    /**
     * @param state the state to set
     */
    public void setState(TMState state) {
        this.state = state;
    }

    /**
     * @param word the word to set
     */
    public void setWord(char[] word) {
        this.word = word;
    }

    /**
     * @param head the head to set
     */
    public void setHead(int head) {
        this.head = head;
    }

    /**
     * @param dead the dead to set
     */
    public void setDead(boolean dead) {
        this.dead = dead;
    }
    
    public TMConfiguration execute(TMTransitionInfo info) throws TMTransitionException {
        if (word[head] == info.getRead()) {
            word[head] = info.getWrite();
            if (info.getMovement() == TMTransition.TMMovement.LEFT) {
                head--;
            } else {
                head++;
            }
            checkWordLength();
            return this;
        } else {
        }
        
        throw new TMTransitionException(word[head] + " doesn't match with " + info.getRead());
    }
    
    public void checkWordLength() {
        if (head <= -1) {
            increaseLeftWord();
        } else if (head >= word.length) {
            increaseRightWord();
        }
    }
    
    public void increaseLeftWord() {
        Character[] temp = new Character[word.length + Constants.WORD_INCREASE];
        int i;
        for (i = 0; i < Constants.WORD_INCREASE; i++) {
            temp[i] = Constants.BLANK;
        }
        
        for (i = i; i < temp.length; i++) {
            temp[i] = word[i - Constants.WORD_INCREASE];
        }
        head += Constants.WORD_INCREASE;
    }
    
    public void increaseRightWord() {
        Character[] temp = new Character[word.length + Constants.WORD_INCREASE];
        int i;
        for (i = 0; i < word.length; i++) {
            temp[i] = word[i];
        }
        
        for (i = i; i < temp.length; i++) {
            temp[i] = Constants.BLANK;
        }
    }

    @Override
    public int hashCode() {
        int hash = 5;
        hash = 29 * hash + Objects.hashCode(this.state);
        hash = 29 * hash + Arrays.hashCode(this.word);
        hash = 29 * hash + this.head;
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
        final TMConfiguration other = (TMConfiguration) obj;
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        if (!Arrays.equals(this.word, other.word)) {
            return false;
        }
        if (this.head != other.head) {
            return false;
        }
        return true;
    }
}
