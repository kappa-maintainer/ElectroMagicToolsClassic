package weissmoon.electromagictools.item.tool.drill;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.init.Enchantments;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.eventhandler.EventPriority;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
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
        super(ToolMaterial.DIAMOND, Strings.Items.IRIDIUM_DRILL_NAME);
        maxCharge = 300000;
        cost = 800;//todo
        tier = 2;
        transferLimit = 1000;
        efficiency = 25F;
        MinecraftForge.EVENT_BUS.register(this);
    }

    @SubscribeEvent(priority = EventPriority.LOWEST)
    public void onBlockBreak(BlockEvent.BreakEvent event){
        if(event.getPlayer().getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemIridiumDrill){
            boolean mode = NBTHelper.getBoolean(event.getPlayer().getHeldItem(EnumHand.MAIN_HAND), MODE_NBT_KEY);
            if(mode){
                event.setExpToDrop(event.getState().getBlock().getExpDrop(event.getState(), event.getWorld(), event.getPos(), 3));
            }else if(event.getState().getBlock().canSilkHarvest(event.getWorld(), event.getPos(), event.getState(), event.getPlayer())){
                event.setExpToDrop(0);
            }
        }
    }

    @SubscribeEvent(priority = EventPriority.HIGHEST)
    public void blockDrops(BlockEvent.HarvestDropsEvent event){
        if(event.getHarvester() != null)
            if(event.getHarvester().getHeldItem(EnumHand.MAIN_HAND).getItem() instanceof ItemIridiumDrill) {
                boolean mode = NBTHelper.getBoolean(event.getHarvester().getHeldItem(EnumHand.MAIN_HAND), MODE_NBT_KEY);
                if (mode) {
                    List<ItemStack> drops = event.getState().getBlock().getDrops(event.getWorld(), event.getPos(), event.getState(), 3);
                    event.getDrops().clear();
                    event.getDrops().addAll(drops);
                }
            }
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
