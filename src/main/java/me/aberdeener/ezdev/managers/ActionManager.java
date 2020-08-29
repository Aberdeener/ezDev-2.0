package me.aberdeener.ezdev.managers;

import lombok.Getter;
import lombok.SneakyThrows;
import me.aberdeener.ezdev.models.Action;
import me.aberdeener.ezdev.models.ezDevException;

import java.util.HashSet;
import java.util.Set;

public class ActionManager {

    @Getter
    private static final Set<Action> actions = new HashSet<>();

    @SneakyThrows
    public static void addAction(Action action) {
        if (getActions().size() > 0) {
            for (Action a : getActions()) {
                if (a.getPhrase().equals(action.getPhrase()) && action.getLengths().stream().anyMatch(element -> a.getLengths().contains(element))) {
                    throw new ezDevException("Action already registered with same name and length. Original action: " + a.getPhrase() + " from addon " + a.getAddon().getName() + ". Repeat action: " + action.getPhrase() + " from addon " + action.getAddon().getName());
                } else {
                    getActions().add(action);
                    return;
                }
            }
        } else getActions().add(action);
    }

    public static Action findAction(String[] tokens) {
        for (Action action : getActions()) {
            if (action.getPhrase().equals(tokens[0])) {
                if (action.getLengths().contains(-1) || action.getLengths().contains(tokens.length - 1)) return action;
            }
        }
        return null;
    }
}
