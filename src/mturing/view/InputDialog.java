package mturing.view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import mturing.model.TuringMachine;
import static mturing.view.MainFrame.setMaterialLNF;

/**
 *
 * @author Allan Leon
 */
public class InputDialog extends JDialog {
    
    private JPanel contentPane;
    private JTextField textBox;
    private JButton okBtn;
    private TuringMachine turingMachine;
    
    public InputDialog(JFrame parent, TuringMachine tm) {
        super(parent);
        this.turingMachine = tm;
        initializeComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack(); 
        setModal(true);
        setVisible(true);
    }
    
    private void initializeComponents() {
        setTitle("Input Test");
        setPreferredSize(new Dimension(350, 100));
        setResizable(false);
        setLocationRelativeTo(null);
        
        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.BLACK);
        
        textBox = new JTextField();
        textBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                dispose();
                execute();
            }
        });
        
        okBtn = new JButton("OK");
        setMaterialLNF(okBtn);
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                execute();
            }
        });
        
        textBox.setBounds(50, 30, 150, 30);
        okBtn.setBounds(260, 30, 60, 30);
        
        contentPane.add(textBox);
        contentPane.add(okBtn);
    }
    
    private void execute() {
        turingMachine.start(textBox.getText());
        dispose();
        new ResultsFrame(turingMachine);
    }
}