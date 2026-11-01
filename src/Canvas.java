import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;
import javax.swing.*;
public class Canvas extends JFrame{
    Grid gameGrid;
    JButton turnButton;
    JButton autoButton;
    java.util.Timer autoNext;
    java.util.TimerTask nextTurn;
    Color white = new Color(207,207,207);
    Color darkGrey = new Color(110,110,110);
    public Canvas(){
        setSize(613,800);
        setTitle("politicalSim");
        setLayout(null);
        setResizable(false);
        setBackground(white);
        gameGrid= new Grid(600,600,50,7);
        gameGrid.setLocation(0,0);
        gameGrid.setBackground(darkGrey);
        add(gameGrid);
        turnButton = new JButton("Next Turn");
        turnButton.setSize(250,100);
        turnButton.setVisible(true);
        turnButton.setLocation(325,650);
        turnButton.addActionListener(l -> nextTurnPressed(gameGrid));
        add(turnButton);
        autoButton = new JButton("Auto Turns");
        autoButton.setSize(250,100);
        autoButton.setVisible(true);
        autoButton.setLocation(25,650);
        autoButton.addActionListener(l -> toggleAutoTurns(gameGrid));
        add(autoButton);
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
        if (autoNext == null)
        {
            gameGrid.updateTurn();
        }
    }

    public void toggleAutoTurns(Grid gameGrid)
    {
        if (autoNext == null)
        {
            nextTurn = new TimerTask(){public void run(){gameGrid.updateTurn();}};

            autoNext = new Timer();

            autoNext.schedule(nextTurn, 0, 100);
        }
        else
        {
            autoNext.cancel();
            autoNext = null;
        }
    }
}
