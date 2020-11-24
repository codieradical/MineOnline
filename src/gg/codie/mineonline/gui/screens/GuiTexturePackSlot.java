package gg.codie.mineonline.gui.screens;

import gg.codie.mineonline.Settings;
import gg.codie.mineonline.client.LegacyGameManager;
import gg.codie.mineonline.client.MinecraftTexturePackRepository;
import gg.codie.minecraft.client.gui.Tessellator;
import gg.codie.mineonline.gui.rendering.FontRenderer;
import gg.codie.mineonline.gui.rendering.Loader;
import gg.codie.mineonline.gui.rendering.textures.EGUITexture;
import gg.codie.mineonline.gui.textures.TexturePackBase;
import org.lwjgl.opengl.GL11;

import java.util.List;

class GuiTexturePackSlot extends GuiSlot
{

    public GuiTexturePackSlot(GuiTexturePacks guitexturepacks)
    {
        super(guitexturepacks.getWidth(), guitexturepacks.getHeight(), 32, (guitexturepacks.getHeight() - 55) + 4, 36, 220);
        parentTexturePackGui = guitexturepacks;
    }

    protected int getSize()
    {
        List list = MinecraftTexturePackRepository.singleton.getTexturePacks();
        return list.size();
    }

    protected void elementClicked(int i, boolean flag)
    {
        List list = MinecraftTexturePackRepository.singleton.getTexturePacks();

        for(EGUITexture texture : EGUITexture.values()) {
            if(texture.useTexturePack) {
                Loader.singleton.unloadTexture(texture);
            }
        }

        if (LegacyGameManager.isInGame() && !Settings.singleton.getTexturePack().equals(((TexturePackBase)list.get(i)).texturePackFileName)) {
            LegacyGameManager.setTexturePack(((TexturePackBase)list.get(i)).texturePackFileName);
        }

        Settings.singleton.setTexturePack(((TexturePackBase) list.get(i)).texturePackFileName);
        Settings.singleton.saveSettings();

        //FontRenderer.reloadFont();
    }

    protected boolean isSelected(int i)
    {
        List list = MinecraftTexturePackRepository.singleton.getTexturePacks();
        return Settings.singleton.getTexturePack().equals(((TexturePackBase)list.get(i)).texturePackFileName);
    }

    protected int getContentHeight()
    {
        return getSize() * 36;
    }

    protected void drawBackground()
    {
        parentTexturePackGui.drawDefaultBackground();
    }

    protected void drawSlot(int i, int j, int k, int l, Tessellator tessellator)
    {
        resize(parentTexturePackGui.getWidth(), parentTexturePackGui.getHeight(), 32, (parentTexturePackGui.getHeight() - 55) + 4);

        TexturePackBase texturepackbase = MinecraftTexturePackRepository.singleton.getTexturePacks().get(i);
        texturepackbase.bindThumbnailTexture();
        GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
        tessellator.startDrawingQuads();
        tessellator.setColorOpaque_I(0xffffff);
        tessellator.addVertexWithUV(j, k + l, 0.0D, 0.0D, 1.0D);
        tessellator.addVertexWithUV(j + 32, k + l, 0.0D, 1.0D, 1.0D);
        tessellator.addVertexWithUV(j + 32, k, 0.0D, 1.0D, 0.0D);
        tessellator.addVertexWithUV(j, k, 0.0D, 0.0D, 0.0D);
        tessellator.draw();
        parentTexturePackGui.drawString(texturepackbase.texturePackFileName, j + 32 + 2, k + 1, 0xffffff);
        parentTexturePackGui.drawString(texturepackbase.firstDescriptionLine, j + 32 + 2, k + 12, 0x808080);
        parentTexturePackGui.drawString(texturepackbase.secondDescriptionLine, j + 32 + 2, k + 12 + 10, 0x808080);
    }

    final GuiTexturePacks parentTexturePackGui; /* synthetic field */
}
