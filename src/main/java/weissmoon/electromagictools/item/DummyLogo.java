package weissmoon.electromagictools.item;

import weissmoon.core.client.render.IIconRegister;
import weissmoon.core.item.WeissItem;
import weissmoon.electromagictools.lib.Reference;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class DummyLogo extends WeissItem {

    //public static DummyLogo instance = new DummyLogo();

    private DummyLogo() {
        super("logo");
        setUnlocalizedName(Reference.MOD_ID + ".logo");
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIconWeiss = iconRegister.registerIcon(this, this.getRegistryName().toString());
    }
}
