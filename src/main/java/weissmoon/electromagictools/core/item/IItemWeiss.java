package weissmoon.electromagictools.core.item;

import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import weissmoon.electromagictools.core.client.render.IIcon;
import weissmoon.electromagictools.core.client.render.IIconRegister;

/**
 * Helper interface for Items
 */
public interface IItemWeiss{

    /**
     * @return registry mod ID
     */
    String getModID();

    /**
     * @return registry name
     */
    String getWeissName();

    /**
     * @param stack to get IIcon for
     * @param pass render pass
     * @return IIcon to render
     */
    @SideOnly(Side.CLIENT)
    IIcon getIcon (ItemStack stack, int pass);

    /**
     * Used to register Block model/StateMapper
     * @param iconRegister instance to call
     */
    @SideOnly(Side.CLIENT)
    public void registerIcons (IIconRegister iconRegister);
}
