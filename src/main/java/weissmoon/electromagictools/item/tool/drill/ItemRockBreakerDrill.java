package weissmoon.electromagictools.item.tool.drill;

import ic2.api.item.ElectricItem;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraftforge.common.ForgeHooks;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;
import thaumcraft.common.lib.utils.BlockUtils;
import weissmoon.core.helper.RNGHelper;
import weissmoon.electromagictools.advancements.RockbreakerTrigger;
import weissmoon.electromagictools.lib.Strings;

import javax.annotation.Nonnull;

import static weissmoon.electromagictools.util.BlockUtils.breakBlock;
import static weissmoon.electromagictools.util.ItemHelper.getChargedItem;

/**
 * Created by Weissmoon on 9/11/20.
 */
public class ItemRockBreakerDrill extends ItemThaumiumDrill {
    private EnumFacing.Axis side;
    public ItemRockBreakerDrill(){
        super(ToolMaterial.DIAMOND, Strings.Items.ROCKBREAKER_DRILL_NAME);
        maxCharge = 900000;
        cost = 800;//todo
        tier = 2;
        transferLimit = 1000;
        efficiency = 25F;
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player){
        side = BlockUtils.getTargetBlock(player.world, player, false, false, 10).sideHit.getAxis();
        return false;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving){
        if(entityLiving.isSneaking()){
            return super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
        }
        if(!(entityLiving instanceof EntityPlayer))
            return true;

        if(ForgeHooks.isToolEffective(worldIn, pos, stack)){
            int x = 0, y = 0, z = 0;
            h:
            for(int a = -1; a < 2; a++){
                for(int b = -1; b < 2; b++){
                    if(side == EnumFacing.Axis.X){
                        y = a;
                        z = b;
                    }else if(side == EnumFacing.Axis.Y){
                        x = a;
                        z = b;
                    }else{
                        x = a;
                        y = b;
                    }
                    BlockPos effectPos = new BlockPos(pos).add(x, y, z);
                    if((a == 0 && b == 0) || !ForgeHooks.isToolEffective(worldIn, effectPos, stack)){
                        continue;
                    }
                    EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByID(35), stack);
                    if (ElectricItem.manager.canUse(stack, cost)) {
                        IBlockState iblockstate = worldIn.getBlockState(effectPos);
                        BlockEvent.BreakEvent event = new BlockEvent.BreakEvent(worldIn, effectPos, iblockstate, (EntityPlayer) entityLiving);
                        MinecraftForge.EVENT_BUS.post(event);
                        if (!event.isCanceled()){
                            if (breakBlock(worldIn, effectPos, (EntityPlayer) entityLiving, stack, event.getExpToDrop())) {
                                ElectricItem.manager.use(stack, cost, entityLiving);
                            }
                        }
                    }
                }
            }
        }
        return true;
    }


    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ){
        if(!player.isSneaking() || player.capabilities.isCreativeMode)
            return EnumActionResult.PASS;
        else{
            if(ElectricItem.manager.getCharge(player.getHeldItem(hand)) == 0){
                if(worldIn instanceof WorldServer){
                    //hurt player
                    player.attackEntityFrom(DamageSource.FIREWORKS, 0.2f);
                    //really hurt player
                    float damageAmount = (float)RNGHelper.getRNGIntClamp((int)player.getMaxHealth() / 2, (int)player.getMaxHealth());
                    if(damageAmount != 0) {
                        float f = damageAmount;
                        damageAmount = Math.max(damageAmount - player.getAbsorptionAmount(), 0.0F);
                        player.setAbsorptionAmount(player.getAbsorptionAmount() - (f - damageAmount));
                        if (damageAmount != 0.0F)
                        {
                            float health = player.getHealth();
                            player.getCombatTracker().trackDamage(new DamageSource("explosion"), health, damageAmount);
                            if(health < damageAmount){
                                //checkTotemDeathProtection START
                                ItemStack itemstack = null;
                                for (EnumHand enumhand : EnumHand.values())
                                {
                                    ItemStack itemstack1 = player.getHeldItem(enumhand);

                                    if (itemstack1.getItem() == Items.TOTEM_OF_UNDYING)
                                    {
                                        itemstack = itemstack1.copy();
                                        itemstack1.shrink(1);
                                        break;
                                    }
                                }
                                EntityPlayerMP entityplayermp = (EntityPlayerMP)player;
                                if (itemstack != null){
                                        entityplayermp.addStat(StatList.getObjectUseStats(Items.TOTEM_OF_UNDYING));
                                        CriteriaTriggers.USED_TOTEM.trigger(entityplayermp, itemstack);

                                        player.setHealth(1.0F);
                                        player.clearActivePotions();
                                        player.addPotionEffect(new PotionEffect(MobEffects.REGENERATION, 900, 1));
                                        player.addPotionEffect(new PotionEffect(MobEffects.ABSORPTION, 100, 1));
                                        player.world.setEntityState(player, (byte)35);
                                        //checkTotemDeathProtection END
                                }else{
                                    //Kill player
                                    SoundEvent soundevent = SoundEvents.ENTITY_PLAYER_DEATH;

                                    if (soundevent != null) {
                                        player.playSound(soundevent, 1, (RNGHelper.getRNGFloat() - RNGHelper.getRNGFloat()) * 0.2F + 1.0F);
                                    }

                                    player.onDeath(new DamageSource("explosion"));
                                    player.setHealth(health - damageAmount);
                                    RockbreakerTrigger.INSTANCE.trigger(entityplayermp, player.getHeldItem(hand));
                                }
                            }else{
                                player.setHealth(health - damageAmount);
                            }

                            if (damageAmount < 3.4028235E37F)
                            {
                                player.addStat(StatList.DAMAGE_TAKEN, Math.round(damageAmount * 10.0F));
                            }
                        }
                    }
                }else{
                    worldIn.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_GENERIC_EXPLODE,SoundCategory.HOSTILE, 1, 1);
                }
            }
            ElectricItem.manager.use(player.getHeldItem(hand), 1000, player);
        }
        return EnumActionResult.SUCCESS;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> list) {
        if (isInCreativeTab(tab)){
            ItemStack stack = new ItemStack(this, 1, 0);
            EnumInfusionEnchantment.addInfusionEnchantment(stack, EnumInfusionEnchantment.SOUNDING, 2);
            list.add(stack);
            stack = getChargedItem(this, 1);
            EnumInfusionEnchantment.addInfusionEnchantment(stack, EnumInfusionEnchantment.SOUNDING, 2);
            list.add(stack);
        }
    }

}
