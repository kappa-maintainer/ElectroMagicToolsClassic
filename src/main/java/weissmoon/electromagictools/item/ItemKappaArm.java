package weissmoon.electromagictools.item;

import com.google.common.collect.Multimap;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import weissmoon.core.item.WeissItem;
import weissmoon.electromagictools.ElectroMagicTools;
import weissmoon.electromagictools.lib.Strings;

import java.util.UUID;

/**
 * Created by Weissmoon on 4/16/22.
 */
public class ItemKappaArm extends WeissItem{

    protected static final UUID REACH_DISTANCE_MODIFIEER = UUID.fromString("eaf95b2b-0c8c-44be-88eb-cf116efbc264");

    public ItemKappaArm(){
        super(Strings.Items.KAPPA_ARM_NAME);
        setMaxStackSize(1);
        setCreativeTab(ElectroMagicTools.EMTtab);
    }

    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack){
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(slot);

        if (slot == EntityEquipmentSlot.OFFHAND){
            multimap.put(EntityPlayer.REACH_DISTANCE.getName(), new AttributeModifier(REACH_DISTANCE_MODIFIEER, "Player modifier", 3, 0));
        }

        return multimap;
        //return this.getItemAttributeModifiers(slot);
    }
}
