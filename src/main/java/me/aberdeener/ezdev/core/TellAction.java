package me.aberdeener.ezdev.core;

import lombok.SneakyThrows;
import me.aberdeener.ezdev.Utils;
import me.aberdeener.ezdev.arguments.Argument;
import me.aberdeener.ezdev.models.Action;
import me.aberdeener.ezdev.models.ezDevException;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class TellAction extends Action {

    protected TellAction(CoreAddon addon) {
        super(addon, "tell", Collections.singletonList(-1));
    }

    @SneakyThrows
    @Override
    public boolean handle(CommandSender sender, List<Object> tokens, LinkedHashMap<String, Argument<?>> arguments, int length, File scriptFile, int line) {
        for (Object e : tokens) {
            System.out.println(e);
        }
        String message = Utils.getMessage(tokens, arguments, scriptFile, line);
        String target = (String) tokens.get(1);
        switch (target) {
            case "sender": {
                Utils.sendMessage(sender, message);
                break;
            }
            case "all": {
                Utils.broadcastMessage(message);
                break;
            }
            default: throw new ezDevException("Invalid target. Target: " + target, scriptFile, line);
        }

        return true;
    }

}
