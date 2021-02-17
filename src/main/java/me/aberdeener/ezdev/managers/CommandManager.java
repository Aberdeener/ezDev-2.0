package me.aberdeener.ezdev.managers;

import lombok.Getter;
import me.aberdeener.ezdev.ezDev;
import me.aberdeener.ezdev.models.Command;
import me.aberdeener.ezdev.models.Script;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandMap;
import org.bukkit.event.Listener;

import java.lang.reflect.Field;
import java.util.*;

public class CommandManager implements Listener {

    @Getter
    private static CommandManager instance;
    private final ezDev plugin;
    @Getter
    private final Map<String, Command> commandMap = new HashMap<>();

    public CommandManager(ezDev plugin) {
        instance = this;
        this.plugin = plugin;
    }

    public boolean registerCommand(Command command) {
        if (this.commandMap.containsKey(command.getLabel()))  {
            ezDev.getInstance().getLogger().warning("Command /" + command.getLabel() + " has previously been registered by an ezDev script (" + CommandManager.getInstance().getCommandScript(command.getLabel()).getFile() + ")");
            return false;
        }

        try {
            final Field bukkitCommandMap = Bukkit.getServer().getClass().getDeclaredField("commandMap");

            bukkitCommandMap.setAccessible(true);
            CommandMap commandMap = (CommandMap) bukkitCommandMap.get(Bukkit.getServer());

            commandMap.register(command.getScript().getName(), command);

            this.commandMap.put(command.getLabel(), command);
            return true;
        } catch (NoSuchFieldException | IllegalAccessException e) {
            e.printStackTrace();
            this.plugin.getLogger().severe("Could not register command " + command.getLabel() + " in script: " + command.getScript().getFile().getName());
            return false;
        }
    }

    public Script getCommandScript(String label) {
        if (this.commandMap.containsKey(label)) {
            return this.commandMap.get(label).getScript();
        }

        return null;
    }

}
