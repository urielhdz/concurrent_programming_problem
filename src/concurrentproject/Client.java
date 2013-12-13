/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrentproject;

import java.awt.Image;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;

/**
 *
 * @author Uriel
 */
public class Client implements Runnable{
    /*
     * Different states
     * 0 = waiting to go to the stadium
     * 1 = waiting for a ticket
     * 2 = in a seat
     * 3 = looking for a seat
     * 4 = leaving the stadium
     * 5 = Paying a ticket
     * 6 = out of the stadium
     */
    int state;
    int fun;
    int counter = 0;
    int id;
    private Image clientImage;
    private Random rand;
    boolean is_going_to_pay = true;
    Stadium stadium;
    public Client(Stadium s, int id){
        this.state = 0;
        this.stadium = s;
        this.id = id;
        this.fun = getRandomInteger(200, 350, new Random());
        rand = new Random();
        int x = rand.nextInt(10) + 1;
        clientImage = new ImageIcon(this.getClass().getResource("imgs/espc"+x+".png")).getImage();

    }
    public Image getClientImage(){
        return this.clientImage;
    }
    @Override
    public void run() {
        try {
            stadium.addClient(this);
        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(this.state < 6){
            try {
                Thread.sleep(1000/60);
                if(this.state == 2){
                    fun--;
                }
                if(this.fun < 0){
                    this.counter = 51;
                    this.state = 4;
                    //System.out.println("Cliente abandonando el estadio");
                }
                if(this.state == 4){
                    counter = 100;
                }
                if(counter > 50){
                    try {
                        stadium.removeClient(this);
                    } catch (InterruptedException ex) {
                        Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
    private int getRandomInteger(int aStart, int aEnd, Random aRandom){
    if (aStart > aEnd) {
      throw new IllegalArgumentException("Start cannot exceed End.");
    }
    //get the range, casting to long to avoid overflow problems
    long range = (long)aEnd - (long)aStart + 1;
    // compute a fraction of the range, 0 <= frac < range
    long fraction = (long)(range * aRandom.nextDouble());
    int randomNumber =  (int)(fraction + aStart);    
    return randomNumber;
  }

    void pay() throws InterruptedException {
        if(is_going_to_pay){
            is_going_to_pay = false;
            Thread.sleep(50);
        }
    }
}
