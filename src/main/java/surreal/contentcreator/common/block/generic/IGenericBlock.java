package surreal.contentcreator.common.block.generic;

import net.minecraft.block.Block;
import surreal.contentcreator.types.CTSoundType;

import java.util.List;

public interface IGenericBlock {
    void setSoundType(CTSoundType soundType);
    default void create(List<Block> listToAdd) {}
}
