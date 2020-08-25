package me.aberdeener.ezdev.managers;

import lombok.Getter;
import me.aberdeener.ezdev.models.Addon;

import java.util.HashSet;
import java.util.Set;

public class AddonManager {

    @Getter
    private static final Set<Addon> addons = new HashSet<>();

    public static void addAddon(Addon addon) {
        getAddons().add(addon);
    }
}
