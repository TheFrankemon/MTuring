package mturing.view;

import mturing.data.Constants;
import mturing.model.Automaton;
import mturing.model.Configuration;
import static mturing.view.MainFrame.setMaterialLNF;
import java.awt.Color;
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
import javax.swing.JScrollPane;

/**
 *
 * @author Allan Leon
 */
public class ResultsFrame extends JFrame {

    private JScrollPane scroll;
    private JPanel panel;
    private Automaton automaton;
    private JButton stepBtn;

    public ResultsFrame(Automaton automaton) {
        setVisible(true);
        this.automaton = automaton;
        initialize();
    }

    private void initialize() {
        setTitle("Results");
        BufferedImage img;
        try {
            img = ImageIO.read(this.getClass().getResource("/dfautomaton/fsm.jpg"));
            Image dimg = img.getScaledInstance(40, 40, Image.SCALE_SMOOTH);
            setIconImage(dimg);
        } catch (IOException ex) {
            Logger.getLogger(MainFrame.class.getName()).log(Level.SEVERE, null, ex);
        }

        setSize(Constants.RESULTS_WINDOW_WIDTH, Constants.RESULTS_WINDOW_HEIGHT);
        setResizable(false);
        setLocationRelativeTo(null);
        getContentPane().setLayout(null);
        getContentPane().setBackground(Color.BLACK);

        panel = new JPanel(new GridLayout(1, 0));
        panel.setBackground(Color.DARK_GRAY);
        addConfigurationsPanel(automaton.getConfigurations());

        scroll = new JScrollPane(panel);
        scroll.setBackground(Color.DARK_GRAY);
        scroll.setBounds(2, 0, Constants.RESULTS_WIDTH, Constants.RESULTS_HEIGHT);

        stepBtn = new JButton(">>>>>>>>");
        stepBtn.setFocusable(false);
        setMaterialLNF(stepBtn);
        stepBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (automaton.next()) {
                    addConfigurationsPanel(automaton.getConfigurations());
                } else {
                    stepBtn.setEnabled(false);
                }
            }
        });
        MainFrame.setMaterialLNF(stepBtn);
        stepBtn.setBounds(Constants.RESULTS_WINDOW_WIDTH / 2 - 60, Constants.RESULTS_WINDOW_HEIGHT - 65, 120, 30);

        getContentPane().add(scroll);
        getContentPane().add(stepBtn);
    }

    private void addConfigurationsPanel(List<Set<Configuration>> configurations) {
        JPanel newPanel = new JPanel(new GridLayout(0, 1));
        for (Configuration conf : configurations.get(0)) {
            newPanel.add(new ConfigurationPanel(conf));
        }
        panel.add(newPanel);
        panel.revalidate();
    }
}
