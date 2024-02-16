package com.furnibuilder.botprotection.events;

import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventHandler;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.events.users.UserLoginEvent;
import com.furnibuilder.botprotection.SSOManager;

public class OnUserLoginEvent implements EventListener {
  @EventHandler
  public static void onUserLoginEvent(UserLoginEvent e) {
    Habbo habbo = e.habbo;
    if (habbo == null)
      return; 
    SSOManager.deleteTicketFromUser(habbo.getHabboInfo().getId());
  }
}