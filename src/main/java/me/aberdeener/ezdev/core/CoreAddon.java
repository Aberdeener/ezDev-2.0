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
        instance = this;
        new TellAction();
        new GiveAction();
        ListenerManager.addEvent("join", PlayerJoinEvent.class, this);
        ListenerManager.addEvent("quit", PlayerQuitEvent.class, this);
    }
}
