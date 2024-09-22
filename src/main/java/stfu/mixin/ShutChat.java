package stfu.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.client.gui.hud.ChatHud;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableTextContent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import stfu.Options;

@Mixin(ChatHud.class)
public class ShutChat {
    @ModifyExpressionValue(method = {"addToMessageHistory", "addVisibleMessage", "addMessage(Lnet/minecraft/client/gui/hud/ChatHudLine;)V"}, at =
    @At(value = "CONSTANT", args = "intValue=100"))
    private int moreHistory(int original) {
        return Options.maxChatHistory.getValue();
    }

    @Inject(method = "addMessage(Lnet/minecraft/text/Text;)V", at = @At("HEAD"), cancellable = true)
    private void filter(Text message, CallbackInfo ci) {
        if(!(message instanceof MutableText mutable && mutable.getContent() instanceof TranslatableTextContent translatable)) return;

        if(translatable.getKey().startsWith("chat.type.advancement")) {
            if(!Options.announceAdvancements.getValue()) ci.cancel();
        } else if(translatable.getKey().equals("chat.type.admin")) {
            Options.AdminChat adminChat = Options.adminChat.getValue();
            if(adminChat == Options.AdminChat.DISABLED || (adminChat == Options.AdminChat.ONLY_PLAYERS && translatable.getArgs()[0].equals("@"))) ci.cancel();
        }
    }
}
