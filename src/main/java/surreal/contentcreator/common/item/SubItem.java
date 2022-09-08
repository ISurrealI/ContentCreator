package surreal.contentcreator.common.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.*;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import stanhebben.zenscript.annotations.Optional;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.functions.item.*;
import surreal.contentcreator.util.CTUtil;
import surreal.contentcreator.util.GeneralUtil;
import surreal.contentcreator.util.IHaloItem;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

@SuppressWarnings("unused")

@ZenRegister
@ZenClass("contentcreator.item.SubItem")
public class SubItem {
    // Functions
    public IItemUnlocalizedNameFunc UNLOCNAME = null;
    public IItemUseFunc ITEMUSE = null;
    public IItemDestroySpeedFunc DESTROYSPEED = null;
    public IItemFloatStackFunc XPREPAIR = null;
    public IItemBlockStartBreakFunc BLOCKSTARTBREAK = null;
    public IItemUsingTickFunc USINGTICK = null;
    public IItemLeftClickEntityFunc LEFTCLICKENTITY = null;
    public IItemContainerItemFunc CONTAINERITEM = null;
    public IItemEntityLifespanFunc ENTITYLIFESPAN = null;
    public IItemCustomEntityFunc CUSTOMENTITY = null;
    public IItemEntityItemUpdateFunc ENTITYUPDATE = null;
    public IItemEntitySwingFunc ENTITYSWING = null;
    public IItemUseFunc ITEMUSEFIRST = null;
    public IItemUseFinishFunc ITEMUSEFINISH = null;
    // damage
    public IItemIntStackFunc ITEMDAMAGE = null;
    public IItemBooleanStackFunc SHOWBAR = null;
    public IItemDoubleStackFunc DURABILITYDISPLAY = null;
    public IItemIntStackFunc COLORDISPLAY = null;
    public IItemIntStackFunc MAXDAMAGE = null;
    public IItemSetDamageFunc SETDAMAGE = null;
    // ------
    public IItemDestroyCreative DESTROYCREATIVE = null;
    public IItemHarvestBlockFunc HARVESTBLOCK = null;
    public IItemIntStackFunc STACKLIMIT = null;
    public IItemReequipAnimation REEQUIP = null;
    public IItemBlockBreakResetFunc BREAKRESET = null;
    public IItemContinueUsingFunc CONTINUEUSING = null;
    public IItemStringStackFunc CREATORMODID = null;
    public IItemIntStackFunc ENCHANTABILITY = null;
    public IItemApplyEnchantmentTableFunc APPLYENCHTABLE = null;
    public IItemBooleanStackFunc BEACONPAYMENT = null;
    public IItemCreatedFunc CREATED = null;
    public IItemIntStackFunc BURNTIME = null;
    public IItemDisableShieldFunc DISABLESHIED = null;
    public IItemStringStackFunc RARITY = null;
    public IItemBooleanStackFunc EFFECT = null;
    public IItemStringStackFunc ACTION = null;
    public IItemIntStackFunc MAXUSEDURATION = null;
    public IItemRightClick RIGHTCLICK = null;
    public IItemHitEntityFunc ENTITYHIT = null;
    public IItemInteractionEntityFunc ENTITYINTERACTION = null;
    public IItemColorFunc COLOR = null;
    public IItemInformation INFO = null;
    public IItemBlockDestroy DESTROYBLOCK = null;
    public IItemHighlightTip HIGHLIGHTTIP = null;
    public IItemPlayerDrop PLAYERDROP = null;

    // Tool
    public IItemAttributeModifier ATTACKDAMAGE = null;
    public IItemAttributeModifier ATTACKSPEED = null;

    // Food
    public IItemIntStackFunc HEALAMOUNT = null;
    public IItemFloatStackFunc SATURATION = null;
    public IItemBooleanStackFunc WOLFSFAVORITE = null;
    public IItemBooleanStackFunc ALWAYSEDIBLE = null;

