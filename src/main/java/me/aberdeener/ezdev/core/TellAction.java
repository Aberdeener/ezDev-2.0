package me.aberdeener.ezdev.core;

import lombok.SneakyThrows;
import me.aberdeener.ezdev.Utils;
import me.aberdeener.ezdev.models.Action;
import me.aberdeener.ezdev.models.ezDevException;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.Collections;

public class TellAction extends Action {

    protected TellAction() throws ezDevException {
        super(CoreAddon.getInstance(), "tell", Collections.singletonList(-1));
    }

    @SneakyThrows
    @Override
    public boolean handle(CommandSender sender, String[] tokens, int length, File scriptFile, int line) {
        String message = Utils.getMessage(tokens, scriptFile, line);
        String target = tokens[1];
        switch (target) {
            case "player":
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
