package com.somemone.ro11ensmpcore.command;

import com.somemone.ro11ensmpcore.Ro11enSmpCore;
import com.somemone.ro11ensmpcore.config.NationTier;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;

public class ConfigCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (args.length == 0) return false;

        if (args[0].equals("neutrallimit")) {
            sender.sendMessage("Limit: " + Ro11enSmpCore.config.getMaxNeutralPlayers());
        }
        if (args[0].equals("coping")) {
            for (NationTier tier : Ro11enSmpCore.config.getTiers()) {
                sender.sendMessage("Min Players: " + tier.getMinPlayers());
                sender.sendMessage("Daily Payout: " + tier.getDailyPayment());
            }
        }
        if (args[0].equals("daydelay")) {
            sender.sendMessage("Delay (Day): " + Ro11enSmpCore.config.getDayDelay());
        }

        return true;
    }

}
