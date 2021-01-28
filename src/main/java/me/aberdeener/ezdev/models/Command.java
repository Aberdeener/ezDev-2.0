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
    private final LinkedHashMap<String, Argument<?>> arguments;

    public Command(String label, Script script, Executor executor, LinkedHashMap<String, Argument<?>> arguments) {
        super(label);
        this.label = label;
        this.script = script;
        this.executor = executor;
        this.arguments = arguments;
    }

    @SneakyThrows
    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!this.executor.isValid(sender)) {
            return true;
        }

        // If there are arguments required, make sure they match what was provided
        List<Object> newTokens = new ArrayList<>();
        if (this.arguments.size() > 0) {
            if (!ArgumentManager.getInstance().matchArguments(sender, this.arguments, args)) return true;
            else {
                int i = 0;
                for (Map.Entry<String, Argument<?>> argument : this.arguments.entrySet()) {
                    newTokens.add(argument.getValue().parse(args, i));
                    System.out.println("Adding " + argument.getValue().parse(args, i));
                    i++;
                }
            }
        }

        int scriptLine = this.script.getCommandLines().get(this);
        for (Map.Entry<Integer, String> line : this.script.getLines().entrySet()) {
            if (line.getKey() > scriptLine) {
                if (line.getValue().equals("end")) break;
                String[] tokens = line.getValue().split(" ");
                Action action = ActionManager.getInstance().findAction(tokens);
                if (action == null) {
                    throw new ezDevException("Invalid action. Action: " + tokens[0], this.script.getFile(), line.getKey());
                } else if (!action.handle(sender, newTokens, this.arguments, tokens.length, this.script.getFile(), line.getKey())) {
                    break;
                }
            }
        }

        return true;
    }

    public enum Executor {
        PLAYER,
        CONSOLE,
        BOTH;

        public boolean isValid(CommandSender sender) {
            if (this != BOTH) {
                if (sender instanceof Player) {
                    if (this != PLAYER) {
                        sender.sendMessage(ChatColor.RED + "Only console can execute this command.");
                        return false;
                    }
                } else if (this != CONSOLE) {
                    sender.sendMessage(ChatColor.RED + "Only players can execute this command.");
                    return false;
                }
            }

            return true;
        }
    }

}
