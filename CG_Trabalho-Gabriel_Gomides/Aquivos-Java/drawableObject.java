/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projetocg;

import java.awt.image.BufferedImage;
import java.io.*;
import java.io.FileWriter;   // Import the FileWriter class

/**
 *
 * @author Gabriel Gomides
 */
public class drawableObject {
    
    FileWriter myWriter; // Objeto usado para LOGs escrever no arquivo de texto
    private double u1, u2; // Para fronteiras
    private int x1,x2,y1,y2; // Coordenadas do objeto
    private int R; // Raio ( Se for circunferência )
    private int rgb; // Cor do objeto
    
    public void drawableObject(){}
    
    // Para setar coordenadas
    public void setCoords( int x1, int x2, int y1, int y2 ){
        this.x1 = x1;
        this.x2 = x2;
        this.y1 = y1;
        this.y2 = y2;
    }

    public int getX1(){
        return this.x1;
    }

    public int getX2(){
        return this.x2;
    }

    public int getY1(){
        return this.y1;
    }

    public int getY2(){
        return this.y2;
    }

    // Para setar a cor do objeto desenhável
    public void setRGB( int RGB ){
        this.rgb = RGB;
    }
    
    // Para abrir arguivo log.txt
    public void OF(){
        try{
            this.myWriter = new FileWriter("log.txt");
        }catch(IOException e){
            System.out.println("Erro ao abrir arquivo.");
            e.printStackTrace();
        }
    }
    
    // Para escrever no arquivo de logs
    public void WF( String s ){
        try{
        this.myWriter.write(s);
        }catch(IOException e){
            System.out.println("Erro ao escrever no arquivo.");
            e.printStackTrace();
        }
    }
    
    // Para salvar e fechar arquivo
    public void CF(){
        try{
        this.myWriter.flush();
        this.myWriter.close();
        }catch(IOException e){
            System.out.println("Erro ( flush/close ).");
            e.printStackTrace();
        }
    }
    
    /*
    -> Implementação do algoritmo de retas ( DDA )
    * Calcula os pontos a serem plotados na tela.
    * Escreve em um arquivo TXT as coordenadas X,Y dos pontos plotados.
    */
    public void ddaAlgorithm( BufferedImage image){
    
        OF(); // Criar/Abrir arquivo de logs ( log.txt )
        WF("========== ALGORITMO DDA ==========\n");
        
        // INÍCIO ( DDA )
        
        // Declaração de variáveis:
        int dx, dy, passos, k;
        double x_incr, y_incr, x, y;
        
        int x1 = this.x1;
        int x2 =this.x2;
        int y1 =this.y1;
        int y2 =this.y2;
        
        // Início
        
        dx = x2-x1;
        dy = y2-y1;
        
        if( Math.abs(dx) > Math.abs(dy) ){
            passos = Math.abs(dx);
        }else{
            passos = Math.abs(dy);
        }
        
        x_incr = (double)dx/(double)passos;
        y_incr = (double)dy/(double)passos;
        x = (double) x1;
        y = (double) y1;
        
        image.setRGB((int) Math.round(x), (int) Math.round(y), this.rgb); // Desenhar pixel
        WF( "Plotado: ("+ Integer.toString((int)Math.round(x)) + "," + Integer.toString((int)Math.round(y)) + ") [ primeiro ponto ]\n" ); // Escrever no arquivo
        
        for( k = 0; k < passos; k++ ){
            x = x+x_incr;
            y = y+y_incr;
            image.setRGB((int) Math.round(x), (int) Math.round(y), this.rgb); // Desenhar pixel
            WF( "Plotado: ("+ Integer.toString((int)Math.round(x)) + "," + Integer.toString((int)Math.round(y)) + ")\n" ); // Escrever no arquivo
        }
        
        // FIM ( DDA )
        
        WF("========== FIM (DDA) ==========\n");
        CF(); // Salvar e fechar arquivo
        
    }
    
