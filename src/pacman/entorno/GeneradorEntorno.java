/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.entorno;

import java.applet.AudioClip;
import java.awt.Color;
import java.awt.HeadlessException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import pacman.personaje.*;

/**
 *
 * @author edson
 */
public class GeneradorEntorno extends JFrame {

    // Laberinto
    private Laberinto LaberintoVirtual;
    private JLabel[][] LaberintoGrafico;
    private PanelEstado estado;

    //AudioClip start sirena;
    private final AudioClip start = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sonidos/Start.wav"));

    // Personajes
    private Pacman pacman;
    private Fantasma f1, f2, f3, f4, f5;

    // Panel de vidas y score
    private JLabel[] vidas;
    private JLabel score;
    private JLabel[] frutas;
    private JPanel panel;
    private JPanel panel2;
    private JPanel panel3;

    public GeneradorEntorno(int numLaberinto, int numLives, int numScore) throws HeadlessException {
        try {
            // configurar pantalla e instanciar paneles graficos
            setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            setSize(450, 450);
            this.setLayout(null);
            this.setBounds(0, 0, 416, 470);
            
            setVisible(true);

            vidas = new JLabel[7];
            score = new JLabel(Integer.toString(numScore));
            frutas = new JLabel[3];
            panel = new JPanel();
            panel2 = new JPanel();
            panel3 = new JPanel();

            this.setLocationRelativeTo(null);

            // Genera el laberinto grafico a partir del Laberinto virtual
            LaberintoVirtual = new Laberinto(numLaberinto);
            LaberintoGrafico = new JLabel[LaberintoVirtual.DevolverCantidadColumnasLaberinto()][LaberintoVirtual.DevolverCantidadFilasLaberinto()];

            // Instanciar personajes en el laberinto
            pacman = new Pacman(numLives, 18, 28, LaberintoGrafico, LaberintoVirtual, numLaberinto, numScore);
            f1 = new Fantasma(1, pacman, LaberintoVirtual, LaberintoGrafico, 16, 16, 80, numLaberinto);
            f2 = new Fantasma(2, pacman, LaberintoVirtual, LaberintoGrafico, 18, 16, 80, numLaberinto);
            f3 = new Fantasma(3, pacman, LaberintoVirtual, LaberintoGrafico, 20, 16, 80, numLaberinto);
            f4 = new Fantasma(4, pacman, LaberintoVirtual, LaberintoGrafico, 18, 14, 80, numLaberinto);
            f5 = new Fantasma(5, pacman, LaberintoVirtual, LaberintoGrafico, 14, 14, 80, numLaberinto);

            GenerarLaberintoGrafico(numLaberinto);
            estado = new PanelEstado(pacman, f1, f2, f3, f4, f5, vidas, frutas, LaberintoVirtual, LaberintoGrafico, this);
            start.play();
            Thread.sleep(4000);

            estado.sirena();
            pacman.Inicializar();
            fantasmaStart();
            estado.start();
        } catch (InterruptedException ex) {
            Logger.getLogger(GeneradorEntorno.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void GenerarLaberintoGrafico(int n) {

        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                switch (evt.getKeyCode()) {
                    case 37:
                        pacman.MovLeft();
                        break;
                    case 39:
                        pacman.MovRight();
                        break;
                    case 38:
                        pacman.MovUp();
                        break;
                    case 40:
                        pacman.MovDown();
                        break;
                }

                score.setText(Integer.toString(pacman.getScore()));

            }
        });

        panel.setBounds(0, 400, 150, 80);
        panel2.setBounds(150, 400, 100, 80);
        panel3.setBounds(250, 400, 150, 80);

        panel.setBackground(Color.BLACK);
        panel2.setBackground(Color.BLACK);
        panel3.setBackground(Color.BLACK);
        score.setForeground(Color.WHITE);
        setBackground(Color.BLACK);

        this.getContentPane().add(panel);
        this.getContentPane().add(panel2);
        this.getContentPane().add(panel3);

        panel2.add(score);
         //cargar panel
        for (int i = 0; i < 7; i++) {
            vidas[i] = new JLabel();
            panel.add(vidas[i]);
            vidas[i].setBounds(20 * i, 0, 20, 20);
            vidas[i].validate();
            if (i < 3) {
                frutas[i] = new JLabel();
                panel3.add(frutas[i]);
                frutas[i].setBounds(250 + (20 * i), 0, 20, 20);
                frutas[i].validate();
            }
        }
        // cargar laberinto
        for (int i = 0; i < LaberintoVirtual.DevolverCantidadColumnasLaberinto(); i++) {
            for (int j = 0; j < LaberintoVirtual.DevolverCantidadFilasLaberinto(); j++) {
                LaberintoGrafico[i][j] = new JLabel();
                add(LaberintoGrafico[i][j]);
                LaberintoGrafico[i][j].setIcon(new ImageIcon("Graficos/" + n + "/Laberinto/" + LaberintoVirtual.DeolverCodigoImagenMatriz(i, j) + ".gif"));
                if (LaberintoVirtual.LaberintoActual()[i][j] != "1" && LaberintoVirtual.LaberintoActual()[i][j] != "2" && LaberintoVirtual.LaberintoActual()[i][j] != "3" && LaberintoVirtual.LaberintoActual()[i][j] != "4") {
                    LaberintoGrafico[i][j].setOpaque(true);
                } else {
                    LaberintoGrafico[i][j].setOpaque(false);
                }

            }
        }

        for (int i = 0; i < LaberintoVirtual.DevolverCantidadColumnasLaberinto(); i++) {
            for (int j = 0; j < LaberintoVirtual.DevolverCantidadFilasLaberinto(); j++) {
                LaberintoGrafico[i][j].setBounds(i * LaberintoVirtual.DevolverLargoImagenes(), j * LaberintoVirtual.DevolverAlturaImagenes(), LaberintoVirtual.DevolverLargoImagenes(), LaberintoVirtual.DevolverAlturaImagenes());
                LaberintoGrafico[i][j].validate();
            }
        }

    }

    private void fantasmaStart() {
        f1.start();
        f2.start();
        f3.start();
        f4.start();
        f5.start();
    }

}
