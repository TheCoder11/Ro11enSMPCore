package somemone.ro11enwar.config;

import com.palmergames.bukkit.towny.object.Nation;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

public class NationWar {

    private Nation nationAttacking;
    private Nation nationDefending;
    private LocalDateTime datetime;

    public NationWar(Nation na, Nation nd) {
        nationAttacking = na;
        nationDefending = nd;

        if (LocalDate.now().getDayOfWeek().equals(DayOfWeek.SATURDAY) || LocalDate.now().getDayOfWeek().equals(DayOfWeek.SUNDAY)) {
            datetime = LocalDateTime.now().plusHours(6L);
        } else {
            LocalDate now = LocalDate.now().plusDays(1L);
            while (!now.getDayOfWeek().equals(DayOfWeek.SATURDAY)) { // adds to days until its Saturday
                now.plusDays(1L);
            }
            datetime = LocalDateTime.of(now, LocalTime.of(9, 0, 0));
        }


    }

    public NationWar(Nation na, Nation nd, LocalDateTime dt) {
        nationAttacking = na;
        nationDefending = nd;
        datetime = dt;
    }

    public Nation getNationAttacking() {
        return nationAttacking;
    }

    public Nation getNationDefending() {
        return nationDefending;
    }

    public LocalDateTime getDateTime() {
        return datetime;
    }
}
