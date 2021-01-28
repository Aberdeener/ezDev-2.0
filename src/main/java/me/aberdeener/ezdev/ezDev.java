package me.aberdeener.ezdev;

import lombok.Getter;
import lombok.SneakyThrows;
import me.aberdeener.ezdev.arguments.IntegerArgument;
import me.aberdeener.ezdev.arguments.MaterialArgument;
import me.aberdeener.ezdev.arguments.PlayerArgument;
import me.aberdeener.ezdev.arguments.StringArgument;
import me.aberdeener.ezdev.core.CoreAddon;
import me.aberdeener.ezdev.managers.*;
import me.aberdeener.ezdev.models.Script;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.HashSet;
import java.util.Set;

public final class ezDev extends JavaPlugin {

    @Getter
    private static ezDev instance;
    @Getter
    private static VariableManager variableManagerInstance;
    @Getter
    private final Set<Script> scripts = new HashSet<>();

    @SneakyThrows
    @Override
    public void onEnable() {
        long startTime = System.currentTimeMillis();

        instance = this;

        getLogger().info("Loading addons...");
        new AddonManager();
        new ActionManager();

        new CoreAddon();
        File[] addonFiles = new File(getDataFolder(), "addons").listFiles();
        if (addonFiles == null) {
            ezDev.getInstance().getLogger().warning("Could not find addon directory! Attempting to create...");
            if (!(new File(getDataFolder(), "addons").mkdir())) {
                getLogger().warning("Could not create addon directory! Ensure your filesystem permissions are setup properly...");
            }
        } else {
            for (File addonFile : addonFiles) {
                if (addonFile.getName().endsWith(".jar")) {
                    new AddonLoader(addonFile);
                }
            }
        }

        getLogger().info("Loading arguments...");
        new ArgumentManager();

        new IntegerArgument();
        new PlayerArgument();
        new MaterialArgument();
        new StringArgument();

        getLogger().info("Loading scripts...");
        new CommandManager(this);

        File[] scriptFiles = new File(getDataFolder(), "scripts").listFiles();
        if (scriptFiles == null) {
            ezDev.getInstance().getLogger().warning("Could not find script directory! Attempting to create...");
            if (!(new File(getDataFolder(), "scripts").mkdir())) {
                getLogger().warning("Could not create script directory! Ensure your filesystem permissions are setup properly...");
            }
        } else {
            for (File scriptFile : scriptFiles) {
                if (scriptFile.getName().endsWith(".ez")) {
                    new Script(scriptFile);
                }
            }
        }

        getLogger().info("Loading custom variables...");
        initVariableManager();

        getCommand("ezDev").setExecutor(new ezDevCommand(this));
        //getServer().getPluginManager().registerEvents(new ListenerManager(), this);

        getLogger().info("Started in " + (System.currentTimeMillis() - startTime) + "ms!");
    }

    public void initVariableManager() {
        variableManagerInstance = null;
        variableManagerInstance = new VariableManager(this);
    }

}
