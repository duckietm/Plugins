package com.eu.habbo.funcommands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;
import lombok.extern.slf4j.Slf4j;


import com.eu.habbo.funcommands.commands.AfkCommand;
import static com.eu.habbo.funcommands.eventloader.loadAll.loadAll;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

@Slf4j
public class FunCommands extends HabboPlugin implements EventListener {
    public static FunCommands INSTANCE = null;

    @Override
    public void onEnable() {
        INSTANCE = this;
        Emulator.getPluginManager().registerEvents(this, this);
        if (Emulator.isReady) {
            AfkCommand.startBackgroundThread();
            this.checkDatabase();
        }
    }

    @Override
    public void onDisable() {
    }

    @Override
    public boolean hasPermission(Habbo habbo, String s) {
        return false;
    }

    private boolean registerPermission(String name, String options, String defaultValue, boolean defaultReturn)
    {
        try (Connection connection = Emulator.getDatabase().getDataSource().getConnection()) {
            try (PreparedStatement statement = connection.prepareStatement("SELECT `column_name` FROM INFORMATION_SCHEMA.COLUMNS WHERE `table_name` = 'permissions' AND `column_name` = '" + name + "'")) {
                while (!statement.executeQuery().next())
                    try (PreparedStatement statement_ = connection.prepareStatement("ALTER TABLE  `permissions` ADD  `" + name +"` ENUM(  " + options + " ) NOT NULL DEFAULT  '" + defaultValue + "'"))
                    { statement_.execute();
                        return true;
                    }
            return true;
        }
    } catch (SQLException e) {
            log.error("Caught SQL exception", e);
        }
        return defaultReturn;
    }

    @EventHandler
    public static void onEmulatorLoaded(EmulatorLoadedEvent e) throws Exception {
        INSTANCE.checkDatabase();
        AfkCommand.startBackgroundThread();
        loadAll();
        log.info("OFFICIAL PLUGIN - Fun Commands (2.0.0) has official loaded!");
    }

    public void checkDatabase() {
        boolean reloadPermissions = false;
        reloadPermissions = this.registerPermission("cmd_slime", "'0', '1', '2'", "1", reloadPermissions);
        reloadPermissions = this.registerPermission("cmd_hug", "'0', '1', '2'", "1", reloadPermissions);
        reloadPermissions = this.registerPermission("cmd_nuke", "'0', '1', '2'", "1", reloadPermissions);
        reloadPermissions = this.registerPermission("cmd_afk", "'0', '1', '2'", "1", reloadPermissions);
        reloadPermissions = this.registerPermission("cmd_tptome", "'0', '1', '2'", "0", reloadPermissions);
        reloadPermissions = this.registerPermission("cmd_superpull", "'0', '1', '2'", "0", reloadPermissions);
        reloadPermissions = this.registerPermission("cmd_superpush", "'0', '1', '2'", "0", reloadPermissions);

        if (reloadPermissions)
        {
            Emulator.getGameEnvironment().getPermissionsManager().reload();
        }

    }

    public static void main(String[] args)
    {
        System.out.println("Don't run this separately");
    }
}