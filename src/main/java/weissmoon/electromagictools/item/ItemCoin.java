package weissmoon.electromagictools.item;

import ic2.api.classic.item.ICoinItem;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import weissmoon.core.api.client.item.IItemRenderCustom;
import weissmoon.core.api.client.item.IItemRenderer;
import weissmoon.core.client.render.IIcon;
import weissmoon.core.client.render.IIconRegister;
import weissmoon.core.client.render.renderOverride.CustomRenderRegistry;
import weissmoon.core.item.WeissItem;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Reference;
import weissmoon.electromagictools.lib.Strings;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Weissmoon on 10/16/20.
 */
public class ItemCoin extends WeissItem implements ICoinItem, IItemRenderCustom {

    private String[] mat = {"tin", "brass", "lead", "silver", "thaumium", "void"};

    public ItemCoin() {
        super(Strings.Items.COIN_NAME);
        hasSubtypes = true;
        setCreativeTab(ElectroMagicTools.EMTtab);
    }
//    itemInfo.coinValue.name
    @SideOnly(Side.CLIENT)
    public void registerIcons (IIconRegister iconRegister){
        itemIconWeiss = iconRegister.registerIcon(this, getTranslationKey().substring(getTranslationKey().indexOf(".") + 1));
        itemIconArray = new IIcon[6];
        int i = 0;
        for(String nem:mat) {
            itemIconArray[i] = iconRegister.registerIcon(this, getTranslationKey().substring(getTranslationKey().indexOf(".") + 1)+ nem);
            i++;
        }
    }

    @Override
    public String getTranslationKey(ItemStack itemstack) {
        return "item.itemIndustrialCoin";
    }
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TextComponentTranslation("itemInfo.coinValue.name", getMoneyValue(stack)).getUnformattedComponentText());
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(@Nonnull CreativeTabs creativeTabs, @Nonnull NonNullList<ItemStack> list) {
        if (isInCreativeTab(creativeTabs))
            for (int i = 0; i <= 5; i++) {
                list.add(new ItemStack(this, 1, i));
            }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon (ItemStack stack, int pass){
        try{
            return itemIconArray[stack.getItemDamage()];
        }catch(IndexOutOfBoundsException ignored){
            return itemIconWeiss;
        }
    }

    @Override
    public int getMoneyValue(ItemStack stack) {
        int c = stack.getItemDamage();
        if(c == 0)
            //1 iron
            return 4;//tin
        if(c == 1)
            //8 bronze
            return 32;//brass
        if(c == 2)
            //64 copper
            return 128;//lead
        if(c == 3)
            return 256;//silver
        if(c == 4)
            //512 gold
            return 1024;//thaumium
        if(c == 5)
            //4096 uranium
            return 16384;//void
            //32768 iridium
        return 0;
    }

    @Override
    public IItemRenderer getIItemRender() {
        return CustomRenderRegistry.getMissingRender();
    }
}
