package AmeliaCute.betteraachievementpopup.forge;

import AmeliaCute.betteraachievementpopup.BetterAchievementPopup;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(BetterAchievementPopup.MOD_ID)
public final class BetterAchievementPopupForge {
    public BetterAchievementPopupForge() 
    {
        EventBuses.registerModEventBus(BetterAchievementPopup.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());
        BetterAchievementPopup.init();  
    }
}
