package com.ubs.opsit.interviews.parser.time;

import static org.apache.commons.lang.builder.EqualsBuilder.reflectionEquals;
import static org.apache.commons.lang.builder.HashCodeBuilder.reflectionHashCode;

/**
 * An Immutable time of the day representation. This class can be used as Map keys and objects of this class
 * can be compared for equality with other object of the class.
 *
 */
public final class TimeOfDay {

	private final int hours;
	private final int minutes;
	private final int seconds;

	public TimeOfDay(int hours, int minutes, int seconds) {
		this.hours = hours;
		this.minutes = minutes;
		this.seconds = seconds;
	}

	public int getHour() {
		return hours;
	}

	public int getMinute() {
		return minutes;
	}

	public int getSecond() {
		return seconds;
	}

	@Override
	public int hashCode() {
		return reflectionHashCode(this);
	}

	@Override
	public boolean equals(Object obj) {
		return reflectionEquals(this, obj);
	}
}
