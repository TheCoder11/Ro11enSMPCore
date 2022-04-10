package com.somemone.ro11ensmpcore.file;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException;
import com.palmergames.bukkit.towny.object.Nation;
import com.somemone.ro11ensmpcore.Ro11enSmpCore;
import com.somemone.ro11ensmpcore.config.NationWar;
import org.bukkit.Bukkit;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileHandler {

    public static List<NationWar> getCurrentWars() {
        File file = new File (Ro11enSmpCore.dataFolder, "wars.csv");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            return new ArrayList<>();
        }

        ArrayList<NationWar> wars = new ArrayList<>();
        BufferedReader reader;
        try {
            reader = new BufferedReader(new FileReader(file));
            String line = reader.readLine();
            while (line != null) {
                String[] nations = line.split(",");
                if (nations.length != 3) continue;

                Bukkit.broadcastMessage(nations[0] + " " + nations[1] + " " + nations[2]);

                Nation attack = TownyAPI.getInstance().getNation( UUID.fromString(nations[0]) );
                Nation defense = TownyAPI.getInstance().getNation( UUID.fromString(nations[1]) );

                Bukkit.broadcastMessage(attack.getName() + " " + defense.getName());

                int daysLeft = Integer.parseInt( nations[2] );

                wars.add( new NationWar(attack, defense, daysLeft) );

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wars;

    }

    public static void saveCurrentWars (List<NationWar> wars) {
        File file = new File (Ro11enSmpCore.dataFolder, "wars.csv");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter writer = new FileWriter(file, false);

            for (NationWar war : wars) {
                writer.write( war.getNationAttacking().getUUID() + "," +
                        war.getNationDefending().getUUID() + "," + war.getDaysLeft() );
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void newWarsDay () throws AlreadyRegisteredException {
        List<NationWar> wars = getCurrentWars();
        for (int i = 0; i < wars.size(); i++) {

            int days = wars.get(i).getDaysLeft();
            Bukkit.broadcastMessage(days + "");
            days--;
            if (days == 0) {
                NationWar war = wars.remove(i);
                i--;

                war.getNationAttacking().addEnemy(war.getNationDefending());

                continue;
            }
            wars.get(i).setDaysLeft(days);

        }
        saveCurrentWars(wars);
    }
}
