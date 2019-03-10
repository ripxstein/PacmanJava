/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pacman.personaje;

import java.applet.AudioClip;
import javax.swing.ImageIcon;
import javax.swing.JLabel;
import pacman.entorno.Laberinto;

/**
 *
 * @author edson
 */
public class Pacman {
    private final AudioClip   waka = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sonidos/WakaWaka.wav"));
    private final AudioClip   point = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sonidos/point.wav"));
    private final AudioClip   eatf = java.applet.Applet.newAudioClip(getClass().getResource("/pacman/sonidos/Eatche.wav"));
    private int vidas;
    private int score;
    private int y,x,b;
    private int dx,dy;
    private int nu;
    private JLabel[][] LaberintoGrafico;
    private Laberinto lab;
    private String[][] cordenadas;
    private boolean activo=true;
    
    public Pacman(int vidas,int x, int y,JLabel[][] entorno, Laberinto lab,int n,int s) {
        
        this.vidas = vidas;
        this.score = s;
        this.LaberintoGrafico=entorno;
        this.dy=y;
        this.dx=x;
        this.activo=true;
        this.lab=lab;
        this.cordenadas=lab.LaberintoActual();
        this.nu=n;
       
               
    }
    public void MovRight()
    {
        int n=1;
        if(x+2==lab.DevolverCantidadColumnasLaberinto())
        {
            for(int i=y;i<y+2;i++)
                    {
                        for(int j=x;j<x+2;j++)
                        {
                            Borrar(j,i); 
                        } 
                        
                        for(int j=0;j<2;j++)
                        {
                             LaberintoGrafico[j][i].setOpaque(false);
                            if(j==1)
                            {
                               
                               Colocar(j,i,n,true,true,true);
                            }
                            else
                            {
                             
                               Colocar(j,i,n,true,true,false);
                            }
                             
                            n++;
                            
                        }
                    }
                   
                    x=0;
        }
        else if(cordenadas[x+2][y]=="1")
        {
            point.play();
            this.activo=false;
            lab.LaberintoActual()[x+2][y]="CA";
            lab.LaberintoActual()[x+3][y]="CA";
            lab.LaberintoActual()[x+2][y+1]="CA";
            lab.LaberintoActual()[x+3][y+1]="CA";
            MovRight();
            MovRight();
        }
        else if(cordenadas[x+2][y].equalsIgnoreCase("o1")||cordenadas[x+2][y].equalsIgnoreCase("a1")||cordenadas[x+2][y].equalsIgnoreCase("c1"))
        {
            
            eatf.play();
            score+=100;
            lab.LaberintoActual()[x+2][y]="CA";
            lab.LaberintoActual()[x+3][y]="CA";
            lab.LaberintoActual()[x+2][y+1]="CA";
            lab.LaberintoActual()[x+3][y+1]="CA";
            MovRight();
            MovRight();
        }
        else if(cordenadas[x+2][y]=="CA" && cordenadas[x+2][y+1]=="CA" )
        {
            puntaje(x+2,y,x+2,y+1);     
            MuDerecho();
            x++;
        }
        
        
      b=b^1;      
        
    }
    public void MovLeft()
    {
        int n=1;
        
        if(x==0)
        {
            for(int i=y;i<y+2;i++)
                    {
                        for(int j=0;j<2;j++)
                        {
                            Borrar(j,i);
                        } 
                        
                        for(int j=LaberintoGrafico[0].length-2;j<LaberintoGrafico[0].length;j++)
                        {
                             LaberintoGrafico[j][i].setOpaque(false);
                            if(j==LaberintoGrafico[0].length-2)
                            {
                               
                               
                               Colocar(j,i,n,true,false,true);
                            }
                            else
                            {
                               
                                Colocar(j,i,n,true,false,false);
                            }
                             
                            n++;
                            
                        }
                    }
                    x=LaberintoGrafico[0].length-2;
        }
        else if(cordenadas[x-1][y]=="2")
        {
            point.play();
            this.activo=false;
            lab.LaberintoActual()[x-1][y]="CA";
            lab.LaberintoActual()[x-2][y]="CA";
            lab.LaberintoActual()[x-1][y+1]="CA";
            lab.LaberintoActual()[x-2][y+1]="CA";
            MovLeft();
            MovLeft();
        }
        else if(cordenadas[x-1][y].equalsIgnoreCase("o2")||cordenadas[x-1][y].equalsIgnoreCase("a2")||cordenadas[x-1][y].equalsIgnoreCase("c2"))
        {
            eatf.play();
            score+=100;
            lab.LaberintoActual()[x-1][y]="CA";
            lab.LaberintoActual()[x-2][y]="CA";
            lab.LaberintoActual()[x-1][y+1]="CA";
            lab.LaberintoActual()[x-2][y+1]="CA";
            MovLeft();
            MovLeft();
        }
        else if(cordenadas[x-1][y]=="CA" && cordenadas[x-1][y+1]=="CA")
        {
            puntaje(x-1,y,x-1,y+1);
            MuIzquierdo();    
            x--;
        }
        
       b=b^1;   
            
       
    }
    public void MovUp()
    {
        int n=1;
        if(y==0)
        {
           for(int j=x;j<x+2;j++)
                    {
                        for(int i=0;i<2;i++)
                        {
                             Borrar(j,i);
                        }
                           
                        for(int i=LaberintoGrafico.length-2;i<LaberintoGrafico.length;i++)
                        {
                            LaberintoGrafico[j][i].setOpaque(false); 
                            if(i==LaberintoGrafico.length-2)
                            {
                            
                               Colocar(j,i,n,false,true,true);
                            }
                            else
                            {
                               
                               Colocar(j,i,n,false,true,false);
                            }
                            
                            n++;
                        }
                    }
                    y=LaberintoGrafico.length-2;
        }
        else if(cordenadas[x][y-1]=="3")
        {
            point.play();
            this.activo=false;
            lab.LaberintoActual()[x][y-1]="CA";
            lab.LaberintoActual()[x][y-2]="CA";
            lab.LaberintoActual()[x+1][y-1]="CA";
            lab.LaberintoActual()[x+1][y-2]="CA";
            MovUp();
            MovUp();
        }
        else if(cordenadas[x][y-1].equalsIgnoreCase("o3")||cordenadas[x][y-1].equalsIgnoreCase("a3")||cordenadas[x][y-1].equalsIgnoreCase("c3"))
        {
            eatf.play();
            score+=100;
            lab.LaberintoActual()[x][y-1]="CA";
            lab.LaberintoActual()[x][y-2]="CA";
            lab.LaberintoActual()[x+1][y-1]="CA";
            lab.LaberintoActual()[x+1][y-2]="CA";
            MovUp();
            MovUp();
        }
        else if(cordenadas[x][y-1]=="CA" && cordenadas[x+1][y-1]=="CA")
        {
                puntaje(x,y-1,x+1,y-1);
                MuArriba();    
                y--;
        }
        
        b=b^1;      
        
    }
    