    // Effects
    public IItemEffectHaloTexture HALOTEXTURE = null;
    public IItemEffectHaloColor HALOCOLOR = null;
    public IItemEffectHaloSpread HALOSPREAD = null;

    public IItemEffectPulse PULSE = null;

    // Variables
    public final int meta;
    public Map<ResourceLocation, IItemPropertyFunc> itemProperties = null;
    public Map<String, Integer> toolClasses = null;
    public List<String> oreList = null;

    public SubItem(int meta) {
        this.meta = meta;
    }

    @ZenMethod
    public SubItem addProperty(String name, IItemPropertyFunc func) {
        if (itemProperties == null) itemProperties = new Object2ObjectOpenHashMap<>();
        itemProperties.put(new ResourceLocation(ModValues.MODID, name), func);
        return this;
    }

    @ZenMethod
    public SubItem setUnlocalizedName(IItemUnlocalizedNameFunc func) {
        this.UNLOCNAME = func;
        return this;
    }

    @ZenMethod
    public SubItem setUnlocalizedName(String unlocName) {
        this.UNLOCNAME = stack -> unlocName;
        return this;
    }

    @ZenMethod
    public SubItem setItemUse(IItemUseFunc func) {
        this.ITEMUSE = func;
        return this;
    }

    @ZenMethod
    public SubItem setDestroySpeed(IItemDestroySpeedFunc func) {
        this.DESTROYSPEED = func;
        return this;
    }

    @ZenMethod
    public SubItem setDestroySpeed(float value) {
        this.DESTROYSPEED = (stack, state) -> value;
        return this;
    }

        @ZenMethod
        public SubItem setXpRepair(IItemFloatStackFunc func) {
            this.XPREPAIR = func;
        return this;
    }

    @ZenMethod
    public SubItem setXpRepair(float ratio) {
        this.XPREPAIR = stack -> ratio;
        return this;
    }

    @ZenMethod
    public SubItem setBlockStartBreak(IItemBlockStartBreakFunc func) {
        this.BLOCKSTARTBREAK = func;
        return this;
    }

    @ZenMethod
    public SubItem setUsingTick(IItemUsingTickFunc func) {
        this.USINGTICK = func;
        return this;
    }

    @ZenMethod
    public SubItem setLeftClickEntity(IItemLeftClickEntityFunc func) {
        this.LEFTCLICKENTITY = func;
        return this;
    }

    @ZenMethod
    public SubItem setContainerItem(IItemContainerItemFunc func) {
        this.CONTAINERITEM = func;
        return this;
    }

    @ZenMethod
    public SubItem setContainerItem(IItemStack stack) {
        this.CONTAINERITEM = stack1 -> stack;
        return this;
    }

    @ZenMethod
    public SubItem setContainerItem(String stack) {
        this.CONTAINERITEM = stack1 -> CraftTweakerMC.getIItemStack(GeneralUtil.getStackFromString(stack));
        return this;
    }

    @ZenMethod
    public SubItem setEntityLifespan(IItemEntityLifespanFunc func) {
        this.ENTITYLIFESPAN = func;
        return this;
    }

    @ZenMethod
    public SubItem setEntityLifespan(int time) {
        this.ENTITYLIFESPAN = (stack, world) -> time;
        return this;
    }

    @ZenMethod
    public SubItem setCustomEntity(IItemCustomEntityFunc func) {
        this.CUSTOMENTITY = func;
        return this;
    }

    @ZenMethod
    public SubItem setEntityItemUpdate(IItemEntityItemUpdateFunc func) {
        this.ENTITYUPDATE = func;
        return this;
    }

    @ZenMethod
    public SubItem setEntitySwing(IItemEntitySwingFunc func) {
        this.ENTITYSWING = func;
        return this;
    }

