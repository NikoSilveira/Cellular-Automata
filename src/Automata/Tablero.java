
package Automata;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.geom.RoundRectangle2D;
import java.util.Random;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;

public class Tablero extends JPanel{
    private Celda[][] celdas = new Celda[50][50];              //Matriz de 50x50
    private int contadorIteraciones, delay, iString, jString;
    private double indicadorVel;                               //Indicador de la velocidad de la simulacion
    private int cantXI = 0, cantBI=0, cantAI=0;
    private int cantXI2 = 0, cantBI2=0, cantAI2=0;
    
    JButton boton1, boton2, boton3, boton4, boton5, boton6;
    JLabel velocidad = new JLabel();
    JLabel contenido = new JLabel();
    JComboBox lista1, lista2;
    
    private ItemHandler handler3 = new ItemHandler();
    private ItemHandler handler4 = new ItemHandler();
    
    private Random random;
    
    //Constructor
    public Tablero(int numDepredadores, int numPresas){
        contadorIteraciones = 0;    //Contador de las iteraciones del programa
        indicadorVel = 1.5;         //Valor inicial de la velocidad
        delay = 1000;               //Valor inicial del tiempo de Sleep
        iString = 0;
        jString = 0;
        
        //Llenar el array con objetos tipo celda
        for(int i=0; i<50; i++){
            for(int j=0; j<50; j++){
                celdas[i][j] = new Celda();
            }
        }
        
        crearIndividuos(numDepredadores, numPresas);
        iniciarComponentes();
    }
    
    
    //Crear los individuos dentro de las casillas
    public void crearIndividuos(int numD, int numP){
        random = new Random();
        int i, j, distribuir;
        i=random.nextInt(50);
        j=random.nextInt(50);
        
        //Depredadores
        while(numD != 0){
            distribuir = random.nextInt(2);
            //Tipo A
            if(distribuir == 0){
                if(celdas[i][j].isLlenoA() == false){
                    celdas[i][j].setMatrizInterna(new Depredador("A"));
                    numD--;
                }
            }
            //Tipo B
            else if(distribuir == 1){
                if(celdas[i][j].isLlenoB() == false){
                    celdas[i][j].setMatrizInterna(new Depredador("B"));
                    numD--;
                }
            }
            i=random.nextInt(50);
            j=random.nextInt(50);
        }
        
        //Presas
        while(numP != 0){
            if(celdas[i][j].isLlenoX() == false){
                celdas[i][j].setMatrizInterna(new Presa());
                numP--;
            }
            i = random.nextInt(50);
            j = random.nextInt(50);
        }
    }
    
    public void Reglas(){
        for(int i=0; i<50; i++){
            for(int j=0; j<50; j++){
                cantXI = celdas[i][j].getCantidadX();
                cantBI = celdas[i][j].getCantidadB();
                cantAI = celdas[i][j].getCantidadA();
                if(cantAI > 0 && cantXI == 0){
                    while(celdas[i][j].getCantidadA() > 0 ){
                       celdas[i][j].eliminarDepredadorA();
                       celdas[i][j].setCantidadA(celdas[i][j].getCantidadA() - 1);
                    }
                }
                if(cantAI > 0 && cantXI >= 2 && celdas[i][j].getCantidadB() < 4){
                    cantAI2 = cantAI;
                    cantXI2 = cantXI;
                    while(celdas[i][j].getCantidadB() < 4 && cantXI2 > 1 && cantAI2 > 0 ){
                        celdas[i][j].eliminarDepredadorA(); 
                        celdas[i][j].setCantidadA(celdas[i][j].getCantidadA() - 1);
                        celdas[i][j].eliminarPresa();
                        celdas[i][j].setCantidadX(celdas[i][j].getCantidadX() - 1);
                        celdas[i][j].setMatrizInterna(new Depredador("B"));
                        celdas[i][j].setCantidadA(celdas[i][j].getCantidadA() + 1 );
                        cantXI2--;
                        cantAI2--;
                    }
                }
                if(cantBI > 0 && cantXI == 0){
                    cantBI2 = cantBI;
                    while(cantBI2 > 0 && celdas[i][j].getCantidadA() < 4){
                        celdas[i][j].eliminarDepredadorB(); 
                        celdas[i][j].setCantidadB(celdas[i][j].getCantidadB() - 1);
                        celdas[i][j].setMatrizInterna(new Depredador("A"));
                        celdas[i][j].setCantidadA(celdas[i][j].getCantidadA() + 1 );
                        cantBI2--;
                    }
                }
                if(cantBI > 0 && cantXI >= 2){
                    cantBI2 = cantBI;
                    cantXI2 = cantXI;
                    while(cantXI2 > 1 && cantBI2 > 1 && celdas[i][j].getCantidadA() < 4){
                        celdas[i][j].eliminarDepredadorB(); 
                        celdas[i][j].setCantidadB(celdas[i][j].getCantidadB() - 1);
                        celdas[i][j].eliminarPresa(); 
                        celdas[i][j].setCantidadX(celdas[i][j].getCantidadX()- 1);
                        celdas[i][j].setMatrizInterna(new Depredador("A"));
                        celdas[i][j].setCantidadA(celdas[i][j].getCantidadA() + 1 );
                        celdas[i][j].setMatrizInterna(new Depredador("A"));
                        celdas[i][j].setCantidadA(celdas[i][j].getCantidadA() + 1 );
                        cantXI2--;
                        cantBI2--;
                    }     
                }
                if(cantXI > 1 && celdas[i][j].getCantidadX() < 4){
                        celdas[i][j].setMatrizInterna(new Presa());
                        celdas[i][j].setCantidadX(celdas[i][j].getCantidadX() + 1 );
                }
            }
        }
    }
    
