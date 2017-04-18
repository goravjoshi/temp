package com.ubs.opsit.interviews.parser.time;

import static java.lang.Integer.parseInt;

/**
 * Exists purely due to the requirement of representation of time 24:00:00. For
 * other time, joda time LocalTime could be used.
 * 
 * It parses input time and returns a time representation. Presently, only 24
 * hours format is supported. This can be extended to support 12 hour format as
 * well. The granularity of time is till seconds. There is no way to represent
 * milliseconds in {@link Main}. Some of the example as input are,
 * <ul>
 * <li>01:00:78</li>
 * <li>00:00:00</li>
 * <li>13:05:00</li>
 * </ul>
 * 
 *
 */
public class TwentyFourHourFormatParser implements TimeParser {

	private static final String TIME_FORMAT_HELP_MESSAGE = "Time must be in the format HH:MM:SS";
	private static final String HOURS_RANGE_EXCEPTION_MESSAGE = "Hours in time not in correct range of [0 to 24].";
	private static final String MINUTES_RANGE_EXCEPTION_MESSAGE = "Minutes in time not in correct range of [0 to 59].";
	private static final String SECONDS_RANGE_EXCEPTION_MESSAGE = "Seconds in time not in correct range of [0 to 59].";

	public TimeOfDay parse(String inputTime) throws InvalidTimeFormat {
		if (!inputTime.matches("\\d\\d:\\d\\d:\\d\\d")) {
			throw new InvalidTimeFormat(TIME_FORMAT_HELP_MESSAGE);
		}

		String[] tokens = inputTime.split(":");
		int hours = parseHours(tokens[0]);
		int minutes = parseMinutes(tokens[1]);
		int seconds = parseSeconds(tokens[2]);
		return new TimeOfDay(hours, minutes, seconds);
	}

	private static int parseHours(String hours) {
		int v = parseInt(hours);
		if (v< 0 || v > 24)
			throw new InvalidTimeFormat(HOURS_RANGE_EXCEPTION_MESSAGE);
		return v;
	}

	private static int parseMinutes(String mins) {
		int v = parseInt(mins);
		if (v < 0 || v > 59)
			throw new InvalidTimeFormat(MINUTES_RANGE_EXCEPTION_MESSAGE);
		return v;
	}
	
	private static int parseSeconds(String seconds) {
		int v = parseInt(seconds);
		if (v<0 || v > 59)
			throw new InvalidTimeFormat(SECONDS_RANGE_EXCEPTION_MESSAGE);
		return v;
	}	
}
