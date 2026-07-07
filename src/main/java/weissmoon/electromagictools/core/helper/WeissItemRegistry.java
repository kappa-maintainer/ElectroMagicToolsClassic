package weissmoon.electromagictools.core.helper;

import net.minecraft.item.Item;
import weissmoon.electromagictools.core.item.IItemWeiss;

import java.util.*;

/**
 * Registry of IItemWeiss instances
 * Created by Weissmoon on 7/22/16.
 */
public class WeissItemRegistry {
    public static final WeissItemRegistry weissItemRegistry = new WeissItemRegistry();
    private List<Item> items = new ArrayList<Item>();

    public void regItem(Item item){
        if(item instanceof IItemWeiss){
            weissItemRegistry.items.add(item);
        }
    }

    public List<Item>getItemList(){
        List<Item> list = new ArrayList<Item>(items);
        return list;
    }
}
