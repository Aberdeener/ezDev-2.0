package me.aberdeener.ezdev.models;

import lombok.Getter;
import org.bukkit.command.CommandSender;

import java.util.Map;

public class Command extends org.bukkit.command.Command {

    @Getter
    private final String label;
    @Getter
    private final Script script;

    public Command(String label, Script script) {
        super(label);
        this.label = label;
        this.script = script;
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        int token_number = getScript().getCommandTokens().get(this);
        for (Map.Entry<Integer, String> token : getScript().getTokens().entrySet()) {
            if (token.getKey() < token_number) continue;
            switch (token.getValue()) {
                case "tell": {

                    break;
                }
                case "give": {

                    break;
                }
                default: continue;
            }
        }
        return true;
    }
}
