package stfu;

import com.mojang.serialization.Codec;
import net.minecraft.client.option.GameOptions;
import net.minecraft.client.option.SimpleOption;
import net.minecraft.util.TranslatableOption;

import java.util.List;

public class Options {
    public static final SimpleOption<Integer> maxChatHistory = new SimpleOption<>(
            "options.maxChatHistory",
            SimpleOption.emptyTooltip(),
            GameOptions::getGenericValueText,
            new SimpleOption.ValidatingIntSliderCallbacks(10, 5000),
            Codec.intRange(10, 5000),
            100,
            value -> {}
    );

    public static final SimpleOption<Boolean> announceAdvancements = SimpleOption.ofBoolean("options.announceAdvancements", true);

    public static final SimpleOption<AdminChat> adminChat = new SimpleOption<>(
            "options.adminChat",
            SimpleOption.emptyTooltip(),
            SimpleOption.enumValueText(),
            new SimpleOption.PotentialValuesBasedCallbacks<>(List.of(AdminChat.values()), Codec.INT.xmap(i->AdminChat.values()[i],
                    AdminChat::ordinal)),
            AdminChat.ENABLED,
            value -> {
            }
    );

    public enum AdminChat implements TranslatableOption  {
        ENABLED,
        ONLY_PLAYERS,
        DISABLED;

        @Override
        public int getId() {
            return ordinal();
        }

        @Override
        public String getTranslationKey() {
            return "options.adminChat." + name().toLowerCase();
        }
    }
}
