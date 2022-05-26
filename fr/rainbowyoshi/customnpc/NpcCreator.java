package fr.rainbowyoshi.customnpc;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.CraftServer;
import org.bukkit.craftbukkit.v1_8_R3.CraftWorld;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;

public class NpcCreator {



    Main plugin;
    public NpcCreator(Main instance) {
        plugin = instance;
    }

    private static List<EntityPlayer> NPC = new ArrayList<EntityPlayer>();
    private static List<String> NPCname = new ArrayList<String>();

    public static void CreateNPC(Player p, String[] args){
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) Objects.requireNonNull(p.getWorld())).getHandle();
        GameProfile gameProfile = new GameProfile(UUID.randomUUID(), args[1]);
        EntityPlayer npc = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));
        npc.setLocation(p.getLocation().getX(), p.getLocation().getY(), p.getLocation().getZ(), p.getLocation().getYaw(), p.getLocation().getPitch());
        if(args.length == 2) {
            String[] name = getSkin(p, args[1]);

            gameProfile.getProperties().put("textures", new Property("textures", name[0], name[1]));
            addNPCPacket(npc);
            NPC.add(npc);

            int var = 1;
            if (Main.getData().contains("data"))
                var = Main.getData().getConfigurationSection("data").getKeys(false).size() + 1;
                NPCname.add(String.valueOf(var));
                PacketReader.npcs.put(String.valueOf(var), npc);

            Main.getData().set("data." + var + ".x", p.getLocation().getX());
            Main.getData().set("data." + var + ".y", p.getLocation().getY());
            Main.getData().set("data." + var + ".z", p.getLocation().getZ());
            Main.getData().set("data." + var + ".pitch", p.getLocation().getPitch());
            Main.getData().set("data." + var + ".yaw", p.getLocation().getYaw());
            Main.getData().set("data." + var + ".world", p.getLocation().getWorld().getName());
            Main.getData().set("data." + var + ".name", args[1]);
            Main.getData().set("data." + var + ".texture", name[0]);
            Main.getData().set("data." + var + ".signature", name[1]);
            Main.getData().set("data." + var + ".command", null);
            Main.getData().set("data." + var + ".look player", false);
            Main.saveData();

            PacketReader.npcworld.put(npc, p.getLocation().getWorld().getName());
            PacketReader.npcx.put(npc, (int) p.getLocation().getX());
            PacketReader.npcy.put(npc, (int) p.getLocation().getY());
            PacketReader.npcz.put(npc, (int) p.getLocation().getZ());
        } else if(args.length == 3) {
            String[] name = getSkin(p, args[2]);

            gameProfile.getProperties().put("textures", new Property("textures", name[0], name[1]));
            addNPCPacket(npc);
            NPC.add(npc);

            int var = 1;
            if (Main.getData().contains("data"))
                var = Main.getData().getConfigurationSection("data").getKeys(false).size() + 1;
                NPCname.add(String.valueOf(var));
                PacketReader.npcs.put(String.valueOf(var), npc);
            Main.getData().set("data." + var + ".x", p.getLocation().getX());
            Main.getData().set("data." + var + ".y", p.getLocation().getY());
            Main.getData().set("data." + var + ".z", p.getLocation().getZ());
            Main.getData().set("data." + var + ".pitch", p.getLocation().getPitch());
            Main.getData().set("data." + var + ".yaw", p.getLocation().getYaw());
            Main.getData().set("data." + var + ".world", p.getLocation().getWorld().getName());
            Main.getData().set("data." + var + ".name", args[1]);
            Main.getData().set("data." + var + ".texture", name[0]);
            Main.getData().set("data." + var + ".signature", name[1]);
            Main.getData().set("data." + var + ".command", null);
            Main.getData().set("data." + var + ".look player", false);
            Main.saveData();

            PacketReader.npcworld.put(npc, p.getLocation().getWorld().getName());
            PacketReader.npcx.put(npc, (int) p.getLocation().getX());
            PacketReader.npcy.put(npc, (int) p.getLocation().getY());
            PacketReader.npcz.put(npc, (int) p.getLocation().getZ());
        } else if(args.length > 3) {
            String[] name = getSkin(p, args[2]);

            gameProfile.getProperties().put("textures", new Property("textures", name[0], name[1]));
            addNPCPacket(npc);
            NPC.add(npc);

            int var = 1;
            if (Main.getData().contains("data"))
                var = Main.getData().getConfigurationSection("data").getKeys(false).size() + 1;
                NPCname.add(String.valueOf(var));
                PacketReader.npcs.put(String.valueOf(var), npc);
            Main.getData().set("data." + var + ".x", p.getLocation().getX());
            Main.getData().set("data." + var + ".y", p.getLocation().getY());
            Main.getData().set("data." + var + ".z", p.getLocation().getZ());
            Main.getData().set("data." + var + ".pitch", p.getLocation().getPitch());
            Main.getData().set("data." + var + ".yaw", p.getLocation().getYaw());
            Main.getData().set("data." + var + ".world", p.getLocation().getWorld().getName());
            Main.getData().set("data." + var + ".name", args[1]);
            Main.getData().set("data." + var + ".texture", name[0]);
            Main.getData().set("data." + var + ".signature", name[1]);
            StringBuilder sb = new StringBuilder();
            for (int i = 3; i < args.length; i++){
                sb.append(args[i]).append(" ");
            }
            String allArgs = sb.toString().trim();
            Main.getData().set("data." + var + ".command", allArgs);
            PacketReader.commands.put(npc.getId(), allArgs);
            Main.getData().set("data." + var + ".look player", false);
            Main.saveData();

            PacketReader.npcworld.put(npc, p.getLocation().getWorld().getName());
            PacketReader.npcx.put(npc, (int) p.getLocation().getX());
            PacketReader.npcy.put(npc, (int) p.getLocation().getY());
            PacketReader.npcz.put(npc, (int) p.getLocation().getZ());
        }
    }

    public static void loadNPC(Location loc, GameProfile profile, String command, String name2){
        MinecraftServer server = ((CraftServer) Bukkit.getServer()).getServer();
        WorldServer world = ((CraftWorld) loc.getWorld()).getHandle();
        GameProfile gameProfile = profile;
        EntityPlayer npc = new EntityPlayer(server, world, gameProfile, new PlayerInteractManager(world));
        npc.setLocation(loc.getX(), loc.getY(), loc.getZ(), loc.getYaw(), loc.getPitch());
        PacketReader.commands.put(npc.getId(), command);
        addNPCPacket(npc);
        NPC.add(npc);
        NPCname.add(name2);
        PacketReader.npcs.put(name2, npc);

        PacketReader.npcworld.put(npc, world.getWorld().getName());
        PacketReader.npcx.put(npc, (int) loc.getX());
        PacketReader.npcy.put(npc, (int) loc.getY());
        PacketReader.npcz.put(npc, (int) loc.getZ());
    }

    private static String[] getSkin(Player p, String name){
        try {
            URL url = new URL("https://api.mojang.com/users/profiles/minecraft/" + name);
            InputStreamReader reader = new InputStreamReader(url.openStream());
            String uuid = new JsonParser().parse(reader).getAsJsonObject().get("id").getAsString();
            URL url2 = new URL("https://sessionserver.mojang.com/session/minecraft/profile/" + uuid + "?unsigned=false");
            InputStreamReader reader2 = new InputStreamReader(url2.openStream());
            JsonObject property = new JsonParser().parse(reader2).getAsJsonObject().get("properties").getAsJsonArray().get(0).getAsJsonObject();
            String texture = property.get("value").getAsString();
            String signature = property.get("signature").getAsString();
            return new String[] {texture, signature};
        } catch (Exception e) {
            EntityPlayer player = ((CraftPlayer) p).getHandle();
            GameProfile profile = player.getProfile();
            Property property = profile.getProperties().get("textures").iterator().next();
            String texture = property.getValue();
            String signature = property.getSignature();
            return new String[] {texture, signature};
        }
    }

    public static void addNPCPacket(EntityPlayer npc){
        for(Player p : Bukkit.getOnlinePlayers()){
            PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
            DataWatcher watcher = npc.getDataWatcher();
            watcher.watch(10, (byte) 127);
            connection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), watcher, true));
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutEntityMetadata());
            connection.sendPacket(new PacketPlayOutKeepAlive());
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc,(byte) (npc.yaw * 256 / 360)));
            new BukkitRunnable() {
                @Override
                public void run() {
                    connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
                }
            }.runTaskLater(Main.getInstance(),50);
        }
    }

    public  static void removeNPC(Player p, EntityPlayer npc){
        PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
        connection.sendPacket(new PacketPlayOutEntityDestroy(npc.getId()));
    }
    public static void addJoinPacket(Player p){
        for(EntityPlayer npc : NPC){
            PlayerConnection connection = ((CraftPlayer)p).getHandle().playerConnection;
            DataWatcher watcher = npc.getDataWatcher();
            watcher.watch(10, (byte) 127);
            connection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), watcher, true));
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutKeepAlive());
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc,(byte) (npc.yaw * 256 / 360)));
            new BukkitRunnable() {
                @Override
                public void run() {
                    connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
                }
            }.runTaskLater(Main.getInstance(),50);
        }
    }

    public static List<EntityPlayer> getNPCs(){
        return NPC;
    }

    public static List<String> getNPCsname(){
        return NPCname;
    }
}
