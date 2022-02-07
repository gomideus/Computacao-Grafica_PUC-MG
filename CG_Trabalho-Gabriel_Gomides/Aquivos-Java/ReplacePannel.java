
package projetocg;

import java.awt.Graphics;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import javax.swing.JPanel;
import java.awt.Color;

public class ReplacePannel extends JPanel {
    private BufferedImage image = new BufferedImage(3000, 3000, BufferedImage.TYPE_INT_RGB);
    
    public ReplacePannel(){
        cleanDrawable();
        repaint();
    }

    public BufferedImage getBuffer(){
        return this.image;
    }
    
    // Replace do método padrão de PAINT do jPannel
    @Override
    public void paint (Graphics graphic) {
        Graphics2D graphics = (Graphics2D) graphic;
        graphics.drawImage(image, null, 0, 0); 
    }
    
    public void update(){
        repaint();
    }
    
    public void cleanDrawable(){
        for( int i = 0; i < image.getHeight(); i++){
            for( int j = 0; j < image.getWidth(); j++){
                image.setRGB(i, j, Color.GRAY.getRGB());
            }
        }
    }
}
