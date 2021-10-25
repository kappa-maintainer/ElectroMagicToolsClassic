package weissmoon.electromagictools.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.oredict.OreDictionary;
import thaumcraft.client.fx.ParticleEngine;
import thaumcraft.client.fx.particles.FXGeneric;
import thaumcraft.common.lib.utils.Utils;
import weissmoon.electromagictools.lib.OreColourList;

import java.util.ArrayList;

/**
 * Created by Weissmoon on 2/7/21.
 */
public class OverrideScanPacket implements IMessage, IMessageHandler<OverrideScanPacket, IMessage>{

    private BlockPos pos;
    private int size;

    public OverrideScanPacket(){}
    public OverrideScanPacket(BlockPos negaposi, int size){
        this.pos = negaposi;
        this.size = size;
    }

    @Override
    public void fromBytes(ByteBuf buf){
        pos = BlockPos.fromLong(buf.readLong());
        size = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf){
        buf.writeLong(pos.toLong());
        buf.writeInt(size);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public IMessage onMessage(OverrideScanPacket message, MessageContext ctx){
        Minecraft.getMinecraft().addScheduledTask(() -> scann(message.pos, message.size));
        return null;
    }

    @SideOnly(Side.CLIENT)
    private void scann(BlockPos posi, int sizeIn){
        World world = Minecraft.getMinecraft().player.world;


        int range = 4 + sizeIn * 4;
        ArrayList<BlockPos> ores = new ArrayList();



        for(int xx = -range; xx <= range; ++xx) {
            for(int yy = -range; yy <= range; ++yy) {
                for(int zz = -range; zz <= range; ++zz) {
                    BlockPos curPos = posi.add(xx, yy, zz);
                    if (Utils.isOreBlock(world, curPos)) {
                        ores.add(curPos);
                    }
                }
            }
        }

        BlockPos origin;
        ArrayList<BlockPos> group = new ArrayList<>();

        while(!ores.isEmpty()){

            origin = ores.get(0);
            group.add(origin);
            ores.remove(0);

            cullGroup(world, origin, group, ores);
            if(!group.isEmpty()){
                generateParticle(group, origin, posi);
                group = new ArrayList<>();
            }
        }

    }

    private void cullGroup(World world, BlockPos origin, ArrayList<BlockPos> group, ArrayList<BlockPos> ores){
        IBlockState originState = world.getBlockState(origin);

        if(ores.isEmpty())
            return;

        for(int xx = -1; xx <= 1; ++xx){
            for(int yy = -1;yy<=1;++yy){
                for(int zz = -1;zz<=1;++zz){
                    BlockPos curPosi = origin.add(xx, yy, zz);
                    IBlockState curState = world.getBlockState(curPosi);
                    if(curState.equals(originState) && ores.contains(curPosi)){
                        group.add(curPosi);
                        ores.remove(curPosi);
                        cullGroup(world, curPosi, group, ores);
                    }
                }
            }
        }
    }

    @SideOnly(Side.CLIENT)
    private void generateParticle(ArrayList<BlockPos> group, BlockPos origin, BlockPos ping){
        World world = Minecraft.getMinecraft().player.world;
        double minX, minY, minZ, maxX, maxY, maxZ;
        minX = maxX = origin.getX();
        minY = maxY = origin.getY();
        minZ = maxZ = origin.getZ();
        for(BlockPos pod : group){
            if(pod.getX()<minX)
                minX = pod.getX();
            else if(pod.getX()>maxX)
                maxX = pod.getX();

            if(pod.getY()<minY)
                minY = pod.getY();
            else if(pod.getY()>maxY)
                maxY = pod.getY();

            if(pod.getZ()<minZ)
                minZ = pod.getZ();
            else if(pod.getZ()>maxZ)
                maxZ = pod.getZ();
        }

        double x, y, z;
        x = (maxX + minX) / 2 + .5;
        y = (maxY + minY) / 2 + .5;
        z = (maxZ + minZ) / 2 + .5;

        double distance = ping.getDistance((int)x, (int)y, (int)z);
        FXGeneric particle = new FXGeneric(world, x, y, z, 0.0D, 0.0D, 0.0D);
        particle.setMaxAge(44);
        int color = getColour(origin);
        float red, green, blue;
        red = (color >> 16&0xFF);
        green = (color >> 8&0xFF);
        blue = (color&0xFF);
        particle.setRBGColorF(red / 255.0F, green / 255.0F, blue / 255.0F);
        float alpha = (red / 255.0F + green / 255.0F + blue / 255.0F) / 3.0F;
        particle.setAlphaF(0.0F, 1.0F, 0.8F, 0.0F);
        particle.setParticles(240, 15, 1);
        particle.setGridSize(16);
        particle.setLoop(true);
        particle.setScale(9.0F);
        particle.setLayer(alpha < 0.25F ? 3 : 2);
        particle.setRotationSpeed(0.0F);
        ParticleEngine.addEffectWithDelay(world, particle, (int)(distance * 2));
    }

    private int getColour(BlockPos pos){
        World world = Minecraft.getMinecraft().player.world;
        IBlockState state = world.getBlockState(pos);
        Block block = state.getBlock();
        int colour = 12632256;
        if(block != Blocks.BEDROCK && block != Blocks.AIR){
            ItemStack stack = new ItemStack(block, 1, block.getMetaFromState(state));
            if(block == Blocks.LIT_REDSTONE_ORE)
                stack = new ItemStack(Blocks.REDSTONE_ORE);
            if(stack.isEmpty())
                return colour;
            int[] oreIDs = OreDictionary.getOreIDs(stack);
            if(oreIDs.length > 0){
                for(int id:oreIDs){
                    for(String name:OreColourList.getNames()){
                        if(OreDictionary.getOreName(id).toLowerCase().contains(name))
                            colour = OreColourList.getColour(name);
                        if(colour != 12632256)
                            return colour;
                    }
                }
            }
        }
        return colour;
    }
}
