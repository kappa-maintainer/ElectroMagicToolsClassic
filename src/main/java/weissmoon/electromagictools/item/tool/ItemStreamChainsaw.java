package weissmoon.electromagictools.item.tool;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import ic2.core.util.misc.StackUtil;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftMaterials;
import thaumcraft.client.fx.FXDispatcher;
import thaumcraft.common.lib.utils.BlockUtils;
import thaumcraft.common.lib.utils.Utils;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Strings;

/**
 * Created by Weissmoon on 9/23/19.
 */
public class ItemStreamChainsaw extends ItemDiamondChainsaw{
    int range = 7;
    double speed = 0.04D;

    public ItemStreamChainsaw() {
        super(ThaumcraftMaterials.TOOLMAT_THAUMIUM, 20, 0F, Strings.Items.STREAM_CHAINSAW_NAME, 100000, 900, 3);
        this.operationEnergyCost = 400;
        this.efficiency = 25F;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack){
        return EnumAction.BOW;
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
                ElectroMagicTools.logger.info("hand no longer active");
                return;
            }
            double x = player.posX;
            double y = player.posY + 1.5;
            double z = player.posZ;
            int pulled = 0;
            for (EntityItem item : worldIn.getEntitiesWithinAABB(EntityItem.class, new AxisAlignedBB(x, y, z, x + 1, y
                    + 1, z + 1).grow(range))) {
                if (item.getEntityData().getBoolean("PreventRemoteMovement")) {
                    continue;
                }
                if (!canPull(stack) || pulled > 200) {
                    break;
                }
                item.addVelocity((x - item.posX) * speed, (y - item.posY) * speed, (z - item.posZ) * speed);
                ElectricItem.manager.use(stack, (float)operationEnergyCost / 2, player);
                pulled++;
                if (worldIn.isRemote) {
                    FXDispatcher.INSTANCE.crucibleBubble((float)item.posX + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2F, (float)item.posY + item.height + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2F, (float)item.posZ + (worldIn.rand.nextFloat() - worldIn.rand.nextFloat()) * 0.2F, 0.33F, 0.33F, 1.0F);
                }
            }
        }
    }

    @Override
    public boolean canContinueUsing(ItemStack oldStack, ItemStack newStack) {
        return StackUtil.isStackEqual(oldStack, newStack, false, false);
    }

    public boolean canPull(ItemStack stack) {
        return ElectricItem.manager.canUse(stack, (float)operationEnergyCost / 2);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        entityLiving.resetActiveHand();
        return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World world, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        if(!player.isSneaking() && Utils.isWoodLog(world, pos) && ElectricItem.manager.canUse(player.getHeldItemMainhand(), this.operationEnergyCost)){
            if(!world.isRemote){
                if(BlockUtils.breakFurthestBlock(world, pos, world.getBlockState(pos), player)) {
                    world.playSound(null, pos, new SoundEvent(new ResourceLocation("thaumcraft:bubble")), SoundCategory.PLAYERS, 0.15F, 1.0F);
                    ElectricItem.manager.use(player.getHeldItem(hand), operationEnergyCost, player);
                }
            }
            player.swingArm(hand);
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
}
