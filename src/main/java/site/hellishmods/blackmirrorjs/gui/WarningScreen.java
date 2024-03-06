package site.hellishmods.blackmirrorjs.gui;

import java.util.HashMap;
import java.util.List;

import com.mojang.blaze3d.matrix.MatrixStack;

import dev.latvian.kubejs.client.ClientEventJS;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.button.Button;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import site.hellishmods.blackmirrorjs.blackmirrorjs;
import site.hellishmods.blackmirrorjs.config.BlackmirrorClientConfig;
import site.hellishmods.blackmirrorjs.config.BlackmirrorCommonConfig;
import site.hellishmods.blackmirrorjs.config.Permission;
import site.hellishmods.blackmirrorjs.lib.Cooldown;
import site.hellishmods.blackmirrorjs.lib.LangKeys;

public class WarningScreen extends Screen {
    private final static Integer MARGIN = 30;
    private final static Integer DEFAULT_COLOR = 0xffffff;
    private final static Integer DEFAULT_GUI_SCALE = 2;

    private HashMap<String, String> langKeys;
    private int ogGuiScale;
    private List<Permission> perms;
    private boolean closed = false;

    private CooldownCheckboxButton doNotShowAgainButton; // TODO: implement

    private final static Integer CONTINUE_COOLDOWN = BlackmirrorClientConfig.COOLDOWNS.get("continue").get();
    private final static Integer PERM_MULT = BlackmirrorClientConfig.COOLDOWNS.get("permmul").get();
    private Cooldown continueCooldown;
    private Cooldown doNotShowAgainCooldown;

    public WarningScreen() {
        super(new StringTextComponent(LangKeys.getLangKeys(Minecraft.getInstance()).get("title")));

        Minecraft mc = Minecraft.getInstance();
        this.langKeys = LangKeys.getLangKeys(mc);
        this.ogGuiScale = mc.options.guiScale;
        this.perms = BlackmirrorCommonConfig.getPerms();

        this.continueCooldown = new Cooldown((30+CONTINUE_COOLDOWN+(perms.size()*PERM_MULT))*20);
        this.doNotShowAgainCooldown = new Cooldown(400+(BlackmirrorClientConfig.COOLDOWNS.get("donotshowagain").get()*20)+continueCooldown.ticks);

        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            if (!closed) restoreOgGuiScale();
        }));
    }

    private void drawCenteredMultilineString(MatrixStack stack, FontRenderer font, ITextComponent text, int x, int y, int color) {
        String[] lines = text.getContents().split("\n");

        for (int l=0; l<lines.length; l++) {
            drawCenteredString(stack, font, new StringTextComponent(lines[l]).withStyle(text.getStyle()), x, y+(MARGIN/2)*l, color);
        }
    }
    private void setGuiScale(int scale) {
        minecraft.options.guiScale = scale;
        minecraft.resizeDisplay();
    }
    private void restoreOgGuiScale() {
        closed = true;
        setGuiScale(ogGuiScale);
    }

    @Override
    public void init() {
        super.init();
        if (perms.size()==0 && !closed) {
            onClose();
            return;
        }

        if (minecraft.options.guiScale!=DEFAULT_GUI_SCALE && !closed) setGuiScale(DEFAULT_GUI_SCALE);

        doNotShowAgainButton = addButton(new CooldownCheckboxButton(width/2-150, height-(MARGIN/3)*6, 300, 20, doNotShowAgainCooldown, new StringTextComponent(langKeys.get("donotshowagain")), false));
        addButton(new Button(width/2-205, height-MARGIN, 200, 20, new TranslationTextComponent("menu.quit"), b -> {restoreOgGuiScale(); minecraft.stop();}));
        addButton(new CooldownButton(width/2+5, height-MARGIN, 200, 20, continueCooldown, new StringTextComponent(langKeys.get("continue")), b -> {onClose();}));
    }
    @Override
    public void render(MatrixStack matrixStack, int mouseX, int mouseY, float partialTicks) {
        renderBackground(matrixStack);
        new PermissionList((MARGIN/3)*17, height-(MARGIN/3)*11, 20).render(matrixStack, mouseX, mouseY, partialTicks);
        super.render(matrixStack, mouseX, mouseY, partialTicks);

        drawCenteredMultilineString(matrixStack, font, new StringTextComponent(langKeys.get("title")).withStyle(TextFormatting.RED), width/2, MARGIN/2, DEFAULT_COLOR);
        drawCenteredMultilineString(matrixStack, font, new StringTextComponent(langKeys.get("description")), width/2, (MARGIN/3)*5, DEFAULT_COLOR);

        drawCenteredString(matrixStack, font, new StringTextComponent(langKeys.get("colorcodes")).withStyle(TextFormatting.RED), width/2, height-(MARGIN/3)*10, DEFAULT_COLOR);
        drawCenteredString(matrixStack, font, new StringTextComponent(langKeys.get("complaints")).withStyle(TextFormatting.RED), width/2, height-(MARGIN/3)*8, DEFAULT_COLOR);
    }
    @Override
    public void onClose() {
        restoreOgGuiScale();

        new ClientEventJS().post(blackmirrorjs.MOD_ID+".user_agreement");
        super.onClose();
    }
}
