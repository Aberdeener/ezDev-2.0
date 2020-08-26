package me.aberdeener.ezdev.core;

import lombok.Getter;
import me.aberdeener.ezdev.managers.ListenerManager;
import me.aberdeener.ezdev.models.Addon;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class CoreAddon extends Addon {

    @Getter
    private static CoreAddon instance;

    public CoreAddon() {
        super("Core");
        // Create your Addon's instance
        instance = this;
        // Initiate your Addon's actions + events
        new TellAction();
        new GiveAction();
        // TODO: See ListenerManager
        ListenerManager.addEvent("join", PlayerJoinEvent.class, this);
        ListenerManager.addEvent("quit", PlayerQuitEvent.class, this);
    }
}
