package com.furnibuilder.botprotection;

import com.eu.habbo.Emulator;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.UUID;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SSOManager {
  public static void deleteTicketFromUser(int userId) {
     try (Connection connection = Emulator.getDatabase().getDataSource().getConnection()) {

       String token = UUID.randomUUID().toString();
       String auth_ticket = token;

        PreparedStatement stm = connection.prepareStatement("UPDATE `users` SET `auth_ticket` = ? WHERE `id` = ?");
        stm.setString(1, "generalized-" + auth_ticket);
        stm.setInt(2, userId);
        stm.execute();
        log.info("UserID:" + userId + " has been generalized");
      } catch (SQLException e) {
        log.error(String.valueOf(e));
      }
   }
}