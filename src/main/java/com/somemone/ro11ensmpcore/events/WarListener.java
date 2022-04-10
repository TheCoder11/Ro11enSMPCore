package com.somemone.ro11ensmpcore.events;

import com.palmergames.adventure.text.Component;
import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.event.NationAddEnemyEvent;
import com.palmergames.bukkit.towny.event.NewDayEvent;
import com.palmergames.bukkit.towny.event.nation.toggle.NationToggleNeutralEvent;
import com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException;
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
    public void onNewDay (NewDayEvent event) throws AlreadyRegisteredException {
        FileHandler.newWarsDay();
    }

}