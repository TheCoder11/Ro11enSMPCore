package somemone.ro11enwar.command;

import com.palmergames.adventure.text.Component;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.object.Nation;
import com.palmergames.bukkit.towny.object.Resident;
import somemone.ro11enwar.Ro11enWar;
import somemone.ro11enwar.config.NationWar;
import somemone.ro11enwar.events.WarStartEvent;
import somemone.ro11enwar.file.FileHandler;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.time.format.DateTimeFormatter;
import java.util.List;

public class WarCommand implements CommandExecutor {

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        if (!(sender instanceof Player)) return false;
        Resident res = TownyUniverse.getInstance().getResident(((Player) sender).getUniqueId());

        assert res != null;
        if (!res.hasNation()) {
            sender.sendMessage(ChatColor.RED + "You have no nation!");
            return true;
        }

        try {

            if (!res.getNation().getKing().equals(res) && Ro11enWar.config.getNeedsKing()) {
                sender.sendMessage(ChatColor.RED + "You are not the leader of the nation! Contact them if you'd like to go to war" );
                return true;
            }

            if (args.length == 0) return false;

            Nation victim = TownyUniverse.getInstance().getNation(args[0]);
            if (victim == null) {
                sender.sendMessage(ChatColor.RED + "Nation could not be found");
                return true;
            }

            String addition = "";

            if (args.length == 2 && args[1].equals("bypass")) {
                res.getNation().addEnemy(victim);
            } else {
                NationWar war = new NationWar(res.getNation(), victim);
                DateTimeFormatter dtf = DateTimeFormatter.ofPattern("hh:mm a, EEE LLL dd");

                List<NationWar> wars = FileHandler.getCurrentWars();
                boolean add = true;
                for (NationWar w : wars)
                    if (w.getNationAttacking().equals(war.getNationAttacking()) && w.getNationDefending().equals(war.getNationDefending()))
                        add = false;

                if (add) {
                    wars.add(war);
                    addition = "The war will start at " + dtf.format(war.getDateTime());
                    Bukkit.getPluginManager().callEvent(new WarStartEvent(war));
                    FileHandler.saveCurrentWars(wars);
                }
            }

            victim.sendMessage(Component.text(ChatColor.GOLD + "War has been declared on you by " + res.getNation().getName() + ". " + addition));

        } catch (Exception e) {
            e.printStackTrace();
        }

        return true;
    }
}
