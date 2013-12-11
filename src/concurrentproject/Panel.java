package concurrentproject;

import java.awt.BorderLayout;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.ComponentOrientation;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;

import javax.swing.JLabel;
import javax.swing.JPanel;

public class Panel extends JPanel {

    private JLabel labelClient[];
    private JLabel textField[];
    
    private JLabel labelTeam1[];
    private JLabel textTeam1[];
    
    private JLabel labelTeam2[];
    private JLabel textTeam2[];
    
    private JPanel statePanel;
    private JPanel teamsPanel;
    public Lienzo canvas;
    
    private JButton buton;
    private JFrame jCredits;
    public Panel(String description) {
        
        this.setLayout(new BorderLayout());
        buton = new JButton("Credits");
        
        canvas = new Lienzo();
        jCredits = new JFrame("Credits");
        canvas.setBackground(Color.WHITE);
        labelClient = new JLabel[7];
        labelTeam1 = new JLabel[6];
        labelTeam2 = new JLabel[6];
        textTeam1 = new JLabel[6];
        textTeam2 = new JLabel[6];
        
        statePanel = new JPanel();
        teamsPanel = new JPanel();
        statePanel.setLayout(new FlowLayout());
        teamsPanel.setLayout(new GridLayout(2,10,2,2));
       
        textField = new JLabel[6];
        for(int x=0; x<6; x++){
            labelClient[x] = new JLabel("Label "+x);
            textField[x] = new JLabel();
        }
        for(int x=0; x<5;x++){
            labelTeam1[x] = new JLabel("");
            labelTeam2[x] = new JLabel("");
            
            textTeam1[x] = new JLabel("");
            textTeam2[x] = new JLabel("");
        }
        initLabels();
        this.setComponentOrientation(ComponentOrientation.LEFT_TO_RIGHT);
        this.statePanel.add(new JLabel());
        for(int x=0;x<6;x++){
            this.statePanel.add(labelClient[x]);
            this.statePanel.add(textField[x]);
        }
        buton.addActionListener(new ActionListener(){

            public void actionPerformed(ActionEvent e) {
                jCredits.setLayout(new GridLayout(0,1,0,0));
                jCredits.add(new JLabel("Creditos"));
                jCredits.add(new JLabel("Universidad Politecnica de Chiapas"));
                jCredits.add(new JLabel("Marcos Uriel Hernandez Camacho"));
                jCredits.add(new JLabel("Jorge Fernando Palacios de los Santos"));
                jCredits.add(new JLabel("Luis Santiago Vazquez Mancilla"));
                jCredits.setSize(400,150);
                jCredits.setVisible(true);
            }
            
        });
        this.add(buton, BorderLayout.EAST);
        this.add(statePanel, BorderLayout.NORTH);
        this.add(teamsPanel, BorderLayout.SOUTH);
        this.add(canvas,BorderLayout.CENTER);
    }
    
    public void setTeamsText(int team, int index, String text){
        if(team == 1){
            textTeam1[index].setText(text);
        }else if(team == 2){
            textTeam2[index].setText(text);
        }
    }
    
    public void setText(int index, String text){
        textField[index].setText(text + "|  ");
    }
    
    private void initLabels(){
        labelClient[0].setText("In seat:");
        labelClient[1].setText("Paying:");
        labelClient[2].setText("Looking for a seat:");
        labelClient[3].setText("Leaving:");
        labelClient[4].setText("Waiting ticket:");
        labelClient[5].setText("Total Clients:");
        labelTeam1[0].setText("Playing:");
        labelTeam2[0].setText("Playing: ");
        labelTeam1[1].setText("Warming up:");
        labelTeam2[1].setText("Warming up: ");
        labelTeam1[2].setText("Injured:");
        labelTeam2[2].setText("Injured:");
        labelTeam1[3].setText("In the Bench:");
        labelTeam2[3].setText("In the Bench:");
        labelTeam1[4].setText("Leaving the Field:");
        labelTeam2[4].setText("Leaving the Field:");
        for(int x=0; x<5; x++){
            teamsPanel.add(labelTeam1[x]);
            teamsPanel.add(textTeam1[x]);
        }
        for(int x=0; x<5; x++){
            teamsPanel.add(labelTeam2[x]);
            teamsPanel.add(textTeam2[x]);
        }
    }
}
