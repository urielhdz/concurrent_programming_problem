/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrentproject;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Uriel
 */
public class PushClients implements Runnable{
    
    Stadium stadium;
    int id_contador;
    public PushClients(Stadium s){
        id_contador = 0;
        this.stadium = s;
        for(int i = 0;i<10;i++){
            id_contador++;
            Client c = new Client(stadium,id_contador);
            Thread t = new Thread(c);
            t.start();
        }
    }

    @Override
    public void run() {
        while(stadium.field.is_playing){
            try {
                Thread.sleep(1000);
                id_contador++;
                Client c = new Client(stadium,id_contador);
                Thread t = new Thread(c);
                t.start();
            } catch (InterruptedException ex) {
                Logger.getLogger(PushClients.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
    }
}
