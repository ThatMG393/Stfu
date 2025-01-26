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
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import stfu.Config;
import stfu.EmptyScreen;

@Mixin(MinecraftClient.class)
public abstract class MinecraftClientMixin {
    @Shadow @Nullable public ClientWorld world;
    @Shadow public abstract ClientPlayNetworkHandler getNetworkHandler();

    @ModifyVariable(at = @At("HEAD"), method = "setScreen", ordinal = 0, argsOnly = true)
    public Screen setScreen(Screen screen) {
        if(Config.HANDLER.instance().disableLoadingTerrain) {
            if (screen instanceof ReconfiguringScreen) screen = new EmptyScreen.Configuration(getNetworkHandler().getConnection());
            else if (screen instanceof DownloadingTerrainScreen) {
                if(world == null) screen = new EmptyScreen();
                else screen = null;
            }
        }
        return screen;
    }
}
