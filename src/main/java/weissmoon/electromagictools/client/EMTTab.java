package weissmoon.electromagictools.client;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import weissmoon.core.client.creativetab.CreativeTabWeiss;
import weissmoon.core.utils.NBTHelper;
import weissmoon.electromagictools.item.ModItems;
import weissmoon.electromagictools.lib.Reference;

import javax.annotation.Nonnull;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class EMTTab extends CreativeTabWeiss {

    private ItemStack tabItem;

    public EMTTab() {
        super(Reference.MOD_NAME, null);
    }

    @Nonnull
    @Override
    public ItemStack createIcon (){
        if (this.tabItem == null){
            try {
                ItemStack s = new ItemStack(ModItems.materials, 1, 16);
                NBTHelper.setBoolean(s, "恋の抑止力", false);
                tabItem = s;
            }catch (Error e){
                System.out.println(e);
                return new ItemStack(Item.getItemById(1));
            }
        }
        return this.tabItem;
    }
}
