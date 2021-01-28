package me.aberdeener.ezdev;

import me.aberdeener.ezdev.managers.*;
import me.aberdeener.ezdev.models.*;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.event.player.PlayerEvent;

import java.util.Map;

public class ezDevCommand implements CommandExecutor {

    private final ezDev plugin;

    public ezDevCommand(ezDev plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command bukkitCommand, String label, String[] args) {
        if (bukkitCommand.getLabel().equalsIgnoreCase("ezDev")) {
            if (!sender.hasPermission("ezdev.admin")) {
                sender.sendMessage(ChatColor.RED + "You do not have permission to execute this command.");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage(ChatColor.DARK_GREEN + "--== [ezDev] ==--");
                sender.sendMessage(ChatColor.YELLOW + "Active Addons: (" + ChatColor.DARK_GREEN + AddonManager.getInstance().getAddons().size() + ChatColor.YELLOW + ")");
                for (Addon addon : AddonManager.getInstance().getAddons()) {
                    sender.sendMessage(ChatColor.GOLD + addon.getName());
                }
                sender.sendMessage(ChatColor.YELLOW + "Actions: (" + ChatColor.DARK_GREEN + ActionManager.getInstance().getActions().size() + ChatColor.YELLOW + ")");
                for (Action action : ActionManager.getInstance().getActions()) {
                    sender.sendMessage(ChatColor.GOLD + action.getPhrase() + ChatColor.YELLOW + " - Addon: " + ChatColor.GOLD + action.getAddon().getName());
                }
                sender.sendMessage(ChatColor.YELLOW + "Events: (" + ChatColor.DARK_GREEN + ListenerManager.getListeners().size() + ChatColor.YELLOW + ")");
                for (Map.Entry<String, Class<? extends PlayerEvent>> event : ListenerManager.getEvents().entrySet()) {
                    // TODO: event.getAddon()
                    sender.sendMessage(ChatColor.GOLD + event.getKey() + ChatColor.YELLOW + " - " + ChatColor.GOLD + event.getValue().getName());
                }
                sender.sendMessage(ChatColor.YELLOW + "Scripts: (" + ChatColor.DARK_GREEN + this.plugin.getScripts().size() + ChatColor.YELLOW + ")");
                for (Script script : this.plugin.getScripts()) {
                    sender.sendMessage(ChatColor.GOLD + script.getFile().toString());
                }
                sender.sendMessage(ChatColor.YELLOW + "Commands: (" + ChatColor.DARK_GREEN + CommandManager.getInstance().getCommandMap().size() + ChatColor.YELLOW + ")");
                for (Map.Entry<String, Command> commandEntry : CommandManager.getInstance().getCommandMap().entrySet()) {
                    sender.sendMessage(ChatColor.GOLD + "/" + commandEntry.getValue().getLabel() + ChatColor.YELLOW + " - Script: " + ChatColor.GOLD + commandEntry.getValue().getScript().getFile());
                }
            }
            else if (args.length == 1 && args[0].equals("reload")) {
                // Reload variables
                sender.sendMessage(ChatColor.YELLOW + "Reloading variables...");
                this.plugin.initVariableManager();
                sender.sendMessage(ChatColor.GREEN + "Complete!");
            }
        }
        return true;
    }
}
