import java.awt.*;
import java.util.*;
import javax.lang.model.util.ElementScanner14;
import javax.swing.*;
public class Grid extends JPanel{
    private TreeMap<Integer,Land> gameMap;
    private int gridSize;
    private ArrayList<Empire> factions;
    public  Grid(int width,int height, int gridSize,int factCount){
        gameMap = new TreeMap<Integer,Land>();
        this.gridSize = gridSize;
        setSize(width,height);
        setLayout(null);
        //System.out.println("grid tile Width: " + tileSizeWidth + " Height: " + tileSizeHeight);
        createGrid(factCount);

    }
    public void createGrid(int factCount){
        int tileSizeHeight,tileSizeWidth, tileID;
        tileSizeWidth=getWidth()/gridSize;
        tileSizeHeight=getHeight()/gridSize;
        tileID=1;
        Land placeHolder;
        for(int y = 0;y<gridSize;y++){
            for(int x = 0;x<gridSize;x++){
                double terrainType = Math.random();
                if (terrainType <= 0.2){
                    placeHolder=new Forest();
                    placeHolder.setBackground(Color.black);
                }
                else if (terrainType >= 0.9){
                    placeHolder=new Mountain();
                    placeHolder.setBackground(Color.gray);
                }
                else
                {
                    placeHolder=new Plains();
                    placeHolder.setBackground(Color.black);
                }
                placeHolder.setID(tileID);
                
                placeHolder.setSize(tileSizeWidth-1, tileSizeHeight-1);
                placeHolder.setLocation(x*tileSizeWidth , y*tileSizeHeight);
                add(placeHolder);
                addTile(tileID,placeHolder);

                if(tileExist(tileID-1)&& tileID%gridSize!=1){
                    getTile(tileID-1).setRight(placeHolder);
                    placeHolder.setLeft(getTile(tileID-1));
                }
                if(tileExist(tileID-gridSize)){
                    getTile(tileID-gridSize).setBelow(placeHolder);
                    placeHolder.setAbove(getTile(tileID-gridSize));
                }
                tileID++;
            }
        }

        factions = new ArrayList<>();
        Land startLand = getTile((int)Math.floor(Math.random()*gridSize*gridSize)+1);
        for (int i = 0; i < factCount; i++)
        {
            int redVal = 100+(155/((int)Math.ceil(Math.cbrt(factCount)-1)))*(i%(int)Math.ceil(Math.cbrt(factCount)));
            int greenVal = 100+(155/((int)Math.ceil(Math.cbrt(factCount)-1)))*((int)(Math.floor(i/(int)Math.ceil(Math.cbrt(factCount))))%(int)Math.ceil(Math.cbrt(factCount)));
            int blueVal = 100+(155/((int)Math.ceil(Math.cbrt(factCount)-1)))*((int)(Math.floor(i/((int)Math.ceil(Math.cbrt(factCount))*(int)Math.ceil(Math.cbrt(factCount)))))%(int)Math.ceil(Math.cbrt(factCount)));
            Color factCol = new Color(redVal, greenVal, blueVal);

            //RANDOMISED COLOURS (CAN LOOP FOREVER WITH TOO MANY FACTIONS)
            /* 
            Color factCol = new Color((int)(25*Math.random()*10), (int)(25*Math.random()*10), (int)(25*Math.random()*10));
            Boolean newCol = false;
            while (newCol == false)
            {
                if (factCol.getBlue() < 40 && factCol.getRed() < 40 && factCol.getGreen() < 40)
                {
                    factCol = new Color((int)(25*Math.random()*10), (int)(25*Math.random()*10), (int)(25*Math.random()*10));
                    continue;
                }

                newCol = true;
                for (int j = 0; j < factions.size(); j++)
                {
                    if (Math.abs(factions.get(j).getColor().getRed()-factCol.getRed()) < 60 && Math.abs(factions.get(j).getColor().getGreen()-factCol.getGreen()) < 60 && Math.abs(factions.get(j).getColor().getBlue()-factCol.getBlue()) < 60)
                    {
                        factCol = new Color((int)(25*Math.random()*10), (int)(25*Math.random()*10), (int)(25*Math.random()*10));
                        newCol = false;
                        break;
                    }
                }
            }
            */

            Empire faction = new Empire(factCol, i+1);
            while (startLand.getOwner() != null || startLand instanceof Impassable)
            {
                startLand = getTile((int)Math.floor(Math.random()*gridSize*gridSize)+1);
            }
            faction.addTile(startLand);
            faction.updateTiles();
            factions.add(faction);
        }
    }

