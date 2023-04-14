package surreal.contentcreator.common.item;

import com.google.common.collect.Multimap;
import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.entity.IEntityItem;
import crafttweaker.api.item.IItemDefinition;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.EnumAction;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import squeek.applecore.api.food.FoodValues;
import squeek.applecore.api.food.IEdible;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.functions.item.IItemPropertyFunc;
import surreal.contentcreator.proxy.CommonProxy;
import surreal.contentcreator.util.CTUtil;
import surreal.contentcreator.util.GeneralUtil;
import surreal.contentcreator.util.IHaloItem;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.*;

@SuppressWarnings("unused")

@ZenRegister
@ZenClass("contentcreator.item.Item")
@Optional.Interface(iface = "squeek.applecore.api.food.IEdible", modid = "applecore")
public class ItemBase extends Item implements IEdible, IHaloItem {
    public final Map<Integer, SubItem> SUBITEMS;
    public boolean modelBlockState = false; // for not needing to mass model files

    public ItemBase() {
        SUBITEMS = new Int2ObjectOpenHashMap<>();
        this.setCreativeTab(CreativeTabs.SEARCH);
    }

    @ZenMethod
    public static ItemBase create(String name) {
        return (ItemBase) new ItemBase().setRegistryName(ModValues.MODID, name).setUnlocalizedName(ModValues.MODID + "." + name);
    }

    @ZenMethod
    public static ItemBase getItem(String name) {
        for (ItemBase item : CommonProxy.ITEMS) {
            if (item.getRegistryName().toString().equals(name)) return item;
        }

        return null;
    }

    @ZenMethod
    public static ItemBase getItem(IItemStack st) {
        ItemStack stack = CraftTweakerMC.getItemStack(st);
        if (stack.getItem() instanceof ItemBase) return (ItemBase) stack.getItem();
        return null;
    }

    @ZenMethod
    public static ItemBase getItem(IItemDefinition definition) {
        Item item = CraftTweakerMC.getItem(definition);
        if (item instanceof ItemBase) return (ItemBase) item;
        return null;
    }

    @ZenMethod
    public static SubItem getSubItem(String name, int meta) {
        ItemBase item = getItem(name);
        return item != null ? item.getItem(meta) : null;
    }

    @ZenMethod
    public ItemBase add(SubItem... items) {
        for (SubItem sub : items) {
            SUBITEMS.put(sub.meta, sub);
        }

        return this;
    }

    @ZenMethod
    public ItemBase setModelVariant() {
        this.modelBlockState = true;
        return this;
    }

    @ZenMethod
    public SubItem getItem(int meta) {
        if (meta >= 0 && meta < SUBITEMS.size())
            return SUBITEMS.get(meta);

        return sub(meta);
    }

    @ZenMethod
    public boolean hasItem(int meta) {
        return meta >= 0 && meta < SUBITEMS.size();
    }

    @ZenMethod
    public static SubItem sub(int meta) {
        return new SubItem(meta);
    }

    @ZenMethod
    public static SubItem sub() {
        return new SubItem(0);
    }

    @ZenMethod
    public void register() {
        if (SUBITEMS.size() <= 0) SUBITEMS.put(0, new SubItem(0));
        else {
            for (SubItem subItem : SUBITEMS.values()) {
                Map<ResourceLocation, IItemPropertyFunc> props = subItem.itemProperties;
                Map<String, Integer> classes = subItem.toolClasses;

                if (props != null) props.forEach((key, value) -> this.addPropertyOverride(key, (stack, worldIn, entityIn) -> value.apply(CraftTweakerMC.getIItemStack(stack), CraftTweakerMC.getIWorld(worldIn), CraftTweakerMC.getIEntityLivingBase(entityIn))));
                if (classes != null) classes.forEach(this::setHarvestLevel);
            }
        }
        this.setHasSubtypes(SUBITEMS.size() > 1);
        CommonProxy.ITEMS.add(this);
    }

    private SubItem get(ItemStack stack) {
        return SUBITEMS.get(stack.getMetadata());
    }

