package weissmoon.electromagictools.block;

import net.minecraft.block.material.Material;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.gameevent.PlayerEvent;
import thaumcraft.api.research.ScanningManager;
import weissmoon.core.block.WeissBlockContainer;
import weissmoon.electromagictools.block.tile.ScannerTile;
import weissmoon.electromagictools.lib.Strings;

/**
 * Created by Weissmoon on 4/24/21.
 */
public class ScannerBlock extends WeissBlockContainer{
    protected ScannerBlock(){
        super(Strings.Blocks.SCANNER_BLOCK_NAME, Material.ROCK);
//        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta){
        return new ScannerTile();
    }

    public void onCraft(PlayerEvent.ItemCraftedEvent event){
        EntityPlayer player = event.player;
        if(player != null){
            ItemStack crafting = event.crafting;
            ScanningManager.scanTheThing(player, crafting);
        }
    }
}
