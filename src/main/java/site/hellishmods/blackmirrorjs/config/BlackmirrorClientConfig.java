package site.hellishmods.blackmirrorjs.config;

import java.util.HashMap;

import net.minecraftforge.common.ForgeConfigSpec;

public class BlackmirrorClientConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    public static final HashMap<String, ForgeConfigSpec.ConfigValue<Integer>> COOLDOWNS = new HashMap<>();

    static {
        BUILDER
            .push("cooldowns")
            .comment(" How long should the buttons on the warning screen be inactive for?");
        COOLDOWNS.put("continue", BUILDER
            .comment(" How long should the 'continue' button be inactive for?", " Calculated (in seconds) using: finalContinueTime = 30 + continueTime + permissionAmount*permissionMultiplier")
            .defineInRange("continueTime", 30, 0, Integer.MAX_VALUE)
        );
        COOLDOWNS.put("donotshowagain", BUILDER
            .comment(" How long should the 'do not show again' toggle be inactive for?", " Calculated (in seconds) using: finalDontShowAgainTime = 20 + finalContinueTime + dontShowAgainTime")
            .defineInRange("dontShowAgainTime", 40, 0, Integer.MAX_VALUE)
        );
        COOLDOWNS.put("permmul", BUILDER
            .comment(" How much time should be added per-permission?", " Calculated (in seconds) using: finalPermMul = 5 + permMul")
            .defineInRange("permMul", 5, 0, Integer.MAX_VALUE)
        );
        BUILDER.pop();

        SPEC = BUILDER.build();
    }
}