    @ZenMethod
    public SubItem setItemDamageGetter(IItemIntStackFunc func) {
        this.ITEMDAMAGE = func;
        return this;
    }

    @ZenMethod
    public SubItem shouldShowDurabilityBar(IItemBooleanStackFunc func) {
        this.SHOWBAR = func;
        return this;
    }

    @ZenMethod
    public SubItem setDurabilityDisplay(IItemDoubleStackFunc func) {
        this.DURABILITYDISPLAY = func;
        return this;
    }

    @ZenMethod
    public SubItem setDurabilityColor(IItemIntStackFunc func) {
       this.COLORDISPLAY = func;
       return this;
    }

    @ZenMethod
    public SubItem setMaxDamageGetter(IItemIntStackFunc func) {
        this.MAXDAMAGE = func;
        return this;
    }

    @ZenMethod
    public SubItem setDamageSetter(IItemSetDamageFunc func) {
        this.SETDAMAGE = func;
        return this;
    }

    // Reequip animation happens even though shouldCauseReequipAnimation is changed, right clicking on creative sets damage of item to 1 even though it shouldn't :face_with_raised_eyebrow:
    // If you know how to fix i would appreciate if you help me to fix it
    @ZenMethod
    public SubItem setTool(String tool, int level, int maxDamage, float efficiency, @Optional int enchantability, @Optional double attackDamage, @Optional double attackSpeed) {
        this.MAXDAMAGE = stack -> maxDamage;
        this.ITEMDAMAGE = stack -> {
            ItemStack internal = ((ItemStack) stack.getInternal());
            NBTTagCompound tag = internal.getTagCompound();
            if (tag != null) return tag.getInteger("Damage");
            else {
                internal.setTagCompound(new NBTTagCompound());
                return 0;
            }
        };
        this.SETDAMAGE = (stack, damageToSet) -> {
            ItemStack internal = ((ItemStack) stack.getInternal());
            NBTTagCompound tag = internal.getTagCompound();
            if (tag != null) tag.setInteger("Damage", damageToSet);
            else internal.setTagCompound(new NBTTagCompound());
        };

        this.DESTROYBLOCK = (world, stack, state, pos, entity) -> {
            if (!world.isRemote() && state.getBlockHardness(world, pos) != 0D) stack.damageItem(1, entity);
            return true;
        };
        this.ENTITYHIT = (stack, entity, attacker) -> {
            stack.damageItem(2, attacker);
            return true;
        };
        this.DESTROYSPEED = (stack, state) -> {
            for (String classes : stack.getToolClasses()) {
                if (state.getBlock().getDefinition().isToolEffective(classes, state)) return efficiency;
            }
            return 1.0F;
        };
        this.HARVESTBLOCK = (state, stack) -> {
            IBlockState st = (IBlockState) state.getInternal();
            Block block = st.getBlock();

            if (tool.equalsIgnoreCase("shovel")) return block instanceof BlockSnow || block instanceof BlockSnowBlock;
            return st.getBlock().getHarvestTool(st) == null || (st.getBlock().getHarvestLevel(st) <= level && st.getBlock().getHarvestTool(st).equalsIgnoreCase(tool));
        };

        this.ITEMUSE = (pl, world, position, hand, face, hitX, hitY, hitZ) -> {
            EntityPlayer player = (EntityPlayer) pl.getInternal();
            ItemStack itemstack = player.getHeldItem(CTUtil.getHand(hand));

            BlockPos pos = (BlockPos) position.getInternal();
            EnumFacing facing = (EnumFacing) face.getInternal();

            World worldIn = (World) world.getInternal();

            if (!player.canPlayerEdit(pos.offset(facing), facing, itemstack)) return "FAIL";
            else {
                IBlockState iblockstate = worldIn.getBlockState(pos);
                Block block = iblockstate.getBlock();

                if (facing != EnumFacing.DOWN && worldIn.isAirBlock(pos.up())) {
                    if (tool.equalsIgnoreCase("shovel") && block == Blocks.GRASS) {
                        worldIn.playSound(player, pos, SoundEvents.ITEM_SHOVEL_FLATTEN, SoundCategory.BLOCKS, 1.0F, 1.0F);

                        if (!worldIn.isRemote) {
                            worldIn.setBlockState(pos, Blocks.GRASS_PATH.getDefaultState(), 11);
                        }

                        return "SUCCESS";
                    }
                    if (tool.equalsIgnoreCase("hoe") && block == Blocks.GRASS || block == Blocks.GRASS_PATH || block == Blocks.DIRT) {
                        worldIn.playSound(player, pos, SoundEvents.ITEM_HOE_TILL, SoundCategory.BLOCKS, 1.0F, 1.0F);

                        if (!worldIn.isRemote) {
                            if (block.getMetaFromState(iblockstate) != 1) worldIn.setBlockState(pos, Blocks.FARMLAND.getDefaultState(), 11);
                            else worldIn.setBlockState(pos, Blocks.DIRT.getDefaultState(), 11);
                        }

                        return "SUCCESS";
                    }
                }
            }

            return "PASS";
        };
        this.REEQUIP = (oldStack, newStack, slotChanged) -> {
            ItemStack stack = ((ItemStack) oldStack.getInternal());
            ItemStack stack1 = ((ItemStack) newStack.getInternal());
            return stack.getItem() != stack1.getItem() || stack.getMetadata() != stack1.getMetadata();
        };

        return this.addToolClass(tool, level).setAttackDamage(attackDamage).setAttackSpeed(attackSpeed).setEnchantability(enchantability);
    }

