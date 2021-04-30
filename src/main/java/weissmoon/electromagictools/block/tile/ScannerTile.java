package weissmoon.electromagictools.block.tile;

import net.minecraft.tileentity.TileEntity;

import java.util.ArrayList;

/**
 * Created by Weissmoon on 4/24/21.
 */
public class ScannerTile extends TileEntity{

    private static final ArrayList<ScannerTile> scanners = new ArrayList<>();

    @Override
    public void onLoad(){
        super.onLoad();
        scanners.add(this);
    }

    @Override
    public void invalidate(){
        removeTile();
        super.invalidate();
    }

    @Override
    public void onChunkUnload(){
        removeTile();
        super.onChunkUnload();
    }

    private void removeTile(){
        scanners.remove(this);
    }
}
