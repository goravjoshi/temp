package com.ubs.opsit.interviews.clock.converter;

import static com.ubs.opsit.interviews.clock.converter.Color.RED;
import static com.ubs.opsit.interviews.clock.converter.Color.YELLOW;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

import com.ubs.opsit.interviews.parser.time.TimeOfDay;

public class BerlinTimeConverter {

    private final LampFactory factory;
    private final List<Function<TimeOfDay, Lamp[]>> berlinRowsProcessors;

    public BerlinTimeConverter() {
        this.factory = new LampFactory();
        berlinRowsProcessors = Arrays.asList(
                getTopRow(),
                getTopHoursRow(),
                getBottomHoursRow(),
                getTopMinutesRow(),
                getBottomMinutesRow());
    }

    public BerlinTimeInstance convert(TimeOfDay aTime) {
        List<Lamp[]> rows = berlinRowsProcessors.stream().map(f -> f.apply(aTime)).collect(Collectors.toList());
        return () -> rows;
    }

    private Function<TimeOfDay, Lamp[]> getTopRow() {
        return aTime -> {
            int second = aTime.getSecond();
            int switchedOn = isEven(second) ? 1 : 0;
            return factory.getLamps(switchedOn, YELLOW);
        };
    }

    private boolean isEven(int number) {        
        return number % 2 == 0;
    }

    private Function<TimeOfDay, Lamp[]> getTopHoursRow() {
        return aTime -> {
            int hour = aTime.getHour();
            int numberOfConsecutiveOnLamps = hour / 5;

            return factory.getLamps(numberOfConsecutiveOnLamps, RED, RED, RED, RED);
        };
    }

    private Function<TimeOfDay, Lamp[]> getBottomHoursRow() {
        return aTime -> {
            int hour = aTime.getHour();
            int numberOfConsecutiveOnLamps = hour % 5;

            return factory.getLamps(numberOfConsecutiveOnLamps, RED, RED, RED, RED);
        };
    }

    private Function<TimeOfDay, Lamp[]> getTopMinutesRow() {
        return aTime -> {
            int minutes = aTime.getMinute();

            int numberOfConsecutiveOnLamps = minutes / 5;

            return factory.getLamps(numberOfConsecutiveOnLamps, YELLOW, YELLOW, RED, YELLOW, YELLOW, RED, YELLOW, YELLOW, RED,
                    YELLOW, YELLOW);
        };
    }

    private Function<TimeOfDay, Lamp[]> getBottomMinutesRow() {
        return aTime -> {
            int minutes = aTime.getMinute();
            int numberOfConsecutiveOnLamps = minutes % 5;

            return factory.getLamps(numberOfConsecutiveOnLamps, YELLOW, YELLOW, YELLOW, YELLOW);
        };
    }

}
