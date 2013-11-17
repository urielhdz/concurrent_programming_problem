package concurrentproject;

import java.awt.GridLayout;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainWindow extends JFrame {

    private Panel playerPanel;
    private Panel clientPanel;
    private Panel stadiumPanel;
    private Panel fieldPanel;

    public MainWindow() {
        super("Stadium simulator");
 
        Field field = new Field(11);
        for(int i = 1;i<=30;i++){
            Player p = new Player(field,1,i);
            Player p2 = new Player(field,2,i);
            Thread t = new Thread(p);
            t.start();
            t = new Thread(p2);
            t.start();
        }
        
        //The limit of clients allowed to be in the stadium is passed through the constructor.
        int limit = Integer.parseInt(JOptionPane.showInputDialog(null, "Limit of seats for the stadium ", 
                                    "Concurrent Programming", 1));
        Stadium s = new Stadium(limit,field);
        //Makes the clients go the stadium
        PushClients pc = new PushClients(s);
        Thread t = new Thread(field);
        t.start();
        t = new Thread(pc);
        t.start();
        t = new Thread(s);
        t.start();

        setSize(800, 600);
        setLocationRelativeTo(null);
        setLayout(new GridLayout(0, 2, 15, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        playerPanel = new Panel("Jugador");
        clientPanel = new Panel("Cliente");
        stadiumPanel = new Panel("Estadio");
        fieldPanel = new Panel("Campo");


        field.addTextModifier(new TextModifiers(){
            @Override
            public void modifyPlayerPanel(String text) {
            }
            @Override
            public void modifyClientPanel(String text) {
            }
            @Override
            public void modifyStadiumPanel(String text) {
            }
            @Override
            public void modifyFieldPanel(String text) {
                fieldPanel.addText(text);
            }
        });
        s.addTextModifier(new TextModifiers(){
            @Override
            public void modifyPlayerPanel(String text) {
            }
            @Override
            public void modifyClientPanel(String text) {
                clientPanel.addText(text);
            }
            @Override
            public void modifyStadiumPanel(String text) {
            }
            @Override
            public void modifyFieldPanel(String text) {
            }
        });

        //add(playerPanel);
        add(clientPanel);
        //add(stadiumPanel);
        add(fieldPanel);
        setVisible(true);
    }
}
