package me.aberdeener.ezdev.core;

import me.aberdeener.ezdev.models.Action;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class PermissionAction extends Action {

    protected PermissionAction() {
        super(CoreAddon.getInstance(), "permission", 1);
    }

    @Override
    public boolean handle(CommandSender sender, String[] tokens, File scriptFile, int line) {
        String permission = tokens[1];
        if (sender instanceof Player) {
            if (!sender.hasPermission(permission)) {
                sender.sendMessage(ChatColor.RED + "I'm sorry but you do not have permission to perform this command, Please contact the server administrations if you believe that this is a mistake.");
                return false;
            } else return true;
        } else return true;
    }
}
