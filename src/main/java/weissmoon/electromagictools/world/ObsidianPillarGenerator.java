package weissmoon.electromagictools.world;


import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.world.DimensionType;
import net.minecraft.world.World;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import weissmoon.core.helper.RNGHelper;
import weissmoon.electromagictools.block.BlockObsidian;
import weissmoon.electromagictools.block.ModBlocks;

/**
 * Created by Weissmoon on 2/18/21.
 */
public class ObsidianPillarGenerator{
    @SubscribeEvent
    public void generatePillar(DecorateBiomeEvent.Decorate event){
        if(event.getType() != DecorateBiomeEvent.Decorate.EventType.SAND_PASS2)
            return;
        World world = event.getWorld();
        if (world.provider.getDimensionType() != DimensionType.OVERWORLD)
            return;

        ChunkPos cp = event.getChunkPos();

        int x = Math.abs(RNGHelper.getRNGInt() % 8);
        int z = Math.abs(RNGHelper.getRNGInt() % 8);
        int y = world.getChunk(cp.getXStart(), cp.getZStart()).getHeightValue(x, z);
        x += cp.getXStart();
        z += cp.getZStart();
        BlockPos pos = new BlockPos(x, y, z);
        if(RNGHelper.getRNGFloat() < 0.999)
            return;
        if(!world.getBlockState(pos).isSideSolid(world, pos, EnumFacing.UP) || world.getBlockState(pos).getBlock() == ModBlocks.obsidianTile)
            return;
        world.setBlockState(pos.up(1), Blocks.OBSIDIAN.getDefaultState());
        world.setBlockState(pos.up(2), Blocks.OBSIDIAN.getDefaultState());
        world.setBlockState(pos.up(3), Blocks.OBSIDIAN.getDefaultState());
        world.setBlockState(pos.up(4), ModBlocks.obsidianTile.getDefaultState().withProperty(BlockObsidian.ELEMENT, RNGHelper.getRNGIntClamp(6)));
    }
}
