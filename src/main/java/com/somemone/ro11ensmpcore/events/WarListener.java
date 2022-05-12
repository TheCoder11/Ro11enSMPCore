package com.somemone.ro11ensmpcore.events;

import com.palmergames.adventure.text.Component;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.event.NationAddEnemyEvent;
import com.palmergames.bukkit.towny.event.NewDayEvent;
import com.palmergames.bukkit.towny.event.TownInvitePlayerEvent;
import com.palmergames.bukkit.towny.event.nation.toggle.NationToggleNeutralEvent;
import com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.exceptions.TownyException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.somemone.ro11ensmpcore.Ro11enSmpCore;
import com.somemone.ro11ensmpcore.file.FileHandler;
import io.github.townyadvanced.flagwar.events.CellAttackEvent;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;

public class WarListener implements Listener {

    @EventHandler
    public void onNeutralDeclare (NationToggleNeutralEvent event) {
        if (!Ro11enSmpCore.config.isNeutral(event.getNation())) {
            event.getNation().setNeutral(false);
            event.getNation().sendMessage(Component.text(ChatColor.GOLD + "You can't be neutral, due to your player count"));
            event.setCancelled(true);
        }
    }

    @EventHandler
    public void onNewPlayer (TownInvitePlayerEvent event) throws NotRegisteredException {
        if (!event.getInvite().getSender().hasNation()) return;

        if (Ro11enSmpCore.config.getMaxNeutralPlayers() > event.getInvite().getSender().getNation().getNumResidents() + 1 && event.getInvite().getSender().getNation().isNeutral()) {
            event.getInvite().getSender().getNation().setNeutral(false);
            event.getInvite().getSender().getNation().sendMessage(Component.text(ChatColor.GOLD + "Due to adding a new player, you have been " +
                    "set to non-neutral"));
        }

    }

}
