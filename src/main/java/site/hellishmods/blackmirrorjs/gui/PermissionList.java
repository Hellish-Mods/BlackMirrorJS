package site.hellishmods.blackmirrorjs.gui;

import com.mojang.blaze3d.matrix.MatrixStack;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.widget.list.ExtendedList;
import net.minecraft.client.gui.widget.list.ExtendedList.AbstractListEntry;
import net.minecraft.util.text.StringTextComponent;
import site.hellishmods.blackmirrorjs.config.BlackmirrorCommonConfig;
import site.hellishmods.blackmirrorjs.config.Permission;

import static site.hellishmods.blackmirrorjs.lib.TextComponentToReorderingProcessor.toReorderProcessor;

public class PermissionList extends ExtendedList<PermissionList.PermissionEntry> {
    private Minecraft ms;

    @SuppressWarnings("resource")
    public PermissionList(int startY, int endY, int itemHeight) {
        super(Minecraft.getInstance(), Minecraft.getInstance().screen.width, Minecraft.getInstance().screen.height, startY, endY, itemHeight);
        this.ms = Minecraft.getInstance();

        BlackmirrorCommonConfig.getPerms().forEach(p -> {
            addEntry(new PermissionEntry(p));
        });
    }

    @Override
    protected void renderBackground(MatrixStack stack) {
        ms.screen.renderBackground(stack);
    }

    class PermissionEntry extends AbstractListEntry<PermissionEntry> {
        private Permission perm;
        public PermissionEntry(Permission perm) {
            this.perm = perm;
        }
        private void drawText(MatrixStack stack, String text, int top, int color) {
            ms.font.drawShadow(stack, text, (float)(ms.screen.width/2-ms.font.width(text)/2), (float)(top+1), color, true);
        }
        @Override
        public void render(MatrixStack stack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean mouseOver, float partialTicks) {
            drawText(stack, perm.title+" ["+perm.level.suffix+"]", top, perm.level.color);
            if (mouseOver) ms.screen.renderTooltip(stack, toReorderProcessor(new StringTextComponent(perm.description), ms), mouseX, mouseY);
        }
    }
}
