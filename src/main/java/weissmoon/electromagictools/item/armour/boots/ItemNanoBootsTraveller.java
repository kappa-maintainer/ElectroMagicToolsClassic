package weissmoon.electromagictools.item.armour.boots;

import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.inventory.EntityEquipmentSlot;
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
        super(Strings.Items.NANO_BOOTS_NAME, ArmorMaterial.IRON);
        this.maxCharge = 100000;
        this.transferLimit = 1600;
        this.jumpBonus = 0.4;
        this.speedBonus = 0.04F;
        this.tier = 2;
        this.energyPerDamage = 800;
        this.visDiscount = 4;
    }

    ItemNanoBootsTraveller(String name, ArmorMaterial materialIn) {
        super(name, materialIn);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return Textures.Armour.NANO_ARMOUR_TEXTURE;
    }

    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        if (source == DamageSource.FALL) {
            int energyPerDamage = this.energyPerDamage;
            int damageLimit = (int)(energyPerDamage > 0 ? ElectricItem.manager.discharge(armor, 2.147483647E9D, 2147483647, true, false, true) / (double)energyPerDamage : 0.0D);
            float absorbtion = damage < 8.0D ? 1.0F : 0.875F;
            absorbtion *= IC2.config.getFloat("electricSuitAbsorbtionScale");
            return new ArmorProperties(10, (double)absorbtion, damageLimit);
        } else {
            return super.getProperties(player, armor, source, damage, slot);
        }
    }

    @Override
    protected double getAbsorptionRatio() {
        return 0.9;
    }
}
