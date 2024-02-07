package com.furnibuilder.wordguesser;

import com.eu.habbo.Emulator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class WordManager {
    private List<String> words;

    public WordManager() {
        this.words = new ArrayList<>();
        reload();
    }

    public void reload() {
        dispose();
        try(Connection connection = Emulator.getDatabase().getDataSource().getConnection(); PreparedStatement statement = connection.prepareStatement("SELECT * FROM `random_words`");
            ResultSet set = statement.executeQuery()) {
            while (set.next())
                this.words.add(set.getString("word"));
        } catch (SQLException e) {
            log.error("Caught SQL exception", e);
        }
    }

    public void dispose() {
        this.words.clear();
    }

    public String getRandomWord() {
        return this.words.get(Emulator.getRandom().nextInt(this.words.size()));
    }
}