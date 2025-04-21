package AmeliaCute.betteraachievementpopup.mixin;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;

import net.minecraft.advancements.Advancement;
import net.minecraft.client.gui.components.toasts.AdvancementToast;

@Mixin(AdvancementToast.class)
public interface MixinAdvancedToastAccessor 
{
    @Accessor("advancement")
    Advancement getAdvancement();
}
