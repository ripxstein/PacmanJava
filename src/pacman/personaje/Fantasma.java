/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.personaje;

import java.applet.AudioClip;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import pacman.entorno.Laberinto;

/**
 *
 * @author edson
 */
public class Fantasma extends Thread {

    private final AudioClip Stop = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sonidos/Stop.wav"));
    private final AudioClip gameO = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sonidos/GameOver.wav"));
    private final AudioClip perse = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sonidos/Persecucion.wav"));
    private final AudioClip eat = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sonidos/eatghost.wav"));
    private int n, nu;
    private int x, y;
    private int dx, dy;
    private int tiempo, dtiempo;
    private Pacman pacman;
    private Laberinto lab;
    private JLabel[][] entorno;
    private int mov;
    private boolean activo;
    private boolean vivo;
    private boolean ban;

    public Fantasma(int n, Pacman pac, Laberinto lab, JLabel[][] entorno, int x, int y, int timer, int nu) {
        this.n = n;
        this.dx = x;
        this.dy = y;
        this.pacman = pac;
        this.lab = lab;
        this.entorno = entorno;
        this.dtiempo = timer;
        this.nu = nu;
        this.vivo = true;
        this.mov = 0;

    }

    @Override
    public void run() {

        Inicializar();
        try {
            MoverseAl();
        } catch (InterruptedException ex) {
            Logger.getLogger(Fantasma.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public void Inicializar() {
        activo = false;
        int l = 1;
        tiempo = dtiempo;

        x = dx;
        y = dy;
        for (int i = y; i < y + 2; i++) {
            for (int j = x; j < x + 2; j++) {

                Colocar(j, i, l, true);
                l++;
            }
        }
    }

    private void MoverseAl() throws InterruptedException {
        int direccion;
        Random rnd = new Random();
        ban = true;
        while (vivo) {
            Activo();
            if (Localizar() == false) {
                direccion = (int) (rnd.nextDouble() * 4 + 1);
                switch (direccion) {
                    case 1:
                        MovRight();
                        break;
                    case 2:
                        MovLeft();
                        break;
                    case 3:
                        MovUp();
                        break;
                    case 4:
                        MovDown();
                        break;
                }
            }

            Comprobar();
            Thread.sleep(tiempo);
        }

    }

    public void Activo() {
        if (pacman.isActivo() == false && ban == true) {
            activo = true;
            ban = false;
            perse.loop();
        }
        if (activo == true) {
            mov++;
            if (tiempo > 40) {
                tiempo--;
            }
            if (mov == 450) {
                activo = false;
                mov = 0;
                ban = true;
                pacman.setActivo(true);
                this.tiempo = this.dtiempo;
            }
        }
    }

    public void MovRight() {
        int l = 1;
        boolean camino[];
        camino = valido(x + 2, y, true);
        if (camino[0] == true) {

            for (int i = y; i < y + 2; i++) {

                Borrar(x, i);

                for (int j = x + 1; j < x + 3; j++) {

                    Colocar(j, i, l, true);

                    l++;

                }

            }
            x++;

        } else if (camino[1] == true) {
            MuDerecho();
        }
    }

    public void MovLeft() {
        int l = 1;
        boolean camino[];
        camino = valido(x - 1, y, true);
        if (camino[0] == true) {
            for (int i = y; i < y + 2; i++) {
                Borrar(x + 1, i);

                for (int j = x - 1; j < x + 1; j++) {

                    Colocar(j, i, l, true);

                    l++;
                }
            }

            x--;
        } else if (camino[1] == true) {
            MuIzquierdo();
        }

    }

    public void MovUp() {
        int l = 1;
        boolean camino[];
        camino = valido(x, y - 1, false);
        if (camino[0] == true) {
            for (int j = x; j < x + 2; j++) {
                Borrar(j, y + 1);

                for (int i = y - 1; i < y + 1; i++) {

                    Colocar(j, i, l, false);

                    l++;
                }
            }

            y--;

        } else if (camino[1] == true) {
            MuArriba();
        }

    }

    public void MovDown() {
        int l = 1;
        boolean camino[];
        camino = valido(x, y + 2, false);
        if (camino[0] == true) {
            for (int j = x; j < x + 2; j++) {
                Borrar(j, y);

                for (int i = y + 1; i < y + 3; i++) {

                    Colocar(j, i, l, false);

                    l++;
                }
            }

            y++;
        } else if (camino[1] == true) {
            MuAbajo();
        }
    }

    private boolean[] valido(int x, int y, boolean b) {
        boolean[] bandera = {false, false};
        if (b == true) {
            if (x == lab.DevolverCantidadColumnasLaberinto() || x == -1) {
                bandera[1] = true;
            } else if (lab.LaberintoActual()[x][y] == "CA" && lab.LaberintoActual()[x][y + 1] == "CA" || lab.LaberintoActual()[x][y] == "1" || lab.LaberintoActual()[x][y] == "2" || lab.LaberintoActual()[x][y] == "3" || lab.LaberintoActual()[x][y] == "4" || lab.LaberintoActual()[x][y] == "a1" || lab.LaberintoActual()[x][y] == "a2" || lab.LaberintoActual()[x][y] == "a3" || lab.LaberintoActual()[x][y] == "a4" || lab.LaberintoActual()[x][y] == "c1" || lab.LaberintoActual()[x][y] == "c2" || lab.LaberintoActual()[x][y] == "c3" || lab.LaberintoActual()[x][y] == "c4" || lab.LaberintoActual()[x][y] == "o1" || lab.LaberintoActual()[x][y] == "o2" || lab.LaberintoActual()[x][y] == "o3" || lab.LaberintoActual()[x][y] == "o4") {
                bandera[0] = true;
            }

        } else {
            if (y == lab.DevolverCantidadFilasLaberinto() || y == -1) {
                bandera[1] = true;
            } else if (lab.LaberintoActual()[x][y] == "CA" && lab.LaberintoActual()[x + 1][y] == "CA" || lab.LaberintoActual()[x][y] == "1" || lab.LaberintoActual()[x][y] == "2" || lab.LaberintoActual()[x][y] == "3" || lab.LaberintoActual()[x][y] == "4" || lab.LaberintoActual()[x][y] == "a1" || lab.LaberintoActual()[x][y] == "a2" || lab.LaberintoActual()[x][y] == "a3" || lab.LaberintoActual()[x][y] == "a4" || lab.LaberintoActual()[x][y] == "c1" || lab.LaberintoActual()[x][y] == "c2" || lab.LaberintoActual()[x][y] == "c3" || lab.LaberintoActual()[x][y] == "c4" || lab.LaberintoActual()[x][y] == "o1" || lab.LaberintoActual()[x][y] == "o2" || lab.LaberintoActual()[x][y] == "o3" || lab.LaberintoActual()[x][y] == "o4") {
                bandera[0] = true;
            }

        }
        return bandera;
    }

    // pasar de un muro a otro cuando este libre

    public void MuDerecho() {
        int l = 1;

        for (int i = y; i < y + 2; i++) {
            for (int j = x; j < x + 2; j++) {

                Borrar(j, i);
            }

            for (int j = 0; j < 2; j++) {

                Colocar(j, i, l, true);

                l++;

            }
        }

        x = 0;
    }

    public void MuIzquierdo() {
        int l = 1;
        for (int i = y; i < y + 2; i++) {
            for (int j = 0; j < 2; j++) {
                Borrar(j, i);
            }

            for (int j = entorno[0].length - 2; j < entorno[0].length; j++) {

                Colocar(j, i, l, true);

                l++;

            }
        }
        x = entorno[0].length - 2;
    }

    public void MuArriba() {
        int l = 1;
        for (int j = x; j < x + 2; j++) {
            for (int i = 0; i < 2; i++) {
                Borrar(j, i);
            }

            for (int i = entorno.length - 2; i < entorno.length; i++) {

                Colocar(j, i, l, false);

                l++;
            }
        }
        y = entorno.length - 2;

    }

    public void MuAbajo() {
        int l = 1;
        for (int j = x; j < x + 2; j++) {
            for (int i = entorno.length - 2; i < entorno.length; i++) {
                Borrar(j, i);
            }

            for (int i = 0; i < 2; i++) {

                Colocar(j, i, l, false);

                l++;
            }
        }
        y = 0;

    }

    public void Borrar(int i, int j) {
        if (lab.LaberintoActual()[i][j] == "1") {
            entorno[i][j].setIcon(new ImageIcon("Graficos/" + nu + "/Laberinto/" + "1" + ".gif"));
        } else if (lab.LaberintoActual()[i][j] == "2") {
            entorno[i][j].setIcon(new ImageIcon("Graficos/" + nu + "/Laberinto/" + "2" + ".gif"));
        } else if (lab.LaberintoActual()[i][j] == "3") {
            entorno[i][j].setIcon(new ImageIcon("Graficos/" + nu + "/Laberinto/" + "3" + ".gif"));
        } else if (lab.LaberintoActual()[i][j] == "4") {
            entorno[i][j].setIcon(new ImageIcon("Graficos/" + nu + "/Laberinto/" + "4" + ".gif"));
        } else if (lab.LaberintoActual()[i][j] == "a1") {
            entorno[i][j].setIcon(new ImageIcon("Graficos/" + nu + "/Laberinto/Frutas/" + "apple_01" + ".png"));
        } else if (lab.LaberintoActual()[i][j] == "a2") {
            entorno[i][j].setIcon(new ImageIcon("Graficos/" + nu + "/Laberinto/Frutas/" + "apple_02" + ".png"));
        } else if (lab.LaberintoActual()[i][j] == "a3") {
            entorno[i][j].setIcon(new ImageIcon("Graficos/" + nu + "/Laberinto/Frutas/" + "apple_03" + ".png"));
        } else if (lab.LaberintoActual()[i][j] == "a4") {
            entorno[i][j].setIcon(new ImageIcon("Graficos/" + nu + "/Laberinto/Frutas/" + "apple_04" + ".png"));
        } else if (lab.LaberintoActual()[i][j] == "c1") {
            entorno[i][j].setIcon(new ImageIcon("Graficos/" + nu + "/Laberinto/Frutas/" + "cherry_01" + ".png"));
        } else if (lab.LaberintoActual()[i][j] == "c2") {
            entorno[i][j].setIcon(new ImageIcon("Graficos/" + nu + "/Laberinto/Frutas/" + "cherry_02" + ".png"));
        } else if (lab.LaberintoActual()[i][j] == "c3") {
            entorno[i][j].setIcon(new ImageIcon("Graficos/" + nu + "/Laberinto/Frutas/" + "cherry_03" + ".png"));
        } else if (lab.LaberintoActual()[i][j] == "c4") {
            entorno[i][j].setIcon(new ImageIcon("Graficos/" + nu + "/Laberinto/Frutas/" + "cherry_04" + ".png"));
        } else if (lab.LaberintoActual()[i][j] == "o1") {
            entorno[i][j].setIcon(new ImageIcon("Graficos/" + nu + "/Laberinto/Frutas/" + "orange_01" + ".png"));
        } else if (lab.LaberintoActual()[i][j] == "o2") {
            entorno[i][j].setIcon(new ImageIcon("Graficos/" + nu + "/Laberinto/Frutas/" + "orange_02" + ".png"));
        } else if (lab.LaberintoActual()[i][j] == "o3") {
            entorno[i][j].setIcon(new ImageIcon("Graficos/" + nu + "/Laberinto/Frutas/" + "orange_03" + ".png"));
        } else if (lab.LaberintoActual()[i][j] == "o4") {
            entorno[i][j].setIcon(new ImageIcon("Graficos/" + nu + "/Laberinto/Frutas/" + "orange_04" + ".png"));
        } else if (entorno[i][j].isOpaque() == true) {
            entorno[i][j].setIcon(new ImageIcon("Graficos/" + nu + "/Laberinto/" + "CA" + ".gif"));
        } else if (entorno[i][j].isOpaque() == false) {
            entorno[i][j].setIcon(new ImageIcon("Graficos/" + nu + "/Laberinto/" + "CB" + ".gif"));
        }

    }

    // Seguimiento de pacman
    private boolean Localizar() throws InterruptedException {
        int run;

        boolean[] mov = {false, false, false, false, false};
        if (pacman.getY() == y) {
            if (pacman.getX() <= x) {
                for (int i = pacman.getX(); i <= x; i++) {

                    if (lab.LaberintoActual()[i][y] != "CA") {
                        break;
                    } else if (i == x) {
                        run = x - pacman.getX();
                        for (int j = 0; j < run; j++) {
                            Activo();
                            if (this.activo == true) {
                                if (x + 2 == lab.DevolverCantidadColumnasLaberinto()) {
                                    MuDerecho();
                                } else if (lab.LaberintoActual()[x + 2][y] == "CA") {
                                    MovRight();

                                } else if (lab.LaberintoActual()[x][y - 1] == "CA") {
                                    MovUp();
                                } else if (lab.LaberintoActual()[x][y + 2] == "CA") {
                                    MovDown();
                                }
                                return false;
                            } else {
                                MovLeft();
                            }

                            Comprobar();
                            Thread.sleep(140);
                        }

                        return true;
                    }

                }
            } else {
                for (int i = x; i <= pacman.getX(); i++) {
                    if (lab.LaberintoActual()[i][y] != "CA") {
                        break;
                    } else if (i == pacman.getX()) {
                        run = pacman.getX() - x;
                        for (int j = 0; j < run; j++) {
                            Activo();
                            if (this.activo == true) {
                                if (x == 0) {
                                    MuIzquierdo();
                                } else if (lab.LaberintoActual()[x - 1][y] == "CA") {
                                    MovLeft();
                                } else if (lab.LaberintoActual()[x][y - 1] == "CA") {
                                    MovUp();
                                } else if (lab.LaberintoActual()[x][y + 2] == "CA") {
                                    MovDown();
                                }
                                return false;
                            } else {
                                MovRight();
                            }

                            Comprobar();
                            Thread.sleep(140);
                        }

                        return true;
                    }

                }
            }
        } else if (pacman.getX() == x) {
            if (pacman.getY() <= y) {
                for (int i = pacman.getY(); i <= y; i++) {

                    if (lab.LaberintoActual()[x][i] != "CA") {
                        break;
                    } else if (i == y) {
                        run = y - pacman.getY();
                        for (int j = 0; j < run; j++) {
                            Activo();
                            if (this.activo == true) {
                                if (y + 2 == lab.DevolverCantidadFilasLaberinto()) {
                                    MuAbajo();
                                } else if (lab.LaberintoActual()[x][y + 2] == "CA") {
                                    MovDown();
                                } else if (lab.LaberintoActual()[x + 2][y] == "CA") {
                                    MovRight();
                                } else if (lab.LaberintoActual()[x - 1][y] == "CA") {
                                    MovLeft();
                                }
                                return false;
                            } else {
                                MovUp();
                            }

                            Comprobar();
                            Thread.sleep(140);
                        }

                        return true;
                    }

                }
            } else {
                for (int i = y; i <= pacman.getY(); i++) {

                    if (lab.LaberintoActual()[x][i] != "CA") {
                        break;
                    } else if (i == pacman.getY()) {
                        run = pacman.getY() - y;
                        for (int j = 0; j < run; j++) {
                            Activo();
                            if (this.activo == true) {
                                if (y == 0) {
                                    MuArriba();
                                } else if (lab.LaberintoActual()[x][y - 1] == "CA") {
                                    MovUp();
                                } else if (x != lab.DevolverCantidadFilasLaberinto() && lab.LaberintoActual()[x + 2][y] == "CA") {
                                    MovRight();
                                } else if (x != 0 && lab.LaberintoActual()[x - 1][y] == "CA") {
                                    MovLeft();
                                }
                                return false;
                            } else {
                                MovDown();
                            }
                            Comprobar();
                            Thread.sleep(140);

                        }

                        return true;
                    }

                }
            }

        }

        return false;
    }

    private void Colocar(int x, int y, int l, boolean h) {
        if (h == true) {
            if (this.activo == false) {
                entorno[x][y].setIcon(new ImageIcon("Graficos/" + nu + "/Fantasmas/fanth" + n + "_0" + l + ".png"));
            } else {
                entorno[x][y].setIcon(new ImageIcon("Graficos/" + nu + "/Fantasmas/fanthe_0" + l + ".png"));
            }
        } else {
            if (this.activo == false) {
                entorno[x][y].setIcon(new ImageIcon("Graficos/" + nu + "/Fantasmas/fantv" + n + "_0" + l + ".png"));
            } else {
                entorno[x][y].setIcon(new ImageIcon("Graficos/" + nu + "/Fantasmas/fanthev_0" + l + ".png"));
            }
        }

    }

    public void Comprobar() {
        if (activo == false) {
            ban = true;
            perse.stop();
        }
        if (((pacman.getX() + 1 == x || pacman.getX() + 1 == x + 1 || pacman.getX() == x || pacman.getX() == x + 1)) && ((pacman.getY() + 1 == y || pacman.getY() + 1 == y + 1 || pacman.getY() == y || pacman.getY() == y + 1))) {
            if (activo == false) {

                for (int i = pacman.getX(); i < pacman.getX() + 2; i++) {
                    for (int j = pacman.getY(); j < pacman.getY() + 2; j++) {
                        pacman.Borrar(i, j);

                    }
                }
                if (pacman.Perder() == true) {
                    Stop.play();
                    pacman.Inicializar();
                }

            } else {
                eat.play();
                pacman.setActivo(true);
                pacman.setScore(20);
                for (int i = x; i < x + 2; i++) {
                    for (int j = y; j < y + 2; j++) {
                        Borrar(i, j);
                    }

                }
                this.mov = 0;
                this.activo = false;
                Inicializar();
            }

        }
    }

    public void setVivo(boolean vivo) {
        perse.stop();
        this.vivo = vivo;
    }

    public void Desaparecer() {

        for (int i = x; i < x + 2; i++) {
            for (int j = y; j < y + 2; j++) {
                Borrar(i, j);
            }
        }
    }

}
