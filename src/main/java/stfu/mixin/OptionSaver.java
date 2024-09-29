package stfu.mixin;

import net.minecraft.client.option.GameOptions;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stfu.Options;

@Mixin(GameOptions.class)
public class OptionSaver {
    @Inject(method = "accept", at = @At("TAIL"))
    private void saveOptions(GameOptions.Visitor visitor, CallbackInfo ci) {
        visitor.accept("maxChatHistory", Options.maxChatHistory);
        visitor.accept("announceAdvancements", Options.announceAdvancements);
        visitor.accept("adminChat", Options.adminChat);
        visitor.accept("compactChat", Options.compactChat);
    }
}
