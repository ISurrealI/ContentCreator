package surreal.contentcreator.common.item;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.enchantments.IEnchantmentDefinition;
import crafttweaker.api.item.IItemStack;
import crafttweaker.api.minecraft.CraftTweakerMC;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.item.EnumRarity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.IRarity;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.CTUtils;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.proxy.CommonProxy;

import java.util.*;

// Implement damage with nbt
@ZenRegister
@ZenClass("mods.contentcreator.item.Item")
public class ItemBase extends Item {
    public final List<ValueItem> METAITEMS;
    public Map<Integer, Integer> COLOR = null;

    public ItemBase() {
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
        for (int i = 0; i < METAITEMS.size(); i++) {
            ValueItem value = METAITEMS.get(i);

            if (value.color != 0xFFFFFF) {
                if (COLOR == null) COLOR = new HashMap<>();
                COLOR.putIfAbsent(i, value.color);
            }
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

        return new ModelResourceLocation(new ResourceLocation((value.modelLocation == null ? this.getRegistryName() + (METAITEMS.size() > 1 ? "_" + meta : "") : value.modelLocation)), "inventory");
    }

    @ZenRegister
    @ZenClass("mods.contentcreator.item.ValueItem")
    public static class ValueItem {
        public String unlocName = null;
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
        public int color = 0xFFFFFF;

        @ZenMethod("setUnlocalizedName")
        public ValueItem setUnlocName(String unlocName) {
            this.unlocName = unlocName;
            return this;
        }

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
            this.rarity = getRarity(rarity);
            return this;
        }

        @ZenMethod
        public ValueItem setHasEffect() {
            this.hasEffect = true;
            return this;
        }

        @ZenMethod
        public ValueItem setTool(String tool, int level) {
            if (toolClasses == null) this.toolClasses = new HashMap<>();
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
            this.enchantments = CTUtils.getEnchantments(enchantment);
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
        public void setColor(int color) {
            this.color = color;
        }

        private static EnumRarity getRarity(String name) {
            for (EnumRarity rarity : EnumRarity.values()) {
                if (rarity.name().equalsIgnoreCase(name)) return rarity;
            }

            return EnumRarity.COMMON;
        }
    }
}
