package com.ubs.opsit.interviews.parser.time;

/**
 * Parses the input string representation to 24 hour format.  
 *
 */
public interface TimeParser {
    public TimeOfDay parse(String time) throws InvalidTimeFormat;
}
