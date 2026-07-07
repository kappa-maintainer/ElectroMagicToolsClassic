package weissmoon.electromagictools.core.api.client.item;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;

/**
 * A class can implement this interface if you want to have different code for different rendering levels
 */
public interface IItemTieredRender extends IItemRenderer{

    /**
     * This method is for the default Minecraft fancy setting
     * @param type is the perspective form which an item is being viewed
     * @param item is the itemstack that is being rendered
     * @param data Extra Type specific data
     */
    public void renderFancy (ItemCameraTransforms.TransformType type, ItemStack item, Object... data);

    /**
     * This method is for the default Minecraft fancy setting and a configuration option in my mod, set at the user's
     * discretion
     * @param type is the perspective form which an item is being viewed
     * @param item is the itemstack that is being rendered
     * @param data Extra Type specific data
     */
    public void renderVeryFancy (ItemCameraTransforms.TransformType type, ItemStack item, Object... data);
}