    @ZenMethod
    public SubItem setWeapon(int maxDamage, double attackDamage, @Optional double attackSpeed, @Optional int enchantability) {
        if (attackSpeed == 0F) attackSpeed = -2.4000000953674316D;
        this.ENTITYHIT = (stack, entity, attacker) -> {
            stack.damageItem(1, attacker);
            return true;
        };
        this.DESTROYBLOCK = (world, stack, state, pos, entity) -> {
            if (!world.isRemote() && state.getBlockHardness(world, pos) != 0D) stack.damageItem(2, entity);
            return true;
        };
        this.DESTROYSPEED = (stack, st) -> {
            IBlockState state = (IBlockState) st.getInternal();
            Block block = state.getBlock();
            if (block instanceof BlockWeb) return 15F;
            else {
                Material material = state.getMaterial();
                return material != Material.PLANTS && material != Material.VINE && material != Material.CORAL && material != Material.LEAVES && material != Material.GOURD ? 1.0F : 1.5F;
            }
        };
        this.DESTROYCREATIVE = (world, pos, stack, player) -> false;

        return this.setAttackDamage(attackDamage).setAttackSpeed(attackSpeed).setEnchantability(enchantability);
    }

    @ZenMethod
    public SubItem setAttackDamage(IItemAttributeModifier func) {
        this.ATTACKDAMAGE = func;
        return this;
    }

    @ZenMethod
    public SubItem setAttackSpeed(IItemAttributeModifier func) {
        this.ATTACKSPEED = func;
        return this;
    }

    @ZenMethod
    public SubItem setAttackDamage(double value) {
        if (value > 0D) {
            this.ATTACKDAMAGE = slot -> {
                EntityEquipmentSlot equipmentSlot = (EntityEquipmentSlot) slot.getInternal();
                if (equipmentSlot == EntityEquipmentSlot.MAINHAND) return value;
                return 0D;
            };
        }
        return this;
    }

    @ZenMethod
    public SubItem setAttackSpeed(double value) {
        if (value > 0D) {
            this.ATTACKSPEED = slot -> {
                EntityEquipmentSlot equipmentSlot = (EntityEquipmentSlot) slot.getInternal();
                if (equipmentSlot == EntityEquipmentSlot.MAINHAND) return value;
                return 0D;
            };
        }
        return this;
    }

