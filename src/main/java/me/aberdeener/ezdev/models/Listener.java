package me.aberdeener.ezdev.models;

import lombok.Getter;
import lombok.SneakyThrows;
import me.aberdeener.ezdev.managers.ActionManager;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerEvent;

import java.util.Map;

public class Listener {

    @Getter
    private final Class<? extends PlayerEvent> event;
    @Getter
    private final Script script;

    public Listener(Class<? extends PlayerEvent> event, Script script) {
        this.event = event;
        this.script = script;
    }

    @SneakyThrows
    public void execute(PlayerEvent event) {
        Player sender = event.getPlayer();
        int scriptLine = getScript().getListenerLines().get(this);
        for (Map.Entry<Integer, String> line : getScript().getLines().entrySet()) {
            if (line.getKey() > scriptLine) {
                if (line.getValue().equals("end")) break;
                String[] tokens = line.getValue().split(" ");
                Action action = ActionManager.findAction(tokens);
                if (action == null) {
                    throw new ezDevException("Invalid action. Action: " + tokens[0]);
                } else {
                    action.handle(sender, tokens, getScript().getFile(), line.getKey());
                }
            }
        }
    }
}
