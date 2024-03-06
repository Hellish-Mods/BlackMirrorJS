package site.hellishmods.blackmirrorjs.config;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import net.minecraft.client.Minecraft;
import site.hellishmods.blackmirrorjs.lib.LangKeys;

public class Permission {
    private static final HashMap<String, String> LANG_KEYS = LangKeys.getLangKeys(Minecraft.getInstance());
    private static final List<Permission> PERMISSIONS = Arrays.asList(
        new Permission("example")
    );
    private static final HashMap<String, Permission> ID_TO_PERM = new HashMap<>();
    static {
        PERMISSIONS.forEach(p -> {
            ID_TO_PERM.put(p.id, p);
        });
    }

    public static Permission getPermById(String id) {
        if (ID_TO_PERM.keySet().contains(id)) return ID_TO_PERM.get(id);
        return null;
    }

    public String id;
    public String title; // TODO: perm levels and colors

    private Permission(String id) {
        this.id = id;
        title = LANG_KEYS.get("permissions."+id);
    }
}
