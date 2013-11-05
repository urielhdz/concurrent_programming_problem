/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrentproject;

import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Uriel
 */
public class Client implements Runnable{
    /*
     * Different states
     * 0 = waiting to go to the stadium
     * 1 = in the stadium
     * 2 = leaving the stadium
     * 3 = out of the stadium
     */
    int state;
    int fun;
    int counter = 0;
    Stadium stadium;
    public Client(Stadium s){
        this.state = 0;
        this.stadium = s;
        this.fun = getRandomInteger(200, 250, new Random());
    }
    @Override
    public void run() {
        try {
            stadium.addClient(this);
        } catch (InterruptedException ex) {
            Logger.getLogger(Client.class.getName()).log(Level.SEVERE, null, ex);
        }
        while(this.state < 3){
            try {
                Thread.sleep(1000/60);
                if(this.state == 1){
                    fun--;
                }
                if(this.fun < 0){
                    this.counter = 50;
                    this.state = 2;
                    System.out.println("Cliente abandonando el estadio");
                }
                if(this.state == 2){
                    counter++;
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
}
