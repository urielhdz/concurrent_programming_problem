package concurrentproject;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;

public class SpectatosFrame extends Canvas implements Runnable{
    
    private Canvas can;
    private Image background;
    private Graphics bufferGraphics;
    private BufferedImage bufferImg;
    private Clients clients;
    private boolean firstTime = true;
    public ArrayList<Client> sea;
    int x = 27;
    int y = 20;
    int xl = 0; int yl=0;
    int seat;
    int look;
    int leave;
    
    public SpectatosFrame(int x, int y, Stadium s){
        can = new Canvas();
        sea = new ArrayList<Client>();
        //setUndecorated(true);
        //setLocationRelativeTo(this);
        
        try {
            //this.background = new ImageIcon(this.getClass().getClassLoader().getResource("/imgs/Gradas.png")).getImage();
            this.background = ImageIO.read(new File("imgs/Gradas.png"));
        } catch (IOException ex) {
            Logger.getLogger(SpectatosFrame.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.bufferImg = new BufferedImage(500,150,BufferedImage.TYPE_INT_RGB);
        this.bufferGraphics = this.bufferImg.createGraphics();
        this.bufferGraphics.drawImage(this.background, 0, 0, 500,150, can);
        this.bufferGraphics.dispose();
        //this.arco = new Arco(570/2, 500,570);
        s.doSomeActions(new ClientActions(){
            public void newClients(Clients c) {
                clients = c;
            }
            public void seating(int seating) {
                seat = seating;
            }
            public void payingTicket(int paying) {
                
            }
            public void lookingSeat(int looking) {
                look = looking;
            }
            public void leaving(int leaving) {
                leave = leaving;
            }
            public void waitingTicket(int waiting) {
                
            }
        });
        //add(can);
    }
    
    public void paint(Graphics g){
        update(g);
    }
    
    public void update(Graphics g){
        if(firstTime){
            Dimension dim = getSize();
            int w = dim.width;
            int h = dim.height;
            this.bufferImg = (BufferedImage)createImage(w, h);
            this.bufferGraphics = this.bufferImg.createGraphics();
            firstTime = false;
        }
        
       
        //if(clients==null){
        if(look != 0){
                ArrayList<Client> looc = clients.getLookingClients();
                for(Client c : looc){
                    bufferGraphics.drawImage(c.getClientImage(), xl+=15, yl, 10, 15, this);
                    if(xl > 30){
                        xl = 0;
                        yl +=15;
                    }
                }
                xl=0;
                yl=0;
            }else{
            bufferGraphics.clearRect(0, 0, 100, 150);
        }
            if(seat != 0){
                bufferGraphics.drawImage(this.background, 0, 0, this.getSize().width,this.getSize().height, null);
                ArrayList<Client> sit = clients.getSittingClients();
                
                for(Client c : sit){
                    
                    bufferGraphics.drawImage(c.getClientImage(), x+=16, y, 10, 15, this);
                    if(x > 420){
                        x = 27;
                        y += 15;
                    }
                }
                 x=27; y = 25;
            } else{
                bufferGraphics.drawImage(this.background, 0, 0, this.getSize().width,this.getSize().height, null);
            }
            
       // }
        //bufferGraphics.drawImage(this.arco.img,this.arco.getX(),this.arco.getY(),this);
        g.drawImage(this.bufferImg,0,0,this);
        repaint();
    }

    @Override
    public void run() {
       
            try {
                Thread.sleep(1000 / 60);
                repaint();
            } catch (InterruptedException ex) {
                Logger.getLogger(SpectatosFrame.class.getName()).log(Level.SEVERE, null, ex);
            }
        
    }
}
