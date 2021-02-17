package me.aberdeener.ezdev.arguments;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;

public class MaterialArgument extends Argument<Material> {

    public MaterialArgument() {
        super("material");
    }

    @Override
    public boolean validate(CommandSender sender, String[] tokens, int i) {
        if (Material.getMaterial(tokens[i]) == null) {
            sender.sendMessage(ChatColor.RED + "Invalid material.");
            return false;
        }
        return true;
    }

    @Override
    public Material parse(String[] tokens, int i) {
        return Material.getMaterial(tokens[i].toUpperCase());
    }
}
