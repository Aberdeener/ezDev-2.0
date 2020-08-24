package me.aberdeener.ezdev.models;

import lombok.Getter;
import me.aberdeener.ezdev.ezDev;
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
        ezDev.getInstance().getLogger().info("ezdev command " + label);
        int headerKey = getScript().getCommandTokens().get(this);
        for (Map.Entry<Integer, String> action : getScript().getTokens().entrySet()) {
            if (action.getKey() < headerKey) continue;
            switch (action.getValue()) {
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
