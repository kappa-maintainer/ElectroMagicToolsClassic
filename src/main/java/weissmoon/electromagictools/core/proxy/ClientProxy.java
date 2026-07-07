package weissmoon.electromagictools.core.proxy;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraftforge.client.event.ModelBakeEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import weissmoon.electromagictools.core.client.render.WeissRenderItem;
import weissmoon.electromagictools.core.client.render.renderOverride.BridgeModel;
import weissmoon.electromagictools.core.client.render.renderOverride.CustomRenderRegistry;

/**
 * Client side only logic
 */
public class ClientProxy extends CommonProxy{

    public IBakedModel dustBa = new BridgeModel();

    public static final WeissRenderItem weissRenderItem = new WeissRenderItem();

    public void initRenderer (){
        MinecraftForge.EVENT_BUS.register(this);
    }

    public void registerRenderOverride(){
        CustomRenderRegistry.registerToModelManager();
    }

    @SubscribeEvent
    public void onModelBakeEvent(ModelBakeEvent modelBakeEvent){
        modelBakeEvent.getModelRegistry().putObject(
            new net.minecraft.client.renderer.block.model.ModelResourceLocation("welectromagic:renderOverride", "inventory"),
            this.dustBa
        );
    }
}
