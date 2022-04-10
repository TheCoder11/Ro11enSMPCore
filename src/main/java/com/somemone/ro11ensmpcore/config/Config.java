package com.somemone.ro11ensmpcore.config;

import com.palmergames.bukkit.towny.object.Nation;

import java.util.ArrayList;

public class Config {

    private ArrayList<NationTier> tiers;
    private int maxNeutralPlayers;
    private int dayDelay;

    public Config (ArrayList<NationTier> tiers, int maxNeutralPlayers, int dayDelay) {
        this.tiers = tiers;
        this.maxNeutralPlayers = maxNeutralPlayers;
        this.dayDelay = dayDelay;
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
}
