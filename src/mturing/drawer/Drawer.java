package mturing.drawer;

import mturing.data.Constants;
import mturing.model.TMState;
import mturing.model.TMState.StateType;
import mturing.model.basics.Point;
import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Stroke;
import java.awt.geom.AffineTransform;
import java.util.List;
import java.util.Set;
import mturing.model.TMConfiguration;
import mturing.model.TMTransition;
import mturing.model.TuringMachine;

/**
 *
 * @author Allan Leon
 */
public class Drawer {

    private static final int ARR_SIZE = 5;

    private static void putPixel(Graphics g, int x, int y) {
        g.drawLine(x, Constants.MAINFRAME_PANEL_HEIGHT - y, x, Constants.MAINFRAME_PANEL_HEIGHT - y);
    }

    public static void drawLine(Graphics g, int x0, int y0, int x1, int y1, Color color) {
        g.setColor(color);

        int dx, dy, d, x, y, deltaE, deltaNE, stepx = 0, stepy = 0;
        dx = x1 - x0;
        dy = y1 - y0;
        if (dx < 0) {
            dx = -dx;
            stepx = -1;
        } else if (dx > 0) {
            stepx = 1;
        }
        if (dy < 0) {
            dy = -dy;
            stepy = -1;
        } else if (dy > 0) {
            stepy = 1;
        }
        x = x0;
        y = y0;
        putPixel(g, x, y);
        if (dx > dy) {
            d = 2 * dy - dx;
            deltaE = 2 * dy;
            deltaNE = 2 * (dy - dx);
            while (x != x1) {
                x += stepx;
                if (d < 0) {
                    d += deltaE;
                } else {
                    y += stepy;
                    d += deltaNE;
                }
                putPixel(g, x, y);
            }
        } else {
            d = -2 * dx + dy;
            deltaE = -2 * dx;
            deltaNE = 2 * (dy - dx);
            while (y != y1) {
                y += stepy;
                if (d > 0) {
                    d += deltaE;
                } else {
                    x += stepx;
                    d += deltaNE;
                }
                putPixel(g, x, y);
            }
        }
    }

    public static void drawDashedLine(Graphics g, int x0, int y0, int x1, int y1, Color color) {
        g.setColor(color);

        int dx, dy, d, x, y, deltaE, deltaNE, stepx = 0, stepy = 0;
        dx = x1 - x0;
        dy = y1 - y0;
        if (dx < 0) {
            dx = -dx;
            stepx = -1;
        } else if (dx > 0) {
            stepx = 1;
        }
        if (dy < 0) {
            dy = -dy;
            stepy = -1;
        } else if (dy > 0) {
            stepy = 1;
        }
        x = x0;
        y = y0;
        int n = 1;
        putPixel(g, x, y);
        if (dx > dy) {
            d = 2 * dy - dx;
            deltaE = 2 * dy;
            deltaNE = 2 * (dy - dx);
            while (x != x1) {
                x += stepx;
                if (d < 0) {
                    d += deltaE;
                } else {
                    y += stepy;
                    d += deltaNE;
                }
                if (n == 1 || n == 2 || n == 3) {
                    putPixel(g, x, y);
                } else {
                    if (n > 6) {
                        n = 0;
                    }
                }
                n++;
            }
        } else {
            d = -2 * dx + dy;
            deltaE = -2 * dx;
            deltaNE = 2 * (dy - dx);
            while (y != y1) {
                y += stepy;
                if (d > 0) {
                    d += deltaE;
                } else {
                    x += stepx;
                    d += deltaNE;
                }
                if (n == 1 || n == 2 || n == 3) {
                    putPixel(g, x, y);
                } else {
                    if (n > 5) {
                        n = 0;
                    }
                }
                n++;
            }
        }
    }

    public static void drawDashedCircle(Graphics g, int x0, int y0, Color color) {
        Graphics2D g2d = (Graphics2D) g.create();
        g2d.setColor(color);
//        g2d.fillPolygon(ints, ints1, y0);
        Stroke dashed = new BasicStroke(1, BasicStroke.CAP_BUTT, BasicStroke.JOIN_BEVEL, 0, new float[]{3}, 0);
        g2d.setStroke(dashed);
        g2d.drawOval(x0 + 6, Constants.MAINFRAME_PANEL_HEIGHT - y0 - Constants.STATE_RADIUS - 15, 20, 20);
        g2d.dispose();
    }

    public static void drawArrow(Graphics g, int x0, int y0, int x1, int y1, Color color) {
        g.setColor(color);
        drawLine(g, x1 - 10, y1, x1, y1, color);
        drawLine(g, x1, y1 - 10, x1, y1, color);
        drawLine(g, x0, y0, x1, y1, color);
    }

    public static void drawCircle(Graphics g, int centerX, int centerY, int radius, Color color) {
        g.setColor(color);

        int x, y, d, dE, dSE;
        x = 0;
        y = radius;
        d = 1 - radius;
        dE = 3;
        dSE = -2 * radius + 5;
        simetry(g, x, y, centerX, centerY);
        while (y > x) {
            if (d < 0) {
                d += dE;
                dE += 2;
                dSE += 2;
                x += 1;
            } else {
                d += dSE;
                dE += 2;
                dSE += 4;
                x += 1;
                y += -1;
            }
            simetry(g, x, y, centerX, centerY);
        }
    }

