/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package view;

import java.util.ArrayList;

/**
 *
 * @author Charly
 */
public class Texto {
    public ArrayList<String> log = new ArrayList<String>();
    public String resul = new String();

    public Texto() {
    }

    public ArrayList<String> getLog() {
        return log;
    }

    public void setLog(ArrayList<String> log) {
        this.log = log;
    }

    public String getResul() {
        return resul;
    }

    public void setResul(String resul) {
        this.resul = resul;
    }
    
    public void addLog(String str){
        log.add(str);
    }

    
    
    
}
