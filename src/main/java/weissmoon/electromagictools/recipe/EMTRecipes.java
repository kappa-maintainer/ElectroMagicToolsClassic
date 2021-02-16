package weissmoon.electromagictools.recipe;

import ic2.api.classic.recipe.ClassicRecipes;
import ic2.api.classic.recipe.crafting.ICraftingRecipeList;
import ic2.api.item.IC2Items;
import ic2.api.item.IElectricItem;
import ic2.api.recipe.Recipes;
<<<<<<< HEAD
import ic2.core.item.recipe.upgrades.FlagModifier;
import ic2.core.platform.registry.Ic2Items;
=======
import ic2.core.item.recipe.upgrades.EnchantmentModifier;
>>>>>>> master
import net.minecraft.init.Blocks;
import net.minecraft.init.Enchantments;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.common.event.FMLInterModComms;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.OreIngredient;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.ThaumcraftApiHelper;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.blocks.BlocksTC;
import thaumcraft.api.crafting.CrucibleRecipe;
import thaumcraft.api.crafting.InfusionRecipe;
import thaumcraft.api.crafting.ShapedArcaneRecipe;
import thaumcraft.api.crafting.ShapelessArcaneRecipe;
import thaumcraft.api.items.ItemsTC;
import thaumcraft.common.lib.enchantment.EnumInfusionEnchantment;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.api.SolarHelmetRegistry;
import weissmoon.electromagictools.block.ModBlocks;
import weissmoon.electromagictools.item.ModItems;
import weissmoon.electromagictools.lib.CraftingAspectList;
import weissmoon.electromagictools.lib.Reference;
import weissmoon.electromagictools.util.ItemHelper;

import java.util.ConcurrentModificationException;

/**
 * Created by Weissmoon on 9/7/19.
 */
public class EMTRecipes {

    private static Ingredient refinedIron, denseIron, generatorWater;

<<<<<<< HEAD
    static ICraftingRecipeList recipes = ClassicRecipes.advCrafting;

