package me.aberdeener.ezdev.models;

import lombok.Getter;
import me.aberdeener.ezdev.managers.ActionManager;
import org.bukkit.command.CommandSender;

import java.io.File;

public abstract class Action {

    @Getter
    private final Addon addon;
    @Getter
    private final String phrase;
    @Getter
    private final int length;

    protected Action(Addon addon, String phrase, int length) {
        this.addon = addon;
        this.phrase = phrase;
        this.length = length;
        ActionManager.addAction(this);
    }

    public abstract void handle(CommandSender sender, String[] tokens, File scriptFile, int line);

}
