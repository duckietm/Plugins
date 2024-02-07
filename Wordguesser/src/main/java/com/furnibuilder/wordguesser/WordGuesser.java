package com.furnibuilder.wordguesser;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.commands.Command;
import com.eu.habbo.habbohotel.commands.CommandHandler;
import com.eu.habbo.habbohotel.rooms.RoomChatMessageBubbles;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;
import com.eu.habbo.plugin.events.emulator.EmulatorLoadedEvent;
import com.eu.habbo.plugin.events.users.UserEnterRoomEvent;
import com.eu.habbo.plugin.events.users.UserLoginEvent;
import com.furnibuilder.wordguesser.commands.RandomWordCommand;
import com.furnibuilder.wordguesser.commands.UpdateWordsCommand;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WordGuesser extends HabboPlugin implements EventListener {
    public static WordGuesser INSTANCE = null;

    private static WordManager wordManager;

    private static boolean hasBeenGuessed = true;

    private static String word = "";

    private static String scrambledWord = "";

    public static String HAS_LOGGED_IN_KEY = "has.logged.in.key";


    public void onEnable() {
        INSTANCE = this;
        Emulator.getPluginManager().registerEvents(this, this);
        if (Emulator.isReady) {
            checkDatabase();
            new WordScheduler();
        }
        log.info("[WordGuesser] Started Word Guesser Plugin!");
    }

    public void onDisable() {
        log.info("[WordGuesser] Stopped Word Guesser Plugin!");
    }

    @EventHandler
    public static void onEmulatorLoaded(EmulatorLoadedEvent event) {
        INSTANCE.checkDatabase();
        new WordScheduler();
    }

    public boolean hasPermission(Habbo habbo, String s) {
        return false;
    }

    private void checkDatabase() {
        boolean reloadPermissions = false;
        try(Connection connection = Emulator.getDatabase().getDataSource().getConnection(); Statement statement = connection.createStatement()) {
            statement.execute("ALTER TABLE  `emulator_texts` CHANGE  `value`  `value` VARCHAR( 255 ) CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci NOT NULL");
        } catch (SQLException sQLException) {}
        Emulator.getTexts().register("commands.description.cmd_randomword", ":rw <word>");
        Emulator.getTexts().register("randomword.cmd_randomword.keys", "rw");
        Emulator.getTexts().register("randomword.cmd_randomword.success", "You got the word right! Congrats.");
        Emulator.getTexts().register("randomword.cmd_randomword.error", "Please fill in a word");
        Emulator.getTexts().register("randomword.cmd_randomword.wrong", "You guessed the wrong word");
        Emulator.getTexts().register("randomword.cmd_randomword.no_word", "There is no word to guess right now");
        Emulator.getTexts().register("randomword.new_word", "The word is scrambled. Beat it! \"%word%\" use :rw (word)");
        Emulator.getTexts().register("randomword.nobody", "Nobody got the right word, the word was %word%");
        Emulator.getTexts().register("randomword.winner", "User %username% got the right word \"%word%\" and won the prize.");
        Emulator.getConfig().register("randomword.prize.currency_type", "5");
        Emulator.getConfig().register("randomword.prize.currency_amount", "1");
        Emulator.getConfig().register("randomword.prize.badge", "");
        Emulator.getTexts().register("randomword.cmd_update_words.success", "Successfully updated random words");
        Emulator.getTexts().register("commands.description.cmd_update_words", ":update_words");
        Emulator.getTexts().register("randomword.cmd_update_words.keys", "update_words");
        CommandHandler.addCommand((Command)new UpdateWordsCommand("cmd_update_words", Emulator.getTexts().getValue("randomword.cmd_update_words.keys").split(";")));
        reloadPermissions = registerPermission("cmd_randomword", "'0', '1'", "0", reloadPermissions);
        reloadPermissions = registerPermission("cmd_update_words", "'0', '1'", "0", reloadPermissions);
        if (reloadPermissions)
            Emulator.getGameEnvironment().getPermissionsManager().reload();
        CommandHandler.addCommand((Command)new RandomWordCommand("cmd_randomword", Emulator.getTexts().getValue("randomword.cmd_randomword.keys").split(";")));
        wordManager = new WordManager();
    }

    private boolean registerPermission(String name, String options, String defaultValue, boolean defaultReturn) {
        try(Connection connection = Emulator.getDatabase().getDataSource().getConnection();
            PreparedStatement statement = connection.prepareStatement("ALTER TABLE  `permissions` ADD  `" + name + "` ENUM(  " + options + " ) NOT NULL DEFAULT  '" + defaultValue + "'")) {
            statement.execute();
            return true;
        } catch (SQLException sQLException) {
            return defaultReturn;
        }
    }

    @EventHandler
    public static void onUserLoginEvent(UserLoginEvent event) {
        if (event.habbo == null)
            return;
        (event.habbo.getHabboStats()).getCache().put(HAS_LOGGED_IN_KEY, Boolean.valueOf(true));
    }

    @EventHandler
    public static void onUserEnterRoomEvent(UserEnterRoomEvent event) {
        if (event.habbo == null || event.room == null)
            return;
        if (!(event.habbo.getHabboStats()).getCache().containsKey(HAS_LOGGED_IN_KEY))
            return;
        if (hasBeenGuessed)
            return;
        (event.habbo.getHabboStats()).getCache().remove(HAS_LOGGED_IN_KEY);
        Emulator.getThreading().run(() -> {
            if (event.habbo == null || event.habbo.getRoomUnit() == null)
                return;
            event.habbo.whisper(Emulator.getTexts().getValue("randomword.new_word").replace("%word%", scrambledWord), RoomChatMessageBubbles.WIRED);
        }, 1000L);
    }

    public static WordManager getWordManager() {
        return wordManager;
    }

    public static String getWord() {
        return word;
    }

    public static void setWord(String newWord) {
        word = newWord;
    }

    public static void setScrambledWord(String scrambled) {
        scrambledWord = scrambled;
    }

    public static void setGuessed(boolean guessed) {
        hasBeenGuessed = guessed;
    }

    public static boolean getGuessed() {
        return hasBeenGuessed;
    }

    public static void main(String[] args) {
        System.out.println("Don't run this seperately");
    }
}
