package weissmoon.electromagictools.block.solar;

import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import weissmoon.core.block.WeissBlock;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.block.tile.SolarTileEntity;
import weissmoon.electromagictools.lib.Strings;

import javax.annotation.Nullable;

/**
 * Created by Weissmoon on 8/19/20.
 */
public class SolarBlockBase extends WeissBlock {

    public static final PropertyBool COMPRESSED = PropertyBool.create("compressed");
    public static final PropertyInteger ELEMENT = PropertyInteger.create("element", 0, 6);
    public final boolean compressedPanel;

    public SolarBlockBase(boolean co) {
        super(co ? Strings.Blocks.SOLAR_COMPRESSED_BLOCK_NAME : Strings.Blocks.SOLAR_BLOCK_NAME, Material.IRON, MapColor.CYAN);
        this.compressedPanel = co;
        setDefaultState(blockState.getBaseState().withProperty(COMPRESSED, false).withProperty(ELEMENT, 0));
        setHardness(5.5F);
        setCreativeTab(ElectroMagicTools.EMTtab);
    }


    @Override
    public boolean hasTileEntity(IBlockState state){
        return true;
    }

    @Override
    protected BlockStateContainer createBlockState()
    {
        return new BlockStateContainer(this, COMPRESSED, ELEMENT);
    }

    @Override
    public ItemStack getItem(World worldIn, BlockPos pos, IBlockState state){
        int i = state.getValue(ELEMENT);
        if (state.getValue(COMPRESSED)){
            i += 8;
        }
        return new ItemStack(this, 1, i);
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state){
        int tileID = state.getValue(ELEMENT);
        if(state.getValue(COMPRESSED))
            tileID += 8;
        if(tileID > 15){
            return null;
        }
        return new SolarTileEntity();
    }

    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    @Override
    public IBlockState getStateFromMeta(int meta){
        boolean comp = false;
        if(meta > 15)
            return getDefaultState();
        if(meta > 7){
            meta -= 8;
            comp = true;
        }
        return getDefaultState().withProperty(ELEMENT, meta).withProperty(COMPRESSED, comp);
    }

    @Override
    public int getMetaFromState(IBlockState state){
        int tileID = state.getValue(ELEMENT);
        if(state.getValue(COMPRESSED))
            tileID += 8;
        if(tileID > 15){
            return 0;
        }
        return tileID;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand){
        ItemStack stack = placer.getHeldItem(hand);
        return getStateFromMeta(stack.getItemDamage());
    }

    @Override
    public boolean hasItemBlock(){
        return true;
    }

}
