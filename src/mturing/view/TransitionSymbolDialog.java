package mturing.view;

import mturing.model.Transition;
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

/**
 *
 * @author Allan Leon
 */
public class TransitionSymbolDialog extends JDialog {

    private JPanel contentPane;
    private JTextField textBox;
    private JButton addBtn;
    private JButton okBtn;
    private JButton xBtn;
    private JLabel symbols;
    private Transition transition;

    public TransitionSymbolDialog(JFrame parent, Transition transition) {
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

        textBox = new JTextField();
        textBox.setDocument(new JTextFieldLimit(1));
        textBox.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent ae) {
                addSymbol();
            }
        });

        addBtn = new JButton("+");
        addBtn.setFocusable(false);
        setMaterialLNF(addBtn);
        addBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                addSymbol();
            }
        });

        okBtn = new JButton("OK");
        okBtn.setFocusable(false);
        setMaterialLNF(okBtn);
        okBtn.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                if (transition.getSymbols().size() > 0) {
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

        symbols = new JLabel(transition.getTransitionText());
        symbols.setForeground(Color.YELLOW);

        textBox.setBounds(70, 30, 100, 30);
        addBtn.setBounds(200, 30, 50, 30);
        okBtn.setBounds(260, 30, 60, 30);
        xBtn.setBounds(10, 30, 50, 30);
        symbols.setBounds(10, 80, 350, 20);

        contentPane.add(textBox);
        contentPane.add(addBtn);
        contentPane.add(okBtn);
        contentPane.add(xBtn);
        contentPane.add(symbols);
    }

    private void addSymbol() {
        if (textBox.getText().length() == 0) {
            transition.addSymbol('\u03B5');
        } else {
            transition.addSymbol(textBox.getText().charAt(0));
            textBox.setText("");
        }
        symbols.setText(transition.getTransitionText());
    }

    private void removeTransition() {
        MainFrame.getAutomaton().getTransitions().remove(transition);
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