package stfu.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.JumpingMount;
import net.minecraft.entity.LivingEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.ModifyVariable;
import org.spongepowered.asm.mixin.injection.Redirect;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(InGameHud.class)
public abstract class BetterHud {
    @Shadow
    @Final
    private MinecraftClient client;

    @Shadow
    @Nullable
    protected abstract LivingEntity getRiddenEntity();

    @Shadow
    protected abstract int getHeartCount(@Nullable LivingEntity entity);

    @ModifyVariable(method = "renderMountHealth", at = @At(value = "STORE"), ordinal = 2)
    private int higherMountHealth(int y) {
        return client.interactionManager.hasStatusBars() ? y - 10 : y;
    }

    @Redirect(method = "renderStatusBars", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;getHeartCount(Lnet/minecraft/entity/LivingEntity;)I"))
    private int alwaysRenderFood(InGameHud inGameHud, LivingEntity entity) {
        return 0;
    }

    @Inject(method = "getAirBubbleY", at = @At(value = "RETURN"), cancellable = true)
    private void moveAirUp(int heartCount, int top, CallbackInfoReturnable<Integer> cir) {
        LivingEntity entity = getRiddenEntity();
        if (entity != null) cir.setReturnValue(cir.getReturnValue() - getHeartCount(entity));
    }

    @Redirect(method = "renderMainHud", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/network/ClientPlayerEntity;getJumpingMount()Lnet/minecraft/entity/JumpingMount;"))
    private JumpingMount showXp(ClientPlayerEntity player) {
        return !client.interactionManager.hasExperienceBar() || client.options.jumpKey.isPressed()
                || player.getMountJumpStrength() > 0 ? player.getJumpingMount() : null;
    }

    @Redirect(method = "renderMainHud", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;shouldRenderExperience()Z"))
    private boolean renderXp(InGameHud instance) {
        return client.interactionManager.hasExperienceBar();
    }

    @Redirect(method = "renderExperienceLevel", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/hud/InGameHud;shouldRenderExperience()Z"))
    private boolean renderXpLevel(InGameHud instance) {
        return client.interactionManager.hasExperienceBar() &&
                ((client.player.getJumpingMount() != null
                        && !client.options.jumpKey.isPressed()
                        && client.player.getMountJumpStrength() <= 0)
                        || client.player.getJumpingMount() == null);
    }
}