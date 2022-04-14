package surreal.contentcreator.util;

public class TintColor {
    private final int tintIndex, value;

    public TintColor(int tint, int value) {
        this.tintIndex = tint;
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public int getTintIndex() {
        return tintIndex;
    }
}
