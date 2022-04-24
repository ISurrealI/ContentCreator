package surreal.contentcreator.common.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.enchantments.IEnchantmentDefinition;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.stats.StatList;
import net.minecraft.util.*;
import net.minecraft.world.World;
import net.minecraftforge.common.IRarity;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Optional;
import squeek.applecore.api.food.FoodValues;
import squeek.applecore.api.food.IEdible;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.util.CTUtil;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.proxy.CommonProxy;
import surreal.contentcreator.util.TintColor;

import java.util.*;

// Implement damage with nbt
@ZenRegister
@ZenClass("mods.contentcreator.item.Item")
@Optional.Interface(iface = "squeek.applecore.api.food.IEdible", modid = "applecore")
public class ItemBase extends Item implements IEdible {
    public static final List<ItemBase> ITEMS = new ArrayList<>();

    public final List<ValueItem> METAITEMS;
    public Map<Integer, List<TintColor>> COLOR = null;

    public ItemBase() {
        ITEMS.add(this);
        METAITEMS = new ArrayList<>();
    }

    @ZenMethod
    public static ItemBase createItem(String name) {
        return (ItemBase) new ItemBase().setRegistryName(ModValues.MODID, name).setCreativeTab(CreativeTabs.SEARCH);
    }

    @ZenMethod
    public static ValueItem create() {
        return new ValueItem();
    }

    @ZenMethod
    public ItemBase addItem(ValueItem... item) {
        this.METAITEMS.addAll(Arrays.asList(item));
        return this;
    }

    @ZenMethod
    public void register() {
        this.setHasSubtypes(METAITEMS.size() > 1);

        if (METAITEMS.size() > 0) {
            for (int i = 0; i < METAITEMS.size(); i++) {
                ValueItem value = METAITEMS.get(i);

                if (value.tintColor != null) {
                    if (COLOR == null) COLOR = new HashMap<>();
                    COLOR.putIfAbsent(i, value.tintColor);
                }
            }
        } else {
            addItem(create());
        }

        CommonProxy.ITEMS.add((ItemBase) this.setUnlocalizedName(ModValues.MODID + "." + this.getRegistryName().getResourcePath()));
    }

    private ValueItem get(ItemStack stack) {
        return METAITEMS.get(stack.getMetadata());
    }

    @Override
    public String getUnlocalizedName(ItemStack stack) {
        ValueItem value = get(stack);

        return value.unlocName == null ? this.getUnlocalizedName() + (METAITEMS.size() > 1 ? "." + stack.getMetadata() : "") : "item." + value.unlocName;
    }

    @Override
    public int getItemStackLimit(ItemStack stack) {
        return get(stack).stackSize;
    }

    @Override
    public float getDestroySpeed(ItemStack stack, IBlockState state) {
        return get(stack).destroySpeed;
    }

    @Override
    public Set<String> getToolClasses(ItemStack stack) {
        return get(stack).toolClasses.size() > 0 ? get(stack).toolClasses.keySet() : super.getToolClasses(stack);
    }

    @Override
    public boolean hasEffect(ItemStack stack) {
        return get(stack).hasEffect;
    }

    @Override
    public IRarity getForgeRarity(ItemStack stack) {
        return get(stack).rarity;
    }

    @Override
    public int getItemEnchantability(ItemStack stack) {
        return get(stack).enchantability;
    }

    @Override
    public boolean canApplyAtEnchantingTable(ItemStack stack, Enchantment enchantment) {
        return get(stack).enchantments != null && Arrays.stream(get(stack).enchantments).anyMatch(a -> a == enchantment);
    }

    @Override
    public boolean isBeaconPayment(ItemStack stack) {
        return get(stack).beaconPayment;
    }

