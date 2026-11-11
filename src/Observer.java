
import java.awt.Color;
import java.util.ArrayList;

public interface Observer {
    public void update(int factID, Color factColor, int factTroops, int factTileCount, int factImprovableCount, ArrayList<Integer> factAllies);
}
