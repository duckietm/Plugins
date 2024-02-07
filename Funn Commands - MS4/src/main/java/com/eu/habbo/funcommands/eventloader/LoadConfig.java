package com.eu.habbo.funcommands.eventloader;
import lombok.extern.slf4j.Slf4j;
import com.eu.habbo.Emulator;

@Slf4j
public class LoadConfig {
    public static void loadConfig() {
        try {
            Emulator.getConfig().register("fun.cmd_hug.enable", "9");
        }
        catch (Exception ex)
        {
            log.error("Caught exception", ex);
        }
    }
}
