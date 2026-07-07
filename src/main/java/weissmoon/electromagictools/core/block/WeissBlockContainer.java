package weissmoon.electromagictools.core.block;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.client.renderer.block.statemap.IStateMapper;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import weissmoon.electromagictools.core.client.render.IIconRegister;
import weissmoon.electromagictools.core.helper.WeissBlockRegistry;

/**
 * Base block which must contain TileEntity
 * automatically register: modID, registry name, unlocalized name, into mod BlockRegistry for model registration
 * Created by Weissmoon on 11/13/16.
 */
public abstract class WeissBlockContainer extends BlockContainer implements IBlockWeiss {

    private final String ModId;
    protected final String RegName;

    protected WeissBlockContainer(String name, Material materialIn) {
        super(materialIn);
        this.ModId = "welectromagic";
        this.RegName = name;
        this.setTranslationKey(this.ModId.toLowerCase() + ":" + this.RegName);
        this.setRegistryName(this.ModId.toLowerCase() + ":" + this.RegName);
        WeissBlockRegistry.weissBlockRegistry.regBlock(this);
    }

    protected WeissBlockContainer(String name, Material materialIn, MapColor color) {
        super(materialIn, color);
        this.ModId = "welectromagic";
        this.RegName = name;
        this.setTranslationKey(this.ModId.toLowerCase() + ":" + this.RegName);
        this.setRegistryName(this.ModId.toLowerCase() + ":" + this.RegName);
        WeissBlockRegistry.weissBlockRegistry.regBlock(this);
    }

    @Override
    abstract public TileEntity createNewTileEntity(World worldIn, int meta);

    @Override
    public final String getModID() {
        return this.ModId;
    }

    @Override
    public final String getWeissName(){
        return this.RegName;
    }

    @Override
    public IProperty[] getIgnoredProperties() {
        return new IProperty[0];
    }

    @Override
    public IStateMapper getStateMapper() {
        return null;
    }

    @Override
    public boolean hasItemBlock() {
        return false;
    }

    @Override
    public void registerBlockIcons(IIconRegister iIconRegister) {
        iIconRegister.registerIcon(this);
    }
}