    public void Movimiento(){
        random = new Random();
        for(int i=0; i<50; i++){
            for(int j=0; j<50; j++){
                if(celdas[i][j].getCantidadA() > 0 && i>=1 && i<=48 && j >=1 && j<=48){
                    if(random.nextInt(4) == 0 && celdas[i][j+1].getCantidadA() < 4){
                        celdas[i][j+1].setMatrizInterna(new Depredador("A"));
                        celdas[i][j+1].setCantidadA(celdas[i][j+1].getCantidadA() + 1 );
                        celdas[i][j].eliminarDepredadorA();
                        celdas[i][j].setCantidadA(celdas[i][j].getCantidadA() - 1);
                    }
                    else if(random.nextInt(4) == 1 && celdas[i][j-1].getCantidadA() < 4){
                        celdas[i][j-1].setMatrizInterna(new Depredador("A"));
                        celdas[i][j-1].setCantidadA(celdas[i][j-1].getCantidadA() + 1 );
                        celdas[i][j].eliminarDepredadorA();
                        celdas[i][j].setCantidadA(celdas[i][j].getCantidadA() - 1);
                    }
                    else if(random.nextInt(4) == 2 && celdas[i+1][j].getCantidadA() < 4){
                        celdas[i+1][j].setMatrizInterna(new Depredador("A"));
                        celdas[i+1][j].setCantidadA(celdas[i+1][j].getCantidadA() + 1 );
                        celdas[i][j].eliminarDepredadorA();
                        celdas[i][j].setCantidadA(celdas[i][j].getCantidadA() - 1);
                    }
                    else if(random.nextInt(4) == 3 && celdas[i-1][j].getCantidadA() < 4){
                        celdas[i-1][j].setMatrizInterna(new Depredador("A"));
                        celdas[i-1][j].setCantidadA(celdas[i-1][j].getCantidadA() + 1 );
                        celdas[i][j].eliminarDepredadorA();
                        celdas[i][j].setCantidadA(celdas[i][j].getCantidadA() - 1);
                    }
                }
                if(celdas[i][j].getCantidadB() > 0 && i>=1 && i<=48 && j >=1 && j<=48){
                    if(random.nextInt(4) == 0 && celdas[i][j+1].getCantidadB() < 4){
                        celdas[i][j+1].setMatrizInterna(new Depredador("B"));
                        celdas[i][j+1].setCantidadB(celdas[i][j+1].getCantidadB() + 1 );
                        celdas[i][j].eliminarDepredadorB();
                        celdas[i][j].setCantidadB(celdas[i][j].getCantidadB() - 1);
                    }
                    else if(random.nextInt(4) == 1 && celdas[i][j-1].getCantidadB() < 4){
                        celdas[i][j-1].setMatrizInterna(new Depredador("B"));
                        celdas[i][j-1].setCantidadB(celdas[i][j-1].getCantidadB() + 1 );
                        celdas[i][j].eliminarDepredadorB();
                        celdas[i][j].setCantidadB(celdas[i][j].getCantidadB() - 1);
                    }
                    else if(random.nextInt(4) == 2 && celdas[i+1][j].getCantidadB() < 4){
                        celdas[i+1][j].setMatrizInterna(new Depredador("B"));
                        celdas[i+1][j].setCantidadB(celdas[i+1][j].getCantidadB() + 1 );
                        celdas[i][j].eliminarDepredadorB();
                        celdas[i][j].setCantidadB(celdas[i][j].getCantidadB() - 1);
                    }
                    else if(random.nextInt(4) == 3 && celdas[i-1][j].getCantidadB() < 4){
                        celdas[i-1][j].setMatrizInterna(new Depredador("B"));
                        celdas[i-1][j].setCantidadB(celdas[i-1][j].getCantidadB() + 1 );
                        celdas[i][j].eliminarDepredadorB();
                        celdas[i][j].setCantidadB(celdas[i][j].getCantidadB() - 1);
                    }
                }
                if(celdas[i][j].getCantidadX() > 0 && i>=1 && i<=48 && j >=1 && j<=48){
                    if(random.nextInt(4) == 0 && celdas[i][j+1].getCantidadX() < 4){
                        celdas[i][j+1].setMatrizInterna(new Presa());
                        celdas[i][j+1].setCantidadX(celdas[i][j+1].getCantidadX() + 1 );
                        celdas[i][j].eliminarPresa();
                        celdas[i][j].setCantidadX(celdas[i][j].getCantidadX() - 1);
                    }
                    else if(random.nextInt(4) == 1 && celdas[i][j-1].getCantidadX() < 4){
                        celdas[i][j-1].setMatrizInterna(new Presa());
                        celdas[i][j-1].setCantidadX(celdas[i][j-1].getCantidadX() + 1 );
                        celdas[i][j].eliminarPresa();
                        celdas[i][j].setCantidadX(celdas[i][j].getCantidadX() - 1);
                    }
                    else if(random.nextInt(4) == 2 && celdas[i+1][j].getCantidadX() < 4){
                        celdas[i+1][j].setMatrizInterna(new Presa());
                        celdas[i+1][j].setCantidadX(celdas[i+1][j].getCantidadX() + 1 );
                        celdas[i][j].eliminarPresa();
                        celdas[i][j].setCantidadX(celdas[i][j].getCantidadX() - 1);
                    }
                    else if(random.nextInt(4) == 3 && celdas[i-1][j].getCantidadX() < 4){
                        celdas[i-1][j].setMatrizInterna(new Presa());
                        celdas[i-1][j].setCantidadX(celdas[i-1][j].getCantidadX() + 1 );
                        celdas[i][j].eliminarPresa();
                        celdas[i][j].setCantidadX(celdas[i][j].getCantidadX() - 1);
                    }
                }
                //Movimiento en los bordes
                if(celdas[0][j].getCantidadX() > 0)
                {
                    if(celdas[1][j].getCantidadX() < 4){
                        celdas[1][j].setMatrizInterna(new Presa());
                        celdas[1][j].setCantidadX(celdas[1][j].getCantidadX()+ 1);
                        celdas[0][j].eliminarPresa();
                        celdas[0][j].setCantidadX(celdas[0][j].getCantidadX() - 1);
                    }
                }
                if(celdas[49][j].getCantidadX() > 0)
                {
                    if(celdas[48][j].getCantidadX() < 4){
                        celdas[48][j].setMatrizInterna(new Presa());
                        celdas[48][j].setCantidadX(celdas[48][j].getCantidadX()+ 1);
                        celdas[49][j].eliminarPresa();
                        celdas[49][j].setCantidadX(celdas[49][j].getCantidadX() - 1);
                    }
                }
                if(celdas[i][0].getCantidadX() > 0)
                {
                    if(celdas[i][1].getCantidadX() < 4){
                        celdas[i][1].setMatrizInterna(new Presa());
                        celdas[i][1].setCantidadX(celdas[i][1].getCantidadX()+ 1);
                        celdas[i][0].eliminarPresa();
                        celdas[i][0].setCantidadX(celdas[i][0].getCantidadX() - 1);
                    }
                }
                if(celdas[i][49].getCantidadX() > 0)
                {
                    if(celdas[i][48].getCantidadX() < 4){
                        celdas[i][48].setMatrizInterna(new Presa());
                        celdas[i][48].setCantidadX(celdas[i][48].getCantidadX()+ 1);
                        celdas[i][49].eliminarPresa();
                        celdas[i][49].setCantidadX(celdas[i][49].getCantidadX() - 1);
                    }
                }
                if(celdas[0][j].getCantidadB() > 0)
                {
                    if(celdas[1][j].getCantidadB() < 4){
                        celdas[1][j].setMatrizInterna(new Depredador("B"));
                        celdas[1][j].setCantidadB(celdas[1][j].getCantidadB()+ 1);
                        celdas[0][j].eliminarDepredadorB();
                        celdas[0][j].setCantidadB(celdas[0][j].getCantidadB() - 1);
                    }
                }
                if(celdas[49][j].getCantidadB() > 0)
                {
                    if(celdas[48][j].getCantidadB() < 4){
                        celdas[48][j].setMatrizInterna(new Depredador("B"));
                        celdas[48][j].setCantidadB(celdas[48][j].getCantidadB()+ 1);
                        celdas[49][j].eliminarDepredadorB();
                        celdas[49][j].setCantidadB(celdas[49][j].getCantidadB() - 1);
                    }
                }
                if(celdas[i][0].getCantidadB() > 0)
                {
                    if(celdas[i][1].getCantidadB() < 4){
                        celdas[i][1].setMatrizInterna(new Depredador("B"));
                        celdas[i][1].setCantidadB(celdas[i][1].getCantidadB()+ 1);
                        celdas[i][0].eliminarDepredadorB();
                        celdas[i][0].setCantidadB(celdas[i][0].getCantidadB() - 1);
                    }
                }
                if(celdas[i][49].getCantidadB() > 0)
                {
                    if(celdas[i][48].getCantidadB() < 4){
                        celdas[i][48].setMatrizInterna(new Depredador("B"));
                        celdas[i][48].setCantidadB(celdas[i][48].getCantidadB()+ 1);
                        celdas[i][49].eliminarDepredadorB();
                        celdas[i][49].setCantidadB(celdas[i][49].getCantidadB() - 1);
                    }
                }
                if(celdas[0][j].getCantidadA() > 0)
                {
                    if(celdas[1][j].getCantidadA() < 4){
                        celdas[1][j].setMatrizInterna(new Depredador("A"));
                        celdas[1][j].setCantidadA(celdas[1][j].getCantidadA()+ 1);
                        celdas[1][j].eliminarDepredadorA();
                        celdas[0][j].setCantidadA(celdas[0][j].getCantidadA() - 1);
                    }
                }
                if(celdas[49][j].getCantidadA() > 0)
                {
                    if(celdas[48][j].getCantidadA() < 4){
                        celdas[48][j].setMatrizInterna(new Depredador("A"));
                        celdas[48][j].setCantidadA(celdas[48][j].getCantidadA()+ 1);
                        celdas[49][j].eliminarDepredadorA();
                        celdas[49][j].setCantidadA(celdas[49][j].getCantidadA() - 1);
                    }
                }
                if(celdas[i][0].getCantidadA() > 0)
                {
                    if(celdas[i][1].getCantidadA() < 4){
                        celdas[i][1].setMatrizInterna(new Depredador("A"));
                        celdas[i][1].setCantidadA(celdas[i][1].getCantidadA()+ 1);
                        celdas[i][0].eliminarDepredadorA();
                        celdas[i][0].setCantidadA(celdas[i][0].getCantidadA() - 1);
                    }
                }
                if(celdas[i][49].getCantidadA() > 0)
                {
                    if(celdas[i][48].getCantidadA() < 4){
                        celdas[i][48].setMatrizInterna(new Depredador("A"));
                        celdas[i][48].setCantidadA(celdas[i][48].getCantidadA()+ 1);
                        celdas[i][49].eliminarDepredadorA();
                        celdas[i][49].setCantidadA(celdas[i][49].getCantidadA() - 1);
                    }
                }
            }
        }
    }
    
