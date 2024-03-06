package site.hellishmods.blackmirrorjs.lib;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.TickEvent.ClientTickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

public class Cooldown {
    public Integer ticks;
    private Runnable callback;
    private boolean done = false;

    public Cooldown(int ticks) {
        MinecraftForge.EVENT_BUS.register(this);
        this.ticks = ticks;
    }
    public void setCallback(Runnable callback) {
        this.callback = callback;
    }
    public boolean checkForEnd() {
        if (ticks<=0) {
            done = true;
            callback.run();
            return true;
        }
        return false;
    }
    @SubscribeEvent
    public void onTick(ClientTickEvent e) {
        if (done || checkForEnd()) return;

        ticks--;
    }
}
