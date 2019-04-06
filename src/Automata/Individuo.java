
package Automata;

public abstract class Individuo {
    //Nombre del individuo
    private String nombre;

    //Constructor
    public Individuo(String nombre) {
        this.nombre = nombre;
    }
    
    public Individuo(){
        
    }

    //Getters & Setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