    @ZenMethod
    public SubItem canDestroyInCreative(IItemDestroyCreative func) {
        this.DESTROYCREATIVE = func;
        return this;
    }

    @ZenMethod
    public SubItem disableCreativeBlockDestroy() {
        this.DESTROYCREATIVE = (world, pos, stack, player) -> false;
        return this;
    }

    @ZenMethod
    public SubItem canHarvestBlock(IItemHarvestBlockFunc func) {
        this.HARVESTBLOCK = func;
        return this;
    }

    @ZenMethod
    public SubItem setMaxStackSize(IItemIntStackFunc func) {
        this.STACKLIMIT = func;
        return this;
    }

    @ZenMethod
    public SubItem setMaxStackSize(int value) {
        this.STACKLIMIT = stack -> value;
        return this;
    }

    @ZenMethod
    public SubItem addToolClass(String tool, int level) {
        if (this.toolClasses == null) this.toolClasses = new Object2ObjectOpenHashMap<>();
        this.toolClasses.put(tool, level);
        return this;
    }

    @ZenMethod
    public SubItem causeReequipAnimation(IItemReequipAnimation func) {
        this.REEQUIP = func;
        return this;
    }

    @ZenMethod
    public SubItem causeBlockBreakReset(IItemBlockBreakResetFunc func) {
        this.BREAKRESET = func;
        return this;
    }

    @ZenMethod
    public SubItem continueUsing(IItemContinueUsingFunc func) {
        this.CONTINUEUSING = func;
        return this;
    }

    @ZenMethod
    public SubItem setContinueUsing() {
        this.CONTINUEUSING = (oldStack, newStack) -> true;
        return this;
    }

    @ZenMethod
    public SubItem setCreatorModId(IItemStringStackFunc func) {
        this.CREATORMODID = func;
        return this;
    }

    @ZenMethod
    public SubItem setEnchantability(IItemIntStackFunc func) {
        this.ENCHANTABILITY = func;
        return this;
    }

    @ZenMethod
    public SubItem setEnchantability(int value) {
        this.ENCHANTABILITY = stack -> value;
        return this;
    }

    @ZenMethod
    public SubItem canApplyEnchantment(IItemApplyEnchantmentTableFunc func) {
        this.APPLYENCHTABLE = func;
        return this;
    }

    @ZenMethod
    public SubItem setBeaconPayment(IItemBooleanStackFunc func) {
        this.BEACONPAYMENT = func;
        return this;
    }

    @ZenMethod
    public SubItem setBeaconPayment() {
        this.BEACONPAYMENT = stack -> true;
        return this;
    }

    @ZenMethod
    public SubItem setItemUseFirst(IItemUseFunc func) {
        this.ITEMUSEFIRST = func;
        return this;
    }

    @ZenMethod
    public SubItem setItemCreated(IItemCreatedFunc func) {
        this.CREATED = func;
        return this;
    }

    @ZenMethod
    public SubItem setBurnTime(IItemIntStackFunc func) {
        this.BURNTIME = func;
        return this;
    }

    @ZenMethod
    public SubItem setBurnTime(int value) {
        this.BURNTIME = stack -> value;
        return this;
    }

    @ZenMethod
    public SubItem canDisableShield(IItemDisableShieldFunc func) {
        this.DISABLESHIED = func;
        return this;
    }

    @ZenMethod
    public SubItem canDisableShield() {
        this.DISABLESHIED = (stack, shield, entity, attacker) -> true;
        return this;
    }

    @ZenMethod
    public SubItem setTooltip(IItemInformation func) {
        this.INFO = func;
        return this;
    }

    @ZenMethod
    public SubItem setTooltip(String... string) {
        this.INFO = (stack, world, isAdvanced) -> string;
        return this;
    }

