package me.aberdeener.ezdev.models;

import lombok.Getter;
import lombok.SneakyThrows;
import me.aberdeener.ezdev.managers.CommandManager;
import me.aberdeener.ezdev.managers.ListenerManager;
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
    private final NavigableMap<Integer, String> lines = new TreeMap<>();
    @Getter
    private final HashMap<Command, Integer> commandLines = new HashMap<>();
    @Getter
    private final HashMap<Listener, Integer> listenerLines = new HashMap<>();

    @SneakyThrows
    public Script(File file){

        this.name = file.getName().replace(".ez", "");
        this.file = file;

        // When a new Script is initialized, generate lines from the File which is passed.
        try {
            Scanner scanner = new Scanner(file);
            int lineNumber = 1;
            while (scanner.hasNextLine()) {
                String line = scanner.nextLine();
                if (line.isBlank()) continue;
                lines.put(lineNumber, line.trim());
                lineNumber++;
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
            return;
        }

        // After generating the lines, scan each of them for Commands or Listeners,
        boolean inHeader = false;
        for (Map.Entry<Integer, String> line : getLines().entrySet()) {
            if (line.getValue().equals("end")) {
                inHeader = false;
                continue;
            }
            if (!inHeader) {
                String[] tokens = line.getValue().split(" ");
                for (int i = 0; i <= tokens.length; i++ ) {
                    String header = tokens[i];
                    String trigger;
                    try {
                        trigger = tokens[i + 1];
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new ezDevException("Headers require triggers. Header: " + header, getFile(), line.getKey());
                    }
                    if (!trigger.endsWith(":")) {
                        throw new ezDevException("Triggers must end with `:`. Trigger: " + trigger, getFile(), line.getKey());
                    }
                    trigger = trigger.substring(0, trigger.length() - 1);
                    switch (header) {
                        case "command": {
                            inHeader = true;
                            Command command = new Command(trigger, this);
                            if (CommandManager.registerCommand(command)) {
                                getCommandLines().put(command, line.getKey());
                                ezDev.getInstance().getLogger().info("Created command /" + command.getLabel() + " in script " + getFile().getName());
                            } else {
                                ezDev.getInstance().getLogger().warning("Command /" + command.getLabel() + " has previously been registered by an ezDev script (" + CommandManager.getCommandScript(command.getLabel()).getFile() + ")");
                            }
                            break;
                        }
                        case "listener": {
                            inHeader = true;
                            Class<? extends Event> event = ListenerManager.getEvents().get(trigger);
                            if (event != null) {
                                Listener listener = new Listener(event, this);
                                ListenerManager.getListeners().add(listener);
                                getListenerLines().put(listener, line.getKey());
                                ezDev.getInstance().getLogger().info("Created listener for event: " + event.getCanonicalName() + " in script " + getFile().getName());
                            } else {
                                ezDev.getInstance().getLogger().warning("Invalid event (" + trigger + ") for listener in script " + getFile().getName());
                            }
                            break;
                        }
                    }
                    if (inHeader) break;
                }
            }
        }
        ezDev.getScripts().add(this);
    }
}
