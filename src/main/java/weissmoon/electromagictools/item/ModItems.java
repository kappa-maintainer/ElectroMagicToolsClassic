package weissmoon.electromagictools.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import weissmoon.core.item.WeissItem;
import weissmoon.core.item.armour.ItemArmourBase;
import weissmoon.electromagictools.item.armour.ItemElectricGoggles;
import weissmoon.electromagictools.item.armour.ItemNanoGoggles;
import weissmoon.electromagictools.item.armour.ItemQuantumGoggles;
import weissmoon.electromagictools.lib.Strings;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class ModItems {

    public static final WeissItem electricScribingTools = new ItemScribingTools();
    public static final ItemArmourBase electricGoggles = new ItemElectricGoggles();
    public static final ItemArmourBase nanoGoggles = new ItemNanoGoggles();
    public static final ItemArmourBase quantumGoggles = new ItemQuantumGoggles();

    public static void init(){
        MinecraftForge.EVENT_BUS.register(new ModItems());
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> itemRegistryEvent){
        IForgeRegistry<Item> GameRegistry = itemRegistryEvent.getRegistry();

        GameRegistry.register(electricScribingTools);
        GameRegistry.register(electricGoggles);
        GameRegistry.register(nanoGoggles);
        GameRegistry.register(quantumGoggles);
    }
}
