package me.aberdeener.ezdev.models;

import lombok.Getter;
import me.aberdeener.ezdev.CommandManager;
import me.aberdeener.ezdev.ListenerManager;
import org.bukkit.event.Event;
import org.bukkit.event.player.PlayerJoinEvent;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Script {

    @Getter
    private final String name;
    @Getter
    private final File file;
    @Getter
    private final HashMap<Integer, String> tokens;
    @Getter
    private final HashMap<Command, Integer> commandTokens = new HashMap<>();

    public Script(File file) {

        this.name = file.getName().replace(".ez", "");
        this.file = file;

        this.tokens = new HashMap<>();

        // When a new Script is initialized, generate its tokens from the File which is passed.
        try {
            Scanner scanner = new Scanner(file);
            int token_number = 1;
            while (scanner.hasNext()) {
                tokens.put(token_number, scanner.next().trim());
                token_number++;
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // After generating the tokens, scan them for Commands or Listeners which are within them.
        for (Map.Entry<Integer, String> token : tokens.entrySet()) {
            // TODO
            if (true) {
                Command command = new Command(token.getValue(), this);
                CommandManager.registerCommand(command);
                getCommandTokens().put(command, token.getKey());
            } else if (false) {
                Class<? extends Event> event = ListenerManager.getEvent(token.getValue());
                if (event != null) ListenerManager.getListeners().add(new Listener(event, this));
            }
        }
    }
}
