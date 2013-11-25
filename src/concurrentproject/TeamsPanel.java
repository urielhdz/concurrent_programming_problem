package concurrentproject;

import java.awt.BorderLayout;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author shagm_000
 */
public class TeamsPanel extends JPanel{
    
    private JLabel label[];
    private JLabel textField[];
    private JPanel statePanel;
    
    public TeamsPanel(int numlabels, String description){
        this.setLayout(new BorderLayout());
        label = new JLabel[numlabels+1];
        statePanel = new JPanel();
        this.statePanel.setLayout(new FlowLayout());
        
        textField = new JLabel[numlabels];
        for(int x=0; x<numlabels; x++){
            label[x] = new JLabel("Label "+x);
            textField[x] = new JLabel();
        }
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.statePanel.add(new JLabel());
        for(int x=0;x<numlabels;x++){
            this.statePanel.add(label[x]);
            this.statePanel.add(textField[x]);
        }
        this.add(statePanel, BorderLayout.NORTH);
    }
    
     public void changeLabelText(int index, String text){
        label[index].setText(text);
    }
    
    public void setText(int index, String text){
        textField[index].setText(text + "||");
    }
}
