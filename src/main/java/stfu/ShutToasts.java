package stfu;

import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.recipe.Recipe;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ClientPlayNetworkHandler.class)
abstract class ShutToasts {
    @Redirect(method = "onGameJoin", at = @At(value = "FIELD", target = "Lnet/minecraft/client/network/ClientPlayNetworkHandler;displayedUnsecureChatWarning:Z", opcode = Opcodes.GETFIELD))
    private boolean youAlreadyShowedItTrustMe(ClientPlayNetworkHandler instance) {
        return true;
    }

    @Redirect(
            method = "method_34011(Lnet/minecraft/client/recipebook/ClientRecipeBook;Lnet/minecraft/recipe/RecipeEntry;)V",
            at = @At(value = "INVOKE", target = "Lnet/minecraft/recipe/Recipe;showNotification()Z")
    )
    private boolean disableRecipeToasts(Recipe<?> instance) {
        return false;
    }
}
