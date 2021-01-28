package me.aberdeener.ezdev.managers;

import lombok.Getter;
import me.aberdeener.ezdev.ezDev;

import java.io.File;
import java.io.IOException;

public class VariableManager {

    @Getter
    private boolean VARIABLES_ENABLED = false;
    private final ezDev plugin;

    public VariableManager(ezDev plugin) {
        this.plugin = plugin;

        File variablesFile = new File(ezDev.getInstance().getDataFolder() + File.separator + "variables.yml");
        if (!variablesFile.exists()) {
            this.plugin.getLogger().info("Variables file does not exist! Attempting to create...");
            try {
                if (!this.plugin.getDataFolder().exists()) ezDev.getInstance().getDataFolder().mkdir();
                variablesFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
                this.plugin.getLogger().severe("Failed to create variables.yml file!");
                return;
            }
        }

        try {
            this.plugin.getConfig().load(variablesFile);
            VARIABLES_ENABLED = true;
        } catch (Exception e) {
            e.printStackTrace();
            this.plugin.getLogger().severe("Failed to load variables from file!");
        }
    }

    public boolean isVariable(String key) {
        if (!VARIABLES_ENABLED) return false;
        return this.plugin.getConfig().getString(this.extractKey(key)) != null;
    }

    public String get(String key) {
        if (!VARIABLES_ENABLED) return key;
        key = this.extractKey(key);
        String value = this.plugin.getConfig().getString(key);
        return value == null ? key : value;
    }

    /**
     * Converts <code>{key}</code> to <code>key</code> by removing the brackets
     *
     * @param raw key to extract
     * @return extracted key
     */
    private String extractKey(String raw) {
        try {
            return raw.substring(raw.indexOf("{") + 1, raw.indexOf("}"));
        } catch (StringIndexOutOfBoundsException ignored) {
            return raw;
        }
    }

}
