package stfu;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.client.keybinding.v1.KeyBindingHelper;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.client.util.InputUtil;
import org.lwjgl.glfw.GLFW;

public class Main implements ModInitializer {
    public static final KeyBinding NARRATOR_KEY = KeyBindingHelper.registerKeyBinding(new KeyBinding(
            "options.narrator_hotkey",
            InputUtil.Type.KEYSYM,
            GLFW.GLFW_KEY_UNKNOWN,
            "key.categories.misc"
    ));

    @Override
    public void onInitialize() {
        Config.HANDLER.load();
    }
}
