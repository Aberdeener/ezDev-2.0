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
import java.util.Collections;

public class GiveAction extends Action {

    protected GiveAction() throws ezDevException {
        super(CoreAddon.getInstance(), "give", Collections.singletonList(3));
    }

    @SneakyThrows
    @Override
    public boolean handle(CommandSender sender, String[] tokens, int length, File scriptFile, int line) {
        String target = tokens[1];
        String item = tokens[2];
        String quantity = tokens[3];
        ItemStack itemStack = new ItemStack(Material.getMaterial(item), Integer.parseInt(quantity));
        switch (target) {
            case "player":
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
        return true;
    }
}