    @ZenMethod
    public SubItem setOres(String... ores) {
        if (this.oreList == null) this.oreList = new ArrayList<>();
        Collections.addAll(this.oreList, ores);
        return this;
    }

    @ZenMethod
    public SubItem setRarity(IItemStringStackFunc func) {
        this.RARITY = func;
        return this;
    }

    @ZenMethod
    public SubItem setRarity(String name) {
        this.RARITY = stack -> name;
        return this;
    }

    @ZenMethod
    public SubItem setEffect(IItemBooleanStackFunc func) {
        this.EFFECT = func;
        return this;
    }

    @ZenMethod
    public SubItem setEffect() {
        this.EFFECT = stack -> true;
        return this;
    }

    @ZenMethod
    public SubItem setUseAction(IItemStringStackFunc func) {
        this.ACTION = func;
        return this;
    }

    @ZenMethod
    public SubItem setUseAction(String value) {
        this.ACTION = stack -> value;
        return this;
    }

    @ZenMethod
    public SubItem setMaxUseDuration(IItemIntStackFunc func) {
        this.MAXUSEDURATION = func;
        return this;
    }

    @ZenMethod
    public SubItem setMaxUseDuration(int value) {
        this.MAXUSEDURATION = stack -> value;
        return this;
    }

    @ZenMethod
    public SubItem setRightClick(IItemRightClick func) {
        this.RIGHTCLICK = func;
        return this;
    }

    // Food Stuff
    @ZenMethod
    public SubItem setHealAmount(IItemIntStackFunc func) {
        this.HEALAMOUNT = func;
        return this;
    }

    @ZenMethod
    public SubItem setHealAmount(int amount) {
        this.HEALAMOUNT = stack -> amount;
        return this;
    }

    @ZenMethod
    public SubItem setSaturation(IItemFloatStackFunc func) {
        this.SATURATION = func;
        return this;
    }

    @ZenMethod
    public SubItem setSaturation(float amount) {
        this.SATURATION = stack -> amount;
        return this;
    }

    @ZenMethod
    public SubItem setWolfsFavorite(IItemBooleanStackFunc func) {
        this.WOLFSFAVORITE = func;
        return this;
    }

    @ZenMethod
    public SubItem setWolfsFavorite() {
        this.WOLFSFAVORITE = stack -> true;
        return this;
    }

    @ZenMethod
    public SubItem setAlwaysEdible(IItemBooleanStackFunc func) {
        this.ALWAYSEDIBLE = func;
        return this;
    }

    @ZenMethod
    public SubItem setAlwaysEdible() {
        this.ALWAYSEDIBLE = stack -> true;
        return this;
    }

    @ZenMethod
    public SubItem setUseFinish(IItemUseFinishFunc func) {
        this.ITEMUSEFINISH = func;
        return this;
    }

    @ZenMethod
    public SubItem setEntityHit(IItemHitEntityFunc func) {
        this.ENTITYHIT = func;
        return this;
    }

    @ZenMethod
    public SubItem setEntityInteract(IItemInteractionEntityFunc func) {
        this.ENTITYINTERACTION = func;
        return this;
    }

    @ZenMethod
    public SubItem setColor(IItemColorFunc func) {
        this.COLOR = func;
        return this;
    }

    @ZenMethod
    public SubItem setBlockDestroyed(IItemBlockDestroy func) {
        this.DESTROYBLOCK = func;
        return this;
    }

    @ZenMethod
    public SubItem setHighlightTip(IItemHighlightTip func) {
        this.HIGHLIGHTTIP = func;
        return this;
    }

    @ZenMethod
    public SubItem setPlayerDropped(IItemPlayerDrop func) {
        this.PLAYERDROP = func;
        return this;
    }

    @ZenMethod
    public SubItem setBackgroundTexture(IItemEffectHaloTexture func) {
        this.HALOTEXTURE = func;
        return this;
    }

