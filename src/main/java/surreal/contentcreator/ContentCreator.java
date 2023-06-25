package surreal.contentcreator;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLConstructionEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import surreal.contentcreator.helpers.CTHelper;
import surreal.contentcreator.crafttweaker.RegistryManager;

@Mod(modid = ContentCreator.MODID, name = "@MODNAME@", version = "@MODVERSION@", dependencies = "after:crafttweaker")
public class ContentCreator {

    public static final String MODID = "@MODID@";

    @Mod.EventHandler
    public void construction(FMLConstructionEvent event) {
        if (Loader.isModLoaded("crafttweaker")) MinecraftForge.EVENT_BUS.register(this);
    }

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {

        // Integration
        if (Loader.isModLoaded("crafttweaker")) CTHelper.registerCT();
    }

    // Crafttweaker Events
    @SubscribeEvent
    public void registerBlocks(RegistryEvent.Register<Block> event) { RegistryManager.INSTANCE.registerBlocks(event.getRegistry()); }

    @SubscribeEvent
    public void registerItems(RegistryEvent.Register<Item> event) { RegistryManager.INSTANCE.registerItems(event.getRegistry()); }

    @SubscribeEvent
    public void registerSounds(RegistryEvent.Register<SoundEvent> event) { RegistryManager.INSTANCE.registerSounds(event.getRegistry()); }
}