    public void updateTurn(){
        for (int i = 0; i < 20; i++)
        {
            System.out.println();
        }

        ArrayList<Integer> skipTurns = new ArrayList<>();
        for(int i = 0; i < factions.size(); i++)
        {
            Empire faction = factions.get(i);
            if (!skipTurns.contains(i))
            {
                try
                {
                    faction.updateTiles();
                }
                catch (IndexOutOfBoundsException e)
                {
                    factions.remove(faction);
                    i--;
                    continue;
                }
                if (Math.random() > 0.5)
                {
                    for(int j = 0; j < faction.getTroops(); j++)
                    {
                        claimRandomTile(faction);
                        faction.updateTiles();
                    }
                }
                else
                {
                    if ((Math.random() > 0.1 || i >= factions.size()-1) && faction.getImprovableCount() != 0)
                    {
                        faction.improveTile();
                    }
                    else
                    {
                        int allianceInd = (int)Math.floor((factions.size()-i)*Math.random()+i);
                        faction.ally(factions.get(allianceInd));
                        factions.get(allianceInd).ally(faction);
                        skipTurns.add(allianceInd);
                    }
                }
            }

            System.out.println("Faction " + faction.getID() + " Colour: " + faction.getColor().toString());
            System.out.println("Faction " + faction.getID() + " Troops: " + faction.getTroops());
            System.out.println("Faction " + faction.getID() + " Total Tiles: " + faction.getTileCount());
            System.out.println("Faction " + faction.getID() + " Improvable Tiles: " + faction.getImprovableCount());
            for (int j = 0; j < faction.getAllies().size(); j++)
            {
                if (factions.contains(faction.getAllies().get(j)))
                {
                    System.out.println("Faction " + faction.getID() + " Allied With " + faction.getAllies().get(j).getID());
                }
            }
            System.out.println("~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~");
        }
    }

    public Land getTile(int ID){
        return gameMap.get(ID);
    }

    public void addTile(int identifier, Land land){
        gameMap.put(identifier, land);
    }

    public boolean tileExist(int ID){
		return gameMap.containsKey(ID);
	}
    
    public void update(){

    }
    public void claimRandomTile(Empire faction){
        ArrayList<Land> list = faction.getPlayableBorderTiles();
        if (list.isEmpty())
        {
            return;
        }
        Land borderTile, tileToclaim;
        //int num2 =0;
        borderTile=list.get((int)(Math.random()*list.size()));
        list=borderTile.getAdjTiles();
        int num=(int)(Math.random()*list.size());
        tileToclaim=null;

        while(faction.isOwner(tileToclaim)||!faction.isClaimable(tileToclaim)){
            // System.out.println("tile i'm chcking from: "+ borderTile.ID+" adjTile trying to take = " +num+ " is Owner = "+ faction.isOwner(tileToclaim) + " is claimable = "+ faction.isClaimable(tileToclaim));
            // if(tileToclaim!=null){
            //     System.out.println(tileToclaim.ID);
            // }
            num=(int)(Math.random()*list.size());
            tileToclaim=list.get(num);
            // if(num2>=25){
            //     System.out.println("loop occured");
            //     return;
            // }
            // num2++;
        }
        //System.out.println("\ntile taken\n");
        faction.addTile(tileToclaim);
    }
}
