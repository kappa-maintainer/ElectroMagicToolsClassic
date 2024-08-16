package weissmoon.electromagictools.world;


import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.apache.commons.lang3.RandomUtils;
import weissmoon.core.helper.RNGHelper;
import weissmoon.electromagictools.EMTConfig;
import weissmoon.electromagictools.block.BlockObsidian;
import weissmoon.electromagictools.block.ModBlocks;

/**
 * Created by Weissmoon on 2/18/21.
 */
public class ObsidianPillarGenerator{
    @SubscribeEvent
    public void generatePillar(DecorateBiomeEvent.Decorate event){
        if(event.getType() != DecorateBiomeEvent.Decorate.EventType.TREE)
            return;
        World world = event.getWorld();
        if (world.provider.getDimensionType() != DimensionType.OVERWORLD)
            return;

        ChunkPos cp = event.getChunkPos();

        int x = RandomUtils.nextInt(0, 16) + cp.getXStart();
        int z = RandomUtils.nextInt(0, 16) + cp.getZStart();
        for(int i = 250;true;i--){
            BlockPos pos = new BlockPos(x, i, z);
            if(!world.isAirBlock(pos)){
                if(RNGHelper.getRNGFloat() < 1.0 - ((float)EMTConfig.ObsidianPillarRate)/1000.0)
                    return;
                if(!world.getBlockState(pos).isTopSolid() || world.getBlockState(pos).getBlock() == ModBlocks.obsidianTile)
                    return;
                world.setBlockState(pos.up(1), Blocks.OBSIDIAN.getDefaultState());
                world.setBlockState(pos.up(2), Blocks.OBSIDIAN.getDefaultState());
                world.setBlockState(pos.up(3), Blocks.OBSIDIAN.getDefaultState());
                world.setBlockState(pos.up(4), ModBlocks.obsidianTile.getDefaultState().withProperty(BlockObsidian.ELEMENT, RNGHelper.getRNGIntClamp(6)));
                return;
            }
        }
    }
}
