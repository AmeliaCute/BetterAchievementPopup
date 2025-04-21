package AmeliaCute.betteraachievementpopup.toast;

import com.mojang.blaze3d.vertex.PoseStack;

import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.Minecraft;

import java.util.ArrayDeque;
import java.util.Queue;

public class ToastManager 
{
    private static final Queue<IToast> queue       = new ArrayDeque<>();

    public static void queue(DisplayInfo info) 
    {
        queue.add(ToastFactory.create(info, ToastFactory.Type.XBOX));
    }

    public static void queue(IToast toast) 
    {
        queue.add(toast);
    }

    public static void update(PoseStack poseStack, Minecraft minecraft, long tick)
    {
        if(queue.isEmpty()) return;
        
        IToast current = queue.peek();
        if(current == null) return;

        current.render(poseStack, minecraft, tick);

        if(!current.isFinished(tick)) return;
        queue.poll();
    }
}
