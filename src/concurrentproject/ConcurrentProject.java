/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package concurrentproject;

import javax.swing.SwingUtilities;

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
        SwingUtilities.invokeLater(new Runnable(){
            public void run(){
                new MainWindow();
            }
        });
    }
}
