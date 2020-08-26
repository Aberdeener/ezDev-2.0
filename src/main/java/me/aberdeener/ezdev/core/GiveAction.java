package me.aberdeener.ezdev.core;

import lombok.SneakyThrows;
import me.aberdeener.ezdev.models.Action;
import me.aberdeener.ezdev.models.ezDevException;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.File;

public class GiveAction extends Action {

    protected GiveAction() {
        super(CoreAddon.getInstance(), "give", 3);
    }

    @SneakyThrows
    @Override
    public void handle(CommandSender sender, String[] tokens, File scriptFile, int line) {
        String target = tokens[1];
        String item = tokens[2];
        String quantity = tokens[3];
        ItemStack itemStack = new ItemStack(Material.getMaterial(item), Integer.parseInt(quantity));
        switch (target) {
            case "sender": {
                if (sender instanceof Player) {
                    ((Player) sender).getInventory().addItem(itemStack);
                } else {
                    throw new ezDevException("Only players can receive items.", scriptFile, line);
                }
                break;
            }
            case "all": {
                for (Player player : Bukkit.getOnlinePlayers()) player.getInventory().addItem(itemStack);
                break;
            }
            default: throw new ezDevException("Invalid target. Target: " + target, scriptFile, line);
        }
    }
}
