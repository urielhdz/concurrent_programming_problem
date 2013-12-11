/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrentproject;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 *
 * @author Uriel
 */
public class Team {
    private CopyOnWriteArrayList<Player> players;
    public Team(){
        players = new CopyOnWriteArrayList<>();
    }
    public void add(Player p){
        this.players.add(p);
    }
    public void remove(Player p){
        this.players.remove(p);
    }
    public int warming_up(){
        int contador = 0;
        for(Player p : players){
            if(p.state == 3) contador++;
        }
        return contador;
    }
    public int leaving(){
        int contador = 0;
        for(Player p : players){
            if(p.state == 5) contador++;
        }
        return contador;
    }
    public int in_bench(){
        int contador = 0;
        for(Player p : players){
            if(p.state == 2) contador++;
        }
        return contador;
    }
    public int playing(){
        int contador = 0;
        for(Player p : players){
            if(p.state == 1) contador++;
        }
        return contador;
    }
     public int injured(){
        int contador = 0;
        for(Player p : players){
            if(p.state == 4) contador++;
        }
        return contador;
    }
     public int size(){
         return this.players.size();
     }
     public void go_out(){
         for(Player p : players){
             p.state = 5;
         }
     }
     public Player search_player(Player pl){
         for(Player p : players){
             if(p.number == pl.number) return p;
         }
         return null;
     }
     public CopyOnWriteArrayList getPlayers(){
         return this.players;
     }
     public CopyOnWriteArrayList getPlayersPlaying(){
        CopyOnWriteArrayList my_players = new CopyOnWriteArrayList();
        for(Player p : players){
            if(p.state == 1)
                my_players.add(p);
        }
        return my_players;
     }
     public CopyOnWriteArrayList getPlayersInBench(){
        CopyOnWriteArrayList my_players = new CopyOnWriteArrayList();
        for(Player p : players){
            if(p.state == 2)
                my_players.add(p);
        }
        return my_players;
     }
}
