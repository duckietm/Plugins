package com.furnibuilder.wordguesser.commands;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.gameclients.GameClient;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.furnibuilder.wordguesser.WordGuesser;

public class UpdateWordsCommand extends Command {
    public UpdateWordsCommand(String permission, String[] keys) {
        super(permission, keys);
    }

    public boolean handle(GameClient gameClient, String[] strings) throws Exception {
        WordGuesser.getWordManager().reload();
        gameClient.getHabbo().whisper(Emulator.getTexts().getValue("randomword.cmd_update_words.success"), RoomChatMessageBubbles.ALERT);
        return true;
    }
}