package surreal.contentcreator.client.particle;

import crafttweaker.CraftTweakerAPI;
import crafttweaker.annotations.ZenRegister;
import crafttweaker.api.minecraft.CraftTweakerMC;
import crafttweaker.api.util.IAxisAlignedBB;
import crafttweaker.api.util.IRandom;
import crafttweaker.api.world.IVector3d;
import crafttweaker.api.world.IWorld;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import stanhebben.zenscript.annotations.ZenExpansion;
import stanhebben.zenscript.annotations.ZenMethodStatic;
import surreal.contentcreator.functions.particle.IParticleUpdateFunc;
import surreal.contentcreator.util.CTUtil;

// Texture Atlas stuff
@SuppressWarnings("unused")

@ZenRegister
@ZenExpansion("contentcreator.particle.IParticle")
public class MCParticle implements IParticle {
    public ParticleCustom particle;

    public MCParticle(IWorld world, double posXIn, double posYIn, double posZIn) {
        this.particle = new ParticleCustom(CraftTweakerMC.getWorld(world), posXIn, posYIn, posZIn).setParticle(this);
    }

    public MCParticle(IWorld world, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        this.particle = new ParticleCustom(CraftTweakerMC.getWorld(world), xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn).setParticle(this);
    }

    @Override
    public World getWorld() {
        return this.particle.getWorld();
    }

    @Override
    public double getPrevPosX() {
        return this.particle.getPrevPosX();
    }

    @Override
    public double getPrevPosY() {
        return this.particle.getPrevPosY();
    }

    @Override
    public double getPrevPosZ() {
        return this.particle.getPrevPosZ();
    }

    @Override
    public double getPosX() {
        return this.particle.getPosX();
    }

    @Override
    public double getPosY() {
        return this.particle.getPosY();
    }

    @Override
    public double getPosZ() {
        return this.particle.getPosZ();
    }

    @Override
    public double getMotionX() {
        return this.particle.getMotionX();
    }

    @Override
    public double getMotionY() {
        return this.particle.getMotionY();
    }

    @Override
    public double getMotionZ() {
        return this.particle.getMotionZ();
    }

    @Override
    public boolean onGround() {
        return this.particle.isOnGround();
    }

    @Override
    public boolean canCollide() {
        return this.particle.isCanCollide();
    }

    @Override
    public boolean isExpired() {
        return this.particle.isExpired();
    }

    @Override
    public float getWidth() {
        return this.particle.getWidth();
    }

    @Override
    public float getHeight() {
        return this.particle.getHeight();
    }

    @Override
    public IRandom getRandom() {
        return CTUtil.getRandom(this.particle.getRand());
    }

    @Override
    public int getParticleTextureIndexX() {
        return this.particle.getParticleTextureIndexX();
    }

    @Override
    public int getParticleTextureIndexY() {
        return this.particle.getParticleTextureIndexY();
    }

    @Override
    public float getParticleTextureJitterX() {
        return this.particle.getParticleTextureJitterX();
    }

    @Override
    public float getParticleTextureJitterY() {
        return this.particle.getParticleTextureJitterY();
    }

    @Override
    public int getParticleAge() {
        return this.particle.getParticleAge();
    }

    @Override
    public int getParticleMaxAge() {
        return this.particle.getParticleMaxAge();
    }

    @Override
    public float getParticleScale() {
        return this.particle.getParticleScale();
    }

    @Override
    public float getParticleGravity() {
        return this.particle.getParticleGravity();
    }

    @Override
    public float getParticleRed() {
        return this.particle.getParticleRed();
    }

    @Override
    public float getParticleGreen() {
        return this.particle.getParticleGreen();
    }

    @Override
    public float getParticleBlue() {
        return this.particle.getParticleBlue();
    }

    @Override
    public float getParticleAlpha() {
        return this.particle.getParticleAlpha();
    }

    @Override
    public float getParticleAngle() {
        return this.particle.getParticleAngle();
    }

    @Override
    public float getPrevParticleAngle() {
        return this.particle.getPrevParticleAngle();
    }

    @Override
    public IParticle disableDepth() {
        this.particle.disableDepth = true;
        return this;
    }

