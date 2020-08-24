package me.aberdeener.ezdev;

import lombok.Getter;
import me.aberdeener.ezdev.models.Script;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public final class ezDev extends JavaPlugin {

    @Getter
    private static ezDev instance;
    @Getter
    private static final Set<Script> scripts = new HashSet<>();

    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();

        instance = this;

        getLogger().info("Loading scripts...");
        initScripts();

        getLogger().info("Loading custom variables...");
        VariableManager.init();

        getCommand("ezDev").setExecutor(new ezDevCommand());

        getLogger().info("Started in " + (System.currentTimeMillis() - startTime) + "ms!");
    }

    private void initScripts() {
        File[] scriptFiles = new File(getDataFolder() + File.separator + "scripts").listFiles();
        if (scriptFiles == null) {
            ezDev.getInstance().getLogger().warning("Could not find script directory! Attempting to create...");
            new File(getDataFolder() + File.separator + "scripts").mkdir();
            return;
        }
        for (File scriptFile : scriptFiles) {
            if (scriptFile.isFile() && scriptFile.getName().endsWith(".ez")) {
                Script script = new Script(scriptFile);
                getScripts().add(script);
            }
        }
    }
}
