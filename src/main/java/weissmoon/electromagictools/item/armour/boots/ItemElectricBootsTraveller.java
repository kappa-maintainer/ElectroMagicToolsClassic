package weissmoon.electromagictools.item.armour.boots;

import ic2.api.classic.item.IDamagelessElectricItem;
import ic2.api.classic.item.IElectricTool;
import ic2.api.item.ElectricItem;
import ic2.api.item.IMetalArmor;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.items.IVisDiscountGear;
import thaumcraft.common.lib.events.PlayerEvents;
import weissmoon.core.client.render.IIconRegister;
import weissmoon.core.item.armour.ItemArmourBase;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Reference;
import weissmoon.electromagictools.lib.Strings;
import weissmoon.electromagictools.lib.Textures;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

import static weissmoon.electromagictools.util.ItemHelper.getChargedItem;
import static weissmoon.electromagictools.util.ItemHelper.getElectricDurability;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class ItemElectricBootsTraveller extends ItemArmourBase implements IDamagelessElectricItem, IVisDiscountGear, IMetalArmor, ISpecialArmor, IElectricTool {

    protected float jumpBonus, speedBonus;
    protected int tier, energyPerDamage, visDiscount, maxCharge, transferLimit;

    private static final Map<ItemStack, Integer> counter = new HashMap<>(10);

    public ItemElectricBootsTraveller(){
        this(Strings.Items.ELECTRIC_BOOTS_NAME, ArmorMaterial.IRON, 10000, 100, 0.16F, 0.0225F, 1, 1000, 2);
        MinecraftForge.EVENT_BUS.register(this);
    }

    protected ItemElectricBootsTraveller(String name, ArmorMaterial materialIn, int maxCharge, int transferLimit, float jumpBonus, float speedBonus, int tier, int energyPerDamage, int visDiscount) {
        super(name, materialIn, 0, EntityEquipmentSlot.FEET);
        setNoRepair();
        setMaxDamage(0);
        setCreativeTab(ElectroMagicTools.EMTtab);
        this.maxCharge = maxCharge;
        this.transferLimit = transferLimit;
        this.jumpBonus = jumpBonus;
        this.tier = tier;
        this.energyPerDamage = energyPerDamage;
        this.visDiscount = visDiscount;
        this.speedBonus = speedBonus;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return Textures.Armour.ELECTRIC_ARMOUR_TEXTURE;
    }

    @Override
    public CreativeTabs[] getCreativeTabs(){
        return new CreativeTabs[]{ElectroMagicTools.EMTtab, CreativeTabs.COMBAT};
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
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack){
        return getElectricDurability(stack);
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }

    @Override
    public double getMaxCharge(ItemStack stack) {
        return maxCharge;
    }

    @Override
    public int getTier(ItemStack stack) {
        return tier;
    }

    @Override
    public double getTransferLimit(ItemStack stack) {
        return transferLimit;
    }

    @Override
    public int getVisDiscount(ItemStack stack, EntityPlayer player) {
        return visDiscount;
    }

    @Override
    public boolean isMetalArmor(ItemStack itemstack, EntityPlayer player) {
        return true;
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, @Nonnull ItemStack armor, DamageSource source, double damage, int slot) {
        if(source.isUnblockable()){
            return new ISpecialArmor.ArmorProperties(0,0, 0);
        }else{
            double absorptionRatio = 0.15 * getAbsorptionRatio();
            double damageLimit = (25 * ElectricItem.manager.getCharge(armor)) / energyPerDamage;
            return new ISpecialArmor.ArmorProperties(0, absorptionRatio, (int)damageLimit);
        }
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, @Nonnull ItemStack armor, int slot) {
        if(ElectricItem.manager.getCharge(armor) >= energyPerDamage)
            return (int) Math.round(3 * getAbsorptionRatio());
        return 0;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, @Nonnull ItemStack stack, DamageSource source, int damage, int slot) {
        ElectricItem.manager.discharge(stack, damage * energyPerDamage, Integer.MAX_VALUE, true, false, false);
    }

    protected double getAbsorptionRatio(){
        return 0.5;
    }

    @SubscribeEvent
    public void onPlayerJump(LivingJumpEvent event){
        if(event.getEntityLiving() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)event.getEntityLiving();
            if(playerHasBoots(player)){
                ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
                double discharge = ElectricItem.manager.discharge(stack, ((ItemElectricBootsTraveller)stack.getItem()).jumpBonus * 100, getTier(stack), true, false, true);
                boolean energyRecieved = discharge != 0;
                if(energyRecieved){
                    player.motionY += ((ItemElectricBootsTraveller)stack.getItem()).jumpBonus;
                    player.fallDistance -= (float) (((ItemElectricBootsTraveller)stack.getItem()).jumpBonus * 8.5);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerFall(LivingFallEvent event){
        if(event.getEntityLiving() instanceof EntityPlayer){
            EntityPlayer player = (EntityPlayer)event.getEntityLiving();
            if(playerHasBoots(player)){
                ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
                double discharge = ElectricItem.manager.discharge(stack, event.getDistance() * 100, getTier(stack), true, false, true);
                if(discharge != 0){
                    if (stack.getItem() instanceof ItemNanoBootsTraveller){
                        event.setDamageMultiplier(0.0F);
                    } else {
                        event.setDamageMultiplier(0.2F);
                    }
                }
            }
        }
    }


    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack){
        if ((!player.capabilities.isFlying)) {
            double charge;
            if (world.isRemote) {
                charge = ElectricItem.manager.canUse(stack, 1.2) ? 1.2 : 0;
            } else {
                charge = ElectricItem.manager.discharge(stack, 1.2, ((ItemElectricBootsTraveller) stack.getItem()).getTier(stack), true, false, true);
            }
            if (charge == 0) return;

            if (player.isSprinting()) {
                player.moveRelative(0, 0, ((ItemElectricBootsTraveller) stack.getItem()).getSpeedBonus(), 2.0F);
                ElectricItem.manager.discharge(stack, charge, ((ItemElectricBootsTraveller) stack.getItem()).getTier(stack), true, false, false);
            } else if (player.motionX != 0 || player.motionZ != 0) {
                ElectricItem.manager.discharge(stack, 1.0F, ((ItemElectricBootsTraveller) stack.getItem()).getTier(stack), true, false, false);

                if (world.isRemote) {
                    if (player.onGround) {
                        float bonus = 0.05F;
                        if (player.isInWater()) {
                            bonus /= 4.0F;
                        }

                        player.moveRelative(0.0F, 0.0F, bonus, 1.0F);
                    } else {
                        if (player.isInWater()) {
                            player.moveRelative(0.0F, 0.0F, 0.025F, 1.0F);
                        }

                        player.jumpMovementFactor = 0.05F;
                    }
                }
            }

            if (player.isSneaking())
                player.stepHeight = 0.61F;
            else
                player.stepHeight = 1.25F;
        } else {
            player.stepHeight = 0.6F;
        }

    }

    public float getSpeedBonus(){
        return speedBonus;
    }

    public boolean playerHasBoots(EntityPlayer player){
        ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        return stack.getItem() instanceof ItemElectricBootsTraveller;
    }

    @Override
    public EnumEnchantmentType getType(ItemStack itemStack) {
        return EnumEnchantmentType.ARMOR_FEET;
    }

    @Override
    public boolean isSpecialSupported(ItemStack itemStack, Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean isExcluded(ItemStack itemStack, Enchantment enchantment) {
        return enchantment == Enchantments.MENDING || enchantment == Enchantments.THORNS;
    }
}
