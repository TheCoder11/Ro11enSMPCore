package somemone.ro11enwar;

import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.command.NationCommand;
import com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Town;
import somemone.ro11enwar.command.WarCommand;
import somemone.ro11enwar.config.Config;
import somemone.ro11enwar.config.NationWar;
import somemone.ro11enwar.events.InviteListener;
import somemone.ro11enwar.events.WarListener;
import somemone.ro11enwar.file.FileHandler;
import somemone.ro11enwar.time.TimeRunnable;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import net.milkbowl.vault.economy.Economy;

import java.io.File;
import java.util.List;
import java.util.logging.Level;

public final class Ro11enWar extends JavaPlugin {

    public static Config config;
    public static Economy economy;
    public static File dataFolder;

    @Override
    public void onEnable() {
        this.saveDefaultConfig();
        handleConfig();

        dataFolder = this.getDataFolder();

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        economy = rsp.getProvider();

        new TimeRunnable().runTaskTimer(this, 0L, 1200L);

        setupTowns();

        getServer().getPluginManager().registerEvents(new InviteListener(), this);
        getServer().getPluginManager().registerEvents(new WarListener(), this);

        getCommand("war").setExecutor(new WarCommand());

    }

    public void handleConfig() {
        FileConfiguration config = this.getConfig();

        this.config = new Config( config.getInt("max-neutral-players"), config.getBoolean("needs-king") );

    }

    public void setupTowns() {
        for (Town town : TownyUniverse.getInstance().getTowns()) {
            if (town.hasNation()) continue;

            try {
                NationCommand.newNation(town.getName(), town);
            } catch (AlreadyRegisteredException e) {
                e.printStackTrace();
            } catch (NotRegisteredException e) {
                e.printStackTrace();
            }

            getServer().getLogger().log(Level.INFO, "Nation created for town " + town.getName());
        }
    }

}
