package me.aberdeener.ezdev.managers;

import lombok.Getter;
import me.aberdeener.ezdev.arguments.Argument;
import me.aberdeener.ezdev.models.ezDevException;
import org.bukkit.ChatColor;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

public class ArgumentManager {

    @Getter
    public static ArgumentManager instance;
    @Getter
    private final List<Argument<?>> arguments = new ArrayList<>();

    public ArgumentManager() {
        instance = this;
    }

    public void addArgument(Argument<?> argument) throws ezDevException {
        for (Argument<?> a : this.arguments) {
            if (a.getLiteral().equals(argument.getLiteral())) {
                throw new ezDevException("Argument with same literal already registered.");
            }
        }

        this.arguments.add(argument);
    }

    /**
     * Used to ensure that the command the sender ran matched the arguments required by the command
     *
     * @param sender person who sent the command
     * @param arguments arguments required by the command
     * @param tokens arguments passed by the sender
     * @return boolean
     */
    public boolean matchArguments(CommandSender sender, LinkedHashMap<String, Argument<?>> arguments, String[] tokens) {
        int i = 0;
        for (Argument<?> argument : arguments.values()) {
            if (tokens.length <= i) {
                sender.sendMessage(ChatColor.RED + "Expected " + argument.getLiteral() + " as argument #" + (i + 1) + ", but found nothing.");
                return false;
            }

            if (!argument.validate(sender, tokens, i)) {
                return false;
            } else {
                i++;
            }
        }

        return true;
    }
}
