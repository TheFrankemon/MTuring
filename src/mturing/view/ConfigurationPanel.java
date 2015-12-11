package mturing.view;

import mturing.data.Constants;
import mturing.model.TMConfiguration;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Allan Leon
 */
public class ConfigurationPanel extends JPanel {
    
    private final TMConfiguration configuration;
    private JLabel label;
    
    public ConfigurationPanel(TMConfiguration configuration) {
        this.configuration = configuration;
        initComponents();
    }

    private void initComponents() {
        setBackground(Color.LIGHT_GRAY);
        if (configuration.isAccepted()) {
            setBackground(Color.GREEN);
        } else if (configuration.isDead()) {
            setBackground(Color.RED);
        }
        
        setBorder(BorderFactory.createBevelBorder(1, Color.WHITE, Color.DARK_GRAY));
        setPreferredSize(new Dimension(Constants.CONFIGURATION_WIDTH, Constants.CONFIGURATION_HEIGHT));
        
        label = new JLabel();
        if (configuration.getWord().equals("")) {
            label.setText(String.format("%s, %s", configuration.getState().getName(), '\u03B5'));
        } else {
            label.setText(String.format("%s, %s", configuration.getState().getName(), configuration.getWord()));
        }
        
        setToolTipText(label.getText());
        add(label);
    }
}