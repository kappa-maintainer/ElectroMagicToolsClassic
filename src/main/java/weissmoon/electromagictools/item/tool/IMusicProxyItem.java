package weissmoon.electromagictools.item.tool;

import net.minecraft.block.BlockJukebox;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import weissmoon.electromagictools.network.JukeboxNonRecordEventMessage;
import weissmoon.electromagictools.network.PacketHandler;

import static net.minecraft.block.BlockJukebox.HAS_RECORD;

/**
 * Created by Weissmoon on 4/19/22.
 */
public interface IMusicProxyItem{

    default EnumActionResult handleMusic(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand){
        IBlockState iblockstate = worldIn.getBlockState(pos);

        if (iblockstate.getBlock() == Blocks.JUKEBOX && !(iblockstate.getValue(HAS_RECORD)))
        {
            ItemStack itemstack = player.getHeldItem(hand);
            iblockstate = iblockstate.withProperty(HAS_RECORD, true);
            worldIn.setBlockState(pos, iblockstate, 2);
            ((BlockJukebox)Blocks.JUKEBOX).insertRecord(worldIn, pos, iblockstate, itemstack);
            if (!worldIn.isRemote){
                PacketHandler.INSTANCE.sendToDimension(getPacket(pos), worldIn.provider.getDimension());
            }
            itemstack.shrink(1);
            player.addStat(StatList.RECORD_PLAYED);

            return EnumActionResult.SUCCESS;
        }
        else
        {
            return EnumActionResult.PASS;
        }
    }

    JukeboxNonRecordEventMessage getPacket(BlockPos pos);

    @SideOnly(Side.CLIENT)
    SoundEvent getSound();
}