    /*
    -> Implementação do algoritmo de retas ( Bresenham )
    * Calcula os pontos a serem plotados na tela.
    * Escreve em um arquivo TXT as coordenadas X,Y dos pontos plotados.
    */
    public void bresenhamAlgorithm ( BufferedImage image) {

        OF(); // Criar/Abrir arquivo de logs ( log.txt )
        WF("========== ALGORITMO BRESENHAM ==========\n");

        // INÍCIO BRESENHAM

        int dx,dy,x,y,i;
        int incrx, incry, p, const1, const2;

        dx = x2 - x1;
        dy = y2 - y1;
        x = x1;
        y = y1;

        image.setRGB(x, y, this.rgb);

        if (dx >= 0) {
            incrx = 1;
        }else {
            dx = -dx;
            incrx = -1;
        }

        if (dy >= 0) {
            incry = 1;
        }else {
            dy = -dy;
            incry = -1;
        }

        if (dy < dx) {
            p = 2 * dy - dx;
            const1 = 2 * dy;
            const2 = 2 * (dy - dx);

            for (i = 1; i <= dx; i++) {
                x += incrx;
                if (p < 0) {
                    p += const1;
                } else {
                    p += const2;
                    y += incry;
                }

                image.setRGB(x, y, this.rgb);
                WF( "Plotado: ("+ Integer.toString(x) + "," + Integer.toString(y) + ")\n" ); // Escrever no arquivo
            }
        }else {
            p = 2 * dx - dy;
            const1 = 2 * dx;
            const2 = 2 * (dx - dy);

            for (i = 1; i <= dy; i++) {
                y += incry;
                if (p < 0) {
                    p += const1;
                }else {
                    p += const2;
                    x += incrx;
                }
                image.setRGB(x, y, this.rgb);
                WF( "Plotado: ("+ Integer.toString(x) + "," + Integer.toString(y) + ")\n" ); // Escrever no arquivo
            }
        }

        // FIM BRESENHAM
            
        WF("========== FIM (BRESENHAM) ==========\n");
        CF(); // Salvar e fechar arquivo

    }
    
    /*
    -> Implementação do algoritmo de circunferência ( Bresenham )
    * Calcula os pontos a serem plotados na tela.
    * Escreve em um arquivo TXT as coordenadas X,Y dos pontos plotados.
    */
    public void bresenhamCircle( BufferedImage image ){
    
        OF(); // Criar/Abrir arquivo de logs ( log.txt )
        WF("========== CIRCUNFERÊNCIA DE BRESENHAM ==========\n");
            
        // Calculando distância entre dois pontos para obter o RAIO:
        /*
        -> Dados os pontos A(xA, yA) e B (xB, Yb), para calcular a distância entre esses dois pontos, utilizamos a fórmula dAB² = (xB – xA)² + (yB – yA)².
        */
        int a = (int)Math.pow((x2-x1),2);
        int b = (int)Math.pow((y2-y1),2);
        this.R = (int)(Math.sqrt(a+b));

        /*
        Início do código de Bresenham. 
        Plota os pontos do secundo octante e chama uma função para plotar os pontos simétricos
        */
        int r = this.R;
        int x = 0;
        int y = r;
        int p = 3 - 2 * r;
        
        symPlot( x1, y1, x, y, this.rgb, image);
        while (x < y) {
            if (p < 0) {
                p += 4 * x + 6;
            } else {
                p += 4 * (x - y) + 10;
                y--;
            }
            x++;
            symPlot( x1, y1, x, y, this.rgb, image);
        }
        
        // Fim do código de bresenham
        
        WF("========== FIM (CIRCUNFERÊNCIA DE BRESENHAM) ==========\n");
        CF(); // Salvar e fechar arquivo
    }
    
    // Função auxiliar do algoritmo de Bresenham ( para plotar os pontos simétricos )
    public void symPlot( int xc, int yc, int x, int y, int cor, BufferedImage image ){
        image.setRGB( (x + xc) , (y + yc) , cor);
        image.setRGB( (x + xc) , (-y + yc) , cor);
        image.setRGB( (-x + xc) , (-y + yc) , cor);
        image.setRGB( (-x + xc) , (y + yc) , cor);
        image.setRGB( (y + xc) , (x + yc) , cor);
        image.setRGB( (y + xc) , (-x + yc) , cor);
        image.setRGB( (-y + xc) , (-x + yc) , cor);
        image.setRGB( (-y + xc) , (x + yc) , cor);
   
        // Escrever no arquivo
        WF( "Plotado: ("+ Integer.toString((x + xc)) + "," + Integer.toString((y + yc)) + ")\n" ); 
        WF( "Plotado: ("+ Integer.toString((x + xc)) + "," + Integer.toString((-y + yc)) + ")\n" ); 
        WF( "Plotado: ("+ Integer.toString((-x + xc)) + "," + Integer.toString((y + yc)) + ")\n" ); 
        WF( "Plotado: ("+ Integer.toString((-x + xc)) + "," + Integer.toString((-y + yc)) + ")\n" ); 
        WF( "Plotado: ("+ Integer.toString((y + xc)) + "," + Integer.toString((x + yc)) + ")\n" ); 
        WF( "Plotado: ("+ Integer.toString((y + xc)) + "," + Integer.toString((-x + yc)) + ")\n" ); 
        WF( "Plotado: ("+ Integer.toString((-y + xc)) + "," + Integer.toString((x + yc)) + ")\n" ); 
        WF( "Plotado: ("+ Integer.toString((-y + xc)) + "," + Integer.toString((-x + yc)) + ")\n" ); 
    }
    
