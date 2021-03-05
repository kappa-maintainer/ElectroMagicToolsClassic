package weissmoon.electromagictools.item.tool;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.World;
import weissmoon.core.client.render.IIconRegister;
import weissmoon.core.item.tools.WeissItemSword;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Reference;
import weissmoon.electromagictools.lib.Strings;
import weissmoon.electromagictools.util.GenericHelper;

import javax.annotation.Nullable;
import java.util.List;

/**
 * Created by Weissmoon on 2/15/20.
 * Thor Hammer
 */
public class ItemMjölnir extends WeissItemSword {
    public ItemMjölnir() {
        super(ToolMaterial.DIAMOND, Strings.Items.MJÖLNIR_NAME);
        setMaxDamage(2000);
        setNoRepair();
        setCreativeTab(ElectroMagicTools.EMTtab);
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand){
        ItemStack itemstack = player.getHeldItem(hand);

        RayTraceResult result = GenericHelper.getEntityLookVector(player, false, 500);
        if(result == null)
            return ActionResult.newResult(EnumActionResult.PASS, itemstack);

        EntityLightningBolt lightning = null;
        if(result.typeOfHit == RayTraceResult.Type.BLOCK)
            lightning = new EntityLightningBolt(world, result.getBlockPos().getX(), result.getBlockPos().getY(), result.getBlockPos().getZ(), false);
        else if(result.typeOfHit == RayTraceResult.Type.ENTITY)
            lightning = new EntityLightningBolt(world, result.entityHit.posX, result.entityHit.posY, result.entityHit.posZ, false);

        if(lightning == null)
            return ActionResult.newResult(EnumActionResult.PASS, itemstack);
        else
            world.spawnEntity(lightning);
            world.spawnEntity(new EntityLightningBolt(world, player.posX - 0.5, player.posY + player.height, player.posZ - 0.5, true));
            player.swingArm(hand);
//            Cremation.lightning.put(lightning, player);
//            Cremation.queueTick();


        if(!player.isCreative())
            itemstack.damageItem(20, player);

        return ActionResult.newResult(EnumActionResult.SUCCESS, itemstack);
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        tooltip.add(new TextComponentTranslation("tooltip.EMT.hammer.broken.Mjölnir").getUnformattedComponentText());
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        Multimap<String, AttributeModifier> multimap = HashMultimap.<String, AttributeModifier>create();

        if (slot == EntityEquipmentSlot.MAINHAND)
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", 2, 0));

        return multimap;
    }
}
