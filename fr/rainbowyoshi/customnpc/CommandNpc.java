package fr.rainbowyoshi.customnpc;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class CommandNpc implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (sender instanceof Player) {
            Player p = (Player) sender;
            if (cmd.getName().equals("npc")) {
                if (p.hasPermission("customnpc.create")) {
                    if (args.length == 0) {
                        p.sendMessage(ChatColor.GREEN + "CustomNPC" + ChatColor.GRAY + " ►" + ChatColor.BLUE + " Aide à la création de npc:");
                        p.sendMessage(ChatColor.GRAY + "-" + ChatColor.LIGHT_PURPLE + " /npc create <nom> <skin> <commande>");
                        p.sendMessage(ChatColor.LIGHT_PURPLE + "(le skin et la commande sont facultatifs)");
                    } else if (args.length == 1) {
                        p.sendMessage(ChatColor.GREEN + "CustomNPC" + ChatColor.GRAY + " ►" + ChatColor.BLUE + " Commande Incorrecte");
                    } else if (args.length == 2) {
                        if (args[0].equalsIgnoreCase("create")) {
                            NpcCreator.CreateNPC(p, args);
                            p.sendMessage(ChatColor.GREEN + "CustomNPC" + ChatColor.GRAY + " ►" + ChatColor.BLUE + " Le NPC " + args[1] + " a été crée");
                        } else {
                            p.sendMessage(ChatColor.GREEN + "CustomNPC" + ChatColor.GRAY + " ►" + ChatColor.BLUE + " Commande Incorrecte");
                        }
                    } else if (args.length == 3) {
                        if (args[0].equalsIgnoreCase("create")) {
                            NpcCreator.CreateNPC(p, args);
                            p.sendMessage(ChatColor.GREEN + "CustomNPC" + ChatColor.GRAY + " ►" + ChatColor.BLUE + " Le NPC " + args[1] + " a été crée");
                        } else {
                            p.sendMessage(ChatColor.GREEN + "CustomNPC" + ChatColor.GRAY + " ►" + ChatColor.BLUE + " Commande Incorrecte");
                        }
                    } else if (args.length > 3) {
                        if (args[0].equalsIgnoreCase("create")) {
                            NpcCreator.CreateNPC(p, args);
                            p.sendMessage(ChatColor.GREEN + "CustomNPC" + ChatColor.GRAY + " ►" + ChatColor.BLUE + " Le NPC " + args[1] + " a été crée");
                        } else {
                            p.sendMessage(ChatColor.GREEN + "CustomNPC" + ChatColor.GRAY + " ►" + ChatColor.BLUE + " Commande Incorrecte");
                        }
                    }
                }
            }
        }
        return false;
    }
}
