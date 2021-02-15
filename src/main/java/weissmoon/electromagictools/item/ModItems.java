package weissmoon.electromagictools.item;

import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
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
import weissmoon.electromagictools.item.tool.ItemCoreDrill;
import weissmoon.electromagictools.item.tool.ItemDiamondChainsaw;
import weissmoon.electromagictools.item.tool.ItemDiamondOmniTool;
import weissmoon.electromagictools.item.tool.ItemElectricHoeGrowth;
import weissmoon.electromagictools.item.tool.ItemIronOmniTool;
import weissmoon.electromagictools.item.tool.ItemMjölnir;
import weissmoon.electromagictools.item.tool.ItemStormCaster;
import weissmoon.electromagictools.item.tool.ItemStreamChainsaw;
import weissmoon.electromagictools.item.tool.ItemThaumiumChainsaw;
import weissmoon.electromagictools.item.tool.ItemThaumiumDrill;
import weissmoon.electromagictools.item.tool.ItemThaumiumOmniTool;

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
    public static final ItemHoe electricHoe = new ItemElectricHoeGrowth();
    public static final WeissItem materials = new ItemMaterials();
    public static final WeissItem onering = new ItemOneRing();
    public static final WeissItemElectricTool thaumiumDrill = new ItemThaumiumDrill();
    public static final WeissItemElectricTool coreDrill = new ItemCoreDrill();
    public static final WeissItemElectricTool diamondChainsaw = new ItemDiamondChainsaw();
    public static final WeissItemElectricTool thaumiumChainsaw = new ItemThaumiumChainsaw();
    public static final WeissItemElectricTool streamChainsaw = new ItemStreamChainsaw();
    public static final WeissItemElectricTool ironOmnitool = new ItemIronOmniTool();
    public static final WeissItemElectricTool diamondOmnitool = new ItemDiamondOmniTool();
    public static final WeissItemElectricTool thaumiumOmnitool = new ItemThaumiumOmniTool();
    public static final Item stormCaster = new ItemStormCaster();
    public static final Item mjölnir = new ItemMjölnir();


    public static Item crafing;
    public static Item focusCristmas;
    public static Item focusExplosion;
    public static Item focusShield;
    public static Item focusEnergyBall;
    public static Item rockBreakerDrill;
    public static Item stormBreaker;
    public static Item focusCharge;
    public static Item focusWandCharge;
    public static Item featherWings;
    public static Item thaumiumWings;
    public static Item nanoWings;
    public static Item quantumWings;
    public static Item quantumChestpiece;

    public static Item itemEMTItems;
    public static Item emtBauble;

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
        GameRegistry.register(electricHoe);
        GameRegistry.register(thaumiumDrill);
        GameRegistry.register(coreDrill);
        GameRegistry.register(diamondChainsaw);
        GameRegistry.register(thaumiumChainsaw);
        GameRegistry.register(streamChainsaw);
        GameRegistry.register(ironOmnitool);
        GameRegistry.register(diamondOmnitool);
        GameRegistry.register(thaumiumOmnitool);
        GameRegistry.register(stormCaster);
        GameRegistry.register(mjölnir);
        GameRegistry.register(materials);
        GameRegistry.register(onering);
    }
}