    public int bit(int code, int pos) {
        int bit = code << (31 - pos);
        bit = bit >>> 31;
        return bit;
    }

    public int region_code( int x, int y, int xmin, int ymin, int xmax, int ymax ) {
        int code = 0;
        if (x < xmin) {  // Para esquerda do retangulo
            code += 1;
        }
        if ( x > xmax) { // Para direita do retangulo
            code += 2;
        }
        if ( y < ymin) { // Abaixo
            code += 4;
        }
        if ( y > ymax) { // Acima
            code += 8;
        }
        return code;
    }
    
    /*
    -> Implementação do algoritmo de recorte de Cohen Sutherland
    * Utiliza os bits e o operador AND para saber se parte da reta está fora da tela.
    * Mostra as retas ( ou partes delas ) que se encontram dentro da região delimitada pelo retângulo. 
    */
    public void cohenAlgorithm(int xmin, int xmax, int ymin, int ymax, BufferedImage image) {

        double xint = 0;
        double yint = 0; 
        int cfora;
        boolean aceite = false, feito = false;

        while (!feito) {
            int c1 = region_code(this.x1, this.y1, xmin, ymin, xmax, ymax);
            int c2 = region_code( this.x2, this.y2, xmin, ymin, xmax, ymax);
            if ( c1 == 0 && c2 == 0 ) {
                aceite = true;
                feito = true;
            } else if ( (c1 & c2) != 0 ) { // Segmento completamente dentro da janela
                feito = true;
            } else {
                cfora = (c1 != 0) ? c1 : c2;
                if( c1 != 0 ){
                    cfora = c1;
                }else{
                    cfora = c2;
                }
                if (bit( cfora, 0 ) == 1) { // limite da esquerda
                    xint = xmin;
                    yint = this.y1 + (this.y2 - this.y1) * (xmin - this.x1) / (this.x2 - this.x1);
                } else if (bit( cfora, 1 ) == 1) {// limite da direita
                    xint = xmax;
                    yint = this.y1 + (this.y2 - this.y1) * (xmax - this.x1) / (this.x2 - this.x1);
                } else if (bit( cfora, 2 ) == 1) {// limite abaixo
                    yint = ymin;
                    xint = this.x1 + (this.x2 - this.x1) * (ymin - this.y1) / (this.y2 - this.y1);
                } else if (bit( cfora, 3 ) == 1) { // limite acima
                    yint = ymax;
                    xint = this.x1 + (this.x2 - this.x1) * (ymax - this.y1) / (this.y2 - this.y1);
                }
                if (c1 == cfora) {
                    this.x1 = (int)(Math.round(xint));
                    this.y1 = (int)(Math.round(yint));
                    
                } else {
                    this.x2 = (int)(Math.round(xint));
                    this.y2 = (int)(Math.round(yint));
                }
            }
        } // fim loop while
        if(aceite) {
            this.ddaAlgorithm(image);
        }
    } // Fim do Cohen-Sutherland
    
    public boolean clipset(double p, double q) {
        double r = q / p;
        if (p < 0) {
            if (r > 1) {
                return false;
            } else if (r > this.u1) {
                this.u1 = r;
            }
        } else if (p > 0) {
            if (r < 0) {
                return false;
            } else if (r < this.u2) {
                this.u2 = r;
            }
        } else if (q < 0) {
            return false;
        }
        return true;
    }
    
    /*
    -> Implementação do algoritmo de recorte de liang
    */
    public void liangAlgorithm(int xmin,int ymin, int xmax, int ymax, BufferedImage buffer) {
      
        double dx1,dx2,dy1,dy2;

        // Converte as coordenadas da classe para double
        dx1 = (double) this.x1;
        dx2 = (double) this.x2;
        dy1 = (double) this.y1;
        dy2 = (double) this.y2;
        
        this.u1 = 0.0;
        this.u2 = 1.0;

        double dx = dx2 - dx1;
        double dy = dy2 - dy1;

        if (clipset(-dx, dx1 - xmin)) {
            if (clipset(dx, xmax - dx1)) {
                if (clipset(-dy, dy1 - ymin)) {
                    if (clipset(dy, ymax - dy1)) {
                        if (u2 < 1.0) {
                            dx2 = dx1 + dx * u2;
                            dy2 = dy1 + dy * u2;
                        }
                        if (u1 > 0.0) {
                            dx1 = dx1 + dx * u1;
                            dy1 = dy1 + dy * u1;
                        }
                        this.x1 = (int)(Math.round(dx1));
                        this.y1 = (int)(Math.round(dy1));
                        this.x2 = (int)(Math.round(dx2));
                        this.y2= (int)(Math.round(dy2));
                        this.ddaAlgorithm(buffer);
                    }
                }
            }
        }
    }


}