    public static void initMaterials() {
=======
    static{
        initMaterials();
    }

    private static void initMaterials() {
>>>>>>> master
        if (ElectroMagicTools.ic2ceLoaded) {
            refinedIron = new OreIngredient("plateRefinedIron");
            denseIron = new OreIngredient("plateDenseIron");
//            refinedIron = new ItemStack(Item.getByNameOrId("ic2c_extras:refinedironplate"));
//            denseIron = new ItemStack(Item.getByNameOrId("ic2c_extras:denseironplate"));
            generatorWater = Ingredient.fromStacks(new ItemStack(Item.getByNameOrId("ic2c_extras:orewashingplant")));
        }else{
<<<<<<< HEAD
            refinedIron = Ic2Items.refinedIronIngot;
            denseIron = new ItemStack(Blocks.IRON_BLOCK, 1, 0);
=======
            refinedIron = Ingredient.fromStacks(new ItemStack(IC2Items.getItem("ingot", "tin").getItem(), 1, 53));
            denseIron = new OreIngredient("blockIron");
            generatorWater = Ingredient.fromStacks(IC2Items.getItem("te", "electrolyzer"));
>>>>>>> master
        }
    }

    public static void initInfusionRecipes(){
<<<<<<< HEAD
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
=======
//        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":solarupgrade"),
//                new SolarUpgradeInfusionRecipe("WMMISSINGRESEARCH",
//                        new ItemStack(ModItems.solarHelmet),
//                        5,
//                        CraftingAspectList.solarHelmetRevealing,
//                        new ItemStack(ModItems.solarHelmet),
//                        Ingredient.fromStacks(SolarHelmetRegistry.stackRequirements.getKeys().toArray(new ItemStack[]{})),
//                        IC2Items.getItem("crafting", "advanced_circuit")));
//        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":solarHelmet"),
//                new InfusionRecipe("WMMISSINGRESEARCH",
//                        ItemHelper.getChargedItem((IElectricItem) ModItems.solarHelmet, 0),
//                        5,
//                        CraftingAspectList.solarHelmetRevealing,
//                        new ItemStack(ModItems.quantumGoggles),
//                        IC2Items.getItem("solar_helmet"),
//                        new ItemStack(IC2Items.getItemAPI().getItem("cable"), 1, 9),
//                        IC2Items.getItem("lapotron_crystal")));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":nanoGoggles"),
                new ElectricInfusionRecipe("NANOGOGGLES",
                        new ItemStack(ModItems.nanoGoggles),
                        5,
                        CraftingAspectList.nanoHelmet,
                        new ItemStack(ModItems.electricGoggles),
                        new ItemStack(Items.DIAMOND),
                        new ItemStack(Items.GOLD_INGOT),
                        IC2Items.getItem("nano_helmet"),
                        new OreIngredient("plateThaumium"),
                        new OreIngredient("plateThaumium"),
                        IC2Items.getItem("crafting", "carbon_plate"),
                        IC2Items.getItem("crafting", "circuit")));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":quantumGoggles"),
                new ElectricInfusionRecipe("QUANTUMGOGGLES",
                        new ItemStack(ModItems.quantumGoggles),
                        5,
                        CraftingAspectList.quantumHelmet,
                        new ItemStack(ModItems.nanoGoggles),
                        new ItemStack(Items.DIAMOND),
                        new ItemStack(Items.MILK_BUCKET),
                        IC2Items.getItem("quantum_helmet"),
                        new OreIngredient("plateThaumium"),
                        new OreIngredient("plateThaumium"),
                        IC2Items.getItem("crafting", "iridium"),
                        IC2Items.getItem("crafting", "advanced_circuit")));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":electricBoots"),
                new ElectricInfusionRecipe("ELECTRICBOOTS",
                        new ItemStack(ModItems.electricBoots),
                        5,
                        CraftingAspectList.electricBoots,
                        new ItemStack(ItemsTC.travellerBoots),
                        new ItemStack(Items.DIAMOND),
                        IC2Items.getItemAPI().getItem("re_battery"),
                        IC2Items.getItem("crafting", "circuit"),
                        IC2Items.getItem("rubber_boots")));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":nanoBoots"),
                new ElectricInfusionRecipe("NANOBOOTS",
                        new ItemStack(ModItems.nanoBoots),
                        5,
                        CraftingAspectList.nanoBoots,
                        new ItemStack(ModItems.electricBoots),
                        new ItemStack(Items.DIAMOND),
                        IC2Items.getItem("nano_boots"),
                        IC2Items.getItem("energy_crystal")));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":quantumBoots"),
                new ElectricInfusionRecipe("QUANTUMBOOTS",
                        new ItemStack(ModItems.quantumBoots),
                        5,
                        CraftingAspectList.quantumBoots,
                        new ItemStack(ModItems.nanoBoots),
                        new ItemStack(Blocks.DIAMOND_BLOCK),
                        IC2Items.getItem("quantum_boots"),
                        IC2Items.getItem("lapotron_crystal"),
                        IC2Items.getItem("crafting", "iridium")));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":electricHoe"),
                new ElectricInfusionRecipe("ELECTRICHOE",
                        new ItemStack(ModItems.electricHoe),
                        5,
                        CraftingAspectList.electricHoe,
                        new ItemStack(ItemsTC.elementalHoe, 1, 0),
                        IC2Items.getItem("lapotron_crystal"),
                        IC2Items.getItem("electric_hoe"),
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
                        new OreIngredient("plateThaumium"),
                        new OreIngredient("plateThaumium"),
                        new OreIngredient("plateThaumium"),
                        denseIron));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":thaumiumchainsaw"),
                new ElectricInfusionRecipe("THAUMIUMCHAINSAW",
                        new ItemStack(ModItems.thaumiumChainsaw),
                        5,
                        CraftingAspectList.thaumiumChainsaw,
                        new ItemStack(ModItems.diamondChainsaw),
                        new ItemStack(Items.DIAMOND),
                        new ItemStack(Items.DIAMOND),
                        new OreIngredient("plateThaumium"),
                        new OreIngredient("plateThaumium"),
                        new OreIngredient("plateThaumium"),
                        new OreIngredient("plateThaumium"),
                        denseIron));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":thaumiumtool"),
                new ElectricInfusionRecipe("THAUMIUMOMNITOOL",
                        new ItemStack(ModItems.thaumiumOmnitool),
                        6,
                        CraftingAspectList.thaumiumOmnitool,
                        new ItemStack(ModItems.thaumiumChainsaw),
                        new ItemStack(ModItems.thaumiumDrill),
                        new OreIngredient("plateThaumium"),
                        new OreIngredient("plateThaumium"),
                        new OreIngredient("plateThaumium"),
                        IC2Items.getItem("crafting", "carbon_plate"),
                        Ingredient.fromStacks(
                                new ItemStack(IC2Items.getItem("ingot", "tin").getItem(), 1, 302),
                                new ItemStack(IC2Items.getItem("ingot", "tin").getItem(), 1, 303)
                        )));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":thaumiumomnitoolupgrade"),
                new ElectricInfusionRecipe("THAUMIUMOMNITOOL",
                        new ItemStack(ModItems.thaumiumOmnitool),
                        6,
                        CraftingAspectList.thaumiumOmnitool,
                        new ItemStack(ModItems.diamondOmnitool),
                        new ItemStack(Blocks.DIAMOND_BLOCK),
                        new ItemStack(BlocksTC.metalAlchemical),
                        new ItemStack(BlocksTC.metalAlchemical),
                        IC2Items.getItem("energy_crystal"),
                        IC2Items.getItem("crafting", "carbon_plate"),
                        IC2Items.getItem("crafting", "advanced_circuit")));
        ItemStack streamChainsawStack = new ItemStack(ModItems.streamChainsaw);
        EnumInfusionEnchantment.addInfusionEnchantment(streamChainsawStack, EnumInfusionEnchantment.BURROWING, 1);
        EnumInfusionEnchantment.addInfusionEnchantment(streamChainsawStack, EnumInfusionEnchantment.COLLECTOR, 1);
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":streamchainsaw"),
                new ElectricInfusionRecipe("STREAMCHAINSAW",
                        streamChainsawStack,
                        6,
                        CraftingAspectList.streamChainsaw,
                        ModItems.thaumiumChainsaw,
                        new ItemStack(Items.WATER_BUCKET),
                        new ItemStack(ItemsTC.elementalAxe),
                        new ItemStack(BlocksTC.logGreatwood),
                        IC2Items.getItem("lapotron_crystal").getItem(),
                        IC2Items.getItem("crafting", "iridium"),
                        IC2Items.getItem("upgrade", "overclocker")));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "armourring"),
                new InfusionRecipe("ARMOURCHARGINGRING",
                        new ItemStack(ModItems.chargeRing),
                        6,
                        CraftingAspectList.armourChargingRing,
                        new ItemStack(ItemsTC.baubles, 1, 1),
                        new ItemStack(ModItems.electricBoots),
                        IC2Items.getItem("te", "generator"),
                        IC2Items.getItem("te", "geo_generator"),
                        IC2Items.getItem("te", "solar_generator"),
                        new ItemStack(IC2Items.getItem("te", "generator").getItem(), 1, 12),
                        new ItemStack(IC2Items.getItem("te", "generator").getItem(), 1, 4),
                        IC2Items.getItem("te", "nuclear_reactor")));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "inventoryring"),
                new InfusionRecipe("INVENTORYCHARGINGRING",
                        new ItemStack(ModItems.chargeRing, 1, 1),
                        6,
                        CraftingAspectList.inventoryChargingRing,
                        new ItemStack(ItemsTC.baubles, 1, 1),
                        new ItemStack(ModItems.thaumiumDrill),
                        IC2Items.getItem("te", "generator"),
                        IC2Items.getItem("te", "geo_generator"),
                        IC2Items.getItem("te", "solar_generator"),
                        new ItemStack(IC2Items.getItem("te", "generator").getItem(), 1, 12),
                        new ItemStack(IC2Items.getItem("te", "generator").getItem(), 1, 4),
                        IC2Items.getItem("te", "nuclear_reactor")));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "nanowings"),
                new ElectricInfusionRecipe("NANOWINGS",
                        new ItemStack(ModItems.nanoWings),
                        4,
                        CraftingAspectList.nanoWings,
                        new ItemStack(ModItems.thaumiumWings),
                        IC2Items.getItem("crafting", "carbon_plate"),
                        IC2Items.getItem("crafting", "carbon_plate"),
                        IC2Items.getItem("crafting", "carbon_plate"),
                        IC2Items.getItem("nano_chestplate"),
                        new OreIngredient("nitor")));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "quantumwings"),
                new ElectricInfusionRecipe("QUANTUMWINGS",
                        new ItemStack(ModItems.quantumWings),
                        6,
                        CraftingAspectList.quantumWings,
                        new ItemStack(ModItems.nanoWings),
                        IC2Items.getItem("crafting", "iridium"),
                        IC2Items.getItem("crafting", "iridium"),
                        IC2Items.getItem("crafting", "iridium"),
                        IC2Items.getItem("quantum_chestplate"),
                        new OreIngredient("nitor")));
        ItemStack rockbreakerDrillStack = new ItemStack(ModItems.rockBreakerDrill);
        EnumInfusionEnchantment.addInfusionEnchantment(rockbreakerDrillStack, EnumInfusionEnchantment.SOUNDING, 2);
        EnumInfusionEnchantment.addInfusionEnchantment(rockbreakerDrillStack, EnumInfusionEnchantment.REFINING, 1);
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "rockbreakerdrill"),
                new ElectricInfusionRecipe("ROCKBREAKERDRILL",
                        rockbreakerDrillStack,
                        6,
                        CraftingAspectList.rockbreakerRill,
                        new ItemStack(ModItems.thaumiumDrill),
                        new ItemStack(Items.FLINT_AND_STEEL),
                        new ItemStack(Items.FIRE_CHARGE),
                        new ItemStack(ItemsTC.elementalPick),
                        new ItemStack(ItemsTC.elementalShovel),
                        IC2Items.getItem("lapotron_crystal"),
                        IC2Items.getItem("resource", "reinforced_stone"),
                        IC2Items.getItem("crafting", "iridium"),
                        IC2Items.getItem("upgrade", "overclocker")
                        ));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "basicCompressed3"),
                new InfusionRecipe("COMPRESSEDSOLAR",
                        new ItemStack(ModBlocks.solarGeneratorCompressed),
                        2,
                        CraftingAspectList.compressedSolar,
                        new ItemStack(ModBlocks.solarGenerator, 1, 8),
                        new ItemStack(ModBlocks.solarGenerator, 1, 8),
                        new ItemStack(ModBlocks.solarGenerator, 1, 8),
                        new ItemStack(ModBlocks.solarGenerator, 1, 8),
                        new ItemStack(ModBlocks.solarGenerator, 1, 8),
                        new ItemStack(ModBlocks.solarGenerator, 1, 8),
                        new ItemStack(ModBlocks.solarGenerator, 1, 8),
                        new ItemStack(ModBlocks.solarGenerator, 1, 8),
                        new ItemStack(ModBlocks.solarGenerator, 1, 8)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "mjolnir"),
                new ElectricInfusionRecipe("MJOLNIR",
                        new ItemStack(ModItems.mjölnir),
                        1,
                        CraftingAspectList.mjölnir,
                        new ItemStack(ModItems.stormCaster, 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(ModItems.materials, 1, 1),
                        new ItemStack(ModItems.materials, 1, 1),
                        new ItemStack(ItemsTC.elementalSword,1, OreDictionary.WILDCARD_VALUE),
                        ThaumcraftApiHelper.makeCrystal(Aspect.ENERGY),
                        ThaumcraftApiHelper.makeCrystal(Aspect.WATER),
                        ThaumcraftApiHelper.makeCrystal(Aspect.AIR),
                        new OreIngredient("nitor")));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "stormbreaker"),
                new ElectricInfusionRecipe("STORMBREAKER",
                        ItemHelper.getChargedItem((IElectricItem)ModItems.stormBreaker, 1),
                        10,
                        CraftingAspectList.stormbreaker,
                        new ItemStack(ModItems.mjölnir, 1, OreDictionary.WILDCARD_VALUE),
                        new ItemStack(ModItems.materials, 1, 1),
                        new ItemStack(ModItems.materials, 1, 1),
                        new ItemStack(Blocks.WEB),
                        new ItemStack(ItemsTC.elementalSword),
                        new ItemStack(IC2Items.getItem("lapotron_crystal").getItem(), 1, OreDictionary.WILDCARD_VALUE),
                        IC2Items.getItem("crafting", "iridium"),
                        IC2Items.getItem("crafting", "iridium"),
                        new ItemStack(IC2Items.getItem("nano_saber").getItem(), 1, OreDictionary.WILDCARD_VALUE)));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "generatorPotentia"),
                new InfusionRecipe("GENERATORPOTENTIA",
                        new ItemStack(ModBlocks.essentiaGenerator),
                        6,
                        CraftingAspectList.generatorPotentia,
                        new ItemStack(IC2Items.getItem("te", "generator").getItem(), 1, 10),
                        new ItemStack(Items.DIAMOND),
                        ThaumcraftApiHelper.makeCrystal(Aspect.EXCHANGE),
                        new ItemStack(Blocks.HOPPER),
                        new ItemStack(BlocksTC.jarNormal),
                        IC2Items.getItem("te", "mv_transformer"),
                        IC2Items.getItem("resource", "advanced_machine"),
                        generatorWater,
                        IC2Items.getItem("crafting", "scrap")));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "industrialVisCharger"),
                new InfusionRecipe("CHARGINGSTATION",
                        new ItemStack(ModBlocks.industrialVisCharger),
                        6,
                        CraftingAspectList.industrialCharger,
                        new ItemStack(BlocksTC.rechargePedestal),
                        IC2Items.getItem("te", "tesla_coil"),
                        IC2Items.getItem("crafting", "iridium"),
                        new OreIngredient("blockDiamond"),
                        ThaumcraftApiHelper.makeCrystal(Aspect.ORDER),
                        new ItemStack(BlocksTC.jarNormal)));
        initUUInfusionRecipes();
    }

    public static void initUUInfusionRecipes(){
        ItemStack crystal = new ItemStack(ModItems.materials, 1, 10);
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "uuglowstone"),
                new InfusionRecipe("UUCRYSTAL",
                        new ItemStack(Blocks.GLOWSTONE),
                        4,
                        CraftingAspectList.uu4,
                        new ItemStack(Items.GLOWSTONE_DUST),
                        crystal));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "uulapis"),
                new InfusionRecipe("UUCRYSTAL",
                        new ItemStack(Items.DYE, 8, 4),
                        4,
                        CraftingAspectList.uu8,
                        ThaumcraftApiHelper.makeCrystal(Aspect.SENSES),
                        crystal,
                        crystal,
                        crystal));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "uuredstone"),
                new InfusionRecipe("UUCRYSTAL",
                        new ItemStack(Items.REDSTONE, 24),
                        4,
                        CraftingAspectList.uu8,
                        ThaumcraftApiHelper.makeCrystal(Aspect.ENERGY),
                        crystal,
                        crystal,
                        crystal));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "uuresin"),
                new InfusionRecipe("UUCRYSTAL",
                        new ItemStack(IC2Items.getItem("misc_resource", "resin").getItem(), 21, IC2Items.getItem("misc_resource", "resin").getItemDamage()),
                        4,
                        CraftingAspectList.uu8,
                        ThaumcraftApiHelper.makeCrystal(Aspect.TRAP),
                        crystal,
                        crystal,
                        crystal));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "uucoal"),
                new InfusionRecipe("UUCRYSTAL",
                        new ItemStack(Items.COAL, 16),
                        4,
                        CraftingAspectList.uu8,
                        new ItemStack(Items.COAL, 1 ,1),
                        crystal,
                        crystal));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "uuuranium"),
                new InfusionRecipe("UUCRYSTAL",
                        new ItemStack(IC2Items.getItem("resource", "uranium_ore").getItem(), 8, IC2Items.getItem("resource", "uranium_ore").getItemDamage()),
                        4,
                        CraftingAspectList.uu16,
                        new ItemStack(BlocksTC.amberBrick),
                        crystal,
                        crystal,
                        crystal));
        if(OreDictionary.doesOreNameExist("oreLead")) {
            ItemStack leadStack = OreDictionary.getOres("oreLead").get(0);
            ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "uulead"),
                    new InfusionRecipe("UUCRYSTAL",
                            //new ItemStack(IC2Items.getItem("resource", "lead_ore").getItem(), 16, IC2Items.getItem("resource", "lead_ore").getItemDamage()),
                            leadStack,
                            4,
                            CraftingAspectList.uu8,
                            new ItemStack(BlocksTC.amberBlock),
                            crystal,
                            crystal));
        }
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "uutin"),
                new InfusionRecipe("UUCRYSTAL",
                        new ItemStack(IC2Items.getItem("resource", "tin_ore").getItem(), 16, IC2Items.getItem("resource", "tin_ore").getItemDamage()),
                        4,
                        CraftingAspectList.uu8,
                        new ItemStack(BlocksTC.stoneArcaneBrick),
                        crystal,
                        crystal));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "uucopper"),
                new InfusionRecipe("UUCRYSTAL",
                        new ItemStack(IC2Items.getItem("resource", "copper_ore").getItem(), 16, IC2Items.getItem("resource", "copper_ore").getItemDamage()),
                        4,
                        CraftingAspectList.uu8,
                        new ItemStack(BlocksTC.stoneArcane),
                        crystal,
                        crystal));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "uuiron"),
                new InfusionRecipe("UUCRYSTAL",
                        new ItemStack(Blocks.IRON_ORE, 16),
                        4,
                        CraftingAspectList.uu8,
                        new ItemStack(Blocks.STONEBRICK),
                        crystal,
                        crystal));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "uugold"),
                new InfusionRecipe("UUCRYSTAL",
                        new ItemStack(Items.GOLD_INGOT, 2),
                        4,
                        CraftingAspectList.uu4,
                        new ItemStack(Items.IRON_INGOT),
                        crystal,
                        crystal,
                        crystal));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "uudiamond"),
                new InfusionRecipe("UUCRYSTAL",
                        new ItemStack(Items.DIAMOND),
                        4,
                        CraftingAspectList.uu8,
                        new ItemStack(Items.GOLD_INGOT),
                        crystal,
                        crystal,
                        crystal,
                        crystal));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "uuuraniumd"),
                new InfusionRecipe("UUCRYSTAL",
                        new ItemStack(IC2Items.getItem("nuclear", "uranium_238").getItem(), 2, IC2Items.getItem("nuclear", "uranium_238").getItemDamage()),
                        4,
                        CraftingAspectList.uu16,
                        new ItemStack(Items.DIAMOND),
                        crystal, crystal,
                        crystal, crystal,
                        crystal, crystal,
                        crystal, crystal));
        ThaumcraftApi.addInfusionCraftingRecipe(new ResourceLocation(Reference.MOD_ID, "uuiridium"),
                new InfusionRecipe("UUCRYSTAL",
                        new ItemStack(IC2Items.getItem("misc_resource", "iridium_ore").getItem(), 2, IC2Items.getItem("misc_resource", "iridium_ore").getItemDamage()),
                        4,
                        CraftingAspectList.uu32,
                        new ItemStack(IC2Items.getItem("nuclear", "uranium_238").getItem(), 2, IC2Items.getItem("nuclear", "uranium_238").getItemDamage()),
                        crystal, crystal,
                        crystal, crystal,
                        crystal, crystal,
                        crystal, crystal,
                        crystal, crystal,
                        crystal, crystal));
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
                        'S', new ItemStack(ItemsTC.scribingTools, 1, 0),
                        'E', IC2Items.getItem("energy_crystal")));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":electricGoggles"),
                new ShapedArcaneRecipe(new ResourceLocation("electricGoggles"),
                        "ELECTRICGOGGLES",
                        10,
                        CraftingAspectList.electricHelmet,
                        ItemHelper.getChargedItem((IElectricItem) ModItems.electricGoggles, 0),
                        " H ", "BGB", "RCR",
                        'G', new ItemStack(ItemsTC.goggles),
                        'C', IC2Items.getItem("crafting", "circuit"),
                        'H', new ItemStack(Items.DIAMOND_HELMET, 1, 0),
                        'B', IC2Items.getItem("re_battery"),
                        'R', new ItemStack(Items.REPEATER)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":diamondomnitool"),
                new ShapelessArcaneRecipe(new ResourceLocation(""),
                        "DIAMONDOMNITOOL",
                        10,
                        CraftingAspectList.diamondOmnitool,
                        ModItems.diamondOmnitool,
                        new ItemStack(ModItems.diamondChainsaw),
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

        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":thaumiumwing"),
                new ShapedArcaneRecipe(new ResourceLocation(""),
                        "THAUMICWINGS",
                        10,
                        CraftingAspectList.thaumiumWings,
                        new ItemStack(ModItems.materials, 1, 9),
                        "ftp", "ftp", "ftp",
                        'f', new ItemStack(ModItems.materials, 1, 2),
                        't', new OreIngredient("plateThaumium"),
                        'p', new OreIngredient("plateIron")));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Reference.MOD_ID + ":thaumiumwings"),
                new ShapedArcaneRecipe(new ResourceLocation(""),
                        "THAUMICWINGS",
                        10,
                        CraftingAspectList.thaumiumWings,
                        ModItems.thaumiumWings,
                        "ww",
                        'w', new ItemStack(ModItems.materials, 1, 9)));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Reference.MOD_ID +  ":basicCompressed"),
                new ShapedArcaneRecipe(new ResourceLocation(""),
                        "COMPRESSEDSOLAR",
                        10,
                        CraftingAspectList.compressedSolar,
                        new ItemStack(ModBlocks.solarGenerator),
                        "ppp", "ppp", "ppp",
                        'p', IC2Items.getItem("te", "solar_generator")));
        ThaumcraftApi.addArcaneCraftingRecipe(new ResourceLocation(Reference.MOD_ID +  ":basicCompressed2"),
                new ShapedArcaneRecipe(new ResourceLocation(""),
                        "COMPRESSEDSOLAR",
                        10,
                        CraftingAspectList.compressedSolar,
                        new ItemStack(ModBlocks.solarGenerator, 1, 8),
                        "ppp", "ppp", "ppp",
                        'p', new ItemStack(ModBlocks.solarGenerator)));

