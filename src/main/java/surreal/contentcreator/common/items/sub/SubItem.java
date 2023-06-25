package surreal.contentcreator.common.items.sub;

import com.google.common.collect.HashMultimap;
import com.google.common.collect.Multimap;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.IAttribute;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.function.BiFunction;

public class SubItem {

    private final Item item;
    private final int id;

    private String translationKey;
    private String highlightTip = null;
    private String[] information;

    private int maxStackSize = 64;
    private int maxUseDuration = 0;
    private int enchantability = 0;
    private int entityLifespan = 6000;

    private boolean hasEffect = false;
    private boolean isRepairable = false;

    private float destroySpeed = 1F;
    private float xpRepairRatio = 2F;

    private ItemStack container = ItemStack.EMPTY;
    private EnumAction action = EnumAction.NONE;
    private IRarity rarity = EnumRarity.COMMON;
    private CreativeTabs creativeTab = CreativeTabs.SEARCH;
    private Multimap<String, AttributeModifier> modifiers = null;

    public SubItem(Item item, int id) {
        this.item = item;
        this.id = id;

        this.translationKey = item.getTranslationKey() + "_" + id;
    }

    // Getters
    public CreativeTabs getCreativeTab() {
        return creativeTab;
    }

    // Setters
    public void setTranslationKey(String translationKey) {
        this.translationKey = translationKey;
    }

    public void setMaxStackSize(int maxStackSize) {
        this.maxStackSize = maxStackSize;
    }

    public void setDestroySpeed(float destroySpeed) {
        this.destroySpeed = destroySpeed;
    }

    public void setContainer(ItemStack container) {
        this.container = container;
    }

    public void setAction(EnumAction action) {
        this.action = action;
    }

    public void setMaxUseDuration(int maxUseDuration) {
        this.maxUseDuration = maxUseDuration;
    }

    public void setInformation(String... information) {
        this.information = information;
    }

    public void setEffect() {
        this.hasEffect = true;
    }

    public void setRarity(IRarity rarity) {
        this.rarity = rarity;
    }

    public void setEnchantability(int enchantability) {
        this.enchantability = enchantability;
    }

    public void setCreativeTab(CreativeTabs creativeTab) {
        this.creativeTab = creativeTab;
    }

    public void setRepairable(boolean repairable) {
        isRepairable = repairable;
    }

    public void setAttributeModifiers(Multimap<String, AttributeModifier> modifiers) {
        this.modifiers = modifiers;
    }

    public void addAttributeModifier(IAttribute attribute, double amount) {
        if (modifiers == null) modifiers = HashMultimap.create();
        modifiers.put(attribute.getName(), new AttributeModifier("Modifier", amount, 0));
    }

    public void setHighlightTip(String highlightTip) {
        this.highlightTip = highlightTip;
    }

    public void setXpRepairRatio(float xpRepairRatio) {
        this.xpRepairRatio = xpRepairRatio;
    }

    public void setEntityLifespan(int entityLifespan) {
        this.entityLifespan = entityLifespan;
    }

    // Item Methods
    public int getItemStackLimit(@Nonnull ItemStack stack) {
        return maxStackSize;
    }

    public EnumActionResult onItemUse(EntityPlayer player, World worldIn, BlockPos pos, EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        return EnumActionResult.PASS;
    }

    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return destroySpeed;
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
        return translationKey;
    }

    public ItemStack getContainerItem(ItemStack stack) {
        return container;
    }

    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    }

    public void onCreated(ItemStack stack, World worldIn, EntityPlayer playerIn) {
    }

    public EnumAction getItemUseAction(ItemStack stack) {
        return action;
    }

    public int getMaxItemUseDuration(ItemStack stack) {
        return maxUseDuration;
    }

    public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
    }

    @SideOnly(Side.CLIENT)
    public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
        if (information != null) Collections.addAll(tooltip, this.information);
    }

    @SideOnly(Side.CLIENT)
    public boolean hasEffect(ItemStack stack) {
        return hasEffect || stack.isItemEnchanted();
    }

    public IRarity getForgeRarity(ItemStack stack) {
        return rarity;
    }

    public int getItemEnchantability(ItemStack stack) {
        return enchantability;
    }

    public boolean getIsRepairable(ItemStack toRepair, ItemStack repair) {
        return isRepairable;
    }

    public Multimap<String, AttributeModifier> getAttributeModifiers(EntityEquipmentSlot slot, ItemStack stack) {
        return modifiers == null ? HashMultimap.create() : modifiers;
    }

    public boolean onDroppedByPlayer(ItemStack item, EntityPlayer player) {
        return true;
    }

    public String getHighlightTip(ItemStack item, String displayName) {
        return highlightTip != null ? highlightTip : displayName;
    }

    public EnumActionResult onItemUseFirst(EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, EnumHand hand) {
        return EnumActionResult.PASS;
    }

    public float getXpRepairRatio(ItemStack stack) {
        return xpRepairRatio;
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
        return entityLifespan;
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
}
