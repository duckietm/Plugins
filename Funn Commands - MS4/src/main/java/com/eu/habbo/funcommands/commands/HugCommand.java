package com.eu.habbo.funcommands.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.Room;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;
public class HugCommand extends Command
{
    public HugCommand(String permission, String[] keys)
    {
        super(permission, keys);
    }

    @Override
    public boolean handle(GameClient gameClient, String[] strings) throws Exception {
        final Room room = gameClient.getHabbo().getHabboInfo().getCurrentRoom();
        if (strings.length >= 2) {
            Habbo habbo = gameClient.getHabbo().getHabboInfo().getCurrentRoom().getHabbo(strings[1]);
            if (habbo != null) {
                if (habbo != gameClient.getHabbo()) {
                    if (habbo.getRoomUnit().getCurrentLocation().distance(gameClient.getHabbo().getRoomUnit().getCurrentLocation()) <= 1.5) {

                        habbo.getRoomUnit().lookAtPoint(gameClient.getHabbo().getRoomUnit().getCurrentLocation());
                        gameClient.getHabbo().getRoomUnit().lookAtPoint(habbo.getRoomUnit().getCurrentLocation());
                        String[] HugEnable = Emulator.getConfig().getValue("fun.cmd_hug.enable").split(";");
                        if (HugEnable.length > 0) {
                            room.giveEffect(gameClient.getHabbo(), Integer.valueOf(HugEnable[Emulator.getRandom().nextInt(HugEnable.length)]), 10);
                            room.giveEffect(habbo, Integer.valueOf(HugEnable[Emulator.getRandom().nextInt(HugEnable.length)]), 10);

                        }
                        gameClient.getHabbo().talk(Emulator.getTexts().getValue("fun.hugmessages.hugger").replace("%hugger%", gameClient.getHabbo().getHabboInfo().getUsername()).replace("%huggedperson%", habbo.getHabboInfo().getUsername()), RoomChatMessageBubbles.HEARTS);
                        habbo.talk(Emulator.getTexts().getValue("fun.hugmessages.huggedperson").replace("%hugger%", gameClient.getHabbo().getHabboInfo().getUsername()).replace("%huggedperson%", habbo.getHabboInfo().getUsername()), RoomChatMessageBubbles.HEARTS);

                    }
                    else {
                        gameClient.getHabbo().whisper(Emulator.getTexts().getValue("fun.hugmessages.tofar").replace("%huggedperson%", habbo.getHabboInfo().getUsername()));
                    }
                }
            }
        }

        return true;
    }
}
