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

        if (claims == 1)
        {
            Land borderTile;
            borderTile=list.get((int)(Math.random()*list.size()));

            ArrayList<Land> expandableTiles = new ArrayList<>();
            for (Land x : borderTile.getAdjTiles()) {
                if (!faction.isOwner(x) && faction.isClaimable(x)) {
                    expandableTiles.add(x);
                }
            }

            ArrayList<Land> tileToClaim = new ArrayList<>();
            if (!expandableTiles.isEmpty()) {
                tileToClaim.add(expandableTiles.get((int) (Math.random() * expandableTiles.size())));
            }
            return tileToClaim;
        }

        CompileClaims left = new CompileClaims(faction, claims/2, list);
        CompileClaims right = new CompileClaims(faction, claims-claims/2, list);

        left.fork();
        ArrayList<Land> finalComp = right.compute();
        finalComp.addAll(left.join());

        return finalComp;
    }
}