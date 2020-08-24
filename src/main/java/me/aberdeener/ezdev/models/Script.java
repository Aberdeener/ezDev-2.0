package me.aberdeener.ezdev.models;

import lombok.Getter;
import me.aberdeener.ezdev.CommandManager;
import me.aberdeener.ezdev.ListenerManager;
import me.aberdeener.ezdev.ezDev;
import org.bukkit.event.Event;

import java.io.File;
import java.io.IOException;
import java.util.*;

public class Script {

    @Getter
    private final String name;
    @Getter
    private final File file;
    @Getter
    private final NavigableMap<Integer, String> tokens = new TreeMap<>();
    @Getter
    private final HashMap<Command, Integer> commandTokens = new HashMap<>();
    @Getter
    private final HashMap<Listener, Integer> listenerTokens = new HashMap<>();

    public Script(File file) {

        this.name = file.getName().replace(".ez", "");
        this.file = file;

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
        boolean inHeader = false;
        for (Map.Entry<Integer, String> token : getTokens().entrySet()) {
            Map.Entry<Integer, String> triggerEntry = getTokens().higherEntry(token.getKey());
            if (triggerEntry == null) break;
            String trigger = triggerEntry.getValue();
            if (trigger.equals("end")) {
                inHeader = false;
                continue;
            }
            if (!inHeader) {
                switch (token.getValue()) {
                    case "command": {
                        inHeader = true;
                        Command command = new Command(trigger.substring(0, trigger.length() - 1), this);
                        CommandManager.registerCommand(command);
                        getCommandTokens().put(command, token.getKey());
                        ezDev.getInstance().getLogger().info("Created command " + command.getLabel() + " in script " + getFile().getName());
                        break;
                    }
                    case "listener": {
                        inHeader = true;
                        trigger = trigger.substring(0, trigger.length() - 1);
                        Class<? extends Event> event = ListenerManager.getEvent(trigger);
                        if (event != null) {
                            Listener listener = new Listener(event, this);
                            ListenerManager.getListeners().add(listener);
                            getListenerTokens().put(listener, token.getKey());
                            ezDev.getInstance().getLogger().info("Created listener for event: " + event.getCanonicalName() + " in script " + getFile().getName()););
                        } else {
                            ezDev.getInstance().getLogger().warning("Invalid event (" + trigger + ") for listener in script " + getFile().getName());
                        }
                        break;
                    }
                }
            }
        }
    }
}
