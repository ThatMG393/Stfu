package stfu.mixin;

import net.minecraft.client.gui.screen.option.AccessibilityOptionsScreen;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Arrays;

@Mixin(AccessibilityOptionsScreen.class)
public class AccessibilityOptionsMixin {
    @Inject(method = "getOptions", at = @At("RETURN"), cancellable = true)
    private static void removeNarratorHotkeySetting(GameOptions gameOptions, CallbackInfoReturnable<SimpleOption<?>[]> cir) {
        cir.setReturnValue(Arrays.stream(cir.getReturnValue()).filter(option -> !option.equals(gameOptions.getNarratorHotkey())).toArray(SimpleOption[]::new));
    }
}
