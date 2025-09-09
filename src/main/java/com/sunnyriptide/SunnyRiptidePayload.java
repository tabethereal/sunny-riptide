package com.sunnyriptide;

import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import net.minecraft.util.Identifier;

public record SunnyRiptidePayload(boolean enabled) implements CustomPayload {
    public static final Id<SunnyRiptidePayload> ID = new Id<>(Identifier.of(SunnyRiptide.MOD_ID, "enabled"));
    public static final PacketCodec<PacketByteBuf, SunnyRiptidePayload> CODEC = PacketCodec.tuple(
            PacketCodecs.BOOL, SunnyRiptidePayload::enabled,
            SunnyRiptidePayload::new);

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}
