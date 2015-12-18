/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mturing.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Logger;
import mturing.model.basics.Point;
import mturing.view.MainFrame;

/**
 *
 * @author Allan Leon
 */
public class TuringMachine {
    
    private static final Logger LOGGER = Logger.getLogger(TuringMachine.class.getName());
    private Set<TMState> states;
    private List<TMTransition> transitions;
    private TMConfiguration configuration;
    private List<TMState> reachableStates;
    private TMState initialState;
    private int createdStatesQuantity;
    
    public TuringMachine() {
        states = new HashSet<>();
        reachableStates = new ArrayList<>();
        transitions = new ArrayList<>();
        initialState = null;
        createdStatesQuantity = 0;
    }

    /**
     * @return the states
     */
    public Set<TMState> getStates() {
        return states;
    }

    /**
     * @return the transitions
     */
    public List<TMTransition> getTransitions() {
        return transitions;
    }
    
    public TMConfiguration getConfiguration() {
        return configuration;
    }

    /**
     * @return the reachableStates
     */
    public List<TMState> getReachableStates() {
        return reachableStates;
    }

    /**
     * @return the initialState
     */
    public TMState getInitialState() {
        return initialState;
    }

    /**
     * @return the createdStatesQuantity
     */
    public int getCreatedStatesQuantity() {
        return createdStatesQuantity;
    }

    /**
     * @param states the states to set
     */
    public void setStates(Set<TMState> states) {
        this.states = states;
    }

    /**
     * @param transitions the transitions to set
     */
    public void setTransitions(List<TMTransition> transitions) {
        this.transitions = transitions;
    }
    
    public void setConfiguration(TMConfiguration configuration) {
        this.configuration = configuration;
    }

    /**
     * @param reachableStates the reachableStates to set
     */
    public void setReachableStates(List<TMState> reachableStates) {
        this.reachableStates = reachableStates;
    }

    /**
     * @param initialState the initialState to set
     */
    public void setInitialState(TMState initialState) {
        this.initialState = initialState;
    }

    /**
     * @param createdStatesQuantity the createdStatesQuantity to set
     */
    public void setCreatedStatesQuantity(int createdStatesQuantity) {
        this.createdStatesQuantity = createdStatesQuantity;
    }
    
    public void addState(TMState state) {
        states.add(state);
        createdStatesQuantity++;
    }
    
    public void createState(Point clickedPoint) {
        addState(new TMState(String.format("q%d", createdStatesQuantity), false, clickedPoint));
    }
    
    public TMTransition createTransition(TMState initial, TMState next) {
        TMTransition newTransition = new TMTransition(initial, next);
        int i = 0;
        boolean found = false;
        while (i < transitions.size() && !found) {
            if (transitions.get(i).equals(newTransition)) {
                newTransition = transitions.get(i);
                found = true;
            }
            i++;
        }
        if (!found) {
            addTransition(newTransition);
        }
        return newTransition;
    }
    
    public void addTransition(TMTransition transition) {
        transitions.add(transition);
    }
    
    public void removeState(TMState state) {
        if (state == initialState) {
            initialState = null;
        }
        states.remove(state);
        int i = 0;
        while (i < transitions.size()) {
            if (transitions.get(i).getInitialState().equals(state) || transitions.get(i).getNextState().equals(state)) {
                transitions.remove(i);
            } else {
                i++;
            }
        }
    }
    
    public void removeTransition(TMTransition transition) {
        transitions.remove(transition);
    }
    
    public void start(String word) {
        configuration = new TMConfiguration(initialState, word.toCharArray(), 0);
    }
    
    public boolean next() {
        boolean dead = true;
        if (!configuration.isDead() && !configuration.isAccepted()) {
            for (TMTransition transition : transitions) {
                try {
                    configuration = transition.execute(configuration);
                    dead = false;
                    return true;
                } catch (TMTransitionException ex) {
                    LOGGER.info(ex.getMessage());
                }
            }
        }
        configuration.setDead(dead);
        return false;
    }
    
    private boolean checkAcceptedStates() {
        for (TMState state : states) {
            if (state.isAccepted()) {
                return true;
            }
        }
        return false;
    }
    
    public void validate() throws TMException {
        if (initialState == null) {
            throw new TMException("No initial state found.");
        } else if (!checkAcceptedStates()) {
            throw new TMException("Must exist at least one accepted state.");
        }
    }
    
    public boolean checkReachableStates() {
        reachableStates.clear();
        if (states.isEmpty()) {
            return true;
        }
        reachableStates.add(initialState);
        for (int i = 0; i < reachableStates.size(); i++) {
            for (TMTransition transition : transitions) {
                if (transition.getInitialState().equals(reachableStates.get(i))) {
                    if (!reachableStates.contains(transition.getNextState())) {
                        reachableStates.add(transition.getNextState());
                    }
                }
            }
        }
        return reachableStates.size() == states.size();
   }
    
    public void removeUnreachableStates() {
        Set<TMState> unreachableStates = new HashSet<>();
        for (TMState current : states) {
            if (!reachableStates.contains(current)) {
                unreachableStates.add(current);
            }
        }
        for (TMState current : unreachableStates) {
            removeState(current);
        }
        MainFrame.drawingState = MainFrame.DrawingState.Drawing;
    }
    
    public void updateTransitions() {
        for (TMTransition current : transitions) {
            current.calculatePos();
        }
    }
}
