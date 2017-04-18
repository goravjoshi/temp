package com.ubs.opsit.interviews.clock.converter;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.IsEqual.equalTo;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import com.ubs.opsit.interviews.parser.time.TimeOfDay;
import com.ubs.opsit.interviews.parser.time.TwentyFourHourFormatParser;


public class BerlinTimeConverterTest {

    @Test
    public void should_represent_midnight_properly() {
        TimeOfDay midnight = parse("00:00:00");
        BerlinTimeConverter converter = new BerlinTimeConverter();

        BerlinTimeInstance instance = converter.convert(midnight);
        List<Lamp[]> rows = instance.getRows();

        assertThat(rows.size(), is(equalTo(5)));
        String expected = "ON\n" 
        + "OFF|OFF|OFF|OFF\n" 
        + "OFF|OFF|OFF|OFF\n" 
        + "OFF|OFF|OFF|OFF|OFF|OFF|OFF|OFF|OFF|OFF|OFF\n"
        + "OFF|OFF|OFF|OFF";

        assertEquals(expected, canonicalize(rows));
    }
    
    @Test
    public void should_properly_represent_middle_of_afternoon() {
        TimeOfDay afternoon = parse("13:17:01");
        BerlinTimeConverter converter = new BerlinTimeConverter();

        BerlinTimeInstance instance = converter.convert(afternoon);
        List<Lamp[]> rows = instance.getRows();

        assertThat(rows.size(), is(equalTo(5)));
        String expected = "OFF\n" 
        + "ON|ON|OFF|OFF\n" 
        + "ON|ON|ON|OFF\n" 
        + "ON|ON|ON|OFF|OFF|OFF|OFF|OFF|OFF|OFF|OFF\n"
        + "ON|ON|OFF|OFF";

        assertEquals(expected, canonicalize(rows));
    }   
    

    private String canonicalize(List<Lamp[]> rows) {
        return rows.stream().map(lamp -> 
            Arrays.stream(lamp).map(Lamp::toString).collect(Collectors.joining("|"))   
        ).collect(Collectors.joining("\n"));
    }
    
    private TimeOfDay parse(String time) {
        return new TwentyFourHourFormatParser().parse(time);
    }

}
