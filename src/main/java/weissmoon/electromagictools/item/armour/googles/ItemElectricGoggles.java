package weissmoon.electromagictools.item.armour.googles;

import ic2.api.classic.item.IDamagelessElectricItem;
import ic2.api.classic.item.IElectricTool;
import ic2.api.item.ElectricItem;
import ic2.api.item.IMetalArmor;
import ic2.core.IC2;
import ic2.core.entity.IC2DamageSource;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnumEnchantmentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Enchantments;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.items.IGoggles;
import thaumcraft.api.items.IVisDiscountGear;
import weissmoon.core.item.armour.ItemArmourBase;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Strings;
import weissmoon.electromagictools.lib.Textures;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import static ic2.core.item.armor.base.ItemElectricArmorBase.hasElectricBoots;
import static weissmoon.electromagictools.util.ItemHelper.getChargedItem;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class ItemElectricGoggles extends ItemArmourBase implements IDamagelessElectricItem, IVisDiscountGear, IGoggles, IMetalArmor, ISpecialArmor, IElectricTool {

    protected double maxCharge, transferLimit;
    protected int tier, energyPerDamage, visDiscount;

    public ItemElectricGoggles(){
        this(Strings.Items.ELECTRIC_GOGGLES_NAME, ArmorMaterial.IRON);
        this.maxCharge = 10000;
        this.transferLimit = 100;
        this.tier = 1;
        this.energyPerDamage = 500;
        this.visDiscount = 4;
    }

    protected ItemElectricGoggles(String name, ArmorMaterial material) {
        super(name , material, 0, EntityEquipmentSlot.HEAD);
        setCreativeTab(ElectroMagicTools.EMTtab);
        this.maxCharge = 0;
        this.transferLimit = 0;
        this.tier = 10;
        this.energyPerDamage = 0;
        this.visDiscount = 0;
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
        if (this.isInCreativeTab(tab)) {
            ItemStack stack = new ItemStack(this, 1, 0);
            list.add(stack);
            list.add(getChargedItem(this, 1));
        }
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
    public boolean showIngamePopups(ItemStack itemstack, EntityLivingBase player) {
        return true;
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
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack) {
        return 1.0D - ElectricItem.manager.getCharge(stack) / this.getMaxCharge(stack);
    }

    @Override
    public ArmorProperties getProperties(EntityLivingBase player, @Nonnull ItemStack armor, DamageSource source, double damage, int slot) {
        if(source.isUnblockable() && !this.isBlockingEverything()){
            return new ISpecialArmor.ArmorProperties(0,0, 0);
        } else if (source.getDamageType().equals(IC2DamageSource.electricity.getDamageType()) && IC2.config.getFlag("SpecialElectricArmor")) {
            return !hasElectricBoots(player) ? new ArmorProperties(0, 1.0D, (int)this.maxCharge - (int)ElectricItem.manager.getCharge(armor)) : new ArmorProperties(0, 1.0D, 2147483647);
        } else{
            double absorptionRatio = 0.15 * getAbsorptionRatio();
            double energyPerDamage = this.energyPerDamage;
            energyPerDamage *= IC2.config.getFloat("electricSuitEnergyCostModifier");
            absorptionRatio *= IC2.config.getFloat("electricSuitAbsorbtionScale");
            int damageLimit = (int)(energyPerDamage > 0.0D ? ElectricItem.manager.discharge(armor, 2.147483647E9D, 2147483647, true, false, true) / energyPerDamage : 0.0D);
            return new ISpecialArmor.ArmorProperties(0, absorptionRatio, damageLimit);
        }
    }

    public boolean isBlockingEverything() {
        return false;
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

    @Override
    public EnumEnchantmentType getType(ItemStack itemStack) {
        return EnumEnchantmentType.ARMOR_HEAD;
    }

    @Override
    public boolean isSpecialSupported(ItemStack itemStack, Enchantment enchantment) {
        return false;
    }

    @Override
    public boolean isExcluded(ItemStack item, Enchantment ench) {
        return ench == Enchantments.THORNS || ench == Enchantments.MENDING;
    }
}
