package weissmoon.electromagictools;

import com.sun.org.apache.regexp.internal.RE;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import thaumcraft.api.ThaumcraftApi;
import weissmoon.core.client.creativetab.CreativeTabWeiss;
import weissmoon.electromagictools.client.EMTTab;
import weissmoon.electromagictools.item.ModItems;
import weissmoon.electromagictools.lib.Reference;
import weissmoon.electromagictools.recipe.Recipes;
import weissmoon.electromagictools.research.TCResearch;

/**
 * Created by Weissmoon on 9/3/19.
 */
@Mod(modid = Reference.MOD_ID, name = Reference.MOD_NAME, version = Reference.VERSION)
public class ElectroMagicTools {

    @Instance
    public static ElectroMagicTools INSTANCE;

    @EventHandler
    public void preInit(FMLPreInitializationEvent event){
        ModItems.init();
    }

    @EventHandler
    public void init(FMLInitializationEvent event){
        Recipes.initArcareCraftingRecipes();
        Recipes.initInfusionRecipes();
        ThaumcraftApi.registerResearchLocation(new ResourceLocation(Reference.MOD_ID, "research/wemt.json"));
        //ThaumcraftApi.registerResearchLocation(new ResourceLocation(Reference.MOD_ID, "research/nya.json"));
    }

    @EventHandler
    public void postInit(FMLInitializationEvent event){
        TCResearch.registerResearchTab();
    }

    public static CreativeTabWeiss EMTtab = new EMTTab();
}
