package weissmoon.electromagictools.util;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCommandBlock;
import net.minecraft.block.BlockStructure;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

/**
 * Created by Weissmoon on 9/25/20.
 */
public class BlockUtils {

    public static boolean breakBlock(World world, BlockPos pos, EntityPlayer player, ItemStack stack, int exp){
        IBlockState iblockstate = world.getBlockState(pos);
        TileEntity tileentity = world.getTileEntity(pos);
        Block block = iblockstate.getBlock();

        if (block instanceof BlockCommandBlock || block instanceof BlockStructure){
//            this.world.notifyBlockUpdate(pos, iblockstate, iblockstate, 3);
            return false;
        }else{
            world.playEvent(player, 2001, pos, Block.getStateId(iblockstate));
            boolean flag = iblockstate.getBlock().canHarvestBlock(world, pos, player);
            boolean flag1 = iblockstate.getBlock().removedByPlayer(iblockstate, world, pos, player, flag);
            if (flag1){
                iblockstate.getBlock().onPlayerDestroy(world, pos, iblockstate);
                if(flag)
                    iblockstate.getBlock().harvestBlock(world, player, pos, iblockstate, tileentity, stack);
            }
            // Drop experience
            if (flag1 && exp > 0)
            {
                iblockstate.getBlock().dropXpOnBlockBreak(world, pos, exp);
            }
            return flag1;
        }
    }
}
