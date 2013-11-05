/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrentproject;

import java.util.ArrayList;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Uriel
 */
public class Stadium implements Runnable{
    int limite;
    Field field;
    ArrayList<Client> clients;
    private final Lock taquilla = new ReentrantLock();
    private final Condition is_not_full = taquilla.newCondition();
    public Stadium(int limite,Field f){
        this.limite = limite;
        this.field = f;
        clients = new ArrayList<>();
    }
    public void addClient(Client c) throws InterruptedException{
        taquilla.lock();
        try{
            while(clients.size() == limite){
                is_not_full.await();
            }
            clients.add(c);
            c.state= 1;
        }finally{
            taquilla.unlock();
        }
    }
    public void removeClient(Client c) throws InterruptedException{
        taquilla.lock();
        try{
            if(clients.size() > 0){
                clients.remove(c);
                c.state = 3;
                is_not_full.signal();
            }
        }finally{
            taquilla.unlock();
        }
    }

    @Override
    public void run() {
        while(clients.size() > 0){
            try {
                Thread.sleep(1000/60);
                //System.out.println("Hay "+clients.size()+" en el estadio");
            } catch (InterruptedException ex) {
                Logger.getLogger(Stadium.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
         
    }
}
