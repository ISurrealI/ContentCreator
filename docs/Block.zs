// Don't forget to add this, because normally ct works in the postInit phase which it's after the registeries done
// Also you can't add recipes or anything that needs to be done in normal ct loader phase
#loader preInit

import contentcreator.block.BlockBase;

// [STATIC]
// creates a new block, for IMaterial look at: https://docs.blamejared.com/1.12/en/Vanilla/Blocks/IMaterial
// BlockBase.create(IMaterial blockMaterial, String registryName);

// Adds item for block
// block.addItem();

# Note: A BLOCK CAN ONLY HAVE 16 STATES, for example 0 to 3 integer amount and 4 different faces like furnace will equal to 16 states [4 * 4 = 16]
# THIS CAN LEAD TO GAME CRASHES IF YOU USE IT WRONGLY
# Also they're kinda unfinished, use wisely

// Adds faces to block, like how furnace can look at different directions (north, south, west, east)
// block.setFacing(String propertyName);

// Same with setFacing but whitelist, for IFacing look at: https://docs.blamejared.com/1.12/en/Vanilla/World/IFacing
// block.setFacing(String propertyName, IFacing... facings);

// Adds Integer property to blocks, like how stone has 6 variants (stone, granite, andesite, diorite (+ the polished variants)
// block.setInteger(String propertyName, int max);

// Same with setInteger but with minimum value, without the minimum value the block will get 0 to max value but you can specify the min value like this
// block.setInteger(String propertyName, int min, int max);

// Sets blocks sound type, plays different sounds when you break, walk on it etc.
// Check at Sounds.zs for more detail
// Default Sound Type is Stone
// block.setSoundType(CTSoundType soundType);

// Sets light opacity between 0 - 255. (I think)
// block.setLightOpacity(int opacity);

// Sets the light the block emits, like glowstone
// block.setLightLevel(float value);

// How hard the block is. Slows down the breaking speed
// block.setHardness(float value);

// How blast resistant it is. Like obsidian
// block.setResistance(float value);

// Changes to resistance and hardness to a same value
// block.setStrength(float value);

// Makes block passable, you need to set collision boxes to pass inside it tho
// block.setPassable();

// Sets blocks hardness to -1.0F and makes it unbreakable
// block.setUnbreakable();

// Sets bounding box, like how slabs and stairs aren't full block and stuffs like that. Use wisely you can accidentally make an untouchable block
// block.setBoundingBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
// block.setBoundingBox(IAxisAllignedBB aabb);

// The outline highlighted when the player is targeting this Block
// block.setSelectedBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
// block.setSelectedBox(IAxisAllignedBB aabb);

// The collision boxes of the block
// block.setCollisionBox(int minX, int minY, int minZ, int maxX, int maxY, int maxZ)
// block.setCollisionBox(IAxisAllignedBB aabb);

// Like ice
// block.setSlipperiness(float setSlipperiness);

// Tool and Minimum Tool Level needed to be able to break block
// block.setHarvestLevel(String tool, int level);

// Light rendering stuff, you mostly use it when you do a different model full block (e.g hopper, cauldron...)
// block.setNonOpaque();
