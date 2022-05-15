package somemone.ro11enwar.config;

import com.palmergames.bukkit.towny.object.Nation;

import java.util.ArrayList;

public class Config {

    private final int maxNeutralPlayers;
    private final boolean needsKing;

    public Config (int maxNeutralPlayers, boolean needsKing) {
        this.maxNeutralPlayers = maxNeutralPlayers;
        this.needsKing = needsKing;
    }

    public boolean isNeutral (Nation nation) {
        return nation.getNumResidents() <= maxNeutralPlayers;
    }

    public int getMaxNeutralPlayers() {
        return maxNeutralPlayers;
    }

    public boolean getNeedsKing() { return needsKing; }
}
