package fr.rainbowyoshi.customnpc;

import net.minecraft.server.v1_8_R3.*;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.craftbukkit.v1_8_R3.entity.CraftPlayer;
import org.bukkit.entity.Entity;
import org.bukkit.entity.NPC;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerMoveEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import org.bukkit.event.world.ChunkLoadEvent;
import org.bukkit.event.world.ChunkUnloadEvent;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.HashMap;
import java.util.UUID;

public class ListenerNpc implements Listener {
    @EventHandler
    public void onJoin(PlayerJoinEvent e) {
        Player p = e.getPlayer();
        PacketReader reader = new PacketReader();
        reader.inject(p);
        if (NpcCreator.getNPCs() == null) return;

        if (NpcCreator.getNPCs().isEmpty())
            return;
        NpcCreator.addJoinPacket(p);
    }

    @EventHandler
    public void onLeave(PlayerQuitEvent e) {
        Player p = e.getPlayer();
        PacketReader reader = new PacketReader();
        reader.uninject(p);
    }

    public static void addNPCPacket(EntityPlayer npc) {
        for (Player p : Bukkit.getOnlinePlayers()) {
            PlayerConnection connection = ((CraftPlayer) p).getHandle().playerConnection;
            DataWatcher watcher = npc.getDataWatcher();
            watcher.watch(10, (byte) 127);
            connection.sendPacket(new PacketPlayOutEntityMetadata(npc.getId(), watcher, true));
            connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.ADD_PLAYER, npc));
            connection.sendPacket(new PacketPlayOutEntityMetadata());
            connection.sendPacket(new PacketPlayOutKeepAlive());
            connection.sendPacket(new PacketPlayOutNamedEntitySpawn(npc));
            connection.sendPacket(new PacketPlayOutEntityHeadRotation(npc, (byte) (npc.yaw * 256 / 360)));
            new BukkitRunnable() {
                @Override
                public void run() {
                    connection.sendPacket(new PacketPlayOutPlayerInfo(PacketPlayOutPlayerInfo.EnumPlayerInfoAction.REMOVE_PLAYER, npc));
                }
            }.runTaskLater(Main.getInstance(), 50);
        }
    }

    @EventHandler
    public void onMove(PlayerMoveEvent e) {
        Player p = e.getPlayer();
        if (e.getFrom() != e.getTo()) {
            for (EntityPlayer npc : NpcCreator.getNPCs()) {
                if (PacketReader.npcworld.get(npc) != null) {
                    if (PacketReader.npcworld.get(npc) == p.getWorld().getName()) {
                        int playerx = (int) p.getLocation().getX();
                        int playerz = (int) p.getLocation().getZ();
                        if (PacketReader.npcx.get(npc).equals(playerx + 100)) {
                            addNPCPacket(npc);
                        } else if (PacketReader.npcx.get(npc).equals(playerx - 100)) {
                            addNPCPacket(npc);
                        } else if (PacketReader.npcz.get(npc).equals(playerz + 100)) {
                            addNPCPacket(npc);
                        } else if (PacketReader.npcz.get(npc).equals(playerz - 100)) {
                            addNPCPacket(npc);
                        }
                    }
                }
            }
        }
    }
}

