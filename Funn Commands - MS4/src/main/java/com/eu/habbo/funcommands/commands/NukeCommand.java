package com.eu.habbo.funcommands.commands;
import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomUnitEffect;
import com.eu.habbo.habbohotel.users.Habbo;

public class NukeCommand extends Command
{
    public NukeCommand(String permission, String[] keys)
    {
        super(permission, keys);
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception
    {
        if (strings.length < 2)
        {
            gameClient.getHabbo().talk(Emulator.getTexts().getValue("habbo.not.found"));
            return false;
        }

        Habbo habbo = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getHabbo(strings[1]);
        if (habbo == null)
        {
            gameClient.getHabbo().talk(Emulator.getTexts().getValue("habbo.not.found"));
            return false;
        }

        if (habbo.getHabboInfo().getId() == gameClient.getHabbo().getHabboInfo().getId())
        {
            gameClient.getHabbo().talk(Emulator.getTexts().getValue("fun.nuke.self"));
            return false;
        }
        if (gameClient.getHabbo().getHabboInfo().getRiding() != null) {
            return false;
        }
        gameClient.getHabbo().talk(Emulator.getTexts().getValue("fun.nuke.action").replace("%username%", habbo.getHabboInfo().getUsername()));

        Emulator.getThreading().run(() -> {
            gameClient.getHabbo().getHabboInfo().getCurrentRoom().giveEffect(habbo, RoomUnitEffect.NINJADISAPPEAR.getId(), 10);
            habbo.shout(Emulator.getTexts().getValue("fun.nuke.nuked").replace("%username%", habbo.getHabboInfo().getUsername()));
        }, 500);

        return true;
    }
}

