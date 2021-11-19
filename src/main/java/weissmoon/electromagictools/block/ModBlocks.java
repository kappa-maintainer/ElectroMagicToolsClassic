package weissmoon.electromagictools.block;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import weissmoon.electromagictools.block.solar.SolarBlockBase;
import weissmoon.electromagictools.block.tile.EssentiaGeneratorBase;
import weissmoon.electromagictools.block.tile.SolarTileEntity;
import weissmoon.electromagictools.block.tile.TileIndustrialChargePedestal;
import weissmoon.electromagictools.item.block.ItemBlockEssentia;
import weissmoon.electromagictools.item.block.ItemBlockIndustrialVisChargingStation;
import weissmoon.electromagictools.item.block.ItemBlockSolar;
import weissmoon.electromagictools.item.block.ItemObsidianTile;
import weissmoon.electromagictools.lib.Reference;

/**
 * Created by Weissmoon on 8/19/20.
 */
public class ModBlocks {

    public static final Block solarGenerator = new SolarBlockBase(false);
    public static final Block solarGeneratorCompressed = new SolarBlockBase(true);
    public static final Block essentiaGenerator = new EssentiaGeneratorBlock();
    public static final Block industrialVisCharger = new IndustrialChargePedestal();

    public static final Block obsidianTile = new BlockObsidian();

    public static void init(){
        MinecraftForge.EVENT_BUS.register(new ModBlocks());
    }

    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> itemRegistryEvent){
        IForgeRegistry<Block> blockRegistry = itemRegistryEvent.getRegistry();

        blockRegistry.register(solarGenerator);
        blockRegistry.register(solarGeneratorCompressed);
        blockRegistry.register(essentiaGenerator);
        blockRegistry.register(industrialVisCharger);
        blockRegistry.register(obsidianTile);
        GameRegistry.registerTileEntity(SolarTileEntity.class, new ResourceLocation(Reference.MOD_ID, "solarpanel"));
        GameRegistry.registerTileEntity(EssentiaGeneratorBase.class, new ResourceLocation(Reference.MOD_ID, "essentiagen"));
        GameRegistry.registerTileEntity(TileIndustrialChargePedestal.class, new ResourceLocation(Reference.MOD_ID, "chargepedestal"));
    }
    @SubscribeEvent
    public void registerItemss(RegistryEvent.Register<Item> itemRegistryEvent){
        IForgeRegistry<Item> blockRegistry = itemRegistryEvent.getRegistry();

        blockRegistry.register(new ItemBlockSolar(false));
        blockRegistry.register(new ItemBlockSolar(true));
        blockRegistry.register(new ItemBlockEssentia());
        blockRegistry.register(new ItemBlockIndustrialVisChargingStation());

        blockRegistry.register(new ItemObsidianTile());
    }
}
