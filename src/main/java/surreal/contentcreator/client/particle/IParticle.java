package surreal.contentcreator.client.particle;

import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.util.IAxisAlignedBB;
import crafttweaker.api.util.IRandom;
import net.minecraft.world.World;
import stanhebben.zenscript.annotations.ZenClass;
import stanhebben.zenscript.annotations.ZenGetter;
import stanhebben.zenscript.annotations.ZenMethod;
import surreal.contentcreator.functions.particle.IParticleUpdateFunc;

@SuppressWarnings("unused")

@ZenRegister
@ZenClass("contentcreator.particle.IParticle")
public interface IParticle {
    @ZenMethod
    @ZenGetter("world")
    World getWorld();

    @ZenMethod
    @ZenGetter("prevPosX")
    double getPrevPosX();

    @ZenMethod
    @ZenGetter("prevPosY")
    double getPrevPosY();

    @ZenMethod
    @ZenGetter("prevPosZ")
    double getPrevPosZ();

    @ZenMethod
    @ZenGetter("posX")
    double getPosX();

    @ZenMethod
    @ZenGetter("posY")
    double getPosY();

    @ZenMethod
    @ZenGetter("posZ")
    double getPosZ();

    @ZenMethod
    @ZenGetter("motionX")
    double getMotionX();

    @ZenMethod
    @ZenGetter("motionY")
    double getMotionY();

    @ZenMethod
    @ZenGetter("motionZ")
    double getMotionZ();

    @ZenMethod
    @ZenGetter("onGround")
    boolean onGround();

    @ZenMethod
    @ZenGetter("canCollide")
    boolean canCollide();

    @ZenMethod
    @ZenGetter("isExpired")
    boolean isExpired();

    @ZenMethod
    @ZenGetter("width")
    float getWidth();

    @ZenMethod
    @ZenGetter("height")
    float getHeight();

    @ZenMethod
    @ZenGetter("rand")
    IRandom getRandom();

    @ZenMethod
    @ZenGetter("particleTextureIndexX")
    int getParticleTextureIndexX();

    @ZenMethod
    @ZenGetter("particleTextureIndexY")
    int getParticleTextureIndexY();

    @ZenMethod
    @ZenGetter("particleTextureJitterX")
    float getParticleTextureJitterX();

    @ZenMethod
    @ZenGetter("particleTextureJitterY")
    float getParticleTextureJitterY();

    @ZenMethod
    @ZenGetter("particleAge")
    int getParticleAge();

    @ZenMethod
    @ZenGetter("particleMaxAge")
    int getParticleMaxAge();

    @ZenMethod
    @ZenGetter("particleScale")
    float getParticleScale();

    @ZenMethod
    @ZenGetter("particleGravity")
    float getParticleGravity();

    @ZenMethod
    @ZenGetter("particleRed")
    float getParticleRed();

    @ZenMethod
    @ZenGetter("particleGreen")
    float getParticleGreen();

    @ZenMethod
    @ZenGetter("particleBlue")
    float getParticleBlue();

    @ZenMethod
    @ZenGetter("particleAlpha")
    float getParticleAlpha();

    @ZenMethod
    @ZenGetter("particleAngle")
    float getParticleAngle();

    @ZenMethod
    @ZenGetter("prevParticleAngle")
    float getPrevParticleAngle();

    @ZenMethod
    IParticle disableDepth();

    @ZenMethod
    IParticle multiplyVelocity(float multiplier);

    @ZenMethod
    IParticle multipleParticleScaleBy(float scale);

    @ZenMethod
    IParticle setTexture(String location);

    @ZenMethod
    IParticle setTexture(int index);

    @ZenMethod
    IParticle setTexture();

    @ZenMethod
    IParticle setFXLayer(int layer);

    @ZenMethod
    IParticle setRGB(float r, float g, float b);

    @ZenMethod
    IParticle setAlpha(float a);

    @ZenMethod
    IParticle setMaxAge(int age);

    @ZenMethod
    void setExpired();

    @ZenMethod
    IParticle setSize(float width, float height);

    @ZenMethod
    IParticle setPosition(double x, double y, double z);

    @ZenMethod
    IParticle move(double x, double y, double z);

    @ZenMethod
    IParticle resetPositionToBB();

    @ZenMethod
    int getBrightnessForRender(float f);

    @ZenMethod
    IAxisAlignedBB getBoundingBox();

    @ZenMethod
    IParticle setBoundingBox(IAxisAlignedBB aabb);

    @ZenMethod
    IParticle setBoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ);

    @ZenMethod
    IParticle onUpdate(IParticleUpdateFunc func);

    @ZenMethod
    void register();
}
