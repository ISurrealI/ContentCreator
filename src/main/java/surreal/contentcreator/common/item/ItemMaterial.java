package surreal.contentcreator.common.item;

import com.google.common.collect.Maps;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.types.CTMaterial;
import surreal.contentcreator.types.parts.PartItem;
import surreal.contentcreator.util.GeneralUtil;
import surreal.contentcreator.util.IHaloItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;
import java.util.Map;

public class ItemMaterial extends Item implements IHaloItem {
    public PartItem part;

    public Map<Integer, CTMaterial> MATERIALS;

    public ItemMaterial(PartItem type) {
        this.part = type;
        this.MATERIALS = Maps.newHashMap();
        for (CTMaterial material : type.materials) {
            MATERIALS.put(material.id, material);
        }

        this.setRegistryName(ModValues.MODID, type.name);
        this.setUnlocalizedName(ModValues.MODID + "." + type.name);
        this.setHasSubtypes(true);
        this.setCreativeTab(CreativeTabs.SEARCH);
    }

    @Nonnull
    @Override
    public String getItemStackDisplayName(ItemStack stack) {
        CTMaterial material = MATERIALS.get(stack.getMetadata());
        if (material != null) return I18n.format("part." + this.part.name, material.getLocalizedName());
        else return super.getItemStackDisplayName(stack);
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (CTMaterial material : this.part.materials) {
                items.add(new ItemStack(this, 1, material.id));
            }
        }
    }

    @SideOnly(Side.CLIENT)
    @Override
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        CTMaterial material = this.MATERIALS.get(stack.getMetadata());
        if (material.tooltips != null) tooltip.addAll(material.tooltips);
    }

    @Nonnull
    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        CTMaterial material = this.MATERIALS.get(stack.getMetadata());
        return EnumRarity.valueOf(material.rarity.toUpperCase());
    }

    public String getModelLocation(CTMaterial material) {
        return ModValues.MODID + ":part/" + material.textureType + "/" + this.part.name;
    }

    @Override
    public ResourceLocation getLocation(World world, EntityPlayer player, ItemStack stack) {
        CTMaterial material = this.MATERIALS.get(stack.getMetadata());
        return material.HALOTEXTURE != null ? GeneralUtil.getTextureLocation(material.HALOTEXTURE.getTexture(CraftTweakerMC.getIWorld(world), CraftTweakerMC.getIPlayer(player), CraftTweakerMC.getIItemStack(stack))) : null;
    }

    @Override
    public int getSpread(World world, EntityPlayer player, ItemStack stack) {
        CTMaterial material = this.MATERIALS.get(stack.getMetadata());
        return material.HALOSPREAD != null ? material.HALOSPREAD.getSpread(CraftTweakerMC.getIWorld(world), CraftTweakerMC.getIPlayer(player), CraftTweakerMC.getIItemStack(stack)) : 1;
    }

    @Override
    public int getColor(World world, EntityPlayer player, ItemStack stack) {
        CTMaterial material = this.MATERIALS.get(stack.getMetadata());
        return material.HALOCOLOR != null ? material.HALOCOLOR.getColor(CraftTweakerMC.getIWorld(world), CraftTweakerMC.getIPlayer(player), CraftTweakerMC.getIItemStack(stack)) : 0xFF000000;
    }
}
