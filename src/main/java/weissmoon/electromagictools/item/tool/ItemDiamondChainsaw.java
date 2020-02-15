package weissmoon.electromagictools.item.tool;

import com.google.common.collect.Multimap;
import ic2.api.item.ElectricItem;
import ic2.api.item.IElectricItem;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import thaumcraft.api.ThaumcraftMaterials;
import weissmoon.core.api.item.IModesCore;
import weissmoon.core.item.tools.WeissItemAxe;
import weissmoon.core.utils.NBTHelper;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Strings;

import java.util.List;
import java.util.Random;

import static weissmoon.electromagictools.util.ItemHelper.getChargedItem;

/**
 * Created by Weissmoon on 9/22/19.
 */
public class ItemDiamondChainsaw extends WeissItemAxe implements IElectricItem, IModesCore {
    
    protected int maxCharge, cost, hitCost, tier;
    protected double transferLimit;
    private static final String SHEARMODE_NBT_TAG= "shearsMode";

    public ItemDiamondChainsaw() {
        super(ThaumcraftMaterials.TOOLMAT_THAUMIUM, 10, -3.2F, Strings.Items.DIAMOND_CHAINSAW_NAME);
        this.maxCharge = 100000;
        this.cost = 250;
        this.hitCost = 300;
        this.tier = 2;
        this.transferLimit = 100;
        this.efficiency = 16F;
        this.setCreativeTab(ElectroMagicTools.EMTtab);
    }

    public ItemDiamondChainsaw(ToolMaterial material, float damage, float speed, String name) {
        super(material, damage, speed, name);
        this.maxCharge = 0;
        this.cost = 0;
        this.hitCost = 0;
        this.tier = 0;
        this.transferLimit = 0;
        this.efficiency = 0;
        this.setCreativeTab(ElectroMagicTools.EMTtab);
    }

    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn){
        NBTHelper.setBoolean(stack, SHEARMODE_NBT_TAG, true);
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
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        ElectricItem.manager.use(stack, cost, entityLiving);
        return true;
    }

    @Override
    public boolean canHarvestBlock(IBlockState state, ItemStack stack) {
        return Items.DIAMOND_AXE.canHarvestBlock(state, stack) ||
                Items.DIAMOND_AXE.canHarvestBlock(state, stack) ||
                Items.SHEARS.canHarvestBlock(state, stack);
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState block) {
        if (!ElectricItem.manager.canUse(stack, cost)) {
            return 1.0F;
        }

        if (Items.DIAMOND_AXE.getDestroySpeed(stack, block) > 1.0F || Items.DIAMOND_SWORD.getDestroySpeed(stack, block) > 1.0F) {
            return efficiency;
        }else{
            return super.getDestroySpeed(stack, block);
        }
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker){
        ElectricItem.manager.use(stack, ((ItemThaumiumDrill)stack.getItem()).cost, attacker);
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn){
        //Tool mode handled by changeToolMode
        return super.onItemRightClick(world, player, handIn);
    }

    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player){
        if(NBTHelper.getBoolean(itemstack, SHEARMODE_NBT_TAG) || player.world.isRemote)
            return false;

        IBlockState block = player.world.getBlockState(pos);
        if (block instanceof IShearable) {
            IShearable target = (IShearable) block;
            if (target.isShearable(itemstack, player.world, pos)) {
                List<ItemStack> drops = target.onSheared(itemstack, player.world, pos, EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("fortune"), itemstack));
                Random rand = new Random();

                for (ItemStack stack : drops) {
                    float f = 0.7F;
                    double xOffset = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                    double yOffset = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                    double zOffset = (double) (rand.nextFloat() * f) + (double) (1.0F - f) * 0.5D;
                    EntityItem entityitem = new EntityItem(player.world, (double) pos.getX() + xOffset, (double) pos.getY() + yOffset, (double) pos.getZ() + zOffset, stack);
                    entityitem.setDefaultPickupDelay();
                    player.world.spawnEntity(entityitem);
                }
                player.addStat(StatList.getBlockStats(block.getBlock()));
            }
        }
        return false;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand){
        if(!NBTHelper.getBoolean(stack, SHEARMODE_NBT_TAG) || player.world.isRemote){
            return false;
        }

        if (target instanceof IShearable) {
            BlockPos tatgetPos = new BlockPos((int) target.posX, (int) target.posY, (int) target.posZ);
            if ((((IShearable)target).isShearable(stack, target.world, tatgetPos))) {
                List<ItemStack> drops = ((IShearable)target).onSheared(stack, target.world, tatgetPos, EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("fortune"), stack));

                Random rand = new Random();
                for (ItemStack istack : drops) {
                    EntityItem ent = target.entityDropItem(istack, 1.0F);
                    ent.motionY += rand.nextFloat() * 0.05F;
                    ent.motionX += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                    ent.motionZ += (rand.nextFloat() - rand.nextFloat()) * 0.1F;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public boolean isRepairable() {
        return false;
    }

    @Override
    public int getItemEnchantability() {
        return 4;
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack){
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);

        if (slot == EntityEquipmentSlot.MAINHAND)
        {
            double attackDamage = 1.5;
            if(ElectricItem.manager.canUse(stack, ((ItemDiamondChainsaw)stack.getItem()).hitCost)) {
                attackDamage = this.attackDamage;
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

    @Override
    public void changeToolMode(ItemStack stack){
        NBTHelper.toggleBoolean(stack, SHEARMODE_NBT_TAG);
    }
}
