package mturing.view;

import mturing.data.Constants;
import mturing.model.Automaton;
import mturing.model.Configuration;
import static mturing.view.MainFrame.setMaterialLNF;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.Image;
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
    private Automaton automaton;
    private JButton stepBtn;
    private char[] ass = {'s','a','l','a','d','a','s','s'};

    public ResultsFrame(Automaton automaton) {
        setVisible(true);
        this.automaton = automaton;
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
        panel.setBackground(Color.DARK_GRAY);
        panel.setBounds(10, 10, Constants.RESULTSFRAME_PANEL_WIDTH, Constants.RESULTSFRAME_PANEL_HEIGHT);
        //addConfigurationsPanel(automaton.getConfigurations());

        stepBtn = new JButton(">>>>>>>>");
        stepBtn.setFocusable(false);
        setMaterialLNF(stepBtn);
        int temp = 200;
        stepBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
               paint();
               // if (automaton.next()) {
               //     addConfigurationsPanel(automaton.getConfigurations());
               // } else {
               //     stepBtn.setEnabled(false);
               // }
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
        dbg.setColor(Color.red);
        dbg.fillRect(0, 0, Constants.RESULTSFRAME_PANEL_WIDTH, Constants.RESULTSFRAME_PANEL_HEIGHT);
        Drawer.drawTape(dbg, ass, temp+10);
        panel.getGraphics().drawImage(doubleBuffer, 0, 0, this);
    }
    
    private void addConfigurationsPanel(List<Set<Configuration>> configurations) {
        JPanel newPanel = new JPanel(new GridLayout(0, 1));
        for (Configuration conf : configurations.get(0)) {
     //       newPanel.add(new ConfigurationPanel(conf));
        }
        panel.add(newPanel);
        panel.revalidate();
    }
}
