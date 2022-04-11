package com.somemone.ro11ensmpcore.config;

import com.palmergames.bukkit.towny.object.Nation;

import java.util.ArrayList;

public class Config {

    private final ArrayList<NationTier> tiers;
    private final int maxNeutralPlayers;
    private final int dayDelay;
    private final boolean needsKing;

    public Config (ArrayList<NationTier> tiers, int maxNeutralPlayers, int dayDelay, boolean needsKing) {
        this.tiers = tiers;
        this.maxNeutralPlayers = maxNeutralPlayers;
        this.dayDelay = dayDelay;
        this.needsKing = needsKing;
    }

    public ArrayList<NationTier> getTiers() {
        return tiers;
    }

    public int getDailyPayment (Nation nation) {
        int numPlayers = nation.getNumResidents();

        NationTier current = tiers.get(0);
        for (NationTier found : tiers) {

            if (found.getMinPlayers() > numPlayers) break;
            if (current.getMinPlayers() < found.getMinPlayers())
                current = found;

        }

        return current.getDailyPayment();
    }

    public boolean isNeutral (Nation nation) {
        return nation.getNumResidents() <= maxNeutralPlayers;
    }

    public int getDayDelay() {
        return dayDelay;
    }

    public int getMaxNeutralPlayers() {
        return maxNeutralPlayers;
    }

    public boolean getNeedsKing() { return needsKing; }
}
