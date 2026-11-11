import java.util.*;
import java.util.concurrent.*;

class CompileClaims extends RecursiveTask<ArrayList<Land>> {
    Empire faction;
    int claims;
    ArrayList<Land> list;

    CompileClaims(Empire faction, int claims, ArrayList<Land> list)
    {
        this.faction = faction;
        this.claims = claims;
        this.list = list;
    }

    public ArrayList<Land> compute()
    {
        if (claims <= 0) {
            return new ArrayList<>();
        }

        if (claims <= 25)
        {
            ArrayList<Land> tile = new ArrayList<>();

            for (int i = 0; i < claims; i++)
            {
                Land borderTile, tileToclaim;
                borderTile=list.get((int)(Math.random()*list.size()));
                ArrayList<Land> listAdj=borderTile.getAdjTiles();
                int num=(int)(Math.random()*listAdj.size());
                tileToclaim=listAdj.get(num);

                while(faction.isOwner(tileToclaim)||!faction.isClaimable(tileToclaim)){

                    num=(int)(Math.random()*listAdj.size());
                    tileToclaim=listAdj.get(num);
                }

                tile.add(tileToclaim);

                
            }
            
            return tile;
        }

        CompileClaims left = new CompileClaims(faction, claims/2, list);
        CompileClaims right = new CompileClaims(faction, claims-claims/2, list);

        left.fork();
        ArrayList<Land> finalComp = right.compute();
        finalComp.addAll(left.join());

        return finalComp;
    }
}