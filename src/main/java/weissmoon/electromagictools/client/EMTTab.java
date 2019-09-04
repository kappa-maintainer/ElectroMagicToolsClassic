package weissmoon.electromagictools.client;

import net.minecraft.item.ItemStack;
import weissmoon.core.client.creativetab.CreativeTabWeiss;
import weissmoon.electromagictools.item.DummyLogo;
import weissmoon.electromagictools.lib.Reference;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class EMTTab extends CreativeTabWeiss {
    public EMTTab() {
        super(Reference.MOD_NAME, new ItemStack(DummyLogo.instance));
    }
}
