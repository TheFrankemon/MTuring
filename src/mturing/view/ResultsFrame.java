package mturing.view;

import mturing.data.Constants;
import mturing.model.TuringMachine;
import static mturing.view.MainFrame.setMaterialLNF;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import mturing.drawer.Drawer;
import mturing.model.TMTransition;

/**
 *
 * @author Allan Leon
 */
public class ResultsFrame extends JFrame {

    private Graphics dbg;
    private BufferedImage doubleBuffer;
    private JPanel panel;
    private TuringMachine turingMachine;
    private JButton stepBtn;

    public ResultsFrame(TuringMachine tm) {
        setVisible(true);
        this.turingMachine = tm;
        initialize();
        paintStart();
    }

    private void initialize() {
        doubleBuffer = new BufferedImage(Constants.RESULTSFRAME_PANEL_WIDTH, Constants.RESULTSFRAME_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
        
        setTitle("Results");
        BufferedImage img;
        try {
            img = ImageIO.read(this.getClass().getResource("/mturing/fsm.jpg"));
            Image dimg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            setIconImage(dimg);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        setSize(Constants.RESULTSFRAME_WIDTH, Constants.RESULTSFRAME_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.BLACK);

        panel = new CustomPanel(doubleBuffer);
        panel.setBackground(new Color(20, 20, 20));
        panel.setBounds(10, 10, Constants.RESULTSFRAME_PANEL_WIDTH, Constants.RESULTSFRAME_PANEL_HEIGHT);

        stepBtn = new JButton(">>>>>>>>");
        stepBtn.setFocusable(false);
        setMaterialLNF(stepBtn);
        stepBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (turingMachine.next()) {
                    try {
                        paintMovement();
                        //System.out.println(turingMachine.getConfiguration().getWordString() + " " + turingMachine.getConfiguration().getHead() + turingMachine.getConfiguration().getWord()[turingMachine.getConfiguration().getHead()] + " " + turingMachine.getConfiguration().getState().getName());
                    } catch (InterruptedException ex) {
                        Logger.getLogger(ResultsFrame.class.getName()).log(Level.SEVERE, null, ex);
                    }
                } else {
                    stepBtn.setEnabled(false);
                }
            }
        });
        MainFrame.setMaterialLNF(stepBtn);
        stepBtn.setBounds(Constants.RESULTSFRAME_WIDTH / 2 - 60, Constants.RESULTSFRAME_HEIGHT - 65, 120, 30);

        getContentPane().add(panel);
        getContentPane().add(stepBtn);
        
    }
    
    private void paintStart() {
        dbg = doubleBuffer.getGraphics();
        dbg.setColor(Color.DARK_GRAY);
        dbg.fillRect(0, 0, Constants.RESULTSFRAME_PANEL_WIDTH, Constants.RESULTSFRAME_PANEL_HEIGHT);
        Drawer.drawTMHead(dbg);
        Drawer.drawTape(dbg, turingMachine.getConfiguration(), 12);
        Drawer.drawMachineState(dbg, turingMachine.getConfiguration());
        panel.getGraphics().drawImage(doubleBuffer, 0, 0, this);
    }
    
    private void paintMovement() throws InterruptedException {
        dbg = doubleBuffer.getGraphics();
        int x = 20;
        int dx = -2;
        if (turingMachine.getConfiguration().getLastMove() == TMTransition.TMMovement.LEFT) {
            x = -20;
            dx = 2;
        }
        while (x != 0) {
            Thread.sleep(60);
            dbg.setColor(Color.DARK_GRAY);
            dbg.fillRect(0, 0, Constants.RESULTSFRAME_PANEL_WIDTH, Constants.RESULTSFRAME_PANEL_HEIGHT);
            Drawer.drawTape(dbg, turingMachine.getConfiguration(), 12 + x);
            Drawer.drawTMHead(dbg);
            Drawer.drawMachineState(dbg, turingMachine.getConfiguration());
            panel.getGraphics().drawImage(doubleBuffer, 0, 0, this);
            x += dx;
        }
    }
}
