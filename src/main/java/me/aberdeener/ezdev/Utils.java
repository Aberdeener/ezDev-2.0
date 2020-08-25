package me.aberdeener.ezdev;

import lombok.SneakyThrows;
import me.aberdeener.ezdev.managers.VariableManager;
import me.aberdeener.ezdev.models.ezDevException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;

public class Utils {

    @SneakyThrows
    public static String getMessage(String[] tokens, File scriptFile, int line) {
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
        return message.substring(1, message.length() - (message.endsWith("\"") ? 1 : 0));
    }

    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    public static void broadcastMessage(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
