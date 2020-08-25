package me.aberdeener.ezdev.models;

import java.io.File;

public class ezDevException extends Exception {

    public ezDevException(String message) {
        super(message);
    }

    public ezDevException(String message, File file, int line) {
        super(message + " Caused in " + file + " on line " + line);
    }

}
