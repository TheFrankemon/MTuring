package mturing.view;

import mturing.model.Automaton;
import static mturing.view.MainFrame.setMaterialLNF;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

/**
 *
 * @author Allan Leon
 */
public class ReachableStatesDialog extends JDialog {

    private JPanel contentPane;
    private JLabel textLbl;
    private JButton yesBtn;
    private JButton noBtn;
    private Automaton automaton;

    public ReachableStatesDialog(JFrame parent, Automaton automaton) {
        super(parent);
        this.automaton = automaton;
        initializeComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setModal(true);
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Unreacheable states...");
        setPreferredSize(new Dimension(390, 120));
        setResizable(false);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.BLACK);

        textLbl = new JLabel("Remove unreacheable states?");
        textLbl.setForeground(Color.WHITE);

        yesBtn = new JButton("OK");
        setMaterialLNF(yesBtn);
        yesBtn.setFocusable(false);

        yesBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                automaton.removeUnreachableStates();
                dispose();
            }
        });

        noBtn = new JButton("Cancel");
        setMaterialLNF(noBtn);
        noBtn.setFocusable(false);
        noBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        });

        textLbl.setBounds(20, 5, 380, 30);
        yesBtn.setBounds(60, 40, 100, 30);
        noBtn.setBounds(210, 40, 100, 30);

        contentPane.add(textLbl);
        contentPane.add(yesBtn);
        contentPane.add(noBtn);
    }
}
