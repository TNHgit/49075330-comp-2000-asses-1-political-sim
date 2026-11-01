import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
public class Canvas extends JFrame{
    Grid gameGrid;
    JButton turnButton;
    Color white = new Color(207,207,207);
    Color darkGrey = new Color(110,110,110);
    public Canvas(){
        setSize(613,800);
        setTitle("politicalSim");
        setLayout(null);
        setResizable(false);
        setBackground(white);
        gameGrid= new Grid(600,600,50,30);
        gameGrid.setLocation(0,0);
        gameGrid.setBackground(darkGrey);
        add(gameGrid);
        turnButton = new JButton("Next Turn");
        turnButton.setSize(300,100);
        turnButton.setVisible(true);
        turnButton.setLocation(300,650);
        turnButton.addActionListener(l -> nextTurnPressed(gameGrid));
        add(turnButton);
        setUpWindowCloser();
        setVisible(true);
    }
    public Canvas(Grid grid, int width, int height, String title){
        setSize(width,height);
        setTitle(title);
        setResizable(false);
        setLayout(null);
        gameGrid= grid;
        add(gameGrid);
        turnButton = new JButton("Next Turn");
        turnButton.setSize(300,100);
        turnButton.setVisible(true);
        turnButton.setLocation(300,650);
        turnButton.addActionListener(l -> nextTurnPressed(gameGrid));
        add(turnButton);
        setUpWindowCloser();
        setVisible(true);
    }

    public void setUpWindowCloser(){
        addWindowListener(new WindowAdapter(){
            @Override 
            public void windowClosing(WindowEvent e){
                System.exit(0);
            }
        }  );
    }

    public void nextTurnPressed(Grid gameGrid)
    {
        gameGrid.updateTurn();
    }
}
