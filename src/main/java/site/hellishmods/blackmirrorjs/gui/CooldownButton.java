package site.hellishmods.blackmirrorjs.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import site.hellishmods.blackmirrorjs.lib.Cooldown;
import site.hellishmods.blackmirrorjs.lib.LangKeys;

public class CooldownButton extends Button {
    private Cooldown cooldown;
    private Minecraft ms;

    public CooldownButton(int x, int y, int w, int h, Cooldown cooldown, ITextComponent text, IPressable function) {
        super(x, y, w, h, text, function);

        this.cooldown = cooldown;
        this.ms = Minecraft.getInstance();

        active = false;
        this.cooldown.setCallback(() -> {active = true;});
        this.cooldown.checkForEnd();
    }

    @Override
    public void renderToolTip(MatrixStack stack, int x, int y) {
        if (active) return;
        ms.screen.renderTooltip(stack, ms.font.split(new StringTextComponent(String.format(LangKeys.getLangKeys(ms).get("cooldown"), (int)Math.ceil(cooldown.ticks/20f))), Math.max(ms.screen.width / 2 - 43, 170)), x, y);
    }
}
