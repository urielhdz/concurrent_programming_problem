package concurrentproject;

import javax.swing.JFrame;
import javax.swing.JOptionPane;

public class MainWindow extends JFrame {

    private Panel clientPanel;
    private JFrame f;
    private SpectatosFrame spectatorsFrame;

    public MainWindow() {
        super("Stadium simulator");
        
        int seconds = Integer.parseInt(JOptionPane.showInputDialog(null, "How many seconds do you want the game to be played?"));
        Field field = new Field(11,seconds);
        int limit = Integer.parseInt(JOptionPane.showInputDialog(null, "Limit of seats for the stadium ", 
                                    "Concurrent Programming", 1));
        for(int i = 1;i<=30;i++){
            Player p = new Player(field,1,i);
            Player p2 = new Player(field,2,i);
            Thread t = new Thread(p);
            t.start();
            t = new Thread(p2);
            t.start();
        }
        //The limit of clients allowed to be in the stadium is passed through the constructor.
        Stadium s = new Stadium(limit,field);
        //Makes the clients go the stadium
        PushClients pc = new PushClients(s);
        Thread t3 = new Thread(field);
        t3.start();
        //t = new Thread(pc);
        //t.start();
        
        Thread t2 = new Thread(s);
        
        t2.start();
        setSize(1000, 500);
        //setLayout(new GridLayout(0, 2, 15, 10));
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        clientPanel = new Panel("Cliente");
        clientPanel.canvas.setStadium(field);
        field.addTextModifier(new TeamText(){

            @Override
            public void modifyTextField(int index, String text, int team) {
                    clientPanel.setTeamsText(team,index, text);
            }
        });
        s.addTextModifier(new TextModifiers(){

            @Override
            public void modifyTextField(int index, String text) {
                clientPanel.setText(index, text);
            }
        });

        add(clientPanel);
        setLocationRelativeTo(null);
        spectatorsFrame = new SpectatosFrame(this.getLocation().x,this.getLocation().y, s);
        Thread t4 = new Thread(spectatorsFrame);
        t4.start();
        f = new JFrame();
        f.setSize(500,150);
        f.setLocation(this.getLocation().x+20, 1);
        f.add(spectatorsFrame);
        f.setVisible(true);
        //spectatorsFrame.setVisible(true);
        setVisible(true);
    }   
    

}
