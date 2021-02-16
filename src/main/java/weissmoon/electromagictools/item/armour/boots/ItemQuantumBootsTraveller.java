package weissmoon.electromagictools.item.armour.boots;

<<<<<<< HEAD
import ic2.api.item.ElectricItem;
import ic2.core.IC2;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingFallEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
=======
import net.minecraft.entity.Entity;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
>>>>>>> master
import weissmoon.electromagictools.lib.Strings;
import weissmoon.electromagictools.lib.Textures;

import javax.annotation.Nullable;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class ItemQuantumBootsTraveller extends ItemNanoBootsTraveller {

    public ItemQuantumBootsTraveller(){
        super(Strings.Items.QUANTUM_BOOTS_NAME, ArmorMaterial.IRON);
<<<<<<< HEAD
        this.maxCharge = 1000000;
        this.transferLimit = 1200;
        this.jumpBonus = 0.67;
        this.speedBonus = 0.067F;
        this.tier = 3;
        this.energyPerDamage = 1000;
        this.visDiscount = 5;
        MinecraftForge.EVENT_BUS.register(this);
=======
        maxCharge = 1000000;
        transferLimit = 12000;
        jumpBonus = 0.67F;
        speedBonus = 0.067F;
        tier = 3;
        energyPerDamage = 20000;
        visDiscount = 5;
>>>>>>> master
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type)
    {
        return Textures.Armour.QUANTUM_ARMOUR_TEXTURE;
    }

    @Override
    protected double getAbsorptionRatio(){
        return 1;
    }
<<<<<<< HEAD

    public ArmorProperties getProperties(EntityLivingBase player, ItemStack armor, DamageSource source, double damage, int slot) {
        if (source == DamageSource.FALL) {
            int energyPerDamage = this.energyPerDamage;
            int damageLimit = (int)(energyPerDamage > 0 ? ElectricItem.manager.discharge(armor, 2.147483647E9D, 2147483647, true, false, true) / (double)energyPerDamage : 0.0D);
            return new ArmorProperties(10, (double)(1.0F * IC2.config.getFloat("electricSuitAbsorbtionScale")), damageLimit);
        } else {
            return super.getProperties(player, armor, source, damage, slot);
        }
    }

    public void damageAbsorbed(EntityPlayer player, int damage) {
        IC2.achievements.issueStat(player, "quantumArmorDamageTaken", damage);
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
=======
>>>>>>> master
}
