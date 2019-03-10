/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.entorno;

import java.applet.AudioClip;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import pacman.personaje.Fantasma;
import pacman.personaje.Pacman;
import pacman.recursos.NombreVentana;

/**
 *
 * @author edson
 */
class PanelEstado extends Thread {
    private final AudioClip gameO = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sonidos/GameOver.wav"));
    private final AudioClip newlev = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sonidos/NewLev.wav"));
    private final AudioClip siren = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sonidos/Siren.wav"));
    private int nfrut;
    private boolean activo;
    private final Pacman pacman;
    private final Fantasma f1, f2, f3, f4, f5;
    private final JLabel[] vidas;
    private final JLabel[] frut;
    private final Laberinto LaberintoVirtual;
    private final JLabel[][] LaberintoGrafico;
    private final JFrame frame;

    public PanelEstado(Pacman pacman, Fantasma f1, Fantasma f2, Fantasma f3, Fantasma f4, Fantasma f5, JLabel[] vidas, JLabel[] frut, Laberinto lab, JLabel[][] grafico, JFrame frame) {
        activo = true;
        this.pacman = pacman;
        this.f1 = f1;
        this.f2 = f2;
        this.f3 = f3;
        this.f4 = f4;
        this.f5 = f5;
        this.vidas = vidas;
        this.frut = frut;
        this.LaberintoVirtual = lab;
        this.LaberintoGrafico = grafico;
        this.frame = frame;
        this.nfrut = 3;
    }

    @Override
    public void run() {

        CargarV();
        CargarF();
        int v = pacman.getVidas();
        int s = pacman.getScore();
        frame.repaint();
        while (activo) {
            boolean ban = false;

            if (v != pacman.getVidas()) {

                ResetFant();
                CargarV();
                frame.repaint();

            }
            if (s != pacman.getScore()) {
                if (pacman.getScore() == 200 || pacman.getScore() == 300 || pacman.getScore() == 500 || pacman.getScore() == 700 || pacman.getScore() == 900) {

                    for (int i = 10; i < 200; i++) {
                        for (int j = 10; j < 200; j++) {
                            if (LaberintoVirtual.LaberintoActual()[i][j] == "CA" && LaberintoVirtual.LaberintoActual()[i + 1][j] == "CA" && LaberintoVirtual.LaberintoActual()[i][j + 1] == "CA" && LaberintoVirtual.LaberintoActual()[i + 1][j + 1] == "CA") {
                                AgregarF(i, j);
                                ban = true;
                                break;
                            }
                        }
                        if (ban == true) {
                            break;
                        }
                    }
                }

            }

            if (pacman.getVidas() == 0) {
                //Perder----------------------  
                siren.stop();
                gameO.play();
                DetenerFant();
                frame.dispose();
                frame.remove(frame);
                this.activo = false;
                new NombreVentana(pacman.getScore()).setVisible(true);

            } else if (Ganar() == true) {

                DetenerFant();
                siren.stop();
                frame.dispose();
                frame.remove(frame);
                this.activo = false;

                if (Laberinto.n == 2) {
                    // Ganar
                    JOptionPane.showMessageDialog(null, "Felicidades has ganado----");
                    new NombreVentana(pacman.getScore()).setVisible(true);
                } else {
                    //Nuevo nivel----------------------  

                    newlev.play();

                    new GeneradorEntorno(Laberinto.n + 1, pacman.getVidas(), pacman.getScore());

                }
            }

            v = pacman.getVidas();
            s = pacman.getScore();

            try {
                Thread.sleep(100);
            } catch (InterruptedException ex) {
                Logger.getLogger(PanelEstado.class.getName()).log(Level.SEVERE, null, ex);
            }

        }

    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public void ResetFant() {
        f1.Desaparecer();
        f2.Desaparecer();
        f3.Desaparecer();
        f4.Desaparecer();
        f5.Desaparecer();
        f1.Inicializar();
        f2.Inicializar();
        f3.Inicializar();
        f4.Inicializar();
        f5.Inicializar();

    }

    private void DetenerFant() {
        f1.setVivo(false);
        f2.setVivo(false);
        f3.setVivo(false);
        f4.setVivo(false);
        f5.setVivo(false);
    }

    private boolean Ganar() {
        int n = 0;
        for (int i = 0; i < LaberintoVirtual.DevolverCantidadColumnasLaberinto(); i++) {
            for (int j = 0; j < LaberintoVirtual.DevolverCantidadFilasLaberinto(); j++) {

                if ((LaberintoVirtual.LaberintoFinal()[i][j] == LaberintoGrafico[i][j].isOpaque()) && LaberintoVirtual.LaberintoActual()[i][j] != "1" && LaberintoVirtual.LaberintoActual()[i][j] != "2" && LaberintoVirtual.LaberintoActual()[i][j] != "3" && LaberintoVirtual.LaberintoActual()[i][j] != "4") {
                    n++;
                }
            }

        }
        return n == 1584;

    }

    public void CargarV() {
        for (JLabel vida : vidas) {
            vida.setIcon(null);
        }
        for (int i = 0; i < pacman.getVidas(); i++) {
            vidas[i].setIcon(new ImageIcon("Graficos/P.png"));
        }
    }

    public void CargarF() {
        for (JLabel frut1 : frut) {
            frut1.setIcon(null);
        }

        if (nfrut == 3) {
            frut[0].setIcon(new ImageIcon("Graficos/frut/apple.png"));
            frut[1].setIcon(new ImageIcon("Graficos/frut/cherry.png"));
            frut[2].setIcon(new ImageIcon("Graficos/frut/orange.png"));
        } else if (nfrut == 2) {
            frut[0].setIcon(new ImageIcon("Graficos/frut/apple.png"));
            frut[1].setIcon(new ImageIcon("Graficos/frut/cherry.png"));
        } else if (nfrut == 1) {
            frut[0].setIcon(new ImageIcon("Graficos/frut/apple.png"));
        }

    }

    public void sirena() {
        siren.loop();
    }

    public void AgregarF(int x, int y) {
        int mu = 1;
        for (int j = y; j < y + 2; j++) {
            for (int i = x; i < x + 2; i++) {

                switch (nfrut) {
                    case 1:
                        LaberintoGrafico[i][j].setIcon(new ImageIcon("Graficos/" + Laberinto.n + "/Frutas/apple_0" + mu + ".png"));
                        LaberintoVirtual.LaberintoActual()[i][j] = "a" + mu;
                        break;
                    case 2:
                        LaberintoGrafico[i][j].setIcon(new ImageIcon("Graficos/" + Laberinto.n + "/Frutas/cherry_0" + mu + ".png"));
                        LaberintoVirtual.LaberintoActual()[i][j] = "c" + mu;
                        break;
                    case 3:
                        LaberintoGrafico[i][j].setIcon(new ImageIcon("Graficos/" + Laberinto.n + "/Frutas/orange_0" + mu + ".png"));
                        LaberintoVirtual.LaberintoActual()[i][j] = "o" + mu;
                        break;
                }
                mu++;
            }

        }
        nfrut--;
        CargarF();
    }

}
