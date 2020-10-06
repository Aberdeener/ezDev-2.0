package me.aberdeener.ezdev.arguments;

import lombok.Getter;
import me.aberdeener.ezdev.managers.ArgumentManager;
import me.aberdeener.ezdev.models.ezDevException;
import org.bukkit.command.CommandSender;

public abstract class Argument {

    @Getter
    private final String literal;

    protected Argument(String literal) throws ezDevException {
        this.literal = literal;
        ArgumentManager.addArgument(this);
    }

    public abstract boolean validate(CommandSender sender, String[] tokens, int i);

    public abstract Object parse(String[] tokens, int i);
}