package weissmoon.electromagictools.item.block;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import weissmoon.core.item.WeissItemBlock;
import weissmoon.electromagictools.block.ModBlocks;

import javax.annotation.Nonnull;

/**
 * Created by Weissmoon on 2/12/21.
 */
public class ItemObsidianTile extends WeissItemBlock{

    public ItemObsidianTile(){
        super(ModBlocks.obsidianTile);
        hasSubtypes = true;
    }

    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> list) {
        if (isInCreativeTab(tab)){
            final int s = 5;
            int i = 0;
            while(i < s) {
                ItemStack stack = new ItemStack(this, 1, i);
                list.add(stack);
                i++;
            }
        }
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

}
