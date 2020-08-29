package me.aberdeener.ezdev.core;

import me.aberdeener.ezdev.ezDev;
import me.aberdeener.ezdev.models.Action;
import me.aberdeener.ezdev.models.ezDevException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Collections;

public class PermissionAction extends Action {

    protected PermissionAction() throws ezDevException {
        super(CoreAddon.getInstance(), "permission", Collections.singletonList(1));
    }

    @Override
    public boolean handle(CommandSender sender, String[] tokens, int length, File scriptFile, int line) {
        String permission = tokens[1];
        if (sender instanceof Player) {
            if (!sender.hasPermission(permission)) {
                sender.sendMessage(ezDev.getInstance().getCommand("ezDev").getPermissionMessage());
                return false;
            } else return true;
        } else return true;
    }
}
