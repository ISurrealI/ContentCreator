package surreal.contentcreator.client.particle;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import surreal.contentcreator.ContentCreator;
import surreal.contentcreator.ModValues;
import surreal.contentcreator.functions.particle.IParticleUpdateFunc;

import java.util.Random;

@SideOnly(Side.CLIENT)
public class ParticleCustom extends Particle {
    public TextureAtlasSprite sprite = Minecraft.getMinecraft().getTextureMapBlocks().getMissingSprite();
    public IParticleUpdateFunc UPDATE = null;

    public boolean disableDepth;
    public int layerFX = 0;

    private IParticle particle;

    protected ParticleCustom(World worldIn, double posXIn, double posYIn, double posZIn) {
        super(worldIn, posXIn, posYIn, posZIn);
    }

    public ParticleCustom(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
    }

    @Override
    public boolean shouldDisableDepth() {
        return this.disableDepth;
    }

    @Override
    public int getFXLayer() {
        return this.layerFX;
    }

    public void setFXLayer(int layer) {
        layerFX = Math.min(layer, 1);
    }

    public World getWorld() {
        return world;
    }

    public double getPrevPosX() {
        return prevPosX;
    }

    public double getPrevPosY() {
        return prevPosY;
    }

    public double getPrevPosZ() {
        return prevPosZ;
    }

    public double getPosX() {
        return posX;
    }

    public double getPosY() {
        return posY;
    }

    public double getPosZ() {
        return posZ;
    }

    public double getMotionX() {
        return motionX;
    }

    public double getMotionY() {
        return motionY;
    }

    public double getMotionZ() {
        return motionZ;
    }

    public boolean isOnGround() {
        return onGround;
    }

    public boolean isCanCollide() {
        return canCollide;
    }

    public boolean isExpired() {
        return isExpired;
    }

    public float getWidth() {
        return width;
    }

    public float getHeight() {
        return height;
    }

    public Random getRand() {
        return rand;
    }

    public int getParticleTextureIndexX() {
        return particleTextureIndexX;
    }

    public int getParticleTextureIndexY() {
        return particleTextureIndexY;
    }

    public float getParticleTextureJitterX() {
        return particleTextureJitterX;
    }

    public float getParticleTextureJitterY() {
        return particleTextureJitterY;
    }

    public int getParticleAge() {
        return particleAge;
    }

    public int getParticleMaxAge() {
        return particleMaxAge;
    }

    public float getParticleScale() {
        return particleScale;
    }

    public float getParticleGravity() {
        return particleGravity;
    }

    public float getParticleRed() {
        return particleRed;
    }

    public float getParticleBlue() {
        return particleBlue;
    }

    public float getParticleGreen() {
        return particleGreen;
    }

    public float getParticleAlpha() {
        return particleAlpha;
    }

    public float getParticleAngle() {
        return particleAngle;
    }

    public float getPrevParticleAngle() {
        return prevParticleAngle;
    }

    @Override
    public void setSize(float p_187115_1_, float p_187115_2_) {
        super.setSize(p_187115_1_, p_187115_2_);
    }

    @Override
    public void resetPositionToBB() {
        super.resetPositionToBB();
    }

    public ParticleCustom setParticle(IParticle particle) {
        this.particle = particle;
        return this;
    }

    public void setSprite(String sprite) {
        this.sprite = Minecraft.getMinecraft().getTextureMapBlocks().getAtlasSprite(ModValues.MODID + ":" + sprite);
        ContentCreator.getLogger().warn(this.sprite.getIconName());
    }

    @Override
    public void onUpdate() {
        if (UPDATE != null) UPDATE.onUpdate(this.particle);
        else super.onUpdate();
    }
}
