package surreal.contentcreator.common.items.sub;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.List;

import static surreal.contentcreator.helpers.GeneralHelper.*;

public class ASubItem {

    public int getItemStackLimit(@Nonnull ItemStack stack) {
        return 64;
    }

    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return EnumActionResult.PASS;
    }

    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return 1F;
    }

    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        return new ActionResult<>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
    }

    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        return stack;
    }

    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        return false;
    }

    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        return false;
    }

    public boolean canHarvestBlock(IBlockState blockIn, ItemStack stack) {
        return false;
    }

    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        return false;
    }

    public String getTranslationKey(ItemStack stack) {
        return "item.unidentified";
    }

    public ItemStack getContainerItem(ItemStack stack) {
        return ItemStack.EMPTY;
    }

    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    }

    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return EnumAction.NONE;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return 0;
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return false;
    }

    public IRarity getForgeRarity(ItemStack stack) {
        return EnumRarity.COMMON;
    }

    public int getItemEnchantability(ItemStack stack) {
        return 0;
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return false;
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        return HashMultimap.create();
    }

    public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
        return true;
    }

    public String getHighlightTip(ItemStack item, String displayName) {
        return displayName;
    }

    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        return EnumActionResult.PASS;
    }

    public float getXpRepairRatio(ItemStack stack) {
        return 0;
    }

    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
        return false;
    }

    public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
    }

    public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
        return false;
    }

    public int getEntityLifespan(ItemStack itemStack, World world) {
        return 6000;
    }

    public boolean hasCustomEntity(ItemStack stack) {
        return false;
    }

    @Nullable
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        return null;
    }

    public boolean onEntityItemUpdate(net.minecraft.entity.item.EntityItem entityItem) {
        return false;
    }

    public float getSmeltingExperience(ItemStack item) {
        return -1;
    }

    public boolean doesSneakBypassUse(ItemStack stack, net.minecraft.world.IBlockAccess world, BlockPos pos, EntityPlayer player) {
        return false;
    }

    public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {}

    public boolean isValidArmor(ItemStack stack, EntityEquipmentSlot armorType, Entity entity) {
        return EntityLiving.getSlotForItemStack(stack) == armorType;
    }

    @Nullable
    public EntityEquipmentSlot getEquipmentSlot(ItemStack stack) {
        return null;
    }

    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return true;
    }

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EntityEquipmentSlot slot, String type) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Nullable
    public FontRenderer getFontRenderer(ItemStack stack) {
        return null;
    }

    @SideOnly(Side.CLIENT)
    @Nullable
    public ModelBiped getArmorModel(EntityLivingBase entityLiving, ItemStack stack, EntityEquipmentSlot armorSlot, net.minecraft.client.model.ModelBiped _default) {
        return null;
    }

    public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public void renderHelmetOverlay(ItemStack stack, EntityPlayer player, ScaledResolution resolution, float partialTicks) {
    }

    public int getDamage(ItemStack stack) {
        setNBT(stack, 0);
        return stack.getTagCompound().getInteger(damageKey());
    }

    public boolean showDurabilityBar(ItemStack stack) {
        return stack.isItemDamaged();
    }

    public double getDurabilityForDisplay(ItemStack stack) {
        return (double)stack.getItemDamage() / (double)stack.getMaxDamage();
    }

    public int getRGBDurabilityForDisplay(ItemStack stack) {
        return MathHelper.hsvToRGB(Math.max(0.0F, (float) (1.0F - getDurabilityForDisplay(stack))) / 3.0F, 1.0F, 1.0F);
    }

    public int getMaxDamage(ItemStack stack) {
        return 0;
    }

    public boolean isDamaged(ItemStack stack) {
        return stack.getItemDamage() > 0;
    }

    public void setDamage(ItemStack stack, int damage) {
        if (!setNBT(stack, damage)) {
            stack.getTagCompound().setInteger(damageKey(), damage);
        }
    }

    private boolean setNBT(ItemStack stack, int damage) {

        if (stack.isItemStackDamageable()) {
            if (stack.hasTagCompound() && !stack.getTagCompound().hasKey(damageKey())) {
                stack.getTagCompound().setInteger(damageKey(), Math.max(0, damage));
                return true;
            } else if (!stack.hasTagCompound()) {
                NBTTagCompound tag = new NBTTagCompound();
                tag.setInteger(damageKey(), Math.max(0, damage));

                stack.setTagCompound(tag);

                return true;
            }
        }

        return false;
    }
}
