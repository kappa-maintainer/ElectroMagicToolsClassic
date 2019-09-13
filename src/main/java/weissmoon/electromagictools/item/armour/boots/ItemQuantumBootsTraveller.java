package weissmoon.electromagictools.item.armour.boots;

import ic2.api.item.ElectricItem;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import weissmoon.electromagictools.lib.Strings;
import weissmoon.electromagictools.lib.Textures;

import javax.annotation.Nullable;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class ItemQuantumBootsTraveller extends ItemNanoBootsTraveller {

    public ItemQuantumBootsTraveller(){
        super(Strings.Items.QUANTUM_BOOTS_NAME, ArmorMaterial.IRON);
        this.maxCharge = 1000000;
        this.transferLimit = 12000;
        this.jumpBonus = 0.67;
        this.speedBonus = 0.067F;
        this.tier = 4;
        this.energyPerDamage = 20000;
        this.visDiscount = 5;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return Textures.Armour.QUANTUM_ARMOUR_TEXTURE;
    }

    @Override
    protected double getAbsorptionRatio() {
        return 1;
    }

    @SubscribeEvent
    public void onNanoBootsFall(LivingFallEvent event){
        if (event.getEntityLiving() instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) event.getEntityLiving();
            ItemStack stack = player.getItemStackFromSlot(EntityEquipmentSlot.FEET);
            if(stack.getItem() instanceof ItemNanoBootsTraveller) {
                if (ElectricItem.manager.use(stack, energyPerDamage, player))
                    event.setCanceled(true);
            }
        }
    }
}
