package weissmoon.electromagictools.item.tool;

import ic2.api.classic.item.IDamagelessElectricItem;
import ic2.api.item.ElectricItem;
import ic2.api.util.IKeyboard;
import ic2.api.util.Keys;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import weissmoon.core.api.client.item.IItemRenderCustom;
import weissmoon.core.api.client.item.IItemRenderer;
import weissmoon.core.client.render.IIcon;
import weissmoon.core.client.render.IIconRegister;
import weissmoon.core.client.render.renderOverride.CustomRenderRegistry;
import weissmoon.core.item.tools.WeissItemSword;
import weissmoon.core.utils.NBTHelper;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Strings;

import javax.annotation.Nonnull;

import static weissmoon.electromagictools.util.ItemHelper.getChargedItem;
import static weissmoon.electromagictools.util.ItemHelper.getElectricDurability;

/**
 * Created by Weissmoon on 9/26/20.
 */
public class ItemStormBreaker extends WeissItemSword implements IDamagelessElectricItem, IItemRenderCustom {

    private final int maxCharge = 2000000;
    private int cost = 5000;
    private double summonCost = 75000;
    protected IIcon itemIconWeissE;

    public ItemStormBreaker() {
        super(ToolMaterial.DIAMOND, Strings.Items.STORMBREAKER_NAME);
        setNoRepair();
        setCreativeTab(ElectroMagicTools.EMTtab);
    }

    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker){
        if(attacker instanceof EntityPlayer) {
            if (ElectricItem.manager.use(stack, cost, attacker)) {
                target.attackEntityFrom(DamageSource.causePlayerDamage((EntityPlayer) attacker), 19.0F);
            }
        }
        return true;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand handIn){
//        if(Keys.instance.isBoostKeyDown(player)){
//            NBTHelper.toggleBoolean(player.getHeldItem(handIn), "throw");
//        }
        if(ElectricItem.manager.canUse(player.getHeldItem(handIn), summonCost)){
            world.spawnEntity(new EntityLightningBolt(world, player.posX - 0.5, player.posY + player.height, player.posZ  - 0.5, true));

            world.spawnEntity(new EntityLightningBolt(world, player.posX + 5.5, player.posY, player.posZ + 5.5, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX + 5.5, player.posY, player.posZ - 5.5, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX - 5.5, player.posY, player.posZ + 5.5, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX - 5.5, player.posY, player.posZ - 6.5, false));

            world.spawnEntity(new EntityLightningBolt(world, player.posX + 8, player.posY, player.posZ, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX - 8, player.posY, player.posZ, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX, player.posY, player.posZ + 8, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX, player.posY, player.posZ - 8, false));


            world.spawnEntity(new EntityLightningBolt(world, player.posX + 8, player.posY, player.posZ + 1, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX + 8, player.posY, player.posZ + 2, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX + 7, player.posY, player.posZ + 3, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX + 7, player.posY, player.posZ + 4, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX - 8, player.posY, player.posZ + 1, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX - 8, player.posY, player.posZ + 2, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX - 7, player.posY, player.posZ + 3, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX - 7, player.posY, player.posZ + 4, false));

            world.spawnEntity(new EntityLightningBolt(world, player.posX + 8, player.posY, player.posZ - 1, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX + 8, player.posY, player.posZ - 2, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX + 7, player.posY, player.posZ - 3, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX + 7, player.posY, player.posZ - 4, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX - 8, player.posY, player.posZ - 1, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX - 8, player.posY, player.posZ - 2, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX - 7, player.posY, player.posZ - 3, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX - 7, player.posY, player.posZ - 4, false));

            world.spawnEntity(new EntityLightningBolt(world, player.posX + 1, player.posY, player.posZ + 8, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX + 2, player.posY, player.posZ + 8, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX + 3, player.posY, player.posZ + 7, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX + 4, player.posY, player.posZ + 7, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX + 1, player.posY, player.posZ - 8, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX + 2, player.posY, player.posZ - 8, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX + 3, player.posY, player.posZ - 7, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX + 4, player.posY, player.posZ - 7, false));

            world.spawnEntity(new EntityLightningBolt(world, player.posX - 1, player.posY, player.posZ + 8, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX - 2, player.posY, player.posZ + 8, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX - 3, player.posY, player.posZ + 7, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX - 4, player.posY, player.posZ + 7, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX - 1, player.posY, player.posZ - 8, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX - 2, player.posY, player.posZ - 8, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX - 3, player.posY, player.posZ - 7, false));
            world.spawnEntity(new EntityLightningBolt(world, player.posX - 4, player.posY, player.posZ - 7, false));

            player.swingArm(handIn);

            if(!player.isCreative())
                ElectricItem.manager.use(player.getHeldItem(handIn), summonCost, player);
            return new ActionResult<>(EnumActionResult.SUCCESS, player.getHeldItem(handIn));
        }else{
            world.spawnEntity(new EntityLightningBolt(world, player.posX -0.5, player.posY, player.posZ - 0.5, false));
            player.swingArm(handIn);
            return new ActionResult<>(EnumActionResult.FAIL, player.getHeldItem(handIn));
        }
    }

    @Override
    public IIcon getIcon(ItemStack stack, int pass) {
//        if(NBTHelper.getBoolean(stack, "throw"))
//            return this.itemIconWeissE;
        return this.itemIconWeiss;
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        this.itemIconWeiss = iconRegister.registerIcon(this, getUnlocalizedName().substring(getUnlocalizedName().indexOf(".") + 1));
//        this.itemIconWeissE = iconRegister.registerIcon(this, getUnlocalizedName().substring(getUnlocalizedName().indexOf(".") + 1) +"e");
    }

    @Override
    public boolean showDurabilityBar(ItemStack stack) {
        return true;
    }

    @Override
    public double getDurabilityForDisplay(ItemStack stack){
        return getElectricDurability(stack);
    }

    @Override
    public int getItemEnchantability(ItemStack stack){
        return 4;
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> list) {
        if (isInCreativeTab(tab)){
            ItemStack stack = new ItemStack(this, 1, 0);
            list.add(stack);
            list.add(getChargedItem(this, 1));
        }
    }

    @Override
    public CreativeTabs[] getCreativeTabs(){
        return new CreativeTabs[]{ getCreativeTab(), CreativeTabs.COMBAT };
    }

    @Override
    public boolean canProvideEnergy(ItemStack stack) {
        return false;
    }

    @Override
    public double getMaxCharge(ItemStack stack) {
        return maxCharge;
    }

    @Override
    public int getTier(ItemStack stack) {
        return 3;
    }

    @Override
    public double getTransferLimit(ItemStack stack) {
        return 500;
    }

    @Override
    public IItemRenderer getIItemRender() {
        return CustomRenderRegistry.getMissingRender();
    }
}
