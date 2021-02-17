package me.aberdeener.ezdev.core;

import lombok.Getter;
import me.aberdeener.ezdev.managers.ListenerManager;
import me.aberdeener.ezdev.models.Addon;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CoreAddon extends Addon {

    public CoreAddon() {
        super("Core");

        // Actions
        new TellAction(this);
        new GiveAction(this);
        new PermissionAction(this);
        new ExecuteAction(this);

        // Events
        ListenerManager.addEvent("join", PlayerJoinEvent.class, this);
        ListenerManager.addEvent("quit", PlayerQuitEvent.class, this);
    }
}
