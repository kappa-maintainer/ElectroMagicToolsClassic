package weissmoon.electromagictools.world;


import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.event.terraingen.DecorateBiomeEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import weissmoon.core.helper.RNGHelper;
import weissmoon.electromagictools.block.BlockObsidian;
import weissmoon.electromagictools.block.ModBlocks;

/**
 * Created by Weissmoon on 2/18/21.
 */
public class ObsidianPillarGenerator{
//    WorldGenerator
    @SubscribeEvent
    public void generatePillar(DecorateBiomeEvent.Decorate event){
//        Biome.
        if(event.getType() != DecorateBiomeEvent.Decorate.EventType.SAND_PASS2)
            return;

        int x = event.getChunkPos().getXStart() + 8;
        int z = event.getChunkPos().getZStart() + 8;
        World world = event.getWorld();

        for(int i = 250;true;i--){
            BlockPos pos = new BlockPos(x, i, z);
            if(!world.isAirBlock(pos)){
                if(RNGHelper.getRNGFloat() < 0.999)
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
