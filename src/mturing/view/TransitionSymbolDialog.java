package mturing.view;

import mturing.model.TMTransition;
import static mturing.view.MainFrame.setMaterialLNF;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.border.EmptyBorder;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import mturing.model.TMTransitionInfo;

/**
 *
 * @author Allan Leon
 */
public class TransitionSymbolDialog extends JDialog {

    private JPanel contentPane;
    private JTextField readBox;
    private JTextField writeBox;
    private JTextField moveBox;
    private JButton addBtn;
    private JButton okBtn;
    private JButton xBtn;
    private JLabel commaLbl;
    private JLabel pipeLbl;
    private JLabel options;
    private TMTransition transition;

    public TransitionSymbolDialog(JFrame parent, TMTransition transition) {
        super(parent);
        this.transition = transition;
        initializeComponents();
        setDefaultCloseOperation(DISPOSE_ON_CLOSE);
        pack();
        setModal(true);
        setVisible(true);
    }

    private void initializeComponents() {
        setTitle("Transition Input");
        setPreferredSize(new Dimension(350, 130));
        setResizable(false);
        setLocationRelativeTo(null);

        contentPane = new JPanel();
        contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
        setContentPane(contentPane);
        contentPane.setLayout(null);
        contentPane.setBackground(Color.BLACK);

        readBox = new JTextField();
        readBox.setDocument(new JTextFieldLimit(1));
        readBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                addTransitionInfo();
            }
        });
        
        writeBox = new JTextField();
        writeBox.setDocument(new JTextFieldLimit(1));
        writeBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                addTransitionInfo();
            }
        });
        
        moveBox = new JTextField();
        moveBox.setDocument(new JTextFieldLimit(1));
        moveBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                addTransitionInfo();
            }
        });

        addBtn = new JButton("+");
        addBtn.setFocusable(false);
        setMaterialLNF(addBtn);
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addTransitionInfo();
            }
        });

        okBtn = new JButton("OK");
        okBtn.setFocusable(false);
        setMaterialLNF(okBtn);
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (transition.getOptions().size() > 0) {
                    dispose();
                } else {
                    JOptionPane.showMessageDialog(null, "Transition doesn't contain any symbol!", "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        xBtn = new JButton("X");
        xBtn.setFocusable(false);
        setMaterialLNF(xBtn);
        xBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                removeTransition();
            }
        });

        options = new JLabel(transition.getTransitionText());
        options.setForeground(Color.YELLOW);
        
        commaLbl = new JLabel(",");
        commaLbl.setForeground(Color.WHITE);
        pipeLbl = new JLabel("|");
        pipeLbl.setForeground(Color.WHITE);

        readBox.setBounds(70, 30, 30, 30);
        pipeLbl.setBounds(100, 30, 10, 30);
        writeBox.setBounds(110, 30, 30, 30);
        commaLbl.setBounds(140, 30, 10, 30);
        moveBox.setBounds(150, 30, 30, 30);
        addBtn.setBounds(200, 30, 50, 30);
        okBtn.setBounds(260, 30, 60, 30);
        xBtn.setBounds(10, 30, 50, 30);
        options.setBounds(10, 80, 350, 20);

        contentPane.add(readBox);
        contentPane.add(pipeLbl);
        contentPane.add(writeBox);
        contentPane.add(commaLbl);
        contentPane.add(moveBox);
        contentPane.add(addBtn);
        contentPane.add(okBtn);
        contentPane.add(xBtn);
        contentPane.add(options);
    }

    private void addTransitionInfo() {
        if (readBox.getText().length() > 0 && writeBox.getText().length() > 0 &&
                (moveBox.getText().equals("<") || moveBox.getText().equals(">"))) {
            TMTransition.TMMovement movement = TMTransition.TMMovement.RIGHT;
            if (moveBox.getText().equals("<")) {
                movement = TMTransition.TMMovement.LEFT;
            }
            transition.addOption(new TMTransitionInfo(readBox.getText().charAt(0), writeBox.getText().charAt(0), movement));
            readBox.setText("");
            writeBox.setText("");
            moveBox.setText("");
            options.setText(transition.getTransitionText());
        }
    }

    private void removeTransition() {
        MainFrame.getTuringMachine().getTransitions().remove(transition);
        dispose();
    }
    
    class JTextFieldLimit extends PlainDocument {
        
        private int limit;
        
        JTextFieldLimit(int limit) {
            super();
            this.limit = limit;
        }

        JTextFieldLimit(int limit, boolean upper) {
            super();
            this.limit = limit;
        }

        @Override
        public void insertString(int offset, String str, AttributeSet attr) throws BadLocationException {
            if (str == null) {
                return;
            }

            if ((getLength() + str.length()) <= limit) {
                super.insertString(offset, str, attr);
            }
        }
    }
}