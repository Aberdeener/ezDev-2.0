package me.aberdeener.ezdev.models;

import lombok.Getter;
import lombok.SneakyThrows;
import me.aberdeener.ezdev.arguments.Argument;
import me.aberdeener.ezdev.managers.ActionManager;
import me.aberdeener.ezdev.managers.ArgumentManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

public class Command extends org.bukkit.command.Command {

    @Getter
    private final String label;
    @Getter
    private final Script script;
    @Getter
    private final Executor executor;
    @Getter
    private final LinkedHashMap<String, Argument> arguments;

    public Command(String label, Script script, Executor executor, LinkedHashMap<String, Argument> arguments) {
        super(label);
        this.label = label;
        this.script = script;
        this.executor = executor;
        this.arguments = arguments;
    }

    @SneakyThrows
    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (getExecutor() != Executor.BOTH) {
            if (sender instanceof Player && getExecutor() != Executor.PLAYER) {
                sender.sendMessage(ChatColor.RED + "Only console can execute this command.");
                return true;
            } else if (getExecutor() != Executor.CONSOLE) {
                sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
                return true;
            }
        }
        // If there are arguments required, make sure they match what was provided
        List<Object> newTokens = new ArrayList<>();
        if (getArguments().size() > 0) {
            if (!ArgumentManager.matchArguments(sender, getArguments(), args)) return true;
            else {
                int i = 0;
                for (Map.Entry<String, Argument> argument : getArguments().entrySet()) {
                    newTokens.add(argument.getValue().parse(args, i));
                    System.out.println("Adding " + argument.getValue().parse(args, i));
                    i++;
                }
            }
        }
        int scriptLine = getScript().getCommandLines().get(this);
        for (Map.Entry<Integer, String> line : getScript().getLines().entrySet()) {
            if (line.getKey() > scriptLine) {
                if (line.getValue().equals("end")) break;
                String[] tokens = line.getValue().split(" ");
                Action action = ActionManager.findAction(tokens);
                if (action == null) {
                    throw new ezDevException("Invalid action. Action: " + tokens[0], getScript().getFile(), line.getKey());
                } else if (!action.handle(sender, newTokens, getArguments(), tokens.length, getScript().getFile(), line.getKey())) break;
            }
        }
        return true;
    }

    public enum Executor {
        PLAYER, CONSOLE, BOTH
    }
}
