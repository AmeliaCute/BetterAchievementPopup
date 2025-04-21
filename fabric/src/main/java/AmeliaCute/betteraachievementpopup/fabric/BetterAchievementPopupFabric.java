package AmeliaCute.betteraachievementpopup.fabric;

import AmeliaCute.betteraachievementpopup.BetterAchievementPopup;
import net.fabricmc.api.ModInitializer;

public final class BetterAchievementPopupFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        BetterAchievementPopup.init();
    }
}
