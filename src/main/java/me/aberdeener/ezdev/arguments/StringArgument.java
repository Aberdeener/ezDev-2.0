package me.aberdeener.ezdev.arguments;

import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

public class StringArgument extends Argument<String> {

    public StringArgument() {
        super("string");
    }

    @Override
    public boolean validate(CommandSender sender, String[] tokens, int i) {
        for (String token : tokens) {
            if (token == null) {
                sender.sendMessage(ChatColor.RED + "Invalid string.");
                return false;
            }
        }

        return true;
    }

    @Override
    public String parse(String[] tokens, int i) {
        StringBuilder sb = new StringBuilder();
        for (int x = i; x <= tokens.length; x++) {
            sb.append(tokens[i]).append(" ");
        }

        return sb.toString().trim();
    }

}
