package me.aberdeener.ezdev.models;

import lombok.Getter;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class Command {

    @Getter
    private final String label;
    @Getter
    private final Script script;

    public Command(String label, Script script) {
        this.label = label;
        this.script = script;
    }

    public void execute(PlayerCommandPreprocessEvent event) {
        int token = script.getCommandTokens().get(this);
    }
}
