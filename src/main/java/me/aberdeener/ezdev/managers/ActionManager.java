package me.aberdeener.ezdev.managers;

import lombok.Getter;
import lombok.SneakyThrows;
import me.aberdeener.ezdev.models.Action;
import me.aberdeener.ezdev.models.ezDevException;

import java.util.HashSet;
import java.util.Set;

public class ActionManager {

    @Getter
    private static ActionManager instance;
    @Getter
    private final Set<Action> actions = new HashSet<>();

    public ActionManager() {
        instance = this;
    }

    @SneakyThrows
    public void addAction(Action action) {
        if (this.actions.size() > 0) {
            for (Action a : this.actions) {
                if (a.getPhrase().equals(action.getPhrase()) && action.getLengths().stream().anyMatch(element -> a.getLengths().contains(element))) {
                    throw new ezDevException("Action already registered with same name and length. Original action: " + a.getPhrase() + " from addon " + a.getAddon().getName() + ". Repeat action: " + action.getPhrase() + " from addon " + action.getAddon().getName());
                } else {
                    this.actions.add(action);
                    return;
                }
            }
        } else {
            this.actions.add(action);
        }
    }

    public Action findAction(String[] tokens) {
        for (Action action : this.actions) {
            if (action.getPhrase().equals(tokens[0])) {
                if (action.getLengths().contains(-1) || action.getLengths().contains(tokens.length - 1)) {
                    return action;
                }
            }
        }

        return null;
    }

}
