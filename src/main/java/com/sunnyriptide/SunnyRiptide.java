package com.sunnyriptide;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.fabricmc.fabric.api.networking.v1.PacketSender;
import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.command.CommandRegistryAccess;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;

import static net.minecraft.server.command.CommandManager.argument;
import static net.minecraft.server.command.CommandManager.literal;

public class SunnyRiptide implements ModInitializer {
    public static final String MOD_ID = "sunnyriptide";
    public static boolean allowSunnyRiptide = false;

    @Override
    public void onInitialize() {
        PayloadTypeRegistry.playS2C().register(SunnyRiptidePayload.ID, SunnyRiptidePayload.CODEC);
        ServerPlayConnectionEvents.JOIN.register(SunnyRiptide::onPlayerJoin);
        CommandRegistrationCallback.EVENT.register(SunnyRiptide::registerCommand);
    }

    public static void onPlayerJoin(ServerPlayNetworkHandler handler, PacketSender sender, MinecraftServer server) {
        server.execute(() -> {
            ServerPlayNetworking.send(handler.getPlayer(), new SunnyRiptidePayload(allowSunnyRiptide));
        });
    }

    public static void registerCommand(CommandDispatcher<ServerCommandSource> dispatcher, CommandRegistryAccess registryAccess, CommandManager.RegistrationEnvironment environment) {
        LiteralArgumentBuilder<ServerCommandSource> command = literal("sunnyriptide")
                .requires((source) -> source.hasPermissionLevel(2))
                .then(argument("enabled", BoolArgumentType.bool())
                        .executes((context) -> {
                            final boolean enabled = BoolArgumentType.getBool(context, "enabled");
                            if (SunnyRiptide.allowSunnyRiptide != enabled) {
                                SunnyRiptide.allowSunnyRiptide = enabled;
                                for (ServerPlayerEntity player : context.getSource().getWorld().getPlayers())
                                    ServerPlayNetworking.send(player, new SunnyRiptidePayload(allowSunnyRiptide));
                            }
                            context.getSource().sendFeedback(() -> Text.literal("Sunny Riptide enabled: %b".formatted(enabled)), true);
                            return 1;
                        })
                );
        dispatcher.register(command);
    }
}
