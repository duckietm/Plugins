package com.eu.habbo.funcommands.eventloader;
import lombok.extern.slf4j.Slf4j;
import com.eu.habbo.Emulator;

@Slf4j
public class LoadTexts {
    public static void loadTexts() {
        try {
            Emulator.getTexts().register("commands.description.cmd_slime", ":slijm <username>");
            Emulator.getTexts().register("fun.cmd_slime.keys", "slijm;slime");
            Emulator.getTexts().register("fun.cmd_slime.throws", "* Gooit slijm in de richting van %username%s *");
            Emulator.getTexts().register("fun.cmd_slime.missed", "* Heleaas mis, %username%s heeft mazzel *");
            Emulator.getTexts().register("fun.cmd_slime.slimed", "* Wordt bedekt met slijm door %username% *");
            Emulator.getTexts().register("commands.description.cmd_hug", ":knuffel <username>");
            Emulator.getTexts().register("fun.cmd_hug.keys", "knuffel;hug");
            Emulator.getTexts().register("fun.hugmessages.huggedperson", "* Je krijgt een dikke knuffel van %hugger% *");
            Emulator.getTexts().register("fun.hugmessages.hugger", "* Knuffelt %huggedperson% met passie *");
            Emulator.getTexts().register("fun.hugmessages.tofar", "%huggedperson% is te ver om te knuffelen. Kom wat dichterbij en probeer het opnieuw!");
            Emulator.getTexts().register("commands.description.cmd_nuke", ":nuke <username>");
            Emulator.getTexts().register("fun.cmd_nuke.keys", "nuke;explode");
            Emulator.getTexts().register("fun.nuke.self", "Je kunt jezelf niet opblazen, gekkie!");
            Emulator.getTexts().register("fun.nuke.action", "* Lanceert een atoombom naar %username% *");
            Emulator.getTexts().register("fun.nuke.nuked", "* %username% wordt vernietigd *");
            Emulator.getTexts().register("commands.description.cmd_afk", ":afk");
            Emulator.getTexts().register("fun.cmd_afk.keys", "brb;afk;");
            Emulator.getTexts().register("fun.cmd_afk.afk", "* %username% is nu afwezig! *");
            Emulator.getTexts().register("fun.cmd_afk.back", "* %username% is nu terug! *");
            Emulator.getTexts().register("fun.cmd_afk.time", "* %username% is nu al %time% minuten weg *");
            Emulator.getTexts().register("fun.cmd_tptome.keys", "tptome;tp");
            Emulator.getTexts().register("commands.description.cmd_tptome", ":tptome <username>");
            Emulator.getTexts().register("fun.error.not_found", "Kan de gebruiker %user% niet vinden.");
            Emulator.getTexts().register("fun.error.tp_self", "Je kunt jezelf niet teleporteren");
            Emulator.getTexts().register("commands.success.cmd_tptome.tptome", "* Teleports %user% naar %gender_name% *");
            Emulator.getTexts().register("fun.cmd_superpush.keys", "spush;superpush");
            Emulator.getTexts().register("fun.cmd_superpull.keys", "spull;superpull");
        }
        catch (Exception ex)
        {
            log.error("Caught exception", ex);
        }
    }
}
