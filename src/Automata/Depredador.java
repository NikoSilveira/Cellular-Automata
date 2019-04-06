
package Automata;

public class Depredador extends Individuo {
    private String tipo;

    //Constructor
    public Depredador(String tipo) {
        this.tipo = tipo;
    }

    //Getters & Setters
    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }
}