package com.sunnyriptide;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class SunnyRiptide implements ModInitializer {
    public static boolean allowSunnyRiptide = false;

    @Override
    public void onInitialize() {
        CommandRegistrationCallback.EVENT.register(SunnyRiptide::registerCommand);
    }

    public static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        LiteralArgumentBuilder<ServerCommandSource> command = literal("sunnyriptide")
                .requires((source) -> source.hasPermissionLevel(2))
                .then(argument("enabled", BoolArgumentType.bool())
                        .executes((context) -> {
                            final boolean enabled = BoolArgumentType.getBool(context, "enabled");
                            SunnyRiptide.allowSunnyRiptide = enabled;
                            context.getSource().sendFeedback(() -> Text.literal("Sunny Riptide enabled: %b".formatted(enabled)), true);
                            return 1;
                        })
                );
        dispatcher.register(command);
    }
}
