package weissmoon.electromagictools.item.tool;

import com.google.common.collect.Multimap;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.IShearable;
import weissmoon.core.item.tools.WeissItemsPickaxe;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Strings;

/**
 * Created by Weissmoon on 9/23/19.
 */
public class ItemIronOmniTool extends WeissItemsPickaxe implements IElectricItem {

    protected int maxCharge, cost, hitCost, tier;
    protected double transferLimit;
    private static final String SHEARMODE_NBT_TAG= "shearsMode";

    public ItemIronOmniTool() {
        super(ToolMaterial.IRON, Strings.Items.IRON_OMNITOOL_NAME);
        this.maxCharge = 50000;
        this.cost = 100;
        this.hitCost = 125;
        this.tier = 2;
        this.transferLimit = 200;
        this.efficiency = 13;
        this.attackDamage = 7;
        this.setCreativeTab(ElectroMagicTools.EMTtab);
    }

    public ItemIronOmniTool(ToolMaterial material, String name) {
        super(material, name);
        this.maxCharge = 0;
        this.cost = 0;
        this.hitCost = 0;
        this.tier = 0;
        this.transferLimit = 0;
        this.efficiency = 0;
        this.attackDamage = 0;
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
    public boolean canHarvestBlock(IBlockState block, ItemStack stack) {
        return Items.IRON_AXE.canHarvestBlock(block) ||
                Items.IRON_SHOVEL.canHarvestBlock(block) ||
                Items.IRON_PICKAXE.canHarvestBlock(block) ||
                Items.IRON_SWORD.canHarvestBlock(block) ||
                block.getBlock() instanceof IShearable;
    }

    public float getDestroySpeed(ItemStack stack, IBlockState block) {
        if (!ElectricItem.manager.canUse(stack, cost)) {
            return 1.0F;
        }

        if (Items.IRON_AXE.getDestroySpeed(stack, block) > 1.0F ||
                Items.IRON_SHOVEL.getDestroySpeed(stack, block) > 1.0F ||
                Items.IRON_PICKAXE.getDestroySpeed(stack, block) > 1.0F ||
                Items.IRON_SWORD.getDestroySpeed(stack, block) > 1.0F ||
                Items.SHEARS.getDestroySpeed(stack, block) > 1.0F){
            return efficiency;
        }else{
            return super.getDestroySpeed(stack, block);
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker){
        ElectricItem.manager.use(stack, ((ItemIronOmniTool)stack.getItem()).cost, attacker);
        return true;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack){
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);

        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            double attackDamage = 1.5;
            if(ElectricItem.manager.canUse(stack, ((ItemIronOmniTool)stack.getItem()).hitCost)) {
                attackDamage = this.attackDamage;
            }
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", attackDamage, 0));
            multimap.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", (double)this.attackSpeed, 0));
        }

        return multimap;
        //return this.getItemAttributeModifiers(slot);
    }

    @Override
    public int getItemEnchantability() {
        return 2;
    }

    @Override
    public boolean isRepairable() {
        return false;
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
