package net.lt_schmiddy.expandedsocialscreen;

import org.lwjgl.glfw.GLFW;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientLifecycleEvents;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.lt_schmiddy.expandedsocialscreen.chatsystem.ChatSystem;
import net.lt_schmiddy.expandedsocialscreen.config.ConfigHandler;
import net.lt_schmiddy.expandedsocialscreen.gui.MainSocialScreen;
import net.lt_schmiddy.expandedsocialscreen.gui.MainSocialScreenGUI;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.options.KeyBinding;
import net.minecraft.client.util.InputUtil;

public class ModEntry implements ModInitializer {

	private static KeyBinding openScreenKeyBinding;

	@Override
	public void onInitialize() {
		// This code runs as soon as Minecraft is in a mod-load-ready state.
		// However, some things (like resources) may still be uninitialized.
		// Proceed with mild caution.
		
		ConfigHandler.load();

		System.out.println("Expanded social screen is loading...");

		openScreenKeyBinding = KeyBindingHelper.registerKeyBinding(new KeyBinding(
			"key.expandedsocialscreen.open", // The translation key of the keybinding's name
			InputUtil.Type.KEYSYM, // The type of the keybinding, KEYSYM for keyboard, MOUSE for mouse.
			GLFW.GLFW_KEY_G, // The keycode of the key
			"category.expandedsocialscreen.ui" // The translation key of the keybinding's category.
		));

		ClientTickEvents.END_CLIENT_TICK.register(client -> {
			while (openScreenKeyBinding.wasPressed()) {
			// client.player.sendMessage(new LiteralText("Key 1 was pressed!"), false);
				MinecraftClient.getInstance().openScreen(new MainSocialScreen(new MainSocialScreenGUI()));
			}
		});

		ClientLifecycleEvents.CLIENT_STOPPING.register(client -> {
			ChatSystem.chatLogger.saveAll();
		});;



	}
}
