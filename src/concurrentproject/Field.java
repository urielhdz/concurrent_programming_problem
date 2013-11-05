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
public class Field implements Runnable{
    int limite;
    ArrayList<Player> team_one;
    ArrayList<Player> team_two;
    private final Lock referee = new ReentrantLock();
    public boolean is_playing = true;
    private final Condition team_one_is_full = referee.newCondition();
    private final Condition team_two_is_full = referee.newCondition();
    private final Condition team_one_is_not_ready = referee.newCondition();
    private final Condition team_two_is_not_ready = referee.newCondition();
    public Field(int limite){
        this.limite = limite;
        team_one = new ArrayList<>();
        team_two = new ArrayList<>();
    }
    public void addPlayerTeamOne(Player p) throws InterruptedException{
        referee.lock();
        try{
            while(team_one.size() == limite){
                System.out.println("Soy el jugador "+p.number+" y estoy esperando para entrar");
                team_one_is_full.await();
            }
            team_one.add(p);
            p.state = 1;
            //System.out.println("Soy el jugador "+p.number+" del equipo uno y entre a jugar");
            //team_one_is_not_ready.signal();
        }finally{
            referee.unlock();
        }
    }
    public void addPlayerTeamTwo(Player p) throws InterruptedException{
        referee.lock();
        try{
            while(team_two.size() == limite){
                team_two_is_full.await();
            }
            team_two.add(p);
            p.state = 1;
            //System.out.println("Soy el jugador "+p.number+" del equipo dos y entre a jugar");
            //team_two_is_not_ready.signal();
        }finally{
            referee.unlock();
        }
    }
    public void playerAbandonTeamOne(Player p) throws InterruptedException{
        referee.lock();
        try{
            if(team_one.size() > 0){
                team_one.remove(p);
                p.state = 3;
                System.out.println("Soy el jugador "+p.number+" del equipo uno y me voy del partido");
                team_one_is_full.signal();
                if(team_one.size()< 1) is_playing = false;
            }
            else{
                is_playing = false;
            }
            /*while(team_one.size() < limite){
                //team_one_is_not_ready.await();
            }*/
            team_one_is_full.signal();
        }finally{
            referee.unlock();
        }
    }
    public void playerAbandonTeamTwo(Player p) throws InterruptedException{
        referee.lock();
        try{
            if(team_two.size() > 0){
                team_two.remove(p);
                p.state = 3;
                System.out.println("Soy el jugador "+p.number+" del equipo dos y me voy del partido");
                team_two_is_full.signal();
                if(team_two.size()< 1) is_playing = false;
            }
            team_two_is_full.signal();
        }finally{
            referee.unlock();
        }
    }

    @Override
    public void run() {
        while(is_playing){
            try {
                Thread.sleep(500);
            } catch (InterruptedException ex) {
                Logger.getLogger(Field.class.getName()).log(Level.SEVERE, null, ex);
            }
            System.out.println("Hay "+team_one.size()+" jugadores en el equipo uno");
            System.out.println("Hay "+team_two.size()+" jugadores en el equipo dos");
        }
    }
}
