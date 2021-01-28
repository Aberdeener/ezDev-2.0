package me.aberdeener.ezdev.core;

import me.aberdeener.ezdev.arguments.Argument;
import me.aberdeener.ezdev.models.Action;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class PermissionAction extends Action {

    protected PermissionAction(CoreAddon addon) {
        super(addon, "permission", Collections.singletonList(1));
    }

    @Override
    public boolean handle(CommandSender sender, List<Object> tokens, LinkedHashMap<String, Argument<?>> arguments, int length, File scriptFile, int line) {
        String permission = (String) tokens.get(1);
        return sender.hasPermission(permission);
    }

}
