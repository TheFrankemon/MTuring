package mturing.controller;

import mturing.view.MainFrame;
import mturing.view.TransitionSymbolDialog;
import mturing.view.MainFrame.DrawingState;
import mturing.view.MainFrame.ModState;
import mturing.data.Constants;
import mturing.model.State;
import mturing.model.Transition;
import mturing.model.basics.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.util.Iterator;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

public class MouseHandler implements MouseListener, MouseMotionListener {

    private State selectedState;
    private State startState;
    private State endState;
    private Iterator iter;
    private boolean found;
    private JFrame parent;

    public MouseHandler(JFrame parent) {
        this.parent = parent;
        selectedState = null;
        startState = null;
        endState = null;
    }
    
    public void reset() {
        selectedState = null;
        startState = null;
        endState = null;
    }

    private Point getClickedPoint(MouseEvent e) {
        int x = e.getX();
        int y = Constants.MAINFRAME_PANEL_HEIGHT - e.getY();
        return new Point(x, y);
    }

    private void handleStateCreation(Point clickedPoint) {
        MainFrame.getAutomaton().createState(clickedPoint);
        MainFrame.drawingState = DrawingState.Drawing;
    }
    
    private void handleTransitionCreation(Point clickedPoint) {
        handleStateSelection(clickedPoint);
        if (selectedState != null) {
            if (startState == null) {
                startState = selectedState;
                startState.setType(State.StateType.START);
            } else if (endState == null) {
                endState = selectedState;
                endState.setType(State.StateType.END);
            }
            MainFrame.drawingState = DrawingState.Drawing;
        }
        if (endState != null && startState != null) {
            new TransitionSymbolDialog(parent, MainFrame.getAutomaton().createTransition(startState, endState));
            startState.setType(State.StateType.NORMAL);
            endState.setType(State.StateType.NORMAL);
            reset();
            MainFrame.drawingState = DrawingState.Drawing;
        }
    }
    
    private void handleTransitionDeletion(Point clickedPoint) {
        iter = MainFrame.getAutomaton().getTransitions().iterator();
        found = false;
        Transition current;
        while (iter.hasNext() && !found) {
            current = (Transition) iter.next();
            if (current.checkPointCollision(clickedPoint)) {
                MainFrame.getAutomaton().removeTransition(current);
                MainFrame.drawingState = DrawingState.Drawing;
                found = true;
            }
        }
    }
    
    private void handleStateDeletion(Point clickedPoint) {
        iter = MainFrame.getAutomaton().getStates().iterator();
        found = false;
        State current;
        while (iter.hasNext() && !found) {
            current = (State) iter.next();
            if (current.checkPointCollision(clickedPoint)) {
                MainFrame.getAutomaton().removeState(current);
                MainFrame.drawingState = DrawingState.Drawing;
                found = true;
            }
        }
    }
    
    private void handleStateSelection(Point clickedPoint) {
        selectedState = null;
        for (State current : MainFrame.getAutomaton().getStates()) {
            if (current.checkPointCollision(clickedPoint)) {
                selectedState = current;
            }
        }
    }

    private void handleStateMovement(Point draggedPoint) {
        if (selectedState != null) {
            selectedState.getPos().setX(draggedPoint.getX());
            selectedState.getPos().setY(draggedPoint.getY());
            MainFrame.getAutomaton().updateTransitions();
            MainFrame.drawingState = DrawingState.Drawing;
        }
    }
    
    private void handleStateAccepted(Point clickedPoint) {
        for (State current : MainFrame.getAutomaton().getStates()) {
            if (current.checkPointCollision(clickedPoint)) {
                current.updateAccepted();
                MainFrame.drawingState = DrawingState.Drawing;
            }
        }
    }
    
    private void handleStateInitial(Point clickedPoint) {
        for (State current : MainFrame.getAutomaton().getStates()) {
            if (current.checkPointCollision(clickedPoint)) {
                MainFrame.getAutomaton().setInitialState(current);
                MainFrame.drawingState = DrawingState.Drawing;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        Point clickedPoint = getClickedPoint(e);
        if (MainFrame.modState == ModState.Creating) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                handleStateCreation(clickedPoint);
            } else if (SwingUtilities.isRightMouseButton(e)) {
                handleStateDeletion(clickedPoint);
            }
        } else if (MainFrame.modState == ModState.Editing) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                handleStateInitial(clickedPoint);
            } else if (SwingUtilities.isRightMouseButton(e)) {
                handleStateAccepted(clickedPoint);
            }
        } else if (MainFrame.modState == ModState.Transition) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                handleTransitionCreation(clickedPoint);
            } else if (SwingUtilities.isRightMouseButton(e)) {
                handleTransitionDeletion(clickedPoint);
            }
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (SwingUtilities.isLeftMouseButton(e)) {
            Point clickedPoint = getClickedPoint(e);
            handleStateSelection(clickedPoint);
        }
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        if (MainFrame.modState == ModState.Creating) {
            if (SwingUtilities.isLeftMouseButton(e)) {
                Point draggedPoint = getClickedPoint(e);
                handleStateMovement(draggedPoint);
            }
        }
    }

    @Override
    public void mouseEntered(MouseEvent e) { }

    @Override
    public void mouseExited(MouseEvent e) { }

    @Override
    public void mouseReleased(MouseEvent e) { }

    @Override
    public void mouseMoved(MouseEvent e) { }
}
