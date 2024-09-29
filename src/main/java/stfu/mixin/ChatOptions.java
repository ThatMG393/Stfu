package stfu.mixin;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.option.ChatOptionsScreen;
import net.minecraft.client.gui.screen.option.GameOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stfu.Options;

@Mixin(ChatOptionsScreen.class)
public abstract class ChatOptions extends GameOptionsScreen {
    public ChatOptions(Screen parent, GameOptions gameOptions, Text title) {
        super(parent, gameOptions, title);
    }

    @Inject(method = "addOptions", at = @At("TAIL"))
    private void addMaxChatHistoryOption(CallbackInfo ci) {
        if (body != null) body.addAll(Options.maxChatHistory, Options.adminChat, Options.announceAdvancements, Options.compactChat);
    }
}
