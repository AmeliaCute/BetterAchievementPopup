package AmeliaCute.betteraachievementpopup.forge.client;

import com.mojang.blaze3d.vertex.PoseStack;

import AmeliaCute.betteraachievementpopup.BetterAchievementPopup;
import AmeliaCute.betteraachievementpopup.toast.ToastManager;
import net.minecraft.client.Minecraft;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = BetterAchievementPopup.MOD_ID, value = Dist.CLIENT)
public class BetterAchievementPopupForgeClient 
{
    @SubscribeEvent
    public static void onRenderOverlay(RenderGameOverlayEvent event)
    {
        if(event.getType() != RenderGameOverlayEvent.ElementType.ALL) return;

        PoseStack poseStack = event.getMatrixStack();
        Minecraft minecraft = Minecraft.getInstance();

        ToastManager.update(poseStack, minecraft, System.nanoTime() / 1_000_000L);
    }    
}
