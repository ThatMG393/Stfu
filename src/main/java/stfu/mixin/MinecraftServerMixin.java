package stfu.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.MinecraftServer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import stfu.Config;

@Mixin(MinecraftServer.class)
public class MinecraftServerMixin {
    @WrapOperation(method = "startServer", at = @At(value = "INVOKE", target = "Ljava/lang/Thread;setUncaughtExceptionHandler(Ljava/lang/Thread$UncaughtExceptionHandler;)V"))
    private static void startServer(Thread instance, Thread.UncaughtExceptionHandler ueh, Operation<Void> original) {
        original.call(instance, ueh);
        instance.setPriority(Config.HANDLER.instance().serverThreadPriority); // Integrated Server
    }
}
