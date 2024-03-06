package site.hellishmods.blackmirrorjs.lib;

import java.util.Arrays;
import java.util.HashMap;

import net.minecraft.client.Minecraft;

public class LangKeys {
    private static final HashMap<String, HashMap<String, String>> LANG_KEYS = new HashMap<>();
    static {
        HashMap<String, String> en_us = new HashMap<>();
        en_us.put("title", "Warning! Please read this as this can do serious harm to your pc!\nThis isn't just regular yada yada".toUpperCase());
        en_us.put("description", String.join("\n", Arrays.asList(
            "This instance uses KubeJS along with a configured instance of BlackMirrorJS, which is",
            "an addon that allows modpack developers to reflect more classes than vanilla KJS allows.",
            "This basically means that, potetntially, by launching this modpack and agreeing",
            "to the following permissions you can do harm to your pc and might even have your",
            "personal infomation leaked. Please make sure you trust the source where you got",
            "this modpack from and you think it's a good idea to give it these permissions.",
            "Hover over them to see more info. If you launched Minecraft with admin/sudo",
            "permissions, please close the game IMMEDIATELY and reopen it without them."
        )));
        en_us.put("colorcodes", "\u00A7aGreen [!]\u00A7f - least likely to do harm  \u00A7eYellow [!!]\u00A7f - potentially harmful  \u00A7cRed [!!!]\u00A7f - very easy to do harm with");
        en_us.put("complaints", "If you didn't read this, do not complain that your computer has a virus afterwards!".toUpperCase());
        en_us.put("cooldown", "This button will become active in %d seconds. This is purposefully annoying so that you would actually use that time to read the text or just quit the game.");
        en_us.put("donotshowagain", "Don't show this popup again after restarting the game");
        en_us.put("continue", "I understand all of the risks, continue");
        en_us.put("permissions.example", "Example");
        LANG_KEYS.put("en_us", en_us);
    } 

    public static HashMap<String, String> getLangKeys(Minecraft mc) {
        String code = mc.getLanguageManager().getSelected().getCode();
        return LANG_KEYS.keySet().contains(code) ? LANG_KEYS.get(code) : LANG_KEYS.get("en_us");
    }
}
