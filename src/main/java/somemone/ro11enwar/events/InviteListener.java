package somemone.ro11enwar.events;

import com.palmergames.adventure.text.Component;
import com.palmergames.bukkit.towny.TownyUniverse;
import com.palmergames.bukkit.towny.command.NationCommand;
import com.palmergames.bukkit.towny.event.NewTownEvent;
import com.palmergames.bukkit.towny.exceptions.NotRegisteredException;
import com.palmergames.bukkit.towny.object.TownyPermission;
import com.palmergames.bukkit.towny.object.WorldCoord;
import com.palmergames.bukkit.towny.utils.PlayerCacheUtil;
import somemone.ro11enwar.Ro11enWar;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

public class InviteListener implements Listener {

    @EventHandler
    public void onTownCreate (NewTownEvent event) {

        NationCommand.newNation(event.getTown().getMayor().getPlayer(), event.getTown().getName(), event.getTown(), true);

        event.getTown().sendMessage(Component.text(ChatColor.GOLD + "You have a nation now, consisting of only you. You can't" +
                " get rid of it, but you can join other nations to combine your power. You can declare neutrality until your town reaches " + Ro11enWar.config.getMaxNeutralPlayers() +
                " people with /neutral"));

    }

    @EventHandler
    public void onBlockBreak (BlockBreakEvent event) {
        if (!WorldCoord.parseWorldCoord(event.getBlock()).hasTownBlock()) return;

        if (PlayerCacheUtil.getCachePermission(event.getPlayer(), event.getBlock().getLocation(), event.getBlock().getType(), TownyPermission.ActionType.DESTROY)) {
            if (TownyUniverse.getInstance().hasResident(event.getPlayer().getName())) {
                try {
                    if (!WorldCoord.parseWorldCoord(event.getBlock()).getTownBlock().getTown().hasNation()) {
                        event.getPlayer().sendMessage(ChatColor.GOLD + "Make a nation before doing anything");
                        event.setCancelled(true);
                    }
                } catch (NotRegisteredException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    @EventHandler
    public void onBlockPlace (BlockPlaceEvent event) {

        if (!WorldCoord.parseWorldCoord(event.getBlock()).hasTownBlock()) return;

        if (PlayerCacheUtil.getCachePermission(event.getPlayer(), event.getBlock().getLocation(), event.getBlock().getType(), TownyPermission.ActionType.BUILD)) {
            if (TownyUniverse.getInstance().hasResident(event.getPlayer().getName())) {
                try {
                    if (!WorldCoord.parseWorldCoord(event.getBlock()).getTownBlock().getTown().hasNation()) {
                        event.getPlayer().sendMessage(ChatColor.GOLD + "Make a nation before doing anything");
                        event.setCancelled(true);
                    }
                } catch (NotRegisteredException e) {
                    e.printStackTrace();
                }
            }
        }
    }

}
