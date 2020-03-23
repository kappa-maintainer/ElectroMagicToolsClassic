package weissmoon.electromagictools;

import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import thaumcraft.api.ThaumcraftApi;
import weissmoon.core.client.creativetab.CreativeTabWeiss;
import weissmoon.electromagictools.client.EMTTab;
import weissmoon.electromagictools.event.EventPool;
import weissmoon.electromagictools.item.ModItems;
import weissmoon.electromagictools.lib.Reference;
import weissmoon.electromagictools.recipe.EMTRecipes;
import weissmoon.electromagictools.research.TCResearch;

/**
 * Created by Weissmoon on 9/3/19.
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION, dependencies = "required-after:weisscore@[0.1.1,);after:industrialcraft2;after:thaumcraft")
public class ElectroMagicTools {

    @Instance
    public static ElectroMagicTools INSTANCE;

    public static boolean ic2ceLoaded;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        ic2ceLoaded = Loader.isModLoaded("ic2c_extras");
        ModItems.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event){
        OreDictionaryEntries.initOreEntries();
        EMTRecipes.initMaterials();
        EMTRecipes.initArcareCraftingRecipes();
        EMTRecipes.initInfusionRecipes();
        EMTRecipes.initNativeClusters();
        EMTRecipes.initIC2Recipes();
        EMTRecipes.initSmeltingRecipes();
        MinecraftForge.EVENT_BUS.register(new EventPool());
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(Reference.MOD_ID, "research/wemt"));
        //ThaumcraftApi.registerResearchLocation(new ResourceLocation(Reference.MOD_ID, "research/nya.json"));
    }

    @EventHandler
    public void postInit(FMLInitializationEvent event){
        TCResearch.registerResearchTab();
    }

    public static CreativeTabWeiss EMTtab = new EMTTab();
}
