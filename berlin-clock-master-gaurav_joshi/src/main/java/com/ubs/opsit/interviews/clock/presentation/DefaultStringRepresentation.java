package com.ubs.opsit.interviews.clock.presentation;

import static com.ubs.opsit.interviews.clock.converter.Color.RED;
import static com.ubs.opsit.interviews.clock.converter.Color.YELLOW;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import com.ubs.opsit.interviews.clock.converter.BerlinTimeInstance;
import com.ubs.opsit.interviews.clock.converter.Color;
import com.ubs.opsit.interviews.clock.converter.Lamp;

public class DefaultStringRepresentation implements Representation {

    private static final String NEW_LINE = "\n";    
    private static Map<Color, String> colorToFirstLetter = new HashMap<>();
    private static String OFF = "O";

    static {
        colorToFirstLetter.put(RED, "R");
        colorToFirstLetter.put(YELLOW, "Y");
    }

    public DefaultStringRepresentation() {}

    public String represent(BerlinTimeInstance instance) {
        List<Lamp[]> rows = instance.getRows();
        return rows.stream().map(lamps -> Arrays.stream(lamps)
                .map(this::getLampPresentation).collect(Collectors.joining()))
                .collect(Collectors.joining(NEW_LINE));
    }

    private String getLampPresentation(Lamp lamp) {
        ensureThatLampColorIsSupported(lamp); 
        if (lamp.isOn()) {
            return colorToFirstLetter.get(lamp.getColor());
        } else {
            return OFF;
        }
    }

    private void ensureThatLampColorIsSupported(Lamp lamp) {
        if(!colorToFirstLetter.containsKey(lamp.getColor())) {
            throw new IllegalArgumentException();
        }
    }

}
