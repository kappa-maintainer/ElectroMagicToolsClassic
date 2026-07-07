package weissmoon.electromagictools.core.helper;

import net.minecraft.entity.player.EntityPlayer;

/**
 * Used to manipulate player data
 * Created by Weissmoon on 3/27/17.
 */
public class PlayerHelper {

    /**
     * @param player to remove experience from
     * @param amount of experience to remove
     */
    public static void removeExperience(EntityPlayer player, int amount){
        int j = 0 + player.experienceTotal;
        if (amount > j)
        {
            amount = j;
        }

        if(player.experienceTotal == 0 && player.experience != 0)
            player.experience = 0;

            player.experience -= (float)amount / (float)player.xpBarCap();

        for (player.experienceTotal -= amount; player.experience <= 0.0F; player.experience /= (float)player.xpBarCap())
        {
            player.experience = (player.experience + 1.0F) * (float)player.xpBarCap();
            /*Same method signature was changed*/
            //player.removeExperienceLevel(1);
            player.onEnchant(null,1);
        }
    }
}
