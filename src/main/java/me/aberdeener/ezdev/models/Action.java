package me.aberdeener.ezdev.models;

import lombok.Getter;
import me.aberdeener.ezdev.arguments.Argument;
import me.aberdeener.ezdev.managers.ActionManager;
import org.bukkit.command.CommandSender;

import java.io.File;
import java.util.LinkedHashMap;
import java.util.List;

public abstract class Action {

    @Getter
    private final Addon addon;
    @Getter
    private final String phrase;
    @Getter
    private final List<Integer> lengths;

    protected Action(Addon addon, String phrase, List<Integer> lengths) throws ezDevException {
        for (int length : lengths) {
            if (length < -1) throw new ezDevException("Action length must be greater than or equal to -1. Action: " + phrase + ", Addon: " + addon.getName());
        }
        this.addon = addon;
        this.phrase = phrase;
        this.lengths = lengths;
        ActionManager.addAction(this);
    }

    public abstract boolean handle(CommandSender sender, List<Object> tokens, LinkedHashMap<String, Argument> arguments, int length, File scriptFile, int line);
}