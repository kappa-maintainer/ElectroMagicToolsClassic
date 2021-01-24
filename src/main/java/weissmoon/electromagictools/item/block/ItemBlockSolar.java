package weissmoon.electromagictools.item.block;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import weissmoon.core.api.client.item.IItemRenderCustom;
import weissmoon.core.api.client.item.IItemRenderer;
import weissmoon.core.client.render.IIcon;
import weissmoon.core.client.render.IIconRegister;
import weissmoon.core.item.WeissItemBlock;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.client.item.SolarIItemRenderer;
import weissmoon.electromagictools.lib.Reference;
import weissmoon.electromagictools.lib.Strings;

import javax.annotation.Nonnull;

import static weissmoon.electromagictools.block.ModBlocks.solarGenerator;
import static weissmoon.electromagictools.block.ModBlocks.solarGeneratorCompressed;

/**
 * Created by Weissmoon on 9/11/20.
 */
public class ItemBlockSolar extends WeissItemBlock implements IItemRenderCustom {
    private final boolean compressed;
    private IIcon[] itemIconArray;
    private String[] icon = {"solarbasic", "solaraqua", "solarperditio", "solarordo", "solarignis", "solaraer", "solarterra"};
    public ItemBlockSolar(boolean compressed) {
        super(compressed ? solarGeneratorCompressed : solarGenerator);
        this.compressed = compressed;
        setCreativeTab(ElectroMagicTools.EMTtab);
        hasSubtypes = true;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> list) {
        if (isInCreativeTab(tab)){
            final int s = compressed ? 7 : 15;
            int i = 0;
            while(i < s) {
                ItemStack stack = new ItemStack(this, 1, i);
                list.add(stack);
                i++;
                if(i == 7)
                    i++;
            }
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
            int i = itemstack.getMetadata();
            boolean c = i > 7;
            sub = icon[c ? i-8: i];
            String aspect = new TextComponentTranslation("block.welectromagic:"+sub+".name").getUnformattedComponentText();
            String name = new TextComponentTranslation("block.welectromagic:blockSolarPanel.name", aspect).getUnformattedComponentText();
            if(compressed) {
                return new TextComponentTranslation("block.welectromagic:solarTriple.name", name).getUnformattedComponentText();
            }else if(c){
                return new TextComponentTranslation("block.welectromagic:solarDouble.name", name).getUnformattedComponentText();
            }
            return name;
        }catch (ArrayIndexOutOfBoundsException e){
            //sub = sub;
        }
        return "item." + Reference.MOD_ID + ":" + Strings.Items.MATERIALS_NAME + sub;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon (ItemStack stack, int pass){
        int i = stack.getMetadata();
        try {
            if (itemIconArray[i] != null)
                return itemIconArray[i];
        }catch (ArrayIndexOutOfBoundsException e){
            //e.printStackTrace();
        }
        return itemIconWeiss;
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIconArray = new IIcon[Strings.Items.Materials.length];
        int n = compressed ? 3 : 1;
        int i = 0;
        int x = compressed ? 7 : 15;
        for(;i < x;i++) {
            if(i == 7){
                n = 2;
                continue;
            }
            itemIconArray[i] = iconRegister.registerIcon(this, "blocks/" + icon[i > 7 ? i-8 : i] + n);

        }
        super.registerIcons(iconRegister);
    }

    @Override
    public IItemRenderer getIItemRender() {
        return new SolarIItemRenderer();
    }
}
