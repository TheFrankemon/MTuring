package mturing.view;

import mturing.data.Constants;
import mturing.model.TuringMachine;
import mturing.model.TMConfiguration;
import static mturing.view.MainFrame.setMaterialLNF;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Polygon;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import mturing.drawer.Drawer;

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
    private int mid = Constants.RESULTSFRAME_PANEL_WIDTH / 2;

    public ResultsFrame(TuringMachine tm) {
        setVisible(true);
        this.turingMachine = tm;
        initialize();
    }

    private void initialize() {
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

        panel = new JPanel();
        panel.setBackground(new Color(20, 20, 20));
        panel.setBounds(10, 10, Constants.RESULTSFRAME_PANEL_WIDTH, Constants.RESULTSFRAME_PANEL_HEIGHT);
        //addConfigurationsPanel(turingMachine.getConfigurations());

        stepBtn = new JButton(">>>>>>>>");
        stepBtn.setFocusable(false);
        setMaterialLNF(stepBtn);
        stepBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               paint();
                if (turingMachine.next()) {
                    System.out.println(turingMachine.getConfiguration().getWordString() + " " + turingMachine.getConfiguration().getHead() + turingMachine.getConfiguration().getWord()[turingMachine.getConfiguration().getHead()] + " " + turingMachine.getConfiguration().getState().getName());
                    //addConfigurationsPanel(turingMachine.getConfigurations());
                } else {
                    stepBtn.setEnabled(false);
                }
            }
        });
        MainFrame.setMaterialLNF(stepBtn);
        stepBtn.setBounds(Constants.RESULTSFRAME_WIDTH / 2 - 60, Constants.RESULTSFRAME_HEIGHT - 65, 120, 30);

        getContentPane().add(panel);
        getContentPane().add(stepBtn);
        doubleBuffer = new BufferedImage(Constants.RESULTSFRAME_PANEL_WIDTH, Constants.RESULTSFRAME_PANEL_HEIGHT, BufferedImage.TYPE_INT_RGB);
    }
    
    private void paint() {
        dbg = doubleBuffer.getGraphics();
        dbg.setColor(Color.DARK_GRAY);
        dbg.fillRect(0, 0, Constants.RESULTSFRAME_PANEL_WIDTH, Constants.RESULTSFRAME_PANEL_HEIGHT);
        drawTMHead();
        Drawer.drawTape(dbg, turingMachine.getConfiguration().getWord(), 192);
        panel.getGraphics().drawImage(doubleBuffer, 0, 0, this);
    }
    
    private void drawTMHead() {
        int[] xs = {mid-13,mid,mid+13};
        int[] ys = {180,150,180};
        Polygon arrowTip = new Polygon(xs, ys, 3);
        dbg.setColor(Color.YELLOW);
        dbg.fillRect(mid - 1, 170, 3, 80);
        dbg.fillPolygon(arrowTip);
    }
    /*private void addConfigurationsPanel(List<Set<Configuration>> configurations) {
        JPanel newPanel = new JPanel(new GridLayout(0, 1));
        for (Configuration conf : configurations.get(0)) {
     //       newPanel.add(new ConfigurationPanel(conf));
        }
        panel.add(newPanel);
        panel.revalidate();
    }*/
}
