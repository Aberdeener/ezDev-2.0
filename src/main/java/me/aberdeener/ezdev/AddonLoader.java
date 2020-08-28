package me.aberdeener.ezdev;

import lombok.Getter;
import lombok.SneakyThrows;
import me.aberdeener.ezdev.models.Addon;
import me.aberdeener.ezdev.models.ezDevException;

import java.io.File;
import java.io.InputStream;
import java.net.URL;
import java.net.URLClassLoader;
import java.util.Scanner;
import java.util.jar.JarFile;
import java.util.zip.ZipEntry;

public class AddonLoader extends URLClassLoader {

    static {
        ClassLoader.registerAsParallelCapable();
    }

    @Getter
    private final Addon addon;
    @Getter
    private final URL url;
    @Getter
    private final File file;
    @Getter
    private final Class<?> mainClass;

    /*
     Credit to @Dreta for the original Jar loader class.
     Modified by Aberdeener for ezDev usage.
     */
    public AddonLoader(File file) throws Exception {
        super(new URL[]{file.toURI().toURL()}, ezDev.class.getClassLoader());

        JarFile jar = new JarFile(file);
        url = file.toURI().toURL();
        this.file = file;

        ZipEntry entry = jar.getEntry("main.txt");
        if (entry == null) {
            throw new ezDevException("Jar does not contain main.txt. This file is required for the initialization of your addon. Jar: " + jar.getName());
        }
        try (InputStream is = jar.getInputStream(entry)) {
            Scanner scanner = new Scanner(is).useDelimiter("\\A");
            String clazz = scanner.hasNext() ? scanner.next() : "";
            try {
                mainClass = findClass(clazz);
            } catch (ClassNotFoundException ex) {
                throw new ezDevException("The specified class in main.txt couldn't be found. Specified class: " + clazz);
            }
        }

        this.addon = (Addon) mainClass.getConstructor().newInstance();
    }
}
