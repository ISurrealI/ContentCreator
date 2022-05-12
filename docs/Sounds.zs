import contentcreator.sound.SoundEvent;
import contentcreator.block.SoundType;

// // // CTSoundEvent // // //

// You can get SoundEvents like:
// var sound as SoundEvent = <soundevent:minecraft:ambient.cave>
// var sound as SoundEvent = SoundEvent.get("minecraft:ambient.cave");

// returns the name of the SoundEvent, returns string
// soundEvent.getName();

// creates a new SoundEvent ("contentcreator:<name>")
// SoundEvent.create(String name);

// // // CTSoundType // // //
# This is like list of sounds for a block

// You can get SoundTypes like:
// var type as SoundType = <soundtype:wood>
// var type as SoundType = SoundType.get("wood");

// returns the volume of the SoundType, returns float
// soundType.getVolume();

// returns the pitch of the SoundType, returns float
// soundType.getPitch();

// returns the break sound, returns SoundEvent
// soundType.getBreakSound();

// returns the walking on it sound, returns SoundEvent
// soundType.getStepSound();

// returns the placing sound, returns SoundEvent
// soundType.getPlaceSound();

// returns the hitting sound, returns SoundEvent
// soundType.getHitSound();

// returns the falling on it sound, returns SoundEvent
// soundType.getFallSound();