package stfu.mixin;

import net.minecraft.client.gui.screen.TitleScreen;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import stfu.Config;

@Mixin(TitleScreen.class)
public class TitleScreenMixin {
    @ModifyVariable(method = "<init>(ZLnet/minecraft/client/gui/LogoDrawer;)V", argsOnly = true, ordinal = 0, at = @At("HEAD"))
    private static boolean shutLoadFade(boolean bl) {
        return !Config.HANDLER.instance().disableWidgetFade && bl;
    }
}