>>>>>>> master
    }
    public static void initCrucibleRecipes(){
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "aquaCompressed"),
                new CrucibleRecipe("COMPRESSEDSOLARAQUA",
                        new ItemStack(ModBlocks.solarGenerator, 1, 1),
                        new ItemStack(ModBlocks.solarGenerator),
                        CraftingAspectList.aquaSolar));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "aquaCompressed2"),
                new CrucibleRecipe("COMPRESSEDSOLARAQUA",
                        new ItemStack(ModBlocks.solarGenerator, 1, 9),
                        new ItemStack(ModBlocks.solarGenerator, 1, 8),
                        CraftingAspectList.aquaSolar));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "aquaCompressed3"),
                new CrucibleRecipe("COMPRESSEDSOLARAQUA",
                        new ItemStack(ModBlocks.solarGeneratorCompressed, 1, 1),
                        new ItemStack(ModBlocks.solarGeneratorCompressed),
                        CraftingAspectList.aquaSolar));

        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "perditioCompressed"),
                new CrucibleRecipe("COMPRESSEDSOLARPERDITIO",
                        new ItemStack(ModBlocks.solarGenerator, 1, 2),
                        new ItemStack(ModBlocks.solarGenerator),
                        CraftingAspectList.perditioSolar));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "perditioCompressed2"),
                new CrucibleRecipe("COMPRESSEDSOLARPERDITIO",
                        new ItemStack(ModBlocks.solarGenerator, 1, 10),
                        new ItemStack(ModBlocks.solarGenerator, 1, 8),
                        CraftingAspectList.perditioSolar));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "perditioCompressed3"),
                new CrucibleRecipe("COMPRESSEDSOLARPERDITIO",
                        new ItemStack(ModBlocks.solarGeneratorCompressed, 1, 2),
                        new ItemStack(ModBlocks.solarGeneratorCompressed),
                        CraftingAspectList.perditioSolar));

