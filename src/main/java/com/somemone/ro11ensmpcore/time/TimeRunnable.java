package com.somemone.ro11ensmpcore.time;

import com.palmergames.adventure.text.Component;
import com.palmergames.bukkit.towny.exceptions.AlreadyRegisteredException;
import com.somemone.ro11ensmpcore.config.NationWar;
import com.somemone.ro11ensmpcore.file.FileHandler;
import org.bukkit.scheduler.BukkitRunnable;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class TimeRunnable extends BukkitRunnable {
    @Override
    public void run() {
        List<NationWar> wars = FileHandler.getCurrentWars();
        boolean changed = false;

        for (int i = 0; i < wars.size(); i++) {
            if (wars.get(i).getDateTime().isAfter(LocalDateTime.now())) {

                changed = true;

                wars.get(i).getNationAttacking().sendMessage(Component.text("Your nation has now declared war on " + wars.get(i).getNationDefending().getName()));
                wars.get(i).getNationDefending().sendMessage(Component.text("Your nation has been declared upon on by " + wars.get(i).getNationAttacking().getName()));
                try {
                    if (!wars.get(i).getNationAttacking().hasEnemy(wars.get(i).getNationDefending()))
                        wars.get(i).getNationAttacking().addEnemy(wars.get(i).getNationDefending());
                } catch (AlreadyRegisteredException e) {
                    e.printStackTrace();
                }

                wars.remove(i);

            }
        }

        if (changed) {
            FileHandler.saveCurrentWars(wars);
        }

    }
}
