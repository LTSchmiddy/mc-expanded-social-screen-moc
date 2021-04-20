package net.lt_schmiddy.expandedsocialscreen.mixin;

import net.lt_schmiddy.expandedsocialscreen.chatsystem.ChatSystem;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.ClientChatListener;
import net.minecraft.client.gui.hud.ChatHudListener;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;

import java.util.UUID;

import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
// import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
// import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ChatHudListener.class)
public abstract class ChatInterceptionMixin implements ClientChatListener {
    @Shadow
    private MinecraftClient client;

    @Inject(at = @At("HEAD"), method = "onChatMessage")
	private void interceptMessage(MessageType messageType, Text message, UUID senderUuid, CallbackInfo info) {
        ChatSystem.messageHandler.processMessage(messageType, message, senderUuid);

	}
}
