package weissmoon.electromagictools.item;

import ic2.api.item.IBoxable;
import ic2.api.item.IMetalArmor;
import ic2.api.item.ItemWrapper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemHoe;
import net.minecraft.item.ItemPickaxe;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.AspectRegistryEvent;
import thaumcraft.api.items.ItemsTC;
import weissmoon.core.item.WeissItem;
import weissmoon.core.item.armour.ItemArmourBase;
import weissmoon.core.item.tools.WeissItemAxe;
import weissmoon.core.item.tools.WeissItemsPickaxe;
import weissmoon.electromagictools.item.armour.ItemEnergyPack;
import weissmoon.electromagictools.item.armour.boots.ItemElectricBootsTraveller;
import weissmoon.electromagictools.item.armour.boots.ItemNanoBootsTraveller;
import weissmoon.electromagictools.item.armour.boots.ItemQuantumBootsTraveller;
import weissmoon.electromagictools.item.armour.googles.ItemElectricGoggles;
import weissmoon.electromagictools.item.armour.googles.ItemNanoGoggles;
import weissmoon.electromagictools.item.armour.googles.ItemQuantumGoggles;
import weissmoon.electromagictools.item.armour.googles.ItemSolarHelmetRevealing;
import weissmoon.electromagictools.item.tool.ItemChargeRing;
import weissmoon.electromagictools.item.tool.ItemEntropyStaff;
import weissmoon.electromagictools.item.tool.ItemHitBauble;
import weissmoon.electromagictools.item.tool.ItemStormBreaker;
import weissmoon.electromagictools.item.tool.drill.ItemCoreDrill;
import weissmoon.electromagictools.item.tool.chainsaw.ItemDiamondChainsaw;
import weissmoon.electromagictools.item.tool.omnitool.ItemDiamondOmniTool;
import weissmoon.electromagictools.item.tool.ItemElectricHoeGrowth;
import weissmoon.electromagictools.item.tool.omnitool.ItemIronOmniTool;
import weissmoon.electromagictools.item.tool.ItemMjölnir;
import weissmoon.electromagictools.item.tool.ItemStormCaster;
import weissmoon.electromagictools.item.tool.chainsaw.ItemStreamChainsaw;
import weissmoon.electromagictools.item.tool.chainsaw.ItemThaumiumChainsaw;
import weissmoon.electromagictools.item.tool.drill.ItemThaumiumDrill;
import weissmoon.electromagictools.item.tool.omnitool.ItemThaumiumOmniTool;
import weissmoon.electromagictools.item.armour.wings.ItemFeatherWings;
import weissmoon.electromagictools.item.armour.wings.ItemNanoWings;
import weissmoon.electromagictools.item.armour.wings.ItemQuantumWings;
import weissmoon.electromagictools.item.armour.wings.ItemThaumiumWings;
import weissmoon.electromagictools.item.tool.drill.ItemIridiumDrill;
import weissmoon.electromagictools.item.tool.drill.ItemRockBreakerDrill;

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
    public static final ItemStormCaster stormCaster = new ItemStormCaster();

    public static final Item mjölnir = new ItemMjölnir();
    public static final Item chargeRing = new ItemChargeRing();
    public static final Item featherWings = new ItemFeatherWings();
    public static final Item thaumiumWings = new ItemThaumiumWings();
    public static final Item nanoWings = new ItemNanoWings();
    public static final Item quantumWings = new ItemQuantumWings();
    public static final Item rockBreakerDrill = new ItemRockBreakerDrill();
    public static final Item stormBreaker = new ItemStormBreaker();

    public static final WeissItemElectricTool iridiumDrill = new ItemIridiumDrill();
    public static final Item gemPack = new ItemEnergyPack();
    public static final Item coin = new ItemCoin();
    public static final Item entropyStaff = new ItemEntropyStaff();
    public static final Item hitBauble = new ItemHitBauble();

    public static Item crafing;
    public static Item focusCristmas;
    public static Item focusExplosion;
    public static Item focusShield;
    public static Item focusEnergyBall;
    public static Item focusCharge;
    public static Item focusWandCharge;
    public static Item quantumChestpiece;

    public static Item itemEMTItems;
    public static Item emtBauble;

    public static void init(){
        MinecraftForge.EVENT_BUS.register(new ModItems());
    }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> itemRegistryEvent){
        IForgeRegistry<Item> GameRegistry = itemRegistryEvent.getRegistry();

        //GameRegistry.register(DummyLogo.instance);
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
        GameRegistry.register(chargeRing);
        GameRegistry.register(materials);
        GameRegistry.register(onering);
        GameRegistry.register(featherWings);
        GameRegistry.register(thaumiumWings);
        GameRegistry.register(nanoWings);
        GameRegistry.register(quantumWings);
        GameRegistry.register(rockBreakerDrill);
        GameRegistry.register(stormBreaker);

        GameRegistry.register(iridiumDrill);
        GameRegistry.register(gemPack);
        GameRegistry.register(coin);
        GameRegistry.register(entropyStaff);
        GameRegistry.register(hitBauble);
    }

    public static void compartTCtoIC(){
        IBoxable boxable = stack -> true;

        ItemWrapper.registerBoxable(ItemsTC.casterBasic, boxable);
        ItemWrapper.registerBoxable(ItemsTC.sanityChecker, boxable);
        ItemWrapper.registerBoxable(ItemsTC.handMirror, boxable);
        ItemWrapper.registerBoxable(ItemsTC.resonator, boxable);
        ItemWrapper.registerBoxable(ItemsTC.golemBell, boxable);
        ItemWrapper.registerBoxable(ItemsTC.plate, boxable);


        IMetalArmor metalArmor = (itemstack, player) -> true;

        ItemWrapper.registerMetalArmor(ItemsTC.thaumiumHelm, metalArmor);
        ItemWrapper.registerMetalArmor(ItemsTC.thaumiumChest, metalArmor);
        ItemWrapper.registerMetalArmor(ItemsTC.thaumiumLegs, metalArmor);
        ItemWrapper.registerMetalArmor(ItemsTC.thaumiumBoots, metalArmor);
        ItemWrapper.registerMetalArmor(ItemsTC.fortressHelm, metalArmor);
        ItemWrapper.registerMetalArmor(ItemsTC.fortressChest, metalArmor);
        ItemWrapper.registerMetalArmor(ItemsTC.fortressLegs, metalArmor);
        ItemWrapper.registerMetalArmor(ItemsTC.crimsonBoots, metalArmor);
        ItemWrapper.registerMetalArmor(ItemsTC.crimsonPlateHelm, metalArmor);
        ItemWrapper.registerMetalArmor(ItemsTC.crimsonPlateChest, metalArmor);
        ItemWrapper.registerMetalArmor(ItemsTC.crimsonPlateLegs, metalArmor);
        ItemWrapper.registerMetalArmor(ItemsTC.crimsonPraetorHelm, metalArmor);
        ItemWrapper.registerMetalArmor(ItemsTC.crimsonPraetorChest, metalArmor);
        ItemWrapper.registerMetalArmor(ItemsTC.crimsonPraetorLegs, metalArmor);
    }

    @SubscribeEvent
    public void registerAspects(AspectRegistryEvent event){
        AspectEventProxy e = event.register;
        e.registerObjectTag(new ItemStack(materials, 1, 0), new AspectList().add(Aspect.METAL, 15).add(Aspect.ENERGY, 10).add(Aspect.DEATH, 5).add(Aspect.ORDER, 5).add(Aspect.EARTH, 5));
        e.registerObjectTag(new ItemStack(materials, 1, 1), new AspectList().add(Aspect.ENERGY, 20).add(Aspect.MAGIC, 15));
        e.registerObjectTag(new ItemStack(materials, 1, 3), new AspectList().add(Aspect.CRAFT, 12).add(Aspect.TRAP, 10).add(Aspect.WATER, 3));
        e.registerObjectTag(new ItemStack(materials, 1, 7), new AspectList().add(Aspect.FLIGHT, 40).add(Aspect.AIR, 69).add(Aspect.PLANT, 150).add(Aspect.CRAFT, 300));
        e.registerObjectTag(new ItemStack(materials, 1, 7), new AspectList().add(Aspect.FLIGHT, 60).add(Aspect.AIR, 69).add(Aspect.PLANT, 20).add(Aspect.CRAFT, 350).add(Aspect.METAL, 70));
        e.registerObjectTag(new ItemStack(onering), new AspectList().add(Aspect.DESIRE, 501).add(Aspect.DEATH, 499).add(Aspect.TRAP, 498).add(Aspect.FLUX, 498).add(Aspect.VOID, 498).add(Aspect.MAN, 498).add(Aspect.SOUL, 499));
    }
}
