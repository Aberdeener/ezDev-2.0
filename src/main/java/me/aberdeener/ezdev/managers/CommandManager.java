package me.aberdeener.ezdev.managers;

import lombok.Getter;
import me.aberdeener.ezdev.ezDev;
import me.aberdeener.ezdev.models.Command;
import me.aberdeener.ezdev.models.Script;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class CommandManager implements Listener {

    @Getter
    private static final Set<Command> commands = new HashSet<>();
    @Getter
    private static final List<String> commandLabels = new ArrayList<>();

    public static boolean registerCommand(Command command) {
        if (getCommandLabels().contains(command.getLabel())) return false;
        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register("ezDev", command);

            getCommands().add(command);
            getCommandLabels().add(command.getLabel());
            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            ezDev.getInstance().getLogger().severe("Could not register command " + command.getLabel() + " in script: " + command.getScript().getFile().getName());
            return false;
        }
    }

    public static Script getCommandScript(String label) {
        if (getCommandLabels().contains(label)) {
            for (Command command : getCommands()) {
                if (command.getLabel().equals(label)) return command.getScript();
            }
        }
        return null;
    }
}
