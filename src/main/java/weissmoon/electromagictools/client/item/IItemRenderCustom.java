package weissmoon.electromagictools.client.item;

import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * An item must implement this interface to have a custom renderer
 * Created by Weissmoon on 9/29/16.
 */

public interface IItemRenderCustom {

    /**
     * Called once
     * @return the IItemRender instance for the item
     */
    @SideOnly(Side.CLIENT)
    IItemRenderer getIItemRender();
}