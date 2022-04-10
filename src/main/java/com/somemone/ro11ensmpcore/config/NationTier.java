package com.somemone.ro11ensmpcore.config;

public class NationTier {
    private int minPlayers;
    private int dailyPayment;

    public NationTier(int minPlayers, int dailyPayment) {
        this.minPlayers = minPlayers;
        this.dailyPayment = dailyPayment;
    }

    public int getDailyPayment() {
        return dailyPayment;
    }

    public int getMinPlayers() {
        return minPlayers;
    }
}