    public void MovDown()
    {
        int n=1;
         if(y+2==lab.DevolverCantidadFilasLaberinto())
        {
            for(int j=x;j<x+2;j++)
                    {
                        for(int i=LaberintoGrafico.length-2;i<LaberintoGrafico.length;i++)
                        {
                          Borrar(j,i);  
                        }
                        
                        for(int i=0;i<2;i++)
                        {
                            LaberintoGrafico[j][i].setOpaque(false); 
                            if(i==1)
                            {
                                
                               Colocar(j,i,n,false,false,true);
                            }
                            else
                            {
                               
                               Colocar(j,i,n,false,false,false);
                            }
                            
                            n++;
                        }
                    }
                    y=0;
        }
        else if(cordenadas[x][y+2]=="1")
        {
            point.play();
            this.activo=false;
            lab.LaberintoActual()[x][y+2]="CA";
            lab.LaberintoActual()[x][y+3]="CA";
            lab.LaberintoActual()[x+1][y+2]="CA";
            lab.LaberintoActual()[x+1][y+3]="CA";
            MovDown();
            MovDown();
        }
        else if(cordenadas[x][y+2].equalsIgnoreCase("o1")||cordenadas[x][y+2].equalsIgnoreCase("a1")||cordenadas[x][y+2].equalsIgnoreCase("c1"))
        {
            eatf.play();
            score+=100;
            lab.LaberintoActual()[x][y+2]="CA";
            lab.LaberintoActual()[x][y+3]="CA";
            lab.LaberintoActual()[x+1][y+2]="CA";
            lab.LaberintoActual()[x+1][y+3]="CA";
            MovDown();
            MovDown();
        }
        else if(cordenadas[x][y+2]=="CA" && cordenadas[x+1][y+2]=="CA")
        {
            puntaje(x,y+2,x+1,y+2);
            MuAbajo();   
            y++;
        }
        
        b=b^1;      
        
    }

    public int getVidas() {
        return vidas;
    }

    public int getScore() {
        return score;
    }

    public int getY() {
        return y;
    }

