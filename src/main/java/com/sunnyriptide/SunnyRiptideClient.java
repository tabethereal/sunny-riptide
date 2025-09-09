package com.sunnyriptide;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;

public class SunnyRiptideClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientPlayNetworking.registerGlobalReceiver(SunnyRiptidePayload.ID, SunnyRiptideClient::onReceivePayload);
    }

    public static void onReceivePayload(SunnyRiptidePayload payload, ClientPlayNetworking.Context context) {
        SunnyRiptide.allowSunnyRiptide = payload.enabled();
    }
}
