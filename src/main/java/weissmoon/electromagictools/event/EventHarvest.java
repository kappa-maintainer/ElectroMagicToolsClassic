package weissmoon.electromagictools.event;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.SoundCategory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.items.ItemsTC;
import weissmoon.electromagictools.item.ModItems;
import weissmoon.electromagictools.item.tool.drill.ItemCoreDrill;

import java.util.Random;

public class EventHarvest {
    @SubscribeEvent
    public  void onHarvestDropEvent(BlockEvent.HarvestDropsEvent event){
        EntityPlayer player = event.getHarvester();
        if (player != null && !player.isSneaking()){
            IBlockState block = event.getState();
            ItemStack stack = new ItemStack(block.getBlock(), 1, block.getBlock().getMetaFromState(block));
            Random random = event.getWorld().rand;
            if (random.nextInt(4) == 0 && player.getHeldItemMainhand().getItem() instanceof ItemCoreDrill) {
                if (matchOreDict(stack, "oreIron")) {
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(ItemsTC.clusters, 1, 0));
                    event.getWorld().playSound(null, event.getPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.2F, 0.7F + event.getWorld().rand.nextFloat() * 0.2F);
                }
                if (matchOreDict(stack, "oreGold")){
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(ItemsTC.clusters, 1, 1));
                    event.getWorld().playSound(null, event.getPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.2F, 0.7F + event.getWorld().rand.nextFloat() * 0.2F);
                }
                if (matchOreDict(stack, "oreCopper")){
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(ItemsTC.clusters, 1, 2));
                    event.getWorld().playSound(null, event.getPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.2F, 0.7F + event.getWorld().rand.nextFloat() * 0.2F);
                }
                if (matchOreDict(stack, "oreTin")){
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(ItemsTC.clusters, 1, 3));
                    event.getWorld().playSound(null, event.getPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.2F, 0.7F + event.getWorld().rand.nextFloat() * 0.2F);
                }
                if (matchOreDict(stack, "oreSilver")){
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(ItemsTC.clusters, 1, 4));
                    event.getWorld().playSound(null, event.getPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.2F, 0.7F + event.getWorld().rand.nextFloat() * 0.2F);
                }
                if (matchOreDict(stack, "oreLead")){
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(ItemsTC.clusters, 1, 5));
                    event.getWorld().playSound(null, event.getPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.2F, 0.7F + event.getWorld().rand.nextFloat() * 0.2F);
                }
                if (matchOreDict(stack, "oreUranium")){
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(ModItems.materials, 1, 0));
                    event.getWorld().playSound(null, event.getPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.2F, 0.7F + event.getWorld().rand.nextFloat() * 0.2F);
                }
                if (matchOreDict(stack, "oreCinnabar")){
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(ItemsTC.clusters, 1, 6));
                    event.getWorld().playSound(null, event.getPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.2F, 0.7F + event.getWorld().rand.nextFloat() * 0.2F);
                }
                if (matchOreDict(stack, "oreQuartz")){
                    event.getDrops().clear();
                    event.getDrops().add(new ItemStack(ItemsTC.clusters, 1, 7));
                    event.getWorld().playSound(null, event.getPos(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.2F, 0.7F + event.getWorld().rand.nextFloat() * 0.2F);
                }
            }
        }
    }

    public static boolean matchOreDict(ItemStack stack, String entry) {
        if (!stack.isEmpty() && (OreDictionary.getOreIDs(stack).length > 0)) {
            for (int i = 0; i < OreDictionary.getOreIDs(stack).length; i++) {
                if (OreDictionary.getOreName(OreDictionary.getOreIDs(stack)[i]).contains(entry)) {
                    return true;
                }
            }
        }
        return false;
    }
}
