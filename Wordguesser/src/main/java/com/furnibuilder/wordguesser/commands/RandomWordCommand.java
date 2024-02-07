package com.furnibuilder.wordguesser.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;
import com.furnibuilder.wordguesser.WordGuesser;

public class RandomWordCommand extends Command {
    public RandomWordCommand(String permission, String[] keys) {
        super(permission, keys);
    }

    public boolean handle(GameClient gameClient, String[] strings) throws Exception {
        if (strings.length != 2) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("randomword.cmd_randomword.error"), RoomChatMessageBubbles.WIRED);
            return true;
        }
        if (WordGuesser.getGuessed()) {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("randomword.cmd_randomword.no_word"), RoomChatMessageBubbles.BOT_GUIDE);
            return true;
        }
        if (WordGuesser.getWord().equalsIgnoreCase(strings[1])) {
            WordGuesser.setGuessed(true);
            if (Emulator.getConfig().getInt("randomword.prize.currency_type") == -1) {
                gameClient.getHabbo().giveCredits(Emulator.getConfig().getInt("randomword.prize.currency_amount"));
            } else {
                gameClient.getHabbo().givePoints(Emulator.getConfig().getInt("randomword.prize.currency_type"), Emulator.getConfig().getInt("randomword.prize.currency_amount"));
            }
            String badgeCode = Emulator.getConfig().getValue("randomword.prize.badge");
            if (!badgeCode.equals(""))
                gameClient.getHabbo().addBadge(badgeCode);
            for (Habbo habbo : Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().values()) {
                if (habbo.getHabboInfo().getCurrentRoom() == null) {
                    habbo.alert(Emulator.getTexts().getValue("randomword.winner").replace("%word%", WordGuesser.getWord()).replace("%username%", gameClient.getHabbo().getHabboInfo().getUsername()));
                    continue;
                }
                habbo.whisper(Emulator.getTexts().getValue("randomword.winner").replace("%word%", WordGuesser.getWord()).replace("%username%", gameClient.getHabbo().getHabboInfo().getUsername()), RoomChatMessageBubbles.ALERT);
            }
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("randomword.cmd_randomword.success"), RoomChatMessageBubbles.SANTA);
        } else {
            gameClient.getHabbo().whisper(Emulator.getTexts().getValue("randomword.cmd_randomword.wrong"), RoomChatMessageBubbles.FRANK);
        }
        return true;
    }
}
