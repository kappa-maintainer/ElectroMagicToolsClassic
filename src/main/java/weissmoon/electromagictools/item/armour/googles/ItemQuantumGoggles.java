package weissmoon.electromagictools.item.armour.googles;

import ic2.core.platform.registry.Ic2Items;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import weissmoon.electromagictools.lib.Strings;
import weissmoon.electromagictools.lib.Textures;

import javax.annotation.Nullable;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class ItemQuantumGoggles extends ItemNanoGoggles {

    public ItemQuantumGoggles() {
        super(Strings.Items.QUANTUM_GOGGLES_NAME, ArmorMaterial.IRON);
        this.maxCharge = 1000000;
        this.transferLimit = 1200;
        this.tier = 3;
        this.energyPerDamage = 1000;
        this.visDiscount = 8;
    }

    public ItemQuantumGoggles(String name, ArmorMaterial material) {
        super(name, material);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return Textures.Armour.QUANTUM_ARMOUR_TEXTURE;
    }

    @Override
    public double getAbsorptionRatio(){
        return 1;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack){
        try {
            Ic2Items.quantumHelmet.getItem().onArmorTick(world, player, stack);
        }catch(Error error){
            error.printStackTrace();
        }
    }
}
