package me.aberdeener.ezdev.models;

import lombok.Getter;
import lombok.SneakyThrows;
import me.aberdeener.ezdev.arguments.Argument;
import me.aberdeener.ezdev.ezDev;
import me.aberdeener.ezdev.managers.ArgumentManager;
import me.aberdeener.ezdev.managers.CommandManager;
import me.aberdeener.ezdev.managers.ListenerManager;
import org.bukkit.event.player.PlayerEvent;

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
                    Command.Executor executor = getExecutor(trigger, tokens, i, line.getKey());
                    LinkedHashMap<String, Argument<?>> arguments = getArguments(tokens, i);
                    if (trigger.endsWith(":") && arguments.size() < 1) trigger = trigger.substring(0, trigger.length() - 1);
                    switch (header) {
                        case "command": {
                            inHeader = true;
                            Command command = new Command(trigger, this, executor, arguments);
                            if (CommandManager.getInstance().registerCommand(command)) {
                                getCommandLines().put(command, line.getKey());
                                ezDev.getInstance().getLogger().info("Created command /" + command.getLabel() + " in script " + getFile().getName());
                            }
                            break;
                        }
                        case "listener": {
                            inHeader = true;
                            Class<? extends PlayerEvent> event = ListenerManager.getEvents().get(trigger);
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
        ezDev.getInstance().getScripts().add(this);
        ezDev.getInstance().getLogger().info("Loaded script " + getFile());
    }

    @SneakyThrows
    private Command.Executor getExecutor(String trigger, String[] tokens, int i, int line_number) {
        Command.Executor executor;
        // If the trigger ends with a colon, we dont expect them to define an executor after - so we fall back to BOTH
        boolean hasArgs = false;
        if (trigger.endsWith(":")) {
            executor = Command.Executor.BOTH;
        } else {
            try {
                // Attempt to extract the executorType with the next token, if it doesnt exist then we catch the error
                String executorType = tokens[i + 2];
                if (!executorType.endsWith(":")) {
                    try {
                        String arg = tokens[i + 3];
                        hasArgs = true;
                    } catch (ArrayIndexOutOfBoundsException e) {
                        throw new ezDevException("Executors must end with `:`. Executor: " + executorType, getFile(), line_number);
                    }
                } else if (!executorType.startsWith("(") || !executorType.contains(")")) {
                    throw new ezDevException("Executors must start with `(` and end with `)`. Executor: " + executorType, getFile(), line_number);
                }
                executorType = executorType.substring(1, executorType.length() - 2);
                switch (executorType) {
                    case "player":
                        executor = Command.Executor.PLAYER;
                        break;
                    case "console":
                        executor = Command.Executor.CONSOLE;
                        break;
                    default: {
                        if (!hasArgs) throw new ezDevException("Invalid executor. Executor: " + executorType + ". Valid executors: `player`, `console`", getFile(), line_number);
                        else executor = Command.Executor.BOTH;
                    }
                }
            } catch (ArrayIndexOutOfBoundsException e) {
                throw new ezDevException("Triggers must end with `:` when an executor is not specified. Trigger: " + trigger, getFile(), line_number);
            }
        }
        return executor;
    }

    private LinkedHashMap<String, Argument<?>> getArguments(String[] tokens, int i) {
        LinkedHashMap<String, Argument<?>> arguments = new LinkedHashMap<>();
        for (int e = i; e < tokens.length; e++) {
            String argument = tokens[e];
            if (argument.endsWith(":")) argument = argument.substring(0, tokens[e].length() - 1);
            if (argument.matches("\\((.*?)<(.*?>)\\)")) {
                String[] argInfo = argument.split("<");
                String argumentType = argInfo[0].substring(1);
                String argumentName = argInfo[1].substring(0, argInfo[1].length() - 2);
                for (Argument<?> a : ArgumentManager.getInstance().getArguments()) {
                    if (a.getLiteral().equals(argumentType)) {
                        arguments.put(argumentName, a);
                        break;
                    }
                }
            }
        }
        return arguments;
    }
}
