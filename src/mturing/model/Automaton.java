package mturing.model;

import mturing.model.basics.Point;
import mturing.view.MainFrame;
import mturing.view.MainFrame.DrawingState;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Allan Leon
 */
public class Automaton {
    
    private Set<State> states;
    private List<Transition> transitions;
    private List<Set<Configuration>> configurations;
    private List<State> reachableStates;
    private State initialState;
    private int createdStatesQuantity;
    
    public Automaton() {
        states = new HashSet<>();
        reachableStates = new ArrayList<>();
        transitions = new ArrayList<>();
        initialState = null;
        configurations = new ArrayList<>();
        createdStatesQuantity = 0;
    }

    public Set<State> getStates() {
        return states;
    }

    public void setStates(Set<State> states) {
        this.states = states;
    }

    public List<Transition> getTransitions() {
        return transitions;
    }
    
    public List<Set<Configuration>> getConfigurations() {
        return configurations;
    }
    
    public int getCreatedStatesQuantity() {
        return createdStatesQuantity;
    }

    public void setTransitions(ArrayList<Transition> transitions) {
        this.transitions = transitions;
    }

    public State getInitialState() {
        return initialState;
    }

    public void setInitialState(State initialState) {
        this.initialState = initialState;
    }
    
    public void addState(State state) {
        states.add(state);
        createdStatesQuantity++;
    }
    
    public void createState(Point clickedPoint) {
        addState(new State(String.format("q%d", createdStatesQuantity), false, clickedPoint));
    }
    
    public Transition createTransition(State initial, State next) {
        Transition newTransition = new Transition(initial, next);
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
    
    public void addTransition(Transition transition) {
        transitions.add(transition);
    }
    
    public void removeState(State state) {
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
    
    public void removeTransition(Transition transition) {
        transitions.remove(transition);
    }
    
    public void start(String word) {
        configurations.clear();
        Set<Configuration> startList = new HashSet<>();
        startList.add(new Configuration(initialState, word));
        configurations.add(startList);
    }
    
    public boolean next() {
        Set<Configuration> nextList = new HashSet<>();
        boolean ok;
        for (Configuration current : configurations.get(0)) {
            if (!current.isDead() && !current.isValid()) {
                ok = false;
                for (Transition transition : transitions) {
                    try {
                        for (Configuration newConf : transition.execute(current)) {
                            nextList.add(newConf);
                            ok = true;
                        }
                    } catch (TransitionException ex) {
                        Logger.getLogger(Automaton.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
                if (!ok) {
                    current.setDead(true);
                    nextList.add(current);
                }
            }
        }
        if (nextList.size() > 0) {
            configurations.add(nextList);
            configurations.remove(0);
            return true;
        }
        return false;
    }
    
    private boolean checkAcceptedStates() {
        for (State state : states) {
            if (state.isAccepted()) {
                return true;
            }
        }
        return false;
    }
    
    public void validate() throws AutomatonException {
        if (initialState == null) {
            throw new AutomatonException("No initial state found.");
        } else if (!checkAcceptedStates()) {
            throw new AutomatonException("Must exist at least one accepted state.");
        }
    }
    
    public boolean checkReachableStates() {
        reachableStates.clear();
        if (states.isEmpty()) {
            return true;
        }
        reachableStates.add(initialState);
        for (int i = 0; i < reachableStates.size(); i++) {
            for (Transition transition : transitions) {
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
        Set<State> unreachableStates = new HashSet<>();
        for (State current : states) {
            if (!reachableStates.contains(current)) {
                unreachableStates.add(current);
            }
        }
        for (State current : unreachableStates) {
            removeState(current);
        }
        MainFrame.drawingState = DrawingState.Drawing;
    }
    
    public void updateTransitions() {
        for (Transition current : transitions) {
            current.calculatePos();
        }
    }
}
