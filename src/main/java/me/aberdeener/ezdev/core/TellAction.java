package me.aberdeener.ezdev.core;

import lombok.SneakyThrows;
import me.aberdeener.ezdev.Utils;
import me.aberdeener.ezdev.models.Action;
import me.aberdeener.ezdev.models.ezDevException;
import org.bukkit.command.CommandSender;

import java.io.File;

public class TellAction extends Action {

    protected TellAction() {
        super(CoreAddon.getInstance(), "tell", -1);
    }

    @SneakyThrows
    @Override
    public boolean handle(CommandSender sender, String[] tokens, File scriptFile, int line) {
        String message = Utils.getMessage(tokens, scriptFile, line);
        String target = tokens[1];
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