<<<<<<< HEAD
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
=======
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "ordoCompressed"),
                new CrucibleRecipe("COMPRESSEDSOLARORDO",
                        new ItemStack(ModBlocks.solarGenerator, 1, 3),
                        new ItemStack(ModBlocks.solarGenerator),
                        CraftingAspectList.ordoSolar));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "ordoCompressed2"),
                new CrucibleRecipe("COMPRESSEDSOLARORDO",
                        new ItemStack(ModBlocks.solarGenerator, 1, 11),
                        new ItemStack(ModBlocks.solarGenerator, 1, 8),
                        CraftingAspectList.ordoSolar));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "ordoCompressed3"),
                new CrucibleRecipe("COMPRESSEDSOLARORDO",
                        new ItemStack(ModBlocks.solarGeneratorCompressed, 1, 3),
                        new ItemStack(ModBlocks.solarGeneratorCompressed),
                        CraftingAspectList.ordoSolar));

        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "ignisCompressed"),
                new CrucibleRecipe("COMPRESSEDSOLARIGNIS",
                        new ItemStack(ModBlocks.solarGenerator, 1, 4),
                        new ItemStack(ModBlocks.solarGenerator),
                        CraftingAspectList.ignisSolar));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "ignisCompressed2"),
                new CrucibleRecipe("COMPRESSEDSOLARIGNIS",
                        new ItemStack(ModBlocks.solarGenerator, 1, 12),
                        new ItemStack(ModBlocks.solarGenerator, 1, 8),
                        CraftingAspectList.ignisSolar));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "ignisCompressed3"),
                new CrucibleRecipe("COMPRESSEDSOLARIGNIS",
                        new ItemStack(ModBlocks.solarGeneratorCompressed, 1, 4),
                        new ItemStack(ModBlocks.solarGeneratorCompressed),
                        CraftingAspectList.ignisSolar));

        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "aerCompressed"),
                new CrucibleRecipe("COMPRESSEDSOLARAER",
                        new ItemStack(ModBlocks.solarGenerator, 1, 5),
                        new ItemStack(ModBlocks.solarGenerator),
                        CraftingAspectList.aerSolar));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "aerCompressed2"),
                new CrucibleRecipe("COMPRESSEDSOLARAER",
                        new ItemStack(ModBlocks.solarGenerator, 1, 13),
                        new ItemStack(ModBlocks.solarGenerator, 1, 8),
                        CraftingAspectList.aerSolar));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "aerCompressed3"),
                new CrucibleRecipe("COMPRESSEDSOLARAER",
                        new ItemStack(ModBlocks.solarGeneratorCompressed, 1, 5),
                        new ItemStack(ModBlocks.solarGeneratorCompressed),
                        CraftingAspectList.aerSolar));

        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "terraCompressed"),
                new CrucibleRecipe("COMPRESSEDSOLARTERRA",
                        new ItemStack(ModBlocks.solarGenerator, 1, 6),
                        new ItemStack(ModBlocks.solarGenerator),
                        CraftingAspectList.terraSolar));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "terraCompressed2"),
                new CrucibleRecipe("COMPRESSEDSOLARTERRA",
                        new ItemStack(ModBlocks.solarGenerator, 1, 14),
                        new ItemStack(ModBlocks.solarGenerator, 1, 8),
                        CraftingAspectList.terraSolar));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "terraCompressed3"),
                new CrucibleRecipe("COMPRESSEDSOLARTERRA",
                        new ItemStack(ModBlocks.solarGeneratorCompressed, 1, 6),
                        new ItemStack(ModBlocks.solarGeneratorCompressed),
                        CraftingAspectList.terraSolar));

        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "uumattercrystal"),
                new CrucibleRecipe("UUCRYSTAL",
                        new ItemStack(ModItems.materials, 1, 10),
                        IC2Items.getItem("misc_resource", "matter"),
                        CraftingAspectList.uuCrystal));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "ignisgenerator"),
                new CrucibleRecipe("GENERATORIGNIS",
                        new ItemStack(ModBlocks.essentiaGenerator, 1, 3),
                        new ItemStack(ModBlocks.essentiaGenerator),
                        CraftingAspectList.generatorIgnis));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "auramgenerator"),
                new CrucibleRecipe("GENERATORAURAM",
                        new ItemStack(ModBlocks.essentiaGenerator, 1, 2),
                        new ItemStack(ModBlocks.essentiaGenerator),
                        CraftingAspectList.generatorAuram));
        ThaumcraftApi.addCrucibleRecipe(new ResourceLocation(Reference.MOD_ID, "aergenerator"),
                new CrucibleRecipe("GENERATORAER",
                        new ItemStack(ModBlocks.essentiaGenerator, 1, 1),
                        new ItemStack(ModBlocks.essentiaGenerator),
                        CraftingAspectList.generatorAer));
    }
