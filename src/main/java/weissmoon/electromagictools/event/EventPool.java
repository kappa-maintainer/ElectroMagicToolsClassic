package weissmoon.electromagictools.event;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import weissmoon.electromagictools.item.ModItems;

import static weissmoon.electromagictools.item.ItemOneRing.CORRUPTION_NBT_TAG;
import static weissmoon.electromagictools.item.ItemOneRing.playersWithRing;
import static weissmoon.electromagictools.item.armour.boots.ItemElectricBootsTraveller.playersWithStepUp;

/**
 * Created by Weissmoon on 9/19/19.
 */
public class EventPool {

    @SubscribeEvent
    public void playerLoggedOut(PlayerEvent.PlayerLoggedOutEvent event) {
        String username = event.player.getName();
        playersWithStepUp.remove(username);
        if(playersWithRing.containsKey(username)) {
            playersWithRing.remove(username);
            if (!event.player.capabilities.isCreativeMode)
                (event.player).capabilities.disableDamage = false;
            event.player.setInvisible(false);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onPlayerDeath(LivingDeathEvent event){
        if(event.getEntity() instanceof EntityPlayer){
            event.getEntityLiving().getEntityData().removeTag(CORRUPTION_NBT_TAG);
        }
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onMobDrop(LivingDropsEvent event){
        EntityLivingBase entity = event.getEntityLiving();
        if(entity instanceof EntityCreeper)
            if(((EntityCreeper)entity).getPowered()) {
                EntityItem item = new EntityItem(entity.world, entity.posX, entity.posY, entity.posZ);
                item.setItem(new ItemStack(ModItems.materials, 1, 1));
                event.getDrops().add(item);
            }
    }
}
