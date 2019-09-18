package weissmoon.electromagictools.recipe;

import ic2.api.item.IC2Items;
import ic2.api.item.IElectricItem;
import ic2.api.recipe.IBasicMachineRecipeManager;
import ic2.api.recipe.Recipes;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.items.ItemsTC;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.api.SolarHelmetRegistry;
import weissmoon.electromagictools.item.ModItems;
import weissmoon.electromagictools.lib.CraftingAspectList;
import weissmoon.electromagictools.lib.Reference;
import weissmoon.electromagictools.util.ItemHelper;

/**
 * Created by Weissmoon on 9/7/19.
 */
public class EMTRecipes {

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

        ItemStack ironClusterRecipe = IC2Items.getItem("dust", "small_iron").copy();
        ironClusterRecipe.setCount(22);
        ((IBasicMachineRecipeManager) Recipes.macerator).addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemsTC.clusters, 1, 0)), null, true, ironClusterRecipe);

        ItemStack goldClusterRecipe = IC2Items.getItem("dust", "small_gold").copy();
        goldClusterRecipe.setCount(22);
        ((IBasicMachineRecipeManager) Recipes.macerator).addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemsTC.clusters, 1, 1)), null, true, goldClusterRecipe);

        ItemStack copperClusterRecipe = IC2Items.getItem("dust", "small_copper").copy();
        copperClusterRecipe.setCount(22);
        ((IBasicMachineRecipeManager) Recipes.macerator).addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemsTC.clusters, 1, 2)), null, true, copperClusterRecipe);

        ItemStack tinClusterRecipe = IC2Items.getItem("dust", "small_tin").copy();
        tinClusterRecipe.setCount(22);
        ((IBasicMachineRecipeManager) Recipes.macerator).addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemsTC.clusters, 1, 3)), null, true, tinClusterRecipe);

        ItemStack silverClusterRecipe = IC2Items.getItem("dust", "small_silver").copy();
        silverClusterRecipe.setCount(22);
        ((IBasicMachineRecipeManager) Recipes.macerator).addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemsTC.clusters, 1, 4)), null, true, silverClusterRecipe);

        ItemStack leadClusterRecipe = IC2Items.getItem("dust", "small_lead").copy();
        leadClusterRecipe.setCount(22);
        ((IBasicMachineRecipeManager) Recipes.macerator).addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemsTC.clusters, 1, 5)), null, true, leadClusterRecipe);
        if(ElectroMagicTools.ic2ceLoaded){
            initIC2CERecipes();
        }else{
            initIC2CRecipes();
        }
    }

    public static void initIC2CRecipes(){

    }

    public static void initIC2CERecipes(){

		/* Thaumium Plates Recipes */
        //thaumiumPlate = GameRegistry.addShapedRecipe(new ItemStack(EMTItems.itemEMTItems, 1, 5), "X", "Y", "Z", 'Y', new ItemStack(ConfigItems.itemResource, 1, 2), 'X', new ItemStack(IC2Items.getItem("ForgeHammer").getItem(), 1, OreDictionary.WILDCARD_VALUE), 'Z', new ItemStack(Blocks.obsidian));
        Recipes.metalformerRolling.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemsTC.ingots)), null, true, new ItemStack(ItemsTC.plate, 1, 2));

		/* Ore Processing for Amber and Cinnabar */
        ItemStack crushedAmberRecipe = new ItemStack(ModItems.materials, 1, 11);
        crushedAmberRecipe.setCount(2);
        Recipes.macerator.addRecipe(Recipes.inputFactory.forStack(new ItemStack(BlocksTC.oreAmber, 1, 7)), null, true, crushedAmberRecipe);

        ItemStack crushedCinnabarRecipe = new ItemStack(ModItems.materials, 1, 13);
        crushedCinnabarRecipe.setCount(2);
        Recipes.macerator.addRecipe(Recipes.inputFactory.forStack(new ItemStack(BlocksTC.oreCinnabar, 1, 0)), null, true, crushedCinnabarRecipe);

        NBTTagCompound waterAmount = new NBTTagCompound();
        waterAmount.setInteger("amount", 1000);

        ItemStack smallCopperDust = IC2Items.getItem("dust", "small_copper");
        smallCopperDust.setCount(2);
        ItemStack smallTinDust = IC2Items.getItem("dust", "small_tin");
        smallCopperDust.setCount(2);

        Recipes.oreWashing.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ModItems.materials, 1, 11)), waterAmount, true, new ItemStack(ModItems.materials, 1, 12), smallCopperDust, IC2Items.getItem("stoneDust"));
        Recipes.oreWashing.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ModItems.materials, 1, 13)), waterAmount, true, new ItemStack(ModItems.materials, 1, 14), smallTinDust, IC2Items.getItem("stoneDust"));

        NBTTagCompound heatAmount = new NBTTagCompound();
        heatAmount.setInteger("minHeat", 1000);

        Recipes.centrifuge.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ModItems.materials, 1, 12)), heatAmount, true, IC2Items.getItem("dust", "small_copper"), new ItemStack(ItemsTC.amber, 1, 6));
        Recipes.centrifuge.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ModItems.materials, 1, 14)), heatAmount, true, IC2Items.getItem("dust", "small_tin"), new ItemStack(ItemsTC.amber, 1, 3));
    }
}
