package me.aberdeener.ezdev.core;

import lombok.Getter;
import me.aberdeener.ezdev.managers.ListenerManager;
import me.aberdeener.ezdev.models.Addon;
import me.aberdeener.ezdev.models.ezDevException;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CoreAddon extends Addon {

    @Getter
    private static CoreAddon instance;

    public CoreAddon() throws ezDevException {
        super("Core");
        instance = this;
        new TellAction();
        new GiveAction();
        new PermissionAction();
        ListenerManager.addEvent("join", PlayerJoinEvent.class, this);
        ListenerManager.addEvent("quit", PlayerQuitEvent.class, this);
    }
}
