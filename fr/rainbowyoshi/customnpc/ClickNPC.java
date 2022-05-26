package fr.rainbowyoshi.customnpc;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class ClickNPC implements Listener {
    @EventHandler
    public void onClick(RightClickNPC e){
        Player p = e.getPlayer();
        EntityPlayer npc = e.getNpc();
        if(PacketReader.commands.get(npc.getId()) != null){
            if(PacketReader.commands.get(npc.getId()).startsWith("/")) {
                p.chat(PacketReader.commands.get(npc.getId()));
            } else p.sendMessage(ChatColor.GREEN + "CustomNPC" + ChatColor.GRAY + " ►" + ChatColor.BLUE + " Commande Incorrecte");
        }
    }
}
