package weissmoon.electromagictools.item.armour;

import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import ic2.api.item.IMetalArmor;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.ISpecialArmor;
import thaumcraft.api.items.IGoggles;
import thaumcraft.api.items.IVisDiscountGear;
import weissmoon.core.item.armour.ItemArmourBase;
import weissmoon.electromagictools.lib.Strings;
import weissmoon.electromagictools.lib.Textures;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class ItemElectricGoggles extends ItemArmourBase implements IElectricItem, IVisDiscountGear, IGoggles, IMetalArmor, ISpecialArmor{

    protected double maxCharge, transferLimit;
    protected int tier, energyPerDamage, visDiscount;

    public ItemElectricGoggles(){
        this(Strings.Items.ELECTRIC_GOGGLES_NAME, ArmorMaterial.IRON);
    }

    protected ItemElectricGoggles(String name, ArmorMaterial material) {
        super(name , material, 0, EntityEquipmentSlot.HEAD);
        this.maxCharge = 100000;
        this.transferLimit = 100;
        this.tier = 2;
        this.energyPerDamage = 1000;
        this.visDiscount = 4;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return Textures.Armour.ELECTRIC_GOOGLE_TEXTURE;
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
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
}
