package me.aberdeener.ezdevdemo;

import lombok.Getter;
import me.aberdeener.ezdev.managers.ListenerManager;
import me.aberdeener.ezdev.models.Addon;
import org.bukkit.event.player.PlayerTeleportEvent;

public class DemoAddon extends Addon {

    @Getter
    private static DemoAddon instance;

    public DemoAddon() {
        // Define the name of your Addon
        super("Demo");
        // Provide the instance of your Addon for Actions to link to
        instance = this;
        // Initialize your Actions
        new TeleportAction();
        // Create your PlayerEvents - NOT CURRENTLY FULLY SUPPORTED :(
        ListenerManager.addEvent("teleport", PlayerTeleportEvent.class, this);
    }

}
