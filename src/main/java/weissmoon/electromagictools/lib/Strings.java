package weissmoon.electromagictools.lib;

import ic2.core.platform.lang.components.base.LangComponentHolder;
import ic2.core.platform.lang.components.base.LocaleComp;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class Strings {

    public static final class Items{
        public static final String SCRIBING_TOOLS_NAME = "itemElectricScribingTools";
        public static final String ELECTRIC_GOGGLES_NAME = "itemElectricGoggles";
        public static final String ELECTRIC_BOOTS_NAME = "itemElectricBoots";
        public static final String NANO_GOGGLES_NAME = "itemNanoGoggles";
        public static final String NANO_BOOTS_NAME = "itemNanoBoots";
        public static final String QUANTUM_GOGGLES_NAME = "itemQuantumGoggles";
        public static final String QUANTUM_BOOTS_NAME = "itemQuantumBoots";
        public static final String SOLAR_GOGGLES_NAME = "itemSolarGoggles";
        public static final String ELECTRIC_HOE_NAME = "itemElectricHoe";
        public static final String THAUMIUM_DRILL_NAME = "itemThaumiumDrill";
        public static final String CORE_DRILL_NAME = "itemCoreDrill";
        public static final String ROCKBREAKER_DRILL_NAME = "itemRockbreakerDrill";
        public static final String DIAMOND_CHAINSAW_NAME = "itemDiamondChainsaw";
        public static final String THAUMIUM_CHAINSAW_NAME = "itemThaumiumChainsaw";
        public static final String STREAM_CHAINSAW_NAME = "itemStreamChainsaw";
        public static final String IRON_OMNITOOL_NAME = "itemIronOmniTool";
        public static final String DIAMOND_OMNITOOL_NAME = "itemDiamondOmniTool";
        public static final String THAUMIUM_OMNITOOL_NAME = "itemThaumiumOmniTool";
        public static final String STORMBRINGER_NAME = "itemStormCaster";
        public static final String MJÖLNIR_NAME = "itemMjolnir";
        public static final String STORMBREAKER_NAME = "itemStormBreaker";
        public static final String CHARGE_RING_NAME = "itemChargeRing";
        public static final String FEATHER_WINGS_GAME = "itemFeatherWings";
        public static final String THAUMIUM_WINGS_GAME = "itemThaumiumWings";
        public static final String NANO_WINGS_GAME = "itemNanoWings";
        public static final String QUANTUM_WINGS_GAME = "itemQuantumWings";

        public static final String IRIDIUM_DRILL_NAME = "itemIridiumDrill";
        public static final String ENERGY_PACK_NEME = "itemEnergyPack";
        public static final String COIN_NAME = "itemCoin";
        public static final String ENTROPY_STAFF_NAME = "itemEntropyStaff";
        public static final String HIT_BAUBLE = "itemHitBauble";
        public static final String KAPPA_ARM_NAME = "itemKappaArm";

        public static final String MATERIALS_NAME = "itemMaterial";
        public static final String[] Materials = {
                "UraniumCluster",       //0
                "LightningSummoner",    //1
                "FeatherMesh",          //2
                "Glue",                 //3
                "DuctTape",             //4
                "RubberBall",           //5
                "CardBoard",            //6
                "FeatherWing",          //7
                "TaintedFeather",       //8
                "ThaumiumWing",         //9
                "UUMatterDrop",         //10
                "CrushedAmberOre",      //11
                "PurifiedAmberOre",     //12
                "CrushedCinnabarOre",   //13
                "PurifiedCinnabarOre"   //14
        };
    }

    public static final class Blocks{
        public static final String SOLAR_BLOCK_NAME = "blockSolarPanel";
        public static final String SOLAR_COMPRESSED_BLOCK_NAME = "blockSolarCompressedPanel";
        public static final String ESSENTIA_GENERATOR_BLOCK_NAME = "blockEssentiaGenerator";
        public static final String INDUSTRIAL_CHARGE_PEDESTAL = "blockChargePedestal";
        public static final String OBSIDIAN_TILE_NAME = "blockObsidianTile";
        public static final String SCANNER_BLOCK_NAME = "blockScanner";
    }

    public static final class LocaleComps{
        public static final LocaleComp MESSAGE_DIAMOND_CHAINSAW_NORMAL = new LangComponentHolder.LocaleItemInfoComp("message.diamondChainsawNormal.name");
        public static final LocaleComp MESSAGE_DIAMOND_CHAINSAW_NO_SHEAR = new LangComponentHolder.LocaleItemInfoComp("message.diamondChainsawNoShear.name");

        public static final LocaleComp DIAMOND_CHAINSAW_SHEAR_TOGGLE = new LangComponentHolder.LocaleItemInfoComp("tooltip.diamondChainsawShearToggle.name");
        public static final LocaleComp DIAMOND_OMNITOOL_TOGGLE = new LangComponentHolder.LocaleItemInfoComp("tooltip.diamondOmnitoolToggle.name");

        public static final LocaleComp DRILL_OF_THE_CORE_TOOLTIP = new LangComponentHolder.LocaleItemInfoComp("tooltip.MEGALOMANIA.name");

        public static final LocaleComp MESSAGE_IRIDIUM_DRILL_FORTUNE = new LangComponentHolder.LocaleItemInfoComp("message.iridiumDrillFortune.name");
        public static final LocaleComp MESSAGE_IRIDIUM_DRILL_NO_FORTUNE = new LangComponentHolder.LocaleItemInfoComp("message.iridiumDrillNoFortune.name");
        public static final LocaleComp MESSAGE_IRIDIUM_DRILL_SILK = new LangComponentHolder.LocaleItemInfoComp("message.iridiumDrillSilk.name");
        public static final LocaleComp MESSAGE_IRIDIUM_DRILL_NO_SILK = new LangComponentHolder.LocaleItemInfoComp("message.iridiumDrillNoSilk.name");
    }
}
