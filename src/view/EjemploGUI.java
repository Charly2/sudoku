/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

/**
 *
 * @author Charly
 */

import java.awt.FlowLayout;

import javax.swing.JPanel;

import javax.swing.JFrame;


public class EjemploGUI {
 JFrame ventana;
 JPanel top;

 
 public EjemploGUI(){
  iniciarGUI();
 }
 

 public void iniciarGUI(){
  instanciarGUI();
  configurarGUI();
  añadirAGUI();
  ventana.setVisible(true);
 }

 private void instanciarGUI() {
  ventana = new JFrame("Ejemplo GUI #3");
  
  top = new Top();
  
 }
 
 private void configurarGUI() {
  ventana.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
  ventana.setLayout(new FlowLayout());
 }

 private void añadirAGUI() {

 
 
  ventana.add(top);
   ventana.pack();
 }

 


 
 public void llenarCampo( ){
  
 }
 
 public static void main(String[] args){
  //Llamamos a una nueva instancia de la clase y a un método en la misma:
  new EjemploGUI().llenarCampo();
 }
 
}