>>>>>>> master

    public static void initIC2Recipes(){

        recipes.addRecipe(new ItemStack(ModItems.ironOmnitool),
                "C", "p", "D",
                'C', IC2Items.getItem("chainsaw"),
                'D', IC2Items.getItem("drill"),
                'p', refinedIron);

<<<<<<< HEAD

        ItemStack helmet = new ItemStack(ModItems.quantumGoggles);
        recipes.addShapelessRecipe(helmet.copy(), (new FlagModifier(helmet.copy(), "EUReaderUpgrade", true)).setUsesInput(), helmet.copy(), Ic2Items.euReader.copy());
        recipes.addShapelessRecipe(helmet.copy(), (new FlagModifier(helmet.copy(), "CropUpgrade", true)).setUsesInput(), helmet.copy(), Ic2Items.cropAnalyzer.copy());
        recipes.addShapelessRecipe(helmet.copy(), (new FlagModifier(helmet.copy(), "ThermometerUpgrade", true)).setUsesInput(), helmet.copy(), Ic2Items.thermometer.copy());
        helmet = new ItemStack(ModItems.nanoGoggles);
        recipes.addShapelessRecipe(helmet.copy(), (new FlagModifier(helmet.copy(), "EUReaderUpgrade", true)).setUsesInput(), helmet.copy(), Ic2Items.euReader.copy());
        recipes.addShapelessRecipe(helmet.copy(), (new FlagModifier(helmet.copy(), "CropUpgrade", true)).setUsesInput(), helmet.copy(), Ic2Items.cropAnalyzer.copy());
        recipes.addShapelessRecipe(helmet.copy(), (new FlagModifier(helmet.copy(), "ThermometerUpgrade", true)).setUsesInput(), helmet.copy(), Ic2Items.thermometer.copy());

=======
        ICraftingRecipeList recipes = ClassicRecipes.advCrafting;

        ItemStack thaumiumDrill = new ItemStack(ModItems.thaumiumDrill);
        recipes.addRecipe(new ItemStack(ModItems.thaumiumDrill),
                "ISI", "CDC", "UMU",
                (new EnchantmentModifier(thaumiumDrill.copy(), Enchantments.SILK_TOUCH)).setUsesInput(),
                'I', IC2Items.getItem("crafting", "iridium"),
                'S', Items.SHEARS,
                'C', IC2Items.getItem("crafting", "advanced_circuit"),
                'D', thaumiumDrill,
                'U', new ItemStack(IC2Items.getItem("crafting", "circuit").getItem(), 1, 260),
                'M', new ItemStack(IC2Items.getItem("crafting", "circuit").getItem(), 1, 263));
        recipes.addRecipe(new ItemStack(ModItems.thaumiumDrill),
                "ICI", "EDE", "UMU",
                (new EnchantmentModifier(thaumiumDrill.copy(), Enchantments.FORTUNE, 3)).setUsesInput(),
                'I', IC2Items.getItem("crafting", "iridium"),
                'C', new ItemStack(IC2Items.getItem("crafting", "circuit").getItem(), 5, IC2Items.getItem("crafting", "advanced_circuit").getItemDamage()),
                'E', new ItemStack(Items.END_CRYSTAL, 3),
                'D', thaumiumDrill,
                'U', new ItemStack(IC2Items.getItem("crafting", "circuit").getItem(), 1, 260),
                'M', new ItemStack(IC2Items.getItem("crafting", "circuit").getItem(), 5, 263));

//        ItemStack iridiumDrill = new ItemStack(ModItems.iridiumDrill);
//        recipes.addRecipe(new ItemStack(ModItems.iridiumDrill),
//                " I ", "IDI", " E ",
//                'I', IC2Items.getItem("crafting", "iridium"),
//                'D', IC2Items.getItem("diamond_drill"),
//                'E', IC2Items.getItem("energy_crystal"));
//        recipes.addRecipe(new ItemStack(ModItems.iridiumDrill),
//                "ISI", "CDC", "UMU",
//                (new EnchantmentModifier(iridiumDrill.copy(), Enchantments.SILK_TOUCH)).setUsesInput(),
//                'I', IC2Items.getItem("crafting", "iridium"),
//                'S', Items.SHEARS,
//                'C', IC2Items.getItem("crafting", "advanced_circuit"),
//                'D', iridiumDrill,
//                'U', new ItemStack(IC2Items.getItem("crafting", "circuit").getItem(), 1, 260),
//                'M', new ItemStack(IC2Items.getItem("crafting", "circuit").getItem(), 1, 263));
//        recipes.addRecipe(new ItemStack(ModItems.iridiumDrill),
//                "ICI", "EDE", "UMU",
//                (new EnchantmentModifier(iridiumDrill.copy(), Enchantments.FORTUNE, 3)).setUsesInput(),
//                'I', IC2Items.getItem("crafting", "iridium"),
//                'C', new ItemStack(IC2Items.getItem("crafting", "circuit").getItem(), 5, IC2Items.getItem("crafting", "advanced_circuit").getItemDamage()),
//                'E', new ItemStack(Items.END_CRYSTAL, 3),
//                'D', iridiumDrill,
//                'U', new ItemStack(IC2Items.getItem("crafting", "circuit").getItem(), 1, 260),
//                'M', new ItemStack(IC2Items.getItem("crafting", "circuit").getItem(), 5, 263));
        recipes.addRecipe(new ItemStack(ModItems.gemPack),
                "P", "C", "E",
                'P', IC2Items.getItem("energy_pack"),
                'C', IC2Items.getItem("crafting", "circuit"),
                'E', IC2Items.getItem("energy_crystal"));
        recipes.addRecipe(new ItemStack(ModItems.gemPack),
                " C ", "BTB", " E ",
                'C', IC2Items.getItem("crafting", "circuit"),
                'B', IC2Items.getItem("re_battery"),
                'T', IC2Items.getItem("ingot", "tin"),
                'E', IC2Items.getItem("energy_crystal"));
        recipes.addRecipe(IC2Items.getItem("lappack"),
                "LPL", " C ",
                'L', new ItemStack(Blocks.LAPIS_BLOCK),
                'P', new ItemStack(ModItems.gemPack),
                'C', IC2Items.getItem("crafting", "advanced_circuit"));
>>>>>>> master
        if(ElectroMagicTools.ic2ceLoaded){
            initIC2CERecipes();
        }
    }

