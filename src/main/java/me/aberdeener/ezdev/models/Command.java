package me.aberdeener.ezdev.models;

import lombok.Getter;
import lombok.SneakyThrows;
import me.aberdeener.ezdev.VariableManager;
import me.aberdeener.ezdev.ezDev;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

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

    @SneakyThrows
    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        ezDev.getInstance().getLogger().info("ezDev command " + commandLabel);
        int scriptLine = getScript().getCommandLines().get(this);
        for (Map.Entry<Integer, String> line : getScript().getLines().entrySet()) {
            if (line.getKey() > scriptLine) {
                if (line.getValue().equals("end")) break;
                String[] tokens = line.getValue().split(" ");
                String action = tokens[0];
                switch (action) {
                    case "tell": {
                        StringBuilder sb = new StringBuilder();
                        boolean lastVariable = false;
                        for (int i = 2; i < tokens.length; i++) {
                            if (i == 2 && !tokens[2].startsWith("\"")) {
                                throw new ezDevException("Message strings must start with \".", getScript().getFile(), line.getKey());
                            }
                            if (VariableManager.isVariable(tokens[i])) {
                                sb.append(VariableManager.get(tokens[i])).append(" ");
                                lastVariable = true;
                            } else {
                                sb.append(tokens[i]).append(" ");
                                lastVariable = false;
                            }
                        }
                        String message = sb.toString().trim();
                        if (!message.endsWith("\"") && !lastVariable) {
                            throw new ezDevException("Message strings must end with \". Message: " + message, getScript().getFile(), line.getKey());
                        }
                        message = message.substring(1, message.length() - (message.endsWith("\"") ? 2 : 0));
                        String target = tokens[1];
                        switch (target) {
                            case "sender": {
                                sender.sendMessage(message);
                                break;
                            }
                            case "all": {
                                for (Player player : Bukkit.getOnlinePlayers()) player.sendMessage(message);
                                break;
                            }
                            default: throw new ezDevException("Invalid target. Target: " + target, getScript().getFile(), line.getKey());
                        }
                        break;
                    }
                    default: throw new ezDevException("Invalid action. Action: " + action, getScript().getFile(), line.getKey());
                }
            }
        }
        return true;
    }
}
