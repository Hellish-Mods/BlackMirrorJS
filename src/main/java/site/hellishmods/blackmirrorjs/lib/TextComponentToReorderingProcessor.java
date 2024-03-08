package site.hellishmods.blackmirrorjs.lib;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.util.IReorderingProcessor;
import net.minecraft.util.text.ITextComponent;

public class TextComponentToReorderingProcessor {
    public static List<IReorderingProcessor> toReorderProcessor(ITextComponent text, Minecraft ms) {
        return ms.font.split(text, Math.max(ms.screen.width / 2 - 43, 170));
    }
}
