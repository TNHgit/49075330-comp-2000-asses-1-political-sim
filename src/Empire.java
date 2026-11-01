import java.awt.*;
import java.util.*;
public class Empire {
    private ArrayList<Land> ownedLand;
    private ArrayList<Land> improvables;
    private ArrayList<Empire> allies;
    private Color nationColor;
    private int troops;
    private int ID;

    public Empire(){
        ownedLand= new ArrayList<>();
        improvables = new ArrayList<>();
        allies = new ArrayList<>();
        nationColor = Color.white;
        troops = 1;
        this.ID = 0;
    }

    public Empire(Color col, int ID){
        ownedLand= new ArrayList<>();
        improvables = new ArrayList<>();
        allies = new ArrayList<>();
        nationColor = col;
        troops = 1;
        this.ID = ID;
    }

    public ArrayList<Land> getBorderTiles(){
        ArrayList<Land> list= new ArrayList<>();
        if (ownedLand==null){
            return null;
        }
        for(Land l:ownedLand){
            for(Land x: l.getAdjTiles()){
                if(!isOwner(x)){
                    list.add(l);
                    break;
                }
            }
        }
        return list;
    }
    public ArrayList<Land> getPlayableBorderTiles(){
        ArrayList<Land> list= new ArrayList<>();
        if (ownedLand==null){
            return null;
        }
        for(Land l:ownedLand){
            for(Land x: l.getAdjTiles()){
                if(!isOwner(x)&&isClaimable(x)){
                    list.add(l);
                    break;
                }
            }
        }
        return list;
    }
    public void addTile(Land tile){
        ownedLand.add(tile);

        if (tile instanceof Improvable){
            improvables.add(tile);
        }

        if (tile.getOwner() != null)
        {
            tile.getOwner().ownedLand.remove(tile);
            if(tile.getOwner().improvables.contains(tile))
            {
                tile.getOwner().improvables.remove(tile);
            }
        }

        tile.setOwner(this);
    }
    public boolean isClaimable(Land tile){
        //TODO
        if(tile ==null || allies.contains(tile.getOwner()) || tile instanceof Impassable){
            return false;
        }
        return true;
    }
    public Boolean isOwner(Land tile){
        if(tile ==null){
            return true;
        }
        return ownedLand.contains(tile);
    }
    public void updateTiles(){
        if(ownedLand.isEmpty())
        {
            throw new IndexOutOfBoundsException();
        }

        for(Land l:ownedLand){
            l.setBackground(nationColor);
        }
    }
    public void improveTile()
    {
        Land l = improvables.get((int)Math.floor(Math.random() * improvables.size()));
        if (l instanceof Improvable tile && tile.getBonus() < 1)
        {

            l.setBackground(Color.lightGray);
            tile.levelUp();
        }
        else
        {
            l.setBackground(Color.darkGray);
            troops++;
            improvables.remove(l);
        }
    }
    public int getImprovableCount()
    {
        return improvables.size();
    }
    public int getTroops()
    {
        return troops;
    }
    public int getTileCount()
    {
        return ownedLand.size();
    }
    public int getID()
    {
        return ID;
    }
    public void ally(Empire other)
    {
        if (!allies.contains(other))
        {
            allies.add(other);
        }
        else
        {
            allies.remove(other);
        }
    }
    public ArrayList<Empire> getAllies()
    {
        return allies;
    }
    public Color getColor()
    {
        return nationColor;
    }
}
