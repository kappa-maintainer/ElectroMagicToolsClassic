package weissmoon.electromagictools.block;

import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.items.ItemStackHandler;
import thaumcraft.api.items.IRechargable;
import weissmoon.core.block.WeissBlock;
import weissmoon.core.helper.InventoryHelper;
import weissmoon.electromagictools.block.tile.TileIndustrialChargePedestal;
import weissmoon.electromagictools.lib.Strings;

import javax.annotation.Nullable;

/**
 * Created by Weissmoon on 11/27/20.
 */
public class IndustrialChargePedestal extends WeissBlock {
    public IndustrialChargePedestal() {
        super(Strings.Blocks.INDUSTRIAL_CHARGE_PEDESTAL, Material.IRON);
    }

    @Override
    public boolean hasTileEntity(IBlockState state){
        return true;
    }

    @Nullable
    @Override
    public TileEntity createTileEntity(World world, IBlockState state){
        return new TileIndustrialChargePedestal();
    }

    @Override
    public boolean onBlockActivated(World worldIn, BlockPos pos, IBlockState state, EntityPlayer playerIn, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        ItemStack stack = playerIn.getHeldItem(hand);
        TileEntity tile = worldIn.getTileEntity(pos);

        if(tile instanceof TileIndustrialChargePedestal) {
            ItemStackHandler stackHandler = ((TileIndustrialChargePedestal) tile).getItemStackHandler();
            if(stackHandler.getStackInSlot(0).isEmpty()) {
                if(!(stack.getItem() instanceof IRechargable))
                    return false;

                ItemStack retrunStack = stackHandler.insertItem(0, stack, true);
                if (!ItemStack.areItemStacksEqual(retrunStack, stack)) {
                    retrunStack = stackHandler.insertItem(0, stack, false);
                    playerIn.setHeldItem(hand, retrunStack);
                    return true;
                }
            }else{
                ItemStack returnStack = stackHandler.extractItem(0, 1, false);
                if(!worldIn.isRemote)
                    InventoryHelper.givePlayerOrDropItemStack(returnStack, playerIn);
                return true;
            }
        }
        return false;
    }

    public boolean doesSideBlockRendering(IBlockState state, IBlockAccess world, BlockPos pos, EnumFacing face)
    {
        return face == EnumFacing.DOWN;
    }
}
