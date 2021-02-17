package me.aberdeener.ezdev.arguments;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class PlayerArgument extends Argument<Player> {

    public PlayerArgument() {
        super("player");
    }

    @Override
    public boolean validate(CommandSender sender, String[] tokens, int i) {
        if (Bukkit.getPlayer(tokens[i]) == null) {
            sender.sendMessage(ChatColor.RED + "That player is offline.");
            return false;
        }
        return true;
    }

    @Override
    public Player parse(String[] tokens, int i) {
        return Bukkit.getPlayer(tokens[i]);
    }
}
