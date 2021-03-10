package weissmoon.electromagictools.item.tool.drill;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import weissmoon.core.utils.NBTHelper;
import weissmoon.electromagictools.lib.Strings;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Weissmoon on 9/2/20.
 */
public class ItemIridiumDrill extends ItemThaumiumDrill{

    private static String MODE_NBT_KEY = "emtMode";

    public ItemIridiumDrill(){
        super(ToolMaterial.DIAMOND, Strings.Items.IRIDIUM_DRILL_NAME, 300000, 1000, 2);
        operationEnergyCost = 300;
        efficiency = 25F;
        MinecraftForge.EVENT_BUS.register(new IridiumDrillEvent());
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn){
        super.addInformation(stack, worldIn, tooltip, flagIn);

        boolean mode = NBTHelper.getBoolean(stack, MODE_NBT_KEY);
        if (mode){
            tooltip.add(Enchantments.FORTUNE.getTranslatedName(3));
        }else{
            tooltip.add(Enchantments.SILK_TOUCH.getTranslatedName(1));
        }
    }

    @Override
    public boolean hasEffect(ItemStack stack)
    {
        return true;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, net.minecraft.enchantment.Enchantment enchantment)
    {
        if(enchantment == Enchantments.FORTUNE || enchantment == Enchantments.SILK_TOUCH)
            return false;
        return enchantment.type.canEnchantItem(stack.getItem());
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book)
    {
        NBTTagList nbttaglist = book.getEnchantmentTagList();
        for (int j = 0; j < nbttaglist.tagCount(); ++j)
        {
            NBTTagCompound nbttagcompound = nbttaglist.getCompoundTagAt(j);
            int k = nbttagcompound.getShort("id");
            Enchantment enchantment = Enchantment.getEnchantmentByID(k);

            if(enchantment == Enchantments.FORTUNE || enchantment == Enchantments.SILK_TOUCH){
                return false;
            }
        }
        return super.isBookEnchantable(stack, book);
    }

//    @Override
    public void changeToolMode(ItemStack stack) {
        NBTHelper.toggleBoolean(stack, MODE_NBT_KEY);
    }
}
