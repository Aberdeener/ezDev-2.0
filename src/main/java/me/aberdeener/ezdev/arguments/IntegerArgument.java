package me.aberdeener.ezdev.arguments;

import me.aberdeener.ezdev.models.ezDevException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class IntegerArgument extends Argument {

    public IntegerArgument() throws ezDevException {
        super("int");
    }

    @Override
    public boolean validate(CommandSender sender, String[] tokens, int i) {
        try {
            Integer.parseInt(tokens[i]);
        } catch (NumberFormatException ignored) {
            sender.sendMessage(ChatColor.RED + "Invalid integer.");
            return false;
        }
        return true;
    }

    @Override
    public Integer parse(String[] tokens, int i) {
        return Integer.parseInt(tokens[i]);
    }

}
