package me.aberdeener.ezdev;

import lombok.SneakyThrows;
import me.aberdeener.ezdev.arguments.Argument;
import me.aberdeener.ezdev.managers.VariableManager;
import me.aberdeener.ezdev.models.ezDevException;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

public class Utils {

    /**
     * Create a formatted message with variables replaced and quotes removed
     * @param tokens String list of tokens to be included in the final message
     * @param scriptFile (for ezDevException purposes) File this was called from
     * @param line (for ezDevException purposes) Line number which this was called from
     * @return Formatted message with variables replaced and quotes removed
     */
    @SneakyThrows
    public static String getMessage(List<Object> tokens, LinkedHashMap<String, Argument> arguments, File scriptFile, int line) {
        StringBuilder sb = new StringBuilder();
        boolean lastVariable = false;
        for (int i = 2; i < tokens.size(); i++) {
            if (i == 2 && !((String) tokens.get(2)).startsWith("\"")) {
                throw new ezDevException("Message strings must start with \".", scriptFile, line);
            }
            if (VariableManager.isVariable((String) tokens.get(i))) {
                sb.append(VariableManager.get((String) tokens.get(i))).append(" ");
                lastVariable = true;
            } else {
                sb.append(tokens.add(i)).append(" ");
                lastVariable = false;
            }
        }
        String message = sb.toString().trim();
        if (!message.endsWith("\"") && !lastVariable) {
            throw new ezDevException("Message strings must end with \". Message: " + message, scriptFile, line);
        }
        return message.substring(1, message.length() - (message.endsWith("\"") ? 1 : 0));
    }

    /**
     * Send a message to a specific CommandSender
     * @param sender person to receive the message.
     * @param message formatted message to be sent (see getMessage())
     */
    public static void sendMessage(CommandSender sender, String message) {
        sender.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }

    /**
     * Send a message to all online players
     * @param message formatted message to be sent (see getMessage())
     */
    public static void broadcastMessage(String message) {
        for (Player player : Bukkit.getOnlinePlayers()) player.sendMessage(ChatColor.translateAlternateColorCodes('&', message));
    }
}
