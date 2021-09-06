package net.minecraft.pentahack.settings;

public class NumberSetting extends Setting {
    public double value, minimum, maximum;

    public NumberSetting(String name, double value, double minimum, double maximum) {
        this.name = name;
        this.value = value;
        this.minimum = minimum;
        this.maximum = maximum;

    }

    public double getValue() {
        return value;
    }

    public void setValue(double value) {
        this.value = value;
    }

    public double getMinimum() {
        return minimum;
    }

    public void setMinimum(double minimum) {
        this.minimum = minimum;
    }

    public double getMaximum() {
        return maximum;
    }

    public void setMaximum(double maximum) {
        this.maximum = maximum;
    }
}
