package Automata;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

public class Ventana2 extends JFrame{
    
    Tablero panel;       //Crear panel de tipo Tablero
    JScrollPane scroll;  //Crear scrollPane
    Thread t;            //Hilo para las iteraciones
    
    //Constructor
    public Ventana2(int numDepredadores, int numPresas){
        panel = new Tablero(numDepredadores, numPresas);
        scroll = new JScrollPane(panel);
        
        //Caracteristicas de la ventana
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);    //Terminar programa al cerrar
        this.setTitle("Automata Celular - Simulacion");  //Titulo
        this.setResizable(true);                         //No poder modificar tamaño de ventana
        setExtendedState(java.awt.Frame.MAXIMIZED_BOTH); //Iniciar maximizada
        this.setSize(1900, 1000);                        //Tamaño de ventana
        this.setLocationRelativeTo(null);                //Centrar ventana
                
        t = new Movimiento(panel);    //Creacion del hilo
        
        
        //Action listener de Comenzar
        panel.boton1.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                t.start();                      //Iniciar thread
                panel.boton1.setEnabled(false); //Deshabilitar boton
                panel.boton2.setEnabled(true);  //Habilitar pausar
                panel.boton4.setEnabled(true);  //Habilitar -vel
                panel.boton5.setEnabled(true);  //Habilitar +vel
            }
        });
        
        //Action listener de Pausar
        panel.boton2.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                panel.boton2.setEnabled(false); //Deshabilitar pausar
                panel.boton3.setEnabled(true);  //Habilitar reanudar
                t.suspend();                    //Pausar el thread
            }
        });
        
        //Action Listener de Reanudar
        panel.boton3.addActionListener(new ActionListener(){
            public void actionPerformed(ActionEvent e){
                panel.boton3.setEnabled(false); //Deshabilitar reanudar
                panel.boton2.setEnabled(true);  //Habilitar pausar
                t.resume();                     //Reanudar el thread
            }
        });
        
        
        iniciarComponentes();
        setVisible(true);
    }
    
    
    public void iniciarComponentes(){
        colocarPanel();
        colocarBoton();
    }
    
    public void colocarPanel(){
        this.getContentPane().add(scroll);                                         //Poner scroll sobre ventana
        scroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_ALWAYS);  //Mostrar siempre scroll bar vertical
        scroll.getVerticalScrollBar().setUnitIncrement(10);                        //Velocidad del scroll
        panel.setLayout(null);                                                     //Quitar layout de panel por defecto
        panel.setBackground(Color.DARK_GRAY);                                      //Color del panel
    }
    
    public void colocarBoton(){
        //Boton para cerrar programa
        JButton botonCerrar = new JButton();
        botonCerrar.setText("Cerrar");
        botonCerrar.setBounds(55, 910, 170, 44);
        botonCerrar.setBackground(Color.PINK);
        botonCerrar.setFont(new Font("Eras Demi ITC",Font.PLAIN,22));
        panel.add(botonCerrar);
        
        //Action performer boton Cerrar
        ActionListener oyenteCerrar = new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                int reply = JOptionPane.showConfirmDialog(null, "¿Esta seguro que desea detener la simulacion?\n"
                        + "Se cerrara el programa", "Automata Celular - Salir", JOptionPane.YES_NO_OPTION);
                   if (reply == JOptionPane.YES_OPTION) {
                   System.exit(0); //Cerrar el programa
                }
            }
        };
        botonCerrar.addActionListener(oyenteCerrar);  //Enlazar boton con accion
    }
    
}
