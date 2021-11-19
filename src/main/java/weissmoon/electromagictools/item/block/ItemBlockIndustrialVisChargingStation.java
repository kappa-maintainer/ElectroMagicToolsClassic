package weissmoon.electromagictools.item.block;

import ic2.core.platform.lang.storage.Ic2InfoLang;
import ic2.core.platform.player.PlayerHandler;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import weissmoon.core.item.WeissItemBlock;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.block.ModBlocks;

import java.util.List;

/**
 * Created by Weissmoon on 11/9/21.
 */
public class ItemBlockIndustrialVisChargingStation extends WeissItemBlock{
    public ItemBlockIndustrialVisChargingStation(){
        super(ModBlocks.industrialVisCharger);
        setCreativeTab(ElectroMagicTools.EMTtab);
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
        if (handler.hasEUReader()) {
            tooltip.add(Ic2InfoLang.euReaderSinkInfo.getLocalizedFormatted(2048.0));
        }
    }
}
