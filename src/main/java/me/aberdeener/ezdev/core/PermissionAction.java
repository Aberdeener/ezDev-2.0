package me.aberdeener.ezdev.core;

import me.aberdeener.ezdev.models.Action;
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
            return sender.hasPermission(permission);
        } else return true;
    }
}
