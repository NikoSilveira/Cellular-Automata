
package Automata;

public class Celda {
    
    private Individuo[][] matrizInterna;      //Matriz dentro de cada casilla del tablero
    private boolean llenoA, llenoB, llenoX;
    private int cantidadA, cantidadB, cantidadX;
    
    //Constructor
    public Celda(){
        matrizInterna = new Individuo[3][4];  //Tamaño de matriz interna: 3x4
        
        llenoA = false;
        llenoB = false;
        llenoX = false;
        
        cantidadA = 0;
        cantidadB = 0;
        cantidadX = 0;
    }
    
    
    //Funcion para cambiar el contenido de una casilla de matrizInterna
    public void setMatrizInterna(Individuo individuo){
        for(int j=0; j<3; j++){
            if(individuo instanceof Depredador){
                if(((Depredador) individuo).getTipo()=="A"){
                    if(!(matrizInterna[0][j] instanceof Depredador)){
                       matrizInterna[0][j] = individuo;
                       cantidadA++;
                       break;
                    }
                }else if(((Depredador) individuo).getTipo()=="B"){
                    if(!(matrizInterna[1][j] instanceof Depredador)){
                        matrizInterna[1][j] = individuo;
                        cantidadB++;
                        break;
                    }
                }
            }else if(individuo instanceof Presa){
                if(!(matrizInterna[2][j] instanceof Presa)){
                    matrizInterna[2][j] = individuo;
                    cantidadX++;
                    break;
                }
            }
        }
        
        //Revisar si quedo llena una fila de seres vivos
        if(matrizInterna[0][3] instanceof Depredador){
            llenoA = true;
        }else{
            llenoA = false;
        }
        
        if(matrizInterna[1][3] instanceof Depredador){
            llenoB = true;
        }else{
            llenoB = false;
        }
        
        if(matrizInterna[2][3] instanceof Presa){
            llenoX = true;
        }else{
            llenoX = false;
        }
    }
    
    //Funciones de eliminacion de individuos
    
    public void eliminarPresa(){
        if(cantidadX == 1){
            matrizInterna[2][0] = null;
        }
        else if(cantidadX == 2){
            matrizInterna[2][1] = null;
        }
        else if(cantidadX == 3){
            matrizInterna[2][2] = null;
        }
        else if(cantidadX == 4){
            matrizInterna[2][3] = null;
        }
        llenoX = false;
    }
    
    public void eliminarDepredadorB(){
        if(cantidadB == 1){
            matrizInterna[1][0] = null;
        }
        else if(cantidadB == 2){
            matrizInterna[1][1] = null;
        }
        else if(cantidadB == 3){
            matrizInterna[1][2] = null;
        }
        else if(cantidadB == 4){
            matrizInterna[1][3] = null;
        }
        llenoB = false;
    }
    
    public void eliminarDepredadorA(){
        if(cantidadA == 1){
            matrizInterna[0][0] = null;
        }
        else if(cantidadA == 2){
            matrizInterna[0][1] = null;
        }
        else if(cantidadA == 3){
            matrizInterna[0][2] = null;
        }
        else if(cantidadA == 4){
            matrizInterna[0][3] = null;
        }
        llenoA = false;
    }

    //Getters y setters de los booleanos
    public boolean isLlenoA() {
        return llenoA;
    }

    public void setLlenoA(boolean llenoA) {
        this.llenoA = llenoA;
    }

    public boolean isLlenoB() {
        return llenoB;
    }

    public void setLlenoB(boolean llenoB) {
        this.llenoB = llenoB;
    }

    public boolean isLlenoX() {
        return llenoX;
    }

    public void setLlenoX(boolean llenoX) {
        this.llenoX = llenoX;
    }
    
    //Getters y setters de contadores

    public int getCantidadA() {
        return cantidadA;
    }

    public void setCantidadA(int cantidadA) {
        this.cantidadA = cantidadA;
    }

    public int getCantidadB() {
        return cantidadB;
    }

    public void setCantidadB(int cantidadB) {
        this.cantidadB = cantidadB;
    }

    public int getCantidadX() {
        return cantidadX;
    }

    public void setCantidadX(int cantidadX) {
        this.cantidadX = cantidadX;
    }
    
}
