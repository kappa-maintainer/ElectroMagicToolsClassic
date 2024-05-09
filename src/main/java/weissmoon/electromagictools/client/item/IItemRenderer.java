package weissmoon.electromagictools.client.item;

import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.item.ItemStack;

/**
 * Created by Weissmoon on 9/21/16.
 */

/**
 * A class must extend this interface in order to have custom render code.
 */
public interface IItemRenderer {

    public enum ItemRendererHelper{
        /**
         * Determines if a rotation effect should be used when rendering an
         * EntityItem, like most default blocks do.
         */
        ENTITY_ROTATION,

        /**
         * Determines if an up-and-down bobbing effect should be used when
         * rendering an EntityItem, like most default items do.
         */
        ENTITY_BOBBING,
        /**
         * Determines if an item should have a rising animation, like most
         * default items do.
         */
        EQUIPT_ANIMATION,
        /**
         * Determines if an item should return additional model, like .OBJ models for rendering
         */
        RETURN_VERTEX
    }

    /**
     * Checks if this renderer should handle a specific item's render type
     * @param item The item we are trying to render
     * @param cameraTransformType A render type to check if this renderer handles
     * @return true if this renderer should handle the given render type,
     * otherwise false
     */
    public boolean handleRenderType(ItemStack item, ItemCameraTransforms.TransformType cameraTransformType);

    /**
     * Checks if certain helper functionality should be executed for this renderer.
     * See ItemRendererHelper for more info
     *
     * @param cameraTransformType is the perspective form which an item is being viewed
     * @param item is the itemstack that is being rendered
     * @param helper The type of helper functionality to be ran
     * @return True to run the helper functionality, false to not.
     */
    public boolean shouldUseRenderHelper(ItemCameraTransforms.TransformType cameraTransformType, ItemStack item, ItemRendererHelper helper);


    /**
     * This method is used to call the rendering code that you want in order to render the item.
     *
     * @param cameraTransformType  is the perspective form which an item is being viewed
     * @param item is the itemstack that is being rendered
     * @param data Extra Type specific data
     */
    public void renderItem(ItemCameraTransforms.TransformType cameraTransformType, ItemStack item, Object... data);
}
