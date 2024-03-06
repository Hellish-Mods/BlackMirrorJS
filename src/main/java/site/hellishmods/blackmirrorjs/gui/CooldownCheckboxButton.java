package site.hellishmods.blackmirrorjs.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.button.CheckboxButton;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import site.hellishmods.blackmirrorjs.lib.Cooldown;
import site.hellishmods.blackmirrorjs.lib.LangKeys;

public class CooldownCheckboxButton extends CheckboxButton {
    private Cooldown cooldown;
    private Minecraft ms;

    public CooldownCheckboxButton(int x, int y, int w, int h, Cooldown cooldown, ITextComponent text, boolean selected) {
        super(x, y, w, h, text.copy().withStyle(TextFormatting.GRAY), selected);

        this.cooldown = cooldown;
        this.ms = Minecraft.getInstance();

        active = false;
        this.cooldown.setCallback(() -> {
            active = true;
            setMessage(text);
        });
        this.cooldown.checkForEnd();
    }

    @Override
    public void renderButton(MatrixStack stack, int mouseX, int mouseY, float partialTicks) {
        super.renderButton(stack, mouseX, mouseY, partialTicks);
        if (isHovered()) {
            renderToolTip(stack, mouseX, mouseY);
        }
    }
    @Override
    public void renderToolTip(MatrixStack stack, int x, int y) {
        if (active) return;
        ms.screen.renderTooltip(stack, ms.font.split(new StringTextComponent(String.format(LangKeys.getLangKeys(ms).get("cooldown"), (int)Math.ceil(cooldown.ticks/20f))), Math.max(ms.screen.width / 2 - 43, 170)), x, y);
    }
}
