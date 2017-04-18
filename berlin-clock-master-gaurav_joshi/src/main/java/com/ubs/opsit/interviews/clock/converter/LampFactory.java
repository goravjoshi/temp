package com.ubs.opsit.interviews.clock.converter;

import java.util.stream.IntStream;

class LampFactory {

    Lamp[] getLamps(int switchedOn, Color... color) {
        if (switchedOn > color.length) {
            throw new IllegalArgumentException("Number of lamp switched on can't be greater than total number of lamps");
        } else {
            Lamp[] lamps = new Lamp[color.length];
            IntStream.range(0, switchedOn).forEach(i -> lamps[i] = Lamp.createLampWithStateOn(color[i]));            
            IntStream.range(switchedOn, color.length).forEach(i -> lamps[i] = Lamp.createLampWithStateOff(color[i]));
            return lamps;
        }

    }


}
