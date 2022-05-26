package fr.rainbowyoshi.customnpc;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.command.CommandExecutor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.UUID;

public class Main extends JavaPlugin {
    private static Main instance;

    public static Config data;

    public void onEnable(){
        data = new Config(this);
        getCommand("npc").setExecutor((CommandExecutor) new CommandNpc());
        this.getServer().getPluginManager().registerEvents(new ListenerNpc(), this);
        this.getServer().getPluginManager().registerEvents(new ClickNPC(), this);
        data.saveDefaultConfig();
        if(data.getConfig().contains("data"))
            loadNpc();
        instance = this;
        if(!Bukkit.getOnlinePlayers().isEmpty())
            for(Player p : Bukkit.getOnlinePlayers()){
                PacketReader reader = new PacketReader();
                reader.inject(p);
                for(EntityPlayer npc : NpcCreator.getNPCs())
                    NpcCreator.removeNPC(p, npc);
            }
    }

    public void onDisable() {
        for (Player p : Bukkit.getOnlinePlayers()) {
            PacketReader reader = new PacketReader();
            reader.uninject(p);
        }
    }
    public static Main getInstance() {
        return instance;
    }

    public static FileConfiguration getData() {
        return data.getConfig();
    }

    public static void saveData(){
        data.saveConfig();
    }

    public void loadNpc(){
        FileConfiguration file = data.getConfig();
        data.getConfig().getConfigurationSection("data").getKeys(false).forEach(npc -> {
            Location loc = new Location(Bukkit.getWorld(file.getString("data." + npc + ".world")),
                    file.getDouble("data." + npc + ".x"), file.getDouble("data." + npc + ".y"), file.getDouble("data." + npc + ".z"));
            loc.setPitch((float) file.getDouble("data." + npc + ".pitch"));
            loc.setYaw((float) file.getDouble("data." + npc + ".yaw"));
            String name = file.getString("data." + npc + ".name");
            GameProfile gameProfile = new GameProfile(UUID.randomUUID(), name);
            gameProfile.getProperties().put("textures", new Property("textures", file.getString("data." + npc + ".texture"), file.getString("data." + npc + ".signature")));
            String command = file.getString("data." + npc + ".command");
            String name2 = file.getString("data." + npc);
            NpcCreator.loadNPC(loc, gameProfile, command, name2);
        });
    }
}


