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
    int limite_warming_up = 11;
    Team team_one;
    //Team team_one_warming_up;
    //ArrayList<Player> team_one_bench;
    Team team_two;
    private final Lock referee = new ReentrantLock();
    public boolean is_playing = true;
    private TeamText textMod;
    private int seconds;
    private int counter_seconds = 0;
    private final Condition team_one_is_full = referee.newCondition();
    private final Condition team_two_is_full = referee.newCondition();
    private final Condition team_one_warming = referee.newCondition();
    private final Condition team_two_warming = referee.newCondition();
    public Field(int limite,int time){
        this.limite = limite;
        this.seconds = time;
        team_one = new Team();
        team_two = new Team();
    }
    public void addTextModifier(TeamText textMod){
        this.textMod = textMod;
    }
    public void warm_up_team_one(Player p) throws InterruptedException{
        referee.lock();
        
        try{
            while(team_one.warming_up() == limite_warming_up){
             //   System.out.println("Soy el jugador "+p.number+" y estoy esperando para calentar");
                team_one_warming.await();
                team_one.search_player(p).state = 2;
            }
            team_one.search_player(p).state = 3;
          //  System.out.println("Soy el jugador "+p.number+" y voy a calentar");
        }finally{
            referee.unlock();
        }
    }
    public void warm_up_team_two(Player p) throws InterruptedException{
        referee.lock();
        
        try{
            while(team_two.warming_up() == limite_warming_up){
             //   System.out.println("Soy el jugador "+p.number+" y estoy esperando para calentar");
                team_two_warming.await();
                team_two.search_player(p).state = 2;
            }
            team_two.search_player(p).state = 3;
          //  System.out.println("Soy el jugador "+p.number+" y voy a calentar");
        }finally{
            referee.unlock();
        }
    }
    public void addPlayerTeamOne(Player p) throws InterruptedException{
        team_one.add(p);
        referee.lock();
        
        try{
            while(team_one.warming_up() == limite_warming_up){
             //   System.out.println("Soy el jugador "+p.number+" y estoy esperando para calentar");
                team_one_warming.await();
                team_one.search_player(p).state = 2;
            }
            team_one.search_player(p).state = 3;
          //  System.out.println("Soy el jugador "+p.number+" y voy a calentar");
        }finally{
            referee.unlock();
        }
    }

    public void addPlayerTeamTwo(Player p) throws InterruptedException{
        team_two.add(p);
        referee.lock();
        
        try{
            while(team_two.warming_up() == limite_warming_up){
               // System.out.println("Soy el jugador "+p.number+" del equipo 2 y estoy esperando para calentar");
                team_two.search_player(p).state = 2;
                team_two_warming.await();
            }
            team_two.search_player(p).state = 3;
         //   System.out.println("Soy el jugador "+p.number+" y voy a calentar");
        }finally{
            referee.unlock();
        }
    }
    public void playerAbandonTeamOne(Player p) throws InterruptedException{
        referee.lock();
        try{
            if(team_one.size() > 0){
                team_one.remove(p);
                p.state = 6;
             //   System.out.println("Soy el jugador "+p.number+" del equipo dos y me voy del partido");
                team_one_is_full.signal();
                if(team_one.size()< 1) is_playing = false;
            }

        }finally{
            referee.unlock();
        }
    }
    public void playerAbandonTeamTwo(Player p) throws InterruptedException{
        referee.lock();
        try{
            if(team_two.size() > 0){
                team_two.remove(p);
                p.state = 6;
             //   System.out.println("Soy el jugador "+p.number+" del equipo dos y me voy del partido");
                if(team_two.size()< 1) is_playing = false;
            }
            team_two_is_full.signal();
        }finally{
            referee.unlock();
        }
    }

    public void goToField(Player p,int team) throws InterruptedException {
        referee.lock();
        
        try{
            if(team == 1){
                while(team_one.playing()== limite){
                 //   System.out.println("Soy el jugador "+p.number+" y estoy esperando para entrar");
                    team_one_is_full.await();
                }
                team_one.search_player(p).state = 1;
                team_one_warming.signal();
               // System.out.println("Soy el jugador "+p.number+" y estoy entrando a jugar");
            }
            else if(team == 2){
                while(team_two.playing()== limite){
                    //System.out.println("Soy el jugador "+p.number+" y estoy esperando para entrar");
                    team_two_is_full.await();
                }
                team_two.search_player(p).state = 1;
                team_two_warming.signal();
                //System.out.println("Soy el jugador "+p.number+" y estoy entrando a jugar");
            }
        }finally{
            referee.unlock();
        }
    }
    public void injured(Player p, int team) throws InterruptedException{
        referee.lock();
        try{
            if(team == 1){
                
                if(p != null)
                    team_one.search_player(p).state = 4;
                team_one_is_full.signal();
            }
            else if(team == 2){
                if(p != null)
                    team_two.search_player(p).state = 4;
                team_two_is_full.signal();
            }
            //System.out.println("Soy el jugador "+p.number+" y me acabo de lesionar");
        }finally{
            referee.unlock();
        }
    }
    
    @Override
    public void run() {
        while(is_playing){
            try {
                Thread.sleep(1000);
                counter_seconds++;
                if(counter_seconds >= seconds){
                    this.team_one.go_out();
                    this.team_two.go_out();
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(Field.class.getName()).log(Level.SEVERE, null, ex);
            }
            //this.textMod.modifyFieldPanel("\nTeam 1: \n Playing: "+team_one.playing()+" \n Warming up: "+team_one.warming_up()+"\n Injured: "+team_one.injured()+"\n In the bench: "+team_one.in_bench()+"\n Leaving the field: "+team_one.leaving());
            //this.textMod.modifyFieldPanel("\nTeam 2: \n Playing: "+team_two.playing()+" \n Warming up: "+team_two.warming_up()+"\n Injured: "+team_two.injured()+"\n In the bench: "+team_two.in_bench()+"\n Leaving the field: "+team_two.leaving());
            //"Team 1: \n Playing: "+team_one.playing()+
            //" Warming up: "+team_one.warming_up()+"
            //Injured: "+team_one.injured()+"
            //In the bench: "+team_one.in_bench()+"
            //Leaving the field: "+team_one.leaving()
            this.textMod.modifyTextField(0, team_one.playing()+"", 1);
            this.textMod.modifyTextField(1, team_one.warming_up()+"", 1);
            this.textMod.modifyTextField(2, team_one.injured()+"", 1);
            this.textMod.modifyTextField(3, team_one.in_bench()+"", 1);
            this.textMod.modifyTextField(4, team_one.leaving()+"", 1);
            
            this.textMod.modifyTextField(0, team_two.playing()+"", 2);
            this.textMod.modifyTextField(1, team_two.warming_up()+"", 2);
            this.textMod.modifyTextField(2, team_two.injured()+"", 2);
            this.textMod.modifyTextField(3, team_two.in_bench()+"", 2);
            this.textMod.modifyTextField(4, team_two.leaving()+"", 2);
//            System.out.println("Team 1: \n Playing: "+team_one.playing()+" \n Warming up: "+team_one.warming_up()+"\n Injured: "+team_one.injured()+"\n In the bench: "+team_one.in_bench()+"\n Leaving the field: "+team_one.leaving());
  //          System.out.println("Team 1: \n Playing: "+team_two.playing()+" \n Warming up: "+team_two.warming_up()+"\n Injured: "+team_two.injured()+"\n In the bench: "+team_two.in_bench()+"\n Leaving the field: "+team_two.leaving());
        }
    }

}
