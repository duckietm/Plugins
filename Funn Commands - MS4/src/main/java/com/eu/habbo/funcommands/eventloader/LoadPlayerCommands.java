package com.eu.habbo.funcommands.eventloader;

import com.eu.habbo.Emulator;
import com.eu.habbo.funcommands.commands.*;
import com.eu.habbo.habbohotel.commands.CommandHandler;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class LoadPlayerCommands {
    public static void loadPlayerCommands() {
        try {
            CommandHandler.addCommand(new NukeCommand("cmd_nuke", Emulator.getTexts().getValue("fun.cmd_nuke.keys").split(";")));
            CommandHandler.addCommand(new HugCommand("cmd_hug", Emulator.getTexts().getValue("fun.cmd_hug.keys").split(";")));
            CommandHandler.addCommand(new SlimeCommand("cmd_slime", Emulator.getTexts().getValue("fun.cmd_slime.keys").split(";")));
            CommandHandler.addCommand(new AfkCommand("cmd_afk", Emulator.getTexts().getValue("fun.cmd_afk.keys").split(";")));
            CommandHandler.addCommand(new TeleportToMeCommand("cmd_tptome", Emulator.getTexts().getValue("fun.cmd_tptome.keys").split(";")));
            CommandHandler.addCommand(new SuperPullCommand("cmd_superpull", Emulator.getTexts().getValue("fun.cmd_superpull.keys").split(";")));
            CommandHandler.addCommand(new SuperPushCommand("cmd_superpush", Emulator.getTexts().getValue("fun.cmd_superpush.keys").split(";")));
        }
        catch (Exception ex)
        {
            log.error("Caught exception", ex);
        }
    }
}
