package weissmoon.electromagictools.core.block;

import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import weissmoon.electromagictools.core.client.render.IIconRegister;

/**
 * Helper interface for Blocks
 * Created by Weissmoon on 11/13/16.
 */
public interface IBlockWeiss {

    /**
     * @return registry mod ID
     */
    String getModID();

    /**
     * @return registry name
     */
    String getWeissName();

    /**
     * @return list of IProperty to ignore in client StateMapper
     */
    @SideOnly(Side.CLIENT)
    IProperty[] getIgnoredProperties();

    /**
     * @return custom StateMapper for block rendering
     * return null to use vanilla StateMapper
     */
    @SideOnly(Side.CLIENT)
    IStateMapper getStateMapper();

    boolean hasItemBlock();

    /**
     * Used to register Block model/StateMapper
     * @param iIconRegister instance to call
     */
    @SideOnly(Side.CLIENT)
    void registerBlockIcons(IIconRegister iIconRegister);
}
