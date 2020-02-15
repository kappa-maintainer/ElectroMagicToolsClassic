package weissmoon.electromagictools.item.tool;

import com.google.common.collect.Multimap;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftMaterials;
import weissmoon.core.item.tools.WeissItemsPickaxe;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Strings;

import static weissmoon.electromagictools.util.ItemHelper.getChargedItem;

/**
 * Created by Weissmoon on 9/6/19.
 */
public class ItemThaumiumDrill extends WeissItemsPickaxe implements IElectricItem{

    protected int maxCharge, cost, tier = 0;
    protected double transferLimit = 0;

    public ItemThaumiumDrill() {
        super(ThaumcraftMaterials.TOOLMAT_THAUMIUM, Strings.Items.THAUMIUM_DRILL_NAME);
        this.maxCharge = 100000;
        this.cost = 250;
        this.tier = 2;
        this.transferLimit = 100;
        this.efficiency = 20F;
        this.setCreativeTab(ElectroMagicTools.EMTtab);
    }

    public ItemThaumiumDrill(ToolMaterial material, String name) {
        super(material, name);
        this.maxCharge = 0;
        this.cost = 0;
        this.tier = 0;
        this.transferLimit = 0;
        this.efficiency = 0;
        this.setCreativeTab(ElectroMagicTools.EMTtab);
    }

    @Override
    public int getMaxDamage(ItemStack stack){
        return this.cost;
    }

    @Override
    public int getDamage(ItemStack stack){
        return this.maxCharge - (int) ElectricItem.manager.getCharge(stack);
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return ElectricItem.manager.getCharge(stack) != this.maxCharge;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack){
        return this.getDamage(stack) / this.maxCharge;
    }

    @Override
    public boolean onBlockDestroyed(ItemStack stack, World world, IBlockState block, BlockPos pos, EntityLivingBase entityLiving) {
        ElectricItem.manager.use(stack, cost, entityLiving);
        return true;
    }

    @Override
    public boolean canHarvestBlock(IBlockState block, ItemStack stack) {
        return Items.DIAMOND_PICKAXE.canHarvestBlock(block, stack) ||
                Items.DIAMOND_SHOVEL.canHarvestBlock(block, stack);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState block) {
        if (!ElectricItem.manager.canUse(stack, cost)) {
            return 1.0F;
        }
        if (Items.DIAMOND_PICKAXE.getDestroySpeed(stack, block) > 1.0F || Items.DIAMOND_SHOVEL.getDestroySpeed(stack, block) > 1.0F) {
            return efficiency;
        }else{
            return super.getDestroySpeed(stack, block);
        }
    }

    @Override
    public boolean hitEntity(ItemStack itemstack, EntityLivingBase entityliving, EntityLivingBase player){
        ElectricItem.manager.use(itemstack, ((ItemThaumiumDrill)itemstack.getItem()).cost, player);
        return true;
    }

    @Override
    public boolean isRepairable()
    {
        return false;
    }

    @Override
    public int getItemEnchantability() {
        return 8;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack){
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);

        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            double attackDamage = 1.5;
            if(ElectricItem.manager.canUse(stack, ((ItemThaumiumDrill)stack.getItem()).cost)) {
                attackDamage = 5.5;
            }
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)this.attackSpeed, 0));
        }

        return multimap;
        //return this.getItemAttributeModifiers(slot);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems (CreativeTabs tab, NonNullList<ItemStack> list){
        if (this.isInCreativeTab(tab)){
            ItemStack stack = new ItemStack(this, 1, 0);
            list.add(stack);
            list.add(getChargedItem(this, 1));
        }
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }

    @Override
    public double getMaxCharge(ItemStack stack) {
        return this.maxCharge;
    }

    @Override
    public int getTier(ItemStack stack) {
        return this.tier;
    }

    @Override
    public double getTransferLimit(ItemStack stack) {
        return this.transferLimit;
    }
}
