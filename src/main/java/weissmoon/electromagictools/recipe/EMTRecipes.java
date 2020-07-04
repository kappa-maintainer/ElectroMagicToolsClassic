package weissmoon.electromagictools.recipe;

import ic2.api.classic.recipe.ClassicRecipes;
import ic2.api.classic.recipe.crafting.ICraftingRecipeList;
import ic2.api.item.IC2Items;
import ic2.api.item.IElectricItem;
import ic2.api.recipe.Recipes;
import ic2.core.item.recipe.upgrades.FlagModifier;
import ic2.core.platform.registry.Ic2Items;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.api.items.ItemsTC;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.api.SolarHelmetRegistry;
import weissmoon.electromagictools.item.ModItems;
import weissmoon.electromagictools.lib.CraftingAspectList;
import weissmoon.electromagictools.lib.Reference;
import weissmoon.electromagictools.util.ItemHelper;

import java.util.ConcurrentModificationException;

/**
 * Created by Weissmoon on 9/7/19.
 */
public class EMTRecipes {

    private static ItemStack refinedIron, denseIron;

    static ICraftingRecipeList recipes = ClassicRecipes.advCrafting;

    public static void initMaterials() {
        if (ElectroMagicTools.ic2ceLoaded) {
            refinedIron = new ItemStack(Item.getByNameOrId("ic2c_extras:refinedironplate"));
            denseIron = new ItemStack(Item.getByNameOrId("ic2c_extras:denseironplate"));
        }else{
            refinedIron = Ic2Items.refinedIronIngot;
            denseIron = new ItemStack(Blocks.IRON_BLOCK, 1, 0);
        }
    }