    //Establecer limites de scroll
    @Override
    public Dimension getPreferredSize() {
        return new Dimension(2240, 1180);
    }
    
    //Inicializar - colocar objetos en matriz
    public void iniciarComponentes(){
        colocarBotones();
        colocarEtiquetas();
        colocarListasDesplegables();
    }
    
    public void colocarBotones(){
        //Boton 1 - iniciar simulacion
        boton1 = new JButton();
        boton1.setText("Comenzar");
        boton1.setBounds(55, 740, 170, 44);
        boton1.setBackground(Color.CYAN);
        boton1.setFont(new Font("Eras Demi ITC",Font.PLAIN,22));
        this.add(boton1);
        
        //Boton 2 - pausar simulacion
        boton2 = new JButton();
        boton2.setText("Pausar");
        boton2.setBounds(55, 800, 170, 44);
        boton2.setBackground(Color.ORANGE);
        boton2.setFont(new Font("Eras Demi ITC",Font.PLAIN,22));
        boton2.setEnabled(false);
        this.add(boton2);
        
        //Boton 3 - Reanudar simulacion
        boton3 = new JButton();
        boton3.setText("Reanudar");
        boton3.setBounds(55,850,170,44);
        boton3.setBackground(Color.ORANGE);
        boton3.setFont(new Font("Eras Demi ITC",Font.PLAIN,22));
        boton3.setEnabled(false);
        this.add(boton3);
        
        //Boton 4 - Bajar velocidad
        boton4 = new JButton();
        boton4.setText("-");
        boton4.setBounds(50, 490, 70, 35);
        boton4.setBackground(Color.LIGHT_GRAY);
        boton4.setFont(new Font("Eras Demi ITC",Font.PLAIN,28));
        boton4.setEnabled(true);
        this.add(boton4);
        
        //Boton 5 - Subir velocidad
        boton5 = new JButton();
        boton5.setText("+");
        boton5.setBounds(160, 490, 70, 35);
        boton5.setBackground(Color.LIGHT_GRAY);
        boton5.setFont(new Font("Eras Demi ITC",Font.PLAIN,18));
        boton5.setEnabled(true);
        this.add(boton5);
        
        //Boton 6 - Mostrar String
        boton6 = new JButton();
        boton6.setText("Mostrar");
        boton6.setBounds(45, 615, 100, 30);
        boton6.setBackground(Color.LIGHT_GRAY);
        boton6.setFont(new Font("Eras Demi ITC",Font.PLAIN,18));
        boton6.setEnabled(false);
        this.add(boton6);
        
        //Action Listener pausar
        boton2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                boton6.setEnabled(true);  //Habilitar boton mostrar al pausar
            } 
        });
        
        //Action Listener reanudar
        boton3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                boton6.setEnabled(false);  //Deshabilitar boton mostrar al reanudar
            } 
        });
        
        //Action Listener 4
        boton4.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //Reducir indicador de velocidad
                indicadorVel = indicadorVel - 0.5;
                velocidad.setText(null);
                velocidad.setText(""+indicadorVel);
               
                //Deshabilitar y habilitar
                if(indicadorVel == 0.5){
                    boton4.setEnabled(false); //bloquear - en 0.5
                }
                if(indicadorVel < 2.5){
                    boton5.setEnabled(true); //habilitar +
                }
               
                //Reducir la velocidad del programa
                delay = delay + 500;
            } 
        });
        
        //Action Listener 5
        boton5.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                //Aumentar indicador de velocidad
                indicadorVel = indicadorVel + 0.5;
                velocidad.setText(null);
                velocidad.setText(""+indicadorVel);
               
                //Deshabilitar y habilitar
                if(indicadorVel == 2.5){
                   boton5.setEnabled(false); //bloquear + en 2.5
                }
                if(indicadorVel > 0.5){
                   boton4.setEnabled(true); //habilitar -
                }
               
                //Aumentar la velocidad del programa
                delay = delay - 500;
           } 
        });
        
        //Action Listener 6
        boton6.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                contenido.setText(null);
                contenido.setText("a^"+celdas[iString][jString].getCantidadA()+" b^"+celdas[iString][jString].getCantidadB()+" x^"+celdas[iString][jString].getCantidadX());
            } 
        });
    }
    
    public void colocarEtiquetas(){
        //Etiqueta N° de iteraciones
        JLabel etiquetaIteraciones = new JLabel();
        etiquetaIteraciones.setForeground(Color.BLACK);
        etiquetaIteraciones.setText("N° iteraciones:");
        etiquetaIteraciones.setBounds(40,325,220,40);
        etiquetaIteraciones.setFont(new Font("Eras Demi ITC",Font.PLAIN,21));
        this.add(etiquetaIteraciones);
        
        //Etiqueta velocidad
        JLabel etiquetaVel = new JLabel();
        etiquetaVel.setForeground(Color.BLACK);
        etiquetaVel.setText("Velocidad");
        etiquetaVel.setBounds(93, 405, 160, 40);
        etiquetaVel.setFont(new Font("Eras Demi ITC",Font.PLAIN,21));
        this.add(etiquetaVel);
        
        //Etiqueta valor de velocidad
        velocidad.setText(null);
        velocidad.setText("1.5");
        velocidad.setBounds(125,435,140,60);
        velocidad.setFont(new Font("Eras Demi ITC",Font.PLAIN,21));
        velocidad.setForeground(Color.BLACK);
        this.add(velocidad);
        
        //Etiqueta del contenido de la celda
        contenido.setText(null);
        contenido.setBounds(50, 650, 200, 40);
        contenido.setFont(new Font("Eras Demi ITC",Font.PLAIN,16));
        contenido.setForeground(Color.BLACK);
        this.add(contenido);
        
        //Etiqueta mostrar contenido de casillas
        JLabel etiquetaString = new JLabel();
        etiquetaString.setBounds(46, 560, 200, 60);
        etiquetaString.setForeground(Color.BLACK);
        etiquetaString.setText("Mostrar Contenido");
        etiquetaString.setFont(new Font("Eras Demi ITC",Font.PLAIN,21));
        this.add(etiquetaString);
        
        //Etiqueta depredador a
        JLabel etiquetaA = new JLabel();
        etiquetaA.setForeground(Color.BLACK);
        etiquetaA.setText("Depredador A");
        etiquetaA.setBounds(85, 70, 160, 40);
        etiquetaA.setFont(new Font("Eras Demi ITC",Font.PLAIN,21));
        this.add(etiquetaA);
        
        //Etiqueta depredador b
        JLabel etiquetaB = new JLabel();
        etiquetaB.setForeground(Color.BLACK);
        etiquetaB.setText("Depredador B");
        etiquetaB.setBounds(85, 150, 160, 40);
        etiquetaB.setFont(new Font("Eras Demi ITC",Font.PLAIN,21));
        this.add(etiquetaB);
        
        //Etiqueta presa x
        JLabel etiquetaC = new JLabel();
        etiquetaC.setForeground(Color.BLACK);
        etiquetaC.setText("Presa");
        etiquetaC.setBounds(85, 230, 160, 40);
        etiquetaC.setFont(new Font("Eras Demi ITC",Font.PLAIN,21));
        this.add(etiquetaC);
    }
    
    public void colocarListasDesplegables(){
        //Lista desplegable 1 - i
        Integer [] opciones1 = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,
        23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49};
        lista1 = new JComboBox(opciones1);
        lista1.setBounds(175,615,60,25);
        lista1.setSelectedItem(0);
        this.add(lista1);
        
        //Lista desplegable 2 - j
        Integer [] opciones2 = {0,1,2,3,4,5,6,7,8,9,10,11,12,13,14,15,16,17,18,19,20,21,22,
        23,24,25,26,27,28,29,30,31,32,33,34,35,36,37,38,39,40,41,42,43,44,45,46,47,48,49};
        lista2 = new JComboBox(opciones2);
        lista2.setBounds(175,655,60,25);
        lista2.setSelectedItem(0);
        this.add(lista2);
        
        lista1.addItemListener(handler3);
        lista2.addItemListener(handler4);
    }
    
    private class ItemHandler implements ItemListener{
        //Action listeners de ComboBoxes
        @Override
        public void itemStateChanged(ItemEvent e) {
            if(e.getSource()==lista1){
                if(lista1.getSelectedItem().equals(0)){
                    iString = 0;
                }else if(lista1.getSelectedItem().equals(1)){
                    iString = 1;
                }else if(lista1.getSelectedItem().equals(2)){
                    iString = 2;
                }else if(lista1.getSelectedItem().equals(3)){
                    iString = 3;
                }else if(lista1.getSelectedItem().equals(4)){
                    iString = 4;
                }else if(lista1.getSelectedItem().equals(5)){
                    iString = 5;
                }else if(lista1.getSelectedItem().equals(6)){
                    iString = 6;
                }else if(lista1.getSelectedItem().equals(7)){
                    iString = 7;
                }else if(lista1.getSelectedItem().equals(8)){
                    iString = 8;
                }else if(lista1.getSelectedItem().equals(9)){
                    iString = 9;
                }else if(lista1.getSelectedItem().equals(10)){
                    iString = 10;
                }else if(lista1.getSelectedItem().equals(11)){
                    iString = 11;
                }else if(lista1.getSelectedItem().equals(12)){
                    iString = 12;
                }else if(lista1.getSelectedItem().equals(13)){
                    iString = 13;
                }else if(lista1.getSelectedItem().equals(14)){
                    iString = 14;
                }else if(lista1.getSelectedItem().equals(15)){
                    iString = 15;
                }else if(lista1.getSelectedItem().equals(16)){
                    iString = 16;
                }else if(lista1.getSelectedItem().equals(17)){
                    iString = 17;
                }else if(lista1.getSelectedItem().equals(18)){
                    iString = 18;
                }else if(lista1.getSelectedItem().equals(19)){
                    iString = 19;
                }else if(lista1.getSelectedItem().equals(20)){
                    iString = 20;
                }else if(lista1.getSelectedItem().equals(21)){
                    iString = 21;
                }else if(lista1.getSelectedItem().equals(22)){
                    iString = 22;
                }else if(lista1.getSelectedItem().equals(23)){
                    iString = 23;
                }else if(lista1.getSelectedItem().equals(24)){
                    iString = 24;
                }else if(lista1.getSelectedItem().equals(25)){
                    iString = 25;
                }else if(lista1.getSelectedItem().equals(26)){
                    iString = 26;
                }else if(lista1.getSelectedItem().equals(27)){
                    iString = 27;
                }else if(lista1.getSelectedItem().equals(28)){
                    iString = 28;
                }else if(lista1.getSelectedItem().equals(29)){
                    iString = 29;
                }else if(lista1.getSelectedItem().equals(30)){
                    iString = 30;
                }else if(lista1.getSelectedItem().equals(31)){
                    iString = 31;
                }else if(lista1.getSelectedItem().equals(32)){
                    iString = 32;
                }else if(lista1.getSelectedItem().equals(33)){
                    iString = 33;
                }else if(lista1.getSelectedItem().equals(34)){
                    iString = 34;
                }else if(lista1.getSelectedItem().equals(35)){
                    iString = 35;
                }else if(lista1.getSelectedItem().equals(36)){
                    iString = 36;
                }else if(lista1.getSelectedItem().equals(37)){
                    iString = 37;
                }else if(lista1.getSelectedItem().equals(38)){
                    iString = 38;
                }else if(lista1.getSelectedItem().equals(39)){
                    iString = 39;
                }else if(lista1.getSelectedItem().equals(40)){
                    iString = 40;
                }else if(lista1.getSelectedItem().equals(41)){
                    iString = 41;
                }else if(lista1.getSelectedItem().equals(42)){
                    iString = 42;
                }else if(lista1.getSelectedItem().equals(43)){
                    iString = 43;
                }else if(lista1.getSelectedItem().equals(44)){
                    iString = 44;
                }else if(lista1.getSelectedItem().equals(45)){
                    iString = 45;
                }else if(lista1.getSelectedItem().equals(46)){
                    iString = 46;
                }else if(lista1.getSelectedItem().equals(47)){
                    iString = 47;
                }else if(lista1.getSelectedItem().equals(48)){
                    iString = 48;
                }else if(lista1.getSelectedItem().equals(49)){
                    iString = 49;
                }
            }
            
            if(e.getSource()==lista2){
                if(lista2.getSelectedItem().equals(0)){
                    jString = 0;
                }else if(lista2.getSelectedItem().equals(1)){
                    jString = 1;
                }else if(lista2.getSelectedItem().equals(2)){
                    jString = 2;
                }else if(lista2.getSelectedItem().equals(3)){
                    jString = 3;
                }else if(lista2.getSelectedItem().equals(4)){
                    jString = 4;
                }else if(lista2.getSelectedItem().equals(5)){
                    jString = 5;
                }else if(lista2.getSelectedItem().equals(6)){
                    jString = 6;
                }else if(lista2.getSelectedItem().equals(7)){
                    jString = 7;
                }else if(lista2.getSelectedItem().equals(8)){
                    jString = 8;
                }else if(lista2.getSelectedItem().equals(9)){
                    jString = 9;
                }else if(lista2.getSelectedItem().equals(10)){
                    jString = 10;
                }else if(lista2.getSelectedItem().equals(11)){
                    jString = 11;
                }else if(lista2.getSelectedItem().equals(12)){
                    jString = 12;
                }else if(lista2.getSelectedItem().equals(13)){
                    jString = 13;
                }else if(lista2.getSelectedItem().equals(14)){
                    jString = 14;
                }else if(lista2.getSelectedItem().equals(15)){
                    jString = 15;
                }else if(lista2.getSelectedItem().equals(16)){
                    jString = 16;
                }else if(lista2.getSelectedItem().equals(17)){
                    jString = 17;
                }else if(lista2.getSelectedItem().equals(18)){
                    jString = 18;
                }else if(lista2.getSelectedItem().equals(19)){
                    jString = 19;
                }else if(lista2.getSelectedItem().equals(20)){
                    jString = 20;
                }else if(lista2.getSelectedItem().equals(21)){
                    jString = 21;
                }else if(lista2.getSelectedItem().equals(22)){
                    jString = 22;
                }else if(lista2.getSelectedItem().equals(23)){
                    jString = 23;
                }else if(lista2.getSelectedItem().equals(24)){
                    jString = 24;
                }else if(lista2.getSelectedItem().equals(25)){
                    jString = 25;
                }else if(lista2.getSelectedItem().equals(26)){
                    jString = 26;
                }else if(lista2.getSelectedItem().equals(27)){
                    jString = 27;
                }else if(lista2.getSelectedItem().equals(28)){
                    jString = 28;
                }else if(lista2.getSelectedItem().equals(29)){
                    jString = 29;
                }else if(lista2.getSelectedItem().equals(30)){
                    jString = 30;
                }else if(lista2.getSelectedItem().equals(31)){
                    jString = 31;
                }else if(lista2.getSelectedItem().equals(32)){
                    jString = 32;
                }else if(lista2.getSelectedItem().equals(33)){
                    jString = 33;
                }else if(lista2.getSelectedItem().equals(34)){
                    jString = 34;
                }else if(lista2.getSelectedItem().equals(35)){
                    jString = 35;
                }else if(lista2.getSelectedItem().equals(36)){
                    jString = 36;
                }else if(lista2.getSelectedItem().equals(37)){
                    jString = 37;
                }else if(lista2.getSelectedItem().equals(38)){
                    jString = 38;
                }else if(lista2.getSelectedItem().equals(39)){
                    jString = 39;
                }else if(lista2.getSelectedItem().equals(40)){
                    jString = 40;
                }else if(lista2.getSelectedItem().equals(41)){
                    jString = 41;
                }else if(lista2.getSelectedItem().equals(42)){
                    jString = 42;
                }else if(lista2.getSelectedItem().equals(43)){
                    jString = 43;
                }else if(lista2.getSelectedItem().equals(44)){
                    jString = 44;
                }else if(lista2.getSelectedItem().equals(45)){
                    jString = 45;
                }else if(lista2.getSelectedItem().equals(46)){
                    jString = 46;
                }else if(lista2.getSelectedItem().equals(47)){
                    jString = 47;
                }else if(lista2.getSelectedItem().equals(48)){
                    jString = 48;
                }else if(lista2.getSelectedItem().equals(49)){
                    jString = 49;
                }
            }
        }
    }
    
    //Correr los algoritmos del programa por el hilo
    public void algoritmos() throws InterruptedException{
        
        contadorIteraciones++;
        Reglas();
        Movimiento();
        speedControl();
    }
    
    //Sleep para velocidad de la simulacion
    public void speedControl() throws InterruptedException{
        Thread.sleep(delay);
    }

    
    //DIBUJOS CON GRAPHICS

   
    public void paintComponent(Graphics g){
        Graphics2D g2 = (Graphics2D) g;
        super.paintComponent(g);
        
        //Afinar la calidad de renderizado
        g2.setRenderingHint(
            RenderingHints.KEY_TEXT_ANTIALIASING,
            RenderingHints.VALUE_TEXT_ANTIALIAS_ON);
        g2.setRenderingHint(
            RenderingHints.KEY_RENDERING,
            RenderingHints.VALUE_RENDER_QUALITY);
        
        
        dibujar(g2);
    }
    
    //dibujar en el panel*
    public void dibujar(Graphics2D g){
        dibujarCeldas(g);
        informacion(g);
        coloresMatriz(g);
    }
    
    //Leer los contenidos de la matriz para dibujar con colores
    public void coloresMatriz(Graphics2D g){
        int a=0, b=0, x=0;
        
        for(int i=0; i<50; i++){
            for(int j=0; j<50; j++){
                //Determinar valor para a
                if(celdas[i][j].getCantidadA() == 0){
                    a = 51;
                }else if(celdas[i][j].getCantidadA() == 1){
                    a = 102;
                }else if(celdas[i][j].getCantidadA() == 2){
                    a = 153;
                }else if(celdas[i][j].getCantidadA() == 3){
                    a = 204;
                }else if(celdas[i][j].getCantidadA() == 4){
                    a = 255;
                }
                
                //Determinar valor para b
                if(celdas[i][j].getCantidadB() == 0){
                    b = 51;
                }else if(celdas[i][j].getCantidadB() == 1){
                    b = 102;
                }else if(celdas[i][j].getCantidadB() == 2){
                    b = 153;
                }else if(celdas[i][j].getCantidadB() == 3){
                    b = 204;
                }else if(celdas[i][j].getCantidadB() == 4){
                    b = 255;
                }
                
                //determinar valor para x
                if(celdas[i][j].getCantidadX() == 0){
                    x = 51;
                }else if(celdas[i][j].getCantidadX() == 1){
                    x = 102;
                }else if(celdas[i][j].getCantidadX() == 2){
                    x = 153;
                }else if(celdas[i][j].getCantidadX() == 3){
                    x = 204;
                }else if(celdas[i][j].getCantidadX() == 4){
                    x = 255;
                }
                
                //Dibujar el rectangulo con su respectivo color
                Color colorCelda = new Color(a,b,x);
                g.setColor(colorCelda);
                RoundRectangle2D rec = new RoundRectangle2D.Double(i*38+284,j*22+44,30,14,1,1);
                g.fill(rec);
            }
        }
    }
    
    //Casillas para matriz
    public void dibujarCeldas(Graphics g){
        for(int i=0; i<50; i++){
            for(int j=0; j<50; j++){
              int pos_x = i*38+280;
              int pos_y = j*22+40;
              Color color = new Color(204,102,0);
              g.setColor(color);
              g.drawRect(pos_x, pos_y, 38, 22);
            }
        }
    }
    
    
    //Informacion en la parte izquierda de la ventana
    public void informacion(Graphics2D g){
        
        //Rectangulo que contiene las iteraciones
        Color color = new Color(153,76,0);
        g.setColor(color);
        RoundRectangle2D rec1 = new RoundRectangle2D.Double(30, 310, 220, 70, 10, 10);
        g.fill(rec1);
        
        //rectangulo que contiene la informacion
        RoundRectangle2D rec2 = new RoundRectangle2D.Double(30, 45, 220, 250, 10, 10);
        g.fill(rec2);
        
        //Rectangulo que contiene datos de velocidad de simulacion
        RoundRectangle2D rec3 = new RoundRectangle2D.Double(30, 395, 220, 150, 10, 10);
        g.fill(rec3);
        
        //Rectangulo que contiene botones mostrar String
        RoundRectangle2D rec4 = new RoundRectangle2D.Double(30, 565, 220, 140, 10, 10);
        g.fill(rec4);
        
        //Datos en el recuadro
        g.setFont(new Font("Eras Demi ITC",Font.PLAIN,30));
        Color colorChar1 = new Color(240,0,0);
        Color colorChar2 = new Color(0,185,0);
        Color ColorChar3 = new Color(0,0,200);
        
        g.setColor(colorChar1);
        g.drawString("a", 50, 97);
        
        g.setColor(colorChar2);
        g.drawString("b", 50, 177);
        
        g.setColor(ColorChar3);
        g.drawString("x", 50, 257);
        
        //N° de iteraciones
        g.setFont(new Font("Eras Demi ITC",Font.PLAIN,24));
        Color colorIteracion = new Color(0,0,0);
        g.setColor(colorIteracion);
        g.drawString(""+contadorIteraciones,185,353);
        
        //i y j de ComboBoxes
        g.setFont(new Font("Eras Demi ITC",Font.PLAIN,16));
        g.drawString("i", 165, 633);
        g.drawString("j", 165, 670);
    }
    
}
