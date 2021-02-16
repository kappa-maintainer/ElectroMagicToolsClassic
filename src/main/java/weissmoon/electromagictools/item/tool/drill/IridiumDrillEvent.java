package weissmoon.electromagictools.item.tool.drill;

import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumHand;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import weissmoon.core.utils.NBTHelper;

import java.util.List;

/**
 * Created by Weissmoon on 1/30/21.
 */
public class IridiumDrillEvent {

    static String MODE_NBT_KEY = "emtMode";

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockEvent.BreakEvent event){
        if(event.getPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemIridiumDrill){
            boolean mode = NBTHelper.getBoolean(event.getPlayer().getHeldItem(EnumHand.MAIN_HAND), MODE_NBT_KEY);
            if(mode){
                event.setExpToDrop(event.getState().getBlock().getExpDrop(event.getState(), event.getWorld(), event.getPos(), 3));
            }else if(event.getState().getBlock().canSilkHarvest(event.getWorld(), event.getPos(), event.getState(), event.getPlayer())){
                event.setExpToDrop(0);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void blockDrops(BlockEvent.HarvestDropsEvent event){
        if(event.getHarvester() != null)
            if(event.getHarvester().getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemIridiumDrill) {
                boolean mode = NBTHelper.getBoolean(event.getHarvester().getHeldItem(EnumHand.MAIN_HAND), MODE_NBT_KEY);
                if (mode) {
                    List<ItemStack> drops = event.getState().getBlock().getDrops(event.getWorld(), event.getPos(), event.getState(), 3);
                    event.getDrops().clear();
                    event.getDrops().addAll(drops);
                }
            }
    }

}
