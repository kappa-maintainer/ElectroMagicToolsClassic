package weissmoon.electromagictools.item.armour.wings;

import ic2.api.classic.item.IDamagelessElectricItem;
import ic2.api.item.ElectricItem;
import ic2.api.util.Keys;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.NonNullList;
import net.minecraft.world.World;
import net.minecraftforge.common.ISpecialArmor;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import weissmoon.core.utils.NBTHelper;
import weissmoon.electromagictools.client.WingsModelRenderer;
import weissmoon.electromagictools.item.ModItems;
import weissmoon.electromagictools.lib.Strings;

import javax.annotation.Nonnull;

import static weissmoon.electromagictools.util.ItemHelper.getChargedItem;
import static weissmoon.electromagictools.util.ItemHelper.getElectricDurability;

/**
 * Created by Weissmoon on 8/19/20.
 */
public class ItemNanoWings extends ItemThaumiumWings implements IDamagelessElectricItem, ISpecialArmor {

    protected int energyPerDamage, energyPerJump, maxCharge, transferLimit;

    public ItemNanoWings(){
        this(Strings.Items.NANO_WINGS_GAME,  ArmorMaterial.IRON);
        fallReduction = 0.3F;
        fallDamage =
        propulsion = 0.25F;
        tier = 2;
        visDiscount = 5;
        maxCharge = 1000000;
        transferLimit = 1600;
        energyPerDamage = 5000;
        energyPerJump = 6;
    }

    protected ItemNanoWings(String name, ArmorMaterial materialIn) {
        super(name, materialIn);
        setNoRepair();
        setMaxDamage(0);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped original) {
        if(model == null){
            ItemStack s = new ItemStack(ModItems.materials, 1, 16);
            NBTHelper.setInteger(s, "icon", 1);
            model = new WingsModelRenderer(s, 1);
        }
        return model;
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

    public void tickWings(EntityPlayer player, ItemStack wings, World world){
        byte flap = NBTHelper.getByte(wings, "flap");

        if(!Keys.instance.isJumpKeyDown(player) && flap > 0)
            ElectricItem.manager.use(wings, energyPerJump * flap, player);

        super.tickWings(player, wings, world);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack){
        return getElectricDurability(stack);
    }


    public int getEnergyPerDamage(){
        return energyPerDamage;
    }

    protected double getAbsorptionRatio(){
        return 0.9;
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
            return (int) Math.round(5 * getAbsorptionRatio());
        return 0;
    }

    @Override
    public void damageArmor(EntityLivingBase entity, @Nonnull ItemStack stack, DamageSource source, int damage, int slot) {
        ElectricItem.manager.discharge(stack, damage * energyPerDamage, 2147483647, true, false, false);
    }
}
