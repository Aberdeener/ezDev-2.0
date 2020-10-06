package me.aberdeener.ezdev.arguments;

import me.aberdeener.ezdev.models.ezDevException;
import org.bukkit.command.CommandSender;

public class WordArgument extends Argument {

    public WordArgument() throws ezDevException {
        super("word");
    }

    @Override
    public boolean validate(CommandSender sender, String[] tokens, int i) {
        return tokens[i] != null;
    }

    @Override
    public Object parse(String[] tokens, int i) {
        return tokens[i];
    }
}
