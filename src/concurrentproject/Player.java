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
public class Player implements Runnable{
    /*
     * Different states
     * 0 = initial state
     * 1 = playing in the field
     * 2 = in the bench
     * 3 = warming up
     * 4 = injured
     * 5 = leaving the field
     * 6 = out of the field
     * 
     */
    int state;
    int energy = 200;
    int counter = 0;
    int health = 1;
    int team;
    int nivel_preparacion = 0;
    int number = 0;
    Field field;
    public Player(Field f,int team,int number){
        this.team = team;
        this.number = number;
        this.field = f;
        this.state = 0;
        this.energy = getRandomInteger(100, 150, new Random());
        this.health = getRandomInteger(0, 1, new Random());
        
    }
    @Override
    public void run() {
        if(this.team == 1){
            try {
                field.addPlayerTeamOne(this);
            } catch (InterruptedException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        else if(this.team == 2){
            try {
                field.addPlayerTeamTwo(this);
            } catch (InterruptedException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        while(state < 6){
            try {
                Thread.sleep(1000/60);
                if(this.state == 3){
                    nivel_preparacion++;
                    if(nivel_preparacion > 100){
                        field.goToField(this,this.team);
                    }
                }
                if(this.state == 1){
                    energy--;
                    if(energy < 0 ){
                        this.state = 5;
                        counter = 50;
                    }
                }
              
                if(this.state == 5) counter ++;
                if(counter > 50){
                     if(this.team == 1){
                        try {
                            field.playerAbandonTeamOne(this);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                    else if(this.team == 2){
                        try {
                            field.playerAbandonTeamTwo(this);
                        } catch (InterruptedException ex) {
                            Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    }
                }
                
                if(getRandomInteger(-health, 100, new Random())< 0){
                    this.counter = 50;
                    field.injured(this,this.team);
                }
                if(this.state == 4 ){
                    counter ++;
                    if(this.counter > 50)
                        this.state = 5;
                }
                
            } catch (InterruptedException ex) {
                Logger.getLogger(Player.class.getName()).log(Level.SEVERE, null, ex);
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
