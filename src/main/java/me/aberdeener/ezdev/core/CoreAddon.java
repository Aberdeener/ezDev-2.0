package me.aberdeener.ezdev.core;

import lombok.Getter;
import me.aberdeener.ezdev.models.Addon;

public class CoreAddon extends Addon {

    @Getter
    private static CoreAddon instance;

    public CoreAddon() {
        super("Core");
        // Create your Addon's instance
        instance = this;
        // Initiate your Addon's actions
        new TellAction();
    }
}
