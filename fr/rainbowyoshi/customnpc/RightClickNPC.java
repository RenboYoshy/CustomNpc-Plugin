package fr.rainbowyoshi.customnpc;

import net.minecraft.server.v1_8_R3.EntityPlayer;
import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class RightClickNPC extends Event implements Cancellable {

    private final Player p;
    private final EntityPlayer npc;
    private boolean isCancelled;
    private static final HandlerList Handlers = new HandlerList();

    public RightClickNPC(Player p, EntityPlayer npc) {
        this.p = p;
        this.npc = npc;
    }

    public Player getPlayer() {
        return p;
    }

    public EntityPlayer getNpc(){
        return npc;
    }

    @Override
    public HandlerList getHandlers() {
        return Handlers;
    }

    public static HandlerList getHandlerList() {
        return Handlers;
    }

    @Override
    public boolean isCancelled() {
        return false;
    }

    @Override
    public void setCancelled(boolean arg) {
        isCancelled = arg;
    }
}
