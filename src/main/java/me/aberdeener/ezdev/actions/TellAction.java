package me.aberdeener.ezdev.actions;

import lombok.SneakyThrows;
import me.aberdeener.ezdev.addons.CoreAddon;
import me.aberdeener.ezdev.managers.VariableManager;
import me.aberdeener.ezdev.models.Action;
import me.aberdeener.ezdev.models.ezDevException;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class TellAction extends Action {

    public TellAction() {
        super(CoreAddon.getInstance(), "tell", -1);
    }

    @SneakyThrows
    @Override
    public void handle(CommandSender sender, String[] tokens, File scriptFile, int line) {
        StringBuilder sb = new StringBuilder();
        boolean lastVariable = false;
        for (int i = 2; i < tokens.length; i++) {
            if (i == 2 && !tokens[2].startsWith("\"")) {
                throw new ezDevException("Message strings must start with \".", scriptFile, line);
            }
            if (VariableManager.isVariable(tokens[i])) {
                sb.append(VariableManager.get(tokens[i])).append(" ");
                lastVariable = true;
            } else {
                sb.append(tokens[i]).append(" ");
                lastVariable = false;
            }
        }
        String message = sb.toString().trim();
        if (!message.endsWith("\"") && !lastVariable) {
            throw new ezDevException("Message strings must end with \". Message: " + message, scriptFile, line);
        }
        message = message.substring(1, message.length() - (message.endsWith("\"") ? 2 : 0));
        String target = tokens[1];
        switch (target) {
            case "sender": {
                sender.sendMessage(message);
                break;
            }
            case "all": {
                for (Player player : Bukkit.getOnlinePlayers()) player.sendMessage(message);
                break;
            }
            default: throw new ezDevException("Invalid target. Target: " + target, scriptFile, line);
        }
    }
}