    private static void drawArrow(Graphics g1, Point p1, Point p2) {
        Graphics2D g = (Graphics2D) g1.create();
        int y1 = Constants.MAINFRAME_PANEL_HEIGHT - p1.getY();
        int y2 = Constants.MAINFRAME_PANEL_HEIGHT - p2.getY();
        double dx = p2.getX() - p1.getX();
        double dy = y2 - y1;
        double angle = Math.atan2(dy, dx);
        int len = (int) Math.sqrt(dx * dx + dy * dy);
        AffineTransform at = AffineTransform.getTranslateInstance(p1.getX(), y1);
        at.concatenate(AffineTransform.getRotateInstance(angle));
        g.transform(at);
        g.fillPolygon(new int[]{len, len - ARR_SIZE, len - ARR_SIZE, len},
                new int[]{0, -ARR_SIZE, ARR_SIZE, 0}, 4);
    }

    private static void simetry(Graphics g, int x, int y, int centerX, int centerY) {
        putPixel(g, x + centerX, y + centerY);
        putPixel(g, y + centerX, x + centerY);
        putPixel(g, y + centerX, -x + centerY);
        putPixel(g, x + centerX, -y + centerY);
        putPixel(g, -x + centerX, -y + centerY);
        putPixel(g, -y + centerX, -x + centerY);
        putPixel(g, -y + centerX, x + centerY);
        putPixel(g, -x + centerX, y + centerY);
    }

    public static void drawTransition(Graphics g, TMTransition transition) {
        Point start = transition.getStartPos();
        Point end = transition.getEndPos();
        if (start.getX() == end.getX() && start.getY() == end.getY()) {
            start = transition.getInitialState().getPos();
            drawDashedCircle(g, start.getX(), start.getY(), Color.WHITE);
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            g.drawString(transition.getTransitionText(), start.getX() + Constants.STATE_RADIUS + 7,
                    Constants.MAINFRAME_PANEL_HEIGHT - start.getY() - 35);
        } else {
            drawDashedLine(g, start.getX(), start.getY(), end.getX(), end.getY(), Color.WHITE);
            drawArrow(g, start, end);
            g.setColor(Color.YELLOW);
            g.setFont(new Font("Segoe UI", Font.PLAIN, 12));
            g.drawString(transition.getTransitionText(), (start.getX() + end.getX()) / 2,
                    Constants.MAINFRAME_PANEL_HEIGHT - ((start.getY() + end.getY()) / 2) - 5);
        }
    }

    public static void drawInitialStateArrow(Graphics g, TMState state) {
        if (state != null) {
            drawArrow(g, state.getPos().getX() - Constants.STATE_RADIUS - 18,
                    state.getPos().getY() - 30, state.getPos().getX() - Constants.STATE_RADIUS + 2,
                    state.getPos().getY() - 10, Color.YELLOW);
        }
    }

    public static void drawState(Graphics g, TMState state) {
        if (state.isAccepted()) {
            drawCircle(g, state.getPos().getX(), state.getPos().getY(), Constants.STATE_RADIUS * 2 / 3, Color.WHITE);
        }
        if (state.getType() == StateType.NORMAL) {
            drawCircle(g, state.getPos().getX(), state.getPos().getY(), Constants.STATE_RADIUS, Color.WHITE);
        } else if (state.getType() == StateType.START) {
            drawCircle(g, state.getPos().getX(), state.getPos().getY(), Constants.STATE_RADIUS, Color.CYAN);
        } else if (state.getType() == StateType.END) {
            drawCircle(g, state.getPos().getX(), state.getPos().getY(), Constants.STATE_RADIUS, Color.ORANGE);
        }
        g.setColor(Color.DARK_GRAY);
        g.fillRect(state.getPos().getX() - 6, Constants.MAINFRAME_PANEL_HEIGHT - state.getPos().getY() - 8, 15, 15);
        g.setColor(Color.MAGENTA);
        g.setFont(new Font("Segoe UI", Font.PLAIN, 12));
        g.drawString(state.getName(), state.getPos().getX() - 5, Constants.MAINFRAME_PANEL_HEIGHT - state.getPos().getY() + 3);
    }

    public static void drawTransitions(Graphics g, List<TMTransition> transitions) {
        for (TMTransition transition : transitions) {
            drawTransition(g, transition);
        }
    }

    public static void drawStates(Graphics g, Set<TMState> states) {
        for (TMState state : states) {
            drawState(g, state);
        }
    }

    public static void drawTuringMachine(Graphics g, TuringMachine turingMachine) {
        drawTransitions(g, turingMachine.getTransitions());
        drawStates(g, turingMachine.getStates());
        drawInitialStateArrow(g, turingMachine.getInitialState());
    }
    
    public static void drawTape(Graphics g, char[] tape, int posx) {
        g.setColor(Color.GREEN);
        g.setFont(new Font("Segoe UI", Font.PLAIN, 24));
        String shown = new String(tape);
        g.drawString(shown, posx, Constants.RESULTSFRAME_PANEL_HEIGHT / 2);
        System.out.printf("%s %d %d\n", shown, posx, Constants.RESULTSFRAME_PANEL_HEIGHT / 2);
    }
}
