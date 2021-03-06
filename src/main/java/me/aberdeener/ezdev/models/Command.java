package me.aberdeener.ezdev.models;

import lombok.Getter;
import lombok.SneakyThrows;
import me.aberdeener.ezdev.managers.ActionManager;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.util.Map;

public class Command extends org.bukkit.command.Command {

    @Getter
    private final String label;
    @Getter
    private final Script script;
    @Getter
    private final Executor executor;

    public Command(String label, Script script, Executor executor) {
        super(label);
        this.label = label;
        this.script = script;
        this.executor = executor;
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
        int scriptLine = getScript().getCommandLines().get(this);
        for (Map.Entry<Integer, String> line : getScript().getLines().entrySet()) {
            if (line.getKey() > scriptLine) {
                if (line.getValue().equals("end")) break;
                String[] tokens = line.getValue().split(" ");
                Action action = ActionManager.findAction(tokens);
                if (action == null) {
                    throw new ezDevException("Invalid action. Action: " + tokens[0], getScript().getFile(), line.getKey());
                } if (!action.handle(sender, tokens, tokens.length, getScript().getFile(), line.getKey())) break;
            }
        }
        return true;
    }

    public enum Executor {
        PLAYER, CONSOLE, BOTH
    }
}
