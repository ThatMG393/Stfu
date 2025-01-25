package stfu.mixin;

import net.minecraft.client.gui.screen.option.ControlsListWidget;
import net.minecraft.client.option.KeyBinding;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ControlsListWidget.KeyBindingEntry.class)
public class KeyBindingWidgetMixin {
    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;isUnbound()Z"))
    private boolean shutReusedModifierKeys(KeyBinding instance) {
        return instance.isUnbound() || instance.getCategory().equals(KeyBinding.CREATIVE_CATEGORY);
    }

    @Redirect(method = "update", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/option/KeyBinding;equals(Lnet/minecraft/client/option/KeyBinding;)Z"))
    private boolean shutReusedModifierKeys(KeyBinding instance, KeyBinding other) {
        return instance.equals(other) && !other.getCategory().equals(KeyBinding.CREATIVE_CATEGORY);
    }
}
