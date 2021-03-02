package weissmoon.electromagictools.event;

import com.google.common.collect.Maps;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import weissmoon.electromagictools.advancements.CremationTrigger;

import java.util.IdentityHashMap;

/**
 * Created by Weissmoon on 2/3/21.
 */
public class Cremation{

    public static final IdentityHashMap<EntityLightningBolt, EntityPlayer> lightning = Maps.newIdentityHashMap();
    public static final IdentityHashMap<EntityLivingBase, EntityPlayer> struck = Maps.newIdentityHashMap();
    private static int tickCount = 0;

    @SubscribeEvent
    public void onEntityStruckByLightning(EntityStruckByLightningEvent event){
        if(lightning.containsKey(event.getLightning())){
            EntityPlayer player = lightning.get(event.getLightning());
            if(event.getEntity() instanceof EntityLivingBase)
                struck.put((EntityLivingBase) event.getEntity(), player);
        }
    }

    @SubscribeEvent
    public void onEntityDeath(LivingDeathEvent event){
        if (event.getSource() == DamageSource.LIGHTNING_BOLT)
//            if(event.getEntityLiving() instanceof EntityPlayerMP)
                if(struck.containsKey(event.getEntityLiving()))
                    CremationTrigger.INSTANCE.trigger((EntityPlayerMP)struck.get(event.getEntityLiving()));
    }

    @SubscribeEvent
    public void onTick(TickEvent.ServerTickEvent event){
        if(tickCount == 0){
            lightning.clear();
            struck.clear();
        }
        if(tickCount > -1){
            tickCount--;
        }
    }

    public static void queueTick(){
        tickCount = 10;
    }
}
