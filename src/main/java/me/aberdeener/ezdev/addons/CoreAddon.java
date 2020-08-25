package me.aberdeener.ezdev.addons;

import lombok.Getter;
import me.aberdeener.ezdev.actions.TellAction;
import me.aberdeener.ezdev.models.Addon;

public class CoreAddon extends Addon {

    @Getter
    private final String name = "Core";

    @Getter
    private static CoreAddon instance;

    public CoreAddon() {
        super();
        // Create your Addon's instance
        instance = this;
        // Initiate your Addon's actions
        new TellAction();
    }
}
