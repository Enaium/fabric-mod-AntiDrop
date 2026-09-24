/*
 * Copyright 2022 Enaium
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package cn.enaium.antidrop.mixin;

import cn.enaium.antidrop.event.ScreenCallbacks;
import net.minecraft.client.multiplayer.MultiPlayerGameMode;
import net.minecraft.client.player.LocalPlayer;
import net.minecraft.core.registries.BuiltInRegistries;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

/**
 * @author Enaium
 */
@Mixin(MultiPlayerGameMode.class)
public abstract class MultiPlayerGameModeMixin {

    @Inject(
            at = @At("HEAD"),
            method = "dropItem(Lnet/minecraft/client/player/LocalPlayer;Z)V",
            cancellable = true
    )
    public void dropSelectedItem(LocalPlayer player, boolean entireStack, CallbackInfo ci) {
        if (!ScreenCallbacks.DropSelectedItemCallback.Companion.getEVENT().getInvoker().interact(BuiltInRegistries.ITEM.getKey(player.getMainHandItem().getItem()).toString())) {
            ci.cancel();
        }
    }
}
