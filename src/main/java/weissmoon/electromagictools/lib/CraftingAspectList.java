package weissmoon.electromagictools.lib;

import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectList;

/**
 * Created by Weissmoon on 9/7/19.
 */
public class CraftingAspectList {

    public static AspectList electricScribingTools = new AspectList().add(Aspect.AIR, 16).add(Aspect.ORDER, 16).add(Aspect.ENTROPY, 16);
    public static AspectList solarHelmetRevealing = new AspectList().add(Aspect.ENERGY, 32).add(Aspect.AIR, 32).add(Aspect.LIGHT, 25);
    public static AspectList electricHelmet = new AspectList().add(Aspect.AIR, 20).add(Aspect.ORDER, 20).add(Aspect.WATER, 20);
    public static AspectList nanoHelmet = new AspectList().add(Aspect.PROTECT, 16).add(Aspect.SENSES, 16).add(Aspect.ENERGY, 16);
    public static AspectList quantumHelmet = new AspectList().add(Aspect.PROTECT, 25).add(Aspect.SENSES, 25).add(Aspect.ENERGY, 25);

    public static AspectList electricBoots = new AspectList().add(Aspect.ENERGY, 16).add(Aspect.PROTECT, 32).add(Aspect.MOTION, 32);
    public static AspectList nanoBoots = new AspectList().add(Aspect.ENERGY, 25).add(Aspect.PROTECT, 32).add(Aspect.MOTION, 48);
    public static AspectList quantumBoots = new AspectList().add(Aspect.ENERGY, 32).add(Aspect.PROTECT, 32).add(Aspect.MOTION, 64);
    public static AspectList electricHoe = new AspectList().add(Aspect.ENERGY, 32).add(Aspect.PLANT, 25).add(Aspect.TOOL, 32).add(Aspect.MAGIC, 48);
    public static AspectList thaumiumDrill = new AspectList().add(Aspect.TOOL, 16).add(Aspect.CRAFT, 16).add(Aspect.ENERGY, 16);
    public static AspectList thaumiumChainsaw = new AspectList().add(Aspect.TOOL, 16).add(Aspect.AVERSION, 16).add(Aspect.ENERGY, 16);
    public static AspectList thaumiumOmnitool = new AspectList().add(Aspect.TOOL, 25).add(Aspect.MECHANISM, 25).add(Aspect.ENERGY, 25).add(Aspect.AVERSION, 25);
    public static AspectList streamChainsaw = new AspectList().add(Aspect.ENERGY, 48).add(Aspect.MECHANISM, 48).add(Aspect.TOOL, 32).add(Aspect.WATER, 32);
    public static AspectList armourChargingRing = new AspectList().add(Aspect.ENERGY, 64).add(Aspect.MECHANISM, 32).add(Aspect.MAGIC, 48);
    public static AspectList inventoryChargingRing = new AspectList().add(Aspect.ENERGY, 64).add(Aspect.TOOL, 32).add(Aspect.MAGIC, 48);


    public static AspectList diamondOmnitool = new AspectList().add(Aspect.ORDER, 15).add(Aspect.AIR, 15);

    public static AspectList thaumiumWings = new AspectList().add(Aspect.AIR, 48).add(Aspect.EARTH, 48).add(Aspect.FIRE, 48).add(Aspect.WATER, 48).add(Aspect.ENTROPY, 48).add(Aspect.ORDER,48);
    public static AspectList nanoWings = new AspectList().add(Aspect.FLIGHT, 48).add(Aspect.MECHANISM, 48).add(Aspect.AIR, 32).add(Aspect.ENERGY, 32);
    public static AspectList quantumWings = nanoWings.copy().add(Aspect.PROTECT, 48);
    public static AspectList rockbreakerRill = new AspectList().add(Aspect.TOOL, 32).add(Aspect.ENERGY, 48).add(Aspect.SENSES, 32);
    public static AspectList uu4 = new AspectList().add(Aspect.EARTH, 4).add(Aspect.AIR, 4).add(Aspect.FIRE, 4).add(Aspect.WATER, 4).add(Aspect.ORDER, 4).add(Aspect.ENTROPY, 4);
    public static AspectList uu8 = new AspectList().add(uu4).add(uu4);
    public static AspectList uu16 = new AspectList().add(uu8).add(uu8);
    public static AspectList uu32 = new AspectList().add(uu16).add(uu16);
    public static AspectList compressedSolar = new AspectList().add(Aspect.FIRE, 8).add(Aspect.WATER, 8).add(Aspect.EARTH, 8).add(Aspect.AIR, 8).add(Aspect.ENTROPY,8).add(Aspect.ORDER, 9);
    public static AspectList solarBase = new AspectList().add(Aspect.EXCHANGE, 16).add(Aspect.MAGIC, 16);
    public static AspectList aquaSolar = new AspectList().add(Aspect.WATER, 16).add(solarBase);
    public static AspectList perditioSolar = new AspectList().add(Aspect.ENTROPY, 16).add(solarBase);
    public static AspectList ordoSolar = new AspectList().add(Aspect.ORDER, 16).add(solarBase);
    public static AspectList ignisSolar = new AspectList().add(Aspect.FIRE, 16).add(solarBase);
    public static AspectList aerSolar = new AspectList().add(Aspect.AIR, 16).add(solarBase);
    public static AspectList terraSolar = new AspectList().add(Aspect.EARTH, 16).add(solarBase);
    public static AspectList uuCrystal = new AspectList().add(Aspect.CRYSTAL, 6).add(Aspect.COLD, 6).add(Aspect.MAGIC, 8);
    public static AspectList mjölnir = new AspectList().add(Aspect.TOOL, 48).add(Aspect.AURA, 48).add(Aspect.ELDRITCH, 32);
    public static AspectList stormbreaker = new AspectList().add(Aspect.TOOL, 49).add(Aspect.ENERGY, 64).add(Aspect.BEAST, 32);
    public static AspectList generatorPotentia = new AspectList().add(Aspect.ENERGY, 32).add(Aspect.EXCHANGE, 32).add(Aspect.MAGIC, 32).add(Aspect.METAL, 64);
    public static AspectList generatorIgnis = new AspectList().add(Aspect.FIRE, 16).add(Aspect.EXCHANGE, 8);
    public static AspectList generatorAuram = new AspectList().add(Aspect.AURA, 16).add(Aspect.EXCHANGE, 8);
    public static AspectList generatorAer = new AspectList().add(Aspect.AIR, 16).add(Aspect.EXCHANGE, 8);
    public static AspectList industrialCharger = new AspectList().add(Aspect.ENERGY, 48).add(Aspect.CRAFT, 48).add(Aspect.EXCHANGE, 48).add(Aspect.DESIRE, 48);

    public static AspectList coreDrill = new AspectList().add(Aspect.ENERGY, 48).add(Aspect.MECHANISM, 48).add(Aspect.TOOL, 32).add(Aspect.EARTH, 32);

    public static AspectList efferetsGift = new AspectList().add(Aspect.FIRE, 55).add(Aspect.CRAFT, 24).add(Aspect.ENERGY, 48).add(Aspect.COLD, 55);
    public static AspectList kappaArm = new AspectList().add(Aspect.WATER, 8).add(Aspect.ORDER, 17);
}
