package com.ubs.opsit.interviews.clock.converter;

public final class Lamp {
    
    private final Color color;
    private final boolean isOn;

    private Lamp(Color color, boolean isOn) {
        this.color = color;
        this.isOn = isOn;
    }

    public static Lamp createLampWithStateOn(Color color) {
        return new Lamp(color, true);
    }
    
    public static Lamp createLampWithStateOff(Color color) {
        return new Lamp(color, false);
    }
    
    public boolean isOn() {
        return isOn;
    }
    
    public Color getColor() {
        return color;
    }

    @Override
    public String toString() {
        return isOn ? "ON" : "OFF";
    }

}
