package com.ubs.opsit.interviews.parser.time;

import static org.junit.Assert.assertThat;

import org.hamcrest.core.IsEqual;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TimeParserTest {
	
	@Rule public ExpectedException expectedException = ExpectedException.none();

	@Test
	public void testCorrectParsing() throws Exception {
		assertExpected("00:00:00", new TimeOfDay(0, 0, 0));
		assertExpected("01:37:01", new TimeOfDay(1, 37, 1));
		assertExpected("15:34:00", new TimeOfDay(15, 34, 0));
		assertExpected("00:01:01", new TimeOfDay(0, 1, 1));		
	}
	
	@Test
	public void invalidTimeWithHourOutsideRangeThrowsException() throws Exception {
		expectedException.expect(InvalidTimeFormat.class);
		expectedException.expectMessage("Hours in time not in correct range of [0 to 24].");
		parse("25:00:00");
	}
	
	@Test
	public void invalidTimeWithMinutesOutsideRangeThrowsException() throws Exception {
		expectedException.expect(InvalidTimeFormat.class);
		expectedException.expectMessage("Minutes in time not in correct range of [0 to 59].");
		parse("00:69:00");
	}
	
	@Test
	public void invalidTimeWithSecondsOutsideRangeThrowsException() throws Exception {
		expectedException.expect(InvalidTimeFormat.class);
		expectedException.expectMessage("Seconds in time not in correct range of [0 to 59].");
		parse("13:04:67");
	}	
	
	@Test
	public void testSpecialCaseParsing() throws Exception {
		assertExpected("24:00:00", new TimeOfDay(24, 0, 0));
	}	
	

	private void assertExpected(String aTime, TimeOfDay timeOfTheDay) {
		TimeOfDay time = parse(aTime);
		assertThat(time, IsEqual.equalTo(timeOfTheDay));
	}
	
	private TimeOfDay parse(String time) {
	    return new TwentyFourHourFormatParser().parse(time);
	}

}
