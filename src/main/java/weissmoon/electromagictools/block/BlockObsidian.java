package weissmoon.electromagictools.block;

import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import weissmoon.core.block.WeissBlock;
import weissmoon.electromagictools.lib.Strings;

/**
 * Created by Weissmoon on 2/10/21.
 */
public class BlockObsidian extends WeissBlock{

    public static final PropertyInteger ELEMENT = PropertyInteger.create("element", 0, 6);

    public BlockObsidian(){
        super(Strings.Blocks.OBSIDIAN_TILE_NAME, Material.ROCK, MapColor.BLACK);
        setDefaultState(blockState.getBaseState().withProperty(ELEMENT, 0));
        setHardness(50.0F);
        setResistance(2000.0F);
        setSoundType(SoundType.STONE);
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, ELEMENT);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state){
        int i = state.getValue(ELEMENT);
        return new ItemStack(this, 1, i);
    }

    @Deprecated
    @Override
    public IBlockState getStateFromMeta(int meta){
        if(meta > 6){
            return getDefaultState();
        }
        return getDefaultState().withProperty(ELEMENT, meta);
    }
    @Override
    public int getMetaFromState(IBlockState state){
        int tileID = state.getValue(ELEMENT);
        if(tileID > 6){
            return 0;
        }
        return tileID;
    }


}
