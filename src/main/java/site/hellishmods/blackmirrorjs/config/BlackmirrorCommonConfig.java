package site.hellishmods.blackmirrorjs.config;

import java.util.ArrayList;
import com.google.common.collect.Lists;

import net.minecraftforge.common.ForgeConfigSpec;

public class BlackmirrorCommonConfig {
    public static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
    public static final ForgeConfigSpec SPEC;

    private static final ForgeConfigSpec.ConfigValue<ArrayList<String>> PERM_IDS;

    static {
        PERM_IDS = BUILDER
            .comment(" A list of all active permission IDs (for the full list, check out https://wiki.modernmodpacks.site/wiki/v/hellish-mods/blackmirrorjs/permission-list)")
            .define("permissions", Lists.newArrayList("example"));

        SPEC = BUILDER.build();
    }

    public static ArrayList<Permission> getPerms() {
        ArrayList<Permission> perms = new ArrayList<>();
        PERM_IDS.get().forEach(id -> {
            Permission perm = Permission.getPermById(id);
            if (perm!=null) perms.add(perm);
        });
        return perms;
    }
}
