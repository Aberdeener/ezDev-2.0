package me.aberdeener.ezdev.managers;

import lombok.Getter;
import me.aberdeener.ezdev.models.Addon;

import java.util.HashSet;
import java.util.Set;

public class AddonManager {

    @Getter
    private static AddonManager instance;
    @Getter
    private final Set<Addon> addons = new HashSet<>();

    public AddonManager() {
        instance = this;
    }

    public void addAddon(Addon addon) {
        this.addons.add(addon);
    }
}
