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

        GlStateManager.disableBlend();
        GlStateManager.enableLighting();
        GlStateManager.enableDepth();
    }

    public static void setSize(ItemStack stack) {
        if (stack.getItem() instanceof IHaloItem && ((IHaloItem) stack.getItem()).shouldPulse(WORLD, PLAYER, stack) && Minecraft.getSystemTime() % 3 == 0)
            GlStateManager.scale(1.15F, 1.15F, 1);
    }

    private static void drawTexturedModalRect(int xCoord, int yCoord, TextureAtlasSprite textureSprite, int widthIn, int heightIn) {
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(xCoord, yCoord + heightIn, 1F).tex(textureSprite.getMinU(), textureSprite.getMaxV()).endVertex();
        bufferbuilder.pos(xCoord + widthIn, yCoord + heightIn, 1F).tex(textureSprite.getMaxU(), textureSprite.getMaxV()).endVertex();
        bufferbuilder.pos(xCoord + widthIn, yCoord, 1F).tex(textureSprite.getMaxU(), textureSprite.getMinV()).endVertex();
        bufferbuilder.pos(xCoord, yCoord, 1F).tex(textureSprite.getMinU(), textureSprite.getMinV()).endVertex();
        tessellator.draw();
    }
}

