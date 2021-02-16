package weissmoon.electromagictools.client;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import org.lwjgl.opengl.GL11;

/**
 * Created by Weissmoon on 12/21/20.
 */
public class ModelIndustrialVisCharger extends ModelBase {

    private static ModelRenderer probe;

    public ModelIndustrialVisCharger(){
        super();
        probe = new ModelRenderer(this, 0, 0);
        probe.addBox(-1.0F, 0.0F, 1.5F, 4, 10, 4);
        probe.setRotationPoint(0.0F, 0.0F, 0.0F);
        probe.rotateAngleX = 180;
    }

    public void render (float f5){
        probe.render(f5);
    }

    public void setPosition(double pos){
//        probe.offsetZ = (float) (pos * 1.35) - 0.45F;
//        probe.offsetY = (float) pos + 0.15F;
    }
}
