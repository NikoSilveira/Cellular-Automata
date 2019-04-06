
package Automata;

import java.util.logging.Level;
import java.util.logging.Logger;

public class Movimiento extends Thread{
    
    private final Tablero tablero;
    
    public Movimiento(Tablero tablero){
        this.tablero = tablero;
    }
    
    public void run(){
        while (true) {
            try {
                tablero.algoritmos();  //Llamar los algoritmos del programa cada iteracion
            } catch (InterruptedException ex) {
                Logger.getLogger(Movimiento.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            tablero.repaint();        //Hacer repaint cada iteracion
            
            try {
                Thread.sleep(500);    //Pausa a las iteraciones del programa
            } catch (Exception ex) {
                System.out.println("error in graphics engine: " + ex.getMessage());
            }
        }
    }
}
