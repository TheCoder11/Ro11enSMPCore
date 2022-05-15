package somemone.ro11enwar.file;

import com.palmergames.bukkit.towny.TownyAPI;
import com.palmergames.bukkit.towny.object.Nation;
import somemone.ro11enwar.Ro11enWar;
import somemone.ro11enwar.config.NationWar;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class FileHandler {

    public static List<NationWar> getCurrentWars() {
        File file = new File (Ro11enWar.dataFolder, "wars.csv");
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

                Nation attack = TownyAPI.getInstance().getNation( UUID.fromString(nations[0]) );
                Nation defense = TownyAPI.getInstance().getNation( UUID.fromString(nations[1]) );

                LocalDateTime datetime = LocalDateTime.parse(nations[2], DateTimeFormatter.ISO_DATE_TIME);

                wars.add( new NationWar(attack, defense, datetime) );

                line = reader.readLine();
            }
            reader.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

        return wars;

    }

    public static void saveCurrentWars (List<NationWar> wars) {
        File file = new File (Ro11enWar.dataFolder, "wars.csv");
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        try {
            FileWriter writer = new FileWriter(file, false);

            writer.write("");

            for (NationWar war : wars) {
                writer.append( war.getNationAttacking().getUUID() + "," +
                        war.getNationDefending().getUUID() + "," + war.getDateTime().format(DateTimeFormatter.ISO_DATE_TIME) + "\n" );
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
