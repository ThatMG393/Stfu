package stfu;

import com.terraformersmc.modmenu.api.ConfigScreenFactory;
import com.terraformersmc.modmenu.api.ModMenuApi;
import dev.isxander.yacl3.api.*;
import dev.isxander.yacl3.api.controller.BooleanControllerBuilder;
import dev.isxander.yacl3.api.controller.EnumControllerBuilder;
import dev.isxander.yacl3.api.controller.IntegerSliderControllerBuilder;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.client.gui.screen.Screen;
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

    @SerialEntry
    public int maxChatHistory = 100;
    @SerialEntry
    public boolean announceAdvancements = true;
    @SerialEntry
    public boolean advancementToasts = true;
    @SerialEntry
    public boolean recipeToasts = false;
    @SerialEntry
    public AdminChat adminChat = AdminChat.ENABLED;
    @SerialEntry
    public CompactChat compactChat = CompactChat.ONLY_CONSECUTIVE;
    @SerialEntry
    public boolean disableWidgetFade = true;
    @SerialEntry
    public boolean disableFade = false;

    @Override
    public ConfigScreenFactory<?> getModConfigScreenFactory() {
        return HANDLER.instance()::generateScreen;
    }

    private Screen generateScreen(Screen parentScreen) {
        return YetAnotherConfigLib.createBuilder()
                .save(HANDLER::save)
                .title(Text.translatable("stfu.options.title"))
                .category(ConfigCategory.createBuilder()
                        .name(Text.translatable("stfu.options.options"))
                        .option(Option.<Integer>createBuilder()
                                .name(Text.translatable("stfu.options.maxChatHistory"))
                                .description(OptionDescription.of(Text.translatable("stfu.options.maxChatHistory.description")))
                                .binding(100, () -> maxChatHistory, val -> maxChatHistory = val)
                                .controller(o -> IntegerSliderControllerBuilder.create(o).range(10, 5000).step(10))
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("stfu.options.announceAdvancements"))
                                .description(OptionDescription.of(Text.translatable("stfu.options.announceAdvancements.description")))
                                .binding(true, () -> announceAdvancements, val -> announceAdvancements = val)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("stfu.options.advancementToasts"))
                                .description(OptionDescription.of(Text.translatable("stfu.options.advancementToasts.description")))
                                .binding(true, () -> advancementToasts, val -> advancementToasts = val)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("stfu.options.recipeToasts"))
                                .description(OptionDescription.of(Text.translatable("stfu.options.recipeToasts.description")))
                                .binding(false, () -> recipeToasts, val -> recipeToasts = val)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.<AdminChat>createBuilder()
                                .name(Text.translatable("stfu.options.adminChat"))
                                .description(OptionDescription.of(Text.translatable("stfu.options.adminChat.description")))
                                .binding(AdminChat.ENABLED, () -> adminChat, val -> adminChat = val)
                                .controller(o -> EnumControllerBuilder.create(o).enumClass(AdminChat.class))
                                .build())
                        .option(Option.<CompactChat>createBuilder()
                                .name(Text.translatable("stfu.options.compactChat"))
                                .description(OptionDescription.of(Text.translatable("stfu.options.compactChat.description")))
                                .binding(CompactChat.ONLY_CONSECUTIVE, () -> compactChat, val -> compactChat = val)
                                .controller(o -> EnumControllerBuilder.create(o).enumClass(CompactChat.class))
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("stfu.options.disableFade.widget"))
                                .description(OptionDescription.of(Text.translatable("stfu.options.disableFade.widget.description")))
                                .binding(true, () -> disableWidgetFade, val -> disableWidgetFade = val)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .option(Option.<Boolean>createBuilder()
                                .name(Text.translatable("stfu.options.disableFade"))
                                .description(OptionDescription.of(Text.translatable("stfu.options.disableFade.description")))
                                .binding(false, () -> disableFade, val -> disableFade = val)
                                .controller(BooleanControllerBuilder::create)
                                .build())
                        .build())
                .build()
                .generateScreen(parentScreen);
    }

    public enum AdminChat implements NameableEnum {
        ENABLED,
        ONLY_PLAYERS,
        DISABLED;

        @Override
        public Text getDisplayName() {
            return Text.translatable("stfu.options.adminChat." + name().toLowerCase());
        }
    }

    public enum CompactChat implements NameableEnum {
        ALL,
        ONLY_CONSECUTIVE,
        NEVER;

        @Override
        public Text getDisplayName() {
            return Text.translatable("stfu.options.compactChat." + name().toLowerCase());
        }
    }
}

