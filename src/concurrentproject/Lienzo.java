/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package concurrentproject;

import java.awt.Canvas;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import javax.swing.ImageIcon;

/**
 *
 * @author urielhernandez
 */
public class Lienzo extends Canvas implements Runnable{
    private Field field;
    private Image background;
    private String background_path = "56254-football-field-drawing_1920x1080.jpg";
    private Graphics bufferGraphics;
    private BufferedImage bufferImg;
    private boolean first_time = true;
    private boolean is_playing = true;
    private int width = 800;
    private int height = 400;
    public Lienzo(){
        
        this.background = new ImageIcon(getClass().getClassLoader().getResource("56254-football-field-drawing_1920x1080.jpg")).getImage();
        this.bufferImg = new BufferedImage(this.getWidth(), this.getHeight(), BufferedImage.TYPE_INT_RGB);
        this.bufferGraphics = this.bufferImg.createGraphics();
        this.bufferGraphics.drawImage(this.background, -100, 0, this.getWidth(),this.getHeight(), this);
       
        
        
        
        this.bufferGraphics.dispose();
    }
    @Override
    public void run() {
        while(is_playing){
            try{
                Thread.sleep(1000 / 60);
                repaint();
            }
            catch (Exception e){
                System.out.println(e);
            }
        }
    }
    @Override
    public void update(Graphics g){
        if(first_time){
            Dimension dim = getSize();
            int w = dim.width;
            int h = dim.height;
            this.bufferImg = (BufferedImage)createImage(w, h);
            this.bufferGraphics = this.bufferImg.createGraphics();
            first_time = false;
        }
        bufferGraphics.drawImage(this.background, 100, 0, this.getWidth(),this.getHeight(), this);
        int i = 0;
        
        for(Object p : this.field.team_one.getPlayersPlaying()){
            i++;
            Player my_player = (Player) p;
            bufferGraphics.drawImage(my_player.background, 200, 20 * i + 60, 20, 30, this);
        }
        i = 0;
        for(Object p : this.field.team_two.getPlayersPlaying()){
            i++;
            Player my_player = (Player) p;
            bufferGraphics.drawImage(my_player.background, 600, (20 * i) + 60, 20, 30, this);
        }
        i = 0;
        for(Object p : this.field.team_two.getPlayersInBench()){
            i++;
            Player my_player = (Player) p;
            bufferGraphics.drawImage(my_player.background,(20 * i) + 60, 300 , 20, 30, this);
        }
        g.drawImage(this.bufferImg,0,0,this);
    }
    @Override
    public void paint(Graphics g){
        update(g);
    }
    void setStadium(Field field) {
        this.field = field;
        Thread t = new Thread(this);
        t.start();
    }
    @Override
    public int getWidth(){
        return this.width;
    }
    @Override
    public int getHeight(){
        return this.height;
    }
}
