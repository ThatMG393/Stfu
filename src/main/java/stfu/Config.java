package stfu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.NameableEnum;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.autogen.AutoGen;
import dev.isxander.yacl3.config.v2.api.autogen.EnumCycler;
import dev.isxander.yacl3.config.v2.api.autogen.IntSlider;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class Config implements ModMenuApi {
    public static ConfigClassHandler<Config> HANDLER = ConfigClassHandler.createBuilder(Config.class)
            .id(Identifier.of("stfu", "config"))
            .serializer(config -> GsonConfigSerializerBuilder.create(config)
                    .setPath(FabricLoader.getInstance().getConfigDir().resolve("stfu.json5"))
                    .setJson5(true)
                    .build())
            .build();

    private static final String category = "options";

    @AutoGen(category = category)
    @IntSlider(min = 10, max = 5000, step = 10)
    @SerialEntry
    public int maxChatHistory = 100;

    @AutoGen(category = category)
    @dev.isxander.yacl3.config.v2.api.autogen.Boolean
    @SerialEntry
    public boolean announceAdvancements = true;

    @AutoGen(category = category)
    @dev.isxander.yacl3.config.v2.api.autogen.Boolean
    @SerialEntry
    public boolean advancementToasts = true;

    @AutoGen(category = category)
    @dev.isxander.yacl3.config.v2.api.autogen.Boolean
    @SerialEntry
    public boolean recipeToasts = false;

    @AutoGen(category = category)
    @EnumCycler
    @SerialEntry
    public AdminChat adminChat = AdminChat.ENABLED;

    @AutoGen(category = category)
    @EnumCycler
    @SerialEntry
    public CompactChat compactChat = CompactChat.ONLY_CONSECUTIVE;

    @AutoGen(category = category)
    @dev.isxander.yacl3.config.v2.api.autogen.Boolean
    @SerialEntry
    public boolean disableWidgetFade = true;

    @AutoGen(category = category)
    @dev.isxander.yacl3.config.v2.api.autogen.Boolean
    @SerialEntry
    public boolean disableFade = false;

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return s->HANDLER.generateGui().generateScreen(s);
    }

    public enum AdminChat implements NameableEnum {
        ENABLED,
        ONLY_PLAYERS,
        DISABLED;

        @Override
        public Text getDisplayName() {
            return Text.translatable("yacl3.config.stfu:config.adminChat." + name().toLowerCase());
        }
    }

    public enum CompactChat implements NameableEnum {
        ALL,
        ONLY_CONSECUTIVE,
        NEVER;

        @Override
        public Text getDisplayName() {
            return Text.translatable("yacl3.config.stfu:config.compactChat." + name().toLowerCase());
        }
    }
}

