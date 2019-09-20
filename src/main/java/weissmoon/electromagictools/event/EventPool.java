package weissmoon.electromagictools.event;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;

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
}
