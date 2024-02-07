package com.furnibuilder.wordguesser;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;

public class WordScheduler implements Runnable {
    private static final int DELAY = 1800;

    private static int LAST_RANDOM_WORD = 0;

    public WordScheduler() {
        Emulator.getThreading().run(this, 300000L);
    }

    public void run() {
        Emulator.getThreading().run(this, 1800L);
        int time = Emulator.getIntUnixTimestamp();
        if (time - LAST_RANDOM_WORD > 1600) {
            sendNewRandomWord();
            LAST_RANDOM_WORD = time;
        }
    }

    public void sendNewRandomWord() {
        String newWord = WordGuesser.getWordManager().getRandomWord();
        String scrambled = scramble(newWord);
        WordGuesser.setWord(newWord);
        WordGuesser.setGuessed(false);
        WordGuesser.setScrambledWord(scrambled);
        for (Habbo habbo : Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().values()) {
            if (habbo.getHabboInfo().getCurrentRoom() == null) {
                habbo.alert(Emulator.getTexts().getValue("randomword.new_word").replace("%word%", scrambled));
                continue;
            }
            habbo.whisper(Emulator.getTexts().getValue("randomword.new_word").replace("%word%", scrambled), RoomChatMessageBubbles.RADIO);
        }
        Emulator.getThreading().run(() -> {
            if (!WordGuesser.getGuessed()) {
                for (Habbo habbo : Emulator.getGameEnvironment().getHabboManager().getOnlineHabbos().values()) {
                    if (habbo.getHabboInfo().getCurrentRoom() == null) {
                        habbo.alert(Emulator.getTexts().getValue("randomword.nobody").replace("%word%", newWord));
                        continue;
                    }
                    habbo.whisper(Emulator.getTexts().getValue("randomword.nobody").replace("%word%", newWord), RoomChatMessageBubbles.GOAT);
                }
                WordGuesser.setGuessed(true);
            }
        }, 300000L);
    }

    public String scramble(String inputString) {
        char[] a = inputString.toCharArray();
        for (int i = 0; i < a.length; i++) {
            int j = Emulator.getRandom().nextInt(a.length);
            char temp = a[i];
            a[i] = a[j];
            a[j] = temp;
        }
        return new String(a);
    }
}