    @Override
    public IParticle multiplyVelocity(float multiplier) {
        this.particle.multiplyVelocity(multiplier);
        return this;
    }

    @Override
    public IParticle multipleParticleScaleBy(float scale) {
        this.particle.multipleParticleScaleBy(scale);
        return this;
    }

    @Override
    public IParticle setTexture(String location) {
        if (this.particle.getFXLayer() != 1) CraftTweakerAPI.logError("Can't set the texture because FXLayer isn't in 1!");
        else this.particle.setSprite(location);
        return this;
    }

    @Override
    public IParticle setTexture(int index) {
        if (this.particle.getFXLayer() != 0) CraftTweakerAPI.logError("Can't set the in Particle Sheet because FXLayer isn't in 0!");
        else this.particle.setParticleTextureIndex(index);
        return this;
    }

    @Override
    public IParticle setTexture() {
        if (this.particle.getFXLayer() != 0) CraftTweakerAPI.logError("Can't set the in Particle Sheet because FXLayer isn't in 0!");
        else this.particle.nextTextureIndexX();
        return this;
    }

    @Override
    public IParticle setFXLayer(int layer) {
        this.particle.setFXLayer(layer);
        return this;
    }

    @Override
    public IParticle setRGB(float r, float g, float b) {
        this.particle.setRBGColorF(r, g, b);
        return this;
    }

    @Override
    public IParticle setAlpha(float a) {
        this.particle.setAlphaF(a);
        return this;
    }

    @Override
    public IParticle setMaxAge(int age) {
        this.particle.setMaxAge(age);
        return this;
    }

    @Override
    public void setExpired() {
        this.particle.setExpired();
    }

    @Override
    public IParticle setSize(float width, float height) {
        this.particle.setSize(width, height);
        return this;
    }

    @Override
    public IParticle setPosition(double x, double y, double z) {
        this.particle.setPosition(x, y, z);
        return this;
    }

    @Override
    public IParticle move(double x, double y, double z) {
        this.particle.move(x, y, z);
        return this;
    }

    @Override
    public IParticle resetPositionToBB() {
        this.particle.resetPositionToBB();
        return this;
    }

    @Override
    public int getBrightnessForRender(float f) {
        return this.particle.getBrightnessForRender(f);
    }

    @Override
    public IAxisAlignedBB getBoundingBox() {
        return CraftTweakerMC.getIAxisAlignedBB(this.particle.getBoundingBox());
    }

    @Override
    public IParticle setBoundingBox(IAxisAlignedBB aabb) {
        this.particle.setBoundingBox(CraftTweakerMC.getAxisAlignedBB(aabb));
        return this;
    }

    @Override
    public IParticle setBoundingBox(double minX, double minY, double minZ, double maxX, double maxY, double maxZ) {
        this.particle.setBoundingBox(new AxisAlignedBB(minX, minY, minZ, maxX, maxY, maxZ));
        return this;
    }

    @Override
    public IParticle onUpdate(IParticleUpdateFunc func) {
        this.particle.UPDATE = func;
        return this;
    }

    @Override
    public void register() {
        if (this.particle.layerFX == 1) this.particle.setParticleTexture(this.particle.sprite);
        Minecraft.getMinecraft().effectRenderer.addEffect(this.particle);
    }

    @ZenMethodStatic
    public static double getInterpPosX() {
        return Particle.interpPosX;
    }

    @ZenMethodStatic
    public static double getInterpPosY() {
        return Particle.interpPosY;
    }

    @ZenMethodStatic
    public static double getInterpPosZ() {
        return Particle.interpPosZ;
    }

    @ZenMethodStatic
    public static IVector3d getCameraViewDir() {
        return CraftTweakerMC.getIVector3d(Particle.cameraViewDir);
    }

    @ZenMethodStatic
    public static MCParticle particle(IWorld world, double posXIn, double posYIn, double posZIn) {
        return new MCParticle(world, posXIn, posYIn, posZIn);
    }

    @ZenMethodStatic
    public static MCParticle particle(IWorld world, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        return new MCParticle(world, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
    }
}
