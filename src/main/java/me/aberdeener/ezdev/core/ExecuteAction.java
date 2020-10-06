package me.aberdeener.ezdev.core;

import me.aberdeener.ezdev.arguments.Argument;
import me.aberdeener.ezdev.models.Action;
import me.aberdeener.ezdev.models.ezDevException;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class ExecuteAction extends Action {

    protected ExecuteAction() throws ezDevException {
        super(CoreAddon.getInstance(), "execute", Collections.singletonList(2));
    }

    @Override
    public boolean handle(CommandSender sender, List<Object> tokens, LinkedHashMap<String, Argument> arguments, int length, File scriptFile, int line) {
        Player target = (Player) tokens.get(0);
        System.out.println(target.getUniqueId().toString());
        String command = (String) tokens.get(1);
        target.performCommand(command);
        return true;
    }
}
