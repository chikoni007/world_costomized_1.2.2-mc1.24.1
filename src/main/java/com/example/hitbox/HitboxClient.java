package com.example.hitbox;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import net.minecraft.text.Text;
import org.lwjgl.glfw.GLFW;

public class HitboxClient implements ClientModInitializer {
    public static float margin = 0.0f;
    private static final float STEP = 0.1f;
    private static final float MAX = 2.0f;

    private static KeyBinding inc;
    private static KeyBinding dec;

    @Override
    public void onInitializeClient() {
        inc = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hitbox.increase", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_RIGHT_BRACKET, "category.hitbox"));
        dec = KeyBindingHelper.registerKeyBinding(new KeyBinding(
                "key.hitbox.decrease", InputUtil.Type.KEYSYM,
                GLFW.GLFW_KEY_LEFT_BRACKET, "category.hitbox"));

        ClientTickEvents.END_CLIENT_TICK.register(client -> {
            while (inc.wasPressed()) change(client, STEP);
            while (dec.wasPressed()) change(client, -STEP);
        });
    }

    private static void change(MinecraftClient client, float delta) {
        if (!client.isInSingleplayer()) return;
        margin = Math.max(0f, Math.min(MAX, margin + delta));
        if (client.player != null) {
            client.player.sendMessage(Text.literal(String.format("Hitbox +%.1f", margin)), true);
        }
    }
}
