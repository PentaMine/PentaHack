package net.minecraft.pentahack.settings;

public class KeyBindSetting extends Setting {
    public int key;
    public boolean pending;

    public KeyBindSetting(String name, int value) {
        this.name = name;
        this.key = value;
        this.pending = false;
    }

    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

}
