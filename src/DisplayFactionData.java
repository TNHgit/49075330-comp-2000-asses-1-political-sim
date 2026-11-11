import java.awt.Color;
import java.util.ArrayList;

public class DisplayFactionData implements Observer, DisplayElement {
    private int factID;
    private Color factColor;
    private int factTroops;
    private int factTileCount;
    private int factImprovableCount;
    private ArrayList<Integer> factAllies;
    private Grid dataSource;

    public DisplayFactionData(Grid datasource)
    {
        this.dataSource = datasource;
        dataSource.registerObserver(this);
    }

    public void update(int factID, Color factColor, int factTroops, int factTileCount, int factImprovableCount, ArrayList<Integer> factAllies)
    {
        this.factID = factID;
        this.factColor = factColor;
        this.factTroops = factTroops;
        this.factTileCount = factTileCount;
        this.factImprovableCount = factImprovableCount;
        this.factAllies = new ArrayList<>(factAllies);

        display();
    }

    public void display()
    {
        System.out.println("Faction " + factID + " Colour: " + factColor.toString());
        System.out.println("Faction " + factID + " Troops: " + factTroops);
        System.out.println("Faction " + factID + " Total Tiles: " + factTileCount);
        System.out.println("Faction " + factID + " Improvable Tiles: " + factImprovableCount);
        for (Integer ally : factAllies)
        {
            System.out.println("Faction " + factID + " Allied With " + ally);
        }
        System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
    }
}
