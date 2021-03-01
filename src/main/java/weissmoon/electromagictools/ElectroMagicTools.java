package weissmoon.electromagictools;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import org.apache.logging.log4j.Logger;
import net.minecraftforge.fml.relauncher.Side;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.casters.CasterTriggerRegistry;
import weissmoon.core.client.creativetab.CreativeTabWeiss;
import weissmoon.electromagictools.advancements.BaubleHitTrigger;
import weissmoon.electromagictools.advancements.CremationTrigger;
import weissmoon.electromagictools.advancements.RockbreakerTrigger;
import weissmoon.electromagictools.advancements.WingDeathTrigger;
import weissmoon.electromagictools.block.ModBlocks;
import weissmoon.electromagictools.client.ClientEvents;
import weissmoon.electromagictools.client.EMTTab;
import weissmoon.electromagictools.event.EventHarvest;
import weissmoon.electromagictools.event.EventPool;
import weissmoon.electromagictools.event.WWMTCastTriggerManager;
import weissmoon.electromagictools.item.ModItems;
import weissmoon.electromagictools.lib.Reference;
import weissmoon.electromagictools.network.PacketHandler;
import weissmoon.electromagictools.override.EnergyIconOverride;
import weissmoon.electromagictools.recipe.EMTRecipes;
import weissmoon.electromagictools.research.TCResearch;
import weissmoon.electromagictools.world.ObsidianPillarGenerator;

/**
 * Created by Weissmoon on 9/3/19.
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "required-after:weisscore@[0.1.1,);required-after:ic2;required-after:thaumcraft;required:ic2-classic-spmod")
public class ElectroMagicTools {

    @Instance
    public static ElectroMagicTools INSTANCE;
    public static CreativeTabWeiss EMTtab = new EMTTab();

    public static boolean ic2ceLoaded;
    public static boolean gtcxLoaded;

    public static Logger logger;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        logger = event.getModLog();
        ic2ceLoaded = Loader.isModLoaded("ic2c_extras");
        gtcxLoaded = Loader.isModLoaded("gtc_expansion");
        ModItems.init();
        MinecraftForge.EVENT_BUS.register(new EventHarvest());
        ModBlocks.init();
        PacketHandler.init();
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
            MinecraftForge.EVENT_BUS.register(new ClientEvents());
    }

    @EventHandler
    public void init(FMLInitializationEvent event){
        OreDictionaryEntries.initOreEntries();
        EMTRecipes.initArcaneCraftingRecipes();
        EMTRecipes.initInfusionRecipes();
        EMTRecipes.initCrucibleRecipes();
        EMTRecipes.initNativeClusters();
        EMTRecipes.initIC2Recipes();
        EMTRecipes.initSmeltingRecipes();
        MinecraftForge.EVENT_BUS.register(new EventPool());
//        MinecraftForge.EVENT_BUS.register(new Cremation());
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(Reference.MOD_ID, "research/wemt"));
        //ThaumcraftApi.registerResearchLocation(new ResourceLocation(Reference.MOD_ID, "research/nya.json"));
        ModItems.compartTCtoIC();
        CasterTriggerRegistry.registerWandBlockTrigger(new WWMTCastTriggerManager(), 1, BlocksTC.infusionMatrix.getDefaultState(), Reference.MOD_ID);
    }

    @EventHandler
    public void postInit(FMLInitializationEvent event){
        CriteriaTriggers.register(WingDeathTrigger.INSTANCE);
        CriteriaTriggers.register(RockbreakerTrigger.INSTANCE);
        CriteriaTriggers.register(BaubleHitTrigger.INSTANCE);
        CriteriaTriggers.register(CremationTrigger.INSTANCE);
        if(FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT)
            EnergyIconOverride.init();
        TCResearch.registerResearchTab();
    }

}
