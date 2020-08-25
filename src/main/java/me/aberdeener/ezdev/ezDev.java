package me.aberdeener.ezdev;

import lombok.Getter;
import me.aberdeener.ezdev.core.CoreAddon;
import me.aberdeener.ezdev.core.TellAction;
import me.aberdeener.ezdev.managers.ListenerManager;
import me.aberdeener.ezdev.managers.VariableManager;
import me.aberdeener.ezdev.models.Action;
import me.aberdeener.ezdev.models.Addon;
import me.aberdeener.ezdev.models.Script;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.*;

public final class ezDev extends JavaPlugin {

    @Getter
    private static ezDev instance;
    @Getter
    private static final Set<Script> scripts = new HashSet<>();
    @Getter
    private static final List<String> actions = Collections.singletonList("tell");
    @Getter
    private static final List<String> targets = Arrays.asList("all", "sender");

    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();

        instance = this;

        getLogger().info("Loading addons...");
        new CoreAddon();
        // Todo, load addons from jar files
//        File[] addonFiles = new File(getDataFolder() + File.separator + "addons").listFiles();
//        if (addonFiles == null) {
//            ezDev.getInstance().getLogger().warning("Could not find addon directory! Attempting to create...");
//            new File(getDataFolder() + File.separator + "addons").mkdir();
//            return;
//        }
//        for (File addonFile : addonFiles) {
//        }

        getLogger().info("Loading scripts...");
        File[] scriptFiles = new File(getDataFolder() + File.separator + "scripts").listFiles();
        if (scriptFiles == null) {
            ezDev.getInstance().getLogger().warning("Could not find script directory! Attempting to create...");
            new File(getDataFolder() + File.separator + "scripts").mkdir();
            return;
        }
        for (File scriptFile : scriptFiles) {
            if (scriptFile.isFile() && scriptFile.getName().endsWith(".ez")) {
                Script script = new Script(scriptFile);
            }
        }

        getLogger().info("Loading custom variables...");
        VariableManager.init();

        getCommand("ezDev").setExecutor(new ezDevCommand());
        getServer().getPluginManager().registerEvents(new ListenerManager(), this);

        getLogger().info("Started in " + (System.currentTimeMillis() - startTime) + "ms!");
    }
}
