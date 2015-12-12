/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mturing.model;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import mturing.data.Constants;
import mturing.model.basics.Point;

/**
 *
 * @author Allan Leon
 */
public class TMTransition {
    
    public enum TMMovement {
        LEFT, RIGHT
    };
    
    private TMState initialState;
    private Set<TMTransitionInfo> options;
    private TMState nextState;
    private Point startPos;
    private Point endPos;
    
    public TMTransition(TMState initialState, TMState nextState) {
        this.initialState = initialState;
        this.nextState = nextState;
        this.options = new HashSet<>();
        calculatePos();
    }
    
    /**
     * @return the initialState
     */
    public TMState getInitialState() {
        return initialState;
    }

    /**
     * @return the options
     */
    public Set<TMTransitionInfo> getOptions() {
        return options;
    }

    /**
     * @return the nextState
     */
    public TMState getNextState() {
        return nextState;
    }

    /**
     * @return the startPos
     */
    public Point getStartPos() {
        return startPos;
    }

    /**
     * @return the endPos
     */
    public Point getEndPos() {
        return endPos;
    }
    
    public void addOption(TMTransitionInfo option) {
        options.add(option);
    }

    /**
     * @param initialState the initialState to set
     */
    public void setInitialState(TMState initialState) {
        this.initialState = initialState;
    }

    /**
     * @param options the options to set
     */
    public void setOptions(Set<TMTransitionInfo> options) {
        this.options = options;
    }

    /**
     * @param nextState the nextState to set
     */
    public void setNextState(TMState nextState) {
        this.nextState = nextState;
    }

    /**
     * @param startPos the startPos to set
     */
    public void setStartPos(Point startPos) {
        this.startPos = startPos;
    }

    /**
     * @param endPos the endPos to set
     */
    public void setEndPos(Point endPos) {
        this.endPos = endPos;
    }
    
    /*public List<Configuration> execute(Configuration current) throws TransitionException {
        List<Configuration> nextConfigurations = new ArrayList<>();
        
        if (current.getState().equals(initialState)) {
            for (TransitionInfo info : options) {
                if (current.matches(info.getTop())) {
                    if (info.getSymbol() == Constants.EPSILON) {
                        nextConfigurations.add(new Configuration(nextState,
                                current.getWord(), current.updateStack(info.getNextTop())));
                    } else if (!current.wordIsEmpty()) {
                        if (current.getWord().charAt(0) == info.getSymbol()) {
                        nextConfigurations.add(new Configuration(nextState,
                                current.getWord().substring(1), current.updateStack(info.getNextTop())));
                        }
                    }
                }
            }
        }
        return nextConfigurations;
    }*/
    
    public TMConfiguration execute(TMConfiguration current) throws TMTransitionException {        
        if (current.getState().equals(initialState)) {
            for (TMTransitionInfo info : options) {
                if (current.matches(info)) {
                    return current.execute(info, nextState);
                }
            }
        }
        throw new TMTransitionException("There are no transitions to be executed!");
    }
    
    public String getTransitionText() {
        if (options.isEmpty()) {
            return "";
        } else {
            String transitionText = "";
            for (TMTransitionInfo info : options) {
                transitionText += info.toString() + ";";
            }
            transitionText = transitionText.substring(0, transitionText.length() - 1);
            return transitionText;
        }
    }
    
    public void calculatePos() {
        startPos = getCircleLineIntersectionPoint(initialState.getPos(), nextState.getPos(), initialState.getPos(), true);
        endPos = getCircleLineIntersectionPoint(initialState.getPos(), nextState.getPos(), nextState.getPos(), false);
    }
    
    public static Point getCircleLineIntersectionPoint(Point pointA, Point pointB,
            Point center, boolean start) {
        double baX = pointB.getX() - pointA.getX();
        double baY = pointB.getY() - pointA.getY();
        double caX = center.getX() - pointA.getX();
        double caY = center.getY() - pointA.getY();

        double a = baX * baX + baY * baY;
        double bBy2 = baX * caX + baY * caY;
        double c = caX * caX + caY * caY - Constants.STATE_RADIUS * Constants.STATE_RADIUS;

        double pBy2 = bBy2 / a;
        double q = c / a;

        double disc = pBy2 * pBy2 - q;
        
        double tmpSqrt = Math.sqrt(disc);
        double abScalingFactor1 = -pBy2 + tmpSqrt;
        double abScalingFactor2 = -pBy2 - tmpSqrt;
        
        if (start) {
            return new Point((int) (pointA.getX() - baX * abScalingFactor2),
                            (int) (pointA.getY() - baY * abScalingFactor2));
        }

        return new Point((int) (pointA.getX() - baX * abScalingFactor1), (int) (pointA.getY()
                - baY * abScalingFactor1));
    }
    
    public boolean checkPointCollision(Point point) {
        if (Math.abs(point.getDistanceTo(initialState.getPos()) +
                point.getDistanceTo(nextState.getPos()) -
                initialState.getPos().getDistanceTo(nextState.getPos())) < 0.5) {
            return true;
        }
        return false;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 41 * hash + Objects.hashCode(this.initialState);
        hash = 41 * hash + Objects.hashCode(this.nextState);
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
        final TMTransition other = (TMTransition) obj;
        if (!Objects.equals(this.initialState, other.initialState)) {
            return false;
        }
        if (!Objects.equals(this.nextState, other.nextState)) {
            return false;
        }
        return true;
    }    
}
