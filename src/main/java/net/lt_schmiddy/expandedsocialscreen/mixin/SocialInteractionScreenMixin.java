package net.lt_schmiddy.expandedsocialscreen.mixin;

import net.minecraft.client.gui.screen.Screen;
// import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.multiplayer.SocialInteractionsScreen;
import net.minecraft.text.Text;

import org.spongepowered.asm.mixin.Mixin;
// import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
// import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(SocialInteractionsScreen.class)
public abstract class SocialInteractionScreenMixin extends Screen {

	protected SocialInteractionScreenMixin(Text title) {
		super(title);
		//TODO Auto-generated constructor stub
	}

	@Inject(at = @At("HEAD"), method = "init")
	private void mod_init(CallbackInfo info) {
		// this.width *= 2;
	}
}
