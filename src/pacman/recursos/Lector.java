/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.recursos;

import java.io.*;
import java.util.*;
import javax.swing.JOptionPane;

/**
 *
 * @author Marco XD
 */
public class Lector {

    public static String lector = "";
    public static String aux = "";
    public static String pun = "";
    public static int nj = 1;

    public static void score(int score, String nombre) throws Exception {
        File f2 = new File("score.txt");
        FileWriter fw = new FileWriter(f2, true);
        BufferedWriter bw = new BufferedWriter(fw);
        Calendar fecha = new GregorianCalendar();
        int año = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        int hora = fecha.get(Calendar.HOUR_OF_DAY);
        int minuto = fecha.get(Calendar.MINUTE);
        pun = nombre + ":  " + score + "\nFecha del juego: " + dia + "/" + (mes + 1) + "/" + año + "\nHora " + hora + ":" + minuto + "\n \n";
        bw.write(pun);
        bw.close();
        nj++;

    }

    public static void hscore() throws Exception {
        File f = new File("score.txt");
        FileReader fr = new FileReader(f);
        BufferedReader br = new BufferedReader(fr);
        while (true) {
            aux = br.readLine();
            if (aux != null) {
                lector = lector + aux + "\n";
            } else {
                break;
            }
        }
        br.close();
        fr.close();
        //bw.close();
        JOptionPane.showMessageDialog(null, lector);
        lector = "";
    }

}
