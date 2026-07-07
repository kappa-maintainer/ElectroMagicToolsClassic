package weissmoon.electromagictools.core.client.render;

import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

/**
 * Intermediary class for lazy coding
 * Created by Weissmoon on 12/18/16.
 */
@SideOnly(Side.CLIENT)
public class IIcon extends ModelResourceLocation {
    protected IIcon(int p_i46078_1_, String... resourceName) {
        super(p_i46078_1_, resourceName);
    }

    public IIcon(String p_i46079_1_) {
        super(p_i46079_1_);
    }

    public IIcon(ResourceLocation p_i46080_1_, String p_i46080_2_) {
        super(p_i46080_1_, p_i46080_2_);
    }

    public IIcon(String p_i46081_1_, String p_i46081_2_) {
        super(p_i46081_1_, p_i46081_2_);
    }
}
