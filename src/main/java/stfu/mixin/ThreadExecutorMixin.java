package stfu.mixin;

import net.minecraft.util.thread.ThreadExecutor;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;

import java.util.concurrent.locks.LockSupport;

@Mixin(ThreadExecutor.class)
public class ThreadExecutorMixin {
    /**
     * @author Stfu
     * @reason The original is horribly inefficient
     */
    @Overwrite
    public void waitForTasks() {
        LockSupport.parkNanos("waiting for tasks", 500000L);
    }
}
