package me.aberdeener.ezdev.arguments;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class IntegerArgument extends Argument<Integer> {

    public IntegerArgument() {
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
