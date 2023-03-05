package surreal.contentcreator.crafttweaker.brackets;

import surreal.contentcreator.crafttweaker.types.CTSoundType;

import static net.minecraft.block.SoundType.*;

public class SoundTypeBracket extends BasicBracket {

    public SoundTypeBracket() {
        super("soundtype");
    }

    public static CTSoundType get(String name) {
        switch (name) {
            case "wood": return new CTSoundType(WOOD);
            case "ground": return new CTSoundType(GROUND);
            case "plant": return new CTSoundType(PLANT);
            case "stone": return new CTSoundType(STONE);
            case "metal": return new CTSoundType(METAL);
            case "glass": return new CTSoundType(GLASS);
            case "cloth": return new CTSoundType(CLOTH);
            case "sand": return new CTSoundType(SAND);
            case "snow": return new CTSoundType(SNOW);
            case "ladder": return new CTSoundType(LADDER);
            case "anvil": return new CTSoundType(ANVIL);
            case "slime": return new CTSoundType(SLIME);
            default: return null;
        }
    }
}
