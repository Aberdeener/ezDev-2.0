package me.aberdeener.ezdev;

import lombok.Getter;
import me.aberdeener.ezdev.models.Script;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Set;

public final class ezDev extends JavaPlugin {

    @Getter
    private static ezDev instance;
    @Getter
    private static Set<Script> scripts;

    @Override
    public void onEnable() {
        instance = this;
        long startTime = System.currentTimeMillis();

        getLogger().info("Loading scripts...");
        File[] scriptFiles = new File(getDataFolder() + File.separator + "scripts").listFiles();
        if (scriptFiles == null) {
            ezDev.getInstance().getLogger().severe("Could not find script directory!");
            return;
        }
        for (File scriptFile : scriptFiles) {
            if (scriptFile.isFile() && scriptFile.getName().endsWith(".ez")) {
                Script script = new Script(scriptFile);
                scripts.add(script);
            }
        }

        getLogger().info("Loading custom variables...");
        VariableManager.init();

        getLogger().info("Started in " + (System.currentTimeMillis() - startTime) + "ms!");
    }
}
