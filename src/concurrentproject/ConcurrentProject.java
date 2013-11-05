/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrentproject;

/**
 *
 * @author Uriel
 */
public class ConcurrentProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        // TODO code application logic here
        Field field = new Field(11);
        for(int i = 1;i<=30;i++){
            Player p = new Player(field,1,i);
            Player p2 = new Player(field,2,i);
            Thread t = new Thread(p);
            t.start();
            t = new Thread(p2);
            t.start();
        }
        Thread t = new Thread(field);
        t.start();
        //The limit of clients allowed to be in the stadium is passed through the constructor.
        Stadium s = new Stadium(30,field);
        //Makes the clients go the stadium
        PushClients pc = new PushClients(s);
        t = new Thread(pc);
        t.start();
        t = new Thread(s);
        t.start();
    }
}
