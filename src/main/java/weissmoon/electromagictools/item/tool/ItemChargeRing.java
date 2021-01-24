package weissmoon.electromagictools.item.tool;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
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

/**
 * Created by Weissmoon on 3/18/20.
 */
public class ItemChargeRing extends WeissItem implements IBauble, IItemRenderCustom{
    public ItemChargeRing() {
        super(Strings.Items.CHARGE_RING_NAME);
        setMaxStackSize(1);
        setCreativeTab(ElectroMagicTools.EMTtab);
    }

    @Override
    public String getUnlocalizedName(ItemStack itemstack) {
        String sub = "";
        try{
            int i = itemstack.getMetadata();
            sub = String.valueOf(i).intern();
        }catch (ArrayIndexOutOfBoundsException ignored){}
        return "item." + Reference.MOD_ID + ":" + Strings.Items.CHARGE_RING_NAME + sub;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(@Nonnull CreativeTabs creativeTabs, @Nonnull NonNullList<ItemStack> list) {
        if (!isInCreativeTab(creativeTabs))
            return;

        list.add(new ItemStack(this, 1, 0));
        list.add(new ItemStack(this, 1, 1));
    }

    @SideOnly(Side.CLIENT)
    public void registerIcons(IIconRegister iconRegister){
        itemIconWeiss = iconRegister.registerIcon(this, getUnlocalizedName().substring(getUnlocalizedName().indexOf(".") + 1));
        itemIconArray = new IIcon[2];
        itemIconArray[0] = iconRegister.registerIcon(this, Reference.MOD_ID + ":" + "itemarmourring");
        itemIconArray[1] = iconRegister.registerIcon(this, Reference.MOD_ID + ":" + "iteminventoryring");
    }

    @SideOnly(Side.CLIENT)
    @Override
    public IIcon getIcon(ItemStack stack, int pass){
        int i = stack.getMetadata();
        try {
            if (itemIconArray[i] != null)
                return itemIconArray[i];
        }catch (ArrayIndexOutOfBoundsException ignored){}
        return itemIconWeiss;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.RING;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        if (!(player instanceof EntityPlayer))
            return;

        int meta = itemstack.getMetadata();
        if(meta == 0){
            double energy = 32;
            for (ItemStack armor:player.getArmorInventoryList()){
                if(armor == ItemStack.EMPTY || !(armor.getItem() instanceof IElectricItem))
                    continue;
                energy -= ElectricItem.manager.charge(armor, energy, 4, true, false);
                if(energy <= 0)
                    break;
            }
        }
        if(meta == 1){
            double energy = 32;
            for (ItemStack item:((EntityPlayer) player).inventory.mainInventory){
                if(item == ItemStack.EMPTY || !(item.getItem() instanceof IElectricItem))
                    continue;
                energy -= ElectricItem.manager.charge(item, energy, 4, true, false);
                if(energy <= 0)
                    break;
            }
        }
    }

    @Override
    public IItemRenderer getIItemRender() {
        return CustomRenderRegistry.getMissingRender();
    }
}
