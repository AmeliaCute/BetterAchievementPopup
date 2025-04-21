package AmeliaCute.betteraachievementpopup.toast.Impl;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import AmeliaCute.betteraachievementpopup.toast.ToastUtil;
import AmeliaCute.betteraachievementpopup.toast.IToast;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class ToastModern implements IToast
{
    private static final long DISPLAY_TIME = 5000L;
    
    private final String title, description;
    private final ItemStack icon;

    private long initTime = -1L;

    public ToastModern(DisplayInfo info)
    {
        this.title        = info.getTitle().getString();
        this.description  = info.getDescription().getString();
        this.icon         = info.getIcon();
    }

    @Override
    public void render(PoseStack pose, Minecraft minecraft, long tick)
    {
        if(initTime < 0) 
        {
            initTime = tick;
            minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_TOAST_IN, 1.0F, 1.0F));
            minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F));
        }

        long  elapsed       = tick - initTime;
        float progress      = elapsed / (float) DISPLAY_TIME;
        
        int scaledHeight    = minecraft.getWindow().getGuiScaledHeight();
        int toastWidth      = 160;
        int toastHeight     = 32;

        int centerX         = minecraft.getWindow().getGuiScaledWidth() / 2 - toastWidth / 2;
        int centerY         = (int) (scaledHeight / 1.5);
        int yOffset         = 0;
        float alphaProgress = 1;

        if(progress <= .05f)
        {
            alphaProgress = Mth.clamp(progress / .05f, 0, 1);
            yOffset       = (int) ((1f - alphaProgress) * 20);
        }
        else if (progress >= .8f)
            alphaProgress = Mth.clamp((1f - progress) / .05f, 0, 1);

        int backgroundColor     = 0x202020;
        int itemBackgroundColor = 0x404040;
        int titleColor          = 0xFFFFFF;
        int descriptionColor    = 0xAAAAAA;
        
        pose.pushPose();
        pose.translate(0, 0, 500);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1f, 1f, 1f, alphaProgress);

        // background
        GuiComponent.fill(pose, centerX, centerY + yOffset, centerX + toastWidth, centerY + toastHeight + yOffset, backgroundColor);
        GuiComponent.fill(pose, centerX + 6, centerY + 6 + yOffset, centerX + 26, centerY + 26 + yOffset, itemBackgroundColor);

        // icon
        minecraft.getItemRenderer().renderAndDecorateFakeItem(icon, centerX + 8, centerY + 8 + yOffset);

        // text
        minecraft.font.draw(pose, title, centerX + 30, centerY + 7 + yOffset, titleColor);
        ToastUtil.drawText(pose, minecraft, description, centerX + 30, centerY + 18 + yOffset, scaledHeight, toastWidth - 34, tick, descriptionColor);

        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        pose.popPose();
    }

    @Override
    public boolean isFinished(long tick)
    {
        if(initTime < 0) return false;
        return tick - initTime >= DISPLAY_TIME;
    }
}
