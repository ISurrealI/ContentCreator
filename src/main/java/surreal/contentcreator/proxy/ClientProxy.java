package surreal.contentcreator.proxy;

import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.Block;
import net.minecraft.block.BlockColored;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.client.renderer.color.IBlockColor;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.event.TextureStitchEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fluids.Fluid;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.client.fluid.CustomFluidStateMapper;
import surreal.contentcreator.common.block.BlockBase;
import surreal.contentcreator.common.block.BlockMaterial;
import surreal.contentcreator.common.block.generic.BlockGenericColored;
import surreal.contentcreator.common.item.ItemBase;
import surreal.contentcreator.common.item.ItemBlockBase;
import surreal.contentcreator.common.item.ItemBlockMaterial;
import surreal.contentcreator.common.item.ItemMaterial;
import surreal.contentcreator.functions.item.IItemColorFunc;
import surreal.contentcreator.types.CTMaterial;
import surreal.contentcreator.util.CTUtil;
import surreal.contentcreator.util.FileGenUtil;
import surreal.contentcreator.util.GeneralUtil;
import surreal.contentcreator.util.IHaloItem;

import java.util.ArrayList;
import java.util.List;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(value = Side.CLIENT)
public class ClientProxy extends CommonProxy {
    public static final List<String> texturesToRegister = new ArrayList<>();
    public static final List<ResourceLocation> fluidTextures = new ArrayList<>();
    public static final IItemColor ITEMCOLOR = (stack, tintIndex) -> {
        if (stack.getItem() instanceof ItemBase) {
            ItemBase item = (ItemBase) stack.getItem();
            IItemColorFunc color = item.SUBITEMS.get(stack.getMetadata()).COLOR;
            if (color != null) return color.colorMultiplier(CraftTweakerMC.getIItemStack(stack), tintIndex);
        }

        return 0xFFFFFF;
    };

    @Override
    public void preInit(FMLPreInitializationEvent event) {
        super.preInit(event);
        registerHaloTextures();
    }

    private static void registerHaloTextures() {
        if (!Loader.isModLoaded("avaritia")) {
            texturesToRegister.add(IHaloItem.getHaloTexture(0));
            texturesToRegister.add(IHaloItem.getHaloTexture(1));
            texturesToRegister.add(IHaloItem.getHaloTexture(2));
        }
    }

    public static final IItemColor ITEMBLOCKCOLOR = (stack, index) -> {
        if (stack.getItem() instanceof ItemBlockBase) {
            ItemBlockBase item = (ItemBlockBase) stack.getItem();
            if (item.blockBase != null && item.blockBase.COLOR != null) return item.blockBase.COLOR.colorMultiplier(CraftTweakerMC.getBlockState(item.blockBase.getStateFromMeta(stack.getMetadata())), index);
            else if (item.getBlock() instanceof BlockGenericColored && ((BlockGenericColored) item.getBlock()).color) return EnumDyeColor.byDyeDamage(stack.getMetadata()).getColorValue();
        }
        if (stack.getItem() instanceof ItemBlockMaterial) {
            CTMaterial material = ((ItemBlockMaterial) stack.getItem()).getMaterial(stack);
            return material.color;
        }

        return 0xFFFFFF;
    };

    public static final IItemColor MATERIALITEMCOLOR = (stack, tintIndex) -> {
        if (stack.getItem() instanceof ItemMaterial) {
            ItemMaterial item = (ItemMaterial) stack.getItem();
            return item.MATERIALS.get(stack.getMetadata()).color;
        }

        return 0xFFFFFF;
    };

    public static final IBlockColor BLOCKCOLOR = (state, worldIn, pos, tintIndex) -> {
        if (state.getBlock() instanceof BlockBase) {
            BlockBase block = (BlockBase) state.getBlock();
            if (block.COLOR != null) return block.COLOR.colorMultiplier(CraftTweakerMC.getBlockState(state), tintIndex);
        } else if (state.getBlock() instanceof BlockGenericColored && ((BlockGenericColored) state.getBlock()).color) {
            return EnumDyeColor.byDyeDamage(state.getBlock().getMetaFromState(state)).getColorValue();
        }

        return 0xFFFFFF;
    };

    public static final IBlockColor BLOCKMATERIALCOLOR = (state, worldIn, pos, tintIndex) -> {
        if (state.getBlock() instanceof BlockMaterial) {
            BlockMaterial block = (BlockMaterial) state.getBlock();
            return block.materials[block.getMetaFromState(state)].color;
        }

        return 0xFFFFFF;
    };

    private static void registerSprites(TextureMap map) {
        for (ResourceLocation location : fluidTextures) {
            map.registerSprite(location);
        }

        for (String location : texturesToRegister) {
            map.registerSprite(new ResourceLocation(location));
        }
    }

    private static void registerMappers() {
        for (Fluid fluid : CommonProxy.FLUIDS) {
            registerFluidMapper(fluid);
        }
    }

    private static void registerFluidMapper(Fluid fluid) {
        if (fluid.getBlock() != null) {
            CustomFluidStateMapper mapper = new CustomFluidStateMapper(fluid.getName());
            ModelLoader.setCustomStateMapper(fluid.getBlock(), mapper);
        }
    }

    @SubscribeEvent
    public static void registerTextures(TextureStitchEvent.Pre event) {
        registerSprites(event.getMap());
    }

    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        registerMappers();

        for (ItemBase item : CommonProxy.ITEMS) {
            for (int i : item.SUBITEMS.keySet()) {
                ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getModelLocation(i)));
            }
        }

        for (Item item : CommonProxy.ITEMBLOCKS) {
            for (int i = 0; i < CTUtil.getStacks(item).size(); i++) {
                ModelLoader.setCustomModelResourceLocation(item, i, new ModelResourceLocation(item.getRegistryName(), "inventory"));
            }
        }

        for (ItemMaterial item : CommonProxy.MAT_ITEMS.values()) {
            for (CTMaterial material : item.part.getMaterials()) {
                ModelLoader.setCustomModelResourceLocation(item, material.id, new ModelResourceLocation(item.getModelLocation(material), "inventory"));
            }
        }

        if (Loader.isModLoaded("resourceloader")) FileGenUtil.generateFiles();
    }

    @SubscribeEvent
    public static void regColorsBlock(ColorHandlerEvent.Block event) {
        for (Block block : CommonProxy.BLOCKS) {
            event.getBlockColors().registerBlockColorHandler(BLOCKCOLOR, block);
        }
        for (Block block : CommonProxy.MAT_BLOCKS) {
            event.getBlockColors().registerBlockColorHandler(BLOCKMATERIALCOLOR, block);
        }
    }

    @SubscribeEvent
    public static void regColorsItem(ColorHandlerEvent.Item event) {
        for (Item item : CommonProxy.ITEMS) {
            event.getItemColors().registerItemColorHandler(ITEMCOLOR, item);
        }

        for (Item item : CommonProxy.ITEMBLOCKS) {
            event.getItemColors().registerItemColorHandler(ITEMBLOCKCOLOR, item);
        }

        for (Item item : CommonProxy.MAT_ITEMS.values()) {
            event.getItemColors().registerItemColorHandler(MATERIALITEMCOLOR, item);
        }
    }
}
