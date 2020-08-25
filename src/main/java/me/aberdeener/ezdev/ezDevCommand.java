package me.aberdeener.ezdev;

import me.aberdeener.ezdev.managers.AddonManager;
import me.aberdeener.ezdev.managers.CommandManager;
import me.aberdeener.ezdev.managers.ListenerManager;
import me.aberdeener.ezdev.managers.VariableManager;
import me.aberdeener.ezdev.models.Addon;
import me.aberdeener.ezdev.models.Command;
import me.aberdeener.ezdev.models.Listener;
import me.aberdeener.ezdev.models.Script;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ezDevCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, org.bukkit.command.Command bukkitCommand, String label, String[] args) {
        if (bukkitCommand.getLabel().equalsIgnoreCase("ezDev")) {
            if (!sender.hasPermission("ezdev.admin")) {
                sender.sendMessage("no");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage(ChatColor.DARK_GREEN + "--== [ezDev] ==--");
                sender.sendMessage(ChatColor.YELLOW + "Active Addons: (" + ChatColor.DARK_GREEN + AddonManager.getAddons().size() + ChatColor.YELLOW + ")");
                for (Addon addon : AddonManager.getAddons()) {
                    sender.sendMessage(ChatColor.GOLD + addon.getName());
                }
                sender.sendMessage(ChatColor.YELLOW + "Active Scripts: (" + ChatColor.DARK_GREEN + ezDev.getScripts().size() + ChatColor.YELLOW + ")");
                for (Script script : ezDev.getScripts()) {
                    sender.sendMessage(ChatColor.GOLD + script.getFile().toString());
                }
                sender.sendMessage(ChatColor.YELLOW + "Commands: (" + ChatColor.DARK_GREEN + CommandManager.getCommands().size() + ChatColor.YELLOW + ")");
                for (Command command : CommandManager.getCommands()) {
                    sender.sendMessage(ChatColor.GOLD + "/" + command.getLabel() + ChatColor.YELLOW + " - " + ChatColor.GOLD + command.getScript().getFile());
                }
                sender.sendMessage(ChatColor.YELLOW + "Listeners: (" + ChatColor.DARK_GREEN + ListenerManager.getListeners().size() + ChatColor.YELLOW + ")");
                for (Listener listener : ListenerManager.getListeners()) {
                    sender.sendMessage(ChatColor.GOLD + listener.getEvent().getName() + ChatColor.YELLOW + " - " + ChatColor.GOLD + listener.getScript().getFile());
                }
            }
            else if (args.length == 1 && args[0].equals("reload")) {
                // Reload variables
                sender.sendMessage(ChatColor.YELLOW + "Reloading variables...");
                VariableManager.init();
                sender.sendMessage(ChatColor.GREEN + "Complete!");
            }
        }
        return true;
    }
}
