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
    public static AspectList diamondOmnitool = new AspectList().add(Aspect.ORDER, 15).add(Aspect.AIR, 15);
}
