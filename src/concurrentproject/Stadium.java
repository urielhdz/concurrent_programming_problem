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
    int limite_looking;
    int limite_tickets;
    Field field;
    Clients clients;
    //ArrayList<Client> clients_waiting;
    private final Lock taquilla = new ReentrantLock();
    private final Condition is_not_full = taquilla.newCondition();
    private final Condition there_are_tickets = taquilla.newCondition();
    private final Condition there_are_looking = taquilla.newCondition();
    public Stadium(int limite,Field f){
        this.limite = limite;
        this.limite_looking = 30;
        this.limite_tickets = 5;
        this.field = f;
        clients = new Clients();
    }
    public void addClient(Client c) throws InterruptedException{
        clients.add(c);
        
        taquilla.lock();
        try{
            while(clients.looking_seat() == limite_looking){
                this.clients.search_client(c).state = 1;
                there_are_tickets.await();
            }
            this.clients.search_client(c).state = 3;
            look_for_seat(c);
            
        }finally{
            taquilla.unlock();
        }
    }
    private void look_for_seat(Client c) throws InterruptedException {
        taquilla.lock();
        while(clients.seating()== limite){
            is_not_full.await();
        }
        this.clients.search_client(c).state = 2;
        there_are_tickets.signal();
    }
    public void removeClient(Client c) throws InterruptedException{
        taquilla.lock();
        try{
            if(clients.size() > 0){
                clients.remove(c);
                c.state = 5;
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
                Thread.sleep(1000);
                System.out.println("Clients: \n In seat: "+clients.seating()+" \n Looking for a seat: "+clients.looking_seat()+"\n Leaving: "+clients.leaving()+"\n Waiting ticket: "+clients.waiting_ticket());
            } catch (InterruptedException ex) {
                Logger.getLogger(Stadium.class.getName()).log(Level.SEVERE, null, ex);
            }
            
        }
         
    }

    
}
