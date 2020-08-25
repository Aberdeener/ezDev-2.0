package me.aberdeener.ezdev.models;

import me.aberdeener.ezdev.ezDev;
import me.aberdeener.ezdev.managers.AddonManager;

public abstract class Addon {

    public abstract String getName();

    public Addon() {
        AddonManager.addAddon(this);
        ezDev.getInstance().getLogger().info("Loading Addon - " + getName());
    }
}
