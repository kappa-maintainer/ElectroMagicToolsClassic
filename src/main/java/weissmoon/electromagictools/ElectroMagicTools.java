package weissmoon.electromagictools;

import com.sun.org.apache.regexp.internal.RE;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.*;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import weissmoon.core.client.creativetab.CreativeTabWeiss;
import weissmoon.electromagictools.client.EMTTab;
import weissmoon.electromagictools.item.ModItems;
import weissmoon.electromagictools.lib.Reference;
import weissmoon.electromagictools.recipe.Recipes;

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
    public void Init(FMLInitializationEvent event){
        Recipes.initInfusionRecipes();
    }

    public static CreativeTabWeiss EMTtab = new EMTTab();
}
