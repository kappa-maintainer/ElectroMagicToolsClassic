package weissmoon.electromagictools.core.helper;

import net.minecraft.block.Block;
import weissmoon.electromagictools.core.block.IBlockWeiss;

import java.util.*;

/**
 * Registry of IBlockWeiss instances
 * Created by Weissmoon on 3/22/19.
 */
public class WeissBlockRegistry {
    public static final WeissBlockRegistry weissBlockRegistry = new WeissBlockRegistry();
    private List<Block> blocks = new ArrayList<Block>();

    public void regBlock(Block item){
        if(item instanceof IBlockWeiss){
            weissBlockRegistry.blocks.add(item);
        }
    }

    public List<Block>getBlockList(){
        List<Block> list = new ArrayList<Block>(blocks);
        return list;
    }
}
