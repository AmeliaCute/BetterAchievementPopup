package AmeliaCute.betteraachievementpopup.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import AmeliaCute.betteraachievementpopup.toast.ToastManager;
import net.minecraft.advancements.Advancement;
import net.minecraft.advancements.DisplayInfo;
import net.minecraft.client.gui.components.toasts.AdvancementToast;
import net.minecraft.client.gui.components.toasts.Toast;
import net.minecraft.client.gui.components.toasts.ToastComponent;
import net.minecraft.client.multiplayer.ClientAdvancements;

@Mixin(ClientAdvancements.class)
public class MixinClientAdvancementManager 
{
   @Redirect(
    method = "update",
    at     = @At(
        value  = "INVOKE",
        target = "Lnet/minecraft/client/gui/components/toasts/ToastComponent;addToast(Lnet/minecraft/client/gui/components/toasts/Toast;)V"
    )
   )
   private void redirectDefaultToast(ToastComponent toastComponent, Toast toast)
   {
        if(toast instanceof AdvancementToast advancementToast)
        {
            Advancement advancement = ((MixinAdvancedToastAccessor) advancementToast).getAdvancement(); 
            DisplayInfo displayInfo = advancement.getDisplay();
            if(displayInfo == null) return;

            ToastManager.queue(displayInfo);
        }
   }
}
