package AmeliaCute.betteraachievementpopup.toast;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;

public class ToastUtil 
{
    public static void drawText(PoseStack pose, Minecraft minecraft, String text, int x, int y, int scaledHeight, int maxWidth, long tick, int color)
    {
        double guiScale   = minecraft.getWindow().getGuiScale();
        int maxHeight     = (int) (minecraft.font.lineHeight * guiScale);
        int textWidth     = minecraft.font.width(text);
        int cycleLength   = textWidth + maxWidth;
        int dx            = 0;
        float elapsedTick = tick / 20f;  

        if(textWidth > maxWidth)
        {
            float m = (elapsedTick * .5f) % cycleLength;
            if(m > maxWidth) dx = -(int)(m - maxWidth);
        }

        RenderSystem.enableScissor(
            (int) (x * guiScale), 
            (int) ((scaledHeight - y) * guiScale - maxHeight), 
            (int) (maxWidth * guiScale),
            (int) (maxHeight * guiScale)
        );

        minecraft.font.draw(pose, text, x + dx, y, color);
        RenderSystem.disableScissor();
    }    
}
