package weissmoon.electromagictools.item.tool.chainsaw;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftMaterials;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;
import thaumcraft.common.lib.utils.BlockUtils;
import thaumcraft.common.lib.utils.Utils;
import weissmoon.electromagictools.lib.Strings;
import weissmoon.electromagictools.util.GenericHelper;

import javax.annotation.Nonnull;

import static weissmoon.electromagictools.util.ItemHelper.getChargedItem;

/**
 * Created by Weissmoon on 9/23/19.
 */
public class ItemStreamChainsaw extends ItemDiamondChainsaw {

    public ItemStreamChainsaw() {
        super(ThaumcraftMaterials.TOOLMAT_ELEMENTAL, 12, -3.2F, Strings.Items.STREAM_CHAINSAW_NAME, 1000000, 400, 900, 2);
        efficiency = 25F;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return 7200;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn) {
        if (IC2.platform.isSimulating()){
            if (IC2.keyboard.isModeSwitchKeyDown(player)) {
                return super.onItemRightClick(world, player, handIn);
            }
            player.setActiveHand(handIn);
            return ActionResult.newResult(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
        }
        return ActionResult.newResult(EnumActionResult.PASS, player.getHeldItem(handIn));
    }

    @Override
    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
        World worldIn = player.getEntityWorld();
        if (worldIn.getTotalWorldTime() % 2 == 0) {
            if (!(player instanceof EntityPlayer)) {
                return;
            }
            if (!player.isHandActive()){
//                ElectroMagicTools.logger.info("hand no longer active");
                return;
            }
            double x = player.posX;
            double y = player.posY + 1.5;
            double z = player.posZ;
            int pulled = 0;
            for (EntityItem item : worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(x, y, z, x + 1, y + 1, z + 1).grow(10))) {
                if (item.getEntityData().getBoolean("PreventRemoteMovement")) {
                    continue;
                }
                if (!canPull(stack) || pulled > 200) {
                    break;
                }
//                item.addVelocity((x - item.posX) * speed, (y - item.posY) * speed, (z - item.posZ) * speed);
                double xd = item.posX - player.posX;
                double yd = item.posY - player.posY + (double)(player.height / 2.0F);
                double zd = item.posZ - player.posZ;
                double distance = Math.sqrt(xd * xd + yd *yd + zd * zd);
                xd /= distance;
                yd /= distance;
                zd /= distance;
                item.motionX -= GenericHelper.minMaxClamp(-0.25, 0.25, xd);
                item.motionY -= GenericHelper.minMaxClamp(-0.25, 0.25, yd);
                item.motionZ -= GenericHelper.minMaxClamp(-0.25, 0.25, zd);
                ElectricItem.manager.use(stack, (float)operationEnergyCost / 2, player);
                pulled++;
//                if (worldIn.isRemote) {
                FXDispatcher.INSTANCE.crucibleBubble((float)item.posX + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2F, (float)item.posY + item.height + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2F, (float)item.posZ + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2F, 0.33F, 0.33F, 1.0F);
//                }
            }
        }
    }

    @Override
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
        return  oldStack.getItem() == newStack.getItem();
//        return StackUtil.isStackEqual(oldStack, newStack, false, false);
    }

    public boolean canPull(ItemStack stack) {
        return ElectricItem.manager.canUse(stack, (float)operationEnergyCost / 2);
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack){
        return EnumAction.BOW;
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        entityLiving.resetActiveHand();
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        if(!player.isSneaking() && Utils.isWoodLog(world, pos) && ElectricItem.manager.canUse(player.getHeldItemMainhand(), operationEnergyCost)){
            if(!world.isRemote){
                if(BlockUtils.breakFurthestBlock(world, pos, world.getBlockState(pos), player)) {
                    world.playSound(null, pos, new SoundEvent(new ResourceLocation("thaumcraft:bubble")), SoundCategory.PLAYERS, 0.15F, 1.0F);
                    ElectricItem.manager.use(player.getHeldItemMainhand(), operationEnergyCost, player);
                }
            }
            player.swingArm(EnumHand.MAIN_HAND);
            return EnumActionResult.SUCCESS;
        }
        return super.onItemUse(player, world, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player){
        IBlockState state = player.world.getBlockState(pos);
        if(!player.isSneaking() && Utils.isWoodLog(player.world, pos) && ElectricItem.manager.canUse(player.getHeldItemMainhand(), this.operationEnergyCost)){
            if(!player.world.isRemote){
                if(BlockUtils.breakFurthestBlock(player.world, pos, state, player)){
                    player.world.playSound(null, pos, new SoundEvent(new ResourceLocation("thaumcraft:bubble")), SoundCategory.PLAYERS, 0.15F, 1.0F);
                    ElectricItem.manager.use(itemstack, this.operationEnergyCost, player);
                    return true;
                }
            }
        }
        return super.onBlockStartBreak(itemstack, pos, player);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> list) {
        if (isInCreativeTab(tab)){
            ItemStack stack = new ItemStack(this, 1, 0);
            EnumInfusionEnchantment.addInfusionEnchantment(stack, EnumInfusionEnchantment.BURROWING, 1);
            EnumInfusionEnchantment.addInfusionEnchantment(stack, EnumInfusionEnchantment.COLLECTOR, 1);
            list.add(stack);
            stack = getChargedItem(this, 1);
            EnumInfusionEnchantment.addInfusionEnchantment(stack, EnumInfusionEnchantment.BURROWING, 1);
            EnumInfusionEnchantment.addInfusionEnchantment(stack, EnumInfusionEnchantment.COLLECTOR, 1);
            list.add(stack);
        }
    }
}
