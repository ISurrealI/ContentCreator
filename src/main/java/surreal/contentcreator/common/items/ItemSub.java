package surreal.contentcreator.common.items;

import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import surreal.contentcreator.common.items.sub.SubItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.ArrayList;
import java.util.List;

public class ItemSub extends Item {

    private final List<SubItem> subItems;

    private List<CreativeTabs> tabList;

    public ItemSub() {
        this.subItems = new ArrayList<>();
        this.tabList = new ArrayList<>();
    }

    public ItemSub add(SubItem subItem) {
        this.subItems.add(subItem);
        this.tabList.add(subItem.getCreativeTab());

        return this;
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    @Override
    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return getSubItem(player.getHeldItem(hand)).onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, @Nonnull IBlockState state) {
        return getSubItem(stack).getDestroySpeed(stack, state);
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
        return getSubItem(playerIn.getHeldItem(handIn)).onItemRightClick(worldIn, playerIn, handIn);
    }

    @Nonnull
    @ParametersAreNonnullByDefault
    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entityLiving) {
        return getSubItem(stack).onItemUseFinish(stack, worldIn, entityLiving);
    }

    @Override
    public int getItemStackLimit(@Nonnull ItemStack stack) {
        return getSubItem(stack).getItemStackLimit(stack);
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean hitEntity(ItemStack stack, EntityLivingBase target, EntityLivingBase attacker) {
        return getSubItem(stack).hitEntity(stack, target, attacker);
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean onBlockDestroyed(ItemStack stack, World worldIn, IBlockState state, BlockPos pos, EntityLivingBase entityLiving) {
        return getSubItem(stack).onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

    @Override
    public boolean canHarvestBlock(@Nonnull IBlockState state, @Nonnull ItemStack stack) {
        return getSubItem(stack).canHarvestBlock(state, stack);
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target, EnumHand hand) {
        return getSubItem(stack).itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @Nonnull
    public String getTranslationKey(@Nonnull ItemStack stack) {
        return getSubItem(stack).getTranslationKey(stack);
    }

    @Nonnull
    @Override
    public ItemStack getContainerItem(@Nonnull ItemStack itemStack) {
        return super.getContainerItem(itemStack);
    }

    @Override
    public boolean hasContainerItem(@Nonnull ItemStack stack) {
        return !getContainerItem(stack).isEmpty();
    }

    @ParametersAreNonnullByDefault
    @Override
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        getSubItem(stack).onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    }

    @ParametersAreNonnullByDefault
    @Override
    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
        getSubItem(stack).onCreated(stack, worldIn, playerIn);
    }

    @Nonnull
    @Override
    public EnumAction getItemUseAction(@Nonnull ItemStack stack) {
        return getSubItem(stack).getItemUseAction(stack);
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack stack) {
        return super.getMaxItemUseDuration(stack);
    }

    @ParametersAreNonnullByDefault
    @Override
    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
        getSubItem(stack).onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
    }

    @ParametersAreNonnullByDefault
    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        getSubItem(stack).addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public boolean hasEffect(@Nonnull ItemStack stack) {
        return getSubItem(stack).hasEffect(stack);
    }

    @Nonnull
    @Override
    public IRarity getForgeRarity(@Nonnull ItemStack stack) {
        return getSubItem(stack).getForgeRarity(stack);
    }

    @Override
    public int getItemEnchantability(@Nonnull ItemStack stack) {
        return getSubItem(stack).getItemEnchantability(stack);
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (int i = 0; i < subItems.size(); i++) {
                if (tab == CreativeTabs.SEARCH || subItems.get(i).getCreativeTab() == tab) items.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Override
    public boolean getIsRepairable(@Nonnull ItemStack toRepair, @Nonnull ItemStack repair) {
        return getSubItem(toRepair).getIsRepairable(toRepair, repair);
    }

    @Nonnull
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, @Nonnull ItemStack stack) {
        return getSubItem(stack).getAttributeModifiers(slot, stack);
    }

    @Override
    public boolean onDroppedByPlayer(@Nonnull ItemStack item, @Nonnull EntityPlayer player) {
        return getSubItem(item).onDroppedByPlayer(item, player);
    }

    @Nonnull
    @Override
    public String getHighlightTip(@Nonnull ItemStack item, @Nonnull String displayName) {
        return getSubItem(item).getHighlightTip(item, displayName);
    }

    @ParametersAreNonnullByDefault
    @Nonnull
    @Override
    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        return getSubItem(player.getHeldItem(hand)).onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }

    @Override
    public float getXpRepairRatio(@Nonnull ItemStack stack) {
        return getSubItem(stack).getXpRepairRatio(stack);
    }

    @ParametersAreNonnullByDefault
    @Override
    public boolean onBlockStartBreak(ItemStack itemstack, BlockPos pos, EntityPlayer player) {
        return getSubItem(itemstack).onBlockStartBreak(itemstack, pos, player);
    }

    @Override
    public void onUsingTick(@Nonnull ItemStack stack, @Nonnull EntityLivingBase player, int count) {
        getSubItem(stack).onUsingTick(stack, player, count);
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        return getSubItem(stack).onLeftClickEntity(stack, player, entity);
    }

    @Override
    public int getEntityLifespan(@Nonnull ItemStack itemStack, @Nonnull World world) {
        return getSubItem(itemStack).getEntityLifespan(itemStack, world);
    }

    @Override
    public boolean hasCustomEntity(@Nonnull ItemStack stack) {
        return getSubItem(stack).hasCustomEntity(stack);
    }

    @ParametersAreNonnullByDefault
    @Nullable
    @Override
    public Entity createEntity(World world, Entity location, ItemStack itemstack) {
        return getSubItem(itemstack).createEntity(world, location, itemstack);
    }

    private SubItem getSubItem(ItemStack stack) {
        return subItems.get(stack.getMetadata());
    }
}
