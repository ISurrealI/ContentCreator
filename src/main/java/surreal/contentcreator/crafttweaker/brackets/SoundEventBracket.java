package surreal.contentcreator.crafttweaker.brackets;

import surreal.contentcreator.crafttweaker.types.CTSoundEvent;

public class SoundEventBracket extends BasicBracket {

    public SoundEventBracket() {
        super("soundevent", "sound");
    }

    public static CTSoundEvent get(String name) {
        return CTSoundEvent.get(name);
    }
}