<<<<<<< HEAD
=======
    private static void initIC2CRecipes(){
        ItemStack ironClusterRecipe = IC2Items.getItem("dust", "iron").copy();
        ironClusterRecipe.setCount(2);
        Recipes.macerator.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemsTC.clusters, 1, 0)), null, true, ironClusterRecipe);

        ItemStack goldClusterRecipe = IC2Items.getItem("dust", "gold").copy();
        goldClusterRecipe.setCount(2);
        Recipes.macerator.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemsTC.clusters, 1, 1)), null, true, goldClusterRecipe);

        ItemStack copperClusterRecipe = IC2Items.getItem("dust", "copper").copy();
        copperClusterRecipe.setCount(2);
        Recipes.macerator.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemsTC.clusters, 1, 2)), null, true, copperClusterRecipe);

        ItemStack tinClusterRecipe = IC2Items.getItem("dust", "tin").copy();
        tinClusterRecipe.setCount(2);
        Recipes.macerator.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemsTC.clusters, 1, 3)), null, true, tinClusterRecipe);

        ItemStack silverClusterRecipe = IC2Items.getItem("dust", "silver").copy();
        silverClusterRecipe.setCount(2);
        Recipes.macerator.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemsTC.clusters, 1, 4)), null, true, silverClusterRecipe);

