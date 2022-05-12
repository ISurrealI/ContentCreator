import contentcreator.fluid.Fluid;

// [STATIC]
// creates a fluid, it will use base textures if you don't specify locations
// Base fluid texture locations are "blocks/fluids/base_still" and "blocks/fluids/base_flow". If you specify the locations it will be "blocks/fluids/<location>"
// Fluid.create(String name, @Optional String stillLocation, @Optional String flowingLocation, @Optional String overlay);

// adds bucket of the liquid
// fluid.addBucket();

// adds block of the liquid
// you can check IMaterial at https://docs.blamejared.com/1.12/fr/Vanilla/Blocks/IMaterial
// fluid.addBlock(crafttweaker.block.IMaterial material);

// sets the lang key of the fluid
// fluid.setUnlocalizedName(String name);

// sets the light value of the fluid
// fluid.setLuminosity(int amount);

// sets the how fast you can walk in the fluid
// fluid.setDensity(int amount);

// sets the temperature of the fluid
// fluid.setTemperature(int amount);

// spreading speed of the fluid
// fluid.setViscosity(int amount);

// liquid flows upwards and bucket of it is upwards
// fluid.setGaseous();

// Change the color items name shown like golden apple
// You can find what String value can get at Values.txt
// fluid.setRarity(String rarity);

// sets color of the liquid
// fluid.setColor(int color);

// sets bucket fill sound
// You can find how to get SoundEvent at Sounds.zs
// fluid.setFillSound(SoundEvent sound);

// sets bucket empty sound
// You can find how to get SoundEvent at Sounds.zs
// fluid.setEmptySound(SoundEvent sound);
