package com.somemone.ro11ensmpcore.config;

import com.palmergames.bukkit.towny.object.Nation;
import com.somemone.ro11ensmpcore.Ro11enSmpCore;

public class NationWar {

    private Nation nationAttacking;
    private Nation nationDefending;
    private int daysLeft;

    public NationWar(Nation na, Nation nd) {
        nationAttacking = na;
        nationDefending = nd;
        daysLeft = Ro11enSmpCore.config.getDayDelay();
    }

    public NationWar(Nation na, Nation nd, int daysLeft) {
        nationAttacking = na;
        nationDefending = nd;
        this.daysLeft = daysLeft;
    }

    public Nation getNationAttacking() {
        return nationAttacking;
    }

    public Nation getNationDefending() {
        return nationDefending;
    }

    public int getDaysLeft() {
        return daysLeft;
    }

    public void setDaysLeft(int daysLeft) {
        this.daysLeft = daysLeft;
    }
}
