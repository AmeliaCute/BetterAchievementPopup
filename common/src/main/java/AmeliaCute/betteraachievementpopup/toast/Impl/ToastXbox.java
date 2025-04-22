package AmeliaCute.betteraachievementpopup.toast.Impl;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;

import AmeliaCute.betteraachievementpopup.toast.ToastUtil;
import AmeliaCute.betteraachievementpopup.BetterAchievementPopup;
import AmeliaCute.betteraachievementpopup.toast.IToast;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.advancements.FrameType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

public class ToastXbox implements IToast 
{
    private static final long DISPLAY_TIME     = 5000L;
    private static final long POP_DURATION     = 300L;
    private static final long WAIT_DURATION    = 250L;
    private static final long EXPAND_DURATION  = 300L;
    private static final long FADE_DURATION    = 200L;

    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation(BetterAchievementPopup.MOD_ID, "textures/gui/xbox_button.png");
    private static final ResourceLocation TOAST_TEXTURE  = new ResourceLocation(BetterAchievementPopup.MOD_ID, "textures/gui/xbox_background.png");
    private static final ResourceLocation ITEM_TEXTURE   = new ResourceLocation(BetterAchievementPopup.MOD_ID, "textures/gui/xbox_item_background.png");

    private final String    title, description;
    private final ItemStack icon;
    private final Boolean   isChallenge;
    private       long      initTime = -1L;

    public ToastXbox(DisplayInfo info)
    {
        this.title       = info.getTitle().getString();
        this.description = info.getDescription().getString();
        this.icon        = info.getIcon();
        this.isChallenge = info.getFrame() == FrameType.CHALLENGE;
    }

    @Override
    public void render(PoseStack pose, Minecraft minecraft, long tick) 
    {
        if (initTime < 0) 
        {
            initTime = tick;
            minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_TOAST_IN, 1.0F, 1.0F));

            if(isChallenge)
                minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.UI_TOAST_CHALLENGE_COMPLETE, 1.0F, 1.0F));
            else if(!isChallenge)
                minecraft.getSoundManager().play(SimpleSoundInstance.forUI(SoundEvents.EXPERIENCE_ORB_PICKUP, 1.0F, 1.0F));
        }

        long  elapsed     = tick - initTime;
        float phaseIntro  = elapsed - POP_DURATION - WAIT_DURATION;
        float phasePop    = Mth.clamp(elapsed / (float) POP_DURATION, 0, 1);
        float phaseExpand = Mth.clamp(phaseIntro / (float) EXPAND_DURATION, 0, 1);
        float phaseFade   = Mth.clamp((phaseIntro - EXPAND_DURATION) / (float) FADE_DURATION, 0, 1);

        int screenHeight  = minecraft.getWindow().getGuiScaledHeight();
        int toastWidth    = 160;
        int toastHeight   = 32;
        int positionX     = minecraft.getWindow().getGuiScaledWidth() / 2 - toastWidth / 2;
        int positionY     = (int)(screenHeight / 1.5);

        pose.pushPose();
        pose.translate(0, 0, 500);

        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShader(GameRenderer::getPositionTexShader);

        if (elapsed <= (POP_DURATION + WAIT_DURATION)) 
        {
            float scale = phasePop;
            float iconSize = 32f;
            float iconX = positionX + toastWidth / 2f - (iconSize / 2f * scale);
            float iconY = positionY + toastHeight / 2f - (iconSize / 2f * scale);
            pose.pushPose();
            pose.translate(iconX, iconY, 0);
            pose.scale(scale, scale, 1f);
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.setShaderTexture(0, BUTTON_TEXTURE);
            GuiComponent.blit(pose, 0, 0, 0, 0, 32, 32, 32, 32);
            pose.popPose();
        }
        if (phaseExpand > 0) 
        {
            int drawWidth = (int) (toastWidth * phaseExpand);
            int drawX = positionX + (toastWidth - drawWidth) / 2;
            RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
            RenderSystem.setShaderTexture(0, TOAST_TEXTURE);
            GuiComponent.blit(pose, drawX, positionY, 0, 0, drawWidth, toastHeight, toastWidth, toastHeight);

            RenderSystem.setShaderTexture(0, ITEM_TEXTURE);
            GuiComponent.blit(pose, drawX + 6, positionY + 6, 0, 0, 20, 20, 20, 20);
        }
        if (phaseFade > 0) 
        {
            RenderSystem.setShaderColor(1f, 1f, 1f, phaseFade);

            minecraft.getItemRenderer().renderAndDecorateFakeItem(icon, positionX + 8, positionY + 8);

            int alpha = (int)(phaseFade * 255) << 24;
            int textColTitle = 0x2A2A2A | alpha;
            int textColDesc  = 0x5C5C5C | alpha;
            ToastUtil.drawText(pose, minecraft, title, positionX + 30, positionY + 7, screenHeight, toastWidth - 34, tick, textColTitle);
            ToastUtil.drawText(pose, minecraft, description, positionX + 30, positionY + 18, screenHeight, toastWidth - 34, tick, textColDesc);
        }

        RenderSystem.setShaderColor(1f, 1f, 1f, 1f);
        pose.popPose();
    }

    @Override
    public boolean isFinished(long tick) 
    {
        return initTime >= 0 && tick - initTime >= DISPLAY_TIME;
    }
}