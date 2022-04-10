package com.somemone.ro11ensmpcore;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.command.NationCommand;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import com.somemone.ro11ensmpcore.command.ConfigCommand;
import com.somemone.ro11ensmpcore.command.WarCommand;
import com.somemone.ro11ensmpcore.config.Config;
import com.somemone.ro11ensmpcore.config.NationTier;
import com.somemone.ro11ensmpcore.config.NationWar;
import com.somemone.ro11ensmpcore.events.InviteListener;
import com.somemone.ro11ensmpcore.events.TimeListener;
import com.somemone.ro11ensmpcore.events.WarListener;
import com.somemone.ro11ensmpcore.file.FileHandler;
import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;
import org.bukkit.scheduler.BukkitRunnable;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public final class Ro11enSmpCore extends JavaPlugin {

    public static Config config;
    public static Economy economy;
    public static File dataFolder;

    private static ArrayList<NationWar> warsUnderDelay = new ArrayList<>();
    private static JavaPlugin currentPlugin;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        handleConfig();

        currentPlugin = this;
        dataFolder = this.getDataFolder();

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        economy = rsp.getProvider();

        setupTowns();

        getServer().getPluginManager().registerEvents(new InviteListener(), this);
        getServer().getPluginManager().registerEvents(new TimeListener(), this);
        getServer().getPluginManager().registerEvents(new WarListener(), this);

        getCommand("ro11ensettings").setExecutor(new ConfigCommand());
        getCommand("war").setExecutor(new WarCommand());

    }

    public void handleConfig() {
        FileConfiguration config = this.getConfig();
        ArrayList<NationTier> two = new ArrayList<>();

        for (String key : config.getConfigurationSection("nation-tiers").getKeys(false)) {
            int minPlayers = config.getInt("nation-tiers." + key + ".min-players");
            int dailyBenefit = config.getInt("nation-tiers." + key + ".daily-benefit");

            NationTier tier = new NationTier(minPlayers, dailyBenefit);
            two.add(tier);
        }


        this.config = new Config( two, config.getInt("max-neutral-players"), config.getInt("days-before-war") );

    }

    public void setupTowns() {
        for (Town town : TownyUniverse.getInstance().getTowns()) {
            if (town.hasNation()) continue;

            Bukkit.broadcastMessage(town.getName());

            NationCommand.newNation(Bukkit.getServer().getPlayer(town.getMayor().getName()), town.getName(), town, true);
        }
    }

    public static void addWar (Nation na, Nation nd) {
        NationWar war = new NationWar(na, nd);

        List<NationWar> wars = FileHandler.getCurrentWars();
        if (wars.contains(war))
            wars.remove(war);
        wars.add(war);

        FileHandler.saveCurrentWars(wars);
    }

    public static boolean isAttackingUnderDelay(Nation nation, Nation victim) {

        List<NationWar> wars = FileHandler.getCurrentWars();
        Bukkit.broadcastMessage("STARTING: " + nation.getName() + ", " + victim.getName());
        for (NationWar war : wars ) {
            Bukkit.broadcastMessage(war.getNationAttacking().getName() + ", " + war.getNationDefending().getName());
            if (war.getNationAttacking().getUUID().equals(nation.getUUID()) && war.getNationDefending().getUUID().equals(victim.getUUID()))
                return true;
        }

        return false;
    }
}
