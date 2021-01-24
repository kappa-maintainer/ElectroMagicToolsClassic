package weissmoon.electromagictools.item.armour.wings;

import baubles.api.BaubleType;
import baubles.api.IBauble;
import baubles.api.render.IRenderBauble;
import ic2.api.util.Keys;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import weissmoon.core.item.armour.ItemArmourBase;
import weissmoon.core.utils.NBTHelper;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.client.WingsModelRenderer;
import weissmoon.electromagictools.item.ModItems;
import weissmoon.electromagictools.lib.Strings;
import weissmoon.electromagictools.lib.Textures;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

/**
 * Created by Weissmoon on 8/17/20.
 */
public class ItemFeatherWings extends ItemArmourBase implements IBauble, IRenderBauble {

    protected float fallReduction, fallDamage, propulsion;
    protected int tier;
    protected SoundEvent sound = new SoundEvent(new ResourceLocation("welectromagic:wingflap"));
    @SideOnly(Side.CLIENT)
    protected WingsModelRenderer model;

    public ItemFeatherWings() {
        this(Strings.Items.FEATHER_WINGS_GAME, ArmorMaterial.LEATHER);
        fallReduction = 0.9F;
        fallDamage = 0.75F;
        propulsion = 0.11F;
        setMaxDamage(120);
        tier = 0;
//        MinecraftForge.EVENT_BUS.register(this);
    }

    protected ItemFeatherWings(String name, ArmorMaterial materialIn) {
        super(name, materialIn, 0, EntityEquipmentSlot.CHEST);
        setCreativeTab(ElectroMagicTools.EMTtab);
    }

    @SideOnly(Side.CLIENT)
    @Override
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack itemStack, EntityEquipmentSlot armorSlot, ModelBiped original) {
        if(model == null)
            model = new WingsModelRenderer(new ItemStack(ModItems.materials, 1, 7), 0);
        return model;
    }

    @Nullable
    @Override
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type){
        return Textures.Armour.EMPTY_TEXTURE;
    }

    @Override
    public void onArmorTick(World world, EntityPlayer player, ItemStack stack) {
        tickWings(player, stack, world);//, 0.11f, 0.9f, 0.9f, 0);
    }

    @Override
    public CreativeTabs[] getCreativeTabs(){
        return new CreativeTabs[]{ElectroMagicTools.EMTtab, CreativeTabs.COMBAT};
    }

    public void tickWings(@Nonnull EntityPlayer player, ItemStack wings, World world){

        boolean falling = !player.onGround && player.motionY < 0;
        byte flap = NBTHelper.getByte(wings, "flap");

        if(Keys.instance.isJumpKeyDown(player)){
            if(flap < 7)
                NBTHelper.setByte(wings, "flap", ++flap);
            if(falling)
                player.motionY *= fallReduction;
            player.fallDistance = 0;
        }else if(flap > 0){
            player.motionY = propulsion * flap;
            world.playSound(player, player.posX, player.posY, player.posZ, sound, SoundCategory.PLAYERS, 0.5F, 1);
            NBTHelper.setByte(wings, "flap", (byte)0);
            player.fallDistance = 0;
        }
        if(!player.isCreative()){
            if (player.isInWater())
                player.motionY -= 0.028;
            if (world.isRainingAt(player.getPosition()))
                player.motionY -= 0.035;
        }
        if(Keys.instance.isSneakKeyDown(player) && falling){
            player.motionY *= 0.6;
            player.fallDistance = 0;
        }
    }

    public final int getTier(){
        return tier;
    }

    public float getFallDamage(){
        return fallDamage;
    }

    @Override
    public BaubleType getBaubleType(ItemStack itemstack) {
        return BaubleType.BODY;
    }

    @Override
    public void onWornTick(ItemStack itemstack, EntityLivingBase player) {
        if (player instanceof EntityPlayer)
            tickWings((EntityPlayer) player, itemstack, player.world);
    }

    @Override
    public void onEquipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public void onUnequipped(ItemStack itemstack, EntityLivingBase player) {

    }

    @Override
    public boolean canUnequip(ItemStack itemstack, EntityLivingBase player) {
        return true;//todo curse binding
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void onPlayerBaubleRender(ItemStack stack, EntityPlayer player, RenderType type, float partialTicks) {
        if(type == RenderType.BODY) {
            model.renderB(stack, player, type, partialTicks);
        }
    }
}