//        ItemStack leadClusterRecipe = IC2Items.getItem("dust", "lead").copy();
//        leadClusterRecipe.setCount(22);
//        Recipes.macerator.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemsTC.clusters, 1, 5)), null, true, leadClusterRecipe);

        ItemStack uraniumCluster = new ItemStack(IC2Items.getItem("nuclear", "uranium_238").getItem(), 3, 180).copy();
        Recipes.macerator.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ModItems.materials, 1, 0)), null, true, uraniumCluster);

    }

>>>>>>> master
    private static void initIC2CERecipes(){

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

        ItemStack uraniumCluster = new ItemStack(Item.getByNameOrId("ic2c_extras:uraniumcrushedore"), 5, 180);
        Recipes.macerator.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ModItems.materials, 1, 0)), null, true, uraniumCluster);

        /* Thaumium Plates Recipes */
        //thaumiumPlate = GameRegistry.addShapedRecipe(new ItemStack(EMTItems.itemEMTItems, 1, 5), "X", "Y", "Z", 'Y', new ItemStack(ConfigItems.itemResource, 1, 2), 'X', new ItemStack(IC2Items.getItem("ForgeHammer").getItem(), 1, OreDictionary.WILDCARD_VALUE), 'Z', new ItemStack(Blocks.obsidian));
        Recipes.metalformerRolling.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ItemsTC.ingots)), null, true, new ItemStack(ItemsTC.plate, 1, 2));

        /* Ore Processing for Amber and Cinnabar */
        ItemStack crushedAmberRecipe = new ItemStack(ModItems.materials, 1, 11);
        crushedAmberRecipe.setCount(2);
        Recipes.macerator.addRecipe(Recipes.inputFactory.forStack(new ItemStack(BlocksTC.oreAmber)), null, true, crushedAmberRecipe);

        ItemStack crushedCinnabarRecipe = new ItemStack(ModItems.materials, 1, 13);
        crushedCinnabarRecipe.setCount(2);
        Recipes.macerator.addRecipe(Recipes.inputFactory.forStack(new ItemStack(BlocksTC.oreCinnabar)), null, true, crushedCinnabarRecipe);

        NBTTagCompound waterAmount = new NBTTagCompound();
        waterAmount.setInteger("amount", 1000);

        NBTTagCompound heatAmount = new NBTTagCompound();
        heatAmount.setInteger("minHeat", 1000);

        ItemStack smallCopperDust = IC2Items.getItem("dust", "small_copper");
        ItemStack smallTinDust = IC2Items.getItem("dust", "small_tin");

        ItemStack stoneDust = new ItemStack(Item.getByNameOrId("ic2c_extras:stonedust"));

        /* Skipping Ore Washing Plant */
        Recipes.centrifuge.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ModItems.materials, 1, 11)), heatAmount, true, smallCopperDust, new ItemStack(ItemsTC.amber), stoneDust);
        Recipes.centrifuge.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ModItems.materials, 1, 13)), heatAmount, true, smallTinDust, new ItemStack(ItemsTC.quicksilver), stoneDust);

        smallCopperDust.setCount(2);
        smallTinDust.setCount(2);

        Recipes.oreWashing.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ModItems.materials, 1, 11)), waterAmount, true, new ItemStack(ModItems.materials, 1, 12), smallCopperDust, stoneDust);
        Recipes.oreWashing.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ModItems.materials, 1, 13)), waterAmount, true, new ItemStack(ModItems.materials, 1, 14), smallTinDust, stoneDust);

        Recipes.centrifuge.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ModItems.materials, 1, 12)), heatAmount, true, smallCopperDust, new ItemStack(ItemsTC.amber));
        Recipes.centrifuge.addRecipe(Recipes.inputFactory.forStack(new ItemStack(ModItems.materials, 1, 14)), heatAmount, true, smallTinDust, new ItemStack(ItemsTC.quicksilver));
    }

    public static void initSmeltingRecipes(){
        //GameRegistry.addSmelting(new ItemStack(ModItems.materials, 1, 0), new ItemStack(IC2Items.getItem("nuclear", "uranium_238").getItem(), 2, 180), 0);
        GameRegistry.addSmelting(new ItemStack(ModItems.materials, 1, 5), new ItemStack(ModItems.materials, 1, 3), 0.5f);

        if(ElectroMagicTools.ic2ceLoaded){
            GameRegistry.addSmelting(new ItemStack(ModItems.materials, 1, 11), new ItemStack(ItemsTC.amber), 0.1f);
            GameRegistry.addSmelting(new ItemStack(ModItems.materials, 1, 12), new ItemStack(ItemsTC.amber), 0.1f);
            GameRegistry.addSmelting(new ItemStack(ModItems.materials, 1, 13), new ItemStack(ItemsTC.quicksilver), 0.1f);
            GameRegistry.addSmelting(new ItemStack(ModItems.materials, 1, 14), new ItemStack(ItemsTC.quicksilver), 0.1f);
        }
    }

    public static void initNativeClusters(){
        int uraId = Item.getIdFromItem(IC2Items.getItem("dust", "iron").getItem());
        int uraCluId = Item.getIdFromItem(ModItems.materials);
        FMLInterModComms.sendMessage("thaumcraft", "nativeCluster",uraId+",180,"+uraCluId+",0,2.0");
    }
}
