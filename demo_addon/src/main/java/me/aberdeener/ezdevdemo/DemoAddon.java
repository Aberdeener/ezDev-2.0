package me.aberdeener.ezdevdemo;

import lombok.Getter;
import me.aberdeener.ezdev.managers.ListenerManager;
import me.aberdeener.ezdev.models.Addon;
import org.bukkit.event.player.PlayerTeleportEvent;

public class DemoAddon extends Addon {

    @Getter
    private static DemoAddon instance;

    public DemoAddon() {
        super("Demo");
        instance = this;
        new TeleportAction();
        ListenerManager.addEvent("teleport", PlayerTeleportEvent.class, this);
    }

}