    public int getX() {
        return x;
    }
    public boolean Perder()
    {
        if(vidas!=0)
        {
            vidas--;
            return true;
        }
        else
        {
            return false;
        }
        
    }

    
    private void puntaje(int x,int y,int x2,int y2){
        
        if((LaberintoGrafico[x][y].isOpaque()==true || LaberintoGrafico[x2][y2].isOpaque()==true))
        {
           LaberintoGrafico[x][y].setOpaque(false);
           LaberintoGrafico[x2][y2].setOpaque(false);
           score++;
           waka.play();
        }
        
        
    }
    public void Inicializar(){
       int n=1;
       x=dx;
       y=dy;
       b=0;
       for(int i=y;i<y+2;i++)
        {
            for(int j=x;j<x+2;j++)
            {
               LaberintoGrafico[j][i].setOpaque(false);
               
               if(j==x+1)
                            {
                                Colocar(j,i,n,true,true,true);
                            }
                            else
                            {
                               Colocar(j,i,n,true,true,false);
                            }
               b=b^1;
               n++;
            }
            
        } 
    }

    public void setActivo(boolean activo) {
        this.activo = activo;
    }

    public boolean isActivo() {
        return activo;
    }
    
    public void Colocar(int x,int y,int n,boolean h,boolean v,boolean ban){
        if(h==true)
        {
            if(v==true)
            {
                if(ban==true)
                {
                    LaberintoGrafico[x][y].setIcon(new ImageIcon("Graficos/"+nu+"/Pacman/D_" +n +b+ ".gif"));
                }
                else
                {
                    LaberintoGrafico[x][y].setIcon(new ImageIcon("Graficos/"+nu+"/Pacman/D_" + n + ".gif")); 
                }
                
            }
            else
            {
                if(ban==true)
                {
                    LaberintoGrafico[x][y].setIcon(new ImageIcon("Graficos/"+nu+"/Pacman/I_" +n +b+ ".gif"));
                }
                else
                {
                    LaberintoGrafico[x][y].setIcon(new ImageIcon("Graficos/"+nu+"/Pacman/I_" + n + ".gif")); 
                }
            }
        }
        else
        {
            if(v==true)
            {
                if(ban==true)
                {
                   LaberintoGrafico[x][y].setIcon(new ImageIcon("Graficos/"+nu+"/Pacman/AR_" +n +b+ ".gif"));  
                }
                else
                {
                    LaberintoGrafico[x][y].setIcon(new ImageIcon("Graficos/"+nu+"/Pacman/AR_" + n + ".gif")); 
                }
            }
            else
            {
                if(ban==true)
                {
                    LaberintoGrafico[x][y].setIcon(new ImageIcon("Graficos/"+nu+"/Pacman/AB_" +n +b+ ".gif"));
                }
                else
                {
                    LaberintoGrafico[x][y].setIcon(new ImageIcon("Graficos/"+nu+"/Pacman/AB_" + n + ".gif"));
                }
            }
            
        }
        
    }
    public void Borrar(int i, int j){
        LaberintoGrafico[i][j].setIcon(new ImageIcon("Graficos/"+nu+"/Laberinto/" + "CB" + ".gif"));
    }
    public void MuDerecho(){
         int n=1;
         for(int i=y;i<y+2;i++)
                    {
                        
                        Borrar(x,i); 
                        
         
                
                        for(int j=x+1;j<x+3;j++)
                        {
                            if(j==x+2)
                            {
                               
                               Colocar(j,i,n,true,true,true);
                            }
                            else
                            {
                               
                               Colocar(j,i,n,true,true,false);
                            }
                             
                            n++;
                            
                        }
                        
                    }
    }
    public void MuIzquierdo(){
        int n=1;
        for(int i=y;i<y+2;i++)
                    {
                            Borrar(x+1,i);
                        
                        for(int j=x-1;j<x+1;j++)
                        {
                            if(j==x-1)
                            {
                               
                               Colocar(j,i,n,true,false,true);
                            }
                            else
                            {
                              
                               Colocar(j,i,n,true,false,false);
                            }
                            
                            n++;
                        }
                    } 
    }
    public void MuArriba(){
        int n=1;
        for(int j=x;j<x+2;j++)
                    {
                         Borrar(j,y+1);   
                        
                        
                        for(int i=y-1;i<y+1;i++)
                        {
                             
                            if(i==y-1)
                            {
                               
                                Colocar(j,i,n,false,true,true);
                            }
                            else
                            {
                              
                                Colocar(j,i,n,false,true,false);
                            }
                            
                            n++;
                        }
                    }
    }
    public void MuAbajo(){
        int n=1;
        for(int j=x;j<x+2;j++)
                    {
                        Borrar(j,y);   
                         
                        
                        for(int i=y+1;i<y+3;i++)
                        {
                             
                            if(i==y+2)
                            {
                                
                               Colocar(j,i,n,false,false,true);
                            }
                            else
                            {
                               
                               Colocar(j,i,n,false,false,false);
                            }
                            
                            n++;
                        }
                    }
                     
    }

    public void setScore(int score) {
        this.score += score;
    }
    
}
