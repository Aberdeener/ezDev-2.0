package me.aberdeener.ezdevdemo;

import me.aberdeener.ezdev.models.Action;
import org.bukkit.command.CommandSender;

import java.io.File;

public class TeleportAction extends Action {
    
    protected TeleportAction() {
        super(DemoAddon.getInstance(), "teleport", 7);
    }

    public void handle(CommandSender commandSender, String[] strings, File file, int i) {
        
    }
}
