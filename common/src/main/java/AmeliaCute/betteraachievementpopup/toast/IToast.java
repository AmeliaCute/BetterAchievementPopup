package AmeliaCute.betteraachievementpopup.toast;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.client.Minecraft;

public interface IToast {
    /**
     * Render the toast each frame.  
     * @param pose current pose stack
     * @param minecraft client instance
     * @param tick current tick count
     */
    void render(PoseStack pose, Minecraft minecraft, long tick);

    /**
     * Whether the toast has finished displaying.  
     */
    boolean isFinished(long tick);
}