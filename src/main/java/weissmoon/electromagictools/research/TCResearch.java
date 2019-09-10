package weissmoon.electromagictools.research;

import net.minecraft.util.ResourceLocation;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.research.ResearchCategories;
import thaumcraft.api.research.ResearchEntry;
import weissmoon.electromagictools.lib.Reference;

/**
 * Created by Weissmoon on 9/10/19.
 */
public class TCResearch {

    public static void initResearch(){

    }

    public static void registerResearchTab(){
        ResearchCategories.registerCategory("WEMT",
                null,
                new AspectList(),
                new ResourceLocation(Reference.MOD_ID + ":textures/logo.png"),
                new ResourceLocation(Reference.MOD_ID + ":textures/misc/background.png"),
                new ResourceLocation("thaumcraft:textures/gui/gui_research_back_over.png"));
    }

    public static void registerResearch(){
        new ResearchEntry();
    }
}
