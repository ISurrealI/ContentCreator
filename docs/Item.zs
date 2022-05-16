// Don't forget to add this, because normally ct works in the postInit phase which it's after the registeries done
// Also you can't add recipes or anything that needs to be done in normal ct loader phase
#loader preInit

import contentcreator.item.Item;
import contentcreator.item.ValueItem;

// [STATIC] Item.create(String name); returns Item
// var exampleItem as Item = Item.create("test");

// [NOT STATIC] item.addItem(ValueItem... metaItem);
// adds Meta Item [ValueItem], returns Item
// NOT MANDATORY. It will automatically generate a basic item if you don't add a value item
// exampleItem.addItem(exampleValueItem, exampleValueItem2 ...);

// [NOT STATIC] registers item, returns void
// item.register();

// // // ValueItem Methods // // //
# NONE OF THEM ARE STATIC AND ALL OF THEM RETURNS ValueItem

// [STATIC] ValueItem.create(int meta);
// Creates Meta Item, returns ValueItem
// meta can't be bigger than Short.MAX_VALUE (32,766)
// var exampleValueItem as ValueItem = ValueItem.create(0);

// valueItem.setUnlocalizedName(String name);
// changes the lang key of the item, returns ValueItem
// this example should return a name like "item.<name>.name"
// valueItem.setUnlocalizedName(String name);

// changes the items model location
// can be same with unlocalized name etc. etc.
// valueItem.setModelLocation(String name);

// Change the color items name shown like golden apple
// You can find what String value can get at Values.txt
// valueItem.setRarity(String rarity);

// Adds enchantment effect to item like golden apple
// valueItem.setHasEffect();

// looks like it does nothing, but can be used to make tools with no damage use if you want
// valueItem.setTool(String tool, int level);

// sets the damage it gives to entities when you attack to them with this tool
// valueItem.setAttackDamage(float attackDamage);

// enchantments this item can get
// valueItem.setAllowedEnchantments(IEnchantmentDefinition... enchantments);

// enchantability of the item
// valueItem.setEnchantability(int enchantability);

// sets the item as a beacon payment
// ValueItem.setBeaconPayment();

// changes block breaking speed
// valueItem.setDestroySpeed(float speed);

// sets the stack size
// valueItem.setStackSize(int size);

// sets the color the layer will be tinted in model.
// tint is the layers number, color is decimal value of the color (you can get hexadecimal values like 0x000000 [BLACK])
// valueItem.setTintColor(int tint, int color);

// makes the item food
// valueItem.setFood(int heal, float saturation);

// does less stuff than it should. For example, if item is a food it'll take this much (in ticks) to be consumed. (Default food eating time is 32)
// valueItem.setUseDuration(int duration);

// can be used to heal dogs if the item is food
// valueItem.setWolfFood();

// can be always eaten even if player isn't hungry, item needs to be food
// valueItem.setAlwaysEdible();

// sets the use animation, like changing item to be used like bow
// You can find what String value can get at Values.txt
// valueItem.setAction(String name);

// sets Ore Dictionary for item
// valueItem.setOres(String... ores);
// valueItem.setOres("oreIron", "ingotIron", "nuggetIron" ...);


// // // USELESS STUFF // // //
# THESE DO NOTHING (for now)

// valueItem.setMaxDamage(int maxDamage);
// valueItem.setBlockBreakDamage(int damage);
// valueItem.setEntityHitDamage(int damage);
// ValueItem.setRepairable();
// valueItem.setRepairStack(IItemStack stack);
// valueItem.setXpRepairRatio(float ratio);