    @ZenMethod
    public SubItem setBackgroundTexture(String texture) {
        this.HALOTEXTURE = (world, player, stack) -> texture;
        return this;
    }

    @ZenMethod
    public SubItem setBackgroundTexture(int halo) {
        this.HALOTEXTURE = (world, player, stack) -> IHaloItem.getHaloTexture(halo);
        return this;
    }

    @ZenMethod
    public SubItem setBackgroundSize(IItemEffectHaloSpread func) {
        this.HALOSPREAD = func;
        return this;
    }

    @ZenMethod
    public SubItem setBackgroundSize(int size) {
        this.HALOSPREAD = (world, player, stack) -> size;
        return this;
    }

    @ZenMethod
    public SubItem setBackgroundColor(IItemEffectHaloColor func) {
        this.HALOCOLOR = func;
        return this;
    }

    @ZenMethod
    public SubItem setBackgroundColor(int color) {
        this.HALOCOLOR = (world, player, stack) -> color;
        return this;
    }

    @ZenMethod
    public SubItem setPulseEffect(IItemEffectPulse func) {
        this.PULSE = func;
        return this;
    }

    @ZenMethod
    public SubItem setPulseEffect() {
        this.PULSE = (world, player, stack) -> true;
        return this;
    }

    @ZenMethod
    public SubItem setBlock(crafttweaker.api.block.IBlockState state) {
        this.ITEMUSE = (iplayer, world, ipos, hand, ifacing, hitX, hitY, hitZ) -> {
            World worldIn = CraftTweakerMC.getWorld(world);
            BlockPos pos = CraftTweakerMC.getBlockPos(ipos);
            EnumFacing facing = CraftTweakerMC.getFacing(ifacing);
            EntityPlayer player = CraftTweakerMC.getPlayer(iplayer);

            IBlockState iblockstate = worldIn.getBlockState(pos);
            Block block = iblockstate.getBlock();

            if (!block.isReplaceable(worldIn, pos))
            {
                pos = pos.offset(facing);
            }

            ItemStack itemstack = player.getHeldItem(CraftTweakerMC.getHand(hand));

            if (!itemstack.isEmpty() && player.canPlayerEdit(pos, facing, itemstack) && worldIn.mayPlace(CraftTweakerMC.getBlockState(state).getBlock(), pos, false, facing, player))
            {
                int i = state.getMeta();
                IBlockState iblockstate1 = CraftTweakerMC.getBlockState(state).getBlock().getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, i, player, CraftTweakerMC.getHand(hand));

                if (placeBlockAt(itemstack, player, worldIn, pos, facing, hitX, hitY, hitZ, iblockstate1))
                {
                    iblockstate1 = worldIn.getBlockState(pos);
                    SoundType soundtype = iblockstate1.getBlock().getSoundType(iblockstate1, worldIn, pos, player);
                    worldIn.playSound(player, pos, soundtype.getPlaceSound(), SoundCategory.BLOCKS, (soundtype.getVolume() + 1.0F) / 2.0F, soundtype.getPitch() * 0.8F);
                    itemstack.shrink(1);
                }

                return "SUCCESS";
            }

            else return "FAIL";
        };

        return this;
    }

    private boolean placeBlockAt(ItemStack stack, EntityPlayer player, World world, BlockPos pos, EnumFacing side, float hitX, float hitY, float hitZ, IBlockState newState)
    {
        if (!world.setBlockState(pos, newState, 11)) return false;

        IBlockState state = world.getBlockState(pos);
        if (state.getBlock() == newState.getBlock())
        {
            ItemBlock.setTileEntityNBT(world, player, pos, stack);
            newState.getBlock().onBlockPlacedBy(world, pos, state, player, stack);

            if (player instanceof EntityPlayerMP)
                CriteriaTriggers.PLACED_BLOCK.trigger((EntityPlayerMP)player, pos, stack);
        }

        return true;
    }
}
