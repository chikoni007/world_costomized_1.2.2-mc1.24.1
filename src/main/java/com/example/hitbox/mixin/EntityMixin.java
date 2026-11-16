package com.example.hitbox.mixin;

import com.example.hitbox.HitboxClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public abstract class EntityMixin {
    @Inject(method = "getTargetingMargin", at = @At("RETURN"), cancellable = true)
    private void hitbox$expand(CallbackInfoReturnable<Float> cir) {
        MinecraftClient mc = MinecraftClient.getInstance();
        if (mc == null || !mc.isInSingleplayer()) return;
        Entity self = (Entity) (Object) this;
        if (self instanceof LivingEntity && self != mc.player) {
            cir.setReturnValue(cir.getReturnValueF() + HitboxClient.margin);
        }
    }
}
