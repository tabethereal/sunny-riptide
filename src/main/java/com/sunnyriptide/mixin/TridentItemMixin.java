package com.sunnyriptide.mixin;

import com.sunnyriptide.SunnyRiptide;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.TridentItem;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(TridentItem.class)
public class TridentItemMixin {
    @Redirect(method = "use", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isTouchingWaterOrRain()Z"))
    private boolean useAlwaysTouchingWaterOrRain(PlayerEntity instance) {
        return SunnyRiptide.allowSunnyRiptide || instance.isTouchingWaterOrRain();
    }

    @Redirect(method = "onStoppedUsing", at = @At(value = "INVOKE", target = "Lnet/minecraft/entity/player/PlayerEntity;isTouchingWaterOrRain()Z"))
    private boolean releaseAlwaysTouchingWaterOrRain(PlayerEntity instance) {
        return SunnyRiptide.allowSunnyRiptide || instance.isTouchingWaterOrRain();
    }
}
