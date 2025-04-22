package AmeliaCute.betteraachievementpopup.toast;

import AmeliaCute.betteraachievementpopup.toast.Impl.ToastModern;
import AmeliaCute.betteraachievementpopup.toast.Impl.ToastXbox;
import net.minecraft.advancements.DisplayInfo;

public class ToastFactory {
    public enum Type { MODERN, XBOX }

    public static IToast create(DisplayInfo info, Type type) {
        switch (type) {
            case MODERN: return new ToastModern(info);
            case XBOX:   return new ToastXbox(info);
            
            default: throw new IllegalArgumentException("Unknown toast type");
        }
    }
}
