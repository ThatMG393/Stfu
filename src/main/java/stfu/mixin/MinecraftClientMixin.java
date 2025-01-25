package stfu.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.ReconfiguringScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.world.ClientWorld;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stfu.EmptyScreen;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow @Nullable public Screen currentScreen;
    @Shadow @Nullable public ClientWorld world;
    @Shadow public abstract ClientPlayNetworkHandler getNetworkHandler();

    @ModifyVariable(at = @At("HEAD"), method = "setScreen", ordinal = 0, argsOnly = true)
    public Screen setScreen(Screen screen) {
        if (screen instanceof ReconfiguringScreen) screen = new EmptyScreen.Configuration(getNetworkHandler().getConnection());
        else if (screen instanceof DownloadingTerrainScreen) {
            if (this.currentScreen instanceof EmptyScreen) screen = null;
            else screen = new EmptyScreen();
        }
        return screen;
    }

    @Inject(method = "setScreen", at = @At("HEAD"), cancellable = true)
    public void setScreenCancelCloseScreen(Screen screen, CallbackInfo ci) {
        if (this.world != null && screen instanceof EmptyScreen) ci.cancel();
    }
}
