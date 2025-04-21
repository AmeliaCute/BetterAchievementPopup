package AmeliaCute.betteraachievementpopup.fabric.client;

import AmeliaCute.betteraachievementpopup.toast.ToastManager;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.Minecraft;

public final class BetterAchievementPopupFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() 
    {
        HudRenderCallback.EVENT.register((poseStack, tickDelta) -> 
        {
            ToastManager.update(poseStack, Minecraft.getInstance(), System.nanoTime() / 1_000_000L);
        });
    }
}
