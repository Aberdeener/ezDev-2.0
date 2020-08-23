package me.aberdeener.ezdev;

import lombok.Getter;
import me.aberdeener.ezdev.models.Script;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.util.Set;

public final class ezDev extends JavaPlugin implements CommandExecutor {

    @Getter
    private static ezDev instance;
    @Getter
    private static Set<Script> scripts;

    @Override
    public void onEnable() {
        instance = this;
        long startTime = System.currentTimeMillis();

        getLogger().info("Loading scripts...");
        initScripts();

        getLogger().info("Loading custom variables...");
        VariableManager.init();

        getLogger().info("Started in " + (System.currentTimeMillis() - startTime) + "ms!");
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getLabel().equalsIgnoreCase("ezDev")) {
            if (!sender.hasPermission("ezdev.admin")) {
                sender.sendMessage("no");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage(ChatColor.DARK_GREEN + "--== [ezDev] ==--");
                sender.sendMessage(ChatColor.YELLOW + "Active Scripts: (" + ChatColor.DARK_GREEN + getScripts().size() + ChatColor.YELLOW + ")");
                for (Script script : getScripts()) {
                    sender.sendMessage(ChatColor.GOLD + script.getName() + ChatColor.YELLOW + " - " + ChatColor.GOLD + script.getFile());
                }
            }
            if (args.length == 1 && args[0].equals("reload")) {
                // Reload scripts
                sender.sendMessage(ChatColor.YELLOW + "Reloading scripts...");
                // TODO
                // Reload variables as well
                sender.sendMessage(ChatColor.YELLOW + "Reloading variables...");
                VariableManager.init();
                sender.sendMessage(ChatColor.GREEN + "Complete!");
            }
        }
        return true;
    }

    private void initScripts() {
        File[] scriptFiles = new File(getDataFolder() + File.separator + "scripts").listFiles();
        if (scriptFiles == null) {
            ezDev.getInstance().getLogger().severe("Could not find script directory!");
            getPluginLoader().disablePlugin(this);
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
