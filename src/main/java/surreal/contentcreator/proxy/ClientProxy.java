package surreal.contentcreator.proxy;

import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelBakery;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import surreal.contentcreator.ModConfig;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.client.fluid.CustomFluidStateMapper;
import surreal.contentcreator.common.block.BlockBase;
import surreal.contentcreator.common.item.ItemBase;
import surreal.contentcreator.util.CTUtil;
import surreal.contentcreator.util.GeneralUtil;
import surreal.contentcreator.util.TintColor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Mod.EventBusSubscriber(modid = ModValues.MODID)
public class ClientProxy extends CommonProxy {
    public static final List<ResourceLocation> fluidTextures = new ArrayList<>();
    private static final List<TintColor> DEFAULTITEMCOLOR = Collections.singletonList(new TintColor(0, 0xFFFFFF));

    public static final IItemColor ITEMCOLOR = (ItemStack stack, int index) -> {
        if (stack.getItem() instanceof ItemBase) {
            ItemBase item = (ItemBase) stack.getItem();
            if (item.COLOR == null) return 0xFFFFFF;

            for (TintColor tintColor : item.COLOR.getOrDefault(stack.getMetadata(), DEFAULTITEMCOLOR)) {
                if (tintColor.getTintIndex() == index) return tintColor.getValue();
            }
        }

        return 0xFFFFFF;
    };

    public static final IBlockColor BLOCKCOLOR = (state, worldIn, pos, tintIndex) -> {
        if (state.getBlock() instanceof BlockBase) {
            BlockBase block = (BlockBase) state.getBlock();
            if (block.COLOR_CHECK != null) return block.COLOR_CHECK.colorMultiplier(CraftTweakerMC.getBlockState(state), worldIn instanceof World ? CraftTweakerMC.getIWorld((World) worldIn) : null, CraftTweakerMC.getIBlockPos(pos), tintIndex);
        }

        return 0xFFFFFF;
    };

    @Override
    public void init(FMLInitializationEvent event) {
        for (ItemBase item : CommonProxy.ITEMS) {
            Minecraft.getMinecraft().getItemColors().registerItemColorHandler(ITEMCOLOR, item);
        }

        for (Block block : CommonProxy.BLOCKS) {
            if (block instanceof BlockBase) Minecraft.getMinecraft().getBlockColors().registerBlockColorHandler(BLOCKCOLOR, block);
        }
    }

    private static void registerSprites(TextureMap map) {
        for (ResourceLocation location : fluidTextures) {
            map.registerSprite(location);
        }
    }

    private static void registerMappers() {
        for (Fluid fluid : CommonProxy.FLUIDS) {
            registerFluidMapper(fluid);
        }
    }

    private static void registerFluidMapper(Fluid fluid) {
        CustomFluidStateMapper mapper = new CustomFluidStateMapper(fluid.getName());
        ModelLoader.setCustomStateMapper(fluid.getBlock(), mapper);
    }

    @SubscribeEvent
    public static void registerTextures(TextureStitchEvent.Pre event) {
        registerSprites(event.getMap());
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        registerMappers();

        for (ItemBase item : CommonProxy.ITEMS) {
            for (int i = 0; i < CTUtil.getStacks(item).size(); i++) {
                ModelLoader.setCustomModelResourceLocation(item, i, item.getModel(i));
            }

            if (ModConfig.CONFIG.generateFiles) GeneralUtil.generateModelFileItem(item);
        }

        for (ItemBlock item : CommonProxy.ITEMBLOCKS) {
            for (int i = 0; i < CTUtil.getStacks(item).size(); i++) {
                ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
            }
            if (ModConfig.CONFIG.generateFiles) GeneralUtil.generateModelFileItem(item);
        }

        if (ModConfig.CONFIG.generateFiles) {
            GeneralUtil.generateFiles();
            GeneralUtil.generateFluidFiles(FLUIDS);
        }
    }
}
