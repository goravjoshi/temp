package com.ubs.opsit.interviews.clock.presentation;

import static java.util.Arrays.asList;
import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import com.ubs.opsit.interviews.clock.converter.BerlinTimeInstance;
import com.ubs.opsit.interviews.clock.converter.Color;
import com.ubs.opsit.interviews.clock.converter.Lamp;

public class DefaultStringRepresentationTest {
    
    @Rule public ExpectedException ee = ExpectedException.none();

    @Test
    public void should_represent_a_time_in_berlin_clock_propertly() {
        BerlinTimeInstance instance = createBerlinInstance(
                lampRow(lamp(Color.RED, true)),
                lampRow(lamp(Color.RED, true), lamp(Color.RED, true)));
        
        String expected = "R\n" +
                "RR";

        Representation representation = new DefaultStringRepresentation();
        
        assertEquals(expected, representation.represent(instance));
    }
    
    @Test
    public void should_represent_berlin_instance_with_off_lamps() throws Exception {
        BerlinTimeInstance instance = createBerlinInstance(
                lampRow(lamp(Color.RED, true)),
                lampRow(lamp(Color.RED, false), lamp(Color.RED, true)));
        String expected = "R\n" +
                "OR";
        
        Representation representation = new DefaultStringRepresentation();
        
        assertEquals(expected, representation.represent(instance));
    }
    
    @Test
    public void should_throw_exception_for_unsupported_colored_lamp() throws Exception {
        BerlinTimeInstance instance = createBerlinInstance(
                lampRow(lamp(Color.BLACK, true)),
                lampRow(lamp(Color.RED, true), lamp(Color.RED, true)));
        
        ee.expect(IllegalArgumentException.class);
        Representation representation = new DefaultStringRepresentation();
        representation.represent(instance);
    }
    
    @Test
    public void should_handle_empty_berlin_instance() throws Exception {        
        BerlinTimeInstance instance = createBerlinInstance(); 
        
        Representation representation = new DefaultStringRepresentation();

        assertEquals("", representation.represent(instance));
    }
    

    private BerlinTimeInstance createBerlinInstance(Lamp[]... rows) {
        return () -> asList(rows);
    }

    private Lamp lamp(Color color, boolean isOn) {
        return isOn ? Lamp.createLampWithStateOn(color) : Lamp.createLampWithStateOff(color);
    }

    private Lamp[] lampRow(Lamp... lamps) {
        return lamps;
    }

}
