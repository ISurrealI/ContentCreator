package surreal.contentcreator.crafttweaker;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.registries.IForgeRegistry;

import java.util.ArrayList;
import java.util.List;

public class RegistryManager {

    public static RegistryManager INSTANCE;

    private List<Block> BLOCKS;
    private List<Item> ITEMS;
    private List<SoundEvent> SOUNDS;

    public RegistryManager() {}

    public Block add(String id, Block block) {
        if (BLOCKS == null) BLOCKS = new ArrayList<>();
        BLOCKS.add(block.setRegistryName(id));

        return block;
    }

    public Item add(String id, Item item) {
        if (ITEMS == null) ITEMS = new ArrayList<>();
        ITEMS.add(item.setRegistryName(id));

        return item;
    }

    public SoundEvent add(SoundEvent soundEvent) {
        if (SOUNDS == null) SOUNDS = new ArrayList<>();
        SOUNDS.add(soundEvent);

        return soundEvent;
    }

    public void registerBlocks(IForgeRegistry<Block> registry) {
        if (BLOCKS != null) BLOCKS.forEach(registry::register);
    }

    public void registerItems(IForgeRegistry<Item> registry) {
        if (ITEMS != null) ITEMS.forEach(registry::register);
    }

    public void registerSounds(IForgeRegistry<SoundEvent> registry) {
        if (SOUNDS != null) SOUNDS.forEach(registry::register);
    }
}
