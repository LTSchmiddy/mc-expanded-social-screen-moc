package net.lt_schmiddy.expandedsocialscreen.mixin;

import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.UUID;
import java.util.function.Supplier;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.Element;
import net.minecraft.client.gui.widget.ButtonWidget;
import net.minecraft.client.gui.widget.ElementListWidget;
import net.minecraft.client.network.SocialInteractionsManager;
import net.minecraft.client.util.math.MatrixStack;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;


import net.minecraft.client.gui.screen.multiplayer.SocialInteractionsPlayerListEntry;
import net.minecraft.client.gui.screen.multiplayer.SocialInteractionsScreen;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
// import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
// import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SocialInteractionsPlayerListEntry.class)
public abstract class SocialInteractionsPlayerListEntryMixin extends ElementListWidget.Entry<SocialInteractionsPlayerListEntry> {
    private ButtonWidget dmButton;

    @Shadow
    private List<Element> buttons;

    @Shadow
    private ButtonWidget hideButton;
    @Shadow
    private ButtonWidget showButton;


    @Inject(at = @At("RETURN"), method = "<init>*")
	private void mod_init(MinecraftClient client, SocialInteractionsScreen parent, UUID uuid, String name, Supplier<Identifier> skinTexture, CallbackInfo info) {
        // System.out.println("Loading DM button...");
        

        SocialInteractionsManager socialInteractionsManager = client.getSocialInteractionsManager();
        if (!client.player.getGameProfile().getId().equals(uuid) && !socialInteractionsManager.isPlayerBlocked(uuid)) {

            this.dmButton = new ButtonWidget(0, 0, 20, 20, new TranslatableText("DM"), (buttonWidget) -> {
                this.onDMButtonClick();
            });
    
            this.buttons = ImmutableList.of(this.hideButton, this.showButton, this.dmButton);
         } else {
            this.buttons = ImmutableList.of();
         }
	}

    @Inject(at = @At("RETURN"), method = "render")
	private void mod_render(MatrixStack matrices, int index, int y, int x, int entryWidth, int entryHeight, int mouseX, int mouseY, boolean hovered, float tickDelta, CallbackInfo info) {

        if (this.dmButton == null){
            return;
        }

        this.dmButton.x = x + (entryWidth - this.dmButton.getWidth() - 50);
        this.dmButton.y = y + (entryHeight - this.dmButton.getHeight()) / 2;
        this.dmButton.render(matrices, mouseX, mouseY, tickDelta);
	}

    public void onDMButtonClick() {
        
    }
}
