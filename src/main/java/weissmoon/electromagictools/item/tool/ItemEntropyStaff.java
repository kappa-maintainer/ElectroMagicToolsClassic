package weissmoon.electromagictools.item.tool;

import ic2.api.classic.item.IDamagelessElectricItem;
import ic2.api.item.ElectricItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.FurnaceRecipes;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import weissmoon.core.item.WeissItem;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Strings;
import weissmoon.electromagictools.network.FlameParticleMessage;
import weissmoon.electromagictools.network.PacketHandler;

import javax.annotation.Nonnull;

import static weissmoon.electromagictools.util.ItemHelper.getChargedItem;
import static weissmoon.electromagictools.util.ItemHelper.getElectricDurability;

/**
 * Created by Weissmoon on 10/20/20.
 */
public class ItemEntropyStaff extends WeissItem implements IDamagelessElectricItem{

    ResourceLocation sound = new ResourceLocation("block.fire.extinguish");
    public int operationEnergyCost;
    public int maxCharge;
    public int transferLimit;
    public int tier;

    public ItemEntropyStaff() {
        super(Strings.Items.ENTROPY_STAFF_NAME);

        operationEnergyCost = 600;
        maxCharge = 30000;
        transferLimit = 200;
        tier = 2;
        setCreativeTab(ElectroMagicTools.EMTtab);
        setMaxStackSize(1);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        ItemStack stack = player.getHeldItem(hand);
        if (ElectricItem.manager.canUse(stack, operationEnergyCost) || player.isCreative()){
            IBlockState state = worldIn.getBlockState(pos);
            ItemStack toSmelt = state.getBlock().getPickBlock(state, new RayTraceResult(RayTraceResult.Type.BLOCK, new Vec3d(hitX, hitY, hitZ), facing, pos), worldIn, pos, player);
            ItemStack result = FurnaceRecipes.instance().getSmeltingResult(toSmelt);
            if(result.getItem() instanceof ItemBlock){
                IBlockState toPlace = ((ItemBlock)result.getItem()).getBlock().getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, result.getItemDamage(), player, hand);
                if(worldIn.setBlockState(pos, toPlace, 3)){
                    worldIn.playSound(player, pos, new SoundEvent(sound), SoundCategory.BLOCKS, 1, 1);
                    if(!worldIn.isRemote){
                        PacketHandler.INSTANCE.sendToDimension(new FlameParticleMessage(pos), worldIn.provider.getDimension());
                    }
                    if(!player.isCreative())
                        ElectricItem.manager.use(stack, operationEnergyCost, player);
                    //Todo smelt stat
                    return EnumActionResult.SUCCESS;
                }
            }
        }
        return EnumActionResult.PASS;
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> list) {
        if (isInCreativeTab(tab)){
            ItemStack stack = new ItemStack(this, 1, 0);
            list.add(stack);
            list.add(getChargedItem(this, 1));
        }
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack){
        return getElectricDurability(stack);
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack){
        return false;
    }

    @Override
    public double getMaxCharge(ItemStack stack){
        return maxCharge;
    }

    @Override
    public int getTier(ItemStack stack){
        return tier;
    }

    @Override
    public double getTransferLimit(ItemStack stack){
        return transferLimit;
    }
}
