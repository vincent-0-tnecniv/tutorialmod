package com.vincent.tutorialmod.menu;

import com.vincent.tutorialmod.menu.custom.CrystallizerScreen;
import com.vincent.tutorialmod.menu.custom.PedestalScreen;
import net.neoforged.neoforge.client.event.RegisterMenuScreensEvent;

public class ModScreens {
    public static void register(RegisterMenuScreensEvent event) {
        event.register(ModMenuTypes.PEDESTAL_MENU.get(), PedestalScreen::new);
        event.register(ModMenuTypes.CRYSTALLIZER_MENU.get(), CrystallizerScreen::new);
    }
}
