package surreal.contentcreator.util;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.*;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class RenderUtil {
    private static final Minecraft MC = Minecraft.getMinecraft();
    private static final World WORLD = MC.world;
    private static final EntityPlayer PLAYER = MC.player;

    public static void renderBackground(ItemStack stack, int x, int y) {
        boolean renderHalo = false;
        boolean renderPulse = false;

        int spread = 0;
        ResourceLocation halo = null;
        int haloColour = 0;

        Item item = stack.getItem();
        if (item instanceof IHaloItem) {
            IHaloItem ihri = (IHaloItem) item;

            spread = ihri.getSpread(WORLD, PLAYER, stack);
            halo = ihri.getLocation(WORLD, PLAYER, stack);
            haloColour = ihri.getColor(WORLD, PLAYER, stack);

            renderHalo = halo != null;
            //renderPulse = ihri.drawPulseEffect(item);
        }

        GlStateManager.enableBlend();
        GlStateManager.disableLighting();
        GlStateManager.disableDepth();

        if (renderHalo) {
            float ca = (float)(haloColour >> 24 & 255) / 255.0F;
            float cr = (float)(haloColour >> 16 & 255) / 255.0F;
            float cg = (float)(haloColour >> 8 & 255) / 255.0F;
            float cb = (float)(haloColour & 255) / 255.0F;
            if (ca == 0) ca = 1F;
            if (spread <= 0) spread = 1;

            GlStateManager.color(cr, cg, cb, ca);

            TextureAtlasSprite sprite = MC.getTextureMapBlocks().getAtlasSprite(halo.toString());

            MC.getTextureManager().bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);

            int xSize = sprite.getIconWidth() * spread;
            int ySize = sprite.getIconHeight() * spread;
            drawTexturedModalRect(x - (7 * spread) - ((sprite.getIconWidth() * (spread - 1))/4) - 1, y - (7 * spread) - ((sprite.getIconHeight() * (spread - 1))/4) - 1, sprite, xSize, ySize);
        }

        /*if (renderPulse) {
            GL11.glPushMatrix();
            double xs = (rand.nextGaussian() * 0.15) + 0.95;
            double ox = (1-xs)/2.0;
            GL11.glEnable(GL11.GL_BLEND);
            GL11.glTranslated(ox*16.0, ox*16.0, 1.0);
            GL11.glScaled(xs, xs, 1.0);

            IIcon icon = item.getItem().getIcon(item, 0);

            t.startDrawingQuads();
            t.setColorRGBA_F(1.0f, 1.0f, 1.0f, 0.6f);
            t.addVertexWithUV(0-ox, 0-ox, 0, icon.getMinU(), icon.getMinV());
            t.addVertexWithUV(0-ox, 16+ox, 0, icon.getMinU(), icon.getMaxV());
            t.addVertexWithUV(16+ox, 16+ox, 0, icon.getMaxU(), icon.getMaxV());
            t.addVertexWithUV(16+ox, 0-ox, 0, icon.getMaxU(), icon.getMinV());
            t.draw();

            GL11.glPopMatrix();
        }*/

        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
    }

    private static void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + heightIn), (double)1F).tex((double)textureSprite.getMinU(), (double)textureSprite.getMaxV()).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + heightIn), (double)1F).tex((double)textureSprite.getMaxU(), (double)textureSprite.getMaxV()).endVertex();
        bufferbuilder.pos((double)(xCoord + widthIn), (double)(yCoord + 0), (double)1F).tex((double)textureSprite.getMaxU(), (double)textureSprite.getMinV()).endVertex();
        bufferbuilder.pos((double)(xCoord + 0), (double)(yCoord + 0), (double)1F).tex((double)textureSprite.getMinU(), (double)textureSprite.getMinV()).endVertex();
        tessellator.draw();
    }
}

