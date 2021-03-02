package weissmoon.electromagictools;

import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;
import weissmoon.electromagictools.block.ModBlocks;
import weissmoon.electromagictools.item.ModItems;

/**
 * Created by Weissmoon on 9/21/19.
 */
public class OreDictionaryEntries {

    public static void initOreEntries() {
        OreDictionary.registerOre("clusterUranium", new ItemStack(ModItems.materials, 1, 0));
        OreDictionary.registerOre("glue", new ItemStack(ModItems.materials, 1, 3));
        OreDictionary.registerOre("slimeball", new ItemStack(ModItems.materials, 1, 3));
        OreDictionary.registerOre("obsidian", new ItemStack(ModBlocks.obsidianTile));
    }
}
