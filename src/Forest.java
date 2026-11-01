import java.awt.Color;
import java.awt.Graphics;

public class Forest extends Land implements Improvable{
    private double growthBonus;

    int[] xPoints = {5, 30, 50, 70, 95};
    int[] yPoints = {75, 15, 60, 15, 75};
    int[] firstRect = {25, 75};
    int[] secondRect = {65, 75};
    int rectX = 10;
    int rectY = 25;
    
    public Forest(){
        growthBonus = 0.2;
    }
    
    public void levelUp(){
        growthBonus += 0.4;
    }

    public double getBonus(){
        return growthBonus;
    }

    @Override
    public void drawLand(int width, int height) {
        super.drawLand(width, height);

        for(int i = 0; i < xPoints.length; i++){
            xPoints[i] *= width;
            xPoints[i] /= 100;
            yPoints[i] *= height;
            yPoints[i] /= 100;
        }
        for(int i = 0; i < firstRect.length; i++){
            firstRect[i] *= width;
            firstRect[i] /= 100;
            secondRect[i] *= height;
            secondRect[i] /= 100;
        }
        rectX *= width;
        rectX /= 100;
        rectY *= height;
        rectY /= 100;
        repaint();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(new Color(100,65,23)); 
        g.fillRect(firstRect[0], firstRect[1], rectX, rectY);
        g.fillRect(secondRect[0], secondRect[1], rectX, rectY);
        g.setColor(new Color(31,80,44));
        g.fillPolygon(xPoints, yPoints, xPoints.length);
    }
}
