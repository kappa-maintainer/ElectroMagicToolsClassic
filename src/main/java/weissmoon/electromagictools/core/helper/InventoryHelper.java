package weissmoon.electromagictools.core.helper;

import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.*;

/**
 * Utility class used to manipulate player inventory
 */
public class InventoryHelper{

    /**
     * @param player whose inventory is to be manipulated
     * @param i slot to reduce itemstack size
     * @param consumeInCreative consume item in creative
     * @return removal performed successfully
     */
    public static boolean consumeItemInSlot (EntityPlayer player, int i, boolean consumeInCreative){
        if(player.capabilities.isCreativeMode) {
            if (consumeInCreative) {
                if (player.inventory.mainInventory.get(i) != ItemStack.EMPTY && player.inventory.mainInventory.get(i).getCount() > 0) {
                    player.inventory.mainInventory.get(i).grow(-1);
                    if (player.inventory.mainInventory.get(i).getCount() <= 0) {
                        player.inventory.mainInventory.set(i, ItemStack.EMPTY);
                    }
                    player.inventoryContainer.detectAndSendChanges();
                }
            }
            return true;
        }else{
            if (player.inventory.mainInventory.get(i) != ItemStack.EMPTY && player.inventory.mainInventory.get(i).getCount() > 0) {
                player.inventory.mainInventory.get(i).grow(-1);
                if (player.inventory.mainInventory.get(i).getCount() <= 0) {
                    player.inventory.mainInventory.set(i, ItemStack.EMPTY);
                }
                player.inventoryContainer.detectAndSendChanges();
                return true;
            }
        }
        return false;
    }

    /**
     * @param player to be searched
     * @param item to search for
     * @return array of slots containing item
     */
    public static int[] getSlotsContainingItem (EntityPlayer player, Item item){
        int[] array = new int[0];
            for (int j = 0; j < player.inventory.mainInventory.size(); j++){
                if ((player.inventory.mainInventory.get(j) != ItemStack.EMPTY) && (player.inventory.mainInventory.get(j).getItem() == item)){
                    array = new int[array.length + 1];
                    array[(array.length - 1)] = j;
                }
            }
        return array;
    }

    /**
     * @param itemStack to give to player
     * @param player to give itemstack to
     */
    public static void givePlayerOrDropItemStack(ItemStack itemStack, EntityPlayer player){
        if (!player.inventory.addItemStackToInventory(itemStack)){
            //player.dropPlayerItemWithRandomChoice(itemStack, false);
            player.world.spawnEntity(new EntityItem(player.world, player.posX, player.posY, player.posZ, itemStack));
        }
    }

    /**
     * @param player to be searched
     * @param item to search for
     * @return does player have item
     */
    public static boolean hasItem(EntityPlayer player, Item item){
        for (int j = 0; j < player.inventory.mainInventory.size(); j++){
            if ((player.inventory.mainInventory.get(j) != ItemStack.EMPTY) && (player.inventory.mainInventory.get(j).getItem() == item)){
                return true;
            }
        }
        return false;
    }

    /**
     * @param player to check for fullness
     * @return player inventory full
     */
    public static boolean isPlayerInventoryFull(EntityPlayer player){
        for (int j = 0; j < player.inventory.mainInventory.size(); j++){
            if ((player.inventory.mainInventory.get(j) == ItemStack.EMPTY)){
                return false;
            }
        }
        return true;
    }
}
