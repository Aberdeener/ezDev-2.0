package me.aberdeener.ezdev.models;

import lombok.Getter;
import me.aberdeener.ezdev.ezDev;
import me.aberdeener.ezdev.managers.AddonManager;

public class Addon {

    @Getter
    private final String name;

    protected Addon(String name) {
        this.name = name;
        AddonManager.addAddon(this);
        ezDev.getInstance().getLogger().info("Loaded Addon - " + getName());
    }
}
