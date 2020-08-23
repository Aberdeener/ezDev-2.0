package me.aberdeener.ezdev;

import lombok.Getter;
import me.aberdeener.ezdev.models.Command;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.util.HashSet;
import java.util.Set;

public class CommandManager implements Listener {

    @Getter
    private static final Set<Command> commands = new HashSet<>();

    public static Command getCommand(String label) {
        for (Command command : commands) {
            if (command.getLabel().equalsIgnoreCase(label)) return command;
        }
        return null;
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Command command = getCommand(event.getMessage().split(" ")[0]);
        if (command != null) command.execute(event);
    }

}
