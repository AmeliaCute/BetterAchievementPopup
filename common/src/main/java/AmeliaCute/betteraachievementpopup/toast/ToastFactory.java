package AmeliaCute.betteraachievementpopup.toast;

import AmeliaCute.betteraachievementpopup.toast.Impl.ToastModern;
import AmeliaCute.betteraachievementpopup.toast.Impl.ToastVanillaLike;
import net.minecraft.advancements.DisplayInfo;

public class ToastFactory {
    public enum Type { MODERN, VANILLA_LIKE }

    public static IToast create(DisplayInfo info, Type type) {
        switch (type) {
            case MODERN: return new ToastModern(info);
            case VANILLA_LIKE: return new ToastVanillaLike(info);
            default: throw new IllegalArgumentException("Unknown toast type");
        }
    }
}
