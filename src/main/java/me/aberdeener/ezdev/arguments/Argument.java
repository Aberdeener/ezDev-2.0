package me.aberdeener.ezdev.arguments;

import lombok.Getter;
import lombok.SneakyThrows;
import me.aberdeener.ezdev.managers.ArgumentManager;
import org.bukkit.command.CommandSender;

public abstract class Argument<T> {

    @Getter
    private final String literal;

    @SneakyThrows
    protected Argument(String literal) {
        this.literal = literal;
        ArgumentManager.getInstance().addArgument(this);
    }

    /**
     * Validates that a string can be parsed into specific type.
     * @param sender User who sent command, used to return error messages to without going back to Command
     * @param tokens List of tokens to process
     * @param i Which token to start processing on
     * @return true or false upon parse attempt
     */
    public abstract boolean validate(CommandSender sender, String[] tokens, int i);

    /**
     * Parses an argument into its object. Assumes that it passed validate().
     * @param tokens List of tokens to process
     * @param i Which token to start processing on
     * @return Parsed object
     */
    public abstract T parse(String[] tokens, int i);
}