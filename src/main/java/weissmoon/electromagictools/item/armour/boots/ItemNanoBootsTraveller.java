package weissmoon.electromagictools.item.armour.boots;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import weissmoon.electromagictools.lib.Strings;
import weissmoon.electromagictools.lib.Textures;

import javax.annotation.Nullable;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class ItemNanoBootsTraveller extends ItemElectricBootsTraveller {

    public ItemNanoBootsTraveller(){
        super(Strings.Items.NANO_BOOTS_NAME, ArmorMaterial.IRON, 100000, 1600, 0.4F, 0.04F, 2, 800, 4);
    }

    protected ItemNanoBootsTraveller(String name, ArmorMaterial materialIn, int maxCharge, int transferLimit, float jumpBonus, float speedBonus, int tier, int energyPerDamage, int visDiscount) {
        super(name, materialIn, maxCharge, transferLimit, jumpBonus, speedBonus, tier, energyPerDamage, visDiscount);
    }

    @Override
    public net.minecraftforge.common.IRarity getForgeRarity(ItemStack stack){
        return EnumRarity.UNCOMMON;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type){
        return Textures.Armour.NANO_ARMOUR_TEXTURE;
    }

    @Override
    protected double getAbsorptionRatio(){
        return 0.9;
    }

    public int getEnergyPerDamage(){
        return energyPerDamage;
    }
}
