package weissmoon.electromagictools.recipe;

import ic2.api.item.IC2Items;
import ic2.api.item.IElectricItem;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.items.ItemsTC;
import weissmoon.electromagictools.api.SolarHelmetRegistry;
import weissmoon.electromagictools.item.ModItems;
import weissmoon.electromagictools.lib.CraftingAspectList;
import weissmoon.electromagictools.lib.Reference;
import weissmoon.electromagictools.util.ItemHelper;

/**
 * Created by Weissmoon on 9/7/19.
 */
public class Recipes {

    public static void initInfusionRecipes(){
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":solarupgrade"),
                new SolarUpgradeInfusionRecipe("FORTRESSMASK",
                        new ItemStack(ModItems.solarHelmet),
                        5,
                        CraftingAspectList.solarHelmetRevealing,
                        new ItemStack(ModItems.solarHelmet, 1, OreDictionary.WILDCARD_VALUE),
                        new Ingredient(SolarHelmetRegistry.stackRequirements.getKeys()),
                        IC2Items.getItem("crafting", "advanced_circuit")));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":solarHelmet"),
                new InfusionRecipe("FORTRESSMASK",
                        ItemHelper.getChargedItem((IElectricItem) ModItems.solarHelmet, 0),
                        5,
                        CraftingAspectList.solarHelmetRevealing,
                        new ItemStack(ModItems.quantumGoggles),
                        IC2Items.getItem("solar_helmet"),
                        new ItemStack(IC2Items.getItemAPI().getItem("cable"), 1, 9),
                        IC2Items.getItem("lapotron_crystal")));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":scribingTools"),
                new ShapedArcaneRecipe(new ResourceLocation(""),
                        "FORTRESSMASK",
                        10,
                        CraftingAspectList.electricScribingTools,
                        ItemHelper.getChargedItem((IElectricItem) ModItems.electricScribingTools, 0),
                        "CSC", "SES", "CSC",
                        'C', IC2Items.getItem("crafting", "circuit"),
                        'S', new ItemStack(ItemsTC.scribingTools),
                        'E', new ItemStack(IC2Items.getItemAPI().getItem("energy_crystal"), 1, OreDictionary.WILDCARD_VALUE)));
    }
}
