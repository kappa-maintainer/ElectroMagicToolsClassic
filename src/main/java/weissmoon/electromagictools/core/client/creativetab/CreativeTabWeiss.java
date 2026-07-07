package weissmoon.electromagictools.core.client.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Item;

import javax.annotation.Nonnull;

/**
 * Base creative tab
 */
public class CreativeTabWeiss extends CreativeTabs{
    private String tabLabel;
    private ItemStack tabItem;

    public CreativeTabWeiss (String label, ItemStack item){
        super(label);
        this.tabLabel = label;
        this.tabItem = item;
    }

    @Nonnull
    @Override
    public ItemStack createIcon (){
        if (this.tabItem != null){
            return this.tabItem;
        }
        return new ItemStack(Item.getItemById(1));
    }

    public String getTranslatedTabLabel (){
        if (this.tabLabel == null){
            return "entity.Item.name";
        }
        return this.tabLabel;
    }
}