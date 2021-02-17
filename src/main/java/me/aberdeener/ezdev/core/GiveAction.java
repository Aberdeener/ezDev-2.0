package me.aberdeener.ezdev.core;

import lombok.SneakyThrows;
import me.aberdeener.ezdev.arguments.Argument;
import me.aberdeener.ezdev.models.Action;
import me.aberdeener.ezdev.models.ezDevException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.List;

public class GiveAction extends Action {

    protected GiveAction(CoreAddon addon) {
        super(addon, "give", Collections.singletonList(3));
    }

    @SneakyThrows
    @Override
    public boolean handle(CommandSender sender, List<Object> tokens, LinkedHashMap<String, Argument<?>> arguments, int length, File scriptFile, int line) {
        String target = (String) tokens.get(1);
        Material item = (Material) tokens.get(2);
        int quantity = (int) tokens.get(3);
        ItemStack itemStack = new ItemStack(item, quantity);
        switch (target) {
            case "sender": {
                ((Player) sender).getInventory().addItem(itemStack);
                break;
            }
            case "all": {
                for (Player player : Bukkit.getOnlinePlayers()) player.getInventory().addItem(itemStack);
                break;
            }
            default: throw new ezDevException("Invalid target. Target: " + target, scriptFile, line);
        }

        return true;
    }

}
