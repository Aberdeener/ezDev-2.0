package me.aberdeener.ezdev;

import lombok.Getter;
import me.aberdeener.ezdev.models.Command;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;

import java.lang.reflect.Field;
import java.util.HashSet;
import java.util.Set;

public class CommandManager implements Listener {

    @Getter
    private static final Set<Command> commands = new HashSet<>();

    public static void registerCommand(Command command) {
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register("ezDev", command);

            getCommands().add(command);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            ezDev.getInstance().getLogger().severe("Could not register command " + command.getLabel() + " in script: " + command.getScript().getFile().getName());
        }
    }
}
