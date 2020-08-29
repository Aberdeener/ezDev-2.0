package me.aberdeener.ezdevdemo;

import me.aberdeener.ezdev.managers.ListenerManager;
import me.aberdeener.ezdev.models.Addon;
import me.aberdeener.ezdev.models.ezDevException;
import org.bukkit.event.player.PlayerTeleportEvent;

public class DemoAddon extends Addon {

    private static DemoAddon instance;

    public static DemoAddon getInstance() {
        return instance;
    }

    public DemoAddon() throws ezDevException {
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
