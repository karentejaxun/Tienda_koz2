/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Vista;

import java.awt.Graphics;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author usuario
 */
public class Fondo extends JPanel {
    public Fondo(){
    }
    
    public void paint(Graphics g){
        ImageIcon imagen = new ImageIcon(getClass().getResource("iconos/6445.jpg"));
        g.drawImage(imagen.getImage(),0,0, getWidth(), getHeight(), this);
        
        setOpaque(false);
        super.paint(g);
    }
    
    public static void main(String[] args){
        JFrame ventana = new JFrame("Imagen de Fondo");
        Fondo fondo = new Fondo();
        ventana.setContentPane(fondo);
        ventana.setSize(1000,800);
        ventana.setVisible(true);
        ventana.setLocationRelativeTo(null);
        ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
    }
    
}
