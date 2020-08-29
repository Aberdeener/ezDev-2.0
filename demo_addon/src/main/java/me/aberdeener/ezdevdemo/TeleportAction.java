package me.aberdeener.ezdevdemo;

import me.aberdeener.ezdev.models.Action;
import org.bukkit.command.CommandSender;

import java.io.File;

public class TeleportAction extends Action {
    
    protected TeleportAction() {
        super(
                // Link to the instance of the Addon you made
                DemoAddon.getInstance(),
                // Define the phrase for the Action. This is what they will need to type in their Script to call this Action
                "teleport",
                // The length of the arguments provided after the phrase
                5
        );
    }

    public boolean handle(CommandSender commandSender, String[] strings, File file, int i) {
        // Do things when "teleport" is called in a Script
        return true;
    }
}