    public String getModelLocation(int meta) {
        SubItem subItem = SUBITEMS.get(meta);
        String model = !this.modelBlockState ? this.getRegistryName().getResourceDomain() + ":" : this.getRegistryName().getResourceDomain() + ":item/" + this.getRegistryName().getResourcePath();
        String variant = "" + meta;
        if (subItem.UNLOCNAME != null) variant = subItem.UNLOCNAME.getUnlocalizedName(CraftTweakerMC.getIItemStack(new ItemStack(this, 1, meta)));
        model += this.modelBlockState ? "#type=" + variant : (meta == 0 && subItem.UNLOCNAME == null ? this.getRegistryName().getResourcePath() : variant) + "#inventory";

        return model;
    }

    public String getSubName(SubItem subItem) {
        return subItem.UNLOCNAME != null ? subItem.UNLOCNAME.getUnlocalizedName(CraftTweakerMC.getIItemStack(new ItemStack(this, 1, subItem.meta))) : null;
    }

    @Override
    public void getSubItems(@Nonnull CreativeTabs tab, @Nonnull NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (int i : SUBITEMS.keySet()) {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }

    @Nonnull
    @Override
    public String getUnlocalizedName(@Nonnull ItemStack stack) {
        SubItem subItem = get(stack);
        return subItem.UNLOCNAME != null ? "item." + ModValues.MODID + "." + subItem.UNLOCNAME.getUnlocalizedName(CraftTweakerMC.getIItemStack(stack)) : this.getUnlocalizedName() + (SUBITEMS.size() > 1 ? "." + stack.getMetadata() : "");
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUse(@Nonnull EntityPlayer player, @Nonnull World worldIn, @Nonnull BlockPos pos, @Nonnull EnumHand hand, @Nonnull EnumFacing facing, float hitX, float hitY, float hitZ) {
        ItemStack stack = player.getHeldItem(hand);
        SubItem subItem = get(stack);
        if (subItem.ITEMUSE != null) return EnumActionResult.valueOf(subItem.ITEMUSE.onItemUse(CraftTweakerMC.getIPlayer(player), CraftTweakerMC.getIWorld(worldIn), CraftTweakerMC.getIBlockPos(pos), CTUtil.getHandEquipment(hand), CraftTweakerMC.getIFacing(facing), hitX, hitY, hitZ).toUpperCase());
        else return super.onItemUse(player, worldIn, pos, hand, facing, hitX, hitY, hitZ);
    }

    @Override
    public float getDestroySpeed(@Nonnull ItemStack stack, @Nonnull IBlockState state) {
        SubItem subItem = get(stack);
        if (subItem.DESTROYSPEED != null) return subItem.DESTROYSPEED.getDestroySpeed(CraftTweakerMC.getIItemStack(stack), CraftTweakerMC.getBlockState(state));
        else return super.getDestroySpeed(stack, state);
    }

    @Override
    public float getXpRepairRatio(@Nonnull ItemStack stack) {
        SubItem subItem = get(stack);
        if (subItem.XPREPAIR != null) return subItem.XPREPAIR.getFloat(CraftTweakerMC.getIItemStack(stack));
        else return super.getXpRepairRatio(stack);
    }

    @Override
    public boolean onBlockStartBreak(@Nonnull ItemStack itemstack, @Nonnull BlockPos pos, @Nonnull EntityPlayer player) {
        SubItem subItem = get(itemstack);
        if (subItem.BLOCKSTARTBREAK != null) return subItem.BLOCKSTARTBREAK.onBlockStartBreak(CraftTweakerMC.getIItemStackMutable(itemstack), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIPlayer(player));
        else return super.onBlockStartBreak(itemstack, pos, player);
    }

    @Override
    public void onUsingTick(@Nonnull ItemStack stack, @Nonnull EntityLivingBase player, int count) {
        SubItem subItem = get(stack);
        if (subItem.USINGTICK != null) subItem.USINGTICK.onUsingTick(CraftTweakerMC.getIItemStackMutable(stack), CraftTweakerMC.getIEntityLivingBase(player), count);
        else super.onUsingTick(stack, player, count);
    }

    @Override
    public boolean onLeftClickEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull Entity entity) {
        SubItem subItem = get(stack);
        if (subItem.LEFTCLICKENTITY != null) return subItem.LEFTCLICKENTITY.onLeftClickEntity(CraftTweakerMC.getIItemStackMutable(stack), CraftTweakerMC.getIPlayer(player), CraftTweakerMC.getIEntity(entity));
        else return super.onLeftClickEntity(stack, player, entity);
    }

    @Nonnull
    @Override
    public ItemStack getContainerItem(@Nonnull ItemStack itemStack) {
        SubItem subItem = get(itemStack);
        if (subItem.CONTAINERITEM != null) return CraftTweakerMC.getItemStack(subItem.CONTAINERITEM.getContainerItem(CraftTweakerMC.getIItemStack(itemStack)));
        else return super.getContainerItem(itemStack);
    }

    @Override
    public int getEntityLifespan(@Nonnull ItemStack itemStack, @Nonnull World world) {
        SubItem subItem = get(itemStack);
        if (subItem.ENTITYLIFESPAN != null) return subItem.ENTITYLIFESPAN.getEntityLifespane(CraftTweakerMC.getIItemStack(itemStack), CraftTweakerMC.getIWorld(world));
        else return super.getEntityLifespan(itemStack, world);
    }

    @Override
    public int getItemEnchantability(@Nonnull ItemStack stack) {
        SubItem subItem = get(stack);
        if (subItem.ENCHANTABILITY != null) return subItem.ENCHANTABILITY.getInt(CraftTweakerMC.getIItemStack(stack));
        else return super.getItemEnchantability(stack);
    }

    @Override
    public boolean canApplyAtEnchantingTable(@Nonnull ItemStack stack, @Nonnull Enchantment enchantment) {
        SubItem subItem = get(stack);
        if (subItem.APPLYENCHTABLE != null) return subItem.APPLYENCHTABLE.canApplyAtEnchantingTable(CraftTweakerMC.getIItemStack(stack), CTUtil.getEnchantment(enchantment));
        else return super.canApplyAtEnchantingTable(stack, enchantment);
    }

    @Nullable
    @Override
    public Entity createEntity(@Nonnull World world, @Nonnull Entity location, @Nonnull ItemStack itemstack) {
        SubItem subItem = get(itemstack);
        if (subItem.CUSTOMENTITY != null) return CraftTweakerMC.getEntity(subItem.CUSTOMENTITY.createEntity(CraftTweakerMC.getIWorld(world), (IEntityItem) CraftTweakerMC.getIEntity(location), CraftTweakerMC.getIItemStack(itemstack)));
        else return super.createEntity(world, location, itemstack);
    }

    @Override
    public boolean hasCustomEntity(@Nonnull ItemStack stack) {
        return get(stack).CUSTOMENTITY != null;
    }

    @Override
    public boolean onEntityItemUpdate(@Nonnull EntityItem entityItem) {
        SubItem subItem = get(entityItem.getItem());
        if (subItem.ENTITYUPDATE != null) return subItem.ENTITYUPDATE.onEntityItemUpdate(new CTUtil.MCEntityItemMutable(entityItem));
        else return super.onEntityItemUpdate(entityItem);
    }

    @Override
    public boolean onEntitySwing(@Nonnull EntityLivingBase entityLiving, @Nonnull ItemStack stack) {
        SubItem subItem = get(stack);
        if (subItem.ENTITYSWING != null) return subItem.ENTITYSWING.onEntitySwing(CraftTweakerMC.getIEntityLivingBase(entityLiving), CraftTweakerMC.getIItemStackMutable(stack));
        else return super.onEntitySwing(entityLiving, stack);
    }

    @Override
    public int getDamage(@Nonnull ItemStack stack) {
        SubItem subItem = get(stack);
        if (subItem.ITEMDAMAGE != null) return subItem.ITEMDAMAGE.getInt(CraftTweakerMC.getIItemStack(stack));
        else return super.getDamage(stack);
    }

    @Override
    public boolean showDurabilityBar(@Nonnull ItemStack stack) {
        SubItem subItem = get(stack);
        if (subItem.SHOWBAR != null) return subItem.SHOWBAR.getBool(CraftTweakerMC.getIItemStack(stack));
        else return super.showDurabilityBar(stack);
    }

    @Override
    public double getDurabilityForDisplay(@Nonnull ItemStack stack) {
        SubItem subItem = get(stack);
        if (subItem.DURABILITYDISPLAY != null) return subItem.DURABILITYDISPLAY.getDouble(CraftTweakerMC.getIItemStack(stack));
        else return super.getDurabilityForDisplay(stack);
    }

    @Override
    public int getRGBDurabilityForDisplay(@Nonnull ItemStack stack) {
        SubItem subItem = get(stack);
        if (subItem.COLORDISPLAY != null) return subItem.COLORDISPLAY.getInt(CraftTweakerMC.getIItemStack(stack));
        else return super.getRGBDurabilityForDisplay(stack);
    }

    @Override
    public int getMaxDamage(@Nonnull ItemStack stack) {
        SubItem subItem = get(stack);
        if (subItem.MAXDAMAGE != null) return subItem.MAXDAMAGE.getInt(CraftTweakerMC.getIItemStack(stack));
        else return super.getMaxDamage(stack);
    }

    @Override
    public void setDamage(@Nonnull ItemStack stack, int damage) {
        SubItem subItem = get(stack);
        if (subItem.SETDAMAGE != null) subItem.SETDAMAGE.setDamage(CraftTweakerMC.getIItemStackMutable(stack), damage);
        else super.setDamage(stack, damage);
    }

    @Override
    public boolean canDestroyBlockInCreative(@Nonnull World world, @Nonnull BlockPos pos, @Nonnull ItemStack stack, @Nonnull EntityPlayer player) {
        SubItem subItem = get(stack);
        if (subItem.DESTROYCREATIVE != null) return subItem.DESTROYCREATIVE.canDestroyBlockInCreative(CraftTweakerMC.getIWorld(world), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIItemStack(stack), CraftTweakerMC.getIPlayer(player));
        else return super.canDestroyBlockInCreative(world, pos, stack, player);
    }

    @Override
    public boolean canHarvestBlock(@Nonnull IBlockState state, @Nonnull ItemStack stack) {
        SubItem subItem = get(stack);
        if (subItem.HARVESTBLOCK != null) return subItem.HARVESTBLOCK.canHarvestBlock(CraftTweakerMC.getBlockState(state), CraftTweakerMC.getIItemStack(stack));
        else return super.canHarvestBlock(state, stack);
    }

    @Override
    public int getItemStackLimit(@Nonnull ItemStack stack) {
        SubItem subItem = get(stack);
        if (subItem.STACKLIMIT != null) return subItem.STACKLIMIT.getInt(CraftTweakerMC.getIItemStack(stack));
        else return super.getItemStackLimit(stack);
    }

    @Override
    public boolean isBeaconPayment(@Nonnull ItemStack stack) {
        SubItem subItem = get(stack);
        if (subItem.BEACONPAYMENT != null) return subItem.BEACONPAYMENT.getBool(CraftTweakerMC.getIItemStack(stack));
        else return super.isBeaconPayment(stack);
    }

    @Override
    public boolean shouldCauseReequipAnimation(@Nonnull ItemStack oldStack, @Nonnull ItemStack newStack, boolean slotChanged) {
        SubItem subItem = get(oldStack);
        if (subItem.REEQUIP != null) return subItem.REEQUIP.shouldCauseReequipAnimation(CraftTweakerMC.getIItemStack(oldStack), CraftTweakerMC.getIItemStack(newStack), slotChanged);
        else return super.shouldCauseReequipAnimation(oldStack, newStack, slotChanged);
    }

    @Override
    public boolean shouldCauseBlockBreakReset(@Nonnull ItemStack oldStack, @Nonnull ItemStack newStack) {
        SubItem subItem = get(oldStack);
        if (subItem.BREAKRESET != null) return subItem.BREAKRESET.shouldCauseBlockBreakReset(CraftTweakerMC.getIItemStack(oldStack), CraftTweakerMC.getIItemStack(newStack));
        else return super.shouldCauseBlockBreakReset(oldStack, newStack);
    }

    @Override
    public boolean canContinueUsing(@Nonnull ItemStack oldStack, @Nonnull ItemStack newStack) {
        SubItem subItem = get(oldStack);
        if (subItem.CONTINUEUSING != null) return subItem.CONTINUEUSING.canContinueUsing(CraftTweakerMC.getIItemStack(oldStack), CraftTweakerMC.getIItemStack(newStack));
        else return super.canContinueUsing(oldStack, newStack);
    }

    @Nullable
    @Override
    public String getCreatorModId(@Nonnull ItemStack itemStack) {
        SubItem subItem = get(itemStack);
        if (subItem.CREATORMODID != null) CraftTweakerAPI.logWarning("You cannot change mod id!");
        return super.getCreatorModId(itemStack);
    }

    @Override
    public boolean canDisableShield(@Nonnull ItemStack stack, @Nonnull ItemStack shield, @Nonnull EntityLivingBase entity, @Nonnull EntityLivingBase attacker) {
        SubItem subItem = get(stack);
        if (subItem.DISABLESHIED != null) return subItem.DISABLESHIED.canDisableShield(CraftTweakerMC.getIItemStack(stack), CraftTweakerMC.getIItemStack(shield), CraftTweakerMC.getIEntityLivingBase(entity), CraftTweakerMC.getIEntityLivingBase(attacker));
        else return super.canDisableShield(stack, shield, entity, attacker);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void addInformation(@Nonnull ItemStack stack, @Nullable World worldIn, @Nonnull List<String> tooltip, @Nonnull ITooltipFlag flagIn) {
        SubItem subItem = get(stack);
        if (subItem.INFO != null) Collections.addAll(tooltip, subItem.INFO.addInformation(CraftTweakerMC.getIItemStack(stack), CraftTweakerMC.getIWorld(worldIn), flagIn.isAdvanced()));
        else super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    @Override
    public int getItemBurnTime(@Nonnull ItemStack itemStack) {
        SubItem subItem = get(itemStack);
        if (subItem.BURNTIME != null) return subItem.BURNTIME.getInt(CraftTweakerMC.getIItemStack(itemStack));
        else return super.getItemBurnTime(itemStack);
    }

    @Nonnull
    @Override
    public IRarity getForgeRarity(@Nonnull ItemStack stack) {
        SubItem subItem = get(stack);

        if (subItem.RARITY != null) return CTUtil.getRarity(subItem.RARITY.getString(CraftTweakerMC.getIItemStack(stack)));
        else return super.getForgeRarity(stack);
    }

    @Override
    public boolean hasEffect(@Nonnull ItemStack stack) {
        SubItem subItem = get(stack);
        if (subItem.EFFECT != null) return subItem.EFFECT.getBool(CraftTweakerMC.getIItemStack(stack));
        else return super.hasEffect(stack);
    }

    @Nonnull
    @Override
    public EnumAction getItemUseAction(@Nonnull ItemStack stack) {
        SubItem subItem = get(stack);
        if (subItem.ACTION != null) return CTUtil.getAction(subItem.ACTION.getString(CraftTweakerMC.getIItemStack(stack)));
        else return super.getItemUseAction(stack);
    }

    @Override
    public int getMaxItemUseDuration(@Nonnull ItemStack stack) {
        SubItem subItem = get(stack);
        if (subItem.MAXUSEDURATION != null) return subItem.MAXUSEDURATION.getInt(CraftTweakerMC.getIItemStack(stack));
        else return super.getMaxItemUseDuration(stack);
    }

    @Nonnull
    @Override
    public ActionResult<ItemStack> onItemRightClick(@Nonnull World worldIn, EntityPlayer playerIn, @Nonnull EnumHand handIn) {
        ItemStack stack = playerIn.getHeldItem(handIn);
        SubItem subItem = get(stack);
        if (playerIn.canEat(this.isAlwaysEatable(stack))) playerIn.setActiveHand(handIn);
        if (subItem.RIGHTCLICK != null) return ActionResult.newResult(EnumActionResult.valueOf(subItem.RIGHTCLICK.onItemRightClick(CraftTweakerMC.getIWorld(worldIn), CraftTweakerMC.getIPlayer(playerIn), CTUtil.getHandEquipment(handIn))), stack);
        else return super.onItemRightClick(worldIn, playerIn, handIn);
    }

    @Nonnull
    @Override
    public EnumActionResult onItemUseFirst(@Nonnull EntityPlayer player, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull EnumFacing side, float hitX, float hitY, float hitZ, @Nonnull EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        SubItem subItem = get(stack);
        if (subItem.ITEMUSEFIRST != null) return EnumActionResult.valueOf(subItem.ITEMUSEFIRST.onItemUse(CraftTweakerMC.getIPlayer(player), CraftTweakerMC.getIWorld(world), CraftTweakerMC.getIBlockPos(pos), CTUtil.getHandEquipment(hand), CraftTweakerMC.getIFacing(side), hitX, hitY, hitZ).toUpperCase());
        else return super.onItemUseFirst(player, world, pos, side, hitX, hitY, hitZ, hand);
    }

    @Nonnull
    @Override
    public ItemStack onItemUseFinish(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull EntityLivingBase entityLiving) {
        SubItem subItem = get(stack);
        if (this.getHealAmount(stack) > 0 && entityLiving instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entityLiving;
            worldIn.playSound(null, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);
            if (player instanceof EntityPlayerMP) CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP) player, stack);

            if (Loader.isModLoaded("applecore")) ACCompatability(stack, player);
            else player.getFoodStats().addStats(this.getHealAmount(stack), this.getSaturation(stack));
            stack.shrink(1);
        }

        if (subItem.ITEMUSEFINISH != null) return CraftTweakerMC.getItemStack(subItem.ITEMUSEFINISH.onItemUseFinish(CraftTweakerMC.getIItemStackMutable(stack), CraftTweakerMC.getIWorld(worldIn), CraftTweakerMC.getIEntityLivingBase(entityLiving)));
        else return super.onItemUseFinish(stack, worldIn, entityLiving);
    }

    @Override
    public boolean itemInteractionForEntity(@Nonnull ItemStack stack, @Nonnull EntityPlayer playerIn, @Nonnull EntityLivingBase target, @Nonnull EnumHand hand) {
        SubItem subItem = get(stack);
        if (isWolfsFavorite(stack) && target instanceof EntityWolf && target.getHealth() < 20F && getHealAmount(stack) > 0) {
            if (!playerIn.isCreative()) stack.shrink(1);
            target.heal(getHealAmount(stack));
            return true;
        }

        if (subItem.ENTITYINTERACTION != null) return subItem.ENTITYINTERACTION.itemInteractionForEntity(CraftTweakerMC.getIItemStackMutable(stack), CraftTweakerMC.getIPlayer(playerIn), CraftTweakerMC.getIEntityLivingBase(target), CTUtil.getHandEquipment(hand));
        else return super.itemInteractionForEntity(stack, playerIn, target, hand);
    }

    @Override
    public boolean hitEntity(@Nonnull ItemStack stack, @Nonnull EntityLivingBase target, @Nonnull EntityLivingBase attacker) {
        SubItem subItem = get(stack);
        if (subItem.ENTITYHIT != null) return subItem.ENTITYHIT.hitEntity(CraftTweakerMC.getIItemStackMutable(stack), CraftTweakerMC.getIEntityLivingBase(target), CraftTweakerMC.getIEntityLivingBase(attacker));
        else return super.hitEntity(stack, target, attacker);
    }

    @Override
    public boolean onBlockDestroyed(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull IBlockState state, @Nonnull BlockPos pos, @Nonnull EntityLivingBase entityLiving) {
        SubItem subItem = get(stack);
        return subItem.DESTROYBLOCK != null ? subItem.DESTROYBLOCK.onBlockDestroyed(CraftTweakerMC.getIWorld(worldIn), CraftTweakerMC.getIItemStackMutable(stack), CraftTweakerMC.getBlockState(state), CraftTweakerMC.getIBlockPos(pos), CraftTweakerMC.getIEntityLivingBase(entityLiving)) : super.onBlockDestroyed(stack, worldIn, state, pos, entityLiving);
    }

    @Override
    public void onCreated(@Nonnull ItemStack stack, @Nonnull World worldIn, @Nonnull EntityPlayer playerIn) {
        SubItem subItem = get(stack);
        if (subItem.CREATED != null) subItem.CREATED.onCreated(CraftTweakerMC.getIItemStackMutable(stack), CraftTweakerMC.getIWorld(worldIn), CraftTweakerMC.getIPlayer(playerIn));
        else super.onCreated(stack, worldIn, playerIn);
    }

    @Nonnull
    @Override
    public Multimap<String, AttributeModifier> getAttributeModifiers(@Nonnull EntityEquipmentSlot slot, @Nonnull ItemStack stack) {
        SubItem subItem = get(stack);
        Multimap<String, AttributeModifier> mm = super.getAttributeModifiers(slot, stack);

        if (subItem.ATTACKDAMAGE != null) mm.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Tool modifier", subItem.ATTACKDAMAGE.getValue(CraftTweakerMC.getIEntityEquipmentSlot(slot)), 0));
        if (subItem.ATTACKSPEED != null) mm.put(SharedMonsterAttributes.ATTACK_SPEED.getName(), new AttributeModifier(ATTACK_SPEED_MODIFIER, "Tool modifier", subItem.ATTACKDAMAGE.getValue(CraftTweakerMC.getIEntityEquipmentSlot(slot)), 0));
        return mm;
    }

    @Override
    public boolean isDamaged(@Nonnull ItemStack stack) {
        SubItem subItem = get(stack);
        IItemStack iStack = CraftTweakerMC.getIItemStack(stack);

        if (subItem.MAXDAMAGE != null && subItem.ITEMDAMAGE != null) return subItem.ITEMDAMAGE.getInt(iStack) > 0;
        else return super.isDamaged(stack);
    }

    public int getHealAmount(ItemStack stack) {
        SubItem subItem = get(stack);
        if (subItem.HEALAMOUNT != null) return subItem.HEALAMOUNT.getInt(CraftTweakerMC.getIItemStack(stack));
        else return 0;
    }

    public float getSaturation(ItemStack stack) {
        SubItem subItem = get(stack);
        if (subItem.SATURATION != null) return subItem.SATURATION.getFloat(CraftTweakerMC.getIItemStack(stack));
        else return 0F;
    }

    public boolean isWolfsFavorite(ItemStack stack) {
        SubItem subItem = get(stack);
        if (subItem.WOLFSFAVORITE != null) return subItem.WOLFSFAVORITE.getBool(CraftTweakerMC.getIItemStack(stack));
        else return false;
    }

    public boolean isAlwaysEatable(ItemStack stack) {
        SubItem subItem = get(stack);
        if (subItem.ALWAYSEDIBLE != null) return subItem.ALWAYSEDIBLE.getBool(CraftTweakerMC.getIItemStack(stack));
        else return false;
    }

    @Optional.Method(modid = "applecore")
    @Override
    public FoodValues getFoodValues(@Nonnull ItemStack stack) {
        return new FoodValues(getHealAmount(stack), getSaturation(stack));
    }

    @Optional.Method(modid = "applecore")
    public void ACCompatability(ItemStack stack, EntityPlayer player) {
        FoodValues values = getFoodValues(stack);
        player.getFoodStats().addStats(values.hunger, values.saturationModifier);
    }

    @Nonnull
    @Override
    public String getHighlightTip(@Nonnull ItemStack item, @Nonnull String displayName) {
        SubItem subItem = get(item);
        if (subItem.HIGHLIGHTTIP != null) {
            String tip = subItem.HIGHLIGHTTIP.getHighlightTip(CraftTweakerMC.getIItemStack(item), displayName);
            if (tip != null) return tip;
        }

        return super.getHighlightTip(item, displayName);
    }

    @Override
    public boolean onDroppedByPlayer(@Nonnull ItemStack item, @Nonnull EntityPlayer player) {
        SubItem subItem = get(item);
        if (subItem.PLAYERDROP != null) return subItem.PLAYERDROP.onDroppedByPlayer(CraftTweakerMC.getIItemStackMutable(item), CraftTweakerMC.getIPlayer(player));
        else return super.onDroppedByPlayer(item, player);
    }

    // MAYBE ???
    @Override
    public boolean doesSneakBypassUse(@Nonnull ItemStack stack, @Nonnull IBlockAccess world,  @Nonnull BlockPos pos, @Nonnull EntityPlayer player) {
        return super.doesSneakBypassUse(stack, world, pos, player);
    }

    @Override
    public boolean isShield(@Nonnull ItemStack stack, @Nullable EntityLivingBase entity) {
        return super.isShield(stack, entity);
    }

    // maybe after the clientside crafttweaker addon mod (if i ever make it :weary:)
    @Nullable
    @Override
    public FontRenderer getFontRenderer(@Nonnull ItemStack stack) {
        return super.getFontRenderer(stack);
    }

    // ARMOR STUFF (WILL BE HANDLED LATER)
    @Override
    public void onArmorTick(@Nonnull World world, @Nonnull EntityPlayer player, @Nonnull ItemStack itemStack) {
        super.onArmorTick(world, player, itemStack);
    }

    @Override
    public boolean isValidArmor(@Nonnull ItemStack stack, @Nonnull EntityEquipmentSlot armorType, @Nonnull Entity entity) {
        return super.isValidArmor(stack, armorType, entity);
    }

    @Nullable
    @Override
    public String getArmorTexture(@Nonnull ItemStack stack, @Nonnull Entity entity, @Nonnull EntityEquipmentSlot slot, @Nonnull String type) {
        return super.getArmorTexture(stack, entity, slot, type);
    }

    @Nullable
    @Override
    public ModelBiped getArmorModel(@Nonnull EntityLivingBase entityLiving, @Nonnull ItemStack itemStack, @Nonnull EntityEquipmentSlot armorSlot, @Nonnull ModelBiped _default) {
        return super.getArmorModel(entityLiving, itemStack, armorSlot, _default);
    }

    @Override
    public void renderHelmetOverlay(@Nonnull ItemStack stack, @Nonnull EntityPlayer player, @Nonnull ScaledResolution resolution, float partialTicks) {
        super.renderHelmetOverlay(stack, player, resolution, partialTicks);
    }

    @Override
    public ResourceLocation getLocation(World world, EntityPlayer player, ItemStack stack) {
        SubItem subItem = get(stack);
        return subItem.HALOTEXTURE != null ? GeneralUtil.getTextureLocation(subItem.HALOTEXTURE.getTexture(CraftTweakerMC.getIWorld(world), CraftTweakerMC.getIPlayer(player), CraftTweakerMC.getIItemStack(stack))) : null;
    }

    @Override
    public int getSpread(World world, EntityPlayer player, ItemStack stack) {
        SubItem subItem = get(stack);
        return subItem.HALOSPREAD != null ? subItem.HALOSPREAD.getSpread(CraftTweakerMC.getIWorld(world), CraftTweakerMC.getIPlayer(player), CraftTweakerMC.getIItemStack(stack)) : 1;
    }

    @Override
    public int getColor(World world, EntityPlayer player, ItemStack stack) {
        SubItem subItem = get(stack);
        return subItem.HALOCOLOR != null ? subItem.HALOCOLOR.getColor(CraftTweakerMC.getIWorld(world), CraftTweakerMC.getIPlayer(player), CraftTweakerMC.getIItemStack(stack)) : 0xFF000000;
    }

    @Override
    public boolean shouldPulse(World world, EntityPlayer player, ItemStack stack) {
        SubItem subItem = get(stack);
        return subItem.PULSE != null && subItem.PULSE.shouldPulse(CraftTweakerMC.getIWorld(world), CraftTweakerMC.getIPlayer(player), CraftTweakerMC.getIItemStack(stack));
    }
}
