package weissmoon.electromagictools.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import weissmoon.core.block.WeissBlock;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.block.tile.EssentiaGeneratorBase;
import weissmoon.electromagictools.lib.Strings;

import javax.annotation.Nullable;

/**
 * Created by Weissmoon on 9/24/20.
 */
public class EssentiaGeneratorBlock extends WeissBlock {

    public static final PropertyInteger ELEMENT = PropertyInteger.create("element", 0, 3);
//                aspect = Aspect.ENERGY;
//                aspect = Aspect.AIR;
//                aspect = Aspect.AURA;
//                aspect = Aspect.FIRE;

    public EssentiaGeneratorBlock() {
        super(Strings.Blocks.ESSENTIA_GENERATOR_BLOCK_NAME, Material.IRON);
        setHardness(5.5F);
        setDefaultState(blockState.getBaseState().withProperty(ELEMENT, 0));
        setCreativeTab(ElectroMagicTools.EMTtab);
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

    @Override
    public boolean hasTileEntity(IBlockState state){
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state){
        return new EssentiaGeneratorBase(state.getValue(ELEMENT));
    }

    @SuppressWarnings("DeprecatedIsStillUsed")
    @Deprecated
    @Override
    public IBlockState getStateFromMeta(int meta){
        return getDefaultState().withProperty(ELEMENT, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state){
        int tileID = state.getValue(ELEMENT);
        return tileID;
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand){
        ItemStack stack = placer.getHeldItem(hand);
        return getStateFromMeta(stack.getItemDamage());
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }

    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return face == EnumFacing.UP || face == EnumFacing.DOWN;
    }
}
