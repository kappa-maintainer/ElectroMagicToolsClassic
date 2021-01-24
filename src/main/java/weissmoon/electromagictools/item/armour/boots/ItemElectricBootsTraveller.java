package weissmoon.electromagictools.item.armour.boots;

import ic2.api.classic.item.IDamagelessElectricItem;
import ic2.api.item.ElectricItem;
import ic2.api.item.IMetalArmor;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AbstractAttributeMap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.*;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.items.IVisDiscountGear;
import weissmoon.core.item.armour.ItemArmourBase;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Strings;
import weissmoon.electromagictools.lib.Textures;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import static weissmoon.electromagictools.util.ItemHelper.getChargedItem;
import static weissmoon.electromagictools.util.ItemHelper.getElectricDurability;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class ItemElectricBootsTraveller extends ItemArmourBase implements IDamagelessElectricItem, IVisDiscountGear, IMetalArmor, ISpecialArmor {

    protected float jumpBonus, speedBonus;
    protected int tier, energyPerDamage, visDiscount, maxCharge, transferLimit;

    public static final List<String> playersWithStepUp = new ArrayList<String>();
    private UUID monsterMotionUUID = UUID.fromString("29d2b7de-c2dd-4d16-a401-190a7b34eb0d");

    public ItemElectricBootsTraveller(){
        this(Strings.Items.ELECTRIC_BOOTS_NAME, ArmorMaterial.IRON);
        maxCharge = 10000;
        transferLimit = 100;
        jumpBonus = 0.16F;
        speedBonus = 0.0225F;
        tier = 1;
        energyPerDamage = 1000;
        visDiscount = 2;
        MinecraftForge.EVENT_BUS.register(this);
    }

    protected ItemElectricBootsTraveller(String name, ArmorMaterial materialIn) {
        super(name, materialIn, 0, EntityEquipmentSlot.FEET);
        setNoRepair();
        setMaxDamage(0);
        setCreativeTab(ElectroMagicTools.EMTtab);
//        maxCharge = 0;
//        transferLimit = 0;
//        jumpBonus = 0;
//        tier = 10;
//        energyPerDamage = 0;
//        visDiscount = 0;
//        speedBonus = 0;
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
        ElectricItem.manager.discharge(stack, damage * energyPerDamage, 2147483647, true, false, false);
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
                    player.fallDistance -= (((ItemElectricBootsTraveller)stack.getItem()).jumpBonus * 8.5);
                }
            }
        }
    }

    @SubscribeEvent
    public void onPlayerTick(LivingUpdateEvent event){
        if(event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            if (playersWithStepUp.contains(player.getName())) {
                if(playerHasBoots(player)){
                    ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
                    double discharge = ElectricItem.manager.discharge(stack, 1.2, ((ItemElectricBootsTraveller)stack.getItem()).getTier(stack), true, false, true);
                    boolean energyRecieved = discharge != 0;
                    //boolean trust = ElectricItem.manager.use(stack, 40, null); //Works here always return false
                    if ((!player.capabilities.isFlying) && player.moveForward > 0 && energyRecieved){
                        //boolean trust = ElectricItem.rawManager.use(stack, 40, null); //Does not work here always return false
                        ElectricItem.manager.discharge(stack, discharge, ((ItemElectricBootsTraveller)stack.getItem()).getTier(stack), true, false, false);
                        player.moveRelative(0, 0, ((ItemElectricBootsTraveller)stack.getItem()).getSpeedBonus(), 1.5F);

                        if (player.isSneaking())
                            player.stepHeight = 0.60001F;
                        else
                            player.stepHeight = 1.25F;
                    }else{
                        player.stepHeight = 0.6F;
                    }
                }else{
                    playersWithStepUp.remove(player.getName());
                    player.stepHeight = 0.6F;
                }
            }else{
                if(playerHasBoots(player)){
                    playersWithStepUp.add(player.getName());
                }
            }
        }else{
            EntityLivingBase entityLiving = event.getEntityLiving();
            ItemStack stack = entityLiving.getItemStackFromSlot(EntityEquipmentSlot.FEET);
            AbstractAttributeMap entityAttributeMap = entityLiving.getAttributeMap();
            IAttributeInstance iattributeinstance = entityAttributeMap.getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED);
            iattributeinstance.removeModifier(monsterMotionUUID);
            if(stack.getItem() instanceof ItemElectricBootsTraveller && ElectricItem.manager.getCharge(stack) > 0){

                iattributeinstance.applyModifier(new AttributeModifier(monsterMotionUUID,
                        monsterMotionUUID.toString(),
                        ((ItemElectricBootsTraveller)stack.getItem()).getSpeedBonus() - 1,
                        2));
            }
        }
    }

    public float getSpeedBonus(){
        return speedBonus;
    }

    public boolean playerHasBoots(EntityPlayer player){
        ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
        return stack.getItem() instanceof ItemElectricBootsTraveller;
    }
}
