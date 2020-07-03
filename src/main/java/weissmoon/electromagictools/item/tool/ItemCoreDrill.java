package weissmoon.electromagictools.item.tool;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import thaumcraft.api.ThaumcraftMaterials;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Strings;

public class ItemCoreDrill extends ItemThaumiumDrill {
    public ItemCoreDrill() {
        super(ThaumcraftMaterials.TOOLMAT_ELEMENTAL, Strings.Items.CORE_DRILL_NAME, 1000000, 1000, 3);
        this.operationEnergyCost = 350;
        this.efficiency = 25F;
        this.setCreativeTab(ElectroMagicTools.EMTtab);
    }
    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState block, BlockPos pos, EntityLivingBase entityLiving) {
        if (!entityLiving.isSneaking() && !world.isRemote){
            ElectroMagicTools.logger.info("cluster dropped");
            IBlockState state = world.getBlockState(pos);
            if (world.rand.nextInt(100) == 0){

            }
        }
        return super.onBlockDestroyed(stack, world, block, pos, entityLiving);
    }
}
