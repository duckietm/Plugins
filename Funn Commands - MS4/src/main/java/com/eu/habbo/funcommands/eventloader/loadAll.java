package com.eu.habbo.funcommands.eventloader;

public class loadAll {
    public static void loadAll() throws Exception {
        LoadPlayerCommands.loadPlayerCommands();
        LoadTexts.loadTexts();
        LoadConfig.loadConfig();

    }
}
