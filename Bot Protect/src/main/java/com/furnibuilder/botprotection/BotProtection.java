package com.furnibuilder.botprotection;

import com.eu.habbo.Emulator;
import com.eu.habbo.habbohotel.users.Habbo;
import com.eu.habbo.plugin.EventListener;
import com.eu.habbo.plugin.HabboPlugin;
import com.furnibuilder.botprotection.events.OnUserLoginEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BotProtection extends HabboPlugin implements EventListener {


  public void onEnable() {
    initializeEvents();
    log.info("BotProtection PLUGIN - BOT Protection Plugin has loaded!");
  }
  
  public void onDisable() {
    log.info("BotProtection PLUGIN - Stopped BOT Protection Plugin!");
  }
  
  public boolean hasPermission(Habbo habbo, String s) {
    return false;
  }
  
  private void initializeEvents() {
    Emulator.getPluginManager().registerEvents(this, this);
    Emulator.getPluginManager().registerEvents(this, (EventListener)new OnUserLoginEvent());
  }
}