    public static void initInfusionRecipes(){
        try {
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
                            Ic2Items.glassFiberCable.copy(),
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
                            new ItemStack(ItemsTC.elementalHoe, 1, 0),
                            new ItemStack(IC2Items.getItemAPI().getItem("lapotron_crystal"), 1, OreDictionary.WILDCARD_VALUE),
                            new ItemStack(IC2Items.getItemAPI().getItem("electric_hoe"), 1, OreDictionary.WILDCARD_VALUE),
                            new ItemStack(Items.DYE, 1, 15),
                            IC2Items.getItem("crafting", "advanced_circuit"),
                            new ItemStack(Blocks.SAPLING, 1, OreDictionary.WILDCARD_VALUE)));
            ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":thaumiumdrill"),
                    new ElectricInfusionRecipe("THAUMIUMDRILL",
                            new ItemStack(ModItems.thaumiumDrill),
                            5,
                            CraftingAspectList.thaumiumDrill,
                            IC2Items.getItem("diamond_drill"),
                            new ItemStack(Items.DIAMOND),
                            new ItemStack(Items.DIAMOND),
                            new ItemStack(ItemsTC.plate, 1, 2),
                            new ItemStack(ItemsTC.plate, 1, 2),
                            new ItemStack(ItemsTC.plate, 1, 2),
                            denseIron));
            ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":thaumiumchainsaw"),
                    new ElectricInfusionRecipe("THAUMIUMCHAINSAW",
                            new ItemStack(ModItems.thaumiumChainsaw),
                            5,
                            CraftingAspectList.thaumiumChainsaw,
                            getDiamondChainsaw(),
                            new ItemStack(Items.DIAMOND),
                            new ItemStack(Items.DIAMOND),
                            new ItemStack(ItemsTC.plate, 1, 2),
                            new ItemStack(ItemsTC.plate, 1, 2),
                            new ItemStack(ItemsTC.plate, 1, 2),
                            denseIron));
            ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":thaumiumtool"),
                    new ElectricInfusionRecipe("THAUMIUMOMNITOOL",
                            new ItemStack(ModItems.thaumiumOmnitool),
                            6,
                            CraftingAspectList.thaumiumOmnitool,
                            new ItemStack(ModItems.thaumiumChainsaw, 1, OreDictionary.WILDCARD_VALUE),
                            new ItemStack(ModItems.thaumiumDrill, 1, OreDictionary.WILDCARD_VALUE),
                            new ItemStack(ItemsTC.plate, 1, 2),
                            new ItemStack(ItemsTC.plate, 1, 2),
                            new ItemStack(ItemsTC.plate, 1, 2),
                            IC2Items.getItem("crafting", "carbon_plate"),
                            new ItemStack(IC2Items.getItem("ingot", "tin").getItem(), 1, 302)));
            ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":thaumiumomnitoolupgrade"),
                    new ElectricInfusionRecipe("THAUMIUMOMNITOOL",
                            new ItemStack(ModItems.thaumiumOmnitool),
                            6,
                            CraftingAspectList.thaumiumOmnitool,
                            new ItemStack(ModItems.diamondOmnitool, 1, OreDictionary.WILDCARD_VALUE),
                            new ItemStack(Blocks.DIAMOND_BLOCK),
                            new ItemStack(BlocksTC.metalAlchemical),
                            new ItemStack(BlocksTC.metalAlchemical),
                            new ItemStack(IC2Items.getItem("energy_crystal").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                            IC2Items.getItem("crafting", "carbon_plate"),
                            IC2Items.getItem("crafting", "advanced_circuit")));
            ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":streamchainsaw"),
                    new ElectricInfusionRecipe("STREAMCHAINSAW",
                            new ItemStack(ModItems.streamChainsaw),
                            6,
                            CraftingAspectList.streamChainsaw,
                            new ItemStack(ModItems.thaumiumChainsaw, 1, OreDictionary.WILDCARD_VALUE),
                            new ItemStack(Items.WATER_BUCKET),
                            new ItemStack(ItemsTC.elementalAxe),
                            new ItemStack(BlocksTC.logGreatwood),
                            new ItemStack(IC2Items.getItem("lapotron_crystal").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                            IC2Items.getItem("crafting", "iridium"),
                            IC2Items.getItem("upgrade", "overclocker")));
        } catch (ConcurrentModificationException e){
            ElectroMagicTools.logger.info("Infusion recipes failed");
            e.printStackTrace();
        }

    }

    public static void initArcareCraftingRecipes(){
        try {
            ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":scribingTools"),
                    new ShapedArcaneRecipe(new ResourceLocation(""),
                            "ELECTRICSCRIBINGTOOLS",
                            10,
                            CraftingAspectList.electricScribingTools,
                            ItemHelper.getChargedItem((IElectricItem) ModItems.electricScribingTools, 0),
                            "CSC", "SES", "CSC",
                            'C', IC2Items.getItem("crafting", "circuit"),
                            'S', new ItemStack(ItemsTC.scribingTools, 1, 0),
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
                            'H', new ItemStack(Items.DIAMOND_HELMET,1 , 0),
                            'B', new ItemStack(IC2Items.getItemAPI().getItem("re_battery"), 1, OreDictionary.WILDCARD_VALUE),
                            'R', new ItemStack(Items.REPEATER)));
            ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":diamondomnitool"),
                    new ShapelessArcaneRecipe(new ResourceLocation(""),
                            "DIAMONDOMNITOOL",
                            10,
                            CraftingAspectList.diamondOmnitool,
                            ModItems.diamondOmnitool,
                            getDiamondChainsaw(),
                            IC2Items.getItem("diamond_drill")));
            ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":diamondomnitoolupgrade"),
                    new ShapedArcaneRecipe(new ResourceLocation(""),
                            "DIAMONDOMNITOOL",
                            10,
                            CraftingAspectList.diamondOmnitool,
                            ModItems.diamondOmnitool,
                            "DCD", "DTD", "DCD",
                            'D', new ItemStack(Items.DIAMOND),
                            'C', IC2Items.getItem("crafting", "advanced_circuit"),
                            'T', new ItemStack(ModItems.ironOmnitool, 1, OreDictionary.WILDCARD_VALUE)));
        } catch (ConcurrentModificationException e){
            ElectroMagicTools.logger.info("Arcane recipes failed");
            e.printStackTrace();
        }
    }

    public static ItemStack getDiamondChainsaw(){
        if (ElectroMagicTools.gtcxLoaded){
            return new ItemStack(Item.getByNameOrId("gtc_expansion:diamond_chainsaw"), 1, OreDictionary.WILDCARD_VALUE);
        }
        return new ItemStack(ModItems.diamondChainsaw, 1, OreDictionary.WILDCARD_VALUE);
    }

    public static void initIC2Recipes(){

        ItemStack ironClusterRecipe = IC2Items.getItem("dust", "small_iron").copy();
        ironClusterRecipe.setCount(22);
        Recipes.macerator.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemsTC.clusters, 1, 0)), null, true, ironClusterRecipe);

        ItemStack goldClusterRecipe = IC2Items.getItem("dust", "small_gold").copy();
        goldClusterRecipe.setCount(22);
        Recipes.macerator.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemsTC.clusters, 1, 1)), null, true, goldClusterRecipe);

        ItemStack copperClusterRecipe = IC2Items.getItem("dust", "small_copper").copy();
        copperClusterRecipe.setCount(22);
        Recipes.macerator.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemsTC.clusters, 1, 2)), null, true, copperClusterRecipe);

        ItemStack tinClusterRecipe = IC2Items.getItem("dust", "small_tin").copy();
        tinClusterRecipe.setCount(22);
        Recipes.macerator.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemsTC.clusters, 1, 3)), null, true, tinClusterRecipe);

        ItemStack silverClusterRecipe = IC2Items.getItem("dust", "small_silver").copy();
        silverClusterRecipe.setCount(22);
        Recipes.macerator.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemsTC.clusters, 1, 4)), null, true, silverClusterRecipe);

        ItemStack leadClusterRecipe = IC2Items.getItem("dust", "small_lead").copy();
        leadClusterRecipe.setCount(22);
        Recipes.macerator.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemsTC.clusters, 1, 5)), null, true, leadClusterRecipe);


        recipes.addRecipe(new ItemStack(ModItems.ironOmnitool),
                "C", "p", "D",
                'C', IC2Items.getItem("chainsaw"),
                'D', IC2Items.getItem("drill"),
                'p', refinedIron);


        ItemStack helmet = new ItemStack(ModItems.quantumGoggles);
        recipes.addShapelessRecipe(helmet.copy(), (new FlagModifier(helmet.copy(), "EUReaderUpgrade", true)).setUsesInput(), helmet.copy(), Ic2Items.euReader.copy());
        recipes.addShapelessRecipe(helmet.copy(), (new FlagModifier(helmet.copy(), "CropUpgrade", true)).setUsesInput(), helmet.copy(), Ic2Items.cropAnalyzer.copy());
        recipes.addShapelessRecipe(helmet.copy(), (new FlagModifier(helmet.copy(), "ThermometerUpgrade", true)).setUsesInput(), helmet.copy(), Ic2Items.thermometer.copy());
        helmet = new ItemStack(ModItems.nanoGoggles);
        recipes.addShapelessRecipe(helmet.copy(), (new FlagModifier(helmet.copy(), "EUReaderUpgrade", true)).setUsesInput(), helmet.copy(), Ic2Items.euReader.copy());
        recipes.addShapelessRecipe(helmet.copy(), (new FlagModifier(helmet.copy(), "CropUpgrade", true)).setUsesInput(), helmet.copy(), Ic2Items.cropAnalyzer.copy());
        recipes.addShapelessRecipe(helmet.copy(), (new FlagModifier(helmet.copy(), "ThermometerUpgrade", true)).setUsesInput(), helmet.copy(), Ic2Items.thermometer.copy());

        if(ElectroMagicTools.ic2ceLoaded){
            initIC2CERecipes();
        }
    }

    private static void initIC2CERecipes(){

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

    public static void initSmeltingRecipes(){
        GameRegistry.addSmelting(new ItemStack(ModItems.materials, 1, 0), new ItemStack(IC2Items.getItem("nuclear", "uranium_238").getItem(), 2, 180), 0);
        GameRegistry.addSmelting(new ItemStack(ModItems.materials, 1, 5), new ItemStack(ModItems.materials, 1, 3), 0);
    }

    public static void initNativeClusters(){
        int uraId = Item.getIdFromItem(IC2Items.getItem("dust", "iron").getItem());
        int uraCluId = Item.getIdFromItem(ModItems.materials);
        FMLInterModComms.sendMessage("thaumcraft", "nativeCluster",uraId+",180,"+uraCluId+",0,2.0");
    }
}