    @Override
    public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
        if (this.isInCreativeTab(tab)) {
            for (int i = 0; i < METAITEMS.size(); i++) {
                items.add(new ItemStack(this, 1, i));
            }
        }
    }

    public ModelResourceLocation getModel(int meta) {
        ValueItem value = METAITEMS.get(meta);

        return new ModelResourceLocation(new ResourceLocation((value.modelLocation == null ? this.getRegistryName().toString().replace(".", "_") + (METAITEMS.size() > 1 ? "_" + meta : "") : ModValues.MODID + ":" + value.modelLocation)), "inventory");
    }

    @Optional.Method(modid = "applecore")
    @Override
    public FoodValues getFoodValues(ItemStack stack) {
        ValueItem value = get(stack);

        return new FoodValues(value.heal, value.saturation);
    }

    @Optional.Method(modid = "applecore")
    public void ACCompatability(ItemStack stack, EntityPlayer player) {
        FoodValues values = getFoodValues(stack);
        player.getFoodStats().addStats(values.hunger, values.saturationModifier);
    }

    @Override
    public ItemStack onItemUseFinish(ItemStack stack, World worldIn, EntityLivingBase entity) {

        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;

            stack.shrink(1);
            worldIn.playSound(player, player.posX, player.posY, player.posZ, SoundEvents.ENTITY_PLAYER_BURP, SoundCategory.PLAYERS, 0.5F, worldIn.rand.nextFloat() * 0.1F + 0.9F);

            if (Loader.isModLoaded("applecore")) ACCompatability(stack, player);
            else {
                ValueItem value = get(stack);
                player.getFoodStats().addStats(value.heal, value.saturation);
            }

            player.addStat(StatList.getObjectUseStats(this));
            if (player instanceof EntityPlayerMP)
                CriteriaTriggers.CONSUME_ITEM.trigger((EntityPlayerMP)player, stack);
        }

        return stack;
    }

    @Override
    public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer player, EntityLivingBase target, EnumHand hand) {
        ValueItem value = get(stack);

        if (!player.world.isRemote) {
            if (target instanceof EntityWolf && value.heal > 0 && value.wolfFood) {
                if (!player.isCreative()) stack.shrink(1);
                target.heal(value.heal);
                return true;
            }
        }

        return false;
    }

    @Override
    public int getMaxItemUseDuration(ItemStack stack) {
        return get(stack).useDuration;
    }

    @Override
    public EnumAction getItemUseAction(ItemStack stack) {
        return get(stack).action;
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer player, EnumHand handIn) {
        ItemStack stack = player.getHeldItem(handIn);
        ValueItem value = get(stack);

        if (value.heal > 0 && player.canEat(value.alwaysEdible)) {
            player.setActiveHand(handIn);
            return new ActionResult<>(EnumActionResult.SUCCESS, stack);
        } else return new ActionResult<>(EnumActionResult.FAIL, stack);
    }

    @ZenRegister
    @ZenClass("mods.contentcreator.item.ValueItem")
    public static class ValueItem {
        public String unlocName = null;

        // model
        public String modelLocation = null;

        public int maxDamage = 0;
        public int entityHitDamage = 0;
        public int blockBreakDamage = 0;
        public boolean showDurability = maxDamage > 0;

        public float attackDamage = 1F;

        public ItemStack repairStack = ItemStack.EMPTY;
        public boolean repairable = !repairStack.isEmpty() && maxDamage > 0;
        public float xpRepairRatio = 2F;

        public int stackSize = 64;
        public float destroySpeed = 1F; // 0 to disable block breaking
        public Map<String, Integer> toolClasses = null;
        public boolean hasEffect = false;
        public EnumRarity rarity = EnumRarity.COMMON;
        public int enchantability = 0;
        public Enchantment[] enchantments = null;
        public boolean beaconPayment = false;
        public List<TintColor> tintColor = null;
        public EnumAction action = EnumAction.NONE;

        // food
        public int heal = 0;
        public float saturation = 0F;
        public boolean wolfFood = false;
        public boolean alwaysEdible = false;

        public int useDuration = 0;

        // ores
        public String[] ores = null;

        public ValueItem() {
            this.toolClasses = new HashMap<>();
        }

        @ZenMethod("setUnlocalizedName")
        public ValueItem setUnlocName(String unlocName) {
            this.unlocName = unlocName;
            return this;
        }

        @ZenMethod
        public ValueItem setModelLocation(String modelLocation) {
            this.modelLocation = modelLocation;
            return this;
        }

        @ZenMethod
        public ValueItem setMaxDamage(int maxDamage) {
            this.maxDamage = maxDamage;
            return this;
        }

        @ZenMethod
        public ValueItem setRarity(String rarity) {
            this.rarity = CTUtil.getRarity(rarity);
            return this;
        }

        @ZenMethod
        public ValueItem setHasEffect() {
            this.hasEffect = true;
            return this;
        }

        @ZenMethod
        public ValueItem setTool(String tool, int level) {
            toolClasses.putIfAbsent(tool, level);
            return this;
        }

        @ZenMethod
        public ValueItem setAttackDamage(float attackDamage) {
            this.attackDamage = attackDamage;
            return this;
        }

        @ZenMethod
        public ValueItem setAllowedEnchantments(IEnchantmentDefinition... enchantment) {
            this.enchantments = CTUtil.getEnchantments(enchantment);
            return this;
        }

        @ZenMethod
        public ValueItem setBlockBreakDamage(int blockBreakDamage) {
            this.blockBreakDamage = blockBreakDamage;
            return this;
        }

        @ZenMethod
        public ValueItem setDestroySpeed(float destroySpeed) {
            this.destroySpeed = destroySpeed;
            return this;
        }

        @ZenMethod
        public ValueItem setEntityHitDamage(int entityHitDamage) {
            this.entityHitDamage = entityHitDamage;
            return this;
        }

        @ZenMethod
        public ValueItem setEfficiency(float efficiency) {
            this.destroySpeed = efficiency;
            return this;
        }

        @ZenMethod
        public ValueItem setEnchantability(int enchantability) {
            this.enchantability = enchantability;
            return this;
        }

        @ZenMethod
        public ValueItem setBaconPayment() {
            this.beaconPayment = true;
            return this;
        }

        @ZenMethod
        public ValueItem setRepairable() {
            this.repairable = true;
            return this;
        }

        @ZenMethod
        public ValueItem setRepairStack(IItemStack repairStack) {
            this.repairStack = CraftTweakerMC.getItemStack(repairStack);
            return this;
        }

        @ZenMethod
        public ValueItem setStackSize(int stackSize) {
            this.stackSize = stackSize;
            return this;
        }

        @ZenMethod
        public ValueItem setXpRepairRatio(float xpRepairRatio) {
            this.xpRepairRatio = xpRepairRatio;
            return this;
        }

        @ZenMethod
        public ValueItem setTintColor(int tint, int color) {
            if (this.tintColor == null) this.tintColor = new ArrayList<>();
            this.tintColor.add(new TintColor(tint, color));
            return this;
        }

        @ZenMethod
        public ValueItem setFood(int heal, float saturation) {
            this.heal = heal;
            this.saturation = saturation;
            this.useDuration = 32;

            return this;
        }

        @ZenMethod
        public ValueItem setUseDuration(int duration) {
            this.useDuration = duration;
            return this;
        }

        @ZenMethod
        public ValueItem setWolfFood() {
            this.wolfFood = true;
            return this;
        }

        @ZenMethod
        public ValueItem setAlwaysEdible() {
            this.alwaysEdible = true;
            return this;
        }

        @ZenMethod
        public ValueItem setAction(String name) {
            this.action = getAction(name);
            return this;
        }

        @ZenMethod
        public ValueItem setOres(String... ores) {
            this.ores = ores;
            return this;
        }

        private static EnumAction getAction(String name) {
            for (EnumAction action : EnumAction.values()) {
                if (action.name().equalsIgnoreCase(name)) return action;
            }

            return EnumAction.NONE;
        }
    }
}
