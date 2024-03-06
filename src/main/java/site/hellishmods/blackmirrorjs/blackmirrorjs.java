package site.hellishmods.blackmirrorjs;

import net.minecraft.client.gui.screen.MainMenuScreen;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.GuiOpenEvent;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModLoadingContext;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.config.ModConfig.Type;
import site.hellishmods.blackmirrorjs.config.BlackmirrorClientConfig;
import site.hellishmods.blackmirrorjs.config.BlackmirrorCommonConfig;
import site.hellishmods.blackmirrorjs.gui.WarningScreen;

@Mod(blackmirrorjs.MOD_ID)
@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.FORGE, modid = blackmirrorjs.MOD_ID)
public class blackmirrorjs
{
    // Consts and vars
    public static final String MOD_ID = "blackmirrorjs";
    private static boolean menuShown = false;

    public blackmirrorjs() {
        ModLoadingContext.get().registerConfig(Type.COMMON, BlackmirrorCommonConfig.SPEC, MOD_ID+"-common.toml");
        ModLoadingContext.get().registerConfig(Type.CLIENT, BlackmirrorClientConfig.SPEC, MOD_ID+"-client.toml");

        MinecraftForge.EVENT_BUS.register(this); // Register mod
    }

    @SubscribeEvent
    @OnlyIn(Dist.CLIENT)
    public static void onFinishLoading(GuiOpenEvent e) {
        if (!(e.getGui() instanceof MainMenuScreen) || menuShown) return;

        e.setGui(new WarningScreen());
        menuShown = true;
    }
}
