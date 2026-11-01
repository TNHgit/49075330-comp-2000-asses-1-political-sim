import java.util.ArrayList;
import javax.swing.*;
abstract class  Land  extends JPanel  {
    private int ID;
    private Land left,right,above,below;
    private Empire owner;

    public Land(){
        ID=0;
        left=null;
        right=null;
        above=null;
        below=null;
        owner = null;
    }
    public Land(int identifier){
        ID=identifier;
        left=null;
        right=null;
        above=null;
        below=null;
        owner = null;
    }

    public void setRight(Land land){
        right=land;
    }

    public void setLeft(Land land){
        left=land;
    }

    public void setAbove(Land land){
        above=land;
    }

    public void setBelow(Land land){
        below=land;
    }

    public int getID(){
        return ID; 
    }
    public void setID(int num){
        ID=num;
    }

    public ArrayList<Land> getAdjTiles(){
        ArrayList<Land> list = new ArrayList<>();
        list.add(above);
        list.add(right);
        list.add(below);
        list.add(left);
        return list;
    }

    public Empire getOwner() 
    {
        return owner;
    }

    public void setOwner(Empire owner)
    {
        this.owner = owner;
    }
}
