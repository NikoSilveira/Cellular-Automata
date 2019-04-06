
package Automata;

import java.awt.Color;
import java.awt.Font;
import java.awt.Image;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;

public class Ventana1 extends JFrame{
    JPanel panel1 = new JPanel();
    
    JComboBox lista1, lista2;
    int numPresas, numDepredadores;
    private ItemHandler handler1 = new ItemHandler();
    private ItemHandler handler2 = new ItemHandler();
    
    //Constructor
    public Ventana1(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE); //Terminar programa al cerrar
        this.setTitle("Automata Celular - Inicio");   //Titulo                        
        this.setResizable(false);                     //No poder modificar tamaño de ventana
        this.setSize(700,500);                        //Tamaño de Ventana
        this.setLocationRelativeTo(null);             //Centrar ventana
        
        numPresas = 250;
        numDepredadores = 250;
        
        iniciarComponentes();
        setVisible(true);
    }
    
    private void iniciarComponentes(){ //Inicializar los componentes de la ventana
        colocarEtiquetas();
        colocarBotones();
        colocarListasDesplegables();
        colocarPaneles();
    }
    
    private void colocarPaneles(){
        panel1.setLayout(null);                //Quitar layout por defecto
        this.getContentPane().add(panel1);     //Poner panel sobre ventana
    }
    
    private void colocarEtiquetas(){
        //Etiqueta de fondo - imagen
        ImageIcon imagen = new ImageIcon("AI.jpeg");
        JLabel etiquetaFondo = new JLabel();
        etiquetaFondo.setBounds(0, 0, 700, 500);   //tamaño etiqueta
        etiquetaFondo.setIcon(new ImageIcon(imagen.getImage().getScaledInstance(700, 500, Image.SCALE_SMOOTH)));  //Escalar etiqueta
        panel1.add(etiquetaFondo);
        
        //Etiqueta titulo "Automata"
        JLabel etiqueta1 = new JLabel("Automata",SwingConstants.CENTER);
        etiqueta1.setBounds(410, 40, 220, 90);
        etiqueta1.setForeground(Color.WHITE);
        etiqueta1.setFont(new Font("Eras Demi ITC",Font.PLAIN,44));
        this.add(etiqueta1);
        
        //Etiqueta titulo "Celular"
        JLabel etiqueta2 = new JLabel("Celular",SwingConstants.CENTER);
        etiqueta2.setBounds(460, 100, 180, 90);
        etiqueta2.setForeground(Color.WHITE);
        etiqueta2.setFont(new Font("Eras Demi ITC",Font.PLAIN,44));
        this.add(etiqueta2);
        
        //Etiqueta N° depredadores
        JLabel depredadores = new JLabel("N° depredadores", SwingConstants.CENTER);
        depredadores.setBounds(410,210,200,80);
        depredadores.setForeground(Color.WHITE);
        depredadores.setFont(new Font("Eras Demi ITC",Font.PLAIN,18));
        this.add(depredadores);
        
        //Etiqueta N° presas
        JLabel presas = new JLabel("N° presas", SwingConstants.CENTER);
        presas.setBounds(380, 270, 200, 80);
        presas.setForeground(Color.WHITE);
        presas.setFont(new Font("Eras Demi ITC",Font.PLAIN,18));
        this.add(presas);
    }
    
    private void colocarListasDesplegables(){
        //Lista desplegable 1 - depredadores
        Integer [] opciones1 = {100,250,500,750,1000};
        lista1 = new JComboBox(opciones1);
        lista1.setBounds(600,240,60,25);
        lista1.setSelectedItem(250);
        this.add(lista1);
        
        //Lista desplegable 2 - presas
        Integer [] opciones2 = {100,250,500,750,1000};
        lista2 = new JComboBox(opciones2);
        lista2.setBounds(600,300,60,25);
        lista2.setSelectedItem(250);
        this.add(lista2);
        
        lista1.addItemListener(handler1);
        lista2.addItemListener(handler2);
    }
    
    private class ItemHandler implements ItemListener{
        //Action listeners de ComboBoxes
        @Override
        public void itemStateChanged(ItemEvent e) {
            //N° depredadores
            if(e.getSource()==lista1){
                if(lista1.getSelectedItem().equals(100)){
                    numDepredadores = 100;
                }else if(lista1.getSelectedItem().equals(250)){
                    numDepredadores = 250;
                }else if(lista1.getSelectedItem().equals(500)){
                    numDepredadores = 500;
                }else if(lista1.getSelectedItem().equals(750)){
                    numDepredadores = 750;
                }else if(lista1.getSelectedItem().equals(1000)){
                    numDepredadores = 1000;
                }
            }
            
            if(e.getSource()==lista2){
                //N° presas
                if(lista2.getSelectedItem().equals(100)){
                    numPresas = 100;
                }else if(lista2.getSelectedItem().equals(250)){
                    numPresas = 250;
                }else if(lista2.getSelectedItem().equals(500)){
                    numPresas = 500;
                }else if(lista2.getSelectedItem().equals(750)){
                    numPresas = 750;
                }else if(lista2.getSelectedItem().equals(1000)){
                    numPresas = 1000;
                }
            }
        }
    }
    
    private void colocarBotones(){
        //Botones de inicio
        JButton botonInicio1 = new JButton();
        JButton botonInicio2 = new JButton();
        
        botonInicio1.setText("Iniciar");
        botonInicio1.setBounds(430,400,100,34);
        botonInicio1.setFont(new Font("Eras Demi ITC",Font.PLAIN,22));
        botonInicio1.setBackground(Color.cyan);  //Color de fondo del boton
        
        botonInicio2.setText("Salir");
        botonInicio2.setBounds(550,400,100,34);
        botonInicio2.setFont(new Font("Eras Demi ITC",Font.PLAIN,22));
        botonInicio2.setBackground(Color.pink);  //Color de fondo del boton
        
        this.add(botonInicio1);
        this.add(botonInicio2);
        
        
        //ACTION LISTENERS
        
        //Boton 1
        ActionListener oyenteInicio1 = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                dispose();                     //Cerrar ventana y liberar recursos
                Ventana2 v2 = new Ventana2(numDepredadores, numPresas);  //Crear ventana para simulacion
            }
        };
        botonInicio1.addActionListener(oyenteInicio1);  //Enlazar boton a accion
        
        //Boton 2
        ActionListener oyenteInicio2 = new ActionListener(){
            @Override
            public void actionPerformed(ActionEvent e) {
                Runtime.getRuntime().exit(0); //Salir y cerrar programa
            }
        };
        botonInicio2.addActionListener(oyenteInicio2);  //enlazar boton a accion
    }
}
