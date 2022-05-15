package somemone.ro11enwar.events;

import somemone.ro11enwar.config.NationWar;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

public class WarStartEvent extends Event {

    private static final HandlerList handlers = new HandlerList();

    @Override
    public HandlerList getHandlers() {
        return handlers;
    }

    private NationWar war;

    public WarStartEvent(NationWar war) {
        this.war = war;
    }

    public NationWar getWar() {
        return war;
    }
}
