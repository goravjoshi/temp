package com.ubs.opsit.interviews.clock;

import com.ubs.opsit.interviews.TimeConverter;
import com.ubs.opsit.interviews.clock.converter.BerlinTimeConverter;
import com.ubs.opsit.interviews.clock.converter.BerlinTimeInstance;
import com.ubs.opsit.interviews.clock.presentation.Representation;
import com.ubs.opsit.interviews.parser.time.TimeOfDay;
import com.ubs.opsit.interviews.parser.time.TimeParser;

public class LocalTimeToBerlinClockConverter implements TimeConverter {    
    
    private final TimeParser timeParser;
    private final BerlinTimeConverter berlinTimeConverter;
    private final Representation representation;    

    public LocalTimeToBerlinClockConverter(TimeParser timeParser, BerlinTimeConverter berlinTimeConverter,
            Representation representation) {
        this.timeParser = timeParser;
        this.berlinTimeConverter = berlinTimeConverter;
        this.representation = representation;
    }

    @Override
    public String convertTime(String aTime) {
        TimeOfDay timeOfTheDay = timeParser.parse(aTime);
        BerlinTimeInstance instance = berlinTimeConverter.convert(timeOfTheDay);
        return representation.represent(instance);
    }

}
