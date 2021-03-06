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
public class Stadium implements Runnable {

    int limite;
    int limite_looking;
    private TextModifiers textMod;
    private ClientActions actions;
    Field field;
    Clients clients;
    //ArrayList<Client> clients_waiting;
    private final Lock taquilla = new ReentrantLock();
    private final Condition is_not_full = taquilla.newCondition();
    private final Condition no_longer_looking = taquilla.newCondition();
    private final Condition ticket_bussy = taquilla.newCondition();

    public Stadium(int limite, Field f) {
        this.limite = limite;
        this.limite_looking = 30;
        this.field = f;
        clients = new Clients();
    }

    public void addTextModifier(TextModifiers textMod) {
        this.textMod = textMod;
    }
    public void doSomeActions(ClientActions act){
        this.actions = act;
    }
    public void addClient(Client c) throws InterruptedException {
        clients.add(c);
        this.clients.search_client(c).state = 1;
        taquilla.lock();
        try {

            while (clients.paying_ticket() > 0) {
                ticket_bussy.await();
            }
            c.state = 5;
            pay_ticket(c);

        } finally {
            taquilla.unlock();
        }
    }

    private void pay_ticket(Client c) throws InterruptedException {
        taquilla.lock();
        try {
            while (clients.waiting_ticket() == limite_looking) {
                no_longer_looking.await();
            }
            c.pay();
            c.state = 3;
            ticket_bussy.signal();
            sit(c);

        } finally {
            taquilla.unlock();
        }

    }

    public void sit(Client c) throws InterruptedException {
        taquilla.lock();
        try {
            
            while (clients.seating() == limite) {

                is_not_full.await();
            }
            c.state = 2;
            no_longer_looking.signal();
        } finally {
            taquilla.unlock();
        }

    }

    public void removeClient(Client c) throws InterruptedException {
        taquilla.lock();
        try {
            if (clients.size() > 0) {
                c.state = 6;
                clients.remove(c);
                
                is_not_full.signal();
            }
        } finally {
            taquilla.unlock();
        }
    }

    @Override
    public void run() {
        while (clients.size() > 0) {
            try {
                if (!field.is_playing) {
                    clients.goAway();
                }
                Thread.sleep(1000);
                //System.out.println("Clients: \n In seat: " + clients.seating() + " \n Paying: " + clients.paying_ticket() + " \n Looking for a seat: " + clients.looking_seat() + "\n Leaving: " + clients.leaving() + "\n Waiting ticket: " + clients.waiting_ticket() + "\n Total clients: " + clients.size());
                //Clients: \n In seat: "+clients.seating()+" \n 
                //Paying: "+clients.paying_ticket()+" \n
                // Looking for a seat: "+clients.looking_seat()+"\n 
                //Leaving: "+clients.leaving()+"\n 
                //Waiting ticket: "+clients.waiting_ticket()+"\n 
                //Total clients: "+clients.size());
                this.actions.newClients(clients);
                this.textMod.modifyTextField(0, clients.seating()+"");
                this.actions.seating(clients.seating());
                this.textMod.modifyTextField(1, clients.paying_ticket()+"");
                this.actions.payingTicket(clients.paying_ticket());
                this.textMod.modifyTextField(2, clients.looking_seat()+"");
                this.actions.lookingSeat(clients.looking_seat());
                this.textMod.modifyTextField(3, clients.leaving()+"");
                this.actions.leaving(clients.leaving());
                this.textMod.modifyTextField(4, clients.waiting_ticket()+"");
                this.actions.waitingTicket(clients.waiting_ticket());
                this.textMod.modifyTextField(5, clients.size()+"");
                
                
                //this.textMod.modifyClientPanel("\nClients: \n In seat: "+clients.seating()+" \n Paying: "+clients.paying_ticket()+" \n Looking for a seat: "+clients.looking_seat()+"\n Leaving: "+clients.leaving()+"\n Waiting ticket: "+clients.waiting_ticket()+"\n Total clients: "+clients.size());
            } catch (InterruptedException ex) {
                Logger.getLogger(Stadium.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }
}
