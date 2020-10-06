package me.aberdeener.ezdev;

import lombok.Getter;
import lombok.SneakyThrows;
import me.aberdeener.ezdev.arguments.*;
import me.aberdeener.ezdev.core.CoreAddon;
import me.aberdeener.ezdev.managers.ListenerManager;
import me.aberdeener.ezdev.managers.VariableManager;
import me.aberdeener.ezdev.models.Script;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public final class ezDev extends JavaPlugin {

    @Getter
    private static ezDev instance;
    @Getter
    private static final Set<Script> scripts = new HashSet<>();

    @SneakyThrows
    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();

        instance = this;

        getLogger().info("Loading addons...");
        new CoreAddon();
        File[] addonFiles = new File(getDataFolder(), "addons").listFiles();
        if (addonFiles == null) {
            ezDev.getInstance().getLogger().warning("Could not find addon directory! Attempting to create...");
            new File(getDataFolder(), "addons").mkdir();
            return;
        }
        for (File addonFile : addonFiles) {
            if (addonFile.getName().endsWith(".jar")) {
                new AddonLoader(addonFile);
            }
        }

        getLogger().info("Loading arguments...");
        new IntegerArgument();
        new PlayerArgument();
        new MaterialArgument();
        new StringArgument();
        new WordArgument();

        getLogger().info("Loading scripts...");
        File[] scriptFiles = new File(getDataFolder(), "scripts").listFiles();
        if (scriptFiles == null) {
            ezDev.getInstance().getLogger().warning("Could not find script directory! Attempting to create...");
            new File(getDataFolder(), "scripts").mkdir();
            return;
        }
        for (File scriptFile : scriptFiles) {
            if (scriptFile.getName().endsWith(".ez")) {
                new Script(scriptFile);
            }
        }

        getLogger().info("Loading custom variables...");
        VariableManager.init();

        getCommand("ezDev").setExecutor(new ezDevCommand());
        //getServer().getPluginManager().registerEvents(new ListenerManager(), this);

        getLogger().info("Started in " + (System.currentTimeMillis() - startTime) + "ms!");
    }
}
