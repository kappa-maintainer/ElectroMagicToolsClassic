package weissmoon.electromagictools.item.armour.boots;

import ic2.api.classic.item.IDamagelessElectricItem;
import ic2.api.classic.item.IElectricTool;
import ic2.api.item.ElectricItem;
import ic2.api.item.IMetalArmor;
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
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
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

/**
 * Created by Weissmoon on 9/3/19.
 */
public class ItemElectricBootsTraveller extends ItemArmourBase implements IDamagelessElectricItem, IVisDiscountGear, IMetalArmor, ISpecialArmor, IElectricTool {

    protected double maxCharge, transferLimit, jumpBonus;
    protected float speedBonus;
    protected int tier, energyPerDamage, visDiscount;

    public static final List<String> playersWithStepUp = new ArrayList<String>();
    private UUID monsterMotionUUID = UUID.fromString("29d2b7de-c2dd-4d16-a401-190a7b34eb0d");

    public ItemElectricBootsTraveller(){
        this(Strings.Items.ELECTRIC_BOOTS_NAME, ArmorMaterial.IRON);
        this.maxCharge = 10000;
        this.transferLimit = 100;
        this.jumpBonus = 0.16;
        this.speedBonus = 0.0225F;
        this.tier = 1;
        this.energyPerDamage = 1000;
        this.visDiscount = 2;
        MinecraftForge.EVENT_BUS.register(this);
    }

    protected ItemElectricBootsTraveller(String name, ArmorMaterial materialIn) {
        super(name, materialIn, 0, EntityEquipmentSlot.FEET);
        setCreativeTab(ElectroMagicTools.EMTtab);
        this.maxCharge = 0;
        this.transferLimit = 0;
        this.jumpBonus = 0;
        this.tier = 10;
        this.energyPerDamage = 0;
        this.visDiscount = 0;
        this.speedBonus = 0;
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
    public void getSubItems (CreativeTabs tab, NonNullList<ItemStack> list){
        if (this.isInCreativeTab(tab)){
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
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1.0D - ElectricItem.manager.getCharge(stack) / this.getMaxCharge(stack);
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return true;
    }

    @Override
    public double getMaxCharge(ItemStack stack) {
        return this.maxCharge;
    }

    @Override
    public int getTier(ItemStack stack) {
        return this.tier;
    }

    @Override
    public double getTransferLimit(ItemStack stack) {
        return this.transferLimit;
    }

    @Override
    public int getVisDiscount(ItemStack stack, EntityPlayer player) {
        return this.visDiscount;
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
            double damageLimit = (25 * ElectricItem.manager.getCharge(armor)) / this.energyPerDamage;
            return new ISpecialArmor.ArmorProperties(0, absorptionRatio, (int)damageLimit);
        }
    }

    @Override
    public int getArmorDisplay(EntityPlayer player, @Nonnull ItemStack armor, int slot) {
        if(ElectricItem.manager.getCharge(armor) >= this.energyPerDamage)
            return (int) Math.round(3 * getAbsorptionRatio());
        return 0;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, @Nonnull ItemStack stack, DamageSource source, int damage, int slot) {
        ElectricItem.manager.discharge(stack, damage * this.energyPerDamage, 0, true, false, false);
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
        return this.speedBonus;
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
