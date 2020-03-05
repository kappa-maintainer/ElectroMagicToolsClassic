package weissmoon.electromagictools.util;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;

import javax.annotation.Nullable;

/**
 * Created by Weissmoon on 2/15/20.
 */
public class GenericHelper {

    @Nullable
    public static RayTraceResult getEntityLookVector(EntityLivingBase entity, boolean collideLiquid, double range) {
        float f = entity.rotationPitch;
        float f1 = entity.rotationYaw;
        Vec3d vec3d = entity.getPositionEyes(1.0F);
        float f2 = MathHelper.cos(-f1 * 0.017453292F - 3.1415927F);
        float f3 = MathHelper.sin(-f1 * 0.017453292F - 3.1415927F);
        float f4 = -MathHelper.cos(-f * 0.017453292F);
        float f5 = MathHelper.sin(-f * 0.017453292F);
        float f6 = f3 * f4;
        float f7 = f2 * f4;
        Vec3d vec3d1 = vec3d.addVector((double)f6 * range, (double)f5 * range, (double)f7 * range);

        return entity.world.rayTraceBlocks(vec3d, vec3d1, collideLiquid, true, false);
    }
}
