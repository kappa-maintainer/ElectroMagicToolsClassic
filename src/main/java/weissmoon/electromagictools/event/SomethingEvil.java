package weissmoon.electromagictools.event;

import net.minecraftforge.event.entity.living.LivingDropsEvent;
import net.minecraftforge.event.entity.player.AttackEntityEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.common.lib.events.ToolEvents;

/**
 * Created by Weissmoon on 8/20/21.
 * Thaumcraft ToolEvents.java wrapper
 */
public class SomethingEvil extends ToolEvents{
    @SubscribeEvent
    public static void playerAttack(AttackEntityEvent event) {
        ToolEvents.playerAttack(event);
    }

    @SubscribeEvent
    public static void playerInteract(PlayerInteractEvent.LeftClickBlock event) {
        ToolEvents.playerInteract(event);
    }

    @SubscribeEvent
    public static void breakBlockEvent(BlockEvent.BreakEvent event) {
        ToolEvents.breakBlockEvent(event);
    }

    @SubscribeEvent
    public static void harvestBlockEvent(final BlockEvent.HarvestDropsEvent event) {
        ToolEvents.harvestBlockEvent(event);
    }

    @SubscribeEvent
    public static void livingDrops(LivingDropsEvent event) {
        ToolEvents.livingDrops(event);
    }
}
