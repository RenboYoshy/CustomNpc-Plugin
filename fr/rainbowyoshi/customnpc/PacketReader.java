package fr.rainbowyoshi.customnpc;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import net.minecraft.server.v1_8_R3.Packet;
import io.netty.channel.Channel;
import net.minecraft.server.v1_8_R3.PacketPlayInUseEntity;
import org.bukkit.Bukkit;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Player;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class PacketReader {

    Channel channel;
    public static Map<UUID, Channel> channels = new HashMap<UUID, Channel>();

    public static Map<Integer, String> commands = new HashMap<Integer, String>();

    public static Map<String, EntityPlayer> npcs = new HashMap<String, EntityPlayer>();

    public static Map<EntityPlayer, Integer> npcx = new HashMap<EntityPlayer, Integer>();

    public static Map<EntityPlayer, Integer> npcy = new HashMap<EntityPlayer, Integer>();

    public static Map<EntityPlayer, Integer> npcz = new HashMap<EntityPlayer, Integer>();

    public static Map<EntityPlayer, String> npcworld = new HashMap<EntityPlayer, String>();

    public void inject(Player p){
        CraftPlayer craftp = (CraftPlayer) p;
        channel = craftp.getHandle().playerConnection.networkManager.channel;
        channels.put(p.getUniqueId(), channel);

        if(channel.pipeline().get("PacketInjector") != null)
            return;

        channel.pipeline().addAfter("decoder", "PacketInjector", new MessageToMessageDecoder<PacketPlayInUseEntity>() {
            @Override
            protected void decode(ChannelHandlerContext channel, PacketPlayInUseEntity packet, List<Object> arg) throws Exception {
                arg.add(packet);
                readPacket(p, packet);
            }
        });
    }

    public void uninject(Player p){
        channel = channels.get(p.getUniqueId());
        if(channel.pipeline().get("PacketInjector") != null)
            channel.pipeline().remove("PacketInjector");
    }
    public void readPacket(Player p, Packet<?> packet){

        if(packet.getClass().getSimpleName().equalsIgnoreCase("PacketPlayInUseEntity")) {
            if (getValue(packet, "action").toString().equalsIgnoreCase("ATTACK"))
                return;
            if (getValue(packet, "action").toString().equalsIgnoreCase("INTERACT_AT"))
                return;


            int id = (int) getValue(packet, "a");

            if(getValue(packet, "action").toString().equalsIgnoreCase("INTERACT")){
                for(EntityPlayer npc : NpcCreator.getNPCs()){
                    if(npc.getId() == id){
                        Bukkit.getScheduler().scheduleSyncDelayedTask(Main.getPlugin(Main.class), new Runnable() {
                            @Override
                            public void run() {
                                Bukkit.getPluginManager().callEvent(new RightClickNPC(p, npc));
                            }
                        }, 0);
                    }
                }
            }
        }
    }

    private Object getValue(Object instance, String name){
        Object result = null;

        try {

            Field field = instance.getClass().getDeclaredField(name);

            field.setAccessible(true);

            result = field.get(instance);

            field.setAccessible(false);

        } catch (Exception e){
            e.printStackTrace();
        }

        return result;
    }
}
