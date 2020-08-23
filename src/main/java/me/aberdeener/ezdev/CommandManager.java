package me.aberdeener.ezdev;

import lombok.Getter;
import me.aberdeener.ezdev.models.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class CommandManager implements Listener {

    @Getter
    private static final Set<Command> commands = new HashSet<>();

    public static Command getCommand(String label) {
        for (Command command : getCommands()) {
            if (command.getLabel().equalsIgnoreCase(label)) return command;
        }
        return null;
    }

    public static void registerCommand(Command command) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register("ezDev", command);

            getCommands().add(command);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            ezDev.getInstance().getPluginLoader().disablePlugin(ezDev.getInstance());
        }
    }

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        Command command = getCommand(event.getMessage().split(" ")[0]);
        if (command != null) command.execute(event.getPlayer(), event.getMessage().split(" ")[0], event.getMessage().split(" "));
    }

}
