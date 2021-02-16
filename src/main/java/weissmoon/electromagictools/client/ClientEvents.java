package weissmoon.electromagictools.client;

import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import weissmoon.core.client.render.IIcon;
import weissmoon.electromagictools.block.tile.EssentiaGeneratorBase;
import weissmoon.electromagictools.block.tile.TileIndustrialChargePedestal;
import weissmoon.electromagictools.client.block.EssentiaGeneratorTESR;
import weissmoon.electromagictools.client.block.IndustrialChargePedestalTESR;

/**
 * Created by Weissmoon on 9/16/20.
 */
public class ClientEvents {

    public static float partialtick = 0;
    protected IIcon itemIconWeiss;

    @SubscribeEvent
    public void onModelRegister(TextureStitchEvent.Post evt){
        TextureMap map = evt.getMap();
//        this.itemIconWeiss = evt.getMap().loadTexture();
    }

    @SideOnly(Side.CLIENT)
    @SubscribeEvent
    public void registerModels(ModelRegistryEvent event) {
//        ModelLoader.setCustomModelResourceLocation(IC2Items.getItemAPI().getItem("batpack"),0, new ModelResourceLocation("welectromagic:itemenergypack#inventory"));
//        ModelLoader.registerItemVariants(IC2Items.getItemAPI().getItem("batpack"), new ModelResourceLocation("welectromagic:itemenergypack#inventory"));
//        ModelLoader.setCustomModelResourceLocation(ModItems.gemPack, 0, new ModelResourceLocation("minecraft:ic2itemarmorbatpack#inventory"));
//        ModelLoader.registerItemVariants(ModItems.gemPack, new ModelResourceLocation("minecraft:ic2itemarmorbatpack#inventory"));
//        evt.getModelRegistry().putObject(new ModelResourceLocation("welectromagic:solartop1"));
//        this.itemIconWeiss = evt.getMap().loadTexture();
        registerTESR();
    }

    public void onTick(TickEvent.RenderTickEvent event){
        partialtick = event.renderTickTime;
    }

    public static void registerTESR(){
        ClientRegistry.bindTileEntitySpecialRenderer(EssentiaGeneratorBase.class, new EssentiaGeneratorTESR());
        ClientRegistry.bindTileEntitySpecialRenderer(TileIndustrialChargePedestal.class, new IndustrialChargePedestalTESR());
    }

}
