package me.aberdeener.ezdev;

import me.aberdeener.ezdev.models.Script;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ezDevCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (command.getLabel().equalsIgnoreCase("ezDev")) {
            if (!sender.hasPermission("ezdev.admin")) {
                sender.sendMessage("no");
                return true;
            }
            if (args.length == 0) {
                sender.sendMessage(ChatColor.DARK_GREEN + "--== [ezDev] ==--");
                sender.sendMessage(ChatColor.YELLOW + "Active Scripts: (" + ChatColor.DARK_GREEN + ezDev.getScripts().size() + ChatColor.YELLOW + ")");
                for (Script script : ezDev.getScripts()) {
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
}
