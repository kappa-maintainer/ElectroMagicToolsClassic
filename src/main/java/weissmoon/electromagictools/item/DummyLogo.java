package weissmoon.electromagictools.item;

import weissmoon.core.client.render.IIconRegister;
import weissmoon.core.item.WeissItem;

/**
 * Created by Weissmoon on 9/3/19.
 */
public class DummyLogo extends WeissItem {

    //public static DummyLogo instance = new DummyLogo();

    private DummyLogo() {
        super("logo");
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        super.registerIcons(iconRegister);
    }
}
