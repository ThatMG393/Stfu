package stfu;

import net.minecraft.client.session.telemetry.TelemetryManager;
import net.minecraft.client.session.telemetry.TelemetrySender;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(TelemetryManager.class)
abstract class ShutTelemetry {
    @Inject(method = "getSender", at = @At("HEAD"), cancellable = true)
    private void NoopSender(CallbackInfoReturnable<TelemetrySender> cir) {
        cir.setReturnValue(TelemetrySender.NOOP);
    }
}
