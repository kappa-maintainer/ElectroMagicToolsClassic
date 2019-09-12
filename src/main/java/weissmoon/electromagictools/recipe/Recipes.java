package weissmoon.electromagictools.recipe;

import ic2.api.item.IC2Items;
import ic2.api.item.IElectricItem;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
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
                new SolarUpgradeInfusionRecipe("WMMISSINGRESEARCH",
                        new ItemStack(ModItems.solarHelmet),
                        5,
                        CraftingAspectList.solarHelmetRevealing,
                        new ItemStack(ModItems.solarHelmet, 1, OreDictionary.WILDCARD_VALUE),
                        new Ingredient(SolarHelmetRegistry.stackRequirements.getKeys()),
                        IC2Items.getItem("crafting", "advanced_circuit")));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":solarHelmet"),
                new InfusionRecipe("WMMISSINGRESEARCH",
                        ItemHelper.getChargedItem((IElectricItem) ModItems.solarHelmet, 0),
                        5,
                        CraftingAspectList.solarHelmetRevealing,
                        new ItemStack(ModItems.quantumGoggles),
                        IC2Items.getItem("solar_helmet"),
                        new ItemStack(IC2Items.getItemAPI().getItem("cable"), 1, 9),
                        IC2Items.getItem("lapotron_crystal")));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":nanoGoggles"),
                new ElectricInfusionRecipe("NANOGOGGLES",
                        new ItemStack(ModItems.nanoGoggles),
                        5,
                        CraftingAspectList.nanoHelmet,
                        new ItemStack(ModItems.electricGoggles, 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(Items.DIAMOND),
                        new ItemStack(Items.GOLD_INGOT),
                        new ItemStack(IC2Items.getItemAPI().getItem("nano_helmet"), 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(ItemsTC.plate, 1, 2),
                        new ItemStack(ItemsTC.plate, 1, 2),
                        IC2Items.getItem("crafting", "carbon_plate"),
                        IC2Items.getItem("crafting", "circuit")));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":quantumGoggles"),
                new ElectricInfusionRecipe("QUANTUMGOGGLES",
                        new ItemStack(ModItems.quantumGoggles),
                        5,
                        CraftingAspectList.quantumHelmet,
                        new ItemStack(ModItems.nanoGoggles, 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(Items.DIAMOND),
                        new ItemStack(Items.MILK_BUCKET),
                        new ItemStack(IC2Items.getItemAPI().getItem("quantum_helmet"), 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(ItemsTC.plate, 1, 2),
                        new ItemStack(ItemsTC.plate, 1, 2),
                        IC2Items.getItem("crafting", "iridium"),
                        IC2Items.getItem("crafting", "advanced_circuit")));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":electricBoots"),
                new ElectricInfusionRecipe("ELECTRICBOOTS",
                        new ItemStack(ModItems.electricBoots),
                        5,
                        CraftingAspectList.electricBoots,
                        new ItemStack(ItemsTC.travellerBoots),
                        new ItemStack(Items.DIAMOND),
                        new ItemStack(IC2Items.getItemAPI().getItem("re_battery"), 1, OreDictionary.WILDCARD_VALUE),
                        IC2Items.getItem("crafting", "circuit"),
                        IC2Items.getItem("rubber_boots")));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":nanoBoots"),
                new ElectricInfusionRecipe("NANOBOOTS",
                        new ItemStack(ModItems.nanoBoots),
                        5,
                        CraftingAspectList.nanoBoots,
                        new ItemStack(ModItems.electricBoots),
                        new ItemStack(Items.DIAMOND),
                        new ItemStack(IC2Items.getItemAPI().getItem("nano_boots"), 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(IC2Items.getItemAPI().getItem("energy_crystal"), 1, OreDictionary.WILDCARD_VALUE)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":quantumBoots"),
                new ElectricInfusionRecipe("QUANTUMBOOTS",
                        new ItemStack(ModItems.quantumBoots),
                        5,
                        CraftingAspectList.quantumBoots,
                        new ItemStack(ModItems.nanoBoots),
                        new ItemStack(Blocks.DIAMOND_BLOCK),
                        new ItemStack(IC2Items.getItemAPI().getItem("quantum_boots"), 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(IC2Items.getItemAPI().getItem("lapotron_crystal"), 1, OreDictionary.WILDCARD_VALUE),
                        IC2Items.getItem("crafting", "iridium")));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":electricHoe"),
                new ElectricInfusionRecipe("ELECTRICHOE",
                        new ItemStack(ModItems.electricHoe),
                        5,
                        CraftingAspectList.electricHoe,
                        new ItemStack(ItemsTC.elementalHoe),
                        new ItemStack(IC2Items.getItemAPI().getItem("lapotron_crystal"), 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(IC2Items.getItemAPI().getItem("electric_hoe"), 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(Items.DYE, 1, 15),
                        IC2Items.getItem("crafting", "advanced_circuit"),
                        new ItemStack(Blocks.SAPLING, 1, OreDictionary.WILDCARD_VALUE)));
    }

    public static void initArcareCraftingRecipes(){
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":scribingTools"),
                new ShapedArcaneRecipe(new ResourceLocation(""),
                        "ELECTRICSCRIBINGTOOLS",
                        10,
                        CraftingAspectList.electricScribingTools,
                        ItemHelper.getChargedItem((IElectricItem) ModItems.electricScribingTools, 0),
                        "CSC", "SES", "CSC",
                        'C', IC2Items.getItem("crafting", "circuit"),
                        'S', new ItemStack(ItemsTC.scribingTools),
                        'E', new ItemStack(IC2Items.getItemAPI().getItem("energy_crystal"), 1, OreDictionary.WILDCARD_VALUE)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":electricGoggles"),
                new ShapedArcaneRecipe(new ResourceLocation("electricGoggles"),
                        "ELECTRICGOGGLES",
                        10,
                        CraftingAspectList.electricHelmet,
                        ItemHelper.getChargedItem((IElectricItem) ModItems.electricGoggles, 0),
                        " H ", "BGB", "RCR",
                        'G', new ItemStack(ItemsTC.goggles),
                        'C', IC2Items.getItem("crafting", "circuit"),
                        'H', new ItemStack(Items.DIAMOND_HELMET),
                        'B', new ItemStack(IC2Items.getItemAPI().getItem("re_battery"), 1, OreDictionary.WILDCARD_VALUE),
                        'R', new ItemStack(Items.REPEATER)));
    }

    public static void initIC2Recipes(){

    }
}
