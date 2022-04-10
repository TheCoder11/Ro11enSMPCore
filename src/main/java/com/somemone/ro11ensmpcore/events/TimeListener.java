package com.somemone.ro11ensmpcore.events;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.event.NewDayEvent;
import com.palmergames.bukkit.towny.object.Nation;
import com.somemone.ro11ensmpcore.Ro11enSmpCore;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class TimeListener implements Listener {

    @EventHandler
    public void moneyListener (NewDayEvent event) {
        for (Nation nation : TownyUniverse.getInstance().getNations()) {
            double currentBalance = nation.getAccount().getHoldingBalance(false);
            int bankMoney = Ro11enSmpCore.config.getDailyPayment(nation);

            nation.getAccount().setBalance(currentBalance + (double) bankMoney, "Ro11en Nation Bonus");
        }
    }
}
