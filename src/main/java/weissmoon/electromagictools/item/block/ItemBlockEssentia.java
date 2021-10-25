package weissmoon.electromagictools.item.block;

import ic2.core.platform.lang.storage.Ic2InfoLang;
import ic2.core.platform.player.PlayerHandler;
import net.minecraft.block.Block;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import weissmoon.core.item.WeissItemBlock;
import weissmoon.electromagictools.lib.Reference;
import weissmoon.electromagictools.lib.Strings;

import javax.annotation.Nonnull;

import java.util.List;

import static weissmoon.electromagictools.block.ModBlocks.essentiaGenerator;

/**
 * Created by Weissmoon on 10/16/20.
 */
public class ItemBlockEssentia extends WeissItemBlock {


    private String[] icon = {"essentiaPotentia", "essentiaAer", "essentiaAura", "essentiaIgnis"};

    public ItemBlockEssentia() {
        super(essentiaGenerator);
        hasSubtypes = true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> list) {
        if (isInCreativeTab(tab)){
            final int s = 4;
            int i = 0;
            while(i < s) {
                ItemStack stack = new ItemStack(this, 1, i);
                list.add(stack);
                i++;
            }
        }
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        PlayerHandler handler = PlayerHandler.getClientPlayerHandler();
        if (handler.hasEUReader()) {
            tooltip.add(Ic2InfoLang.electricProduction.getLocalizedFormatted(Ic2InfoLang.electricTransferRateVariable.getLocalized()));
        }
    }

    @Override
    public int getMetadata(int damage) {
        return damage;
    }

    @Override
    public String getItemStackDisplayName(ItemStack itemstack) {
        String sub = "Null";
        try{
            sub = icon[itemstack.getMetadata()];
            String aspect = new TextComponentTranslation("tile.welectromagic:"+sub+".name").getUnformattedComponentText();
            return new TextComponentTranslation("tile.welectromagic:blockEssentiaGenerator.name", aspect).getUnformattedComponentText();
        }catch (ArrayIndexOutOfBoundsException e){
            //sub = sub;
        }
        return "item." + Reference.MOD_ID + ":" + Strings.Items.MATERIALS_NAME + sub;
    }
}
