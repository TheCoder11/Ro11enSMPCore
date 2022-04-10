package com.somemone.ro11ensmpcore.command;

import com.palmergames.adventure.text.Component;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import com.somemone.ro11ensmpcore.Ro11enSmpCore;
import com.somemone.ro11ensmpcore.config.NationWar;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class WarCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) return false;
        Resident res = TownyUniverse.getInstance().getResident(((Player) sender).getUniqueId());

        if (!res.hasNation()) {
            sender.sendMessage(ChatColor.RED + "You have no nation!");
            return true;
        }

        try {

            if (!res.getNation().getKing().equals(res)) {
                sender.sendMessage(ChatColor.RED + "You are not the leader of the nation! Contact them if you'd like to go to war" );
                return true;
            }

            if (args.length == 0) return false;

            Nation victim = TownyUniverse.getInstance().getNation(args[0]);
            if (victim == null) {
                sender.sendMessage(ChatColor.RED + "Nation could not be found");
                return true;
            }

            sender.sendMessage(ChatColor.GOLD + "The war has been declared. You know need to wait for " + Ro11enSmpCore.config.getDayDelay() + " days to start the attack.");
            Ro11enSmpCore.addWar(res.getNation(), victim);
            victim.sendMessage(Component.text(ChatColor.GOLD + "War has been declared on you by " + res.getNation().getName() +
                    ". You have " + Ro11enSmpCore.config.getDayDelay() + " days to prepare."));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
