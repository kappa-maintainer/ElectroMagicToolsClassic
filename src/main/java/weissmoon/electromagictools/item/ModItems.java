package weissmoon.electromagictools.item;

import net.minecraft.item.Item;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import weissmoon.core.item.WeissItem;
import weissmoon.core.item.armour.ItemArmourBase;
import weissmoon.electromagictools.item.armour.boots.ItemElectricBootsTraveller;
import weissmoon.electromagictools.item.armour.boots.ItemNanoBootsTraveller;
import weissmoon.electromagictools.item.armour.boots.ItemQuantumBootsTraveller;
import weissmoon.electromagictools.item.armour.googles.ItemElectricGoggles;
import weissmoon.electromagictools.item.armour.googles.ItemNanoGoggles;
import weissmoon.electromagictools.item.armour.googles.ItemQuantumGoggles;
import weissmoon.electromagictools.item.armour.googles.ItemSolarHelmetRevealing;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class ModItems {

    public static final WeissItem electricScribingTools = new ItemScribingTools();
    public static final ItemArmourBase electricGoggles = new ItemElectricGoggles();
    public static final ItemArmourBase nanoGoggles = new ItemNanoGoggles();
    public static final ItemArmourBase quantumGoggles = new ItemQuantumGoggles();
    public static final ItemArmourBase electricBoots = new ItemElectricBootsTraveller();
    public static final ItemArmourBase nanoBoots = new ItemNanoBootsTraveller();
    public static final ItemArmourBase quantumBoots = new ItemQuantumBootsTraveller();
    public static final ItemArmourBase solarHelmet = new ItemSolarHelmetRevealing();

    public static void init(){
        MinecraftForge.EVENT_BUS.register(new ModItems());
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> itemRegistryEvent){
        IForgeRegistry<Item> GameRegistry = itemRegistryEvent.getRegistry();

        GameRegistry.register(DummyLogo.instance);
        GameRegistry.register(electricScribingTools);
        GameRegistry.register(electricGoggles);
        GameRegistry.register(nanoGoggles);
        GameRegistry.register(quantumGoggles);
        GameRegistry.register(solarHelmet);
        GameRegistry.register(electricBoots);
        GameRegistry.register(nanoBoots);
        GameRegistry.register(quantumBoots);
    }
}
