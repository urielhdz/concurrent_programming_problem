/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrentproject;

import java.util.ArrayList;

/**
 *
 * @author Uriel
 */
public class Clients {
    private ArrayList<Client> clients;
    public Clients(){
        clients = new ArrayList<>();
    }
    public void add(Client c){
        this.clients.add(c);
    }
    public void remove(Client c){
        this.clients.remove(c);
    }
    public int looking_seat(){
        int contador = 0;
        for(Client c : clients){
            if(c != null)
                if(c.state == 3) contador++;
        }
        return contador;
    }
    public void goAway(){
        for(Client c : clients){
            if(c != null)
                c.state = 4;
        }
    }
    public int seating(){
        
        int contador = 0;
        for(Client c : clients){
            if(c != null)
                if(c.state == 2) contador++;
        }
        return contador;
    }
    public int waiting_ticket(){
        int contador = 0;
        for(Client c : clients){
            if(c != null)
                if(c.state == 1) contador++;
        }
        return contador;
    }
     public int leaving(){
        int contador = 0;
        for(Client c : clients){
            if(c != null)
                if(c.state == 4) contador++;
        }
        return contador;
    }
     public int paying_ticket(){
        int contador = 0;
        for(Client c : clients){
            if(c != null)
                if(c.state == 5) contador++;
        }
        return contador;
    }
     public int size(){
         return this.clients.size();
     }
     public Client search_client(Client pl){
         for(Client c : clients){
             
             if(c == pl) return c;
         }
         return null;
     }
}
